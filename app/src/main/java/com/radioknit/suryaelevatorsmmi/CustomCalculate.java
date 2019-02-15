package com.radioknit.suryaelevatorsmmi;

import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.radioknit.suryaelevatorsmmi.MainActivity.sendBtDataFlag;
import static com.radioknit.suryaelevatorsmmi.MainActivity.sendMessage;
import static com.radioknit.suryaelevatorsmmi.MainActivity.strCmpDate;
import static com.radioknit.suryaelevatorsmmi.MainActivity.writeModeFlag;

/**
 * Created by soft on 1/8/18.
 */

public class CustomCalculate {

    public static String calculateChkSum(int[] calValue){
        String strCmd=String.format("%02x",calValue[0])+String.format("%02x",calValue[1])+String.format("%02x",calValue[2])+String.format("%02x",calValue[3])+String.format("%02x",calValue[4])+String.format("%02x",calValue[5]);
        int chkSum  = 0;
        for(int i = 0; i<strCmd.length(); i++){
            chkSum = chkSum + strCmd.charAt(i);

        }
        // System.out.println("Checksum: "+chkSum);
        return Integer.toString(chkSum,16).substring(1,3);
    }

    /*public static String asciiToHex(String asciiValue){
        char[] chars =
    }*/

       public static byte hex2ascii(byte sum){
        if(sum<10)
           {
               sum = (byte) (sum | 0x30);
           }else{
            sum = (byte) (sum + 0x37);
        }
           return sum;
    }


    public static String currentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        String strCurDate = sdf.format(c.getTime());
        return strCurDate;
    }

    public static long[] calTimeDiff(String strStartDate, String strCurDate){
        /*Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        strCurDate = sdf.format(c.getTime());
        Log.e(TAG, "Date : " + strCurDate);*/

        long[] valDiff = new long[4];
        //String dateStart = strDate;
        //String dateStart = "06/21/2018 12:49:51";

        Date d1 = null;
        Date d2 = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());

        try {
            d1 = format.parse(strStartDate);
            d2 = format.parse(strCurDate);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            valDiff[0] = diffDays;
            valDiff[1] = diffHours;
            valDiff[2] = diffMinutes;
            valDiff[3] = diffSeconds;
             Log.e("Custom", "diffDays : " + diffDays);
             Log.e("Custom", "diffHours : " + diffHours);
             Log.e("Custom", "diffMinutes : " + diffMinutes);
             Log.e("Custom", "diffSeconds : " + diffSeconds);


            // System.out.print(diffDays + " days, ");
            // System.out.print(diffHours + " hours, ");
            // System.out.print(diffMinutes + " minutes, ");
            // System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return valDiff;
    }


    public static void chkWriteModeState() {
        if (strCmpDate.equals("0")) {
            writeModeFlag = false;
        } else {
            String strCurDate = CustomCalculate.currentDate();
            long[] timeDiff = calTimeDiff(strCmpDate, strCurDate);
            if (timeDiff[0] < 1) {
                if (timeDiff[1] < 1) {
                    if (timeDiff[2] > 10) {
                        writeModeFlag = false;
                    }
                } else {
                    writeModeFlag = false;
                }
            } else {
                writeModeFlag = false;
            }
        }
        Log.e("Custom", "writeModeFlag: " + writeModeFlag);
    }

}
