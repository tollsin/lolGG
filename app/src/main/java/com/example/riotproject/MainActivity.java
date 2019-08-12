package com.example.riotproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;


import com.example.riotproject.adapter.PagerAdapter;
import com.example.riotproject.frament.SkinFragment;
import com.example.riotproject.frament.FullSearchFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,LodingActivity.class);
        startActivity(intent);

        ViewPager viewPager = findViewById(R.id.vp_pager);
        TabLayout tabLayout = findViewById(R.id.tl_tabs);

        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new FullSearchFragment());
        fragments.add(new SkinFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragments);

        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);


    }
}
