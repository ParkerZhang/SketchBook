package scketch.anatomy;
//https://github.com/elastic/elasticsearch/blob/main/client/rest/src/test/java/org/elasticsearch/client/ResponseExceptionTests.java

import co.elastic.clients.transport.http.TransportHttpClient;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        Properties prop = new Properties();
        String[] files, params;
        prop.load(Main.class.getClassLoader().getResourceAsStream("APIKEY"));
        String ApiKey = prop.getProperty("API");
        prop.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));
        System.out.println(prop.getProperty("greeting"));

        files = prop.getProperty("files").split(",");
        String[] tmp =prop.getProperty("range").split(":");
        int range_start, range_end;
        range_start = Integer.parseInt(tmp[0]);
        range_end = Integer.parseInt(tmp[1]);

        MemJson[] mj = new MemJson[files.length];
        for (int i = 0; i < files.length; i++) {
            params = prop.getProperty(files[i]).split(",");
            mj[i] = new MemJson(files[i], params);
        }

        RestClient restClient = RestClient.builder(
                        new HttpHost("asus", 9200, "https")
                        ,
                        new HttpHost("asus", 9201, "https")
                ).setDefaultHeaders(new Header[]{new BasicHeader("Authorization", String.format("ApiKey %s", ApiKey))})
                .build();

        Request request;
        Response response;
        String indexName  ;

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
                response = restClient.performRequest(request);

            } catch (Exception e) {
            }

            for (int i = 0;  i < mj[cnt].count(); i++) {
                if (i < range_start) i = range_start;
                if (i> range_end && range_end != -1) break;
                request = new Request(
                        "PUT",
                        String.format("/%s/_doc/%d",indexName, i));
                request.setJsonEntity(mj[0].get(i));
                response = restClient.performRequest(request);
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                System.out.print(body);
                System.out.printf("\n%d:%s", i, body);
            }
        }
        restClient.close();
        System.out.printf("\n%s\n",prop.getProperty("bye"));

    }
}