package com.faceopen.camerabenchmark.data;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class BitmapDT {

    private static BitmapDT instance;
    private ArrayList<Bitmap> completeList = new ArrayList<Bitmap>();

    public static BitmapDT getInstance() {
        if (instance == null) {
            instance = new BitmapDT();
        }
        return instance;
    }

    public void setBitMaps(ArrayList<Bitmap> bitMaps) {
        this.completeList = bitMaps;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return completeList;
    }
}
