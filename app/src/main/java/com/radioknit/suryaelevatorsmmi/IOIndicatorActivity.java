package com.radioknit.suryaelevatorsmmi;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;


import static com.radioknit.suryaelevatorsmmi.MainActivity.IPMEM1;
import static com.radioknit.suryaelevatorsmmi.MainActivity.IPMEM4;
import static com.radioknit.suryaelevatorsmmi.MainActivity.IPMEM6;
import static com.radioknit.suryaelevatorsmmi.MainActivity.MNTMEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.OUTMEM1;
import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;
import static com.radioknit.suryaelevatorsmmi.MainActivity.oldIPMEM1;
import static com.radioknit.suryaelevatorsmmi.MainActivity.oldIPMEM4;
import static com.radioknit.suryaelevatorsmmi.MainActivity.oldIPMEM6;
import static com.radioknit.suryaelevatorsmmi.MainActivity.oldMNTMEM;


public class IOIndicatorActivity extends AppCompatActivity {

    private static final String TAG = "IOIndicatorActivity";
    private Context mContext;
    private BluetoothAdapter bluetoothAdapter;
    private Button btnInputSignals;
    private Button btnoutputSignals;
    private LinearLayout llInputSignals;
    private LinearLayout llOutputSignals;
    private static CheckBox chk_OP_RunUp;
    private static CheckBox chk_OP_RunDn;
    private static CheckBox chk_OP_Speed_1_OP;
    private static CheckBox chk_OP_Speed_2_OP;
    private static CheckBox chk_OP_ARD_Relay;
    private static CheckBox chk_OP_CT_Relay;
    private static CheckBox chk_OP_Speed_3_OP;
    private static CheckBox chk_OP_MainContact;
    private static CheckBox chk_OP_DC_OP;
    private static CheckBox chk_OP_DO_OP;
    private static CheckBox chk_OP_Blank_1;
    private static CheckBox chk_OP_Break_Sig_1;
    private static CheckBox chk_OP_Break_Sig_2;
    private static CheckBox chk_OP_Blank_2;
    private static CheckBox chk_OP_Blank_3;
    private static CheckBox chk_OP_Blank_4;
    private static CheckBox chk_io_MC_Room_ins;
    private static CheckBox chk_io_ARDIp;
    private static CheckBox chk_io_SldnSwUp2;
    private static CheckBox chk_io_SldnSwDn2;
    private static CheckBox chk_io_MotorTherm;
    private static CheckBox chk_io_FiremanSw;
    private static CheckBox chk_io_CtIp;
    private static CheckBox chk_io_BreakSwitch;
    private static CheckBox chk_io_Encoder_ch_A;
    private static CheckBox chk_io_Encoder_ch_BB;
    private static CheckBox chk_io_UpStopSw;
    private static CheckBox chk_io_RstDnStop;
    private static CheckBox chk_io_SlowSwDn1;
    private static CheckBox chk_io_SlowSwUp1;
    private static CheckBox chk_io_DoorZoneSw;
    private static CheckBox chk_io_BrkIn;
    private static CheckBox chk_io_AM;
    private static CheckBox chk_io_MntUp;
    private static CheckBox chk_io_MntDn;
    private static CheckBox chk_io_SftEdge;
    private static CheckBox chk_io_IR;
    private static CheckBox chk_io_RunStp;
    private static CheckBox chk_io_Far;
    private static CheckBox chk_io_RRD;
    private static CheckBox chk_io_Blank_2;
    private static CheckBox chk_io_Blank_3;
    private static CheckBox chk_io_Blank_4;
    private static CheckBox chk_io_Blank_5;
    private static CheckBox chk_io_Blank_6;
    private static CheckBox chk_io_Blank_7;
    private static CheckBox chk_io_Blank_8;
    private static CheckBox chk_io_Blank_9;
    private Menu menu;
    // =============

    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";
    final Handler myHandlerChk = new Handler();

    //output signals
    char oldOUTMEM1;

