package com.example.beatbox;

import android.util.Log;

import androidx.databinding.BaseObservable;

/**
 * 为了让sound与布局文件联系，如果使用Fragment作为中转的话，必须要再定义一个专门针对Sound
 * 的fragment，这和Sound模型有冲突，所以定义这个ViewModel，来联系Sound和View
 */
public class SoundViewModel extends BaseObservable {
    private final String TAG = "SoundViewModel";
    private Sound mSound;
    private BeatBox mBeatBox;

    public SoundViewModel(BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    public Sound getSound() {
        return mSound;
    }

    //获取sound的名字
    public String getTitle() {
        return mSound.getName();
    }
    public void setSound(Sound sound) {
        mSound = sound;
        notifyChange();//针对继承的BaseObservable，只要有更新就会通知绑定类
    }

    public void onButtonClicked() {
        mBeatBox.play(mSound);
//        Log.d(TAG, "onButtonClicked: 已点击播放"+mSound.getName());
    }
}
