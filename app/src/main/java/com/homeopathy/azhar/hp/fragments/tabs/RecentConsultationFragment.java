package com.homeopathy.azhar.hp.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.adapter.ConsultationListAdapter;
import com.homeopathy.azhar.hp.utils.Constants;
import com.homeopathy.azhar.hp.utils.Firebase;

import java.util.List;

public class RecentConsultationFragment extends Fragment {
    public static RecentConsultationFragment newInstance() {
        return new RecentConsultationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recent_consultation, container, false);
        getConsultationList();
        return rootView;
    }

    private void getConsultationList() {
        final CollectionReference consultations = new Firebase().consultations;

        Query queryFirst = consultations
                .whereEqualTo(Constants.createdBy, Constants.AUTH.getUid())
                .limit(25);

        queryFirst.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {


                        List<DocumentSnapshot> document = documentSnapshots.getDocuments();
                        if(document.size()>0) {

                            populateView(document);

                            // Get the last visible document
                            DocumentSnapshot lastVisible = document.get(documentSnapshots.size() - 1);

                            // Construct a new query starting at this document,
                            // get the next 25 cities.

                            //noinspection unused
                            Query queryNext = consultations
                                    .whereEqualTo(Constants.createdBy, Constants.AUTH.getUid())
                                    .startAfter(lastVisible)
                                    .limit(25);

                            // Use the query for pagination

                        }

                    }
                });
    }

    private void populateView(List<DocumentSnapshot> consultationDocument) {
        RecyclerView.Adapter adapter = new ConsultationListAdapter(getActivity(), consultationDocument);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.removeAllViews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
