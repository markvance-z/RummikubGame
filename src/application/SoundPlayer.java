package application;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {
    public void tileSound() {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src\\media\\tileSound.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
