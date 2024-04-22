package scketch.anatomy;

import java.io.IOException;
import java.util.Properties;

public class LoadJsons  {


    private static MemJson[] _mj;
    private static Properties _prop;
    private static String[] _files;
    private static String _status;
    private static LoadJsons single_instance = null;

    public static synchronized LoadJsons getInstance()
    {
        System.out.println("getInstance");
        if (single_instance == null) {
            single_instance = new LoadJsons();
            System.out.println("First Instance ");
        }
        return single_instance;
    }

    public MemJson[] get_mj() {

        return _mj;
    }

    public String get_status() {
        return _status;
    }

    private  LoadJsons() {
        try {
            _prop = new Properties();
            _prop.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
            System.out.printf("\n%s,%s\n", LoadJsons.class.getName(), _prop.getProperty("greeting"));
        } catch (Exception e) {
            System.out.printf("%s,%s", LoadJsons.class.getName(), e.getMessage());
        }
        _files = _prop.getProperty("files").split(",");
        _mj = new MemJson[_files.length];
        _status = "Emppty";

    }

    public void load() {
        _status = "Dirty";
        String[] params;
        try {
            for (int i = 0; i < _files.length; i++) {
                params = _prop.getProperty(_files[i]).split(",");
                _mj[i] = new MemJson(_files[i], params);
            }
        } catch (Exception e) {
            System.err.printf("%s,%s", this.getClass().getName(), e.getMessage());
        }
        _status = "Clean";
    }

}
