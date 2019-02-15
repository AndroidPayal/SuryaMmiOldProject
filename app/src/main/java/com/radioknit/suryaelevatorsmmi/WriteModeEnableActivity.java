package com.radioknit.suryaelevatorsmmi;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

import static com.radioknit.suryaelevatorsmmi.MainActivity.P5MEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.P5MEMFlag;
import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;
import static com.radioknit.suryaelevatorsmmi.MainActivity.strCmpDate;
import static com.radioknit.suryaelevatorsmmi.MainActivity.writeModeFlag;


public class WriteModeEnableActivity extends AppCompatActivity {

    private static final String TAG = "WriteModeEnabled";
    private Context mContext;
    ArrayList<String> arrCommandValueList;
    private Button btnWriteModeEnable;
    Button btnDateLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_write_mode_enable);
        generateId();
        createObj();
        registerEvent();


    }


    private void registerEvent() {
        btnWriteModeEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCmpDate = CustomCalculate.currentDate();
                //Log.e(TAG, "strCmpDate: " + strCmpDate);
                writeModeFlag = true;
            }

        });

        btnDateLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_date_lock, null);
                final EditText editTextYear = alertLayout.findViewById(R.id.editYear);
                final EditText editTextMonth = alertLayout.findViewById(R.id.editMonth);
                final EditText editTextDate = alertLayout.findViewById(R.id.editDate);
                final EditText editTextHour = alertLayout.findViewById(R.id.editHour);


                AlertDialog.Builder alert = new AlertDialog.Builder(WriteModeEnableActivity.this);
                alert.setTitle("Date Lock");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                //alert.setCancelable(false);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String strYear = editTextYear.getText().toString();
                        String strMonth = editTextMonth.getText().toString().trim();
                        String strDate = editTextDate.getText().toString().trim();
                        String strHour = editTextHour.getText().toString().trim();
                        if(strYear.equals("") || strMonth.equals("") || strDate.equals("") || strHour.equals("")){
                            Toast.makeText(getApplicationContext(),"No field should be empty.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(strYear.length()<4){
                            Toast.makeText(getApplicationContext(),"Year should be 4 digit.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(isConnected()) {
                            CustomCalculate.chkWriteModeState();
                            if(writeModeFlag){
                                 P5MEM[0] = (char) Integer.parseInt(strYear.substring(0,2));
                                 P5MEM[1] = (char) Integer.parseInt(strYear.substring(2,4));
                                 P5MEM[2] = (char) Integer.parseInt(strMonth);
                                 P5MEM[3] = (char) Integer.parseInt(strDate);
                                 P5MEM[4] = (char) Integer.parseInt(strHour);
                                 P5MEMFlag = true;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Can't set...Enable Write Mode first. ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

    }


    private void createObj() {
        getSupportActionBar().setTitle("Write Mode");
        mContext = WriteModeEnableActivity.this;
        arrCommandValueList = new ArrayList<String>();

    }

    private void generateId() {
        btnWriteModeEnable = (Button)findViewById(R.id.btnEnableWriteMode);
        btnDateLock = findViewById(R.id.btnDateLock);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


}
