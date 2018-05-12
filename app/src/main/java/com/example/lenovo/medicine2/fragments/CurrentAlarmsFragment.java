package com.example.lenovo.medicine2.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.medicine2.DbHelper;
import com.example.lenovo.medicine2.MyCursorAdapter;
import com.example.lenovo.medicine2.R;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by LENOVO on 3/31/2018.
 */

public class CurrentAlarmsFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    MyCursorAdapter curAdapter;
    Cursor cur;
    TextView tvName, tvTime, txtEmpty;
    DbHelper dbHelper;
    Button btnDismiss;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false );
        dbHelper = new DbHelper(getActivity());
        cur = dbHelper.getAllMedicine();

        listView = (ListView)rootView.findViewById(R.id.listView);
        tvName = (TextView)rootView.findViewById(R.id.tvName);
        tvTime = (TextView)rootView.findViewById(R.id.tvTime);
        txtEmpty = (TextView)rootView.findViewById(R.id.txtEmpty);
//        txtEmpty.setVisibility(View.INVISIBLE);
        btnDismiss = (Button)rootView.findViewById(R.id.btnDismiss);

        curAdapter = new MyCursorAdapter(getActivity(), cur);
        listView.setAdapter(curAdapter);
        listView.setEmptyView(txtEmpty);


        listView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        String item = ((TextView) view.findViewById(R.id.tvName)).getText().toString();
//        MyCursorAdapter.item = item;
//        Cursor cursor = dbHelper.getMedicineId(item);
//        int id = cursor.getInt(0);
//        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), id,
//                new Intent(getActivity(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(contentIntent);
//
//        dbHelper.deleteMedicine(item);
//        cur = dbHelper.getMedicine();
//        curAdapter = new MyCursorAdapter(getActivity(), cur);
//        listView.removeViewAt(i);
    }
}
