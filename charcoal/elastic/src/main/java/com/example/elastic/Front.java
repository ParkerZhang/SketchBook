package com.example.elastic;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;


@RestController
public class Front extends ControllerBase {
    @Autowired
    private Environment environment;
    private String jsonPath;
    private final String _jsonString =
            "{\"data\":[{\"c\":[\"1\",\"12\"],\"p\":181.535,\"s\":\"TSLA\",\"t\":1711461682628,\"v\":1},{\"c\":[\"1\",\"12\"],\"p\":181.52,\"s\":\"TSLA\",\"t\":1711461682472,\"v\":10},{\"c\":[\"1\",\"12\"],\"p\":181.58,\"s\":\"TSLA\",\"t\":1711461682650,\"v\":1},{\"c\":[\"1\",\"8\",\"12\"],\"p\":181.58,\"s\":\"TSLA\",\"t\":1711461682652,\"v\":96},{\"c\":[\"1\",\"8\"],\"p\":181.58,\"s\":\"TSLA\",\"t\":1711461682653,\"v\":204},{\"c\":[\"1\",\"8\",\"12\"],\"p\":181.6,\"s\":\"TSLA\",\"t\":1711461682653,\"v\":4},{\"c\":[\"1\",\"8\",\"12\"],\"p\":181.59,\"s\":\"TSLA\",\"t\":1711461682653,\"v\":10},{\"c\":[\"1\"],\"p\":181.58,\"s\":\"TSLA\",\"t\":1711461682653,\"v\":100},{\"c\":[\"1\"],\"p\":181.58,\"s\":\"TSLA\",\"t\":1711461682654,\"v\":100},{\"c\":null,\"p\":70367.54,\"s\":\"BINANCE:BTCUSDT\",\"t\":1711461681747,\"v\":0.00012}],\"type\":\"trade\"}";
    private String _front;

    public Front() {

    }

    @GetMapping("/load")
    public String load() throws IOException {
        set();
        return "completed!";
    }


    @GetMapping("/list")
    public String get() {
        return _front;
    }

    @GetMapping("/restore")
    public String restore() {
        StringBuilder tmpString = new StringBuilder();
        jsonPath = environment.getProperty("front.path");
        _start();
        super.set(null);
        File[] files = new File(jsonPath + "/archive/").listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
                f.renameTo(new File(jsonPath + "/" + f.getName()));
                _count++;
            }
        }
        super.set(String.format("length %d", tmpString.length()));
        _end("complete");
        return _toJson();

    }

    public void set(InputStream input) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = input.read(buffer)) != -1; ) {
            result.write(buffer);
        }
        _front = result.toString(StandardCharsets.UTF_8);
    }


    public void setIOUtils(InputStream input) throws IOException {
        _front = IOUtils.toString(input, StandardCharsets.UTF_8);
    }

    public void set() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(_jsonString.getBytes(StandardCharsets.UTF_8));
        set(input);
    }

    @GetMapping("/loadDir")
    private String loadDir(@RequestParam("option") String option) throws IOException {
        jsonPath = environment.getProperty("front.path");
        switch (option) {
            case "1":
                return String.format("{\"option\" : \"1\" ,\"result\" :%s}", set_1(jsonPath));
            case "2":
                return String.format("{\"option\" : \"2\" ,\"result\" : %s}", set_2("/home/src/finnhub/data"));
            default:
                return String.format("{\"option\" : \"0\" ,\"result\" : %s}", set(jsonPath));
        }
    }

    public String set_1(String path) {
        StringBuilder tmpString = new StringBuilder();
        try {
            _start();
            super.set(null);
            File[] files = new File(path).listFiles();
            for (File f : files) {
                if (!f.isDirectory()) {
                    FileInputStream fs = new FileInputStream(f);
                    setIOUtils(fs);
                    //tmpString.append(_front);
                    _count++;
                    fs.close();
                    f.renameTo(new File(path + "/archive/" + f.getName()));
                }
            }
            super.set(String.format("length %d", tmpString.length()));
            _end("complete");
            return _toJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String set_2(String path) {
        return String.format("{\"%d\" : \"%s\" }", 2, path);
    }

    public String set(String path) {
        StringBuilder tmpString = new StringBuilder();
        try {
            _start();
            super.set(null);
            File[] files = new File(path).listFiles();
            for (File f : files) {
                if (!f.isDirectory()) {
                    FileInputStream fs = new FileInputStream(f);
                    set(fs);
                    //tmpString.append(_front);
                    _count++;
                    fs.close();
                    f.renameTo(new File(path + "/archive/" + f.getName()));
                }
            }
            super.set(String.format("length %d", tmpString.length()));
            _end("complete");
            return _toJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
