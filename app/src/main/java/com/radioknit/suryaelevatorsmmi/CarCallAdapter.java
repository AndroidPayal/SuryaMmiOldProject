package com.radioknit.suryaelevatorsmmi;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radioknit.suryaelevatorsmmi.Const;
import com.radioknit.suryaelevatorsmmi.CustomCalculate;
import com.radioknit.suryaelevatorsmmi.R;
import com.radioknit.suryaelevatorsmmi.Utils;

import static com.radioknit.suryaelevatorsmmi.CarCallActivity.showState;
import static com.radioknit.suryaelevatorsmmi.CarCallActivity.showStateDown;
import static com.radioknit.suryaelevatorsmmi.CarCallActivity.showStateUp;
import static com.radioknit.suryaelevatorsmmi.MainActivity.GTMEM;
import static com.radioknit.suryaelevatorsmmi.MainActivity.GTMEMFlag;
import static com.radioknit.suryaelevatorsmmi.MainActivity.isConnected;
import static com.radioknit.suryaelevatorsmmi.MainActivity.sendMessage;


public class CarCallAdapter extends BaseAdapter {

    private static final String TAG = "CarCallIndicator";

    private Context mContext;
    protected ViewHolder mViewHolder;
    private View view;
    private LayoutInflater layoutInflater;
    private String cop1Calls;
    private int callIndex;
    private CarCallIndicatorSignalListner mCarCallIndicatorSignalListner;
    private String strUpDnCalls;
    private int floorNo;
    public static TextView textViewStateAll[]=new TextView[8];
    public static ImageView imageViewUpAll[] = new ImageView[8];
    public static ImageView imageViewDownAll[] = new ImageView[8];
    private Drawable drawableSel = null, drawableNotSel = null;

    public interface customListener {

    }
    public CarCallAdapter(Context context, String cop1Calls , CarCallIndicatorSignalListner carCallIndicatorSignalListner){
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.cop1Calls = cop1Calls;
        mCarCallIndicatorSignalListner = carCallIndicatorSignalListner;

    }


    class ViewHolder {

        TextView txtFloorNumber;
        ImageView imgUp;
        ImageView imgDown;
        LinearLayout llItem_call;
    }

    @Override
    public int getCount() {
        return Const.NO_OF_FLOORS;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder ;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_calles, null);
            viewHolder = new ViewHolder();
            viewHolder.txtFloorNumber = (TextView) convertView.findViewById(R.id.tv_call_item_floor_no);
            viewHolder.imgUp = (ImageView) convertView.findViewById(R.id.img_calls_up_indicator);
            viewHolder.imgDown = (ImageView) convertView.findViewById(R.id.img_calls_dn_indicator);
            viewHolder.llItem_call = (LinearLayout) convertView.findViewById(R.id.llItems_calls);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        drawableSel = mContext.getResources().getDrawable(R.drawable.circular_text_view_selected);
        drawableNotSel=mContext.getResources().getDrawable(R.drawable.circular_text_view);
        viewHolder.txtFloorNumber.setText(""+(7-(position)));
        textViewStateAll[position]= viewHolder.txtFloorNumber;
        imageViewDownAll[position]= viewHolder.imgDown;
        imageViewUpAll[position]= viewHolder.imgUp;

        try {

            imageViewUpAll[0].setVisibility(View.INVISIBLE);
            imageViewDownAll[7].setVisibility(View.INVISIBLE);
        }catch (Exception e){
            //Catch exception
        }

        if(showState[position]==0){
            textViewStateAll[position].setBackground(drawableNotSel);
        }
        else if(showState[position]==1){
            textViewStateAll[position].setBackground(drawableSel);
        }

        if(showStateUp[position]==0){
            imageViewUpAll[position].setImageResource(R.drawable.up_arraow);
        }
        else if(showStateUp[position]==1){
            imageViewUpAll[position].setImageResource(R.drawable.up_green);
        }

        if(showStateDown[position]==0){
            imageViewDownAll[position].setImageResource(R.drawable.down_arr);
        }
        else if(showStateDown[position]==1){
            imageViewDownAll[position].setImageResource(R.drawable.down_green);
        }

