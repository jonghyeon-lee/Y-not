package com.hnd.y_not_proto2.member;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnd.y_not_proto2.R;
import com.hnd.y_not_proto2.chat.ChatActivity;

/**
 * Created by Administrator on 2016-03-18.
 */
public class MyInfoFragment extends Activity
{
    Button btn_self_chat;
    Button btn_edit;
    SharedPreferences prefs;
    // called when DetailsFragmentListener's view needs to be created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info);
        prefs = getSharedPreferences("Chat", 0);
        // inflate DetailsFragment's layout


        // get the EditTexts
        ImageView profileImageView = (ImageView)findViewById(R.id.my_profile);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView phoneTextView = (TextView) findViewById(R.id.phoneTextView);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        nameTextView.setText(intent.getStringExtra("name"));
        phoneTextView.setText(intent.getStringExtra("state"));
        profileImageView.setImageResource(intent.getIntExtra("profile", 0));
        /*
        btn_edit = (Button)findViewById(R.id.btn_edit);


        btn_edit.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MyInfoFragment.this, EditMyInfoFragment.class);
                startActivity(intent);

            }
        });*/

        btn_self_chat = (Button)findViewById(R.id.btn_self_chat);
        btn_self_chat.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("mobno", prefs.getString("REG_FROM",""));
                Intent chat = new Intent(MyInfoFragment.this, ChatActivity.class);
                chat.putExtra("INFO", args);
                chat.putExtra("ME",1);
                startActivity(chat);
            }
        });

    }



  /*
    // DialogFragment to confirm deletion of contact
    private DialogFragment confirmDelete =
            new DialogFragment()
            {
                // create an AlertDialog and return it
                @Override
                public Dialog onCreateDialog(Bundle bundle)
                {
                    // create a new AlertDialog Builder
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setTitle(R.string.confirm_title);
                    builder.setMessage(R.string.confirm_message);

                    // provide an OK button that simply dismisses the dialog
                    builder.setPositiveButton(R.string.button_delete,
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(
                                        DialogInterface dialog, int button)
                                {
                                    final MyDatabaseConnector databaseConnector =
                                            new MyDatabaseConnector(getActivity());

                                    // AsyncTask deletes contact and notifies listener
                                    AsyncTask<Long, Object, Object> deleteTask =
                                            new AsyncTask<Long, Object, Object>()
                                            {
                                                @Override
                                                protected Object doInBackground(Long... params)
                                                {
                                                    databaseConnector.deleteContact(params[0]);
                                                    return null;
                                                }

                                                @Override
                                                protected void onPostExecute(Object result)
                                                {
                                                    listener.onContactDeleted();
                                                }
                                            }; // end new AsyncTask

                                    // execute the AsyncTask to delete contact at rowID
                                    deleteTask.execute(new Long[] { rowID });
                                } // end method onClick
                            } // end anonymous inner class
                    ); // end call to method setPositiveButton

                    builder.setNegativeButton(R.string.button_cancel, null);
                    return builder.create(); // return the AlertDialog
                }
            }; // end DialogFragment anonymous inner class
            */
} // end class DetailsFragment