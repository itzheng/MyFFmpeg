package org.itzheng.ffmpeg;

public class FFmpegInfo {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static FFmpegInfo _instance = new FFmpegInfo();

    public static FFmpegInfo getInstance() {
        return _instance;
    }

    /**
     * FFmpeg 版本信息
     *
     * @return
     */
    public native String version();

    /**
     * 媒体文件信息
     *
     * @param path
     * @return
     */
    public native String fileInfo(String path);
}
