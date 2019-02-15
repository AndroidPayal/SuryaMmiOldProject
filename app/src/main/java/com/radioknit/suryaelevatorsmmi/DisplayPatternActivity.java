package com.radioknit.suryaelevatorsmmi;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static com.radioknit.suryaelevatorsmmi.MainActivity.CONFREAD;
import static com.radioknit.suryaelevatorsmmi.MainActivity.CONFREADFLAG;
import static com.radioknit.suryaelevatorsmmi.MainActivity.D0MEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.D1MEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.P1MEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.P1MEMFlag;
import static com.radioknit.suryaelevatorsmmi.MainActivity.P2MEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.P2MEMFlag;
import static com.radioknit.suryaelevatorsmmi.MainActivity.POMEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.POMEMFlag;
import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;
import static com.radioknit.suryaelevatorsmmi.MainActivity.sendMessage;
import static com.radioknit.suryaelevatorsmmi.MainActivity.writeModeFlag;


public class DisplayPatternActivity extends AppCompatActivity {

    private static final String TAG = "DisplayPatternActivity";
    private Button btnViewDisplayPattern;
    private RelativeLayout rlViewDisplayPatternData;
    private RelativeLayout rlViewDisplayPatternCustom;
    private Button btnSetValues;
    private Button btnGetValues;
    private ArrayList<String> arrPattern;
    private Context mContext;
    final Handler myHandlerChk = new Handler();
    private Menu menu;
    private Spinner spinZero;
    private Spinner spinOne;
    private Spinner spinTwo;
    private Spinner spinThree;
    private Spinner spinFour;
    private Spinner spinFive;
    private Spinner spinSix;
    private Spinner spinSeven;
    private Spinner spinMNTMode;
    private Spinner spinARDMode;
    private Button btnSetAllDisplayPattern;
    private TextView txtZero;
    private TextView txtOne;
    private TextView txtTwo;
    private TextView txtThree;
    private TextView txtFour;
    private TextView txtFive;
    private TextView txtSix;
    private TextView txtSeven;
    private TextView txtMNTMode;
    private TextView txtARDMode;

