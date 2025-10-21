package org.photomgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DupeNamer {
    private static List noDupList=null;

    public static void main(String[] args) {
//        testHoursName();

        noDupList = new ArrayList();
        noDupList.add("20221030_001714.JPG");
        noDupList.add("20221030_001714_0.JPG");
        noDupList.add("20221030_001714_1.JPG");
        noDupList.add("20221030_001714_2.JPG");

        testDups("20221030_001714.JPG");
    }

    //public static testHourCald()

    private static String addZero(int number){
        return number<1?"00":number<10?"0"+number:
                ""+number;
    }

    private static void testDups(String new_name){
        if(noDupList==null) {noDupList = new ArrayList();}
        int breaker = 0;
        String tempName = new_name.substring(0, new_name.lastIndexOf("."));
        String tempExt = new_name.substring(new_name.lastIndexOf("."));
        System.out.format("tempName: %s  tempExt: %s \n", tempName, tempExt);

        //System.out.println("exists? " + noDupList.contains (new_name) );
        while(noDupList.contains (new_name) && breaker <300){
            new_name = tempName+"_"+breaker+tempExt;
//            noDupList.add(new_name);
            breaker++;
        }

        noDupList.add(new_name);
        System.out.println("noDupList: " + noDupList);
        System.out.println("last: "+new_name);
    }

    private static void testHoursName(){
        /*

        //20221120_130202  ==> 20221120_135802
        int hrs = 13, mins = 2;
        int mComp = 56, hComp =0;
        int minsFin = 0, hoursFin=0;

        minsFin = mins+mComp;
        System.out.println(minsFin%60);
        hoursFin = hrs+hComp;

        String uno = "20221120_"+addZero(hrs)+addZero(mins)+"02";
        String dos = "20221120_"+addZero(hoursFin)+addZero(minsFin)+"02";

        System.out.println("mins final "+ (mins+mComp));
        String exp = "20221120_130202  ==> 20221120_135802";
        System.out.println( exp +"\n" +uno + "  ==> "+dos);  */

        //20221120_131002  ==> 20221120_140602
        int hrs = 13, mins = 10;
        int mComp = 56, hComp =0;
        int minsFin = 0, hoursFin=0;

        minsFin = mins+mComp;
        hoursFin = hrs+hComp;
        System.out.println(minsFin%60);
        if(minsFin>59){
            hoursFin= hoursFin+1;
            minsFin=minsFin%60;
        }
        String uno = "20221120_"+addZero(hrs)+addZero(mins)+"02";
        String dos = "20221120_"+addZero(hoursFin)+addZero(minsFin)+"02";

        System.out.println("mins final "+ (mins+mComp));

        String exp = "20221120_131002  ==> 20221120_140602";
        System.out.println( exp +"\n" +uno + "  ==> "+dos);
    }
}
