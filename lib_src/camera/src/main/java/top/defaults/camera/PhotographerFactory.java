package top.defaults.camera;

import android.app.Activity;

public class PhotographerFactory {

    public static Photographer createPhotographerWithCamera2(Activity activity, CameraView preview, Photographer.onDataListener listener) {
        InternalPhotographer photographer = new Camera2Photographer(listener);
        photographer.initWithViewfinder(activity, preview);
        preview.assign(photographer);
        return photographer;
    }
}
