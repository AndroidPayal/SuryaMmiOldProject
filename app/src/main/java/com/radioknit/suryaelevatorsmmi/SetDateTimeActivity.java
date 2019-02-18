package com.radioknit.suryaelevatorsmmi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;
import static com.radioknit.suryaelevatorsmmi.MainActivity.sendMessage;

public class SetDateTimeActivity extends AppCompatActivity {

    private static final String TAG = "SetDataTimeActivity";
    private Context mContext;
    private TextView mTvDeviceName;
    private Button btnDone;
    private Button btnCancel;
    private TextView txtDate;
    private TextView txtTime;
    private Button btnSetDate;
    private Button btnsetTime;

    private DatePicker datePicker;
    private Calendar calendar;
    private int myear, mmonth, mday;
    private int mHour, mMinute;
    private TextView txtCommands;
    private BluetoothAdapter bluetoothAdapter;

    // =============

    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";

    private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");
    private Menu menu;
    final Handler myHandlerChk = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_date_time);

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }


    private void registerEvent() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateCommand =  "12F331031700";
                String timeCommand =  "12F2"+mHour+mMinute+"0000";

                String str1 = Integer.toHexString(18);
                String str2 = Integer.toHexString(243);
//                String str3 = Integer.toHexString(mday);
//                String str4 = Integer.toHexString(mmonth +1);
                String str3 = String.valueOf(mday);
                String str4 = String.valueOf(mmonth + 1);
                String yr = String.valueOf(myear).substring(2,4);
//                Log.e(TAG, "Yr = "+ yr);
//                String str5 = Integer.toHexString(Integer.parseInt(yr));
                String str5 = String.valueOf(yr);
                String str6 = Integer.toHexString(68);


                int sum = 0;
//                Log.e(TAG, "day1 = "+mday+" month1 = "+ (mmonth+1)+" year1 = "+Integer.parseInt(yr));
                sum = sum+ 18+243+mday+(mmonth+1)+Integer.parseInt(yr)+68;
                if(Integer.parseInt(yr) >= 10){
                    sum = sum +6;
                }

                if((mmonth+1) >= 10){
                    sum = sum +6;
                }
                if(mday >= 10){
                    sum = sum + 6;
                }
                String sumHex = String.format("%04x", sum+6);
                Log.e(TAG, " Sum =  "+ sum);
                Log.e(TAG, " sumHex =  "+ sumHex);
                String msb = sumHex.substring(1,2);
//                Log.e(TAG,"Msb = "+ msb);
                String lsb = sumHex.substring(2,4);
//                Log.e(TAG,"lsb = "+ lsb);
                int msb1 = (Integer.parseInt(msb) | 50);

                String finalString = str1+str2+str3+str4+str5+str6+lsb+msb1;

                int a1 = Integer.parseInt(str1,16);
                int a2 = Integer.parseInt(str2,16);
                int a3 = Integer.parseInt(str3,16);
                int a4 = Integer.parseInt(str4,16);
                int a5 = Integer.parseInt(str5,16);
                int a6 = Integer.parseInt(str6,16);
                int a7 = Integer.parseInt(lsb,16);
                int a8 = Integer.parseInt(String.valueOf(msb1),16);

//                Log.e(TAG, " a3 = "+ a3 +" a4 = "+ a4 +" a5 = "+a5);

                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

                String strChkSum= CustomCalculate.calculateChkSum(sendValChkSum);

                byte[] br1 = {(byte)0x12,(byte) 0xf3 , (byte) a5,(byte) a4,(byte)a3,(byte) a6,(byte) a7, (byte) a8};
//                Log.e(TAG, "br1 = "+ new String(br1));

                String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
