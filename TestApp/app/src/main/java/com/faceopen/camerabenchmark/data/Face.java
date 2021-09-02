package com.faceopen.camerabenchmark.data;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import hex.genmodel.easy.RowData;

public class Face {

    public boolean saveEntry = true;
    public boolean deleteEntry = false;
    public int ID;
    public Rect rect;
    public Rect changedRect;
    public ArrayList<Point> landmarks = new ArrayList<>();
    public boolean isBlur;
    public int blur;
    public int poseCnn;
    public int pose;
    public int poseV;
    public Bitmap croppedBitmap;
    public float spoofVal = 0;//+ve: real, -ve: spoof, 0:unknown
    public String modelSpoof;
    public String modelSpoof1;
    public String modelSpoofProbabilities = "";//+ve: real, -ve: spoof, 0:unknown
    public ArrayList<Point> mLandmarkPoints = new ArrayList<>();
    public ArrayList<Point> changedLandmarkPoints = new ArrayList<>();
    String string;
    public String spoofValues;
    //RowData row = new RowData();

    public String true_value = "";

    public void setId(int id) {
        this.ID = id;
    }

    public void setRect(int left, int top, int right, int bottom) {
        rect = new Rect(left, top, right, bottom);
    }

    public boolean add68Landmark(int x, int y) {
        return mLandmarkPoints.add(new Point(x, y));
    }

    public boolean add5Landmark(float x, float y) {
        return landmarks.add(new Point((int) x, (int) y));
    }

    public void setPose(int pose) {
        this.pose = pose;
    }

    public void setPoseCNN(int pose) {
        this.poseCnn = pose;
    }

    public void setVerPose(int poseV) {
        this.poseV = poseV;
    }

    public void setSpoof(float spoofVal) {
        this.spoofVal = spoofVal;
    }

    public void setBlur(int blur, boolean isBlur) {
        this.blur = blur;
        this.isBlur = isBlur;
    }

    public void set(int blur, boolean isBlur) {
        this.blur = blur;
        this.isBlur = isBlur;
    }

    public void setSaved(boolean saveEntry) {
        this.saveEntry = saveEntry;
    }

    public void setDelete(boolean deleteEntry) {
        this.deleteEntry = deleteEntry;
    }

    public void setTrueValue(String saveEntry) {
        this.true_value = saveEntry;
    }

