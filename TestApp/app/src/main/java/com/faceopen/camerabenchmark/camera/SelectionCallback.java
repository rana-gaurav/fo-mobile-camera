package com.faceopen.camerabenchmark.camera;

import java.io.Serializable;

public interface SelectionCallback extends Serializable {
    void onComplete(String type);
}
