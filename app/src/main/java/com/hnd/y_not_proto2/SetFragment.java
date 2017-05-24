package com.hnd.y_not_proto2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-03-24.
 */
public class SetFragment extends Fragment {
    private Button logout;
    List<NameValuePair> params;
    SharedPreferences prefs;
    ListAdapter adapter;
    ProgressDialog logout_progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.set_fragment, container, false);
        prefs = getActivity().getSharedPreferences("Chat", 0);
        logout_progress = new ProgressDialog(getActivity());
        logout_progress.setMessage(getString(R.string.in_logout));
        logout_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        logout_progress.setIndeterminate(true);
        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Logout().execute();
                logout_progress.show();
            }
        });
        return view;
    }
    private class Logout extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser json = new JSONParser();
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobno", prefs.getString("REG_FROM","")));
            JSONObject jObj = json.getJSONFromUrl("http://horsendogs.cloudapp.net:80/logout",params);

            return jObj;
        }
        @Override
        protected void onPostExecute(JSONObject json) {

            String res = null;
            try {
                res = json.getString("response");
                Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
                if(res.equals("Removed Sucessfully")) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("REG_FROM", "");
                    edit.commit();
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    logout_progress.dismiss();
                    startActivity(login);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}
