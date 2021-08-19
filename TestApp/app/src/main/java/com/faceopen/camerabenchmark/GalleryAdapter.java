package com.faceopen.camerabenchmark;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 16.03.2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private int itemHeight;
    private Context mContext;
    //private List<Image> data;

    private ArrayList<Bitmap> imageid = new ArrayList<Bitmap>();

//    public GalleryAdapter(List<Image> data) {
//        this.data = data;
//    }

    public GalleryAdapter(Context context, ArrayList<Bitmap> data) {
        this.imageid = data;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Activity context = (Activity) recyclerView.getContext();
        Point windowDimensions = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(windowDimensions);
        itemHeight = Math.round(windowDimensions.y * 0.6f);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.mylist, parent, false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                itemHeight);
        v.setLayoutParams(params);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageBitmap(imageid.get(position));
//        Glide.with(holder.itemView.getContext())
//                .load(imageid.get(position).getResource())
//                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imageid.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private View overlay;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            //overlay = itemView.findViewById(R.id.overlay);
        }

        public void setOverlayColor(@ColorInt int color) {
            overlay.setBackgroundColor(color);
        }
    }
}
