import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvObjects {

  private String _filePath;
  private String _csvName;

  private String _key1, _key2, _key3, _key4, _value;
  private Map<String, CsvObject> _CsvObjectList = new HashMap<String, CsvObject>();
  private Map<String, Integer> _header = new HashMap<String, Integer>();

  public CsvObjects() {
    _filePath = null;
    _csvName = "_";
    _key1 = "key1";
    _key2 = "key2";
    _key3 = "key3";
    _key4 = "key4";
    _value = "value";
  }

  public CsvObjects(String csvName) {
    setCsvName(csvName);
  }

  ;

  public void setFile(String path) {

    _filePath = path;

  }

  public void setCsvName(String csvName) {
    Properties prop = MyProperty.prop;
    _csvName = csvName;
    _key1 = prop.getProperty(String.format("%s.key1", csvName));
    _key2 = prop.getProperty(String.format("%s.key2", csvName));
    _key3 = prop.getProperty(String.format("%s.key3", csvName));
    _key4 = prop.getProperty(String.format("%s.key4", csvName));
    _value = prop.getProperty(String.format("%s.value", csvName));
  }

  ;

  public Map<String, CsvObject> getList() {
    return _CsvObjectList;
  }

  public Map<String, CsvObject> load() throws RuntimeException, IOException {
    Logger log = Logger.getLogger(this.getClass().getName());
    StringBuilder tmpString = new StringBuilder();
    _CsvObjectList.clear();
    long ln = 0;
    BufferedReader br = new BufferedReader(new FileReader(_filePath));
    String[] header = br.readLine().replace("\uFEFF", "").split(","); // remove UTF8 BOM
    setHeader(header);
    String line = "";
    try {
      while ((line = br.readLine()) != null) {
        ln++;
        String[] field = line.split(",");
        CsvObject csvObject = new CsvObject();
        csvObject.setKey1(field[_header.get(_key1)]); // _header.get("key1")
        csvObject.setKey2(field[_header.get(_key2)]);
        csvObject.setType(field[_header.get(_key3)]);
        if (_CsvObjectList.get(csvObject.getKey()) == null) {
          _CsvObjectList.put(csvObject.getKey(), csvObject);

        } else {
          csvObject = _CsvObjectList.get(csvObject.getKey());

        }
        if (field.length == header.length) {
          csvObject.setValue(field[_header.get(_key4)], field[_header.get(_value)]);
        } else {
          log.log(Level.WARNING, String.format("skipped  : line: %d,\t header : %d, column: %d,  %s\n", ln,
              header.length, field.length, line));
        }
      }
    } catch (Exception e) {
      log.log(Level.WARNING, String.format("err: line: %d,\t %s\n%s\n", ln, line, e.getMessage()));
    }
    return _CsvObjectList;
  }

  public void setHeader(String[] header) {
    for (int i = 0; i < header.length; i++) {
      _header.put(header[i], i);
    }
  }

}
