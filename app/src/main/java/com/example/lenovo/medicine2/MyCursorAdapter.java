package com.example.lenovo.medicine2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicine2.fragments.CurrentAlarmsFragment;

import static android.content.ContentValues.TAG;

/**
 * Created by LENOVO on 4/1/2018.
 */

public class MyCursorAdapter extends CursorAdapter {
//    public static String item;
    TextView tvName, tvDose, tvTime, tvInterval;
    DbHelper helper;
    Button btnDismiss;


    public MyCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
        helper = new DbHelper(context);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        tvName = viewGroup.findViewById(R.id.tvName);

        return LayoutInflater.from(context).inflate(R.layout.current_alarms_row, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        tvName = (TextView)view.findViewById(R.id.tvName);
        tvDose = (TextView)view.findViewById(R.id.tvDose);
        tvTime = (TextView)view.findViewById(R.id.tvTime);
        tvInterval = (TextView)view.findViewById(R.id.tvInterval);

        tvName.setText(cursor.getString(2));
        tvDose.setText(cursor.getString(3));
        tvTime.setText(cursor.getString(4));
        tvInterval.setText(cursor.getString(5));

        btnDismiss = (Button)view.findViewById(R.id.btnDismiss);
        //set a tag with the current row textView
        btnDismiss.setTag(tvName);

        btnDismiss.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                TextView tvNameCurrent = (TextView)view.getTag();
                String item = tvNameCurrent.getText().toString();
                int id = helper.getMedicineId(item);

                //deleting the alarm
                Intent intent = new Intent(view.getContext(), AlarmReceiver.class);
                PendingIntent contentIntent = PendingIntent.getBroadcast(view.getContext(), id, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(contentIntent);
                Toast.makeText(view.getContext(), "Alarm is canceled...", Toast.LENGTH_SHORT).show();

                helper.deleteMedicine(item);
                cursor.requery();
                notifyDataSetChanged();
            }
        });


    }
}
