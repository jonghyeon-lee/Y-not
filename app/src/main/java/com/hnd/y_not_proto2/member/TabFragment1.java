package com.hnd.y_not_proto2.member;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.hnd.y_not_proto2.R;

import org.apache.http.NameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016-03-18.
 */
public class TabFragment1 extends Fragment {
    //public static final String ROW_ID = "row_id";
    String tag = "cap";
    String[] name, new_name;
    int count = 0;

    ListView mLv_member;
    ArrayList<HashMap<String, String>> users = new ArrayList<HashMap<String, String>>();

    List<NameValuePair> params;
    SharedPreferences prefs;
    ListAdapter adapter;

    private ListView lv;
    ArrayList<Mstate> al = new ArrayList<Mstate>();

    ListView listView;
    Cursor cursor;
    static final String[] field = new String[] {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    };

    // ArrayList<Fstate> Fal = new ArrayList<Fstate>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        prefs = getActivity().getSharedPreferences("Chat", 0);
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        SharedPreferences.Editor edit = prefs.edit();
        //edit.putString("REG_FROM", mobno.getText().toString());
        //edit.putString("FROM_NAME", name.getText().toString());


        al.add(new Mstate(prefs.getString("FROM_NAME",""), R.drawable.a,prefs.getString("REG_FROM","") ));

        MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext(),R.layout.my_state, al);         // 데이터

        lv = (ListView)view.findViewById(R.id.mystate);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //  MyInfoFragment detailsFragment = new MyInfoFragment();

                Intent intent = new Intent(getActivity().getApplicationContext(), MyInfoFragment.class);


                intent.putExtra("name", al.get(position).name);
                intent.putExtra("profile", al.get(position).profile);
                intent.putExtra("state", al.get(position).state);
                startActivity(intent);

                   /* FragmentTransaction transaction =
                            getFragmentManager().beginTransaction();
                    transaction.replace(R.id.tab1, detailsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit(); // causes DetailsFragment to display
                */


                //  Toast.makeText(getActivity().getApplicationContext(), mData.mTitle, Toast.LENGTH_SHORT).show();

            }

        });

        listView = (ListView)view.findViewById(R.id.Friend);

        cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, field, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        ContactListItemAdapter Fadapter = new ContactListItemAdapter(getActivity(), R.layout.my_state, cursor);
        listView.setAdapter(Fadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                cursor.moveToPosition(position);
                int nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(nameIdx);

                int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phone = cursor.getString(phoneIdx);
                Intent intent = new Intent(getActivity().getApplicationContext(), FriendInfo.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                startActivity(intent);

            }

        });

        //  FriendAdapter mAdapter = new FriendAdapter(getActivity().getApplicationContext(), cs);

        //  listView.setAdapter(mAdapter);

       /* ArrayAdapter<HashMap<String, String>> list_adapAdapter =
                new ArrayAdapter<HashMap<String,String>>(getActivity().getApplicationContext()
                        ,android.R.layout.simple_list_item_2);

        String phone = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String name     = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
          SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),

                android.R.layout.simple_list_item_2, getPhone(),

                new String[] {name , phone },

                new int[] { android.R.id.text1, android.R.id.text2 }, 0);

        listView.setAdapter(mAdapter);
*/

        return view;
    }


    private final class ContactListItemAdapter extends ResourceCursorAdapter {
        public ContactListItemAdapter(Context context, int layout, Cursor c) {
            super(context, layout, c);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ContactListItemCache cache = (ContactListItemCache) view.getTag();
            TextView nameView = cache.nameView;
            TextView phoneView = cache.phoneView;
            ImageView photoView = cache.photoView;

// 이름 표시
            int nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIdx);
            nameView.setText(name);

// 번호 표시
            int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String phone = cursor.getString(phoneIdx);
            phoneView.setText(phone);

// 사진 표시
            ContentResolver cr = getActivity().getContentResolver();
            int contactId_idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
            Long contactId = cursor.getLong(contactId_idx);
            Log.e("###", contactId_idx + " | " + contactId + " | " + name);

            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);

            // 주소록에 사진없으면 기본 사진 보여주기
            if (input != null)
            {
                Bitmap contactPhoto = BitmapFactory.decodeStream(input);
                photoView.setImageBitmap(contactPhoto);
            }
            else
            {
                photoView.setImageDrawable(getResources().getDrawable(R.drawable.a));
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = super.newView(context, cursor, parent);
            ContactListItemCache cache = new ContactListItemCache();
            cache.nameView = (TextView) view.findViewById(R.id.my_name);
            cache.phoneView = (TextView) view.findViewById(R.id.my_state);
            cache.photoView = (ImageView) view.findViewById(R.id.my_image);
            view.setTag(cache);
            return view;
        }
    }

    final static class ContactListItemCache {
        public TextView nameView, phoneView;
        public ImageView photoView;
    }

    //실질적으로 가져오는 메소드
   /* private Cursor getPhone() {

        Uri people = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER };

        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ " COLLATE LOCALIZED ASC";

        return getActivity().getContentResolver().query(people, selectionArgs, null, selectionArgs, sortOrder);

    }

    class FriendAdapter extends CursorAdapter {

        public FriendAdapter(Context context, Cursor c)
        {
            super(context, c);
        }


        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            final ImageView image = (ImageView)view.findViewById(R.id.my_image);
            final TextView name = (TextView)view.findViewById(R.id.my_name);
            final TextView age = (TextView)view.findViewById(R.id.my_state);

            image.setImageResource(R.drawable.b);
            name.setText(cursor.getString(cursor.getColumnIndex("name")));
            age.setText(cursor.getString(cursor.getColumnIndex("age")));
            return;
        }
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_state, parent, false);
            return view;
        }
    }
*/

    class MyAdapter extends BaseAdapter { // 리스트 뷰의 아답타
        Context context;
        int layout;
        ArrayList<Mstate> al;
        LayoutInflater inf;
        public MyAdapter(Context context, int layout, ArrayList<Mstate> al) {
            this.context = context;
            this.layout = layout;
            this.al = al;
            inf = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return al.size();
        }
        @Override
        public Object getItem(int position) {
            return al.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                convertView = inf.inflate(layout, null);
            }
            ImageView profile = (ImageView)convertView.findViewById(R.id.my_image);
            TextView name = (TextView)convertView.findViewById(R.id.my_name);
            TextView state = (TextView)convertView.findViewById(R.id.my_state);

            Mstate m = al.get(position);
            profile.setImageResource(m.profile);
            name.setText(m.name);
            state.setText(m.state);

            return convertView;
        }
    }

    class Mstate {
        String name = "";
        int profile;
        String state = "";
        public Mstate(String name, int profile, String state) {
            super();
            this.name = name;
            this.profile = profile;
            this.state = state;
        }
        public Mstate() {}
    }



}