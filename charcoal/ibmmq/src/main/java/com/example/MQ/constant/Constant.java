package com.example.MQ.constant;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public   class Constant {
    static public final long SECOND = 1000L;
    static public final long MINUTE = 60 * SECOND;
    static public final long HOUR = 60 * MINUTE;

    static public final String DATATYPE = "appdatatype";
    static public final String TEMPQUEUEPREFIX = "AMQ.";
    public static final int HEARTBEAT_RATE = 10000;

    public static String DESTINATION_NAME ;//= "DEV.QUEUE.1";

    @Value("${mq.DESTINATION_NAME}")
    public void setDestinationName(String value) {
            DESTINATION_NAME=value;
            System.out.println("Loading constant");
    }
    public static final String LUBB_DUPP = "Lubb-dupp";

    public enum DataTypes {
        OURDATATYPE(10),
        OUROTHERDATATYPE(20);

        private final int value;

        DataTypes(int setting) { this.value = setting; }
        public int getValue() { return this.value; }
    }
}
