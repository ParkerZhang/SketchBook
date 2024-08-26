package io.confluent.developer;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class PaymentSchema {
    public static Schema createSchema() {


        // Define the Avro schema

        Schema kafkaMessageSchema = SchemaBuilder.record("Payment")
                .namespace("io.confluent.developer.avro")
                .fields()
                .name("header").type(createHeader()).noDefault()
                .name("payload").type(createPayload()).noDefault()
                .endRecord();

        // Print the schema
        System.out.println(kafkaMessageSchema.toString());
        return kafkaMessageSchema;
    }

    public static Schema createHeader() {


        // Define the Avro schema
        return SchemaBuilder.record("Header")
                .namespace("io.confluent.developer.avro")
                .fields()
                .name("SendTo").type().stringType().noDefault()
                .name("SourceSystem").type().stringType().noDefault()
                .name("messageId").type().stringType().noDefault()
                .name("timestamp").type().stringType().noDefault()
                .endRecord();
    }
    public static Schema createPayload() {
        return SchemaBuilder.record("Payload")
                .namespace("io.confluent.developer.avro")
                .fields()
                .name("item").type().stringType().noDefault()
                .name("total_cost").type().doubleType().noDefault()
                .name("customer_id").type().stringType().noDefault()
                .name("purchase_date").type().unionOf().stringType().and().nullType().endUnion().stringDefault("null")
                .endRecord();
    }
}
