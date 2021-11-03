package org.itzheng.myffmpeg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.itzheng.ffmpeg.FFmpegInfo;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        setOnClickListener(R.id.btnVersion);
        setOnClickListener(R.id.btnMediaInfo);

    }

    /**
     * 设置点击事件
     *
     * @param viewId
     */
    private void setOnClickListener(int viewId) {
        findViewById(viewId).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVersion:
                showToast("版本号：" + FFmpegInfo.getInstance().version());
                break;
            case R.id.btnMediaInfo:
                showMediaInfo();

                break;
        }
    }

    private void showMediaInfo() {
        String fileName = "sintel.mp4";
//        String fileName = "Ring.ogg";
        File file = new File(getCacheDir().getAbsolutePath(), fileName);
        if (!file.exists()) {
            try {
                Log.w(TAG, "copy file: ");
                CopyFileUtils.copyFile(getAssets().open(fileName), file);
            } catch (IOException e) {
                e.printStackTrace();
                showToast(e.getMessage());
                return;
            }
        }
        String info = FFmpegInfo.getInstance().fileInfo(file.getPath());
        showToast(info);
    }

    /**
     * 弹出信息
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
