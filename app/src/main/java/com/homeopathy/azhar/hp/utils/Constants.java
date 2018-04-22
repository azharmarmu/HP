package com.homeopathy.azhar.hp.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class Constants {

    public static final String ENV = "development";
    //public static final String ENV = "production";

    /* Firebase AUTH */
    public static final FirebaseAuth AUTH = FirebaseAuth.getInstance();

    public static int REQUEST_CAMERA = 1;
    public static int REQUEST_GALLERY = 2;

    /**/
    static final String LOGIN = "Login";
    static final String TOKEN = "token";
    public static PhoneAuthProvider.ForceResendingToken OTP_RESEND_TOKEN = null;

    public static final String DEVICE_TOKEN = "device_token";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String VERIFICATION_ID = "verification_id";

    static final String USER = "users";

    // broadcast receiver intent filters
    public static final String PUSH_NOTIFICATION = "pushNotification";

    /* Gender */
    public static String MALE = "male";
    public static String FEMALE = "female";

    /* Relations */
    public static String MYSELF = "myself";
    public static String SPOUSE = "spouse";
    public static String PARENTS = "parents";
    public static String CHILD = "child";
    public static String OTHERS = "others";

    /* Type of consultation*/
    public static String TEXT = "text";
    public static String AUDIO = "audio";

    /* type of users chat*/

    public static String assistant = "assistant";
    public static String patient = "patient";
    public static String doctor = "doctor";

    /* Message status */
    public static String SENT = "sent";
    public static String DELIVERED = "delivered";
    public static String READ = "read";

    /* Consult Status */
    public static String NEW = "new";
    public static String ONGOING = "ongoing";
    public static String Completed = "completed";
    public static String Closed = "closed";

    /* Fire store collections */
    public static String CONSULTATION = "consultations";

    /* consultation collections fields */
    public static String createdBy = "createdBy";
    public static String patientName = "patientName";
    public static String patientAge = "patientAge";
    public static String patientGender = "patientGender";
    public static String consultFor = "consultFor";
    public static String consultType = "consultType";
    public static String msgType = "msgType";
    public static String healthConcern = "healthConcern";
    public static String consultCreatedAT = "consultCreatedAT";
    public static String messageTime = "messageTime";
    public static String status = "status";

    /* consultation collections sub-collections chat */
    public static String CHATS = "chats";

    /* fields of chat sub-collections*/
    public static String from = "from";
    public static String message = "message";

    /* AM or PM */
    public static String AM = "AM";
    public static String PM = "PM";
    public static String consultationID = "consultationID";

    /* Type of message */

    public static String text = "text";
    public static String image = "image";
    public static String audio = "audio";
}
