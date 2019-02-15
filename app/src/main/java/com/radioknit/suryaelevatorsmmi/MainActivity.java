package com.radioknit.suryaelevatorsmmi;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.droidsonroids.gif.GifImageView;

import static com.radioknit.suryaelevatorsmmi.CustomCalculate.hex2ascii;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final boolean D = true;
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";


    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private static StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private static BluetoothChatService mChatService = null;
    //private static BluetoothChatService connector;
    BluetoothDevice device;
    int tryBTConnect=0;
    static StringBuffer msgAppend=new StringBuffer();
    private Menu menu;
    private MainActivity mContext;
    final Handler myHandlerChk = new Handler();
    private boolean needClean;
    private static TextView txtFloorNumber;
    private static GifImageView imgUp;
    private static GifImageView imgDown;

    //fields initialized for received data memory
    public static char OPMEM1, oldOPMEM1;
    char OPMEM2, oldOPMEM2;
    public static char OPMEM3, oldOPMEM3;
    public static char OPMEM4, oldOPMEM4;
    public static char OPMEM5, oldOPMEM5;

    public static char IPMEM1;
    char IPMEM2, oldIPMEM2;
    char IPMEM3, oldIPMEM3;
    public static char IPMEM4;
    char IPMEM5, oldIPMEM5;
    public static char IPMEM6;

    char COUNTMEM, oldCOUNTMEM;
    public static char FLRNUM = 0xFF, oldFLRNUM = 0xFF;
    public static String DISCODE;
    char oldDISCODE;
    public static char OUTMEM1, oldOUTMEM1;
    char RUNSTAT1, oldRUNSTAT1;
    char CONBITS, oldCONBITS;

    public static char MNTMEM, CONFREAD = 0;
    public static char loopTurn=0;
    public static boolean sendBtDataFlag;
    public static char[] POMEM = new char[5], oldPOMEM = new char[5];
    public static char[] P1MEM = new char[5], P2MEM = new char[5], GTMEM = new char[3], P5MEM = new char[5];
    public static boolean POMEMFlag = false,P1MEMFlag=false, P2MEMFlag =false, GTMEMFlag = false, dataChangeFlag=false, lineReadyFlag = false, writeModeFlag = false, P5MEMFlag = false;
    public static char[] btTxBuff = new char[20];
    public static char[] CFMEM = new char[6];
    public static char[] D0MEM = new char[5];
    public static char[] D1MEM = new char[5];
    public static int[] DEMEM = new int[4], oldDEMEM = new int[4];
    public static int[] DTMEM = new int[3] ,oldDTMEM = new int[3];

    public static boolean CONFREADFLAG = false;
    byte btTxBuf[] = new byte[16];
    String tmpRxStr="";

    String strReceived="";

    public static String strCmpDate = "0";

    public static char oldMNTMEM;
    public static char oldIPMEM1;
    public static char oldIPMEM4;
    public static char oldIPMEM6;

    private ImageView imgMainDisplay;
    private ImageView imgCarCall;
    private ImageView imgIoIndicator;
    private ImageView imgDateAndTime;
    private ImageView imgProgramCode;
    private ImageView imgDisplayPattern;
    TextView txtDate;
    TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createObj();
        generateId();
        registerEvent();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getFilesDir(), "MyFile"); // Pass getFilesDir() and "MyFile" to read file

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            device = mBluetoothAdapter.getRemoteDevice(buffer.toString());
            try{
                mChatService.connect(device);
            }
            catch (Exception e){
                tryBTConnect=tryBTConnect+1;
                //mChatService.connect(device);
                //Toast.makeText(this,"First time clicked",Toast.LENGTH_SHORT).show();

            }

            // Log.d(TAG, buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        myHandlerChk.postDelayed(checkDataContinue, 0);


    }

    private void createObj() {
        mContext = MainActivity.this;
    }


    private void generateId() {
        txtDate = (TextView) findViewById(R.id.tvDate);
        txtTime = (TextView) findViewById(R.id.tvTime);
        txtFloorNumber = (TextView) findViewById(R.id.tvFloorNumber);
        imgDown = (GifImageView) findViewById(R.id.imgArrowDown);
        imgUp = (GifImageView) findViewById(R.id.imgArrowUp);
        imgMainDisplay = (ImageView)findViewById(R.id.imgMainDisplay);
        imgCarCall = (ImageView)findViewById(R.id.imgCarCall);
        imgIoIndicator = (ImageView)findViewById(R.id.imgIoIndicatore);
        imgDateAndTime = (ImageView)findViewById(R.id.imgDateTime);
        imgProgramCode = (ImageView)findViewById(R.id.imgProgramCode);
        imgDisplayPattern = (ImageView)findViewById(R.id.imgDisplayPattern);
        tryBTConnect=0;
    }

    private void registerEvent() {
        imgMainDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, MainDisplayActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgCarCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, CarCallActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgIoIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, IOIndicatorActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, SetDateTimeActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgProgramCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, ProgramCodeActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgDisplayPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, DisplayPatternActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
    }
    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            try {
                if (isConnected()) {
                    try{
                    /*if(mConnectedDeviceName!=null){
                        setStatus("Connected to "+ mConnectedDeviceName);
                    }*/

                        menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                    }
                    catch (Exception e){
                        //Catch
                    }
                }
                else {
                    try{
                        // setStatus(R.string.title_not_connected);
                        menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
                    }
                    catch (Exception e){
                        //Catch
                    }
                }

            }catch (Exception e){
                //Catch null pointer exception
            }


           // if((oldPOMEM[0]!=POMEM[0])||((oldPOMEM[1]!=POMEM[1]))||(oldPOMEM[2]!=POMEM[2])||(oldPOMEM[3]!=POMEM[3])||(oldPOMEM[4]!=POMEM[4])){
            if((btTxBuf[0] != 0xFF)  && (lineReadyFlag) ) {

                lineReadyFlag = false;
                loopTurn++;
                switch (loopTurn) {
                    case 1:
                        if (POMEMFlag) {
                            POMEMFlag = false;

                            Log.e(TAG, "loopTurn0: " + loopTurn);
                            Log.e(TAG, "POMEMFlag: " + POMEMFlag);

//             oldPOMEM[0] = POMEM[0];
//             oldPOMEM[1] = POMEM[1];
//             oldPOMEM[2] = POMEM[2];
//             oldPOMEM[3] = POMEM[3];
//             oldPOMEM[4] = POMEM[4];
                            btTxBuff[0] = 'P';
                            btTxBuff[1] = '0';
                            btTxBuff[2] = POMEM[0];
                            btTxBuff[3] = POMEM[1];
                            btTxBuff[4] = POMEM[2];
                            btTxBuff[5] = POMEM[3];
                            btTxBuff[6] = POMEM[4];
                            dataChangeFlag = true;
                        }
                        break;
                    case 2:
                        if (P1MEMFlag) {
                            P1MEMFlag = false;
                            Log.e(TAG, "loopTurn1: " + loopTurn);
                            Log.e(TAG, "P1MEMFlag: " + P1MEMFlag);
                            btTxBuff[0] = 'P';
                            btTxBuff[1] = '1';
                            btTxBuff[2] = P1MEM[0];
                            btTxBuff[3] = P1MEM[1];
                            btTxBuff[4] = P1MEM[2];
                            btTxBuff[5] = P1MEM[3];
                            btTxBuff[6] = P1MEM[4];
                            dataChangeFlag = true;
                        }
                        break;
                    case 3:
                        if (P2MEMFlag) {
                            P2MEMFlag = false;
                            Log.e(TAG, "loopTurn1: " + loopTurn);
                            Log.e(TAG, "P1MEMFlag: " + P1MEMFlag);
                            btTxBuff[0] = 'P';
                            btTxBuff[1] = '2';
                            btTxBuff[2] = P2MEM[0];
                            btTxBuff[3] = P2MEM[1];
                            btTxBuff[4] = P2MEM[2];
                            btTxBuff[5] = P2MEM[3];
                            btTxBuff[6] = P2MEM[4];
                            dataChangeFlag = true;
                        }
                        break;
                    case 4:
                        if (CONFREADFLAG) {
                            CONFREADFLAG = false;
                            Log.e(TAG, "loopTurn1: " + loopTurn);
                            Log.e(TAG, "P1MEMFlag: " + P1MEMFlag);
                            btTxBuff[0] = 'C';
                            btTxBuff[1] = 'F';
                            btTxBuff[2] = 0;
                            btTxBuff[3] = 0;
                            btTxBuff[4] = 0;
                            btTxBuff[5] = 0;
                            btTxBuff[6] = CONFREAD;
                            dataChangeFlag = true;
                        }
                        break;
                    case 5:
                        if (GTMEMFlag) {
                            GTMEMFlag = false;
                            Log.e(TAG, "loopTurn1: " + loopTurn);
                            Log.e(TAG, "P1MEMFlag: " + P1MEMFlag);
                            btTxBuff[0] = 'G';
                            btTxBuff[1] = 'T';
                            btTxBuff[2] = GTMEM[0];
                            btTxBuff[3] = GTMEM[1];
                            btTxBuff[4] = GTMEM[2];
                            btTxBuff[5] = 0;
                            btTxBuff[6] = 0;
                            dataChangeFlag = true;
                        }
                        break;
                    case 6:
                        if (P5MEMFlag) {
                            P5MEMFlag = false;
                            Log.e(TAG, "loopTurn1: " + loopTurn);
                            Log.e(TAG, "P5MEMFlag: " + P5MEMFlag);
                            btTxBuff[0] = 'P';
                            btTxBuff[1] = '5';
                            btTxBuff[2] = P5MEM[0];
                            btTxBuff[3] = P5MEM[1];
                            btTxBuff[4] = P5MEM[2];
                            btTxBuff[5] = P5MEM[3];
                            btTxBuff[6] = P5MEM[4];
                            dataChangeFlag = true;
                        }
                        break;
                    default:
                        loopTurn = 0;
                        break;
                }

            }

            if(dataChangeFlag ){

                dataChangeFlag=false;
                btTxBuf[0] = 0x0A;
                btTxBuf[1] = (byte)btTxBuff[0];
                btTxBuf[2] = (byte)btTxBuff[1];
//                tmp[3] = hex2ascii((byte) (btTxBuff[2] / (byte)0x10));
//                tmp[4] = hex2ascii((byte) (btTxBuff[2] % (byte)0x10));
//                tmp[5] = hex2ascii((byte) (btTxBuff[3] / (byte)0x10));
//                tmp[6] = hex2ascii((byte) (btTxBuff[3] % (byte)0x10));
//                tmp[7] = hex2ascii((byte) (btTxBuff[4] / (byte)0x10));
//                tmp[8] = hex2ascii((byte) (btTxBuff[4] % (byte)0x10));
//                tmp[9] = hex2ascii((byte) (btTxBuff[5] / (byte)0x10));
//                tmp[10] = hex2ascii((byte) (btTxBuff[5] % (byte)0x10));
//                tmp[11] = hex2ascii((byte) (btTxBuff[6] / (byte)0x10));
//                tmp[12] = hex2ascii((byte) (btTxBuff[6] % (byte)0x10));
                btTxBuf[3] = hex2ascii((byte) (btTxBuff[2]>>4));
                btTxBuf[4] = hex2ascii((byte) (btTxBuff[2] &0x0F));
                btTxBuf[5] = hex2ascii((byte) (btTxBuff[3] >>4));
                btTxBuf[6] = hex2ascii((byte) (btTxBuff[3] &0x0F));
                btTxBuf[7] = hex2ascii((byte) (btTxBuff[4]>>4));
                btTxBuf[8] = hex2ascii((byte) (btTxBuff[4] &0x0F));
                btTxBuf[9] = hex2ascii((byte) (btTxBuff[5] >>4));
                btTxBuf[10] = hex2ascii((byte) (btTxBuff[5] &0x0F));
                btTxBuf[11] = hex2ascii((byte) (btTxBuff[6] >>4));
                btTxBuf[12] = hex2ascii((byte) (btTxBuff[6] &0x0F));
                btTxBuf[13] = '0';//check sum upper nibble
                btTxBuf[14] = '0';//check sum lower nibble
                btTxBuf[15] = 0x0D;

                if (isConnected()) {
                    sendMessage(btTxBuf);
                    btTxBuf[0] = (byte) 0xFF;
                    Log.e(TAG , "sent data ");
                } else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
            }

            myHandlerChk.postDelayed(this, 100);
        }

    };

    public void appendLog1(String message, boolean hexMode, boolean outgoing, boolean clean) {
        try {
            StringBuffer msg = new StringBuffer();

            msg.append(hexMode ? UtilsBluetooth.toHex1(message) : message);
            if (outgoing) msg.append("\n");

            //txtLog.append(msg);
            //  shwLog.append(msg);
            //msgAppend.append(msg);
            //msgAppendChk.append(msg);
            // Log.e(TAG, "msgAppend = "+ msgAppend.toString());
            //Log.e(TAG, "msgAppendChk = "+ msgAppendChk.toString());
            strReceived = strReceived + msg.toString();//msgAppend.toString();
            //Log.e(TAG, "strReceived = "+ strReceived);
            if (strReceived.contains("\r")) {
                if(strReceived.contains("t522")){
                    lineReadyFlag = true;
                }
                msg.setLength(0);
                processReceivedData(strReceived);
                Log.e(TAG, "strReceived = "+ strReceived);
                strReceived = "";
                //msgAppend.setLength(0);

            }
        }
        catch (Exception e)
        {
            //Not Supposed to happen.
            //throw new RuntimeException();
        }
    }


    public void processReceivedData(String strReceived) {

       /* int indexOd = strReceived.indexOf("\r");
        String temp = strReceived.substring(0, indexOd);
        Log.e(TAG, "temp = " + temp);*/

        int indexOd = strReceived.lastIndexOf("\r");
        String temp = strReceived.substring(indexOd - 16, indexOd);
        Log.e(TAG, "temp = " + temp);

        /*for(int i=0; i<=strReceievedBuffer.length; i++){
            Log.e(TAG, "strReceievedBuffer" + i + " = " + strReceievedBuffer[i]);
        }*/




        if (temp.contains("fs")) {
            int index = temp.indexOf("fs");

            String temp1 = temp.substring(index + 2, index + 4);
            int tmp = Integer.parseInt(temp1, 16);
            COUNTMEM = (char) tmp;
            //Log.e(TAG, "COUNTMEM = "+ COUNTMEM);

            temp1 = temp.substring(index + 4, index + 6);
            tmp = Integer.parseInt(temp1, 16);
            FLRNUM = (char) tmp;

            Log.e(TAG, "FLRNUM = " + FLRNUM);
            //Log.e(TAG, "FLRNUM old = "+ oldFLRNUM);
            //txtFloorNumber.setText(String.valueOf(FLRNUM));

            temp1 = (temp.substring(index + 6, index + 8));
            //temp1 = "*";
            //tmp = Integer.parseInt(temp1,16);
            //char ch = (char) tmp;
            DISCODE = temp1;
            Log.e(TAG, "DISCODE = " + tmp);

            temp1 = temp.substring(index + 8, index + 10);
            tmp = Integer.parseInt(temp1, 16);
            OUTMEM1 = ((char) tmp);
            Log.e(TAG, "OUTMEM1 = " + OUTMEM1);

            temp1 = temp.substring(index + 10, index + 12);
            tmp = Integer.parseInt(temp1, 16);
            RUNSTAT1 = (char) tmp;
            //Log.e(TAG, "RUNSTAT1 = "+ RUNSTAT1);

            temp1 = temp.substring(index + 12, index + 14);
            tmp = Integer.parseInt(temp1, 16);
            CONBITS = (char) tmp;
            //Log.e(TAG, "CONBITS = "+ CONBITS);

/*
            //String strFlr = temp.substring(indexfs + 6, indexfs + 8);
//            strFSFlrCnt = temp.substring(indexfs + 4, indexfs + 6);
            strFSFlrCnt = Integer.parseInt(temp.substring(indexfs + 4, indexfs + 6)) + "";
            strFSFlrNo = temp.substring(index + 6, indexfs + 8);

            Log.e(TAG, "strFlr = "+ strFSFlrNo);
            Log.e(TAG, "strFSFlrCnt = "+ strFSFlrCnt);
            String strDisplay = strFSFlrNo + "    " + strFSFlrCnt;
            txtFloorNumber.setText(strDisplay);
            */
        }


        if (temp.contains("cf")) {
            int index = temp.indexOf("cf");

            String temp1 = temp.substring(index + 2, index + 4);
            int tmp = Integer.parseInt(temp1, 16);
            CFMEM[0] = (char) tmp;

            temp1 = temp.substring(index + 4, index + 6);
            tmp = Integer.parseInt(temp1, 16);
            CFMEM[1] = (char) tmp;

            temp1 = temp.substring(index + 6, index + 8);
            tmp = Integer.parseInt(temp1, 16);
            CFMEM[2] = (char) tmp;

            temp1 = temp.substring(index + 8, index + 10);
            tmp = Integer.parseInt(temp1, 16);
            CFMEM[3] = (char) tmp;

            temp1 = temp.substring(index + 10, index + 12);
            tmp = Integer.parseInt(temp1, 16);
            CFMEM[4] = (char) tmp;

            temp1 = temp.substring(index + 12, index + 14);
            tmp = Integer.parseInt(temp1, 16);
            CFMEM[5] = (char) tmp;
        }


        if (temp.contains("d0")) {
            int index = temp.indexOf("d0");

            String temp1 = temp.substring(index + 2, index + 4);
            int tmp = Integer.parseInt(temp1, 16);
            D0MEM[0] = (char) tmp;

            temp1 = temp.substring(index + 4, index + 6);
            tmp = Integer.parseInt(temp1, 16);
            D0MEM[1] = (char) tmp;

            temp1 = temp.substring(index + 6, index + 8);
            tmp = Integer.parseInt(temp1, 16);
            D0MEM[2] = (char) tmp;

            temp1 = temp.substring(index + 8, index + 10);
            tmp = Integer.parseInt(temp1, 16);
            D0MEM[3] = (char) tmp;

            temp1 = temp.substring(index + 10, index + 12);
            tmp = Integer.parseInt(temp1, 16);
            D0MEM[4] = (char) tmp;

        }

        if (temp.contains("d1")) {
            int index = temp.indexOf("d1");

            String temp1 = temp.substring(index + 2, index + 4);
            int tmp = Integer.parseInt(temp1, 16);
            D1MEM[0] = (char) tmp;

            temp1 = temp.substring(index + 4, index + 6);
            tmp = Integer.parseInt(temp1, 16);
            D1MEM[1] = (char) tmp;

            temp1 = temp.substring(index + 6, index + 8);
            tmp = Integer.parseInt(temp1, 16);
            D1MEM[2] = (char) tmp;

            temp1 = temp.substring(index + 8, index + 10);
            tmp = Integer.parseInt(temp1, 16);
            D1MEM[3] = (char) tmp;

            temp1 = temp.substring(index + 10, index + 12);
            tmp = Integer.parseInt(temp1, 16);
            D1MEM[4] = (char) tmp;

        }
//        if(oldFLRNUM != FLRNUM){


        if (temp.contains("ip")) {
            int index = temp.indexOf("ip");

            String temp1 = temp.substring(index + 2, index + 4);
            int tmp = Integer.parseInt(temp1, 16);
            IPMEM1 = (char) ~tmp;

            temp1 = temp.substring(index + 4, index + 6);
            tmp = Integer.parseInt(temp1, 16);
            IPMEM2 = (char) ~tmp;

            temp1 = temp.substring(index + 6, index + 8);
            tmp = Integer.parseInt(temp1, 16);
            IPMEM3 = (char) ~tmp;

            temp1 = temp.substring(index + 8, index + 10);
            tmp = Integer.parseInt(temp1, 16);
            IPMEM4 = (char) ~tmp;

            temp1 = temp.substring(index + 10, index + 12);
            tmp = Integer.parseInt(temp1, 16);
            IPMEM5 = (char) ~tmp;

            temp1 = temp.substring(index + 12, index + 14);
            tmp = Integer.parseInt(temp1, 16);
            IPMEM6 = (char) ~tmp;
        }

        if (temp.contains("op")) {
            int index = temp.indexOf("op");

            String temp1 = temp.substring(index + 2, index + 4);
            int tmp = Integer.parseInt(temp1, 16);
            OPMEM1 = (char) ~tmp;
            Log.e(TAG, "OPMEM1:" + String.valueOf(tmp));

            temp1 = temp.substring(index + 4, index + 6);
            tmp = Integer.parseInt(temp1, 16);
            OPMEM2 = (char) ~tmp;

            temp1 = temp.substring(index + 6, index + 8);
            tmp = Integer.parseInt(temp1, 16);
            OPMEM3 = (char) ~tmp;

            temp1 = temp.substring(index + 8, index + 10);
            tmp = Integer.parseInt(temp1, 16);
            OPMEM4 = (char) ~tmp;

            temp1 = temp.substring(index + 10, index + 12);
            tmp = Integer.parseInt(temp1, 16);
            OPMEM5 = (char) ~tmp;

           /*

            Log.e(TAG, "tmp:" + String.valueOf(tmp));
            Log.e(TAG, "tmp1:" + String.valueOf(tmp1));
            Log.e(TAG, "and result:" + String.valueOf(tmp & 1));

            byte chk = (byte) (tmp1 & 3);

            switch (chk)
            {
                case 0:
                    imgUp.setVisibility(View.GONE);
                    imgDown.setVisibility(View.GONE);
                    break;
                case 1:
                    imgUp.setVisibility(View.VISIBLE);
                    imgDown.setVisibility(View.GONE);
                    break;
                case 2:
                    imgUp.setVisibility(View.GONE);
                    imgDown.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    imgUp.setVisibility(View.GONE);
                    imgDown.setVisibility(View.GONE);
                    break;

            }
            */
        }

        if (temp.contains("De")) {
            int index = temp.indexOf("De");

            String temp1 = temp.substring(index + 2, index + 4);
            DEMEM[0] = Integer.parseInt(temp1, 16);

            temp1 = temp.substring(index + 4, index + 6);
            DEMEM[1] = Integer.parseInt(temp1, 16);
            Log.e(TAG, "year: " + ((DEMEM[0] * 100) + DEMEM[1]));
            temp1 = temp.substring(index + 6, index + 8);
            DEMEM[2] = Integer.parseInt(temp1, 16);

            temp1 = temp.substring(index + 8, index + 10);
            DEMEM[3] = Integer.parseInt(temp1, 16);

        }

        if(oldDEMEM[0] != DEMEM[0] || oldDEMEM[1] != DEMEM[1] || oldDEMEM[2] != DEMEM[2] || oldDEMEM[3] != DEMEM[3]){
            oldDEMEM[0] = DEMEM[0];
            oldDEMEM[1] = DEMEM[1];
            oldDEMEM[2] = DEMEM[2];
            oldDEMEM[3] = DEMEM[3];
            String strDate = String.valueOf(DEMEM[3]) + " / " + String.valueOf(DEMEM[2]) + " / " + String.valueOf((DEMEM[0] * 100) + DEMEM[1]);
            txtDate.setText(strDate);
            Log.e(TAG, "text year: " + ((DEMEM[0] * 100) + DEMEM[1]));
        }

        if (temp.contains("Dt")) {
            int index = temp.indexOf("Dt");

            String temp1 = temp.substring(index + 2, index + 4);
            DTMEM[0] = Integer.parseInt(temp1, 16);

            temp1 = temp.substring(index + 4, index + 6);
            DTMEM[1] = Integer.parseInt(temp1, 16);

            temp1 = temp.substring(index + 6, index + 8);
            DTMEM[2] = Integer.parseInt(temp1, 16);


        }

        if(oldDTMEM[0] != DTMEM[0] || oldDTMEM[1] != DTMEM[1] || oldDTMEM[2] != DTMEM[2]){
            oldDTMEM[0] = DTMEM[0];
            oldDTMEM[1] = DTMEM[1];
            oldDTMEM[2] = DTMEM[2];
            String strTime = String.valueOf(DTMEM[0]) + " : " + String.valueOf(DTMEM[1]) + " : " + String.valueOf(DTMEM[2]);
            txtTime.setText(strTime);
        }


        if (temp.contains("mn")) {
            int index = temp.indexOf("mn");

            String temp1 = temp.substring(index + 2, index + 4);
            int tmp = Integer.parseInt(temp1, 16);
            MNTMEM = (char) tmp;
        }

//--------------------------------------------------------------
        if (oldFLRNUM != FLRNUM) {
            oldFLRNUM = FLRNUM;
            Log.e(TAG, "FLRNUM old = " + FLRNUM);
            txtFloorNumber.setText(String.valueOf(FLRNUM));
            int tmp = FLRNUM;
            String strDisplay = tmp + "    " + DISCODE;
            txtFloorNumber.setText(strDisplay);
        }
        //---------------------------------------------------------


        if (oldOPMEM5 != OPMEM5) {

            oldOPMEM5 = OPMEM5;
            // int tmp = OPMEM5;
            char chk = (char) (OPMEM5 & 3);
            Log.e(TAG, "FLR Arrow = " + chk);
            switch (chk) {
                case 0:
                    imgUp.setVisibility(View.GONE);
                    imgDown.setVisibility(View.GONE);
                    break;
                case 1:
                    imgUp.setVisibility(View.VISIBLE);
                    imgDown.setVisibility(View.GONE);
                    break;
                case 2:
                    imgUp.setVisibility(View.GONE);
                    imgDown.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    imgUp.setVisibility(View.GONE);
                    imgDown.setVisibility(View.GONE);
                    break;

            }
        }
    }


    public static boolean isConnected(){

        return (mChatService.getState() == BluetoothChatService.STATE_CONNECTED);

        /*if (mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
            return true;
        }else {
            return false;
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
       /* if (D)
            Log.e(TAG, "++ ON START ++");*/

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null)
                setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
       /* if (D)
            Log.e(TAG, "+ ON RESUME +");*/

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity
        // returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    private void setupChat() {
        //  Log.d(TAG, "setupChat()");


        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }
    @Override
    public synchronized void onPause() {
        super.onPause();
        /*if (D)
            Log.e(TAG, "- ON PAUSE -");*/
    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (D)
            Log.e(TAG, "-- ON STOP --");*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null)
            mChatService.stop();
       /* if (D)
            Log.e(TAG, "--- ON DESTROY ---");*/
    }

    private final void setStatus(int resId) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(resId);
        actionBar.show();
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subTitle);
        actionBar.show();
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    //final ActionBar actionBar = getSupportActionBar();
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            /*setStatus(getString(R.string.title_connected_to,
                                    mConnectedDeviceName));*/
                            /*setStatus(getString(R.string.title_connected_to,
                                    mConnectedDeviceName));*/
                            // menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                            setStatus("Connected to "+ mConnectedDeviceName);

                            //actionBar.setSubtitle("Connected to "+ mConnectedDeviceName);
                           /* //for AutoConnect
                            btConnect=0;
                            btConnect=btConnect+1;*/

                            // mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            //actionBar.setSubtitle(getString(R.string.title_connecting));
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                           /* Toast.makeText(getApplicationContext(),
                                    "Listening Mode",
                                    Toast.LENGTH_SHORT).show();
*/
                            if(tryBTConnect==1){
                                mChatService.connect(device);
                                tryBTConnect=0;
                                return;
                            }
                            /*if(btConnect==1){
                                mChatService.connect(device);
                            }*/
                        case BluetoothChatService.STATE_NONE:
                            /*try {
                                menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
                            }
                            catch (Exception e){
                                //
                            }*/
                            setStatus(R.string.title_not_connected);
                            //actionBar.setSubtitle(getString(R.string.title_not_connected));

                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);

                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    /*byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    txtFpi.setText(readMessage);
                   *//* mConversationArrayAdapter.add(mConnectedDeviceName + ":  "
                            + readMessage);*/
                    //Log.d(TAG,"message rx" );

                   // final String readMessage = (String) msg.obj;

                    String readMessage = (String) msg.obj;
                    if (readMessage != null) {
                        //Log.d(TAG,"readMessage " + readMessage);
                            appendLog1(readMessage, false, false, needClean);
                        readMessage="";
                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (D)
            Log.d(TAG, "onActivityResult " + resultCode);*/
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                   /* Toast.makeText(getApplicationContext(),
                            "Connection Request",
                            Toast.LENGTH_SHORT).show();*/
                    connectDevice(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    //  Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(
                DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device);
    }


    /**
     * Sends a message.
     *
     * @param message
     *            A string of text to send.
     */
    public static void sendMessage(byte[] message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            /*Toast.makeText(this.getApplicationContext(), R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();*/

            return;
        }

        // Check that there's actually something to send
        if (message.length> 0) {
            msgAppend.setLength(0);
            // Get the message bytes and tell the BluetoothConnectionRoom to write
            //byte[] send = message.getBytes();
            mChatService.write(message);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.device_control_activity, menu);
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        this.menu = menu;
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_search:
                mChatService.stop();
                Intent serverIntent = null;
                serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;

            case R.id.logout:
                //createDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
