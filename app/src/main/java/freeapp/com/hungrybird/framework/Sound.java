package freeapp.com.hungrybird.framework;


import android.media.SoundPool;

public class Sound {

    int soundId;
    SoundPool soundPool;

    public Sound(int soundId, SoundPool soundPool) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

}
