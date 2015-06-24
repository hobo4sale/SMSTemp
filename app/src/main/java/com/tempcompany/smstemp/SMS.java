package com.tempcompany.smstemp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;

/**
 * Created by Tim on 6/20/2015.
 */
public class SMS extends Activity {
    private static final String SENT = "sent";
    private static final String DELIVERED = "delivered";
    private String toastMessage = "";

    public String mSendSMS(String phoneNum, String msg, Context context) {

        try{
            Intent sentIntent = new Intent(SENT);
            Intent deliveryIntent = new Intent(DELIVERED);

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
                }
            }, new IntentFilter(SENT));

            registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                   toastMessage = "Delivered";
                }

            }, new IntentFilter(DELIVERED));

      /*Send SMS*/
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, msg, sentPI,
                    deliverPI);
        } catch (Exception ex) {
            Log.d("Toast Message ", toastMessage);
            toastMessage = ex.getMessage().toString();
            ex.printStackTrace();
        }

        return toastMessage;

    }
    public void mClearTxt() {
        EditText msgTxt = (EditText) findViewById(R.id.msgTxt);
        msgTxt.setText("");

    }




}
