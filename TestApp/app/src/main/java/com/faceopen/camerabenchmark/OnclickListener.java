package com.faceopen.camerabenchmark;

public interface OnclickListener {
    public void onClick(int position);
    public void onSaveChecked(int position, boolean state);
    public void onDeleteChecked(int position, boolean state);
}