        //for holding the position on selected item
        // for maintaining the car call state.

//        if((15-callIndex) == position){
//            Log.e(TAG, "Floor hilight = "+ (15- callIndex));
//            holder.txtFloorNumber.setBackgroundColor(mContext.getResources().getColor(R.color.red));
//        }
        if(Utils.isObjNotNull(cop1Calls)) {
            if (Utils.isStringNotNull(cop1Calls)) {
                if (cop1Calls.charAt(0) == '1') {
                    callIndex = 8;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }

                if (cop1Calls.charAt(1) == '1') {
                    callIndex = 7;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(2) == '1') {
                    callIndex = 6;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(3) == '1') {
                    callIndex = 5;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(4) == '1') {
                    callIndex = 4;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(5) == '1') {
                    callIndex = 3;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(6) == '1') {
                    callIndex = 2;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(7) == '1') {
                    callIndex = 1;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
            }
        }

        viewHolder.txtFloorNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    /*viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    mCarCallIndicatorSignalListner.sendCarCallIndicatorSignal(position);*/
//                    viewHolder.txtFloorNumber.setTag(position);
                //callLopCop((7-position),1);
                int flrNum = (7-position);
                switch (flrNum){
                    case 0:
                        GTMEM[0] = 1;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 1:
                        GTMEM[0] = 2;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 2:
                        GTMEM[0] = 4;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 3:
                        GTMEM[0] = 8;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 4:
                        GTMEM[0] = 16;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 5:
                        GTMEM[0] = 32;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 6:
                        GTMEM[0] = 64;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 7:
                        GTMEM[0] = 128;
                        GTMEM[1] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                }
                GTMEMFlag = true;
            }
        });

        viewHolder.imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCarCallIndicatorSignalListner.sendUpCallIndicatorSignal(position);
                //callLopCop((7-position),2);
                int flrNum = (7-position);
                switch (flrNum){
                    case 0:
                        GTMEM[1] = 1;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 1:
                        GTMEM[1] = 2;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 2:
                        GTMEM[1] = 4;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 3:
                        GTMEM[1] = 8;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 4:
                        GTMEM[1] = 16;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 5:
                        GTMEM[1] = 32;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 6:
                        GTMEM[1] = 64;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                    case 7:
                        GTMEM[1] = 128;
                        GTMEM[0] = 0;
                        GTMEM[2] = 0;
                        GTMEMFlag = true;
                        break;
                }
                GTMEMFlag = true;

            }
        });

        viewHolder.imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCarCallIndicatorSignalListner.sendDnCallIndicatorSignal(position);
                //callLopCop((7-position),4);

                int flrNum = (7-position);
                switch (flrNum){

                    case 0:
                        GTMEM[2] = 1;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;
                    case 1:
                        GTMEM[2] = 2;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;
                    case 2:
                        GTMEM[2] = 4;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;
                    case 3:
                        GTMEM[2] = 8;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;
                    case 4:
                        GTMEM[2] = 16;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;
                    case 5:
                        GTMEM[2] = 32;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;
                    case 6:
                        GTMEM[2] = 64;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;
                    case 7:
                        GTMEM[2] = 128;
                        GTMEM[0] = 0;
                        GTMEM[1] = 0;
                        GTMEMFlag = true;
                        break;

                }
                GTMEMFlag = true;
            }
        });

        showUpDnCalls(viewHolder,strUpDnCalls, position);

        return convertView;
    }

    private void showUpDnCalls(ViewHolder holder,String strUpDnCalls, int position) {

        if(Utils.isObjNotNull(strUpDnCalls)) {
            if (strUpDnCalls.charAt(7) == '1') {
                if (floorNo == position) {
                    holder.imgDown.setImageResource(R.drawable.down_black_arraw);
                }
            }
            if (strUpDnCalls.charAt(6) == '1') {
                if (floorNo == position) {
                    holder.imgUp.setImageResource(R.drawable.up_black_arrow);
                }
            }
        }
    }

    public interface  CarCallIndicatorSignalListner{
        void sendCarCallIndicatorSignal(int position);
        void sendUpCallIndicatorSignal(int position);
        void sendDnCallIndicatorSignal(int position);
    }


    public void callLopCop(int flrNo, int placeCall) {

        int a1 = 18;
        int a2 = 65;
        int a3 = flrNo;
        int a4 = placeCall;
        int a5 = 67;
        int a6 = 76;
        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
        String strChkSum= CustomCalculate.calculateChkSum(sendValChkSum);
        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
        asciiString = asciiString + strChkSum + "\r";
        //  Log.e(TAG, "asciiString = "+ asciiString);
        if(isConnected()) {
            sendMessage(asciiString.getBytes());
        }
    }


    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

}
