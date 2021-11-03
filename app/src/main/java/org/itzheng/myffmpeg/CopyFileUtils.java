package org.itzheng.myffmpeg;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CopyFileUtils {
    private static final String TAG = "CopyFileUtils";

    /**
     * 复制文件
     *
     * @param inputStream getAssets().open(fileName);
     * @param file        new File(getCacheDir().getAbsolutePath(), fileName);
     * @return
     */
    public static File copyFile(InputStream inputStream, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[8192];
            int byteCount;
            while ((byteCount = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteCount);
            }
            fileOutputStream.flush();

            return file;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
