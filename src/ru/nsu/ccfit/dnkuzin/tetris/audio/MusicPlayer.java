package ru.nsu.ccfit.dnkuzin.tetris.audio;

import ru.nsu.ccfit.dnkuzin.tetris.exceptions.AudioException;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPlayer {
    public static boolean isMenuMusic = false;
    private static final List<String> tracks = List.of(
            "/music/KISH_father.wav",
            "/music/durak.wav",
            "/music/KISH_morning.wav",
            "/music/KISH_thief.wav");
    public static final String menuMusic = "/music/KINO.wav";
    public static final String gameOverMusic = "/music/night.wav";

    private static final ArrayList<String> buffer = new ArrayList<>();
    private static Clip currClip;
    private static boolean stoppedManually = false;
    public static void playNext(String path){
        URL resource = MusicPlayer.class.getResource(path);
        if (resource == null){
            throw new AudioException("Cannot find audio file: " + path);
        }

        stoppedManually = false;

        if (currClip != null) {
            currClip.stop();
            currClip.close();
        }

        try (AudioInputStream stream = AudioSystem.getAudioInputStream(resource))
        {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            currClip = clip;
            currClip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    if (!stoppedManually) {
                        String newPath = getSong();
                        playNext(newPath);
                    }
                }
            });
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void startGame() throws AudioException{
        if (currClip != null){
            if (!currClip.isRunning()){
                currClip.start();
            }else{
                return;
            }
        }else{
            String path = getSong();
            playNext(path);
        }
    }
    public static void stop(){
        stoppedManually = true;
        if (currClip != null){
            currClip.stop();
            currClip.close();
            currClip = null;
        }
    }
    public static void pause(){
        stoppedManually = true;
        if (currClip != null){
            currClip.stop();
        }
    }
    public static void startMenu() throws AudioException{
        if (currClip != null){
            if (!currClip.isRunning()){
                currClip.start();
            }else{
                return;
            }
        }else{
            playNext(menuMusic);
        }
    }
    public static void startGameOver() throws AudioException{
        if (currClip != null){
            if (!currClip.isRunning()){
                currClip.start();
            }else{
                return;
            }
        }else{
            playNext(gameOverMusic);
        }
    }

    private static void fillSongsBuffer(){
        buffer.clear();
        buffer.addAll(tracks);
        Collections.shuffle(buffer);
    }
    private static String getSong(){
        if (buffer.isEmpty()){
            fillSongsBuffer();
        }
        final String song = buffer.getFirst();
        buffer.removeFirst();
        return song;
    }
}
