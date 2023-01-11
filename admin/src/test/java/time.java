import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Classname time
 * @Description
 * @Date 2023/1/9 12:37
 * @Created by linmour
 */
public class time {
    @Test
    public void a(){


        DateTimeFormatter aFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.AUGUST, 3, 12, 30);
        String foramttedString = localDateTime.format(aFormatter); // "2017-03-08 12:30"

        System.out.println("origional LocalDatetime object: " + localDateTime);
        System.out.println("generated string : " + foramttedString);




    }
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(formatter);
    }
}
