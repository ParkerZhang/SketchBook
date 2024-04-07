// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.util.Map;

public class Main {
  private static Logger log = Logger.getLogger(Main.class.getName());
  private static Properties _prop;
  public static void main(String[] args) throws IOException {
    System.out.println("Hello world!");


    log.log(Level.INFO, "Application Started");
    _prop = MyProperty.prop;
    _prop.load( new FileInputStream("src/main/resources/application.properties"));
    // _prop.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));
    CsvObjects csvObjects = new CsvObjects();
    csvObjects.setCsvName(_prop.getProperty("Main.csv1.csvName"));
    csvObjects.setFile(_prop.getProperty("Main.csv1.fileName"));
    Map <String,CsvObject> list = csvObjects.load();
    log.log(Level.INFO,list.toString());
    log.log(Level.INFO, list.get("1_12_1.3").getValue().toString());
    log.log(Level.INFO, list.get("1_12_1.3").getValueLast().toString());
    log.log(Level.INFO, list.get("31_12_3.3").getValue().toString());
    log.log(Level.INFO, list.get("31_12_3.3").getValueLast());
  }

  // @Test
  // void addition() {
  //     assertEquals(2, 1 + 1);
  // }
}