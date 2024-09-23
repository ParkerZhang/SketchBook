package org.example.compondRecord;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.file.DataFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvroGenericCompondService {


    public static void generateAvro() throws IOException {
        String schemaJson = "{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"Person\",\n" +
                "  \"namespace\": \"com.example\",\n" +
                "  \"fields\": [\n" +
                "    { \"name\": \"name\", \"type\": \"string\" },\n" +
                "    { \"name\": \"age\", \"type\": \"int\" },\n" +
                "    {\n" +
                "      \"name\": \"addresses\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"array\",\n" +
                "        \"items\": {\n" +
                "          \"type\": \"record\",\n" +
                "          \"name\": \"Address\",\n" +
                "          \"fields\": [\n" +
                "            { \"name\": \"street\", \"type\": \"string\" },\n" +
                "            { \"name\": \"city\", \"type\": \"string\" },\n" +
                "            { \"name\": \"zipCode\", \"type\": \"string\" }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

//            Schema schema = new Schema.Parser().parse(schemaJson);
        Schema schema = new Schema.Parser().parse(new File("person.avsc"));
        // Create a new Person record
        GenericRecord person = new GenericData.Record(schema);
        person.put("name", "John Doe");
        person.put("age", 30);

        // Create addresses
        List<GenericRecord> addresses = new ArrayList<>();
        GenericRecord address = new GenericData.Record(schema.getField("addresses").schema().getElementType());
        address.put("street", "123 Main St");
        address.put("city", "Anytown");
        address.put("zipCode", "12345");
        addresses.add(address);

        // Add addresses to person
        person.put("addresses", addresses);

        // Write to file
        File file = new File("person.avro");
        DatumWriter<GenericRecord> datumWriter = new SpecificDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(person);
        dataFileWriter.close();

        System.out.println("Avro file created: " + file.getAbsolutePath());
    }


    public static void avroToJson() throws IOException {
        Schema schema = new Schema.Parser().parse(new File("person.avsc"));
        File file = new File("person.avro");
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
        GenericRecord user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user);
            System.out.println(user);

        }

    }

}