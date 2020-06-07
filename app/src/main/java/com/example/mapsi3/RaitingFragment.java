package com.example.mapsi3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


public class RaitingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_raiting_, container, false);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(layout.getContext(), makeArrayPlace());
        recyclerView.setAdapter(adapter);
        return layout;
    }

    ArrayList<MyPlaceList> makeArrayPlace() {
        ArrayList<MyPlaceList> arrP = new ArrayList<MyPlaceList>();
        int size = DrawThread.Userspoint.size() - 1;
        MyPlaceList place1 = new MyPlaceList(size + 1, DrawThread.Userspoint.get(0).name, DrawThread.Userspoint.get(0).score);
        for (int i = size; i > 0; i--) {
            MyPlaceList place = new MyPlaceList(size - i + 1, DrawThread.Userspoint.get(i).name, DrawThread.Userspoint.get(i).score);
            arrP.add(place);
        }
        arrP.add(place1);
        return arrP;
    }

    public RaitingFragment() {

    }

    public static RaitingFragment newInstance(String param1, String param2) {
        RaitingFragment fragment = new RaitingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}
