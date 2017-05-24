package com.hnd.y_not_proto2.member;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnd.y_not_proto2.R;

/**
 * Created by Administrator on 2016-03-18.
 */
public class FriendInfo extends Activity {

    Button btn_chat, btn_call, btn_sns;
    String tel;
    public String TelNumber;
    // called when DetailsFragmentListener's view needs to be created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_info);
        // inflate DetailsFragment's layout


        btn_call = (Button) findViewById(R.id.btn_call);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_sns = (Button) findViewById(R.id.btn_sns);

        // get the EditTexts
        ImageView profileImageView = (ImageView) findViewById(R.id.friend_profile);
        TextView nameTextView = (TextView) findViewById(R.id.friend_nameTextView);
        TextView phoneTextView = (TextView) findViewById(R.id.friend_phoneTextView);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        nameTextView.setText(intent.getStringExtra("name"));
        phoneTextView.setText(intent.getStringExtra("phone"));

        TelNumber = intent.getStringExtra("phone");

        // profileImageView.setImageResource(intent.getIntExtra("profile", 0));

        btn_call.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(("tel:" + TelNumber)));
                //callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });


    }
}
