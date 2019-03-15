package com.radioknit.suryaelevatorsmmi;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;


/**

 */
public class UtilsBluetooth {

    /**

     */
    public static void log(String message) {
        if (BuildConfig.DEBUG) {
            if (message != null) Log.i(Const.TAG, message);
        }
    }
    // ============================================================================


    /**
     *
     */
    public static String printHex(String hex) {
        StringBuilder sb = new StringBuilder();
        int len = hex.length();
        String a=(toHex1(hex));
        try {
            for (int i = 0; i < len; i += 2) {
                sb.append("0x").append(hex.substring(i, i + 2)).append(" ");
            }
        } catch (NumberFormatException e) {
            log("printHex NumberFormatException: " + e.getMessage());

        } catch (StringIndexOutOfBoundsException e) {
            log("printHex StringIndexOutOfBoundsException: " + e.getMessage());
        }
        return sb.toString();
    }
    // ============================================================================


    /**

     * @param hex -
     * @return -
     */
    public static byte[] toHex(String hex) {
        int len = hex.length();
        byte[] result = new byte[len];
        try {
            int index = 0;
            for (int i = 0; i < len; i += 2) {
                result[index] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);

                Log.e("Tag_loop", "toHex: index ="+index + " i = " + i + " val= " + result[index] );

                index++;
            }
            for (int i = 0;i<result.length;i++)
                Log.e("Tag_final", "toHex: final = " + result[i] );


        } catch (NumberFormatException e) {
            log("toHex NumberFormatException: " + e.getMessage());

        } catch (StringIndexOutOfBoundsException e) {
            log("toHex StringIndexOutOfBoundsException: " + e.getMessage());
        }
        return result;
    }

    public static long toAscii(String s){
        StringBuilder sb = new StringBuilder();
        String ascString = null;
        long asciiInt;
        for (int i = 0; i < s.length(); i++){
            //sb.append((int)s.charAt(i));
            int asciiVal=(int)s.charAt(i);
            sb.append(Integer.toHexString(asciiVal));
            char c = s.charAt(i);
        }
        Log.d("Tag_ascii", "toAscii: final ascii hex=" + sb.toString() );
        ascString = sb.toString();

        asciiInt = Long.parseLong(ascString);
        Log.d("Tag_ascii", "toAscii: "+asciiInt+"\nasciiStr="+ascString);
        return asciiInt;
    }

    public static String hexToASCII(String hex)
    {
        // initialize the ASCII code string as empty.
        String ascii = "";

        for (int i = 0; i < hex.length(); i += 2) {

            // extract two characters from hex string
            String part = hex.substring(i, i + 2);

            // change it into base 16 and typecast as the character
            char ch = (char)Integer.parseInt(part, 16);

            // add this char to final ASCII string
            ascii = ascii + ch;
        }

        Log.d("Tag_ascii", "hexToASCII: "+ascii);
        return ascii;
    }



    // ============================================================================
    public static String  toHex11(String hex) {

        StringBuffer buffer=new StringBuffer();

        String s1="49";
        String s2="49";

        String s3="00";
        String s4="00";

        int d=13;
        String a81=String.valueOf(d);

        int x1 = Integer.parseInt(s1,16);
        int x2 = Integer.parseInt(s2,16);
        int x3 = Integer.parseInt(s3,16);
        int x4 = Integer.parseInt(s4,16);

        int len = hex.length();
        byte[] result = new byte[len];

        int index = 0;
        for (int i = 0; i < len; i += 2) {
            result[index] = (byte) Integer.parseInt(hex.substring(i, i + 2),16);
            index++;

        }


        String str1=Integer.toHexString(result[0]);

        int a1 = Integer.parseInt(str1,16);
        //System.out.println("RES="+a1);


        int a=x1+x2+a1+x3+x4;
        int a6 =a & 15;

//        System.out.println("a6="+a6);

        int a7 = (a & 240)>>4 ;

//        System.out.println("a7="+a7);
        buffer.append(s1).append(s2).append(a1).
                append(s3).append(s4).append(a7).append(a6).append(a81);

        return  buffer.toString();
    }
    // ============================================================================

    public static String  toHex1(String hex) {

        StringBuffer buffer=new StringBuffer();
        int len = hex.length();
        byte[] result = new byte[len];

            int index = 0;
            for (int i = 0; i < len; i += 2) {
                result[index] = (byte) Integer.parseInt(hex.substring(i, i + 2),16);
                index++;

            }
        // System.out.println("RES="+result[0]);

        String str1=Integer.toHexString(result[0]);
        String str2=Integer.toHexString(result[1]);
        String str3=Integer.toHexString(result[2]);
        String str4=Integer.toHexString(result[3]);
        String str5=Integer.toHexString(result[4]);

        int a1 = Integer.parseInt(str1,16);
        int a2 = Integer.parseInt(str2,16);
        int a3 = Integer.parseInt(str3,16);
        int a4 = Integer.parseInt(str4,16);
        int a5 = Integer.parseInt(str5,16);


        int a=a1+a2+a3+a4+a5;
        String z=Integer.toHexString(a);
        //System.out.println("RES="+z);


        int a6 =a & 15;

        String a61=decimal_value(a6);
       // System.out.println("a61="+a61);

        int a7 = (a & 240)>>4 ;
        String a71=decimal_value(a7);
        //System.out.println("a7="+a71);


        hex=hex.concat(a61);
        hex=hex.concat(a71);

        int d=13;
        String a81=String.valueOf(d);

        hex=hex.concat(a81);



       // System.out.println("STRING="+hex);

       // return z.getBytes();

         buffer.append(hex).append("\n").append(z);
        //buffer.append(hex);
        return  buffer.toString();
    }

    public static byte[] toHex2(String hex) {

        int len = hex.length();
        byte[] result = new byte[len];
        try {
            int index = 0;
            for (int i = 0; i < len; i += 2) {
                result[index] = (byte) Integer.parseInt(hex.substring(i, i + 2),16);
                index++;

            }
        } catch (NumberFormatException e) {


        } catch (StringIndexOutOfBoundsException e) {

        }
        // System.out.println("RES="+result[0]);

        String str1=Integer.toHexString(result[0]);
        String str2=Integer.toHexString(result[1]);
        String str3=Integer.toHexString(result[2]);
        String str4=Integer.toHexString(result[3]);
        String str5=Integer.toHexString(result[4]);

        int a1 = Integer.parseInt(str1,16);
        int a2 = Integer.parseInt(str2,16);
        int a3 = Integer.parseInt(str3,16);
        int a4 = Integer.parseInt(str4, 16);
        int a5 = Integer.parseInt(str5,16);


        int a=a1+a2+a3+a4+a5;
        String z=Integer.toHexString(a);
//        System.out.println("RES="+z);


        int a6 =a & 15;

        String a61=decimal_value(a6);
//        System.out.println("a61="+a61);

        int a7 = (a & 240)>>4 ;
        String a71=decimal_value(a7);
//        System.out.println("a7="+a71);


        hex=hex.concat(a61);
        hex=hex.concat(a71);

        int d=13;
        String a81=String.valueOf(d);

        hex=hex.concat(a81);

//        System.out.println("STRING="+hex);


        return z.getBytes();
    }

    public static byte[] toHex3(String hex) {
        int a =0;
        int len = hex.length();
        byte[] result = new byte[len];
        byte[] buffer = new byte[a];
        try {
            int index = 0;
            for (int i = 0; i < len; i += 2) {
                result[index] = (byte) Integer.parseInt(hex.substring(i, i + 2),16);
                index++;

            }
        } catch (NumberFormatException e) {


        } catch (StringIndexOutOfBoundsException e) {

        }
        // System.out.println("RES="+result[0]);

        String str1=Integer.toHexString(result[0]);
        String str2=Integer.toHexString(result[1]);
        String str3=Integer.toHexString(result[2]);
        String str4=Integer.toHexString(result[3]);
        String str5=Integer.toHexString(result[4]);

        int a1 = Integer.parseInt(str1,16);
        int a2 = Integer.parseInt(str2,16);
        int a3 = Integer.parseInt(str3,16);
        int a4 = Integer.parseInt(str4,16);
        int a5 = Integer.parseInt(str5,16);


        a=a1+a2+a3+a4+a5;
        String z=Integer.toHexString(a);
        // System.out.println("RES="+z);


        int a6 =a & 15;

        String a61=decimal_value(a6);
        // System.out.println("a61="+a61);

        int a7 = (a & 240)>>4 ;
        String a71=decimal_value(a7);
        //  System.out.println("a7="+a71);


        hex=hex.concat(a61);
        hex=hex.concat(a71);

        int d=13;
        String a81=String.valueOf(d);

        hex=hex.concat(a81);

        //System.out.println("STRING="+hex);


        return buffer;
    }

    public static String decimal_value(int val) {
        String va = null;
        if (val == 10) {
            va = "A";
        } else if (val == 11) {
            va = "B";
        } else if (val ==12 ) {
            va = "C";
        } else if (val ==13 ) {
            va ="D";
        } else if (val ==14 ) {
            va = "E";
        } else if (val == 15) {
            va = "F";
        } else if (val == 0) {
            va = "0";
        } else if (val == 1) {
            va = "1";
        } else if (val == 2) {
            va = "2";
        } else if (val == 3) {
            va = "3";
        } else if (val == 4) {
            va = "4";
        } else if (val == 5) {
            va = "5";
        } else if (val == 6) {
            va = "6";
        } else if (val == 7) {
            va = "7";
        } else if (val == 8) {
            va = "8";
        } else if (val == 9) {
            va = "9";
        }
        return va;
    }

    /**

     */
    public static byte[] concat(byte[]A, byte[] B) {
        byte[] C = new byte[A.length + B.length];
//        System.arraycopy(A, 0, C, 0, A.length);
//        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }
    // ============================================================================


    /**

     */
    public static String getPrefence(Context context, String item) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(item, Const.TAG);
    }
    // ============================================================================


    /**

     */
    public static boolean getBooleanPrefence(Context context, String tag) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(tag, true);
    }
    // ============================================================================


    /**

     */
    // ============================================================================
    public static class InputFilterHex implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isDigit(source.charAt(i))
                        && source.charAt(i) != 'A' && source.charAt(i) != 'D'
                        && source.charAt(i) != 'B' && source.charAt(i) != 'E'
                        && source.charAt(i) != 'C' && source.charAt(i) != 'F'
                        ) {
                    return "";
                }
            }
            return null;
        }
    }
    // ============================================================================
}
