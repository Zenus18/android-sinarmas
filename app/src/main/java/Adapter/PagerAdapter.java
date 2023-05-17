package Adapter;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.letsmovie.home_fragment;
import com.example.letsmovie.profile_fragment;
import com.example.letsmovie.search_fragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numTab;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numTab = NumOfTabs;
    }

    @NonNull
    @Override
    public androidx.fragment.app.Fragment getItem(int position) {
        switch (position){
            case 0:
                return new home_fragment();
            case 1:
                return new search_fragment();
            case 2:
                return new profile_fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return numTab;
    }
}
