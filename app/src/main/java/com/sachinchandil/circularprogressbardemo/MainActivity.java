package com.sachinchandil.circularprogressbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.sachinchandil.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {


    private SeekBar seekBar;
    private CircularProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (CircularProgressBar) findViewById(R.id.progress);
        seekBar = (SeekBar) findViewById(R.id.seekBarProgress);
        progressBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
