package scketch.anatomy;

import java.util.Properties;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class ReadSingleDocument {

    private static Properties _prop = new Properties();
    private static String _ApiKey;
    private static LoadJsons loadJsons = LoadJsons.getInstance();

    private static String[] _files;
    private static String _host;
    private static Elastic _es;

    public static void main(String[] args) throws Exception {


        _es = new Elastic();
        _prop.load(ReadSingleDocument.class.getClassLoader().getResourceAsStream("application.properties"));
        System.out.printf("\n%s,%s\n", ReadSingleDocument.class.getName(), _prop.getProperty("greeting"));
        _files = _prop.getProperty("files").split(",");

        String indexName;
        String[] tmp = _prop.getProperty("range").split(":");
        int range_start, range_end;
        range_start = Integer.parseInt(tmp[0]);
        range_end = Integer.parseInt(tmp[1]);

        for (int cnt = 0; cnt < _files.length; cnt++) {
            indexName = _files[cnt];

            for (int i = 0; i < 10; i++) {
                System.out.println(_es.readingDocumentById(indexName, String.format("%d", i)));
            }
        }
        _es.close();
        System.out.printf("\n%s,%s\n", ReadSingleDocument.class.getName(), _prop.getProperty("bye"));
        System.out.flush();
    }
}
