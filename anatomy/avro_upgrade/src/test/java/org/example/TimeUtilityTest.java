package org.example;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;


public class TimeUtilityTest   {

    @Test
    public void testConvertZuluToJoda() {
        String zuluString = "2024-07-10T19:01:34Z";
        DateTime expected = new DateTime(zuluString);
        DateTime result = TimeUtility.convertZuluToJoda(zuluString);
        System.out.println(expected);
        System.out.println(result);
        Assert.assertEquals(expected.toDateTime( DateTimeZone.forID("UTC")),result.toDateTime( DateTimeZone.forID("UTC")));
    }

    @Test
    public void testConvertZuluToInstant() {
        String zuluString = "2024-07-10T19:01:34Z";
        Instant expected = Instant.parse(zuluString);
        Instant result = TimeUtility.convertZuluToInstant(zuluString);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testDateTimeToJavaInstant() {
        DateTime jodaDateTime = new DateTime("2024-07-10T19:01:34Z");
        Instant expected = jodaDateTime.toDate().toInstant();
        Instant result = TimeUtility.DateTimeToJavaInstant(jodaDateTime);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testCompareTime() {
        String zuluString = "2024-07-10T19:01:34Z";
//        assertDoesNotThrow(() -> TimeUtility.compareTime(zuluString));
    }
}
