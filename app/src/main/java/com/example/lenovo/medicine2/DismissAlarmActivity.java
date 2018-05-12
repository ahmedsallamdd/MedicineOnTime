package com.example.lenovo.medicine2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DismissAlarmActivity extends Activity implements View.OnClickListener {
    Button btnDismissAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismiss_alarm);

        btnDismissAlarm = findViewById(R.id.btnDismissAlarm);
        btnDismissAlarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        System.exit(0);
//        finish();
        AlarmReceiver.stopRingtone();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
