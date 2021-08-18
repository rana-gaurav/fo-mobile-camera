package com.faceopen.camerabenchmark.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.model.ItemModel;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ItemModel> {
    public CustomAdapter(@NonNull Context context, ArrayList<ItemModel> itemModelArrayList) {
        super(context,0, itemModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View listitemView=convertView;
       if(listitemView==null){
           listitemView= LayoutInflater.from(getContext()).inflate(R.layout.card_item,parent,false);
       }

       ItemModel itemModel=getItem(position);

        TextView textView=listitemView.findViewById(R.id.tvData);
        ImageView imageView=listitemView.findViewById(R.id.imageView);

        textView.setText(itemModel.getName());
        imageView.setImageResource(itemModel.getImage());

        return listitemView;
    }
}
