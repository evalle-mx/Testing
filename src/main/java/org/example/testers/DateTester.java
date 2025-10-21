package org.example.testers;

import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTester {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) {
//        addTimeToday();
//        addHours();
        long timeInMillis = 1760581787755L;
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(timeInMillis);

        System.out.println( timeInMillis );
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeInMillis ) );
        long addedTimeInMillis = addHoursToMillis(timeInMillis,4,2);
        System.out.println( addedTimeInMillis );
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(addedTimeInMillis ) );
    }



    private static long addHoursToMillis(long timeInMillis, long hours, long mins) {
        mins+= (hours*60);
        return addMinsToMillis(timeInMillis, mins); //timeInMillis+(hours * 60 * 60 *1000);
    }
    private static long addMinsToMillis(long timeInMillis, long mins) {

        return timeInMillis + (mins * 60 *1000);
    }


    protected static void addHours() {
        Date currentDate = new Date();  //1695873164807 => 2023/09/27 23:52:44
        System.out.println("Today (now): " + dateFormat.format(currentDate));
        int iHrs =5;
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        c.add(Calendar.HOUR, iHrs);    //c.add(Calendar.HOUR, -1);

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();

        System.out.println("Date "+ iHrs+" : " + dateFormat.format(currentDatePlusOne));

    }

    /**
     * Agrega una unidad a cada elemento (+1y +1m +1d +1h +1m +1s
     */
    protected static void addTimeToday(){
//        long milis = currentDate.getTime();
//        System.out.println(milis);

        Date currentDate = new Date();  //1695873164807 => 2023/09/27 23:52:44
        System.out.println("Today (now): " + dateFormat.format(currentDate));

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        // manipulate date
        c.add(Calendar.YEAR, 1);    //c.add(Calendar.YEAR, -10);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, 1); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.HOUR, 1);    //c.add(Calendar.HOUR, -1);
        c.add(Calendar.MINUTE, 1);
        c.add(Calendar.SECOND, 1);

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();

        System.out.println("Date +1: " + dateFormat.format(currentDatePlusOne));
    }



}
