package com.example.MQ.constant;


public class Constant {
    static public final long SECOND = 1000L;
    static public final long MINUTE = 60 * SECOND;
    static public final long HOUR = 60 * MINUTE;

    static public final String DATATYPE = "appdatatype";
    static public final String TEMPQUEUEPREFIX = "AMQ.";
    public static final String DESTINATION_NAME = "DEV.QUEUE.1";

    public enum DataTypes {
        OURDATATYPE(10),
        OUROTHERDATATYPE(20);

        private final int value;

        DataTypes(int setting) { this.value = setting; }
        public int getValue() { return this.value; }
    }
}
