package com.hnd.y_not_proto2.sns_unknown;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hnd.y_not_proto2.JSONParser;
import com.hnd.y_not_proto2.R;
import com.hnd.y_not_proto2.sns.CardViewActivity;
import com.hnd.y_not_proto2.sns.SNSItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016-03-23.
 */
public class SNSFragment_UK extends Fragment {
    //MyActivity 시작
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SNSItem> myDataset;
    ArrayList<HashMap<String, String>> card = new ArrayList<HashMap<String, String>>();
    Button refresh,logout;
    List<NameValuePair> params;
    SharedPreferences prefs;



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.sns_fragment, container, false);
        super.onCreate(savedInstanceState);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to imprve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new SNSAdapter(myDataset, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //cardview item 추가
        myDataset.add(new SNSItem(getString(R.string.text1), getString(R.string.c_user), getString(R.string.c_cntgood), getString(R.string.c_cntcomment), R.mipmap.insideout));
        myDataset.add(new SNSItem(getString(R.string.text2), getString(R.string.c_user), getString(R.string.c_cntgood), getString(R.string.c_cntcomment),R.mipmap.mini));
        myDataset.add(new SNSItem(getString(R.string.text3), getString(R.string.c_user), getString(R.string.c_cntgood), getString(R.string.c_cntcomment), R.mipmap.toystory));

        return view;
    }
    //서버
    private class CardUpadate extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground (String... args) {
            JSONParser json = new JSONParser();
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("reg_id", prefs.getString("CARD","")));
            return json.getJSONArray("http://horsendogs.cloudapp.net:80/getuser",params);
        }
        @Override
        protected void onPostExecute(JSONArray json) {
            for(int i = 0; i < json.length(); i++){
                JSONObject c = null;
                try {
                    c = json.getJSONObject(i);
                    //프로필사진받아오기, 게시물사진 받아오기
                    String name = c.getString("name");
                    String symbol_conts = c.getString("symbol_conts");
                    int favs_cnt = c.getInt("favs_cnt");//댓글수도 있어야함.
                    int com_cnt = c.getInt("com_cnt");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", name);
                    map.put("symbol_conts",symbol_conts);
                    map.put("favs_cnt",String.valueOf(favs_cnt));
                    map.put("com_cnt",String.valueOf(com_cnt));
                    card.add(map);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            ListAdapter adapter = new SimpleAdapter(getActivity(), card,
                    R.layout.sns_fragment,
                    new String[] { "name","symbol_conts","favs_cnt","com_cnt" }, new int[] {
                    R.id.textview_name, R.id.textview,R.id.cnt_good,R.id.cnt_comment});
        }


    }
    class SNSAdapter extends RecyclerView.Adapter<SNSAdapter.ViewHolder> {
        private ArrayList<SNSItem> mDataset;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder


        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            public ImageButton ImageBtn;
            public TextView nameText;
            public ImageView mImageView;
            public TextView mTextView;
            public Button btn_good;
            public TextView cntGood;
            public Button btn_comment;
            public TextView cntComment;
            public CardView cardview;
            public FloatingActionButton fab;



            @Override
            public void onClick(View v) {


            }

            public ViewHolder(View view) {
                super(view);

                ImageBtn = (ImageButton) view.findViewById(R.id.image_profile);
                nameText = (TextView) view.findViewById(R.id.textview_name);
                mImageView = (ImageView) view.findViewById(R.id.image);
                mTextView = (TextView) view.findViewById(R.id.textview);
                btn_good = (Button) view.findViewById(R.id.btn_good);
                cntGood = (TextView) view.findViewById(R.id.cnt_good);
                btn_comment = (Button) view.findViewById(R.id.btn_comment);
                cntComment = (TextView) view.findViewById(R.id.cnt_comment);
                cardview = (CardView) view.findViewById(R.id.cardview);
                fab = (FloatingActionButton)view.findViewById(R.id.plus_card);//floatingactionbutton구현
                /*fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent3 = new Intent(getContext(),InputCardActivity.class);
                        startActivity(intent3);
                    }
                });*/

            }
        }

        Context c;

        // Provide a suitable constructor (depends on the kind of dataset)
        public SNSAdapter(ArrayList<SNSItem> myDataset, Context context) {
            c = context;
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public SNSAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sns_my_view, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.ImageBtn.setOnClickListener(listener);
            // holder.nameText.setText(mDataset.get(position).nametext);//추후 사용자이름 받아오기
            holder.mTextView.setText(mDataset.get(position).contenttext);//CardUpdate.execute();?
            holder.mImageView.setImageResource(mDataset.get(position).img);
            holder.btn_good.setOnClickListener(listener);
            holder.cntGood.setText(mDataset.get(position).cntgood);
            holder.btn_comment.setOnClickListener(listener);
            //holder.plusCard.setOnClickListener();

            holder.cntComment.setText(mDataset.get(position).cntcomment);
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                //cardview 전체를 아우르는 뷰에 onclicklistener 걸기, RecyclerView에는 listview의 onItemClickListener가 존재하지 않음.
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getActivity(), CardViewActivity.class);//현재 액티비티 설정이 오류나는 이유를 모르겠음
                    startActivity(intent1);
                }
            });

        }


        Button.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.image_profile:
                        //프로필이미지를통해 타임라인으로 페이지 전환
                        break;
                    case R.id.btn_good:
                        //좋아요버튼 수 증가
                        //서버에서 숫자가져와서 표시, 그 숫자(스트링으로 된거 정수로 바꿔서)에 버튼을 누르면 그 숫자 +1해서 cntGood 텍스트뷰에 표시
                        //버튼 눌렸다고 서버로 보내기
                        //서버에서는 가진 숫자 +1 해서 저장해둠.
                        break;
                    case R.id.btn_comment:
                        Intent intent2 = new Intent(getActivity(),CardViewActivity.class);
                        startActivity(intent2);
                        break;

                }
            }
        };

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
