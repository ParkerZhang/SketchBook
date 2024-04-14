// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());
    private static Properties _prop;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");


        log.log(Level.INFO, "Application Started");
        _prop = MyProperty.prop;
        _prop.load(new FileInputStream("src/main/resources/application.properties"));
        // _prop.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));
        CsvObjects csvObjects = new CsvObjects();
        csvObjects.setCsvName(_prop.getProperty("Main.csv1.csvName"));
        csvObjects.setFile(_prop.getProperty("Main.csv1.fileName"));
        Map<String, CsvObject> A = csvObjects.load();


        // json objects
        JsonObjects jos = new JsonObjects();
        jos.setJsonName(_prop.getProperty("Main.Regulator.folder"));
        jos.setFolder(_prop.getProperty("Main.Regulator.folder"));
        Map<String, JsonObject> B = jos.load();

        log.log(Level.INFO, String.valueOf(B.size()));
        int count = 0;
        for (JsonObject jo : B.values()) {
            // log.log(Level.INFO, jo.getValue().toString());
            count = count + jo.getValue().size();
        }
        System.out.println(count);


        // Diff B-A
        String[] k;
        String ka;
        for (String kb : B.keySet()) {
            System.out.println("col1,col2,event,oldvalue,newvalue");
            k = kb.split("\\|");
            ka = String.format("%s_%s_UTI", k[0].split("_")[0].toString(), k[1]);
            if (A.get(ka) == null) {
            } else
                System.out.printf("%s,%s,%s\n", kb.replace("|", ","), A.get(ka).getValueLast(), B.get(kb).getValueLast());
        }
    }

    // @Test
    // void addition() {
    //     assertEquals(2, 1 + 1);
    // }
}