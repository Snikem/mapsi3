package com.example.mapsi3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class AdapterForRangList extends ArrayAdapter<MyPlaceList> {
    private String TEG="wriworiqwoirpoqiwerpoiqwpoeriopqwi";
    public AdapterForRangList(Context context, MyPlaceList[] arr) {
        super(context, R.layout.adapterfortop, arr);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final MyPlaceList placeList = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapterfortop, null);
        }
        Log.d(TEG,"жопа");
            ((TextView) convertView.findViewById(R.id.placeintop1)).setText(Integer.toString(placeList.Place));
            ((TextView) convertView.findViewById(R.id.nicknameforadapter)).setText(placeList.name);
            ((TextView) convertView.findViewById(R.id.countpxforadapter)).setText(Integer.toString(placeList.countPx));

        return convertView;
    }
}
