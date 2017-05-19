package com.devoflife.app.testdownload.Presentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.devoflife.app.testdownload.ComCons.AppConstant;
import com.devoflife.app.testdownload.ComCons.CommonFunc;
import com.devoflife.app.testdownload.R;


public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!CommonFunc.hasPermissions(this, AppConstant.APP_PERMISSIONS)) {
                requestPermissions(AppConstant.APP_PERMISSIONS, AppConstant.REQUEST_CODE.PERMISSION);
                return;
            }
        }
        gotoMain();
    }

    private void gotoMain(){
        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivityForResult(mainIntent, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppConstant.REQUEST_CODE.PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gotoMain();
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        finish();
    }
}