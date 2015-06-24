package com.tempcompany.smstemp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button sendBtn;
    private EditText phoneNumTxt;
    private EditText msgTxt;
    private String toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        phoneNumTxt = (EditText) findViewById(R.id.phoneNumTxt);
        msgTxt = (EditText) findViewById(R.id.msgTxt);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNum = phoneNumTxt.getText().toString();
                String msg = msgTxt.getText().toString();
                //shouldnt be able to press button if no message
                if (new Utilities().isValidNumber(phoneNum) && msg.length() > 0) {
                    phoneNum = phoneNum.replaceAll("/[^0-9]/g", "");
                    Toast.makeText(getBaseContext(),
                            sendSMS(phoneNum, msg, getApplicationContext()),
                            Toast.LENGTH_SHORT).show();
                }
                    //String toastMsg = new SMS().mSendSMS(phoneNum, msg);

                else {

                    Toast.makeText(getBaseContext(),
                           "Please enter a valid phone number and a message",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //who made this?? IT SUX
    public String sendSMS(String phoneNum, String msg, Context context) {
        try{
            Intent sentIntent = new Intent("sent");
            Intent deliveryIntent = new Intent("delivered");

            PendingIntent sentPI = PendingIntent.getBroadcast(
                    context, 0, sentIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent deliverPI = PendingIntent.getBroadcast(
                    context, 0, deliveryIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent)   {
                    String result = "";

                    switch (getResultCode()) {

                        case Activity.RESULT_OK:
                            result = "Transmission successful";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            result = "Transmission failed";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            result = "Radio off";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            result = "No PDU defined";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            result = "No service";
                            break;
                    }
                    Toast.makeText(getApplicationContext(), result,
                            Toast.LENGTH_LONG).show();
                }
            }, new IntentFilter("sent"));

            registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    toastMessage = "Delivered";
                }

            }, new IntentFilter("delivered"));

      /*Send SMS*/
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, msg, sentPI,
                    deliverPI);
        } catch (Exception ex) {
            toastMessage = ex.getMessage().toString();
            ex.printStackTrace();
        }

        return toastMessage;

    }

}
