package com.faceopen.camerabenchmark.previewImages;

import com.faceopen.camerabenchmark.data.Face;

public interface OnclickListener {
    void onClick(int position);
    void onSaveChecked(int position, boolean state, Face face);
    void onDeleteChecked(int position, boolean state, Face face);
}
