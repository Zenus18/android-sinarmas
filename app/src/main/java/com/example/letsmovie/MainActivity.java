package com.example.letsmovie;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    BottomNavigationView button_nav;
    Fragment homefragment = new home_fragment();
    Fragment searchfragment = new search_fragment();
    Fragment profilefragment = new profile_fragment();

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        actionBar = getSupportActionBar();
        actionBar.hide();
        //menambahkan tablayout
        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.home_svgrepo_com)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.search_svgrepo_com)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.user_01_svgrepo_com)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //setting view pager dengan manggunakan pagerAdapter
        ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter adapter = new Adapter.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        //untuk
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}