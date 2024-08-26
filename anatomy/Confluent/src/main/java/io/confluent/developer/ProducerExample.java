package io.confluent.developer;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class ProducerExample {
    static String schema ="{  \"type\":\"record\",  \"namespace\": \"io.confluent.developer.avro\",  \"name\":\"Purchase\",  \"fields\": [    {\"name\": \"item\", \"type\":\"string\"},    {\"name\": \"total_cost\", \"type\": \"double\" },    {\"name\": \"customer_id\", \"type\": \"string\"} , {\"name\": \"purchase_date\", \"type\" : [\"string\",\"null\"],\"default\":\"null\"}  ]}";
    static String cashflowSchema = "{\"type\":\"record\",\"name\":\"Payment\",\"namespace\":\"io.confluent.developer.avro\",\"fields\":[{\"name\":\"header\",\"type\":{\"type\":\"record\",\"name\":\"Header\",\"fields\":[{\"name\":\"SendTo\",\"type\":\"string\"},{\"name\":\"SourceSystem\",\"type\":\"string\"},{\"name\":\"messageId\",\"type\":\"string\"},{\"name\":\"timestamp\",\"type\":\"string\"}]}},{\"name\":\"payload\",\"type\":{\"type\":\"record\",\"name\":\"Payload\",\"fields\":[{\"name\":\"item\",\"type\":\"string\"},{\"name\":\"total_cost\",\"type\":\"double\"},{\"name\":\"customer_id\",\"type\":\"string\"},{\"name\":\"purchase_date\",\"type\":[\"string\",\"null\"],\"default\":\"null\"}]}}]}";
    public static void main(final String[] args) throws IOException {


        final Properties config = readConfig("client.properties");
        final String topic = "ist.swapone.cashflow";
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        config.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, true);
        try (final Producer<String, GenericRecord> producer = new KafkaProducer<>(config)) {
            //  GenericRecord user = new GenericRecordBuilder(new org.apache.avro.Schema.Parser().parse(new File("user.avsc")))
            //GenericRecord purchase = new GenericRecordBuilder(new Schema.Parser().parse(schema))

            Schema paymentSchema = PaymentSchema.createSchema();
            Schema headerSchema = PaymentSchema.createHeader();
            Schema payloadSchema = PaymentSchema.createPayload();

            GenericRecord payload = new   //GenericRecordBuilder(new Schema.Parser().parse(schema))
                     GenericRecordBuilder(payloadSchema)
                    .set("item", "watch")
                    .set("total_cost", 30.2)
                    .set("customer_id", "VIP_25")
                    .set("purchase_date","2024-08-25")
                    .build();
            GenericRecord header = new GenericRecordBuilder(headerSchema)
                    .set("messageId","12345")
                    .set("timestamp", "123.456")
                    .set("SendTo","SWE")
                    .set("SourceSystem","SwapOne")
                    .build();
            GenericRecord purchase = new
                    //GenericRecordBuilder(new Schema.Parser().parse(cashflowSchema))
                    GenericRecordBuilder(paymentSchema)
                    .set("header",header).set("payload",payload).build();

            ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(topic, purchase);
            producer.send(record);
            producer.send(record);
            producer.send(record);
            producer.send(record);
            System.out.printf("Record Sent : %s\n", record.toString());
//            producer.close();
        }
        catch (Exception e) { System.err.println(e.toString());}
    }

    public static Properties readConfig(final String configFile) throws IOException {
        // reads the client configuration from client.properties
        // and returns it as a Properties object
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }

        final Properties config = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            config.load(inputStream);
        }
        return config;
    }
}
