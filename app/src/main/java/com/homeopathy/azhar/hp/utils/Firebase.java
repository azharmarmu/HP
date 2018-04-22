package com.homeopathy.azhar.hp.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


/**
 * Created by azharuddin on 4/8/17.
 */

public class Firebase {

    private final FirebaseDatabase dbRT = FirebaseDatabase.getInstance();
    private final FirebaseFirestore dbFS = FirebaseFirestore.getInstance();

    /* RealTime */
    private final DatabaseReference ENVIRONMENT = dbRT.getReference(Constants.ENV);
    public final DatabaseReference userListDBRef = ENVIRONMENT.child(Constants.USER);

    /* Fire Store */
    public final CollectionReference consultations = dbFS.collection(Constants.CONSULTATION);

    /* FireStore Offline */
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build();

    public Firebase() {
        dbFS.setFirestoreSettings(settings);
    }

}
