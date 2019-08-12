package com.example.riotproject.frament;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riotproject.R;
import com.example.riotproject.request.SkinRequest;

import java.util.concurrent.ExecutionException;

public class SkinFragment extends Fragment implements View.OnClickListener {

    public SkinFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_skin, container, false);

        v.findViewById(R.id.btn_skin);

        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onClick(View view) {

    }
}
