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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import static com.radioknit.suryaelevatorsmmi.MainActivity.CFMEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.CONFREAD;
import static com.radioknit.suryaelevatorsmmi.MainActivity.CONFREADFLAG;
import static com.radioknit.suryaelevatorsmmi.MainActivity.POMEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.POMEMFlag;
import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;
import static com.radioknit.suryaelevatorsmmi.MainActivity.strCmpDate;
import static com.radioknit.suryaelevatorsmmi.MainActivity.writeModeFlag;


public class ProgramCodeActivity extends AppCompatActivity {

    private static final String TAG = "ProgramCodeActivity";
    final Handler myHandlerChk = new Handler();
    ArrayList<String> arrCommandValueList;
    ArrayList<String> arrDoorType;
    ArrayList<String> arrNoOfFloorValueList;
    ArrayList<String> arrDoorOpenTime;
    private Spinner spinStopDelay;
    private ArrayAdapter<String> adapter;
    private Spinner spinNoOfStops;
    private Spinner spinDoorOpenTime;
    private Spinner spinDoorType;
    private TextView txtStopDelay;
    private TextView txtNoOfStop;
    private TextView txtDoorOpenTime;
    private TextView txtDoorType;
    private ArrayAdapter<String> adapterNoOfStop;
    private ArrayAdapter<String> adapterDoorType;
    private ArrayAdapter<String> adapterDoorOpenTime;
    private Button btnSetProgramCode;
    private Button btnReadPlc;
    private Context mContext;
    private Menu menu;
    char[] oldCFMEM = new char[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_program_code);
        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);

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

            if(oldCFMEM != CFMEM){
                oldCFMEM = CFMEM;
                int tmp = CFMEM[0];
                char chk = (char) (CFMEM[0] & 1);
                String strDoorType;
                if(chk == 1){
                    strDoorType = "Auto";
                    txtDoorType.setText(strDoorType);
                }else {
                    strDoorType = "Manual";
                    txtDoorType.setText(strDoorType);
                }

                tmp = CFMEM[1];
                txtNoOfStop.setText(String.valueOf(tmp + 1));
                tmp = CFMEM[2];
                String strDelay = String.valueOf(tmp) + " sec.";
                txtStopDelay.setText(strDelay);
                tmp = CFMEM[3];
                String strDoorOpen = String.valueOf(tmp) + " sec.";
                txtDoorOpenTime.setText(strDoorOpen);
               // CFMEM[0] = 0xFF;
            }
            myHandlerChk.postDelayed(this, 100);

        }


    };




    private void registerEvent() {
        btnSetProgramCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()) {
                        CustomCalculate.chkWriteModeState();
                        if(writeModeFlag){
                            setProgramCode();
                            POMEMFlag=true;
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


        btnReadPlc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    CONFREAD = 0xFF;
                    CONFREADFLAG = true;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

   void setProgramCode(){

        String strDoorType = spinDoorType.getSelectedItem().toString();
        String strNoOfStop = spinNoOfStops.getSelectedItem().toString();
        String strStopDelay = spinStopDelay.getSelectedItem().toString();
        String strDoorOpenTime = spinDoorOpenTime.getSelectedItem().toString();

        switch (strDoorType){
            case "NC":
                POMEM[0]=0;
                break;
            case "Auto":
                POMEM[0]=0x51;
                break;
            case "Manual":
                POMEM[0]=0x50;
                break;
        }

        if(strNoOfStop.equals("NC")){
            POMEM[1]= 0;
        }else{
            POMEM[1]= (char) Integer.parseInt(strNoOfStop);
        }
        if(strStopDelay.equals("NC")) {
            POMEM[2] = 0;
        }else{
            POMEM[2] = (char) Integer.parseInt(strStopDelay);
        }
            if(strDoorOpenTime.equals("NC")) {
                POMEM[3] = 0;
            }else{
                POMEM[3] = (char) Integer.parseInt(strDoorOpenTime);
            }
        POMEM[4]=0x00;
    }

    private void createObj() {
        getSupportActionBar().setTitle("Program Code");
        mContext = ProgramCodeActivity.this;

        arrCommandValueList = new ArrayList<String>();
        arrDoorType =new ArrayList<String>();
        arrNoOfFloorValueList = new ArrayList<String>();
        arrDoorOpenTime = new ArrayList<String>();

       /*arrCommandValueList.add("NC");
        arrDoorType.add("NC");
        arrNoOfFloorValueList.add("NC");
        arrDoorOpenTime.add("NC");*/

        for (int i = 1; i <= 255; i++) {
            arrCommandValueList.add(String.valueOf(i));
        }

        arrDoorType.add("Auto");
        arrDoorType.add("Manual");

        for (int i = 2; i <= 8; i++) {
            arrNoOfFloorValueList.add(String.valueOf(i));
        }


        for (int i = 1; i < 16; i++) {
            arrDoorOpenTime.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCommandValueList);
        adapterDoorType = new ArrayAdapter<String>(mContext, R.layout.list_item, arrDoorType);
        adapterNoOfStop = new ArrayAdapter<String>(mContext, R.layout.list_item, arrNoOfFloorValueList);
        adapterDoorOpenTime = new ArrayAdapter<String>(mContext, R.layout.list_item, arrDoorOpenTime);

        spinStopDelay.setAdapter(adapter);
        spinNoOfStops.setAdapter(adapterNoOfStop);
        spinDoorOpenTime.setAdapter(adapterDoorOpenTime);
        spinDoorType.setAdapter(adapterDoorType);


    }

    private void generateId() {

        btnSetProgramCode = (Button) findViewById(R.id.btnSetProgramCodes);
        btnReadPlc = (Button)findViewById(R.id.btnProgram_code_read_plc);

        spinStopDelay = (Spinner) findViewById(R.id.spinStopDelay);
        spinNoOfStops = (Spinner) findViewById(R.id.spinNoOfStop);
        spinDoorOpenTime = (Spinner) findViewById(R.id.spinDoorOpenTime);
        spinDoorType = (Spinner) findViewById(R.id.spinDoorType);

        txtStopDelay = (TextView) findViewById(R.id.tvStopDelay);
        txtNoOfStop = (TextView) findViewById(R.id.tvNoOfStop);
        txtDoorOpenTime = (TextView) findViewById(R.id.tvDoorOpenTime);
        txtDoorType = (TextView) findViewById(R.id.tvDoorType);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(mContext, MainActivity.class));
        myHandlerChk.removeCallbacks(checkDataContinue);
        finish();
    }


    // ==========================================================================


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
/*                Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                startActivity(intent);*/

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_password, null);
                final EditText editTextPassword = alertLayout.findViewById(R.id.editTextPassword);

                AlertDialog.Builder alert = new AlertDialog.Builder(ProgramCodeActivity.this);
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
                            Toast.makeText(ProgramCodeActivity.this,"Password can't be empty",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(strPassword.equals("shruti")){
                            Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ProgramCodeActivity.this,"Wrong Password...Try Again",Toast.LENGTH_SHORT).show();
                            //return;
                        }

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}