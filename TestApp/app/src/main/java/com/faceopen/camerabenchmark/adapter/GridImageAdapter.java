package com.faceopen.camerabenchmark.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.faceopen.camerabenchmark.R;

import java.util.ArrayList;

public class GridImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bitmap> arrayList;

    public GridImageAdapter(Context context, ArrayList<Bitmap> bitmapArrayList) {
        this.context = context;
        this.arrayList = bitmapArrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_image, parent, false);
        }
        ImageView imageView;
        CheckBox cbSave;
        imageView = convertView.findViewById(R.id.gvImage);
        cbSave = convertView.findViewById(R.id.sw_save);
        Glide.with(context).asBitmap().load(arrayList.get(position)).into(imageView);

        cbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        return convertView;
    }
}
