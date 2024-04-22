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
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.printf("\n%s,%s\n", Main.class.getName(), "Starting");
        LoadJsons lj = LoadJsons.getInstance();

        MemJson[] mj;
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> {
                    lj.load();
                    System.out.println("\nData laded.");
                    return null;
                }
        );
        while (!completableFuture.isDone()) {
            System.out.println("\nLoading...");
            sleep(1000);
        }

        //System.out.printf("\n%s\n",Main.class.getName(), "bye");
        System.out.printf("\n%s,%s\n", Main.class.getName(), "bye");
        System.out.flush();
    }
}