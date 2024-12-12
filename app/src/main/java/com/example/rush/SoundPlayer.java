package com.example.rush;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
private SoundPool countDownSoundPool;
private SoundPool countSoundPool;
private static int countDownSound;
private static  int countSound;


    public SoundPlayer(Context context){
        countDownSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        countDownSound = countDownSoundPool .load(context,R.raw.bare, 1);

        countSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        countSound = countDownSoundPool .load(context,R.raw.count, 1);
    }

    public void countDownSound() {countDownSoundPool.play(countDownSound,1f,1f,0,0,1);}
    public void countSound() {countDownSoundPool.play(countSound,1f,1f,0,0,1);}

}
