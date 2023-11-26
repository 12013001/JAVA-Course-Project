package minesweeper;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Music {
    public Clip clip;
    public void playMusic(String location){
        try {
            File add = new File(location);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(add);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void over(){
        clip.stop();
    }

    public void playMusic1(String location) {
        try {
            try {
                File add = new File(location);
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(add);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
