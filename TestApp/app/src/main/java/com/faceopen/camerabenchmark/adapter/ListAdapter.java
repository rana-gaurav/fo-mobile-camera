package com.faceopen.camerabenchmark.adapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faceopen.camerabenchmark.R;

import java.util.ArrayList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<Bitmap> imageid = new ArrayList<Bitmap>();
    private Activity context;
    public ListAdapter(Activity context, ArrayList<Bitmap> imageid) {
        this.context = context;
        this.imageid = imageid;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mylist, null);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;

    }

    // ImageView iv1 =  TakePicture.findViewById(R.id.iv_preview) ; // getContext().findViewById(R.id.iv_preview) ;

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        // final MyListData myListData = listdata[position];
        holder.imageView.setImageBitmap(imageid.get(position));


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TakePicture.bmp = imageid.get(position) ;
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageid.size() ;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView imageOk;
        public ImageView imageDel;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.iv_image);

        }
    }

    public void removeAt(int position) {
        if(position>=0) {
            try {
                imageid.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, imageid.size());
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
