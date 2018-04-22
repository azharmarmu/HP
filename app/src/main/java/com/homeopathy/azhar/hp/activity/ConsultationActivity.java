package com.homeopathy.azhar.hp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.utils.Constants;
import com.homeopathy.azhar.hp.utils.DialogUtils;
import com.homeopathy.azhar.hp.utils.Firebase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {


    private static final String TAG = ConsultationActivity.class.getClass().getSimpleName();

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        setToolBar();
        setRelation();
        setConsultationType();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void genderClick(View view) {
        ImageView male = findViewById(R.id.ivMale);
        ImageView female = findViewById(R.id.ivFemale);
        switch (view.getId()) {
            case R.id.ivMale:
                gender = Constants.MALE;
                male.setImageResource(R.drawable.ic_male_click);
                female.setImageResource(R.drawable.ic_female_unclick);
                break;
            case R.id.ivFemale:
                gender = Constants.FEMALE;
                male.setImageResource(R.drawable.ic_male_unclick);
                female.setImageResource(R.drawable.ic_female_click);
                break;
        }
    }

    private void setRelation() {
        Spinner spRelation = findViewById(R.id.spRelation);
        spRelation.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> relation = new ArrayList<>();
        relation.add(Constants.MYSELF);
        relation.add(Constants.SPOUSE);
        relation.add(Constants.PARENTS);
        relation.add(Constants.CHILD);
        relation.add(Constants.OTHERS);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, relation);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spRelation.setAdapter(dataAdapter);
    }

    private void setConsultationType() {
        RadioGroup rgConsultationType = findViewById(R.id.rgConsultationType);
        rgConsultationType.setOnCheckedChangeListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        consultFor = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbTextConsultation:
                consultType = Constants.TEXT;
                break;
            case R.id.rbAudioConsultation:
                consultType = Constants.AUDIO;
                break;
        }
    }

    String name = "";
    String age = "";
    String gender = "";
    String consultFor = Constants.MYSELF;
    String consultType = Constants.TEXT;
    String healthConcern = "";
    Timestamp timestamp;

    public void consultNow(View view) {
        progressBar = findViewById(R.id.progressBar);
        EditText patientName = findViewById(R.id.patientName);
        EditText patientAge = findViewById(R.id.patientAge);
        EditText patientHealthConcern = findViewById(R.id.etHealthConcern);


        name = patientName.getText().toString().trim();
        age = patientAge.getText().toString().trim();
        healthConcern = patientHealthConcern.getText().toString().trim();
        timestamp = new Timestamp(System.currentTimeMillis());

        if (!name.isEmpty() && !age.isEmpty() && !gender.isEmpty() && !consultFor.isEmpty() && !consultType.isEmpty() && !healthConcern.isEmpty()) {

            progressBar.setVisibility(View.VISIBLE);

            String result = "Name : " + name + "\nAge : " + age +
                    "\nGender : " + gender + "\nConsultFor : " + consultFor +
                    "\nConsultType : " + consultType + "\nHealth concern : " + healthConcern +
                    "\nConsultCreatedAT : " + timestamp;

            //DialogUtils.appToastLong(this, result);

            Log.e("Result", result);


            HashMap<String, Object> consultationMap = new HashMap<>();
            consultationMap.put(Constants.createdBy, Constants.AUTH.getUid());
            consultationMap.put(Constants.patientName, name);
            consultationMap.put(Constants.patientAge, age);
            consultationMap.put(Constants.patientGender, gender);
            consultationMap.put(Constants.consultFor, consultFor);
            consultationMap.put(Constants.consultType, consultType);
            consultationMap.put(Constants.healthConcern, healthConcern);
            consultationMap.put(Constants.consultCreatedAT, timestamp);
            consultationMap.put(Constants.status, Constants.NEW);

            createConsultation(consultationMap);
        } else {
            DialogUtils.appToastLong(this, "All field is mandatory");
        }

    }

    private void createConsultation(HashMap<String, Object> consultationMap) {

        CollectionReference consultations = new Firebase().consultations;
        final DocumentReference documents = consultations.document();

        documents
                .set(consultationMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        CollectionReference chats = documents.collection(Constants.CHATS);
                        prepareBasicChat(chats);
                        moveToChatScreen(documents);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


    private void prepareBasicChat(CollectionReference chats) {
        HashMap<String, Object> chatMap = new HashMap<>();

        chatMap.put(Constants.from, Constants.patient);
        chatMap.put(Constants.patientName, name);
        chatMap.put(Constants.patientAge, age);
        chatMap.put(Constants.patientGender, gender);
        chatMap.put(Constants.consultFor, consultFor);
        chatMap.put(Constants.consultType, consultType);
        chatMap.put(Constants.msgType, Constants.text);
        chatMap.put(Constants.healthConcern, healthConcern);
        chatMap.put(Constants.messageTime, timestamp);
        chatMap.put(Constants.status, Constants.SENT);

        /* Patient message - 1 */
        chats.document().set(chatMap);

        chatMap.clear(); // ---> clear map fro fresh message

        String message = "Hello " + name + ", Please hold a bit";

        chatMap.put(Constants.from, Constants.assistant);
        chatMap.put(Constants.message, message);
        chatMap.put(Constants.msgType, Constants.text);
        chatMap.put(Constants.messageTime, timestamp);
        chatMap.put(Constants.status, Constants.SENT);

        /* Nurse message - 2 */
        chats.document().set(chatMap);
    }

    private void moveToChatScreen(DocumentReference documents) {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, ChatScreenActivity.class);
        intent.putExtra(Constants.consultationID,documents.getId());
        startActivity(intent);
        finish();
    }
}
