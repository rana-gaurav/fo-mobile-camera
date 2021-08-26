package com.faceopen.camerabenchmark.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faceopen.camerabenchmark.R;

import java.util.ArrayList;


public class ImagePreviewAdapter extends RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder> {

    private ArrayList<Bitmap> allData = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> selectedData = new ArrayList<Bitmap>();
    private Activity context;
    public ImagePreviewAdapter(Activity context, ArrayList<Bitmap> allData) {
        this.context = context;
        this.allData = allData;
    }

    @NonNull
    @Override
    public ImagePreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mylist, null);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagePreviewAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(allData.get(position));
        holder.rgGroup.setOnCheckedChangeListener(null);
        holder.rbSave.setChecked(true);
        selectedData.addAll(allData);
        holder.rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_save) {
                    selectedData.add(position, allData.get(position));
                    Log.d("RRR", "onCheckedChanged " + selectedData.size());
                }
                if (checkedId == R.id.rb_del) {
                    selectedData.remove(position);
                    Log.d("RRR", "onCheckedChanged " + selectedData.size());
                }
            }
        });
        holder.imageView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return allData.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RadioGroup rgGroup;
        public RadioButton rbSave;
        public RadioButton rbDel;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView =  itemView.findViewById(R.id.iv_image);
            this.rgGroup =itemView.findViewById(R.id.rg_group);
            this.rbSave =  itemView.findViewById(R.id.rb_save);
            this.rbDel = itemView.findViewById(R.id.rb_del);
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
}
