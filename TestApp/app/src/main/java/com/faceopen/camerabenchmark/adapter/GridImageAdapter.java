package com.faceopen.camerabenchmark.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.dialog.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;

import java.util.ArrayList;

public class GridImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bitmap> arrayList;
    LabeledSwitch labeledSwitch;

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
        imageView = convertView.findViewById(R.id.gvImage);
        Glide.with(context)
                .asBitmap()
                .load(arrayList.get(position))
                .into(imageView);
        labeledSwitch = convertView.findViewById(R.id.labelswitch);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    // if labeled switch on
                } else {
                    // if label switch is off
                }
            }
        });
        return convertView;
    }
}
