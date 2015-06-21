package com.tempcompany.smstemp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    Button sendBtn;
    EditText phoneNumTxt;
    EditText msgTxt;

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
                if (phoneNum.length() == 10 && msg.length() > 0)
                    new SMS().sendSMS(phoneNum, msg);
                else {
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
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
}
