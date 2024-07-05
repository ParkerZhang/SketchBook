package org.example;

import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.flattener.PrintMode;
import com.github.wnameless.json.unflattener.JsonUnflattener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        String json = "{ \"a\":\n" +
                "  { \"b\": 1,\n" +
                "    \"c\": null,\n" +
                "    \"d\": [false, true]\n" +
                "  },\n" +
                "  \"e\": \"f\",\n" +
                "  \"g\": 2.3\n" +
                "}";

        if (args.length < 1 )
                json = getInput();
        else
                json = Files.readString(Path.of(args[0]));


        final FlattenMode flatMode =  FlattenMode.MONGODB ;
        final PrintMode  printMode = PrintMode.PRETTY;
        final JsonFlattener jsonFlattener = new JsonFlattener(json).withFlattenMode(flatMode).withPrintMode(printMode);

        String resultedJson = jsonFlattener.flatten();

        System.out.println(resultedJson);
    }
    private static String getInput() {
        StringBuilder sb = new StringBuilder();
        final Scanner scanner = new Scanner(System.in);
        for (String line = scanner.nextLine(); !line.isEmpty(); line = scanner.nextLine()) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.length() == 0 ? null : sb.toString();
    }
}