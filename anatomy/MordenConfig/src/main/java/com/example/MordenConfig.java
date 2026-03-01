package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;

public class MordenConfig {
    private final Properties prop = new Properties();

    public MordenConfig(String fileName) {
        // getClass().getClassLoader() -- looking for the root of the class path
        // getClass().getResource() -- looking for the package path, unless / is
        // specified

       loadProperties(fileName);
    }   

    public String get(String key, String defauleValue) {
        return System.getProperty(key, prop.getProperty(key, defauleValue));
    }

    public static void main(String[] args) {
        System.out.println("System Property: app.name is " + System.getProperty("app.name"));
        if (args.length > 0) {
            System.out.println("First Program Argument: " + args[0]);
        }
        var config = new MordenConfig("config.properties");
        String name = config.get("app.name", "Default Name");
        System.out.println("App Name: " + name);
    }

    private void loadProperties(String fileName) {
        String sysFileName = System.getProperty("config.file");
        // 1. Get the filename from the System Property (-Dconfig.file=...)
        if (sysFileName == null || sysFileName.isEmpty()) {
            System.out.println("System property -Dconfig.file not provided, using properties file from Resource.");
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
                if (is != null)
                    prop.load(is);
                System.out.println("Properties loaded successfully from: " + fileName);
            } catch (IOException e) {
                System.err.println("Could not load config:" + e.getMessage());
            }
        } else {

            try (InputStream input = new FileInputStream(new File(sysFileName))) {
                prop.load(input);
                System.out.println("Properties loaded successfully from: " + sysFileName);
            } catch (IOException ex) {
                System.err.println("Could not find or load properties file: " + sysFileName);
                ex.printStackTrace();
            }
        }
    }
}
