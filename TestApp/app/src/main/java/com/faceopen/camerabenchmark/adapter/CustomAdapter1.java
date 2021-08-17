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

public class CustomAdapter1 extends ArrayAdapter<String> {
    private final Context context;
    private final String[] items;
    private final Integer[] logo;

    public CustomAdapter1(Context context, String[] items, Integer[] logo) {
        super(context, R.layout.card_item,items);
        this.context = context;
        this.items = items;
        this.logo = logo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.card_item,null,true );
        TextView title=(TextView)   view.findViewById(R.id.tvData);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageView);

        title.setText(items[position]);
        imageView.setImageResource(logo[position]);

        return view;
    }
}
