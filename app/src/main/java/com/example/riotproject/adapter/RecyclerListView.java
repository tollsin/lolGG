package com.example.riotproject.adapter;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.riotproject.R;
import com.example.riotproject.data.MathReferenceDto;

import java.util.ArrayList;

public class RecyclerListView {
    private RecyleAdapter recyleAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MathReferenceDto> items;

    public RecyclerListView(Context context, View view, Fragment fragment) {

        items = new ArrayList<>();
        recyleAdapter = new RecyleAdapter(context,items);
        recyclerView = view.findViewById(R.id.main_recycle);
        recyclerView.setAdapter(recyleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
