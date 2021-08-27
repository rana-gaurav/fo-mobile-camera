package com.faceopen.camerabenchmark.adapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faceopen.camerabenchmark.OnclickListener;
import com.faceopen.camerabenchmark.R;

import java.util.ArrayList;


public class ImagePreviewAdapter extends RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder> {

    private ArrayList<Bitmap> allData = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> selectedData = new ArrayList<Bitmap>();
    private Activity context;
    private OnclickListener clickListener;
    public ImagePreviewAdapter(Activity context, ArrayList<Bitmap> allData, OnclickListener listener) {
        this.context = context;
        this.allData = allData;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ImagePreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mylist, null);
        ViewHolder viewHolder = new ViewHolder(listItem);
        viewHolder.rbSave.setChecked(true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagePreviewAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(allData.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });

       holder.rbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               clickListener.onSaveChecked(position, isChecked);
           }
       });

        holder.rbDel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickListener.onDeleteChecked(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allData.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RadioGroup radioGroup;
        public RadioButton rbSave;
        public RadioButton rbDel;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView =  itemView.findViewById(R.id.iv_image);
            this.radioGroup = itemView.findViewById(R.id.rg_group1);
            this.rbSave = itemView.findViewById(R.id.rb_save1);
            this.rbDel = itemView.findViewById(R.id.rb_del1);
        }
    }

    public void removeAt(int position) {
        try{
            allData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, allData.size());
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Bitmap> getSelectedData(){
        Log.d("RRR", "getSelectedData "+selectedData.size());
        return selectedData;
    }

   public void setCheckListener(){

   }
}
