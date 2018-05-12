package com.example.lenovo.medicine2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddMedicineActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText editMed, editDose, editInterval;
    public TextView txtTime;
    Button btnTime, btnSave, btnCancel;
//    Button btnDate;
//    public TextView txtDate
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    Calendar cal;
    public int hour, minute;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    DbHelper helper;
    private static Context context;
    public static Context getAppContext(){
        return AddMedicineActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        AddMedicineActivity.context = getApplicationContext();

        helper = new DbHelper(this);
//        db = openOrCreateDatabase("Medicine Alarms", MODE_PRIVATE, null);
        cal = Calendar.getInstance();

        editMed = findViewById(R.id.editMed);
        editDose = findViewById(R.id.editDose);
        editInterval = findViewById(R.id.editInterval);
        txtTime = findViewById(R.id.txtTime);
//        txtDate = findViewById(R.id.txtDate);
        btnTime = findViewById(R.id.btnTime);
//        btnDate = findViewById(R.id.btnDate);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        spinner = findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.interval,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        btnTime.setOnClickListener(this);
//        btnDate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==btnTime){
            DialogFragment dialogFragment = new TimePickerFragment();
            dialogFragment.show(getFragmentManager(),"timePicker");

        }
//        if (view==btnDate){
//            DialogFragment newFragment = new DatePickerFragment();
//            newFragment.show(getFragmentManager(),"datePicker");
//
//        }
        if (view==btnSave){
            if (editMed.getText().toString().isEmpty()==false && editDose.getText().toString().isEmpty()==false
                    && editInterval.getText().toString().isEmpty()==false) {

                //create the alarm
                alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(this, AlarmReceiver.class);
                i.putExtra("Medicine name", editMed.getText().toString());
                final int id = (int) (cal.getTimeInMillis() / 100000);
                i.putExtra("id", id);
                pendingIntent = PendingIntent.getBroadcast(this, id, i, PendingIntent.FLAG_UPDATE_CURRENT);

                long interval = 0;
                //check the spinner value and set the alarm
                if (spinner.getSelectedItem().toString().equals("Hours")) {
                    interval = Integer.parseInt(editInterval.getText().toString()) * 60 * 60 * 1000;
                } else if (spinner.getSelectedItem().toString().equals("Minutes")) {
                    interval = Integer.parseInt(editInterval.getText().toString()) * 60 * 1000;
                }

                if(cal.getTimeInMillis() < System.currentTimeMillis()){
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, pendingIntent);

                //insert the alarm into the database
                 helper.addMedicine(id,editMed.getText().toString(), editDose.getText().toString(), txtTime.getText().toString(),
                        String.valueOf(interval), "Running");

                Toast.makeText(this, "Alarm Added", Toast.LENGTH_SHORT).show();

                //grab the user input and show it in main activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "Complete All Fields", Toast.LENGTH_LONG).show();
            }

        }
        if (view==btnCancel){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }
    @SuppressLint("ValidFragment")
    public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = String.valueOf(hourOfDay) + " : " + String.valueOf(minute);
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, cal.get(Calendar.SECOND));
            txtTime.setText(time);

        }
    }
//    @SuppressLint("ValidFragment")
//    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//
//            return new DatePickerDialog(getActivity(), this, year, month, day);
//        }
//
//        @Override
//        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//            String date = String.valueOf(day) + " / " + String.valueOf(month)+ " / " + String.valueOf(year);
//            txtDate.setText(date);
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
