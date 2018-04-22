package com.homeopathy.azhar.hp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.activity.ChatScreenActivity;
import com.homeopathy.azhar.hp.utils.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by azharuddin on 3/8/17.
 */

@SuppressWarnings("unchecked")
public class ConsultationListAdapter extends RecyclerView.Adapter<ConsultationListAdapter.MyViewHolder> {
    private Activity activity;
    private List<DocumentSnapshot> consultationDoc;

    public ConsultationListAdapter(Activity activity, List<DocumentSnapshot> consultationDoc) {
        this.activity = activity;
        this.consultationDoc = consultationDoc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_consultation, parent, false);

        return new ConsultationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final DocumentSnapshot consultation = consultationDoc.get(position);
        HashMap<String, Object> consultationMap = (HashMap<String, Object>) consultation.getData();
        if (consultationMap != null) {

            holder.consultationName.setText(consultationMap.get(Constants.healthConcern).toString());
            holder.consultationLastMsg.setText("");

            /* Date & Time*/
            Date date = (Date) consultationMap.get(Constants.consultCreatedAT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String am_pm = cal.get(Calendar.AM_PM) == 0 ? Constants.AM : Constants.PM;

            String consultTime = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " " + am_pm;
            holder.consultationTime.setText(consultTime);

            /* Consultation Type */
            String consultationType = consultationMap.get(Constants.consultType).toString();
            if (consultationType.equals(Constants.TEXT)) {
                holder.consultationType.setImageResource(R.drawable.ic_text);
            } else if (consultationType.equals(Constants.AUDIO)) {
                holder.consultationType.setImageResource(R.drawable.ic_audio);
            }

            /* On click */
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ChatScreenActivity.class);
                    intent.putExtra(Constants.consultationID,consultation.getId());
                    activity.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return consultationDoc.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView consultationName, consultationLastMsg, consultationTime;
        ImageView consultationType;

        MyViewHolder(View itemView) {
            super(itemView);
            consultationName = itemView.findViewById(R.id.tvConsultationName);
            consultationLastMsg = itemView.findViewById(R.id.tvConsultLastMsg);
            consultationTime = itemView.findViewById(R.id.tvConsultTime);
            consultationType = itemView.findViewById(R.id.ivConsultType);
        }
    }
}
