package com.faceopen.camerabenchmark.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.SharedPreferenceManager;

public class CustomAdapter1 extends ArrayAdapter<String> {
    private final Context context;
    private final String[] items;
    private final Integer[] logo;
    private int mPosition ;

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
        TextView title=(TextView) view.findViewById(R.id.tvData);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageView);
        ImageView checkMark=(ImageView) view.findViewById(R.id.checkMark);
        title.setText(items[position]);
        imageView.setImageResource(logo[position]);
        if(SharedPreferenceManager.getCategory1(context) && position == 0){
            Log.d("YYY", "getCategory1");
            checkMark.setVisibility(View.VISIBLE);
        }
        if(SharedPreferenceManager.getCategory2(context) && position == 1){
            Log.d("YYY", "getCategory2");
            checkMark.setVisibility(View.VISIBLE);
        }
        if(SharedPreferenceManager.getCategory3(context) && position == 2){
            Log.d("YYY", "getCategory3");
            checkMark.setVisibility(View.VISIBLE);
        }
        if(SharedPreferenceManager.getCategory4(context) && position == 3){
            Log.d("YYY", "getCategory4");
            checkMark.setVisibility(View.VISIBLE);
        }
        if(SharedPreferenceManager.getCategory5(context) && position == 4){
            Log.d("YYY", "getCategory5");
            checkMark.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void refresh(){
        notifyDataSetChanged();
    }
}
