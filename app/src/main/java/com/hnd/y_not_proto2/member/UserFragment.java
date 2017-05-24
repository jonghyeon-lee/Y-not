package com.hnd.y_not_proto2.member;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hnd.y_not_proto2.JSONParser;
import com.hnd.y_not_proto2.R;
import com.hnd.y_not_proto2.chat.ChatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016-03-18.
 */
public class UserFragment extends Fragment {
    ListView mLv_member;
    ArrayList<HashMap<String, String>> users = new ArrayList<HashMap<String, String>>();
    Button logout;
    List<NameValuePair> params;
    SharedPreferences prefs;
    ListAdapter adapter;
    ProgressDialog logout_progress;
    ImageView refresh;
    ProgressDialog sync_progress;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.user_fragment, container, false);
        prefs = getActivity().getSharedPreferences("Chat", 0);
        sync_progress = new ProgressDialog(getActivity());
        sync_progress.setMessage(getString(R.string.in_register));
        sync_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sync_progress.setIndeterminate(true);
        mLv_member = (ListView)view.findViewById(R.id.listView);
        refresh = (ImageView)view.findViewById(R.id.refresh);

        adapter = new SimpleAdapter(getActivity(),null,
                R.layout.user_list_single,
                new String[] { "name","mobno" }, new int[] {
                R.id.name, R.id.mobno});
        /*adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mLv_member.setSelection(adapter.getCount() - 1);
            }
        });*/
        /*m_AACcAdapter.registerDataSetObserver(new DataSetObserver(){
            @Override
            public void onChanged(){
                super.onChanged();
                mlv_ChatList.setSelection(m_AACcAdapter.getCount() - 1);
            }
        });*/
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync_progress.show();
                new Load().execute();
            }
        });
        /*if(!prefs.getString("REG_ID", "").isEmpty()) {
            new Load().execute();
        }*/

        return view;
    }


    private class Load extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONParser json = new JSONParser();
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobno", prefs.getString("REG_FROM","")));
            return json.getJSONArray("http://horsendogs.cloudapp.net:80/getuser",params);


        }
        @Override
        protected void onPostExecute(JSONArray json) {
            for(int i = 0; i < json.length(); i++){
                JSONObject c = null;
                try {
                    c = json.getJSONObject(i);
                    String name = c.getString("name");
                    String mobno = c.getString("mobno");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", name);
                    map.put("mobno", mobno);
                    users.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter = new SimpleAdapter(getActivity(), users,
                    R.layout.user_list_single,
                    new String[] { "name","mobno" }, new int[] {
                    R.id.name, R.id.mobno});
            mLv_member.setAdapter(adapter);
            mLv_member.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Bundle args = new Bundle();
                    args.putString("mobno", users.get(position).get("mobno"));
                    Intent chat = new Intent(getActivity(), ChatActivity.class);
                    chat.putExtra("INFO", args);
                    chat.putExtra("ME",0);
                    startActivity(chat);
                }
            });
            sync_progress.dismiss();
        }
    }
}