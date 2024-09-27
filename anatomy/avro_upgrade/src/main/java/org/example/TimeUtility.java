package org.example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
//import org.apache.avro.data.TimeConversions.TimeMillisConversion;
import org.apache.avro.data.TimeConversions.TimeConversion;
import org.joda.time.DateTimeZone;
//import org.joda.time.LocalDateTime;
import org.joda.time.DateTime;

public class TimeUtility {
    DateTime joda_dt;
    org.joda.time.Instant instant_dt;

    public static DateTime convertZuluToJoda(String zuluString) {
        DateTime dt;
        org.joda.time.LocalDateTime ldt = null;
        try {
            dt = DateTime.parse(zuluString);
            ldt = dt.toLocalDateTime();
            System.out.printf("Zulu String %s  \n\tin DateTime : %s,\n\tin localDateTime is : %s\n\tin DateTime(UTC) :%s\n",zuluString,dt,ldt, ldt.toDateTime(DateTimeZone.UTC));
        } catch (Exception ex) {
            System.err.println("DATA issue and ERROR : " + ex.getMessage());
        }

        return ldt.toDateTime(DateTimeZone.UTC);
    }

    public static java.time.Instant convertZuluToInstant(String zuluString) {
        java.time.Instant dt;
        java.time.LocalDateTime ldt = null;
        try {
            dt = java.time.Instant.parse(zuluString);
            ldt =java.time.LocalDateTime.ofInstant( dt,ZoneId.of("UTC"));
        } catch (Exception ex) {
            System.err.println("DATA issue and ERROR : " + ex.getMessage());
        }
        return ldt.toInstant(ZoneOffset.UTC);
    }


    public static Instant DateTimeToJavaInstant(org.joda.time.DateTime  ldt) {
        java.time.Instant instant = ldt.toInstant().toDate().toInstant();
        System.out.println("Joda-Time DateTime: " + ldt);
        System.out.println("Converted Instant: " + instant);
        return instant;
    }

    public static void test()
    {
        String creationDateTime  = "2024-07-10T19:01:34Z";
        String creationDateTime2 = "2024-09-27T01:51:48Z";
        String swapCreationDateTime = "2024-08-26T15:56:18Z";   //--UTC
        System.out.printf("\nComparing %s\n",creationDateTime);
        compareTime(creationDateTime);
        System.out.printf("\nComparing %s\n",creationDateTime2);
        compareTime(creationDateTime2);
        System.out.printf("\nComparing %s\n",swapCreationDateTime);
        compareTime(swapCreationDateTime);
    }

    public static void compareTime(String zuluTime) {
        System.out.println(convertZuluToJoda(zuluTime));
        System.out.println(convertZuluToInstant(zuluTime));
        System.out.println(DateTimeToJavaInstant(convertZuluToJoda(zuluTime)));
    }
}
