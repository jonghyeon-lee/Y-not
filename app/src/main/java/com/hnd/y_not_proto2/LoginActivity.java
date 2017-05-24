package com.hnd.y_not_proto2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-03-18.
 */
public class LoginActivity extends FragmentActivity {
    SharedPreferences prefs;
    EditText name, mobno;
    Button login;
    List<NameValuePair> params;
    ProgressDialog register_progress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.login_fragment, container, false);
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("Chat", 0);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.name);
        mobno = (EditText) findViewById(R.id.mobno);
        login = (Button) findViewById(R.id.log_btn);
        register_progress = new ProgressDialog(this);
        register_progress.setMessage(getString(R.string.in_register));
        register_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        register_progress.setIndeterminate(true);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_progress.show();
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("REG_FROM", mobno.getText().toString());
                edit.putString("FROM_NAME", name.getText().toString());
                edit.commit();
                new Login().execute();
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.backkey_title)
                        .setMessage(R.string.backkey_content)
                        .setPositiveButton(R.string.backkey_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton(R.string.backkey_no, null).show();
                return false;
            default:
                return false;
        }
    }

    private class Login extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser json = new JSONParser();
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name.getText().toString()));
            params.add(new BasicNameValuePair("mobno", mobno.getText().toString()));
            params.add((new BasicNameValuePair("reg_id",prefs.getString("REG_ID",""))));
            //Log.e("TAG","name:"+ name.getText().toString() +", mobno:" + mobno.getText().toString()+ ", reg_id:"+prefs.getString("REG_ID",""));
            JSONObject jObj = json.getJSONFromUrl("http://horsendogs.cloudapp.net:80/login",params);
            return jObj;

        }
        @Override
        protected void onPostExecute(JSONObject json) {
            register_progress.dismiss();
            try {
                String res = json.getString("response");
                if(res.equals("Sucessfully Registered")) {
                    /*Fragment reg = new UserFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, reg);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();*/
                    Toast.makeText(getApplication(), res, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplication(), res, Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}