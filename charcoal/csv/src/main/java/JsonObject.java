import org.json.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonObject {
    private JSONObject _jo;
    private static Logger _log = Logger.getLogger(JsonObject.class.getName());

    public String get_key1() {
        return _key1;
    }

    public void set_key1(String _key1) {
        this._key1 = _key1;
    }

    private String _key1;

    public String get_key2() {
        return _key2;
    }

    public void set_key2(String _key2) {
        this._key2 = _key2;
    }

    private String _key2;

    public String get_key3() {
        return _key3;
    }

    public void set_key3(String _key3) {
        this._key3 = _key3;
    }

    public String get_key4() {
        return _key4;
    }

    public void set_key4(String _key4) {
        this._key4 = _key4;
    }

    public String get_value1() {
        return _value1;
    }

    public void set_value1(String _value1) {
        this._value1 = _value1;
    }

    private String _key3;
    private String _key4;

    private String _value1;

    public String get_value2() {
        return _value2;
    }

    private String _value2;
    private String _value3;

    private Map<String, String> _value = new HashMap<String, String>();

    public JsonObject() {
    }

    public JsonObject(String source) {
        _jo = new JSONObject(source);
    }

    public JsonObject(InputStream input) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = input.read(buffer)) != -1; ) {
            result.write(buffer);
        }
        _jo = new JSONObject(result.toString(StandardCharsets.UTF_8));
    }

    public String jsonSTring() {
        return JSONObject.valueToString(_jo.toString());
    }

    public void set_keys(String k1, String k2, String k3, String k4) {
        _key1 = getKeyValue(k1);
        _key2 = getKeyValue(k2);
        _key3 = getKeyValue(k3);
        _key4 = getKeyValue(k4);
    }

    public void set_values(String v1)  {
        _value1 = getKeyValue(v1);
    }
    public void set_values(String v1,String v2)  {
        _value1 = getKeyValue(v1);
        _value2 = getKeyValue(v2);
    }

    public String getKeyValue(String k) {
        String pointer = "", seperator = "";
        String retVal = null;
        int start = 0, len = 0, position = 0;
        pointer = k;
        try {
        } catch (Exception e) {
        }
        if (k.contains(":")) {  // messageid:10,2
            String[] tmp = k.split(":");
            pointer = tmp[0];
            String[] substring = tmp[1].split(",");
            start = Integer.parseInt(substring[0]);
            len = Integer.parseInt(substring[1]);
            try {

                retVal = _jo.query(pointer).toString();
                retVal = retVal.substring(start, len);
            } catch (Exception e) {
                _log.log(Level.WARNING, String.format("not able to split by %s, substring:%d,%d", k, start, len));
            }
            ;

        } else if (k.contains("|")) {  // messageid|_|2
            String[] tmp = k.split("\\|");
            pointer = tmp[0];
            seperator = tmp[1];
            position = Integer.parseInt(tmp[2]);
            try {
                retVal = _jo.query(pointer).toString();
                retVal = retVal.split(seperator)[position];
            } catch (Exception e) {
                _log.log(Level.WARNING, String.format("not able to split %s, %s,%d", k, seperator, position));
            }

        } else retVal = _jo.query(pointer).toString();

        return retVal;
    }
    public String getKey() {
        return String.format("%s|%s|%s",_key1,_key2,_key4);
    }

    public String getSubKey() {
        return String.format("%s",_key3);
    }

    public void setValue(String k,String v) {
        _value.put(k,v);
    }
    public Map<String,String> getValue(){ return _value;}
    public String getValueLast() {
        return _value.get(Collections.max(_value.keySet()));
    }
}
