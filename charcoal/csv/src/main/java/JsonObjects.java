import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class JsonObjects {
    private String _key1, _key2, _key3, _key4, _value, _value2, _folder, _JsonName;
    private Map<String, JsonObject> _jsonList = new HashMap<String, JsonObject>();

    public String get_value2() {
        return _value2;
    }

    public JsonObjects() {
        _folder = null;
        _JsonName = "_";
        _key1 = "key1";
        _key2 = "key2";
        _key3 = "key3";
        _key4 = "key4";
        _value = "value";
        _value2 = "value2";
    }

    public JsonObjects(String JsonName) {
        setJsonName(JsonName);
    }

    public void setFolder(String path) {
        _folder = path;
    }

    public void setJsonName(String JsonName) {
        Properties prop = MyProperty.prop;
        _JsonName = JsonName;
        _key1 = prop.getProperty(String.format("%s.key1", JsonName));
        _key2 = prop.getProperty(String.format("%s.key2", JsonName));
        _key3 = prop.getProperty(String.format("%s.key3", JsonName));
        _key4 = prop.getProperty(String.format("%s.key4", JsonName));
        _value = prop.getProperty(String.format("%s.value", JsonName));
        _value2 = prop.getProperty(String.format("%s.value2", JsonName));
    }

    public Map<String, JsonObject> load() {
        StringBuilder tmpString = new StringBuilder();
        int count = 0;
        try {
            File[] files = new File(_folder).listFiles();
            for (File f : files) {
                if (!f.isDirectory()) {

                    FileInputStream fs = new FileInputStream(f);
                    JsonObject jo = readJson(fs);
                    if (_jsonList.get(jo.getKey()) == null) {
                        _jsonList.put(jo.getKey(), jo);
                        jo.setValue(jo.getSubKey(), jo.get_value1());

                    } else {
                        _jsonList.get(jo.getKey()).setValue(jo.getSubKey(), jo.get_value1());
                    }
                    fs.close();
                    count++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return _jsonList;
    }

    private JsonObject readJson(InputStream input) throws IOException {
        JsonObject jo = new JsonObject(input);
        jo.set_keys(_key1, _key2, _key3, _key4);
        jo.set_values(_value, _value2);
        return jo;
    }
}
