package com.homeopathy.azhar.hp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.adapter.ChatScreenAdapter;
import com.homeopathy.azhar.hp.adapter.ConsultationListAdapter;
import com.homeopathy.azhar.hp.utils.Constants;
import com.homeopathy.azhar.hp.utils.Firebase;

import java.util.List;
import java.util.Objects;


public class ChatScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String consultationID = Objects.requireNonNull(bundle.get(Constants.consultationID)).toString();
            getChat(consultationID);
        }
    }

    private void getChat(String consultationID) {
        final CollectionReference chats = new Firebase().consultations
                .document(consultationID)
                .collection(Constants.CHATS);

        Query queryFirst = chats
                .orderBy(Constants.messageTime, Query.Direction.DESCENDING)
                .limit(50);

        queryFirst.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {


                        List<DocumentSnapshot> document = documentSnapshots.getDocuments();
                        populateView(document);

                        // Get the last visible document
                        DocumentSnapshot lastVisible = document.get(documentSnapshots.size() - 1);

                        // Construct a new query starting at this document,
                        // get the next 25 cities.

                        //noinspection unused
                        Query queryNext = chats
                                .whereEqualTo(Constants.createdBy, Constants.AUTH.getUid())
                                .startAfter(lastVisible)
                                .limit(25);

                        // Use the query for pagination

                    }
                });
    }

    private void populateView(List<DocumentSnapshot> chatDocument) {
        RecyclerView.Adapter adapter = new ChatScreenAdapter(this, chatDocument);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.removeAllViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void sendMessage(View view) {
    }
}
