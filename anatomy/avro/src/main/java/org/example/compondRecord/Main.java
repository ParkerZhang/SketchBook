package org.example.compondRecord;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        AvroGenericCompondService.generateAvro();
        AvroGenericCompondService.avroToJson();

        AvroPersonService avroPersonService = new AvroPersonService();
        avroPersonService.generateAvor();
        avroPersonService.avroToJson();
    }
}
