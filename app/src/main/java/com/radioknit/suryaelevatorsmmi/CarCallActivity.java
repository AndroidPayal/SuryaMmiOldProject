package com.radioknit.suryaelevatorsmmi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.OutputStream;

import static com.radioknit.suryaelevatorsmmi.MainActivity.OPMEM1;
import static com.radioknit.suryaelevatorsmmi.MainActivity.OPMEM3;
import static com.radioknit.suryaelevatorsmmi.MainActivity.OPMEM4;
import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;
import static com.radioknit.suryaelevatorsmmi.MainActivity.oldOPMEM5;

public class CarCallActivity extends AppCompatActivity implements CarCallAdapter.CarCallIndicatorSignalListner {


    private static final String TAG = "CarCallActivity";

    private static Context mContext;
    private static ListView lstFloorsIndicator;
    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";
    public static int showState[]=new int[8], showStateUp[] = new int[8], showStateDown[] = new int[8];
    //final Handler ha = new Handler();
    final Handler myHandlerChk = new Handler();
    CarCallAdapter adapter;
    private Menu menu;
    String str13HexCarCallsCop1N = "", str13HexCarCallsCop2N = "", str11ChkFlrN = "", str11HexSwitchDataN = "";
    char oldOPMEM1, oldOPMEM3, oldOPMEM4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_call);
        createObj();
        generateId();
        registerEvent();
        //checkDataContinue();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void createObj() {
        getSupportActionBar().setTitle("Car Call");
        mContext = CarCallActivity.this;

    }

    private void registerEvent() {

    }

    private void generateId() {
        lstFloorsIndicator = (ListView)findViewById(R.id.lstFloorIndicator);
        adapter = new CarCallAdapter(getApplicationContext(), "00000000" , this);
        for(int pos=0;pos<=7;pos++){
            showState[pos]=0;
            showStateUp[pos]=0;
            showStateDown[pos]=0;
        }
        lstFloorsIndicator.setAdapter(adapter);
        str13HexCarCallsCop1N = "";
        str13HexCarCallsCop2N = "";
        str11ChkFlrN = "";
        str11HexSwitchDataN = "";
    }

    @Override
    public void sendCarCallIndicatorSignal(int position) {

    }

    @Override
    public void sendUpCallIndicatorSignal(int position) {

    }

    @Override
    public void sendDnCallIndicatorSignal(int position) {

    }

    /*void checkDataContinue(){

        boolean b = ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!str13HexCarCallsCop1.equals("") && !str13HexCarCallsCop2.equals("")){
                    showCarCalls(str13HexCarCallsCop1, str13HexCarCallsCop2);
                }
                if(!str11ChkFlr.equals("") && !str11HexSwitchData.equals("")){
                    showUpDnCalls(str11ChkFlr, str11HexSwitchData);
                }
                ha.postDelayed(this, 100);
            }
        }, 100);
    }*/


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

            if(oldOPMEM1 != OPMEM1){
                oldOPMEM1 = OPMEM1;
                char chk = (char) (OPMEM1 & 1);
                Log.e(TAG , "OPMEMCAR: " + String.valueOf(chk));
                if (chk == 1) {
                    showState[7]=1;
                } else {
                    showState[7]=0;
                }

                chk = (char) (OPMEM1 & 2);
                if (chk == 2) {
                    showState[6]=1;
                } else {
                    showState[6]=0;
                }

                chk = (char) (OPMEM1 & 4);
                if (chk == 4) {
                    showState[5]=1;
                } else {
                    showState[5]=0;
                }

                chk = (char) (OPMEM1 & 8);
                if (chk == 8) {
                    showState[4]=1;
                } else {
                    showState[4]=0;
                }

                chk = (char) (OPMEM1 & 16);
                if (chk == 16) {
                    showState[3]=1;
                } else {
                    showState[3]=0;
                }

                chk = (char) (OPMEM1 & 32);
                if (chk == 32) {
                    showState[2]=1;
                } else {
                    showState[2]=0;
                }

                chk = (char) (OPMEM1 & 64);
                if (chk == 64) {
                    showState[1]=1;
                } else {
                    showState[1]=0;
                }

                chk = (char) (OPMEM1 & 128);
                if (chk == 128) {
                    showState[0]=1;
                } else {
                    showState[0]=0;
                }
                adapter.notifyDataSetChanged();
            }

            if(oldOPMEM3 != OPMEM3){
                oldOPMEM3 = OPMEM3;
                char chk = (char) (OPMEM3 & 1);
                if (chk == 1) {
                    showStateDown[7]=1;
                } else {
                    showStateDown[7]=0;
                }

                chk = (char) (OPMEM3 & 2);
                if (chk == 2) {
                    showStateDown[6]=1;
                } else {
                    showStateDown[6]=0;
                }

                chk = (char) (OPMEM3 & 4);
                if (chk == 4) {
                    showStateDown[5]=1;
                } else {
                    showStateDown[5]=0;
                }

                chk = (char) (OPMEM3 & 8);
                if (chk == 8) {
                    showStateDown[4]=1;
                } else {
                    showStateDown[4]=0;
                }

                chk = (char) (OPMEM3 & 16);
                if (chk == 16) {
                    showStateDown[3]=1;
                } else {
                    showStateDown[3]=0;
                }

                chk = (char) (OPMEM3 & 32);
                if (chk == 32) {
                    showStateDown[2]=1;
                } else {
                    showStateDown[2]=0;
                }

                chk = (char) (OPMEM3 & 64);
                if (chk == 64) {
                    showStateDown[1]=1;
                } else {
                    showStateDown[1]=0;
                }

                chk = (char) (OPMEM3 & 128);
                if (chk == 128) {
                    showStateDown[0]=1;
                } else {
                    showStateDown[0]=0;
                }
                adapter.notifyDataSetChanged();
            }

            if(oldOPMEM4 != OPMEM4){
                oldOPMEM4 = OPMEM4;

                char chk = (char) (OPMEM4 & 1);
                if (chk == 1) {
                    showStateUp[7]=1;
                } else {
                    showStateUp[7]=0;
                }

                chk = (char) (OPMEM4 & 2);
                if (chk == 2) {
                    showStateUp[6]=1;
                } else {
                    showStateUp[6]=0;
                }

                chk = (char) (OPMEM4 & 4);
                if (chk == 4) {
                    showStateUp[5]=1;
                } else {
                    showStateUp[5]=0;
                }

                chk = (char) (OPMEM4 & 8);
                if (chk == 8) {
                    showStateUp[4]=1;
                } else {
                    showStateUp[4]=0;
                }

                chk = (char) (OPMEM4 & 16);
                if (chk == 16) {
                    showStateUp[3]=1;
                } else {
                    showStateUp[3]=0;
                }

                chk = (char) (OPMEM4 & 32);
                if (chk == 32) {
                    showStateUp[2]=1;
                } else {
                    showStateUp[2]=0;
                }

                chk = (char) (OPMEM4 & 64);
                if (chk == 64) {
                    showStateUp[1]=1;
                } else {
                    showStateUp[1]=0;
                }

                chk = (char) (OPMEM4 & 128);
                if (chk == 128) {
                    showStateUp[0]=1;
                } else {
                    showStateUp[0]=0;
                }
                adapter.notifyDataSetChanged();
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
     void showCarCalls(String strCarCallCop1, String strCarCallCop2) {

       /* CarCallAdapter adapter1 = new CarCallAdapter(mContext, "00000000","00000000",(CarCallAdapter.CarCallIndicatorSignalListner) this);
        lstFloorsIndicator.setAdapter(adapter1);*/

        try {
            //int index = strCarCall.lastIndexOf("1311");
           /* String hexCarCallsCop1 = strCarCallCop1;
            String hexCarCallsCop2 = strCarCallCop2;*/
            String strcallCop1 = Utils.hexToBin(strCarCallCop1);
            String strcallCop2 = Utils.hexToBin(strCarCallCop2);
            //Log.e(TAG, "hexCarCallsCop1 = "+hexCarCallsCop1);
            //Log.e(TAG, "strcallCop1 = "+strcallCop1);
            //Log.e(TAG, "hexCarCallsCop2 = "+hexCarCallsCop2);
            //Log.e(TAG, "strcallCop2 = "+strcallCop2);
            String  strCallCopCombine = strcallCop2 + strcallCop1;
            for(int indexCop=0; indexCop <=15; indexCop++) {
                if (strCallCopCombine.charAt(indexCop) == '0') {
                    showState[indexCop] = 0;
                } else if(strCallCopCombine.charAt(indexCop) == '1') {
                    showState[indexCop] = 1;
                }
            }
            adapter.notifyDataSetChanged();
            /*CarCallAdapter adapter = new CarCallAdapter(mContext, strcallCop1, strcallCop2, (CarCallAdapter.CarCallIndicatorSignalListner) this);
            lstFloorsIndicator.setAdapter(adapter);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showUpDnCalls(String strChkFlr, String hexSwitchData) {

       /* CarCallAdapter adapter1 = new CarCallAdapter(mContext, "00000000","00000000", (CarCallAdapter.CarCallIndicatorSignalListner)this);
        lstFloorsIndicator.setAdapter(adapter1);
*/
        try {
            //int index = strUpDn.lastIndexOf("114c");
            //String strChkFlr=strUpDn.substring(index-2,index);
           // Log.e(TAG, "strChkFlr = "+ strChkFlr);
            //String hexSwitchData = strUpDn.substring(index+8,index+10);
            String binSwitchData = Utils.hexToBin(hexSwitchData);
           // Log.e(TAG, "hexSwitchData = "+ hexSwitchData);
            if(strChkFlr.equals("30")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[15]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[15]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[15]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[15]=1;
                }
            }
            if(strChkFlr.equals("31")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[14]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[14]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[14]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[14]=1;
                }
            }
            if(strChkFlr.equals("32")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[13]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[13]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[13]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[13]=1;
                }
            }
            if(strChkFlr.equals("33")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[12]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[12]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[12]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[12]=1;
                }
            }
            if(strChkFlr.equals("34")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[11]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[11]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[11]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[11]=1;
                }
            }
            if(strChkFlr.equals("35")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[10]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[10]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[10]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[10]=1;
                }
            }
            if(strChkFlr.equals("36")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[9]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[9]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[9]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[9]=1;
                }
            }
            if(strChkFlr.equals("37")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[8]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[8]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[8]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[8]=1;
                }
            }
            if(strChkFlr.equals("38")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[7]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[7]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[7]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[7]=1;
                }
            }
            if(strChkFlr.equals("39")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[6]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[6]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[6]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[6]=1;
                }
            }
            if(strChkFlr.equals("3a")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[5]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[5]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[5]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[5]=1;
                }
            }
            if(strChkFlr.equals("3b")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[4]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[4]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[4]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[4]=1;
                }
            }
            if(strChkFlr.equals("3c")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[3]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[3]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[3]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[3]=1;
                }
            }
            if(strChkFlr.equals("3d")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[2]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[2]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[2]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[2]=1;
                }
            }
            if(strChkFlr.equals("3e")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[1]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[1]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[1]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[1]=1;
                }
            }
            if(strChkFlr.equals("3f")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[0]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[0]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[0]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[0]=1;
                }
            }
            adapter.notifyDataSetChanged();

            /*String hexUpDnCalls = String.format("%04x", (int) strUpDn.charAt(index + 3));
            String strUpDnCalls = Utils.hexToBin(hexUpDnCalls);
            String floorNo = String.format("%04x", (int) strUpDn.charAt(index - 2));
            int flrNo = Integer.parseInt(floorNo) - 30;*/

            /*CarCallAdapter adapter = new CarCallAdapter(mContext, strUpDnCalls, flrNo,(CarCallAdapter.CarCallIndicatorSignalListner) this);
            lstFloorsIndicator.setAdapter(adapter);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(mContext, MainActivity.class));
        myHandlerChk.removeCallbacks(checkDataContinue);
        finish();
    }

    // ==========================================================================
}
