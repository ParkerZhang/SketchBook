import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvObject {
    private static long _unconvertedNumber = -1;
    private long _key1 = 0, _Key2 = 0;
    private Map<Long,String> _Value = new HashMap<Long,String>();

    private String _type = "";
    private Logger _log= Logger.getLogger(this.getClass().getName());
    public CsvObject() {
    }

    private long convertLong(String X) {
        long x;
        try {
            x = Integer.parseInt(X);
        } catch (Exception e) {
            x = _unconvertedNumber --;
            _log.log(Level.SEVERE,String.format("Expected integer: %s, convert to%d\n, %s\n", X,x, e.toString()));
        } ;
        return x;
    }
    public void setKey1(String key1) {

        _key1 = convertLong(key1);
    }

    public void setKey2(String key2) {
        _Key2 = convertLong(key2);
    }

    public void setValue(String key3, String value) {
        _Value.put(convertLong(key3),value);
    }

    public void setType(String type) {
        _type = type;
    }

    public String getKey() {
        return String.format("%d_%d_%s", _key1, _Key2, _type);
    }

    public Map<Long, String> getValue() {
        return _Value;
    }

    public String getValue(String key4){
        return _Value.get(convertLong(key4));
    }

    public String getValueLast() {
        return _Value.get(Collections.max(_Value.keySet()));
    }

    public String getType() {
        return _type;
    }

    public long getKey1() {
        return _key1;
    }

    public long getKey2() {
        return _Key2;
    }

}
