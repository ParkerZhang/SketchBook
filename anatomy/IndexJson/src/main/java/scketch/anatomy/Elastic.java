package scketch.anatomy;
//https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/connecting.html

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.json.spi.JsonProvider;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

public class Elastic {
    private static Properties _prop = new Properties();
    private static String _ApiKey;
    private static LoadJsons loadJsons = LoadJsons.getInstance();
    private static RestClient _restClient;
    private static String[] _files;
    private static ElasticsearchTransport _transport;
    private static String _host;

    public static ElasticsearchClient get_esClient() {
        return _esClient;
    }

    public static ElasticsearchTransport get_transport() {
        return _transport;
    }

    public static RestClient get_restClient() {
        return _restClient;
    }

    private static ElasticsearchClient _esClient;
    private BulkRequest.Builder _br;

    public Elastic() throws IOException {
        _prop.load(Main.class.getClassLoader().getResourceAsStream("APIKEY"));
        _ApiKey = _prop.getProperty("API");
        _prop.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));

        System.out.printf("%s,%s", this.getClass().getName(), _prop.getProperty("greeting"));
        _host = _prop.getProperty("host");
        _restClient = RestClient.builder(
                        new HttpHost(_host, 9200, "https")
                        ,
                        new HttpHost(_host, 9201, "https")
                ).setDefaultHeaders(new Header[]{new BasicHeader("Authorization", String.format("ApiKey %s", _ApiKey))})
                .build();

        _transport = new RestClientTransport(_restClient, new JacksonJsonpMapper());
        _esClient = new ElasticsearchClient(_transport);
    }

    public void createIndex(String name) throws IOException {
        try {
            if (_esClient.indices().get(d -> d.index(name)) == null) {
                _esClient.indices().create(d -> d.index(name));
            } else {
                System.out.printf("\n%s,Index %s already exists\n", this.getClass().getName(), name);
            }
        } catch (ElasticsearchException e) {
            _esClient.indices().create(d -> d.index(name));
        }
    }

    public void deleteIndex(String name) throws IOException {
        try {

            if (_esClient.indices().get(d -> d.index(name)) != null) {
                _esClient.indices().delete(d -> d.index(name));
            } else {
                System.out.printf("\n%s,Index %s does not exist\n", this.getClass().getName(), name);
            }
            ;
        } catch (Exception e) {
//            System.out.printf("\n%s,Index %s does not exist\n%s\n", this.getClass().getName(), name, e.getMessage());
        }
    }

    public String getIndex(String name) throws IOException {
        return _esClient.indices().get(d -> d.index(name)).toString();
    }

    public void indexDocument(String indexName, String id, String document) throws IOException {
        IndexResponse response = _esClient.index(i -> i
                .index(indexName)
                .id(id)
                .withJson(new StringReader(document))
        );
        System.out.printf("\n%s: indexed version %s ", id, response.version());
    }

    public void bulkStart() {
        _br = new BulkRequest.Builder();
    }

    public void bulkFlush() throws IOException {
        BulkResponse result = _esClient.bulk(_br.build());

        if (result.errors()) {
            System.out.printf("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    System.out.printf(item.error().reason());
                }
            }
        }
        bulkStart();
    }

    public void bulkIndexDocument(String indexName, String id, String document) throws IOException {
        Reader reader = new StringReader(document);
        JsonpMapper jsonpMapper = _esClient._transport().jsonpMapper();
        JsonProvider jsonProvider = jsonpMapper.jsonProvider();
        JsonData jd = JsonData.from(jsonProvider.createParser(reader), jsonpMapper);

        BulkRequest.Builder br = new BulkRequest.Builder();
        _br.operations(op -> op
                .index(idx -> idx
                        .index(indexName)
                        .id(id)
                        .document(jd)
                )
        );
    }

    public void close() throws IOException {
        _restClient.close();
        System.out.printf("\n%s,%s\n", this.getClass().getName(), _prop.getProperty("bye"));
    }


    public String readingDocumentById(String indexName, String id) throws IOException {
        GetResponse<ObjectNode> response = _esClient.get(g -> g
                        .index(indexName)
                        .id(id),
                ObjectNode.class);
        String result = null;

        if (response.found()) {
            ObjectNode json = response.source();
            result = json.toString();

        }
        return result;

    }
}
