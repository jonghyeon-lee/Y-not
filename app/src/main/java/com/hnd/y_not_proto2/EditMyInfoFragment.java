package com.hnd.y_not_proto2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-03-18.
 */
public class EditMyInfoFragment extends Activity
{

    // EditTexts for contact information

    EditText nameEditText;
    EditText phoneEditText;
    ImageView profileImageView;
    TextView nameTextView, phoneTextView;
    Button btn_save, btn_cancle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_edit);

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        phoneEditText = (EditText)findViewById(R.id.phoneEditText);

        Intent intent = getIntent();
        profileImageView = (ImageView)findViewById(R.id.edit_my_profile);
        nameTextView = (TextView) findViewById(R.id.nameLabel);
        phoneTextView = (TextView) findViewById(R.id.phoneLabel);

        btn_save = (Button)findViewById(R.id.btn_save);
        btn_cancle = (Button)findViewById(R.id.btn_cancle);

    }

}
