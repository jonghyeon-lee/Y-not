package com.hnd.y_not_proto2.sns;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hnd.y_not_proto2.R;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016-03-22.
 */
public class CardViewActivity extends Activity {

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    ArrayList<HashMap<String, String>> card = new ArrayList<HashMap<String, String>>();
    Button refresh,logout;
    List<NameValuePair> params;
    SharedPreferences prefs;

    @Override
    protected  void onCreate ( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_cardview_clicked);


        ImageView clickedImage = (ImageView)findViewById(R.id.clicked_image);
        Button btngoood = (Button)findViewById(R.id.clicked_btngood);
        TextView cntgoood =  (TextView)findViewById(R.id.clicked_cntgood);
        TextView cntcommment = (TextView) findViewById(R.id.clicked_cntcomment);
        EditText eidtComment = (EditText)findViewById(R.id.editcomment);
        Button submitComment = (Button)findViewById(R.id.add_comment);
        ListView commentList = (ListView)findViewById(R.id.comment_listview);

        clickedImage.setImageResource(R.drawable.kkk);
        btngoood.setOnClickListener(listener);
        cntgoood.setText(getString(R.string.c_cntgood));//서버
        cntcommment.setText(getString(R.string.c_cntcomment));//서버
        submitComment.setOnClickListener(listener);


        mListView = (ListView) findViewById(R.id.comment_listview);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        //댓글이랑 카드뷰 아이템 추가시 아이템 데이터 서버에서 불러오는거 설정
        mAdapter.addItem(getResources().getDrawable(R.drawable.profile),getString(R.string.c_user),
                getString(R.string.comment1));
        mAdapter.addItem(getResources().getDrawable(R.drawable.profile),getString(R.string.c_user),
                getString(R.string.comment2));
        mAdapter.addItem(getResources().getDrawable(R.drawable.profile),getString(R.string.c_user),
                getString(R.string.comment3));
        mAdapter.addItem(getResources().getDrawable(R.drawable.profile),getString(R.string.c_user),
                getString(R.string.comment4));


    }



    Button.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_good:
                    //좋아요버튼 수 증가
                    //서버에서 숫자가져와서 표시, 그 숫자(스트링으로 된거 정수로 바꿔서)에 버튼을 누르면 그 숫자 +1해서 cntGood 텍스트뷰에 표시
                    //버튼 눌렸다고 서버로 보내기
                    //서버에서는 가진 숫자 +1 해서 저장해둠.
                    break;
                case R.id.add_comment:
                    //editText에서 getText해와서 그 내용을 리스트뷰에 아이템 추가형태로 구현.
                    int count;
                    count = mAdapter.getCount();

                    // 아이템 추가. => 현재사용자 이미지랑 이름 받아오기, 댓글내용가져오기
                    //mAdapter.addItem(현재사용자프로필, 현재사용자이름, editComment.getText().toString());

                    // listview 갱신
                    //mAdapter.notifyDataSetChanged();
                    break;
                case R.id.btn_comment:
                    //댓글입력할 수 있는 다이얼로그 띄우기 =>  카드뷰 전체 눌렀을 때 효과!
                    //Intent intent = new Intent(MyActivity.context,CardviewClicked.class);
                    //startActivity(intent);
                    break;

            }
        }
    };
    //커스텀리스트뷰로 댓글 구
    private class ViewHolder {

        public ImageView comment_user;
        public TextView comment_username;
        public TextView comment_content;

    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.sns_custom_item, null);

                holder.comment_user = (ImageView) convertView.findViewById(R.id.comment_img);
                holder.comment_content = (TextView) convertView.findViewById(R.id.comment_text);
                holder.comment_username = (TextView) convertView.findViewById(R.id.comment_username);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mImage != null) {
                holder.comment_user.setVisibility(View.VISIBLE);
                holder.comment_user.setImageDrawable(mData.mImage);
            }else{
                holder.comment_user.setVisibility(View.GONE);
            }

            holder.comment_content.setText(mData.mContent);
            holder.comment_username.setText(mData.mUser);

            return convertView;
        }
        // 커스텀리스트뷰 이용 댓글에서 그 외 필요시 구현할 메소드들
        public void addItem(Drawable icon, String username, String mTitle){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mImage = icon;
            addInfo.mUser = username;
            addInfo.mContent = mTitle;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }

    }

}
