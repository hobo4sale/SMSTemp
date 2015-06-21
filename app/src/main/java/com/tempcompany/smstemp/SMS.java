package com.tempcompany.smstemp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;

/**
 * Created by Tim on 6/20/2015.
 */
public class SMS extends Activity {
    private Button sendBtn;
    private EditText phoneNum;

    public void sendSMS(String phoneNum, String msg) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNum, null, msg, null, null);
        return;

    }
    public void clearTxt() {

        EditText phoneNumTxt = (EditText) findViewById(R.id.phoneNumTxt);
        EditText msgTxt = (EditText) findViewById(R.id.msgTxt);
        phoneNumTxt.setText("");
        msgTxt.setText("");

    }




}
