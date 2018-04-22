package com.homeopathy.azhar.hp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.firestore.DocumentSnapshot;
import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.utils.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by azharuddin on 3/8/17.
 */

@SuppressWarnings({"unchecked", "unused"})
public class ChatScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @SuppressWarnings({"FieldCanBeLocal"})
    private Activity activity;
    private List<DocumentSnapshot> chatDoc;

    public ChatScreenAdapter(Activity activity, List<DocumentSnapshot> chatDoc) {
        this.activity = activity;
        this.chatDoc = chatDoc;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 1:
                viewHolder = new MessageSenderViewHolder(inflater.inflate(R.layout.chat_cell_sender_message, parent, false));
                break;
            case 2:
                viewHolder = new MessageReceiverViewHolder(inflater.inflate(R.layout.chat_cell_receiver_message, parent, false));
                break;
            case 3:
                viewHolder = new MessageBOTViewHolder(inflater.inflate(R.layout.chat_cell_bot_message, parent, false));
                break;
            case 4:
                viewHolder = new ImageSenderViewHolder(inflater.inflate(R.layout.chat_cell_sender_image, parent, false));
                break;
            case 5:
                viewHolder = new ImageReceiverViewHolder(inflater.inflate(R.layout.chat_cell_receiver_image, parent, false));
                break;
            case 6:
                viewHolder = new ImageBOTViewHolder(inflater.inflate(R.layout.chat_cell_bot_image, parent, false));
                break;
            default:
                break;
        }

        //noinspection ConstantConditions
        return viewHolder;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        DocumentSnapshot chats = chatDoc.get(position);
        HashMap<String, Object> chatMap = (HashMap<String, Object>) chats.getData();

        if (chatMap != null) {

            String type = String.valueOf(chatMap.get(Constants.msgType)); // text, image, audio
            String from = String.valueOf(chatMap.get(Constants.from)); // patient, doctor, assistant

            if (type.equalsIgnoreCase(Constants.text)) { // --> text message

                if (from.equalsIgnoreCase(Constants.patient)) {  // --> patient
                    senderMessage((MessageSenderViewHolder) holder, chatMap);
                } else if (from.equalsIgnoreCase(Constants.doctor)) { // --> doctor
                    receiverMessage((MessageReceiverViewHolder) holder, chatMap);
                } else if (from.equalsIgnoreCase(Constants.assistant)) { // --> assistant
                    botMessage((MessageBOTViewHolder) holder, chatMap);
                }

            } else if (type.equalsIgnoreCase(Constants.image)) { // --> image message

                if (from.equalsIgnoreCase(Constants.patient)) { // --> patient
                    senderImage((ImageSenderViewHolder) holder, chatMap);
                } else if (from.equalsIgnoreCase(Constants.doctor)) { // --> doctor
                    receiverImage((ImageReceiverViewHolder) holder, chatMap);
                } else if (from.equalsIgnoreCase(Constants.assistant)) { // --> assistant
                    botImage((ImageBOTViewHolder) holder, chatMap);
                }

            } else if (type.equalsIgnoreCase(Constants.audio)) { // --> audio message
                // TODO: 01/05/18
            }
        }
    }

    /* text part */
    private void senderMessage(MessageSenderViewHolder holder, HashMap<String, Object> chatMap) {

        holder.senderMsg.setText(String.valueOf(chatMap.get(Constants.message)));

        /* Date & Time*/
        Date date = (Date) chatMap.get(Constants.messageTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String am_pm = cal.get(Calendar.AM_PM) == 0 ? Constants.AM : Constants.PM;

        String chatTime = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " " + am_pm;

        holder.senderMsgTime.setText(chatTime);
    }

    private void receiverMessage(MessageReceiverViewHolder holder, HashMap<String, Object> chatMap) {
        holder.receiverMsg.setText(String.valueOf(chatMap.get(Constants.message)));

        /* Date & Time*/
        Date date = (Date) chatMap.get(Constants.messageTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String am_pm = cal.get(Calendar.AM_PM) == 0 ? Constants.AM : Constants.PM;

        String chatTime = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " " + am_pm;
        holder.receiverMsgTime.setText(chatTime);
    }

    private void botMessage(MessageBOTViewHolder holder, HashMap<String, Object> chatMap) {
        holder.botMessage.setText(String.valueOf(chatMap.get(Constants.message)));
    }

    /* image part*/
    private void senderImage(final ImageSenderViewHolder holder, final HashMap<String, Object> chatMap) {

    }

    private void receiverImage(ImageReceiverViewHolder holder, HashMap<String, Object> chatMap) {

    }

    private void botImage(ImageBOTViewHolder holder, HashMap<String, Object> chatMap) {

    }


    @Override
    public int getItemCount() {
        return chatDoc.size();
    }

    private class MessageSenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg, senderMsgTime;
        ImageView senderMsgStatus;

        private MessageSenderViewHolder(View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.message_sender);
            senderMsgTime = itemView.findViewById(R.id.message_sender_time);
            senderMsgStatus = itemView.findViewById(R.id.message_sender_status);
        }
    }

    private class MessageReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg, receiverMsgTime;

        private MessageReceiverViewHolder(View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.message_receiver);
            receiverMsgTime = itemView.findViewById(R.id.message_receiver_time);
        }

    }

    private class MessageBOTViewHolder extends RecyclerView.ViewHolder {
        TextView botMessage;

        private MessageBOTViewHolder(View itemView) {
            super(itemView);
            botMessage = itemView.findViewById(R.id.message_bot);
        }
    }

    private class ImageSenderViewHolder extends RecyclerView.ViewHolder {
        ImageView senderImg, senderImgStatus;
        TextView senderImgTime;

        private ImageSenderViewHolder(View itemView) {
            super(itemView);
            senderImg = itemView.findViewById(R.id.image_sender);
            senderImgTime = itemView.findViewById(R.id.image_sender_time);
            senderImgStatus = itemView.findViewById(R.id.image_sender_status);
        }
    }

    private class ImageReceiverViewHolder extends RecyclerView.ViewHolder {
        ImageView receiverImg;
        TextView receiverImgTime;

        private ImageReceiverViewHolder(View itemView) {
            super(itemView);
            receiverImg = itemView.findViewById(R.id.image_sender);
            receiverImgTime = itemView.findViewById(R.id.image_receiver_time);
        }
    }

    private class ImageBOTViewHolder extends RecyclerView.ViewHolder {
        TextView botMessage;

        private ImageBOTViewHolder(View itemView) {
            super(itemView);
            botMessage = itemView.findViewById(R.id.message_bot);
        }
    }


    @Override
    public int getItemViewType(int position) {

        DocumentSnapshot chats = chatDoc.get(position);
        HashMap<String, Object> chatMap = (HashMap<String, Object>) chats.getData();

        try {
            if (chatMap != null) {

                String type = String.valueOf(chatMap.get(Constants.msgType)); // text, image, audio
                String from = String.valueOf(chatMap.get(Constants.from)); // patient, doctor, assistant

                if (type.equalsIgnoreCase(Constants.text)) {
                    if (from.equalsIgnoreCase(Constants.patient)) {
                        return 1;
                    } else if(from.equalsIgnoreCase(Constants.doctor)) {
                        return 2;
                    }else if(from.equalsIgnoreCase(Constants.assistant)){
                        return 3;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

}