    //input signals


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioindicator);

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void generateId() {

        btnInputSignals = (Button) findViewById(R.id.btnInputSignals);
        btnoutputSignals = (Button)findViewById(R.id.btnOutputSignals);
        llInputSignals = (LinearLayout) findViewById(R.id.llInout_Signals);
        llOutputSignals = (LinearLayout)findViewById(R.id.llOutputSignals);
        chk_OP_RunUp = (CheckBox)findViewById(R.id.rdo_OP_Run_up);
        chk_OP_RunDn = (CheckBox)findViewById(R.id.rdo_OP_Run_dn);
        chk_OP_Speed_1_OP = (CheckBox)findViewById(R.id.rdo_OP_Speed_1_op);
        chk_OP_Speed_2_OP = (CheckBox)findViewById(R.id.rdo_OP_Speed_2_op);
        chk_OP_ARD_Relay = (CheckBox)findViewById(R.id.rdo_OP_ARD_Relay);
        chk_OP_CT_Relay = (CheckBox)findViewById(R.id.rdo_OP_CT_Realy);
        chk_OP_Speed_3_OP = (CheckBox)findViewById(R.id.rdo_OP_Speed_3_op);
        chk_OP_MainContact = (CheckBox)findViewById(R.id.rdo_OP_Main_contact);
        chk_OP_DC_OP = (CheckBox)findViewById(R.id.rdo_OP_DC_op);
        chk_OP_DO_OP = (CheckBox)findViewById(R.id.rdo_OP_DO_op);
        chk_OP_Break_Sig_1 = (CheckBox)findViewById(R.id.rdo_OP_Break_Sig_1);
        chk_OP_Break_Sig_2 = (CheckBox)findViewById(R.id.rdo_OP_Break_Sig_2);
        chk_OP_Blank_1 = (CheckBox)findViewById(R.id.rdo_OP_Blank_1);
        chk_OP_Blank_2 = (CheckBox)findViewById(R.id.rdo_OP_Blank_2);
        chk_OP_Blank_3 = (CheckBox)findViewById(R.id.rdo_OP_Blank_3);
        chk_OP_Blank_4 = (CheckBox)findViewById(R.id.rdo_OP_Blank_4);

        chk_io_MC_Room_ins = (CheckBox) findViewById(R.id.chMCRoomIns);
        chk_io_ARDIp = (CheckBox) findViewById(R.id.chArdIP);
        chk_io_SldnSwUp2 = (CheckBox) findViewById(R.id.chSldnSwUp2);
        chk_io_SldnSwDn2 = (CheckBox) findViewById(R.id.chSldnSwDn2);
        chk_io_MotorTherm = (CheckBox) findViewById(R.id.chMotorTherm);
        chk_io_FiremanSw = (CheckBox) findViewById(R.id.chFiremanSw);
        chk_io_CtIp = (CheckBox)findViewById(R.id.chCtIP);
        chk_io_BreakSwitch = (CheckBox)findViewById(R.id.chBreakSwitch);
        chk_io_Encoder_ch_A = (CheckBox)findViewById(R.id.chEncoderChA);
        chk_io_Encoder_ch_BB = (CheckBox)findViewById(R.id.chEnccoderChB);
        chk_io_UpStopSw = (CheckBox)findViewById(R.id.chUpStopSw);
        chk_io_RstDnStop = (CheckBox)findViewById(R.id.chRstDnStop);
        chk_io_SlowSwDn1 = (CheckBox)findViewById(R.id.chSlowSwDN1);
        chk_io_SlowSwUp1 = (CheckBox)findViewById(R.id.chSlowSwUP1);
        chk_io_DoorZoneSw = (CheckBox)findViewById(R.id.chDoorZoneSw);
        chk_io_BrkIn = (CheckBox)findViewById(R.id.chBrkIn);
        chk_io_AM = (CheckBox)findViewById(R.id.chAm);
        chk_io_MntUp = (CheckBox)findViewById(R.id.chMntUp);
        chk_io_MntDn = (CheckBox)findViewById(R.id.chMntDn);
        chk_io_SftEdge = (CheckBox)findViewById(R.id.chSftEdge);
        chk_io_IR= (CheckBox)findViewById(R.id.chIR);
        chk_io_RunStp = (CheckBox)findViewById(R.id.chRunSft);
        chk_io_Far = (CheckBox)findViewById(R.id.chFAR);
        chk_io_RRD = (CheckBox)findViewById(R.id.chRRD);
        chk_io_Blank_2 = (CheckBox)findViewById(R.id.rdoBlankTwo);
        chk_io_Blank_3 = (CheckBox)findViewById(R.id.rdoBlank_three);
        chk_io_Blank_4 = (CheckBox)findViewById(R.id.rdoBlank_four);
        chk_io_Blank_5 = (CheckBox)findViewById(R.id.rdoBlank_five);
        chk_io_Blank_6 = (CheckBox)findViewById(R.id.rdoBlank_six);
        chk_io_Blank_7 = (CheckBox)findViewById(R.id.rdoBlank_seven);
        chk_io_Blank_8 = (CheckBox)findViewById(R.id.rdoBlankEight);
        chk_io_Blank_9 = (CheckBox)findViewById(R.id.rdoBlankNine);

    }

    private void registerEvent() {
        btnInputSignals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llInputSignals.setVisibility(View.VISIBLE);
                llOutputSignals.setVisibility(View.GONE);
            }
        });

        btnoutputSignals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llInputSignals.setVisibility(View.GONE);
                llOutputSignals.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createObj() {
        mContext = getApplicationContext();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        getSupportActionBar().setTitle("IO Indicator");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(mContext, MainActivity.class));
        myHandlerChk.removeCallbacks(checkDataContinue);
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


            /*if(!str71IOValues1.equals("")){
                setIOValues(str71IOValues1);
            }
            if(!str77IOValues2.equals("")){
                setIOValuesTwo(str77IOValues2);
            }*/

            if(oldOUTMEM1 != OUTMEM1){
                oldOUTMEM1=OUTMEM1;
                setOutputSignal(OUTMEM1);
            }

            if(oldIPMEM1 != IPMEM1){
                oldIPMEM1 = IPMEM1;
                setInputSignal();
            }

            if(oldIPMEM4 != IPMEM4){
                oldIPMEM4 = IPMEM4;
                setInputSignal();
            }

            if(oldIPMEM6 != IPMEM6){
                oldIPMEM6 = IPMEM6;
                setInputSignal();
            }

            if(oldMNTMEM != MNTMEM){
                oldMNTMEM = MNTMEM;
                setInputSignal();
            }

            myHandlerChk.postDelayed(this, 0);
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_control_activity, menu);
        this.menu = menu;
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_search:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ==========================================================================

    private static void setOutputSignal(char tempByte) {

        char chk = (char) (tempByte & 1);
       // Log.e(TAG, "chk1: " + chk);
        if (chk == 1) {
            chk_OP_RunUp.setChecked(true);
        } else {
            chk_OP_RunUp.setChecked(false);
        }



        chk = (char) (tempByte & 2);
       // Log.e(TAG, "chk2: " + chk);
        if (chk == 2) {
            chk_OP_RunDn.setChecked(true);
        } else {
            chk_OP_RunDn.setChecked(false);
        }

        chk = (char) (tempByte & 4);
       // Log.e(TAG, "chk3: " + chk);
        if(chk == 4){
            chk_OP_Speed_1_OP.setChecked(true);
        } else {
            chk_OP_Speed_1_OP.setChecked(false);
        }

        chk = (char) (tempByte & 32);
       // Log.e(TAG, "chk4: " + chk);
        if(chk == 32){
            chk_OP_DC_OP.setChecked(false);
            chk_OP_DO_OP.setChecked(true);
        } else {
            chk_OP_DC_OP.setChecked(true);
            chk_OP_DO_OP.setChecked(false);
        }

        chk = (char) (tempByte & 64);
       // Log.e(TAG, "chk5: " + chk);
        if(chk == 64){
            chk_OP_Speed_2_OP.setChecked(true);
        } else {
            chk_OP_Speed_2_OP.setChecked(false);
        }

    }


    private static void setInputSignal() {

        char chk = (char) (IPMEM6 & 16);
        if(chk == 16){
            chk_io_SldnSwUp2.setChecked(true);
        } else {
            chk_io_SldnSwUp2.setChecked(false);
        }

        chk = (char) (MNTMEM & 1);
        if(chk == 1){
            chk_io_AM.setChecked(true);
        } else {
            chk_io_AM.setChecked(false);
        }

        chk = (char) (MNTMEM & 2);
        if(chk == 2){
            chk_io_MntUp.setChecked(true);
        } else {
            chk_io_MntUp.setChecked(false);
        }

        chk = (char) (MNTMEM & 4);
        if(chk == 4){
            chk_io_MntDn.setChecked(true);
        } else {
            chk_io_MntDn.setChecked(false);
        }

        chk = (char) (IPMEM1 & 64);
        if(chk == 64){
            chk_io_UpStopSw.setChecked(true);
        } else {
            chk_io_UpStopSw.setChecked(false);
        }


        chk = (char) (IPMEM1 & 128);
        if(chk == 128){
            chk_io_RstDnStop.setChecked(true);
        } else {
            chk_io_RstDnStop.setChecked(false);
        }

        chk = (char) (IPMEM1 & 1);
        if(chk == 1){
            chk_io_DoorZoneSw.setChecked(true);
        } else {
            chk_io_DoorZoneSw.setChecked(false);
        }


        chk = (char) (IPMEM4 & 8);
        if(chk == 8){
            chk_io_SlowSwDn1.setChecked(true);
        } else {
            chk_io_SlowSwDn1.setChecked(false);
        }

        chk = (char) (IPMEM4 & 4);
        if(chk == 4){
            chk_io_SlowSwUp1.setChecked(true);
        } else {
            chk_io_SlowSwUp1.setChecked(false);
        }
    }


}
