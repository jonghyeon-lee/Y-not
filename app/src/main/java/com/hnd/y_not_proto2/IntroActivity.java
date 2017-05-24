package com.hnd.y_not_proto2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Administrator on 2016-03-19.
 */
public class IntroActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }

}

