package com.example.lenovo.medicine2.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.lenovo.medicine2.AddMedicineActivity;
import com.example.lenovo.medicine2.MainActivity;
import com.example.lenovo.medicine2.R;

public class RatingFragment extends Fragment implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {
    EditText edtComment;
    Button btnSubmit;
    RatingBar ratingBar;
    public String rate;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rating_fragment, container, false);

        edtComment = rootView.findViewById(R.id.edtComment);
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        ratingBar = rootView.findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(this);
        btnSubmit.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        rate = String.valueOf(ratingBar.getRating());
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "Thank you for rating us..!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
