package com.faceopen.camerabenchmark.adapter;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faceopen.camerabenchmark.Face;
import com.faceopen.camerabenchmark.OnclickListener;
import com.faceopen.camerabenchmark.R;

import java.util.ArrayList;

public class ImagePreviewAdapter extends RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder> {

    private ArrayList<Face> allData = new ArrayList<Face>();
    private ArrayList<Integer> deletedIndex = new ArrayList<Integer>();
    private Activity mContext;
    private OnclickListener clickListener;
    public ImagePreviewAdapter(Activity context, ArrayList<Face> allData, OnclickListener listener) {
        this.mContext = context;
        this.allData = allData;
        this.clickListener = listener;
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
        Log.d("KKK",""+position);
        Face face = allData.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.pl);
        requestOptions.error(R.drawable.pl);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(allData.get(position).croppedBitmap)
                .into( holder.imageView);

        holder.radioGroup.setVisibility(View.VISIBLE);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });

        holder.radioGroup.setOnCheckedChangeListener(null);
        if(face.saveEntry){
            holder.rbSave.setChecked(true);
        }else{
            holder.rbDel.setChecked(true);
        }

        RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 if (checkedId == R.id.rb_save1){
                     face.setSaved(true);
                     clickListener.onSaveChecked(position, true);
                     deletedIndex.remove(Integer.valueOf(position));
                 }
                 if (checkedId == R.id.rb_del1){
                     face.setSaved(false);
                     clickListener.onDeleteChecked(position, true);
                     deletedIndex.add(position);
                 }
            }
        };
        holder.radioGroup.setOnCheckedChangeListener(radioListener);
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
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView =  itemView.findViewById(R.id.iv_image);
            this.radioGroup = itemView.findViewById(R.id.rg_group1);
            this.rbSave = itemView.findViewById(R.id.rb_save1);
            this.rbDel = itemView.findViewById(R.id.rb_del1);
        }
    }

    public ArrayList<Integer> getDeletedIndex(){
        return deletedIndex;
    }

}
