package scketch.anatomy;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        String[] files, params;

        prop.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));
        System.out.println(prop.getProperty("greeting"));

        files = prop.getProperty("files").split(",");
        MemJson[] mj = new MemJson[files.length];
        for (int i = 0; i < files.length; i++) {
            params = prop.getProperty(files[i]).split(",");
            mj[i] = new MemJson(files[i], params);
        }
        System.out.println(prop.getProperty("bye"));
    }
}