    private Button btnViewDisplayPatternCustom;
    private Spinner spinZeroCustom;
    private Spinner spinOneCustom;
    private Spinner spinTwoCustom;
    private Spinner spinThreeCustom;
    private Spinner spinFourCustom;
    private Spinner spinFiveCustom;
    private Spinner spinSixCustom;
    private Spinner spinSevenCustom;
    private Spinner spinMNTModeCustom;
    private Spinner spinARDModeCustom;
    private Button btnSetDisplayPatternCustom;
    private TextView txtZeroCustom;
    private TextView txtOneCustom;
    private TextView txtTwoCustom;
    private TextView txtThreeCustom;
    private TextView txtFourCustom;
    private TextView txtFiveCustom;
    private TextView txtSixCustom;
    private TextView txtSevenCustom;
    private TextView txtMNTModeCustom;
    private TextView txtARDModeCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_pattern);
        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void generateId() {
        btnViewDisplayPattern = (Button)findViewById(R.id.btnViewDisplayPattern);
        //llViewDisplayPattern = (LinearLayout)findViewById(R.id.llDisplayPattern);
        rlViewDisplayPatternCustom = findViewById(R.id.rlViewDisplayPatternCustom);
        rlViewDisplayPatternData = (RelativeLayout)findViewById(R.id.rlViewDisplayPatternValue);
        btnSetValues = (Button) findViewById(R.id.btnDisplayPatternSetValues);
        btnGetValues = (Button)findViewById(R.id.btnDisplayPatternViewValues);
        spinZero = findViewById(R.id.spinZero);
        spinOne = findViewById(R.id.spinOne);
        spinTwo = findViewById(R.id.spinTwo);
        spinThree = findViewById(R.id.spinThree);
        spinFour = findViewById(R.id.spinFour);
        spinFive = findViewById(R.id.spinFive);
        spinSix = findViewById(R.id.spinSix);
        spinSeven = findViewById(R.id.spinSeven);
        spinMNTMode = findViewById(R.id.spinMNTMode);
        spinARDMode = findViewById(R.id.spinARDMode);
        btnSetAllDisplayPattern = findViewById(R.id.btnSetAllDisplayPattern);
        txtZero = findViewById(R.id.tvZero);
        txtOne = findViewById(R.id.tvOne);
        txtTwo = findViewById(R.id.tvTwo);
        txtThree = findViewById(R.id.tvThree);
        txtFour = findViewById(R.id.tvFour);
        txtFive = findViewById(R.id.tvFive);
        txtSix = findViewById(R.id.tvSix);
        txtSeven = findViewById(R.id.tvSeven);
        txtMNTMode = findViewById(R.id.tvMNTMode);
        txtARDMode = findViewById(R.id.tvARDMode);


        btnViewDisplayPatternCustom = findViewById(R.id.btnViewDisplayPatternCustom);
        spinZeroCustom = findViewById(R.id.spinZeroCustom);
        spinOneCustom = findViewById(R.id.spinOneCustom);
        spinTwoCustom = findViewById(R.id.spinTwoCustom);
        spinThreeCustom = findViewById(R.id.spinThreeCustom);
        spinFourCustom = findViewById(R.id.spinFourCustom);
        spinFiveCustom = findViewById(R.id.spinFiveCustom);
        spinSixCustom = findViewById(R.id.spinSixCustom);
        spinSevenCustom = findViewById(R.id.spinSevenCustom);
        spinMNTModeCustom = findViewById(R.id.spinMNTModeCustom);
        spinARDModeCustom = findViewById(R.id.spinARDModeCustom);
        btnSetDisplayPatternCustom = findViewById(R.id.btnSetAllDisplayPatternCustom);
        txtZeroCustom = findViewById(R.id.tvZeroCustom);
        txtOneCustom = findViewById(R.id.tvOneCustom);
        txtTwoCustom = findViewById(R.id.tvTwoCustom);
        txtThreeCustom = findViewById(R.id.tvThreeCustom);
        txtFourCustom = findViewById(R.id.tvFourCustom);
        txtFiveCustom = findViewById(R.id.tvFiveCustom);
        txtSixCustom = findViewById(R.id.tvSixCustom);
        txtSevenCustom = findViewById(R.id.tvSevenCustom);
        txtMNTModeCustom = findViewById(R.id.tvMNTModeCustom);
        txtARDModeCustom = findViewById(R.id.tvARDModeCustom);

        btnSetValues.setTextColor(getResources().getColor(R.color.colorAccent));

    }

    private void createObj() {
        getSupportActionBar().setTitle("Display Pattern");
        mContext = DisplayPatternActivity.this;
        String[] tempPattern = getResources().getStringArray(R.array.arr_display_pattern);
        arrPattern = new ArrayList<>(Arrays.asList(tempPattern));
    }

    private void registerEvent() {
        btnSetAllDisplayPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()) {
                    CustomCalculate.chkWriteModeState();
                    if(writeModeFlag){
                    setDisplayPattern();
                    P1MEMFlag=true;
                    P2MEMFlag=true;
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



        btnSetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSetValues.setTextColor(getResources().getColor(R.color.colorAccent));
                btnGetValues.setTextColor(getResources().getColor(R.color.white));
                rlViewDisplayPatternCustom.setVisibility(View.VISIBLE);
                rlViewDisplayPatternData.setVisibility(View.GONE);
            }
        });

        btnGetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSetValues.setTextColor(getResources().getColor(R.color.white));
                btnGetValues.setTextColor(getResources().getColor(R.color.colorAccent));
                rlViewDisplayPatternCustom.setVisibility(View.GONE);
                rlViewDisplayPatternData.setVisibility(View.VISIBLE);
            }
        });


        btnViewDisplayPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llViewDisplayPattern.removeAllViews();

                if(isConnected()) {
                    CONFREAD = 0xFF;
                    CONFREADFLAG = true;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
            }

        });


        btnSetDisplayPatternCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    CustomCalculate.chkWriteModeState();
                    if(writeModeFlag){
                    setDisplayPatternCustom();
                    P1MEMFlag=true;
                    P2MEMFlag=true;
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

        btnViewDisplayPatternCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    //pd = ProgressDialog.show(mContext, "", "Please wait", true);
                    CONFREAD = 0xFF;
                    CONFREADFLAG = true;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    void setDisplayPattern() {
        //P1MEM
        String strZero = spinZero.getSelectedItem().toString();
        String strOne = spinOne.getSelectedItem().toString();
        String strTwo = spinTwo.getSelectedItem().toString();
        String strThree = spinThree.getSelectedItem().toString();
        String strFour = spinFour.getSelectedItem().toString();

        //P2MEM
        String strFive = spinFive.getSelectedItem().toString();
        String strSix = spinSix.getSelectedItem().toString();
        String strSeven = spinSeven.getSelectedItem().toString();
        String strARDMode = spinARDMode.getSelectedItem().toString();
        String strMNTMode = spinMNTMode.getSelectedItem().toString();

        switch (strZero)
        {
            case "NC":
                P1MEM[0] =  0;
                break;
            case "0":
                P1MEM[0] =  0xC0;
                break;
            case "1":
                P1MEM[0] =  0xF9;
                break;
            case "2":
                P1MEM[0] =  0xA4;
                break;
            case "3":
                P1MEM[0] =  0xB0;
                break;
            case "4":
                P1MEM[0] =  0x99;
                break;
            case "5":
                P1MEM[0] =  0x92;
                break;
            case "6":
                P1MEM[0] =  0x82;
                break;
            case "7":
                P1MEM[0] =  0xF8;
                break;
            case "8":
                P1MEM[0] =  0x80;
                break;
            case "9":
                P1MEM[0] =  0x98;
                break;
            case "A":
                P1MEM[0] =  0x88;
                break;
            case "b":
                P1MEM[0] =  0x83;
                break;
            case "C":
                P1MEM[0] =  0xC6;
                break;
            case "d":
                P1MEM[0] =  0xA1;
                break;
            case "E":
                P1MEM[0] =  0x86;
                break;
            case "F":
                P1MEM[0] =  0x8E;
                break;
            case "G":
                P1MEM[0] =  0xC2;
                break;
            case "M":
                P1MEM[0] =  0xAA;
                break;
            case "L":
                P1MEM[0] =  0xC7;
                break;
            case "-1":
                P1MEM[0] =  0xB9;
                break;
            default:
                P1MEM[0] =  0;
                break;
        }

        switch (strOne)
        {
            case "NC":
                P1MEM[1] =  0;
                break;
            case "0":
                P1MEM[1] =  0xC0;
                break;
            case "1":
                P1MEM[1] =  0xF9;
                break;
            case "2":
                P1MEM[1] =  0xA4;
                break;
            case "3":
                P1MEM[1] =  0xB0;
                break;
            case "4":
                P1MEM[1] =  0x99;
                break;
            case "5":
                P1MEM[1] =  0x92;
                break;
            case "6":
                P1MEM[1] =  0x82;
                break;
            case "7":
                P1MEM[1] =  0xF8;
                break;
            case "8":
                P1MEM[1] =  0x80;
                break;
            case "9":
                P1MEM[1] =  0x98;
                break;
            case "A":
                P1MEM[1] =  0x88;
                break;
            case "b":
                P1MEM[1] =  0x83;
                break;
            case "C":
                P1MEM[1] =  0xC6;
                break;
            case "d":
                P1MEM[1] =  0xA1;
                break;
            case "E":
                P1MEM[1] =  0x86;
                break;
            case "F":
                P1MEM[1] =  0x8E;
                break;
            case "G":
                P1MEM[1] =  0xC2;
                break;
            case "M":
                P1MEM[1] =  0xAA;
                break;
            case "L":
                P1MEM[1] =  0xC7;
                break;
            case "-1":
                P1MEM[1] =  0xB9;
                break;
            default:
                P1MEM[1] =  0;
                break;

        }

        switch (strTwo)
        {
            case "NC":
                P1MEM[2] =0;
                break;
            case "0":
                P1MEM[2] =0xC0;
                break;
            case "1":
                P1MEM[2] =0xF9;
                break;
            case "2":
                P1MEM[2] =0xA4;
                break;
            case "3":
                P1MEM[2] =0xB0;
                break;
            case "4":
                P1MEM[2] =0x99;
                break;
            case "5":
                P1MEM[2] =0x92;
                break;
            case "6":
                P1MEM[2] =0x82;
                break;
            case "7":
                P1MEM[2] =0xF8;
                break;
            case "8":
                P1MEM[2] =0x80;
                break;
            case "9":
                P1MEM[2] =0x98;
                break;
            case "A":
                P1MEM[2] =0x88;
                break;
            case "b":
                P1MEM[2] =0x83;
                break;
            case "C":
                P1MEM[2] =0xC6;
                break;
            case "d":
                P1MEM[2] =0xA1;
                break;
            case "E":
                P1MEM[2] =0x86;
                break;
            case "F":
                P1MEM[2] =0x8E;
                break;
            case "G":
                P1MEM[2] =0xC2;
                break;
            case "M":
                P1MEM[2] =0xAA;
                break;
            case "L":
                P1MEM[2] =0xC7;
                break;
            case "-1":
                P1MEM[2] =0xB9;
                break;
            default:
                P1MEM[2] =0;
                break;

        }

        switch (strThree)
        {
            case "NC":
                P1MEM[3] =0;
                break;
            case "0":
                P1MEM[3] =0xC0;
                break;
            case "1":
                P1MEM[3] =0xF9;
                break;
            case "2":
                P1MEM[3] =0xA4;
                break;
            case "3":
                P1MEM[3] =0xB0;
                break;
            case "4":
                P1MEM[3] =0x99;
                break;
            case "5":
                P1MEM[3] =0x92;
                break;
            case "6":
                P1MEM[3] =0x82;
                break;
            case "7":
                P1MEM[3] =0xF8;
                break;
            case "8":
                P1MEM[3] =0x80;
                break;
            case "9":
                P1MEM[3] =0x98;
                break;
            case "A":
                P1MEM[3] =0x88;
                break;
            case "b":
                P1MEM[3] =0x83;
                break;
            case "C":
                P1MEM[3] =0xC6;
                break;
            case "d":
                P1MEM[3] =0xA1;
                break;
            case "E":
                P1MEM[3] =0x86;
                break;
            case "F":
                P1MEM[3] =0x8E;
                break;
            case "G":
                P1MEM[3] =0xC2;
                break;
            case "M":
                P1MEM[3] =0xAA;
                break;
            case "L":
                P1MEM[3] =0xC7;
                break;
            case "-1":
                P1MEM[3] =0xB9;
                break;
            default:
                P1MEM[3] =0;
                break;

        }

        switch (strFour)
        {
            case "NC":
                P1MEM[4] =0;
                break;
            case "0":
                P1MEM[4] =0xC0;
                break;
            case "1":
                P1MEM[4] =0xF9;
                break;
            case "2":
                P1MEM[4] =0xA4;
                break;
            case "3":
                P1MEM[4] =0xB0;
                break;
            case "4":
                P1MEM[4] =0x99;
                break;
            case "5":
                P1MEM[4] =0x92;
                break;
            case "6":
                P1MEM[4] =0x82;
                break;
            case "7":
                P1MEM[4] =0xF8;
                break;
            case "8":
                P1MEM[4] =0x80;
                break;
            case "9":
                P1MEM[4] =0x98;
                break;
            case "A":
                P1MEM[4] =0x88;
                break;
            case "b":
                P1MEM[4] =0x83;
                break;
            case "C":
                P1MEM[4] =0xC6;
                break;
            case "d":
                P1MEM[4] =0xA1;
                break;
            case "E":
                P1MEM[4] =0x86;
                break;
            case "F":
                P1MEM[4] =0x8E;
                break;
            case "G":
                P1MEM[4] =0xC2;
                break;
            case "M":
                P1MEM[4] =0xAA;
                break;
            case "L":
                P1MEM[4] =0xC7;
                break;
            case "-1":
                P1MEM[4] =0xB9;
                break;
            default:
                P1MEM[4] =0;
                break;
        }

        switch (strFive)
        {
            case "NC":
                P2MEM[0] = 0;
                break;
            case "0":
                P2MEM[0] = 0xC0;
                break;
            case "1":
                P2MEM[0] = 0xF9;
                break;
            case "2":
                P2MEM[0] = 0xA4;
                break;
            case "3":
                P2MEM[0] = 0xB0;
                break;
            case "4":
                P2MEM[0] = 0x99;
                break;
            case "5":
                P2MEM[0] = 0x92;
                break;
            case "6":
                P2MEM[0] = 0x82;
                break;
            case "7":
                P2MEM[0] = 0xF8;
                break;
            case "8":
                P2MEM[0] = 0x80;
                break;
            case "9":
                P2MEM[0] = 0x98;
                break;
            case "A":
                P2MEM[0] = 0x88;
                break;
            case "b":
                P2MEM[0] = 0x83;
                break;
            case "C":
                P2MEM[0] = 0xC6;
                break;
            case "d":
                P2MEM[0] = 0xA1;
                break;
            case "E":
                P2MEM[0] = 0x86;
                break;
            case "F":
                P2MEM[0] = 0x8E;
                break;
            case "G":
                P2MEM[0] = 0xC2;
                break;
            case "M":
                P2MEM[0] = 0xAA;
                break;
            case "L":
                P2MEM[0] = 0xC7;
                break;
            case "-1":
                P2MEM[0] = 0xB9;
                break;
            default:
                P2MEM[0] = 0;
                break;
        }

        switch (strSix)
        {
            case "NC":
                P2MEM[1] = 0;
                break;
            case "0":
                P2MEM[1] = 0xC0;
                break;
            case "1":
                P2MEM[1] = 0xF9;
                break;
            case "2":
                P2MEM[1] = 0xA4;
                break;
            case "3":
                P2MEM[1] = 0xB0;
                break;
            case "4":
                P2MEM[1] = 0x99;
                break;
            case "5":
                P2MEM[1] = 0x92;
                break;
            case "6":
                P2MEM[1] = 0x82;
                break;
            case "7":
                P2MEM[1] = 0xF8;
                break;
            case "8":
                P2MEM[1] = 0x80;
                break;
            case "9":
                P2MEM[1] = 0x98;
                break;
            case "A":
                P2MEM[1] = 0x88;
                break;
            case "b":
                P2MEM[1] = 0x83;
                break;
            case "C":
                P2MEM[1] = 0xC6;
                break;
            case "d":
                P2MEM[1] = 0xA1;
                break;
            case "E":
                P2MEM[1] = 0x86;
                break;
            case "F":
                P2MEM[1] = 0x8E;
                break;
            case "G":
                P2MEM[1] = 0xC2;
                break;
            case "M":
                P2MEM[1] = 0xAA;
                break;
            case "L":
                P2MEM[1] = 0xC7;
                break;
            case "-1":
                P2MEM[1] = 0xB9;
                break;
            default:
                P2MEM[1] = 0;
                break;

        }

        switch (strSeven)
        {
            case "NC":
                P2MEM[2] = 0;
                break;
            case "0":
                P2MEM[2] = 0xC0;
                break;
            case "1":
                P2MEM[2] = 0xF9;
                break;
            case "2":
                P2MEM[2] = 0xA4;
                break;
            case "3":
                P2MEM[2] = 0xB0;
                break;
            case "4":
                P2MEM[2] = 0x99;
                break;
            case "5":
                P2MEM[2] = 0x92;
                break;
            case "6":
                P2MEM[2] = 0x82;
                break;
            case "7":
                P2MEM[2] = 0xF8;
                break;
            case "8":
                P2MEM[2] = 0x80;
                break;
            case "9":
                P2MEM[2] = 0x98;
                break;
            case "A":
                P2MEM[2] = 0x88;
                break;
            case "b":
                P2MEM[2] = 0x83;
                break;
            case "C":
                P2MEM[2] = 0xC6;
                break;
            case "d":
                P2MEM[2] = 0xA1;
                break;
            case "E":
                P2MEM[2] = 0x86;
                break;
            case "F":
                P2MEM[2] = 0x8E;
                break;
            case "G":
                P2MEM[2] = 0xC2;
                break;
            case "M":
                P2MEM[2] = 0xAA;
                break;
            case "L":
                P2MEM[2] = 0xC7;
                break;
            case "-1":
                P2MEM[2] = 0xB9;
                break;
            default:
                P2MEM[2] = 0;
                break;

        }

        switch (strMNTMode)
        {
            case "NC":
                P2MEM[3] = 0;
                break;
            case "0":
                P2MEM[3] = 0xC0;
                break;
            case "1":
                P2MEM[3] = 0xF9;
                break;
            case "2":
                P2MEM[3] = 0xA4;
                break;
            case "3":
                P2MEM[3] = 0xB0;
                break;
            case "4":
                P2MEM[3] = 0x99;
                break;
            case "5":
                P2MEM[3] = 0x92;
                break;
            case "6":
                P2MEM[3] = 0x82;
                break;
            case "7":
                P2MEM[3] = 0xF8;
                break;
            case "8":
                P2MEM[3] = 0x80;
                break;
            case "9":
                P2MEM[3] = 0x98;
                break;
            case "A":
                P2MEM[3] = 0x88;
                break;
            case "b":
                P2MEM[3] = 0x83;
                break;
            case "C":
                P2MEM[3] = 0xC6;
                break;
            case "d":
                P2MEM[3] = 0xA1;
                break;
            case "E":
                P2MEM[3] = 0x86;
                break;
            case "F":
                P2MEM[3] = 0x8E;
                break;
            case "G":
                P2MEM[3] = 0xC2;
                break;
            case "M":
                P2MEM[3] = 0xAA;
                break;
            case "L":
                P2MEM[3] = 0xC7;
                break;
            case "-1":
                P2MEM[3] = 0xB9;
                break;
            default:
                P2MEM[3] = 0;
                break;

        }

        switch (strARDMode)
        {
            case "NC":
                P2MEM[4] = 0;
                break;
            case "0":
                P2MEM[4] = 0xC0;
                break;
            case "1":
                P2MEM[4] = 0xF9;
                break;
            case "2":
                P2MEM[4] = 0xA4;
                break;
            case "3":
                P2MEM[4] = 0xB0;
                break;
            case "4":
                P2MEM[4] = 0x99;
                break;
            case "5":
                P2MEM[4] = 0x92;
                break;
            case "6":
                P2MEM[4] = 0x82;
                break;
            case "7":
                P2MEM[4] = 0xF8;
                break;
            case "8":
                P2MEM[4] = 0x80;
                break;
            case "9":
                P2MEM[4] = 0x98;
                break;
            case "A":
                P2MEM[4] = 0x88;
                break;
            case "b":
                P2MEM[4] = 0x83;
                break;
            case "C":
                P2MEM[4] = 0xC6;
                break;
            case "d":
                P2MEM[4] = 0xA1;
                break;
            case "E":
                P2MEM[4] = 0x86;
                break;
            case "F":
                P2MEM[4] = 0x8E;
                break;
            case "G":
                P2MEM[4] = 0xC2;
                break;
            case "M":
                P2MEM[4] = 0xAA;
                break;
            case "L":
                P2MEM[4] = 0xC7;
                break;
            case "-1":
                P2MEM[4] = 0xB9;
                break;
            default:
                P2MEM[4] = 0;
                break;
        }

    }




    void setDisplayPatternCustom() {
        //P1MEM
        String strZero = spinZeroCustom.getSelectedItem().toString();
        String strOne = spinOneCustom.getSelectedItem().toString();
        String strTwo = spinTwoCustom.getSelectedItem().toString();
        String strThree = spinThreeCustom.getSelectedItem().toString();
        String strFour = spinFourCustom.getSelectedItem().toString();

        //P2MEM
        String strFive = spinFiveCustom.getSelectedItem().toString();
        String strSix = spinSixCustom.getSelectedItem().toString();
        String strSeven = spinSevenCustom.getSelectedItem().toString();
        String strARDMode = spinARDModeCustom.getSelectedItem().toString();
        String strMNTMode = spinMNTModeCustom.getSelectedItem().toString();

        switch (strZero)
        {
            case "NC":
                P1MEM[0] =  0;
                break;
            case "1":
                P1MEM[0] =  0xF4;
                break;
            case "2":
                P1MEM[0] =  0xF5;
                break;
            case "3":
                P1MEM[0] =  0xF6;
                break;
            case "4":
                P1MEM[0] =  0xF7;
                break;
            case "5":
                P1MEM[0] =  0xF8;
                break;
            case "6":
                P1MEM[0] =  0xF9;
                break;
            case "7":
                P1MEM[0] =  0xFA;
                break;
            case "b":
                P1MEM[0] =  0xF3;
                break;
            case "G":
                P1MEM[0] =  0xF2;
                break;
            default:
                P1MEM[0] =  0;
                break;
        }

        switch (strOne)
        {
            case "NC":
                P1MEM[1] =  0;
                break;
            case "1":
                P1MEM[1] =  0xF4;
                break;
            case "2":
                P1MEM[1] =  0xF5;
                break;
            case "3":
                P1MEM[1] =  0xF6;
                break;
            case "4":
                P1MEM[1] =  0xF7;
                break;
            case "5":
                P1MEM[1] =  0xF8;
                break;
            case "6":
                P1MEM[1] =  0xF9;
                break;
            case "7":
                P1MEM[1] =  0xFA;
                break;
            case "b":
                P1MEM[1] =  0xF3;
                break;
            case "G":
                P1MEM[1] =  0xF2;
                break;
            default:
                P1MEM[1] =  0;
                break;

        }

        switch (strTwo)
        {
            case "NC":
                P1MEM[2] =  0;
                break;
            case "1":
                P1MEM[2] =  0xF4;
                break;
            case "2":
                P1MEM[2] =  0xF5;
                break;
            case "3":
                P1MEM[2] =  0xF6;
                break;
            case "4":
                P1MEM[2] =  0xF7;
                break;
            case "5":
                P1MEM[2] =  0xF8;
                break;
            case "6":
                P1MEM[2] =  0xF9;
                break;
            case "7":
                P1MEM[2] =  0xFA;
                break;
            case "b":
                P1MEM[2] =  0xF3;
                break;
            case "G":
                P1MEM[2] =  0xF2;
                break;
            default:
                P1MEM[2] =  0;
                break;

        }

        switch (strThree)
        {
            case "NC":
                P1MEM[3] =  0;
                break;
            case "1":
                P1MEM[3] =  0xF4;
                break;
            case "2":
                P1MEM[3] =  0xF5;
                break;
            case "3":
                P1MEM[3] =  0xF6;
                break;
            case "4":
                P1MEM[3] =  0xF7;
                break;
            case "5":
                P1MEM[3] =  0xF8;
                break;
            case "6":
                P1MEM[3] =  0xF9;
                break;
            case "7":
                P1MEM[3] =  0xFA;
                break;
            case "b":
                P1MEM[3] =  0xF3;
                break;
            case "G":
                P1MEM[3] =  0xF2;
                break;
            default:
                P1MEM[3] =  0;
                break;

        }

        switch (strFour)
        {
            case "NC":
                P1MEM[4] =  0;
                break;
            case "1":
                P1MEM[4] =  0xF4;
                break;
            case "2":
                P1MEM[4] =  0xF5;
                break;
            case "3":
                P1MEM[4] =  0xF6;
                break;
            case "4":
                P1MEM[4] =  0xF7;
                break;
            case "5":
                P1MEM[4] =  0xF8;
                break;
            case "6":
                P1MEM[4] =  0xF9;
                break;
            case "7":
                P1MEM[4] =  0xFA;
                break;
            case "b":
                P1MEM[4] =  0xF3;
                break;
            case "G":
                P1MEM[4] =  0xF2;
                break;
            default:
                P1MEM[4] =  0;
                break;
        }

        switch (strFive)
        {
            case "NC":
                P2MEM[0] =  0;
                break;
            case "1":
                P2MEM[0] =  0xF4;
                break;
            case "2":
                P2MEM[0] =  0xF5;
                break;
            case "3":
                P2MEM[0] =  0xF6;
                break;
            case "4":
                P2MEM[0] =  0xF7;
                break;
            case "5":
                P2MEM[0] =  0xF8;
                break;
            case "6":
                P2MEM[0] =  0xF9;
                break;
            case "7":
                P2MEM[0] =  0xFA;
                break;
            case "b":
                P2MEM[0] =  0xF3;
                break;
            case "G":
                P2MEM[0] =  0xF2;
                break;
            default:
                P2MEM[0] =  0;
                break;
        }

        switch (strSix)
        {
            case "NC":
                P2MEM[1] =  0;
                break;
            case "1":
                P2MEM[1] =  0xF4;
                break;
            case "2":
                P2MEM[1] =  0xF5;
                break;
            case "3":
                P2MEM[1] =  0xF6;
                break;
            case "4":
                P2MEM[1] =  0xF7;
                break;
            case "5":
                P2MEM[1] =  0xF8;
                break;
            case "6":
                P2MEM[1] =  0xF9;
                break;
            case "7":
                P2MEM[1] =  0xFA;
                break;
            case "b":
                P2MEM[1] =  0xF3;
                break;
            case "G":
                P2MEM[1] =  0xF2;
                break;
            default:
                P2MEM[1] =  0;
                break;

        }

        switch (strSeven)
        {
            case "NC":
                P2MEM[2] =  0;
                break;
            case "1":
                P2MEM[2] =  0xF4;
                break;
            case "2":
                P2MEM[2] =  0xF5;
                break;
            case "3":
                P2MEM[2] =  0xF6;
                break;
            case "4":
                P2MEM[2] =  0xF7;
                break;
            case "5":
                P2MEM[2] =  0xF8;
                break;
            case "6":
                P2MEM[2] =  0xF9;
                break;
            case "7":
                P2MEM[2] =  0xFA;
                break;
            case "b":
                P2MEM[2] =  0xF3;
                break;
            case "G":
                P2MEM[2] =  0xF2;
                break;
            default:
                P2MEM[2] =  0;
                break;

        }

        switch (strMNTMode)
        {
            case "NC":
                P2MEM[3] =  0;
                break;
            case "1":
                P2MEM[3] =  0xF4;
                break;
            case "2":
                P2MEM[3] =  0xF5;
                break;
            case "3":
                P2MEM[3] =  0xF6;
                break;
            case "4":
                P2MEM[3] =  0xF7;
                break;
            case "5":
                P2MEM[3] =  0xF8;
                break;
            case "6":
                P2MEM[3] =  0xF9;
                break;
            case "7":
                P2MEM[3] =  0xFA;
                break;
            case "b":
                P2MEM[3] =  0xF3;
                break;
            case "G":
                P2MEM[3] =  0xF2;
                break;
            default:
                P2MEM[3] =  0;
                break;

        }

        switch (strARDMode)
        {
            case "NC":
                P2MEM[4] =  0;
                break;
            case "1":
                P2MEM[4] =  0xF4;
                break;
            case "2":
                P2MEM[4] =  0xF5;
                break;
            case "3":
                P2MEM[4] =  0xF6;
                break;
            case "4":
                P2MEM[4] =  0xF7;
                break;
            case "5":
                P2MEM[4] =  0xF8;
                break;
            case "6":
                P2MEM[4] =  0xF9;
                break;
            case "7":
                P2MEM[4] =  0xFA;
                break;
            case "b":
                P2MEM[4] =  0xF3;
                break;
            case "G":
                P2MEM[4] =  0xF2;
                break;
            default:
                P2MEM[4] =  0;
                break;
        }

    }



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

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_password, null);
                final EditText editTextPassword = alertLayout.findViewById(R.id.editTextPassword);

                AlertDialog.Builder alert = new AlertDialog.Builder(DisplayPatternActivity.this);
                alert.setTitle("Set Write Mode and Date Lock");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                //alert.setCancelable(false);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strPassword = editTextPassword.getText().toString();
                        if(strPassword.equals("")){
                            Toast.makeText(DisplayPatternActivity.this,"Password can't be empty",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(strPassword.equals("shruti")){
                            Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(DisplayPatternActivity.this,"Wrong Password...Try Again",Toast.LENGTH_SHORT).show();
                            //return;
                        }

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                return true;

            default:
                return true;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myHandlerChk.removeCallbacks(checkDataContinue);
        //startActivity(new Intent(mContext, MainActivity.class));
        finish();
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
            if(D0MEM[0] != 0xFF){

                char tmpChar = D0MEM[0];

                switch (tmpChar) {
                    case 0xC0:
                        txtZero.setText("0");
                        break;
                    case 0xF9:
                        txtZero.setText("1");
                        txtZeroCustom.setText("6");
                        break;
                    case 0xA4:
                        txtZero.setText("2");
                        break;
                    case 0xB0:
                        txtZero.setText("3");
                        break;
                    case 0x99:
                        txtZero.setText("4");
                        break;
                    case 0x92:
                        txtZero.setText("5");
                        break;
                    case 0x82:
                        txtZero.setText("6");
                        break;
                    case 0xF8:
                        txtZero.setText("7");
                        txtZeroCustom.setText("5");
                        break;
                    case 0x80:
                        txtZero.setText("8");
                        break;
                    case 0x98:
                        txtZero.setText("9");
                        break;
                    case 0x88:
                        txtZero.setText("A");
                        break;
                    case 0x83:
                        txtZero.setText("b");
                        break;
                    case 0xC6:
                        txtZero.setText("C");
                        break;
                    case 0xA1:
                        txtZero.setText("d");
                        break;
                    case 0x86:
                        txtZero.setText("E");
                        break;
                    case 0x8E:
                        txtZero.setText("F");
                        break;
                    case 0xC2:
                        txtZero.setText("G");
                        break;
                    case 0xAA:
                        txtZero.setText("M");
                        break;
                    case 0xC7:
                        txtZero.setText("L");
                        break;
                    case 0xB9:
                        txtZero.setText("-1");
                        break;
                    case 0xF3:
                        txtZeroCustom.setText("b");
                        break;
                    case 0xF2:
                        txtZeroCustom.setText("G");
                        break;
                    case 0xF4:
                        txtZeroCustom.setText("1");
                        break;
                    case 0xF5:
                        txtZeroCustom.setText("2");
                        break;
                    case 0xF6:
                        txtZeroCustom.setText("3");
                        break;
                    case 0xF7:
                        txtZeroCustom.setText("4");
                        break;
                    case 0xFA:
                        txtZeroCustom.setText("7");
                        break;
                }

                tmpChar = D0MEM[1];
                switch (tmpChar) {
                    case 0xC0:
                        txtOne.setText("0");
                        break;
                    case 0xF9:
                        txtOne.setText("1");
                        txtOneCustom.setText("6");
                        break;
                    case 0xA4:
                        txtOne.setText("2");
                        break;
                    case 0xB0:
                        txtOne.setText("3");
                        break;
                    case 0x99:
                        txtOne.setText("4");
                        break;
                    case 0x92:
                        txtOne.setText("5");
                        break;
                    case 0x82:
                        txtOne.setText("6");
                        break;
                    case 0xF8:
                        txtOne.setText("7");
                        txtOneCustom.setText("5");
                        break;
                    case 0x80:
                        txtOne.setText("8");
                        break;
                    case 0x98:
                        txtOne.setText("9");
                        break;
                    case 0x88:
                        txtOne.setText("A");
                        break;
                    case 0x83:
                        txtOne.setText("b");
                        break;
                    case 0xC6:
                        txtOne.setText("C");
                        break;
                    case 0xA1:
                        txtOne.setText("d");
                        break;
                    case 0x86:
                        txtOne.setText("E");
                        break;
                    case 0x8E:
                        txtOne.setText("F");
                        break;
                    case 0xC2:
                        txtOne.setText("G");
                        break;
                    case 0xAA:
                        txtOne.setText("M");
                        break;
                    case 0xC7:
                        txtOne.setText("L");
                        break;
                    case 0xB9:
                        txtOne.setText("-1");
                        break;
                    case 0xF3:
                        txtOneCustom.setText("b");
                        break;
                    case 0xF2:
                        txtOneCustom.setText("G");
                        break;
                    case 0xF4:
                        txtOneCustom.setText("1");
                        break;
                    case 0xF5:
                        txtOneCustom.setText("2");
                        break;
                    case 0xF6:
                        txtOneCustom.setText("3");
                        break;
                    case 0xF7:
                        txtOneCustom.setText("4");
                        break;
                    case 0xFA:
                        txtOneCustom.setText("7");
                        break;
                }

                tmpChar = D0MEM[2];
                switch (tmpChar) {
                    case 0xC0:
                        txtTwo.setText("0");
                        break;
                    case 0xF9:
                        txtTwo.setText("1");
                        txtTwoCustom.setText("6");
                        break;
                    case 0xA4:
                        txtTwo.setText("2");
                        break;
                    case 0xB0:
                        txtTwo.setText("3");
                        break;
                    case 0x99:
                        txtTwo.setText("4");
                        break;
                    case 0x92:
                        txtTwo.setText("5");
                        break;
                    case 0x82:
                        txtTwo.setText("6");
                        break;
                    case 0xF8:
                        txtTwo.setText("7");
                        txtTwoCustom.setText("5");
                        break;
                    case 0x80:
                        txtTwo.setText("8");
                        break;
                    case 0x98:
                        txtTwo.setText("9");
                        break;
                    case 0x88:
                        txtTwo.setText("A");
                        break;
                    case 0x83:
                        txtTwo.setText("b");
                        break;
                    case 0xC6:
                        txtTwo.setText("C");
                        break;
                    case 0xA1:
                        txtTwo.setText("d");
                        break;
                    case 0x86:
                        txtTwo.setText("E");
                        break;
                    case 0x8E:
                        txtTwo.setText("F");
                        break;
                    case 0xC2:
                        txtTwo.setText("G");
                        break;
                    case 0xAA:
                        txtTwo.setText("M");
                        break;
                    case 0xC7:
                        txtTwo.setText("L");
                        break;
                    case 0xB9:
                        txtTwo.setText("-1");
                        break;
                    case 0xF3:
                        txtTwoCustom.setText("b");
                        break;
                    case 0xF2:
                        txtTwoCustom.setText("G");
                        break;
                    case 0xF4:
                        txtTwoCustom.setText("1");
                        break;
                    case 0xF5:
                        txtTwoCustom.setText("2");
                        break;
                    case 0xF6:
                        txtTwoCustom.setText("3");
                        break;
                    case 0xF7:
                        txtTwoCustom.setText("4");
                        break;
                    case 0xFA:
                        txtTwoCustom.setText("7");
                        break;
                }

                tmpChar = D0MEM[3];
                switch (tmpChar) {
                    case 0xC0:
                        txtThree.setText("0");
                        break;
                    case 0xF9:
                        txtThree.setText("1");
                        txtThreeCustom.setText("6");
                        break;
                    case 0xA4:
                        txtThree.setText("2");
                        break;
                    case 0xB0:
                        txtThree.setText("3");
                        break;
                    case 0x99:
                        txtThree.setText("4");
                        break;
                    case 0x92:
                        txtThree.setText("5");
                        break;
                    case 0x82:
                        txtThree.setText("6");
                        break;
                    case 0xF8:
                        txtThree.setText("7");
                        txtThreeCustom.setText("5");
                        break;
                    case 0x80:
                        txtThree.setText("8");
                        break;
                    case 0x98:
                        txtThree.setText("9");
                        break;
                    case 0x88:
                        txtThree.setText("A");
                        break;
                    case 0x83:
                        txtThree.setText("b");
                        break;
                    case 0xC6:
                        txtThree.setText("C");
                        break;
                    case 0xA1:
                        txtThree.setText("d");
                        break;
                    case 0x86:
                        txtThree.setText("E");
                        break;
                    case 0x8E:
                        txtThree.setText("F");
                        break;
                    case 0xC2:
                        txtThree.setText("G");
                        break;
                    case 0xAA:
                        txtThree.setText("M");
                        break;
                    case 0xC7:
                        txtThree.setText("L");
                        break;
                    case 0xB9:
                        txtThree.setText("-1");
                        break;
                    case 0xF3:
                        txtThreeCustom.setText("b");
                        break;
                    case 0xF2:
                        txtThreeCustom.setText("G");
                        break;
                    case 0xF4:
                        txtThreeCustom.setText("1");
                        break;
                    case 0xF5:
                        txtThreeCustom.setText("2");
                        break;
                    case 0xF6:
                        txtThreeCustom.setText("3");
                        break;
                    case 0xF7:
                        txtThreeCustom.setText("4");
                        break;
                    case 0xFA:
                        txtThreeCustom.setText("7");
                        break;
                }

                tmpChar = D0MEM[4];
                switch (tmpChar) {
                    case 0xC0:
                        txtFour.setText("0");
                        break;
                    case 0xF9:
                        txtFour.setText("1");
                        txtFourCustom.setText("6");
                        break;
                    case 0xA4:
                        txtFour.setText("2");
                        break;
                    case 0xB0:
                        txtFour.setText("3");
                        break;
                    case 0x99:
                        txtFour.setText("4");
                        break;
                    case 0x92:
                        txtFour.setText("5");
                        break;
                    case 0x82:
                        txtFour.setText("6");
                        break;
                    case 0xF8:
                        txtFour.setText("7");
                        txtFourCustom.setText("5");
                        break;
                    case 0x80:
                        txtFour.setText("8");
                        break;
                    case 0x98:
                        txtFour.setText("9");
                        break;
                    case 0x88:
                        txtFour.setText("A");
                        break;
                    case 0x83:
                        txtFour.setText("b");
                        break;
                    case 0xC6:
                        txtFour.setText("C");
                        break;
                    case 0xA1:
                        txtFour.setText("d");
                        break;
                    case 0x86:
                        txtFour.setText("E");
                        break;
                    case 0x8E:
                        txtFour.setText("F");
                        break;
                    case 0xC2:
                        txtFour.setText("G");
                        break;
                    case 0xAA:
                        txtFour.setText("M");
                        break;
                    case 0xC7:
                        txtFour.setText("L");
                        break;
                    case 0xB9:
                        txtFour.setText("-1");
                        break;
                    case 0xF3:
                        txtFourCustom.setText("b");
                        break;
                    case 0xF2:
                        txtFourCustom.setText("G");
                        break;
                    case 0xF4:
                        txtFourCustom.setText("1");
                        break;
                    case 0xF5:
                        txtFourCustom.setText("2");
                        break;
                    case 0xF6:
                        txtFourCustom.setText("3");
                        break;
                    case 0xF7:
                        txtFourCustom.setText("4");
                        break;
                    case 0xFA:
                        txtFourCustom.setText("7");
                        break;
                }



                /*int tmp = D0MEM[0];
                //txtZero.setText(String.valueOf(tmp));
                tmp = D0MEM[1];
                txtOne.setText(String.valueOf(tmp));
                tmp = D0MEM[2];
                txtTwo.setText(String.valueOf(tmp));
                tmp = D0MEM[3];
                txtThree.setText(String.valueOf(tmp));
                tmp = D0MEM[4];
                txtFour.setText(String.valueOf(tmp));*/
                D0MEM[0] = 0xFF;
            }

            if(D1MEM[0] != 0xFF){

                char tmpChar = D1MEM[0];

                switch (tmpChar) {
                    case 0xC0:
                        txtFive.setText("0");
                        break;
                    case 0xF9:
                        txtFive.setText("1");
                        txtFiveCustom.setText("6");
                        break;
                    case 0xA4:
                        txtFive.setText("2");
                        break;
                    case 0xB0:
                        txtFive.setText("3");
                        break;
                    case 0x99:
                        txtFive.setText("4");
                        break;
                    case 0x92:
                        txtFive.setText("5");
                        break;
                    case 0x82:
                        txtFive.setText("6");
                        break;
                    case 0xF8:
                        txtFive.setText("7");
                        txtFiveCustom.setText("5");
                        break;
                    case 0x80:
                        txtFive.setText("8");
                        break;
                    case 0x98:
                        txtFive.setText("9");
                        break;
                    case 0x88:
                        txtFive.setText("A");
                        break;
                    case 0x83:
                        txtFive.setText("b");
                        break;
                    case 0xC6:
                        txtFive.setText("C");
                        break;
                    case 0xA1:
                        txtFive.setText("d");
                        break;
                    case 0x86:
                        txtFive.setText("E");
                        break;
                    case 0x8E:
                        txtFive.setText("F");
                        break;
                    case 0xC2:
                        txtFive.setText("G");
                        break;
                    case 0xAA:
                        txtFive.setText("M");
                        break;
                    case 0xC7:
                        txtFive.setText("L");
                        break;
                    case 0xB9:
                        txtFive.setText("-1");
                        break;
                    case 0xF3:
                        txtFiveCustom.setText("b");
                        break;
                    case 0xF2:
                        txtFiveCustom.setText("G");
                        break;
                    case 0xF4:
                        txtFiveCustom.setText("1");
                        break;
                    case 0xF5:
                        txtFiveCustom.setText("2");
                        break;
                    case 0xF6:
                        txtFiveCustom.setText("3");
                        break;
                    case 0xF7:
                        txtFiveCustom.setText("4");
                        break;
                    case 0xFA:
                        txtFiveCustom.setText("7");
                        break;
                }

                tmpChar = D1MEM[1];
                switch (tmpChar) {
                    case 0xC0:
                        txtSix.setText("0");
                        break;
                    case 0xF9:
                        txtSix.setText("1");
                        txtSixCustom.setText("6");
                        break;
                    case 0xA4:
                        txtSix.setText("2");
                        break;
                    case 0xB0:
                        txtSix.setText("3");
                        break;
                    case 0x99:
                        txtSix.setText("4");
                        break;
                    case 0x92:
                        txtSix.setText("5");
                        break;
                    case 0x82:
                        txtSix.setText("6");
                        break;
                    case 0xF8:
                        txtSix.setText("7");
                        txtSixCustom.setText("5");
                        break;
                    case 0x80:
                        txtSix.setText("8");
                        break;
                    case 0x98:
                        txtSix.setText("9");
                        break;
                    case 0x88:
                        txtSix.setText("A");
                        break;
                    case 0x83:
                        txtSix.setText("b");
                        break;
                    case 0xC6:
                        txtSix.setText("C");
                        break;
                    case 0xA1:
                        txtSix.setText("d");
                        break;
                    case 0x86:
                        txtSix.setText("E");
                        break;
                    case 0x8E:
                        txtSix.setText("F");
                        break;
                    case 0xC2:
                        txtSix.setText("G");
                        break;
                    case 0xAA:
                        txtSix.setText("M");
                        break;
                    case 0xC7:
                        txtSix.setText("L");
                        break;
                    case 0xB9:
                        txtSix.setText("-1");
                        break;
                    case 0xF3:
                        txtSixCustom.setText("b");
                        break;
                    case 0xF2:
                        txtSixCustom.setText("G");
                        break;
                    case 0xF4:
                        txtSixCustom.setText("1");
                        break;
                    case 0xF5:
                        txtSixCustom.setText("2");
                        break;
                    case 0xF6:
                        txtSixCustom.setText("3");
                        break;
                    case 0xF7:
                        txtSixCustom.setText("4");
                        break;
                    case 0xFA:
                        txtSixCustom.setText("7");
                        break;
                }

                tmpChar = D1MEM[2];
                switch (tmpChar) {
                    case 0xC0:
                        txtSeven.setText("0");
                        break;
                    case 0xF9:
                        txtSeven.setText("1");
                        txtSevenCustom.setText("6");
                        break;
                    case 0xA4:
                        txtSeven.setText("2");
                        break;
                    case 0xB0:
                        txtSeven.setText("3");
                        break;
                    case 0x99:
                        txtSeven.setText("4");
                        break;
                    case 0x92:
                        txtSeven.setText("5");
                        break;
                    case 0x82:
                        txtSeven.setText("6");
                        break;
                    case 0xF8:
                        txtSeven.setText("7");
                        txtSevenCustom.setText("5");
                        break;
                    case 0x80:
                        txtSeven.setText("8");
                        break;
                    case 0x98:
                        txtSeven.setText("9");
                        break;
                    case 0x88:
                        txtSeven.setText("A");
                        break;
                    case 0x83:
                        txtSeven.setText("b");
                        break;
                    case 0xC6:
                        txtSeven.setText("C");
                        break;
                    case 0xA1:
                        txtSeven.setText("d");
                        break;
                    case 0x86:
                        txtSeven.setText("E");
                        break;
                    case 0x8E:
                        txtSeven.setText("F");
                        break;
                    case 0xC2:
                        txtSeven.setText("G");
                        break;
                    case 0xAA:
                        txtSeven.setText("M");
                        break;
                    case 0xC7:
                        txtSeven.setText("L");
                        break;
                    case 0xB9:
                        txtSeven.setText("-1");
                        break;
                    case 0xF3:
                        txtSevenCustom.setText("b");
                        break;
                    case 0xF2:
                        txtSevenCustom.setText("G");
                        break;
                    case 0xF4:
                        txtSevenCustom.setText("1");
                        break;
                    case 0xF5:
                        txtSevenCustom.setText("2");
                        break;
                    case 0xF6:
                        txtSevenCustom.setText("3");
                        break;
                    case 0xF7:
                        txtSevenCustom.setText("4");
                        break;
                    case 0xFA:
                        txtSevenCustom.setText("7");
                        break;
                }

                tmpChar = D1MEM[3];
                switch (tmpChar) {
                    case 0xC0:
                        txtMNTMode.setText("0");
                        break;
                    case 0xF9:
                        txtMNTMode.setText("1");
                        txtMNTModeCustom.setText("6");
                        break;
                    case 0xA4:
                        txtMNTMode.setText("2");
                        break;
                    case 0xB0:
                        txtMNTMode.setText("3");
                        break;
                    case 0x99:
                        txtMNTMode.setText("4");
                        break;
                    case 0x92:
                        txtMNTMode.setText("5");
                        break;
                    case 0x82:
                        txtMNTMode.setText("6");
                        break;
                    case 0xF8:
                        txtMNTMode.setText("7");
                        txtMNTModeCustom.setText("5");
                        break;
                    case 0x80:
                        txtMNTMode.setText("8");
                        break;
                    case 0x98:
                        txtMNTMode.setText("9");
                        break;
                    case 0x88:
                        txtMNTMode.setText("A");
                        break;
                    case 0x83:
                        txtMNTMode.setText("b");
                        break;
                    case 0xC6:
                        txtMNTMode.setText("C");
                        break;
                    case 0xA1:
                        txtMNTMode.setText("d");
                        break;
                    case 0x86:
                        txtMNTMode.setText("E");
                        break;
                    case 0x8E:
                        txtMNTMode.setText("F");
                        break;
                    case 0xC2:
                        txtMNTMode.setText("G");
                        break;
                    case 0xAA:
                        txtMNTMode.setText("M");
                        break;
                    case 0xC7:
                        txtMNTMode.setText("L");
                        break;
                    case 0xB9:
                        txtMNTMode.setText("-1");
                        break;
                    case 0xF3:
                        txtMNTModeCustom.setText("b");
                        break;
                    case 0xF2:
                        txtMNTModeCustom.setText("G");
                        break;
                    case 0xF4:
                        txtMNTModeCustom.setText("1");
                        break;
                    case 0xF5:
                        txtMNTModeCustom.setText("2");
                        break;
                    case 0xF6:
                        txtMNTModeCustom.setText("3");
                        break;
                    case 0xF7:
                        txtMNTModeCustom.setText("4");
                        break;
                    case 0xFA:
                        txtMNTModeCustom.setText("7");
                        break;
                }

                tmpChar = D1MEM[4];
                switch (tmpChar) {
                    case 0xC0:
                        txtARDMode.setText("0");
                        break;
                    case 0xF9:
                        txtARDMode.setText("1");
                        txtARDModeCustom.setText("6");
                        break;
                    case 0xA4:
                        txtARDMode.setText("2");
                        break;
                    case 0xB0:
                        txtARDMode.setText("3");
                        break;
                    case 0x99:
                        txtARDMode.setText("4");
                        break;
                    case 0x92:
                        txtARDMode.setText("5");
                        break;
                    case 0x82:
                        txtARDMode.setText("6");
                        break;
                    case 0xF8:
                        txtARDMode.setText("7");
                        txtARDModeCustom.setText("5");
                        break;
                    case 0x80:
                        txtARDMode.setText("8");
                        break;
                    case 0x98:
                        txtARDMode.setText("9");
                        break;
                    case 0x88:
                        txtARDMode.setText("A");
                        break;
                    case 0x83:
                        txtARDMode.setText("b");
                        break;
                    case 0xC6:
                        txtARDMode.setText("C");
                        break;
                    case 0xA1:
                        txtARDMode.setText("d");
                        break;
                    case 0x86:
                        txtARDMode.setText("E");
                        break;
                    case 0x8E:
                        txtARDMode.setText("F");
                        break;
                    case 0xC2:
                        txtARDMode.setText("G");
                        break;
                    case 0xAA:
                        txtARDMode.setText("M");
                        break;
                    case 0xC7:
                        txtARDMode.setText("L");
                        break;
                    case 0xB9:
                        txtARDMode.setText("-1");
                        break;
                    case 0xF3:
                        txtARDModeCustom.setText("b");
                        break;
                    case 0xF2:
                        txtARDModeCustom.setText("G");
                        break;
                    case 0xF4:
                        txtARDModeCustom.setText("1");
                        break;
                    case 0xF5:
                        txtARDModeCustom.setText("2");
                        break;
                    case 0xF6:
                        txtARDModeCustom.setText("3");
                        break;
                    case 0xF7:
                        txtARDModeCustom.setText("4");
                        break;
                    case 0xFA:
                        txtARDModeCustom.setText("7");
                        break;
                }



                /*int tmp = D1MEM[0];
                txtFive.setText(String.valueOf(tmp));
                tmp = D1MEM[1];
                txtSix.setText(String.valueOf(tmp));
                tmp = D1MEM[2];
                txtSeven.setText(String.valueOf(tmp));
                tmp = D1MEM[3];
                txtMNTMode.setText(String.valueOf(tmp));
                tmp = D1MEM[4];
                txtARDMode.setText(String.valueOf(tmp));*/
                D1MEM[0] = 0xFF;
            }

            myHandlerChk.postDelayed(this, 0);
        }

    };
}
