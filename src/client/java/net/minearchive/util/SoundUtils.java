package net.minearchive.util;

import net.minearchive.AccessMC;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.util.Objects;

public class SoundUtils implements AccessMC {
    public static void playSound(String name, int volume) {
        try {
            Clip clip = AudioSystem.getClip();
            BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull(SoundUtils.class.getResourceAsStream("/assets/uzaware/sounds/" + name)));
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(bis);
            clip.open(inputStream);
            FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue((floatControl.getMaximum() - floatControl.getMinimum() * ((float) volume / 100f)) + floatControl.getMinimum());
            clip.start();
        } catch (Exception ignored) { }
    }
}
