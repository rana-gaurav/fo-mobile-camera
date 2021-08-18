package com.faceopen.camerabenchmark;

import java.io.Serializable;

public interface SelectionCallback extends Serializable {
    void onComplete(String type);

}
