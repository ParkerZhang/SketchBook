package scketch.anatomy;
import java.io.FileOutputStream;
import java.util.Properties;

public class PointInTimeSearch {
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


//        GET review/_search
//        {
//            "runtime_mappings":{
//            "_doc_id":{
//                "type":"long",
//                        "script":{
//                    "source":"""
//                            emit(Integer.parseInt(doc['_id'].value));
//
//                              """
//                    }
//                }
//            },
//            "size":1000,
//                "search_after": [999],
//            "sort": [
//                {
//                    "_doc_id":"asc"
//                }
//                ]
//        }


        for (int cnt = 0; cnt < _files.length; cnt++) {
            indexName = _files[cnt];
            System.out.println(_es.searchAfterPITDocument(indexName, 10000,new FileOutputStream(String.format("/home/data/pitSearch-%s.json",indexName),false)));

        }
        _es.close();
        System.out.printf("\n%s,%s\n", PointInTimeSearch.class.getName(), _prop.getProperty("bye"));
        System.out.flush();
    }
}


