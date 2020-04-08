package com.example.beatbox;

import android.content.Intent;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId; //Sound Pool需要预加载音频，需要设置自己的ID

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
    /**
     * 获得wav名字并修改
     * @param assetPath
     */
    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }
}
