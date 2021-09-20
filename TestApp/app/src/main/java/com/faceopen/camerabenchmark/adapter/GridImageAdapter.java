package com.faceopen.camerabenchmark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.faceopen.camerabenchmark.data.Face;
import com.faceopen.camerabenchmark.previewImages.OnImageClickListener;
import com.faceopen.camerabenchmark.R;

import java.util.ArrayList;

public class GridImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Face> allData;
    private OnImageClickListener clickListener;

    public GridImageAdapter(Context context, ArrayList<Face> faceArrayList, OnImageClickListener listener) {
        this.context = context;
        this.allData = faceArrayList;
        this.clickListener = listener;
    }

    @Override
    public int getCount() {
        return allData.size();
    }

    @Override
    public Object getItem(int position) {
        return allData.get(position);
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
        RadioGroup radioGroup;
        RadioButton rbSave;
        RadioButton rbDel;

        imageView = convertView.findViewById(R.id.gvImage);
        radioGroup = convertView.findViewById(R.id.rg_group1);
        rbSave = convertView.findViewById(R.id.rb_save1);
        rbDel = convertView.findViewById(R.id.rb_del1);

        Face face = allData.get(position);
        Glide.with(context).asBitmap().load(allData.get(position).croppedBitmap).into(imageView);

        radioGroup.setOnCheckedChangeListener(null);
        if(face.saveEntry){
            rbSave.setChecked(true);
            rbSave.setTextColor(context.getResources().getColor(R.color.white));
            rbDel.setTextColor(context.getResources().getColor(R.color.red));
        }else{
            rbDel.setChecked(true);
            rbDel.setTextColor(context.getResources().getColor(R.color.white));
            rbSave.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_save1){
                    face.setSaved(true);
                    rbSave.setTextColor(context.getResources().getColor(R.color.white));
                    rbDel.setTextColor(context.getResources().getColor(R.color.red));
                    clickListener.onSaveChecked(position, true, face);
                }
                if (checkedId == R.id.rb_del1){
                    face.setSaved(false);
                    rbDel.setTextColor(context.getResources().getColor(R.color.white));
                    rbSave.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    clickListener.onDeleteChecked(position, true, face);
                }
            }
        };
        radioGroup.setOnCheckedChangeListener(radioListener);
        return convertView;
    }
}
