package com.example.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理assets资源,创建Sound,维护Sound的集合
 */
public class BeatBox {
    //日志记录
    private static final String TAG = "BeatBox";
    private List<Sound> mSounds = new ArrayList<>();
    //音频播放池
    private static final int MAX_SOUNDS = 5;
    //存储资源目录
    private static final String SOUND_FOLDER = "sample_sounds";
    //访问assets的类
    private AssetManager mAssetManager;
    private SoundPool mSoundPool;
    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        //指定最大播放音频数，确定音频流类型，指定采样率
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        loadSounds();
    }

    /**
     * 播放音乐
     * @param sound
     */
    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void relese() {
        mSoundPool.release();
    }
    /**
     * 加载文件夹里面的声音
     */
    private void loadSounds() {
        String [] soundNames;
        try {
            soundNames = mAssetManager.list(SOUND_FOLDER);//列出文件夹下所有的文件名
            Log.i(TAG, "loadSounds: " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.e(TAG, "loadSounds: could not list assets",ioe );
            return;
        }
        for (String filename : soundNames) {
            try {
                String assetPath = SOUND_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);  //每获得一个音频就将其加载
                mSounds.add(sound);
            } catch (IOException e) {
                Log.e(TAG, "loadSounds: "+filename,e );
            }

        }

    }

    /**
     * 用AssetFileDescriptor打开对应路径的音频，获得对应Id
     * @param sound
     * @throws IOException
     */
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(assetFileDescriptor, 1);
        sound.setSoundId(soundId);

    }
    public List<Sound> getSounds() {
        return mSounds;
    }
}
