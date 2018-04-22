package com.homeopathy.azhar.hp.fragments.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.activity.ConsultationActivity;
import com.homeopathy.azhar.hp.activity.IndexActivity;
import com.homeopathy.azhar.hp.activity.MainActivity;

public class HomeFragment extends Fragment {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View rootView;
    TextView tvCreateConsultation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        createConsultation();
        return rootView;
    }

    private void initViews() {
        tvCreateConsultation = rootView.findViewById(R.id.tvCreateConsultation);
    }

    private void createConsultation() {
        tvCreateConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent consultationActivity = new Intent(getActivity(), ConsultationActivity.class);
                startActivity(consultationActivity);
            }
        });
    }
}
