package scketch.anatomy;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class IndexSingleDocument {

    private static Properties _prop = new Properties();
    private static String _ApiKey;
    private static LoadJsons loadJsons =  LoadJsons.getInstance();
    private static RestClient _restClient;
    private static String[] _files;
    private static String _host;

    public static void main(String[] args) throws Exception {

        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> {
                    loadJsons.load();
                    System.out.println("\nLoaded.");
                    return null;
                }
        );

        _prop.load(Main.class.getClassLoader().getResourceAsStream("APIKEY"));
        _ApiKey = _prop.getProperty("API");
        _prop.load(IndexSingleDocument.class.getClassLoader().getResourceAsStream("application.properties"));
        System.out.printf("\n%s,%s\n", IndexSingleDocument.class.getName(), _prop.getProperty("greeting"));
        _files = _prop.getProperty("files").split(",");
        _host = _prop.getProperty("host");
        String[] tmp = _prop.getProperty("range").split(":");
        int range_start, range_end;
        range_start = Integer.parseInt(tmp[0]);
        range_end = Integer.parseInt(tmp[1]);

        _restClient = RestClient.builder(
                        new HttpHost(_host, 9200, "https")
                        ,
                        new HttpHost(_host, 9201, "https")
                ).setDefaultHeaders(new Header[]{new BasicHeader("Authorization", String.format("ApiKey %s", _ApiKey))})
                .build();

        Request request;
        Response response;
        String indexName;


        while (!completableFuture.isDone()) {
            System.out.println("Loading...");
            sleep(1000);
        }


        MemJson[] mj = loadJsons.get_mj();

        for (int cnt = 0; cnt < mj.length; cnt++) {
            indexName = mj[cnt].getname();
            request = new Request(
                    "PUT",
                    String.format("/%s", indexName));
            request.setJsonEntity("{\n" +
                    "  \"mappings\" : {\n" +
                    "    \"properties\" : {\n" +
                    "      \"@timestamp\" : {\n" +
                    "        \"format\" : \"strict_date_optional_time||epoch_second\",\n" +
                    "        \"type\": \"date\"\n" +
                    "      },\n" +
                    "    \"message\":{\n" +
                    "      \"type\" : \"wildcard\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}\n" +
                    "}");
            try {
                response = _restClient.performRequest(request);

            } catch (Exception e) {
            }

            for (int i = 0; i < mj[cnt].count(); i++) {
                if (i < range_start) i = range_start;
                if (i > range_end && range_end != -1) break;
                request = new Request(
                        "PUT",
                        String.format("/%s/_doc/%d", indexName, i));
                request.setJsonEntity(mj[0].get(i));
                response = _restClient.performRequest(request);
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                System.out.print(body);
                System.out.printf("\n%d:%s", i, body);
            }
        }
        _restClient.close();
        System.out.printf("\n%s\n", _prop.getProperty("bye"));

    }
}
