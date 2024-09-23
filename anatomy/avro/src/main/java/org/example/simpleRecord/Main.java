package org.example.simpleRecord;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AvroUserService.generateAvor();
        AvroUserService.avroToJson();

        AvroGenericService.generateAvro();
        AvroGenericService.avroToJson();
    }


}