//                Log.e(TAG, "asciiString = "+asciiString);
                /*int sumSendString  = 0;
                for(int i = 0; i<asciiString.length(); i++){
                    sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
                }
                Log.e(TAG, "sumSendString = "+ sumSendString);*/
                //asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";
                asciiString = asciiString + strChkSum + "\r";
                Log.e(TAG, "asciiString = "+ asciiString);

                if (isConnected()) {
//                  connector.write(br1);
                    //connector.write(asciiString.getBytes());
                    sendMessage(asciiString.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
//
//                Utils.calculateHexaValue(br1.toString());

                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // For time command
                int sum2 = 0;
                String str21 = Integer.toHexString(242);
//                String str31 = Integer.toHexString(mHour);
//                String str41 = Integer.toHexString(mMinute);
                String str31 = String.valueOf(mHour);
                String str41 = String.valueOf(mMinute);
                sum2 = sum2+ 18+243+mHour+mMinute+68+68;
                if(mHour >= 10){
                    sum2 = sum2 + 6;
                }

                if(mMinute >= 10){
                    sum2 = sum2 + 6;
                }

                String sumHex2 = String.format("%04x", sum2 + 6);
//                Log.e(TAG, " Sum time =  "+ sum2);
//                Log.e(TAG, " sumHex time =  "+ sumHex2);
                String msb2 = sumHex2.substring(1,2);
//                Log.e(TAG,"Msb time= "+ msb2);
                String lsb2 = sumHex2.substring(2,4);
//                Log.e(TAG,"lsb time = "+ lsb2);
                int msb3 = (Integer.parseInt(msb2) | 50);
//                Log.e(TAG, "OR Result  time =" +(Integer.parseInt(msb2) | 50));


                int b1 = Integer.parseInt(str1,16);
                int b2 = Integer.parseInt(str21,16);
                int b3 = Integer.parseInt(str31,16);
                int b4 = Integer.parseInt(str41,16);
                int b5 = Integer.parseInt(str6,16);
                int b6 = Integer.parseInt(str6,16);
                int b7 = Integer.parseInt(lsb2,16);
                int b8 = Integer.parseInt(String.valueOf(msb3),16);


                int[] sendValChkSum1={b1, b2, b3, b4, b5, b6};

                String strChkSum1= CustomCalculate.calculateChkSum(sendValChkSum1);


//                Log.e(TAG, "Input String 2  = "+ b3+" " + b4 +" "+b5+" "+ b6+" "+b7+" "+b8 );


//                Log.e(TAG, "Char = "+ (char) a1 );

                byte[] br2 = {(byte)0x12,(byte) 0xf2 , (byte) b3,(byte) b4,(byte)b5,(byte) b6,(byte) b7, (byte) b8};
//                Log.e(TAG, "br2 = "+ new String(br2));

                String asciiString1  = String.format("%04x", b1).substring(2,4)+String.format("%04x", b2).substring(2,4)+String.format("%04x", b3).substring(2,4)+String.format("%04x", b4).substring(2,4)+String.format("%04x", b5).substring(2,4)+String.format("%04x", b6).substring(2,4) ;
                Log.e(TAG, "asciiString = "+asciiString);
/*                int sumSendString1  = 0;
                for(int i = 0; i<asciiString1.length(); i++){
                    sumSendString1 = sumSendString1 + Integer.parseInt(String.format("%04x", (int) asciiString1.charAt(i)).substring(2,4));
                }
                Log.e(TAG, "sumSendString = "+ sumSendString);
                asciiString1 = asciiString1 +String.valueOf(sumSendString1).substring(1,3)+ "\r";*/
                asciiString1 = asciiString1 + strChkSum1 + "\r";
//                Log.e(TAG, "asciiString = "+ asciiString1);



                if (isConnected()) {
//                    connector.write(br1);
                    //connector.write(asciiString1.getBytes());
                    sendMessage(asciiString1.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

//                txtCommands.setText(br1 +" \n \n"+ br2);

            }
        });

        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(myear, mmonth+1, mday);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                myear = year;
                                mmonth = monthOfYear;
                                mday = dayOfMonth;

                            }
                        }, myear, mmonth,mday );
                datePickerDialog.show();
            }
        });


        btnsetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay + "-" +  minute);
                        mHour = hourOfDay;
                        mMinute = minute;
                    }
                },  mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

    }

    private void createObj() {

        getSupportActionBar().setTitle("Date And Time");

        mContext = SetDateTimeActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        calendar = Calendar.getInstance();
        myear = calendar.get(Calendar.YEAR);

        mmonth = calendar.get(Calendar.MONTH);
        mday = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(myear, mmonth+1, mday);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);


    }

    private void generateId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        btnDone = (Button)findViewById(R.id.btnSetDateTime_Done);
        btnCancel = (Button)findViewById(R.id.btnSetDateTime_Cancel);
        btnSetDate = (Button) findViewById(R.id.btnSetDateTimeFrag_setDate);
        btnsetTime = (Button)findViewById(R.id.btnSetDateTimeFrag_setTime);
        txtDate = (TextView)findViewById(R.id.txtSetDateTimeFrag_Date);
        txtTime = (TextView)findViewById(R.id.txtSetDateTimeFrag_Time);
        txtCommands = (TextView) findViewById(R.id.tvCommands);
    }

    private void showDate(int year, int month, int day) {
        txtDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            if (isConnected()) {
                try{
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                }
                catch (Exception e){
                    //Catch
                }
            }
            else {
                try{
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
                }
                catch (Exception e){
                    //Catch
                }
            }
            myHandlerChk.postDelayed(this, 100);
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write_mode, menu);
        this.menu = menu;
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.wroteModeEnable :
                Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(mContext, MainActivity.class));
        myHandlerChk.removeCallbacks(checkDataContinue);
        finish();
    }

    // ============================================================================


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.device_control_activity, menu);
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_search:
                if (super.isAdapterReady()) {
                    if (isConnected()) stopConnection();
                    else startDeviceListActivity();
                } else {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                return true;

            case R.id.wroteModeEnable :
                Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                startActivityForResult(intent,WRITE_MODE_ENABLE );
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ============================================================================
*/




}
