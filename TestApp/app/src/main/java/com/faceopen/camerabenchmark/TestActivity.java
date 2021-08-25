package com.faceopen.camerabenchmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.joanfuentes.hintcase.HintCase;
//import com.joanfuentes.hintcaseassets.hintcontentholders.SimpleHintContentHolder;
//import com.joanfuentes.hintcaseassets.shapeanimators.RevealCircleShapeAnimator;
//import com.joanfuentes.hintcaseassets.shapeanimators.UnrevealCircleShapeAnimator;
//import com.joanfuentes.hintcaseassets.shapes.CircularShape;

public class TestActivity extends AppCompatActivity {
    Button btn;
    LinearLayoutCompat parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btn = findViewById(R.id.btn);
        parent = findViewById(R.id.parent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //showHint();
            }
        }, 1000);
        //showHint();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SimpleHintContentHolder blockInfo = new SimpleHintContentHolder.Builder(getBaseContext())
//                        .setContentTitle("FAB button power!")
//                        .setContentText("The FAB button is gonna help you with the main action of every screen.")
//                        .setTitleStyle(R.style.title)
//                        .setContentStyle(R.style.content)
//                        .build();
//                new HintCase(v.getRootView())
//                        .setTarget(findViewById(R.id.btn), new CircularShape())
//                        .setShapeAnimators(new RevealCircleShapeAnimator(),
//                                new UnrevealCircleShapeAnimator())
//                        .setHintBlock(blockInfo)
//                        .show();
//            }
//        });
//    }
//
//    public void showHint() {
//        SimpleHintContentHolder blockInfo = new SimpleHintContentHolder.Builder(getApplicationContext())
//                .setContentTitle("FAB button power!")
//                .setContentText("The FAB button is gonna help you with the main action of every screen.")
//                .setTitleStyle(R.style.title)
//                .setContentStyle(R.style.content)
//                .build();
//        new HintCase(getWindow().getDecorView())
//                .setTarget(findViewById(R.id.btn), new CircularShape())
//                .setShapeAnimators(new RevealCircleShapeAnimator(),
//                        new UnrevealCircleShapeAnimator())
//                .setHintBlock(blockInfo)
//                .show();
//    }
    }
}