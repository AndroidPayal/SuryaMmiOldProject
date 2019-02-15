package com.radioknit.suryaelevatorsmmi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.radioknit.suryaelevatorsmmi.PrefUtils.PREFS_LOGIN_DATE_KEY;
import static com.radioknit.suryaelevatorsmmi.PrefUtils.PREFS_LOGIN_PASSWORD_KEY;
import static com.radioknit.suryaelevatorsmmi.PrefUtils.PREFS_LOGIN_USERNAME_KEY;

/**
 * Created by soft on 23/6/18.
 */

public class LoginActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String url;
    String strLoginStatus = "";
    EditText editTextUser;
    EditText editTextPass;
    Button buttonLogin;
    String strUser = "";
    String strPass = "";
    String strDate = "";
    String strCurDate = "";
    TextView textSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUser = (EditText)findViewById(R.id.editTextUser);
        editTextPass = (EditText)findViewById(R.id.editTextPass);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

      //  textSignUp = (TextView) findViewById(R.id.textSignUp);
        /*textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(LoginActivity.this, SignUpActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });*/

        currentDate();

        strUser= PrefUtils.getFromPrefs(LoginActivity.this, PREFS_LOGIN_USERNAME_KEY, "");
        strPass = PrefUtils.getFromPrefs(LoginActivity.this, PREFS_LOGIN_PASSWORD_KEY, "");
        strDate = PrefUtils.getFromPrefs(LoginActivity.this, PREFS_LOGIN_DATE_KEY, "");
      //  Log.e(TAG, "strDate : " + strDate);
        if(!strUser.equals("") || !strPass.equals("")){
            if(isOnline()){
                /*url= "http://theliftiot.net/SMMIClient/SMMIService/signin?email_id=" + strUser +
                        "&password=" + strPass;*/

                url= "http://117.247.90.85/SMMIClient/SMMIService/signin?email_id=" + strUser +
                        "&password=" + strPass;
                //Log.e(TAG, "URL : " + url);
                new GetStatus().execute();
            }
            else {
                long diffDays = calDays();
                if(diffDays < 2){
                    Toast.makeText(LoginActivity.this, "You are in offline mode", Toast.LENGTH_SHORT).show();
                    Intent i1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i1);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this , "Your last login is greater than 2 days...Need Internet Connection for Login", Toast.LENGTH_LONG).show();
                }
            }


        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUser = editTextUser.getText().toString().trim();
                strPass = editTextPass.getText().toString().trim();
                if(strUser.equals("") || strPass.equals("")){
                    Toast.makeText(LoginActivity.this,"Please enter valid Username/Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isOnline()) {
                    /*url = "http://theliftiot.net/SMMIClient/SMMIService/signin?email_id=" + strUser +
                            "&password=" + strPass;*/

                    url = "http://117.247.90.85/SMMIClient/SMMIService/signin?email_id=" + strUser +
                            "&password=" + strPass;
                   // Log.e(TAG, "URL : " + url);
                    new GetStatus().execute();
                }
                else {
                    Toast.makeText(LoginActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void currentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        strCurDate = sdf.format(c.getTime());
    }



    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }

    }

    public void checkConnection(){

        if(isOnline()){

            Toast.makeText(LoginActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(LoginActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();

        }

    }


    long calDays(){
        /*Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        strCurDate = sdf.format(c.getTime());
        Log.e(TAG, "Date : " + strCurDate);*/

        long valDiffDays = 0;
        String dateStart = strDate;
        //String dateStart = "06/21/2018 12:49:51";

        Date d1 = null;
        Date d2 = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(strCurDate);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            valDiffDays = diffDays;
           // Log.e(TAG, "diffDays : " + diffDays);
           // Log.e(TAG, "diffHours : " + diffHours);
           // Log.e(TAG, "diffMinutes : " + diffMinutes);
           // Log.e(TAG, "diffSeconds : " + diffSeconds);


           // System.out.print(diffDays + " days, ");
           // System.out.print(diffHours + " hours, ");
           // System.out.print(diffMinutes + " minutes, ");
           // System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return valDiffDays;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetStatus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
           // System.out.println("============================Output String is:"+jsonStr);
           // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    strLoginStatus = jsonObj.getString("login");
                   // Log.e(TAG, "strLoginStatus: " + strLoginStatus);


                } catch (final JSONException e) {
                   // Log.e(TAG, "Json parsing error: " + e.getMessage());

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            try {

                switch (strLoginStatus) {
                    case "1":
                        PrefUtils.saveToPrefs(LoginActivity.this, PREFS_LOGIN_USERNAME_KEY, strUser);
                        PrefUtils.saveToPrefs(LoginActivity.this, PREFS_LOGIN_PASSWORD_KEY, strPass);
                        PrefUtils.saveToPrefs(LoginActivity.this, PREFS_LOGIN_DATE_KEY, strCurDate);
                        Intent i1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i1);
                        finish();
                        break;
                    case "0":
                        Toast.makeText(getApplicationContext(),
                                "Incorrect Username/Password",
                                Toast.LENGTH_SHORT)
                                .show();
                        break;

                    default:
                        Toast.makeText(getApplicationContext(),
                                "Server not found",
                                Toast.LENGTH_SHORT)
                                .show();
                        break;
                }

                }catch (NullPointerException e){
                //catch null pointer exception
            }

        }

    }


}
