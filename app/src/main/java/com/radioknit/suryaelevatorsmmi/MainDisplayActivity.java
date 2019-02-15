package com.radioknit.suryaelevatorsmmi;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

import static com.radioknit.suryaelevatorsmmi.MainActivity.DISCODE;
import static com.radioknit.suryaelevatorsmmi.MainActivity.FLRNUM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.IPMEM1;
import static com.radioknit.suryaelevatorsmmi.MainActivity.IPMEM4;
import static com.radioknit.suryaelevatorsmmi.MainActivity.OPMEM4;
import static com.radioknit.suryaelevatorsmmi.MainActivity.OPMEM5;
import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;

public class MainDisplayActivity extends AppCompatActivity {

    private static final String TAG = "MainDisplayActivity";
    private BluetoothAdapter bluetoothAdapter;
    private Button btnData;
    private LinearLayout llData;
    private static Button btnDoorOpen;
    private static Button btnDoorClose;
    private static Button btnAttnStart;
    private static Button btnNonStop;
    private static Button btnAuto;
    private static Button btnVIP2;
    private static Button btnDIR;
    private static Button btnStop;
    private String remaningString;
//    private static ImageView imgDn;
//    private static ImageView imgUp;
    private static GifImageView imgDn;
    private static GifImageView imgUp;
    private TextView txtFloorNumber;
    private TextView txtFloorCount;
    private String connectedDeviceName;
    private StringBuffer outStringBuffer;
    private StringBuffer mOutStringBuffer;
    private static Context mContext;
    final Handler myHandlerChk = new Handler();
    // =============
    private Menu menu;
    String strReceivedDataNew = "";
    char oldFLRNUM = 0xFF;
    char oldOPMEM5 = 0xFF;
    char oldIPMEM4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_display);

        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void registerEvent() {

    }

    private void createObj() {
        getSupportActionBar().setTitle("Main Display");
        mContext = MainDisplayActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    private void generateId() {
        btnData = (Button)findViewById(R.id.btn_main_dispaly_data);
        llData = (LinearLayout)findViewById(R.id.llmain_display_data);
        btnDoorOpen = (Button)findViewById(R.id.btnMani_display_doorOpen);
        btnDoorClose = (Button)findViewById(R.id.btnMani_display_doorClose);
        btnAttnStart = (Button)findViewById(R.id.btnMani_display_AttnStart);
        btnNonStop = (Button)findViewById(R.id.btnMani_display_NonStop);
        btnDIR = (Button)findViewById(R.id.btnMani_display_Dir);
        btnAuto = (Button)findViewById(R.id.btnMani_display_Auto);
        btnStop = (Button)findViewById(R.id.btnMani_display_stop);
        btnVIP2 = (Button)findViewById(R.id.btnMani_display_VIP2);
//        imgUp = (ImageView)findViewById(R.id.imgUp);
//        imgDn = (ImageView)findViewById(R.id.imgDwn);
        imgUp = (GifImageView)findViewById(R.id.imgUp);
        imgDn = (GifImageView)findViewById(R.id.imgDwn);
        txtFloorNumber = (TextView)findViewById(R.id.tv_main_display_floorNo);
        txtFloorCount = (TextView)findViewById(R.id.tv_main_display_floorCnt);
        strReceivedDataNew = "";
    }

   @Override
   public void onBackPressed() {
       super.onBackPressed();
       myHandlerChk.removeCallbacks(checkDataContinue);
       finish();
   }

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

    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            // showReceivedData();
            if (isConnected()) {
                try {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                } catch (Exception e) {
                    //Catch
                }
            } else {
                try {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
                } catch (Exception e) {
                    //Catch
                }
            }

            if (oldFLRNUM != FLRNUM) {
                oldFLRNUM = FLRNUM;
                int tmp = FLRNUM;
                txtFloorNumber.setText(String.valueOf(tmp));
                txtFloorCount.setText(DISCODE);
            }

            if (oldOPMEM5 != OPMEM5) {
                oldOPMEM5 = OPMEM5;
                char chk = (char) (OPMEM5 & 3);
                Log.e(TAG, "FLR Arrow = " + chk);
                switch (chk) {
                    case 0:
                        imgUp.setVisibility(View.GONE);
                        imgDn.setVisibility(View.GONE);
                        break;
                    case 1:
                        imgUp.setVisibility(View.VISIBLE);
                        imgDn.setVisibility(View.GONE);
                        break;
                    case 2:
                        imgUp.setVisibility(View.GONE);
                        imgDn.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        imgUp.setVisibility(View.GONE);
                        imgDn.setVisibility(View.GONE);
                        break;
                }
            }

            if (oldIPMEM4 != IPMEM4) {
                oldIPMEM4 = IPMEM4;
                char chk = (char) (IPMEM4  & 1);

                if (chk == 1) {
                    btnDoorClose.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                } else {
                    btnDoorClose.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
                }
                chk = (char) (IPMEM4 & 2);
                if (chk == 2) {
                    btnDoorOpen.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                } else {
                    btnDoorOpen.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
                }
                //IPMEM4 = 0xFF;
            }

            myHandlerChk.postDelayed(this, 100);
        }

    };



}
