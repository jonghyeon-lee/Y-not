package com.hnd.y_not_proto2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-03-18.
 */
public class TabFragment2 extends Fragment {
    private ListView chat_list;
    private MyAdapter chatAdatper;
    private ArrayList<chatlist> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);

        list=new ArrayList<chatlist>();     // ArrayList로 생성
        chat_list=(ListView) view.findViewById(R.id.chat_list); // listview의 아이디 값을 받아오고
        chatAdatper=new MyAdapter(list);       // Adapter 생성
        chat_list.setAdapter(chatAdatper);            // Adapter에 붙인다

        //아이템 추가
        list.add(new chatlist(R.drawable.a,"test 1","1"));
        list.add(new chatlist(R.drawable.c,"test 2","2"));
        list.add(new chatlist(R.drawable.b,"test 3","3"));
        return view;
    }


    // ListView의 아이템에 들어가는 커스텀된 데이터들의 묶음
    public class chatlist{
        private int profile;    // R.drawable.~ 리소스 아이디 값을 받아오는 변수
        private String name;    // String 카카오톡 대화 목록의 이름
        private String chat;    // String 마지막 대화
        // 매개변수가 있는 생성자로 받아와 값을 전달한다.
        public chatlist(int profile, String name, String chat){
            this.profile=profile;
            this.name=name;
            this.chat=chat;
        }
    }

    // Adapter
    public class MyAdapter extends BaseAdapter {
        private ArrayList<chatlist> list;

        public MyAdapter(ArrayList<chatlist> list){
            // Adapter 생성시 list값을 넘겨 받는다.
            this.list=list;
        }

        @Override
        public int getCount() {
            // list의 사이즈 만큼 반환
            return list.size();
        }

        @Override
        public chatlist getItem(int position) {
            // 현재 position에 따른 list의 값을 반환 시켜준다.
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos=position;


            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.chat_list, parent, false);

                ImageView profile=(ImageView)convertView.findViewById(R.id.profile_image);
                TextView name=(TextView)convertView.findViewById(R.id.name);
                TextView chat=(TextView)convertView.findViewById(R.id.chat);

                profile.setImageResource(getItem(pos).profile);
                name.setText(getItem(pos).name);
                chat.setText(getItem(pos).chat);
            }
            return convertView;
        }
    }
}
