package top.defaults.camera;

import android.graphics.Bitmap;

public class ImageData {
    private Bitmap bitmap;
    private int imageHeight;
    private int imageWidth;
    private int imageFormat;
    private byte[] imageByte;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] getByte() {
        return imageByte;
    }

    public void setByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(int imageFormat) {
        this.imageFormat = imageFormat;
    }
}
