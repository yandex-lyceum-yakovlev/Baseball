package io.github.tennis.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import static io.github.tennis.GameResources.*;

public class SoundManager {

    private final Sound hitSound;
    private final Sound scoreSound;
    private final Sound failSound;

    private final Music backgroundMusic;

    private float volume = 100.0f;
    private boolean enabled = true;

    public SoundManager() {
        hitSound = Gdx.audio.newSound(Gdx.files.internal(HIT_SOUND_PATH));
        scoreSound = Gdx.audio.newSound(Gdx.files.internal(SCORE_SOUND_PATH));
        failSound = Gdx.audio.newSound(Gdx.files.internal(FAIL_SOUND_PATH));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(BG_MUSIC_PATH));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(volume);
    }

    public void playHit() {
        if (enabled) hitSound.play(volume);
    }

    public void playScore() {
        if (enabled) scoreSound.play(volume);
    }

    public void playFail() {
        if (enabled) failSound.play(volume);
    }

    public void playMusic() {
        if (enabled && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        backgroundMusic.stop();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) stopMusic();
        else playMusic();
    }

    public void dispose() {
        hitSound.dispose();
        scoreSound.dispose();
        failSound.dispose();
        backgroundMusic.dispose();
    }
}
