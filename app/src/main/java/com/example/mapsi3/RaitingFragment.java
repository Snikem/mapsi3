package com.example.mapsi3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_raiting_, container, false);

        AdapterForRangList adapter = new AdapterForRangList(getContext(), makeArrayPlace());
        ListView lv = (ListView) layout.findViewById(R.id.list23);

        lv.setAdapter(adapter);
        return layout;
    }
    ArrayList<MyPlaceList> makeArrayPlace(){
        ArrayList<MyPlaceList> arrP = new ArrayList<MyPlaceList>();
        int size = DrawThread.Userspoint.size()-1;

        for(int i = size;i>0;i--){
            MyPlaceList place = new MyPlaceList(size-i+1,DrawThread.Userspoint.get(i).name,DrawThread.Userspoint.get(i).score);
            arrP.add(place);
        }
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




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
