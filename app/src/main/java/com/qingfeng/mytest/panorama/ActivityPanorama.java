package com.qingfeng.mytest.panorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qingfeng.mytest.R;
import com.zph.glpanorama.GLPanorama;

/**
 * 全景图片
 */
public class ActivityPanorama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        init();
    }

    private void init(){
        GLPanorama panorama = (GLPanorama) findViewById(R.id.glp);
        panorama.setGLPanorama(R.drawable.img2);
    }
}
