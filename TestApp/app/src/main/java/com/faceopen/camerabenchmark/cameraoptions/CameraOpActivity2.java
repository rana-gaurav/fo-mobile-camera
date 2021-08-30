package com.faceopen.camerabenchmark.cameraoptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.faceopen.AppActivity;
import com.faceopen.camerabenchmark.CameraActivity;
import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.SharedPreferenceManager;
import com.faceopen.camerabenchmark.adapter.CustomAdapter1;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CameraOpActivity2 extends AppActivity {

    GridView gridView;
    CustomAdapter1 adapter1;
    int selectedPos = 0;
    Set<String> listSelect = new HashSet<String>();
    String[] items = {"Under Light", "Under SunLight", "Left Side Light", "Right Side", "Light on Straight Face"};
    Integer[] logo = {

            R.drawable.ic_android,
            R.drawable.ic_android,
            R.drawable.ic_android,
            R.drawable.ic_android,
            R.drawable.ic_android};

    @BindView(R.id.checkMark)
    ImageView checkMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_op2);
        gridView = findViewById(R.id.gridView);
        adapter1 = new CustomAdapter1(this, items, logo);
        gridView.setAdapter(adapter1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // itemValue is here the Options of Different Lighting Conditions and passing
                // these text to next Activity to evaluate the whether it done or not.
                String itemValue = (String) gridView.getItemAtPosition(position);
                Log.d("item","Item is :"+itemValue);
                selectedPos = position;
                Intent intent = new Intent(CameraOpActivity2.this, CameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", itemValue);
                intent.putExtras(bundle);
                startActivityForResult(intent, selectedPos);
                //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == selectedPos) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("YYY", "onActivityResult " + selectedPos);
                if (selectedPos == 0)
                    SharedPreferenceManager.setCategory1(this, true);
                if (selectedPos == 1)
                    SharedPreferenceManager.setCategory2(this, true);
                if (selectedPos == 2)
                    SharedPreferenceManager.setCategory3(this, true);
                if (selectedPos == 3)
                    SharedPreferenceManager.setCategory4(this, true);
                if (selectedPos == 4)
                    SharedPreferenceManager.setCategory5(this, true);
                adapter1.refresh();
            }

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}