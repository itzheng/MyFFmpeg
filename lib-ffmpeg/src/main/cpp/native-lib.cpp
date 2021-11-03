#include <jni.h>
#include <string>
#include "alog.cpp"

extern "C" {
#include "ffmpeg/libavutil/avutil.h"
}

/**获取 ffmpeg 的版本信息
 * ffmpeg/libavutil/avutil.h
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_org_itzheng_ffmpeg_FFmpegInfo_version(JNIEnv *env, jobject thiz) {
    const char *version = av_version_info();
    LOGV("version: %s", version);
    return env->NewStringUTF(version);
}

extern "C" {
#include "libavformat/avformat.h"
}
/**
 * 获取媒体文件信息
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_org_itzheng_ffmpeg_FFmpegInfo_fileInfo(JNIEnv *env, jobject thiz, jstring file_path) {
    const char *path = env->GetStringUTFChars(file_path, nullptr);
    LOGI("path %s", path);

    AVFormatContext *fmt_ctx = nullptr;
    AVDictionaryEntry *dictionaryEntry = nullptr;
    int ret;
    char *info_buffer = nullptr;

    //找到流信息
    ret = avformat_open_input(&fmt_ctx, path, nullptr, nullptr);
    if (ret < 0) {
        LOGE("error: %s", av_err2str(ret));
        goto end;
    }

    //解析 meta data
    while ((dictionaryEntry = av_dict_get(fmt_ctx->metadata, "", dictionaryEntry,
                                          AV_DICT_IGNORE_SUFFIX))) {
        LOGI("%s = %s \n", dictionaryEntry->key, dictionaryEntry->value);
    }
    // const char key_val_sep-->key-value分割符1个字符, const char pairs_sep-->结束符1个字符
    av_dict_get_string(fmt_ctx->metadata, &info_buffer, '=', '\n');
    LOGI("info:\n%s", info_buffer);

    end:
    LOGI("end");
    env->ReleaseStringUTFChars(file_path, path);
    if (fmt_ctx) {
        avformat_close_input(&fmt_ctx);
    }
    return env->NewStringUTF(info_buffer);
}