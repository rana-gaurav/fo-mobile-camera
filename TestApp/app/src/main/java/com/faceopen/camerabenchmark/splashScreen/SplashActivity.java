package com.faceopen.camerabenchmark.splashScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.faceopen.camerabenchmark.base.AppActivity;
import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.cameraoptions.CamOptionActivity;
import com.faceopen.camerabenchmark.data.AppConstants;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SplashActivity extends AppActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = AppConstants.SPLASH_TIME_OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, CamOptionActivity.class);
                startActivity(intent);
                //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                finish();
            }
        },SPLASH_SCREEN_TIME_OUT);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}