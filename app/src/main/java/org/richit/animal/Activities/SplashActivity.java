package org.richit.animal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import org.richit.animal.R;

public class SplashActivity extends AppCompatActivity {

    //Here we set time limit for showing our app launching screen
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
        * The advantages of defining stictmode policies within your application is to
        * force you in the development phase to make your application more well behaved
        * within the device it is running on:
        * avoid running IO operations on the UI thread, avoids Activity leakages, and so on.
        * When you define these in your code, you make your application crashes if the defined
        * strict polices has been compromised, which makes you fixes the issues you've done
        * (the not well behaved approaches, like network operations on the UI thread)
        */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Here we delayed at least 3000 milli seconds to show the main content of our app
        //so that our contents from online can loaded properly
        //or our content from shared preferences cn loaded properly
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