    public void setSpoofData(String str) {
        this.string = str;
    }

//    public void setSpoofValues(String str) {
//        this.spoofValues = str;
//        if (!str.equals("")) {
//            String column = "b,pH,pV,sV,5mmsV,0b,0g,0r,0d,1b,1g,1r,1d,2b,2g,2r,2d,3b,3g,3r,3d,4b,4g,4r,4d,5b,5g,5r,5d,6b,6g,6r,6d,7b,7g,7r,7d,8b,8g,8r,8d,9b,9g,9r,9d,10b,10g,10r,10d,11b,11g,11r,11d,12b,12g,12r,12d,13b,13g,13r,13d,14b,14g,14r,14d,15b,15g,15r,15d,16b,16g,16r,16d,17b,17g,17r,17d,18b,18g,18r,18d,19b,19g,19r,19d,20b,20g,20r,20d,21b,21g,21r,21d,22b,22g,22r,22d,23b,23g,23r,23d,24b,24g,24r,24d,25b,25g,25r,25d,26b,26g,26r,26d,27b,27g,27r,27d,28b,28g,28r,28d,29b,29g,29r,29d,30b,30g,30r,30d,31b,31g,31r,31d,32b,32g,32r,32d,33b,33g,33r,33d,34b,34g,34r,34d,35b,35g,35r,35d,36b,36g,36r,36d,37b,37g,37r,37d,38b,38g,38r,38d,39b,39g,39r,39d,40b,40g,40r,40d,41b,41g,41r,41d,42b,42g,42r,42d,43b,43g,43r,43d,44b,44g,44r,44d,45b,45g,45r,45d,46b,46g,46r,46d,47b,47g,47r,47d,48b,48g,48r,48d,49b,49g,49r,49d,50b,50g,50r,50d,51b,51g,51r,51d,52b,52g,52r,52d,53b,53g,53r,53d,54b,54g,54r,54d,55b,55g,55r,55d,56b,56g,56r,56d,57b,57g,57r,57d,58b,58g,58r,58d,59b,59g,59r,59d,60b,60g,60r,60d,61b,61g,61r,61d,62b,62g,62r,62d,63b,63g,63r,63d,64b,64g,64r,64d,65b,65g,65r,65d,66b,66g,66r,66d,67b,67g,67r,67d";
//            String[] str1 = column.split(",");
//            String[] str2 = this.spoofValues.split(",");
//            row.clear();
//            for (int j = 0; j < str1.length; j++) {
//                if (str1[j].equals("sV") || str1[j].equals("5mmsV") || str1[j].endsWith("d") || str1[j].equals("pV") || str1[j].equals("pH") || str1[j].equals("b"))
//                    continue;
//                row.put(str1[j], str2[j]);
//            }
//            spoofValues = "3," + spoofValues;
//        }
//    }


//    public void debugImage(int width, int height) {
//        int x = rect.left - 250;
//        x = Math.max(x, 0);
//        int y = rect.top - 250;
//        y = Math.max(y, 0);
//        int x1 = rect.right + 250;
//        int y1 = rect.bottom + 250;
//        x1 = Math.min(width, x1);
//        y1 = Math.min(height, y1);
//        changedRect = new Rect(rect.left - x, rect.top - y, (rect.right - x), (rect.bottom - y));
//        croppedBitmap = FaceAnalyzer.getInstance().getImage(x, y, x1, y1);
//        for (Point p : mLandmarkPoints) {
//            changedLandmarkPoints.add(new Point(p.x - x, p.y - y));
//        }
//    }


//    public Bitmap cropImage() {
//        Log.i("testing_test", rect.left + "  " + rect.top + "  " + rect.right + "  " + rect.bottom);
//        return FaceAnalyzer.getInstance().getImage(rect.left, rect.top, rect.right, rect.bottom);
//    }
//
//    public Bitmap spoofImage(int width, int height) {
//        int x = rect.left - (int) (rect.width() * 0.5);
//        x = Math.max(x, 0);
//        int y = rect.top - (int) (rect.height() * 0.5);
//        y = Math.max(y, 0);
//        int x1 = rect.right + (int) (rect.width() * 0.5);
//        x1 = Math.min(width, x1);
//        int y1 = rect.bottom + (int) (rect.height() * 0.5);
//        y1 = Math.min(height, y1);
//        return FaceAnalyzer.getInstance().getImage(x, y, x1, y1);
//    }


//    public String toJSON(int width, int height) throws JSONException {
//        int x = rect.left - 250;
//        x = Math.max(x, 0);
//        int y = rect.top - 250;
//        y = Math.max(y, 0);
//        int x1 = rect.right + 250;
//        int y1 = rect.bottom + 250;
//        x1 = Math.min(width, x1);
//        y1 = Math.min(height, y1);
//        if (croppedBitmap == null)
//            croppedBitmap = FaceAnalyzer.getInstance().getImage(x, y, x1, y1);
//        JSONArray recArr = new JSONArray();
//        JSONArray recIDK = new JSONArray();
//        JSONArray poseIDK = new JSONArray();
//        JSONArray idArr = new JSONArray();
//        JSONArray landIDK = new JSONArray();
//        JSONObject face = new JSONObject();
//        recArr.put(rect.left - x);
//        recArr.put(rect.top - y);
//        recArr.put((rect.right - x) - (rect.left - x));
//        recArr.put((rect.bottom - y) - (rect.top - y));
//        recIDK.put(recArr);
//        JSONArray landArray = new JSONArray();
//        for (Point p : landmarks) {
//            landArray.put(p.x - x);
//        }
//        for (Point p : landmarks) {
//            landArray.put(p.y - y);
//        }
//        landIDK.put(landArray);
//        poseIDK.put("Center");
//        idArr.put(ID);
//        face.put("rects", recIDK);
//        face.put("landmarks", landIDK);
//        face.put("face_pose", poseIDK);
//        face.put("face_id", idArr);
//        Log.i("testing_data", face.toString());
//        return face.toString();
//    }

}
