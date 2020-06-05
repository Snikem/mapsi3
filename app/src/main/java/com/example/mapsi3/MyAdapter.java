package com.example.mapsi3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MyPlaceList> list;
    private LayoutInflater inflater;
    public MyAdapter(Context context, ArrayList<MyPlaceList> list){
        this.list=list;
        this.inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapterfortop,parent,false);
        return new MyAdapter.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        MyPlaceList placeList = list.get(position);
        holder.place.setText(Integer.toString(placeList.Place));
        holder.name1.setText(placeList.name);
        holder.score.setText(Integer.toString(placeList.countPx));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView place,score,name1;

        public ViewHolder(@NonNull View view) {
            super(view);
            place = (TextView)view.findViewById(R.id.placeintop1);
            name1 = (TextView)view.findViewById(R.id.nicknameforadapter);
            score = (TextView)view.findViewById(R.id.countpxforadapter);
        }
    }



    /*

    public AdapterForRangList(Context context, MyPlaceList[] arr) {
        super(context, R.layout.adapterfortop, arr);
    }
    public AdapterForRangList(Context context, ArrayList<MyPlaceList> arr) {
        super(context, R.layout.adapterfortop, arr);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final MyPlaceList placeList = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapterfortop, null);
        }

            ((TextView) convertView.findViewById(R.id.placeintop1)).setText(Integer.toString(placeList.Place));
            ((TextView) convertView.findViewById(R.id.nicknameforadapter)).setText(placeList.name);
            ((TextView) convertView.findViewById(R.id.countpxforadapter)).setText(Integer.toString(placeList.countPx));

        return convertView;
    }*/
}
