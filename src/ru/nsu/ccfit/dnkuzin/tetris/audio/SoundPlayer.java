package ru.nsu.ccfit.dnkuzin.tetris.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {

    public static void play(SoundEffect effect) {
        try {
            InputStream raw = SoundPlayer.class.getResourceAsStream(effect.getPath());
            if (raw == null) {
                throw new IllegalArgumentException("Sound not found: " + effect.getPath());
            }

            BufferedInputStream buffered = new BufferedInputStream(raw);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(buffered);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (Exception e) {
            System.err.println("Cannot play sound: " + effect.getPath());
        }
    }
}