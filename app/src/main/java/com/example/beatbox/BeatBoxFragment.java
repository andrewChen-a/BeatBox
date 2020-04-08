package com.example.beatbox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beatbox.databinding.FragmentBeatBoxBinding;
import com.example.beatbox.databinding.ListItemSoundBinding;

import java.util.List;


public class BeatBoxFragment extends Fragment {
    final String TAG = "BeatBoxFragment";
    private BeatBox mBeatBox;
    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fragment中的保护实例不被销毁的方法，所在的activity被销毁时，他将保留传给新的activity,解决设备旋转问题
        setRetainInstance(true);
        mBeatBox = new BeatBox(getActivity());
        Log.d(TAG, "onCreate: ");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_beat_box,container,false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        Log.d(TAG, "onCreateView: ");
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.relese();
    }
    private class SoundHolder extends RecyclerView.ViewHolder {
        private ListItemSoundBinding mListItemSoundBinding;
        private SoundHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            mListItemSoundBinding=binding;
            //在数据绑定对象中设置ViewModel，这样mListItemSoundBinding
            // 就通过ViewModel获得了BeatBox
            mListItemSoundBinding.setViewModel(new SoundViewModel(mBeatBox));
        }
        //更新新的sound数据
        public void bind(Sound sound) {
            mListItemSoundBinding.getViewModel().setSound(sound);
            mListItemSoundBinding.executePendingBindings();//强迫recyclerView刷新，更加流畅
        }
    }
    /**
     * 适配器获取每一个绑定的item，返回到SoundHolder
     */
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        private List<Sound> mSounds;
        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }
        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding listItemSoundBinding = DataBindingUtil.inflate(inflater, R.layout.list_item_sound, parent, false);
            return new SoundHolder(listItemSoundBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
