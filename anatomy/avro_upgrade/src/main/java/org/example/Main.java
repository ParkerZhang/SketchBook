package org.example;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.example.TimeUtility.COB;
import static org.example.TimeUtility.convertZuluToInstant;
import static org.joda.time.format.DateTimeFormat.forPattern;
import static org.junit.Assert.assertEquals;
//import org.apache.commons.lang.StringUtils;

//import org.apache.commons.lang.StringUtils;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
        Instant now = Instant.now();

        System.out.println(now);
        ZonedDateTime ld = now.atZone(ZoneId.of("America/New_York")) ;
        java.time.format.DateTimeFormatter   formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println(formatter.format(ld));
        System.out.println(ZoneId.systemDefault());
        String sval = "2024-09-20";
        String sval_time=sval + "T17:00:00Z";
//        Instant dateValJava = convertZuluToInstant(sval_time);
//
//        DateTimeFormatter patternFormat= DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
//        DateTime dateValJoda = patternFormat.parseDateTime(sval+"T17:00:00").toDateTime(DateTimeZone.UTC);
//        System.out.printf("%s ==> instant is : %s, ==> Joda is: %s\n", sval_time,dateValJava,dateValJoda );
//
//
//
//
//         dateValJoda = patternFormat.parseDateTime(sval_time);
//
//        System.out.printf("%s ==> instant is : %s, ==> Joda is: %s\n", sval_time,dateValJava,dateValJoda.toInstant());
//
//
        java.time.format.DateTimeFormatter pattern = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        java.time.ZonedDateTime zonedDateTime = ZonedDateTime.parse(sval_time,pattern.withZone(ZoneId.systemDefault()));

        System.out.printf("%s ==> zonedDateTime is : %s, ==> instant is: %s\n", sval_time,zonedDateTime,zonedDateTime.toInstant() );

//        System.out.printf("is empty %b\n", StringUtils.isEmpty("hello"));
//        System.out.printf("is empty %b\n", StringUtils.isEmpty(""));
//        System.out.printf("is empty %b\n", StringUtils.isEmpty(null));
//
//
//
        TimeUtility.test();
//        COB(2024,9,28);
//        assertEquals(COB(2024,9,28),"2024-09-27");
//        COB(2024,9,29);
//       System.out.printf("COB of 2024-09-30 at 19hour is %s.\n",  COB(2024,9,30));
    }
}