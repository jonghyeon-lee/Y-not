package com.hnd.y_not_proto2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hnd.y_not_proto2.member.TabFragment1;
import com.hnd.y_not_proto2.member.UserFragment;
import com.hnd.y_not_proto2.sns.SNSFragment;

/**
 * Created by Administrator on 2016-03-18.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                return tab1;
            case 1:
                UserFragment usr = new UserFragment();
                return usr;
            case 2: // 실명
                SNSFragment sns1 = new SNSFragment();
                return sns1;
            case 3: // 익명
                SNSFragment sns2 = new SNSFragment();
                return sns2;
            case 4:
                SetFragment set = new SetFragment();
                return set;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}