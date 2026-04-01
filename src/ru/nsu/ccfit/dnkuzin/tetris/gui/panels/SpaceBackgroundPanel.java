package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SpaceBackgroundPanel extends JPanel {
    int[] starsX;
    int[] starsY;
    int[] starSize;
    Random random = new Random();
    Timer backgroundTimer;
    int countOfStars = 300;

    int width = 600;
    int height = 800;

    float[] brightStars;
    float[] brightDelta;
    float brightDeltaStar = 0.02f;
    protected SpaceBackgroundPanel(){
        initStars();
        backgroundTimer = new Timer(40, e -> {
            updateStars();
            repaint();
        });
        backgroundTimer.start();
    }
    protected void initStars(){
        starsX = new int[countOfStars];
        starsY = new int[countOfStars];
        starSize = new int[countOfStars];
        brightStars = new float[countOfStars];
        brightDelta = new float[countOfStars];

        for (int i = 0; i < countOfStars; i++){
            starsX[i] = random.nextInt(width);
            starsY[i] = random.nextInt(height);
            starSize[i] = random.nextInt(3) + 1;
            brightStars[i] = random.nextFloat();
            brightDelta[i] = (brightStars[i] >= 0.9f) ? -brightDeltaStar : brightDeltaStar;
        }
    }
    protected void updateStars(){
        int fallTick = 2;
        for (int i = 0; i < countOfStars; i++){
            starsY[i] = (starsY[i] + fallTick);
            if (starsY[i] >= height){
                starsY[i] %= height;
                starsX[i] = random.nextInt(width);
            }
            if (brightStars[i] >= 0.9f){
                brightStars[i] = 0.9f;
                brightDelta[i] = -brightDeltaStar;
            }else if (brightStars[i] <= 0.1f){
                brightStars[i] = 0.1f;
                brightDelta[i] = brightDeltaStar;
            }
            brightStars[i] = brightStars[i] + brightDelta[i];

        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(18, 18, 22));
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < countOfStars; i++){
            int alpha = (int)(brightStars[i]*255);
            g2.setColor(new Color(255, 255, 255, alpha));
            g2.fillOval(starsX[i], starsY[i], starSize[i], starSize[i]);
        }
        g2.setColor(new Color(255, 255, 255));

        g2.dispose();
    }

}
