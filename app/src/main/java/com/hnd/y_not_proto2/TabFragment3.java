package com.hnd.y_not_proto2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2016-03-18.
 */
public class TabFragment3 extends Fragment {

    // callback method implemented by MainActivity
    public interface EditMyInfoFragmentListener
    {
        // called after edit completed so contact can be redisplayed
        public void onAddEditCompleted(long rowID);
    }

    private EditMyInfoFragmentListener listener;

    // EditTexts for contact information
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText stateEditText;


    // called when Fragment's view needs to be created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // inflate GUI and get references to EditTexts
        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        phoneEditText = (EditText) view.findViewById(R.id.phoneEditText);

        //  contactInfoBundle = getArguments(); // null if creating new contact



        // set Save Contact Button's event listener
        Button saveContactButton =
                (Button) view.findViewById(R.id.saveContactButton);
        // saveContactButton.setOnClickListener(saveContactButtonClicked);
        return view;
    }



}