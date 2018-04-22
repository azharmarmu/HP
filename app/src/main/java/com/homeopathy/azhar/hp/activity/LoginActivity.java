package com.homeopathy.azhar.hp.activity;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.utils.DialogUtils;
import com.homeopathy.azhar.hp.utils.FirebasePhoneAuthentication;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements Serializable, PermissionListener, PermissionRequestErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void nextClick(View view) {
        Dexter.withActivity(this)
                .withPermission(
                        Manifest.permission.RECEIVE_SMS)
                .withListener(this)
                .check();
    }

    private void validatePhoneNumber() {
        TextView phoneNumber = findViewById(R.id.loginPhoneNumber);
        String phone = "+91" + phoneNumber.getText().toString();
        if (!phone.isEmpty()) {
            FirebasePhoneAuthentication.sendVerificationCode(LoginActivity.this, phone);
        } else {
            DialogUtils.appToastShort(getApplicationContext(), "Enter valid mobile number");
        }
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        validatePhoneNumber();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        validatePhoneNumber();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        token.continuePermissionRequest();
    }

    @Override
    public void onError(DexterError error) {
        Log.e("Dexter", "There was an error: " + error.toString());
    }
}
