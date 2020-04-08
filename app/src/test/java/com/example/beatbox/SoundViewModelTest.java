package com.example.beatbox;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SoundViewModelTest {
    private BeatBox mBeatBox;
    private Sound mSound;
    private SoundViewModel mSubject;
    //在测试之前运行一次，这样就不用重复写测试前准备的代码
    @Before
    public void setUp() throws Exception {
        mBeatBox = mock(BeatBox.class);//创建一个虚拟的BeatBox，即使BeatBox有问题，也没事，因为类里面的方法不被执行
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);
    }

    @Test
    public void exposesSoundNameAsTitle() {
        assertThat(mSubject.getTitle(), is(mSound.getName()));
    }

    @Test
    public void callsBeatBoxPlayOnButtonClicked() {
        mSubject.onButtonClicked();
        verify(mBeatBox).play(mSound);//验证mBeatBox是否以mSound作为参数调用了play
    }
}
