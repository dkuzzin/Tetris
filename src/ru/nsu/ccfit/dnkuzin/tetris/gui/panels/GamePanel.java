package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;

import ru.nsu.ccfit.dnkuzin.tetris.controller.GameController;
import ru.nsu.ccfit.dnkuzin.tetris.exceptions.BoardException;
import ru.nsu.ccfit.dnkuzin.tetris.gui.Fonts;
import ru.nsu.ccfit.dnkuzin.tetris.model.*;
import ru.nsu.ccfit.dnkuzin.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.dnkuzin.tetris.model.tetromino.TetrominoType;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GamePanel extends SpaceBackgroundPanel {
    private final GameModel gameModel;
    private static final int CELL_SIZE = 40;

    private static final Color GRID_COLOR = new Color(32, 32, 36);
    private static final Color EMPTY_CELL_COLOR = new Color(22, 22, 26);


    public GamePanel(GameModel gameModel, GameController controller){
        this.gameModel = gameModel;
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                controller.handleKeyPressed(e.getKeyCode());
            }
            @Override
            public void keyReleased(KeyEvent e){
                controller.handleKeyReleased(e.getKeyCode());
            }
        });
    }

    private Color getColor(TetrominoType tetrominoType){
        return switch (tetrominoType){
            case I -> new Color(80, 220, 230);
            case O -> new Color(230, 210, 70);
            case T -> new Color(170, 90, 220);
            case S -> new Color(90, 200, 120);
            case Z -> new Color(220, 90, 90);
            case J -> new Color(70, 120, 220);
            case L -> new Color(230, 150, 70);
        };
    }

    //Dimension - хранение width и height
    @Override
    public Dimension getPreferredSize(){
        int width = gameModel.getBoard().getSizeX() * CELL_SIZE + 200;
        int height = gameModel.getBoard().getSizeY() * CELL_SIZE;

        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        drawUI(g);
        drawGrid(g);
        drawLocked(g);
        drawCurrentTetromino(g);
        drawNextTetromino(g);
    }

    private void drawUI(Graphics g){
        int offsetX = gameModel.getBoard().getSizeX() * CELL_SIZE;
        int width = 200;
        int y = 50;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(18, 18, 22));
        g2d.fillRect(offsetX, 0, width, getHeight());

        g.setColor(Color.WHITE);
        g.setFont(Fonts.getTitleFont(16f));

        g.drawString("Очки:", offsetX + 20, y);

        y += 30;
        g.drawString(String.valueOf(gameModel.getScore()), offsetX + 20, y);

        y += 50;
        g.drawString("Уровень:", offsetX + 20, y);

        y += 30;
        g.drawString(String.valueOf(gameModel.getLevel()), offsetX + 20, y);
        y += 60;
        g.drawString("Следующая:", offsetX + 20, y);
    }
    private void drawNextTetromino(Graphics g){
        Tetromino tetromino = gameModel.getNextTetromino();
        if (tetromino == null){
            return;
        }

        int offsetX = gameModel.getBoard().getSizeX() * CELL_SIZE;

        int startPixelX = offsetX + 20;
        int startPixelY = 250;

        Coordinate[] cells = tetromino.getLocalCells();
        for (Coordinate cell : cells){
            int pixelX = startPixelX + (cell.x() * CELL_SIZE);
            int pixelY = startPixelY + (cell.y() * CELL_SIZE);

            drawBlockAbsolute(g, pixelX, pixelY);
        }
    }

    private void drawCurrentTetromino(Graphics g){
        Tetromino tetromino = gameModel.getCurrTetromino();
        if (tetromino == null){
            return;
        }

        Coordinate[] cells = tetromino.getGlobalCells();
        for (Coordinate i : cells){
            drawNeonBlock(g,getColor(tetromino.getType()), i.x(), i.y());
        }
    }

    private void drawGrid(Graphics g){
        int width = gameModel.getBoard().getSizeX();
        int height = gameModel.getBoard().getSizeY();

        g.setColor(GRID_COLOR);
        for (int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int pixelX = x * CELL_SIZE;
                int pixelY = y * CELL_SIZE;

                g.setColor(EMPTY_CELL_COLOR);

                g.setColor(GRID_COLOR);
                g.drawRect(pixelX, pixelY, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    private void drawBlockAbsolute(Graphics g, int pixelX, int pixelY) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(pixelX, pixelY, CELL_SIZE, CELL_SIZE);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(pixelX + 1, pixelY + 1, CELL_SIZE - 2, CELL_SIZE - 2);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(pixelX, pixelY, CELL_SIZE, CELL_SIZE);
    }


    private void drawNeonBlock(Graphics g, Color color, int x, int y) {
        int pixelX = x * CELL_SIZE;
        int pixelY = y * CELL_SIZE;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color.darker().darker());
        g2d.fillRect(pixelX, pixelY, CELL_SIZE, CELL_SIZE);

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(pixelX + 1, pixelY + 1, CELL_SIZE - 2, CELL_SIZE - 2);

        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80));
        g2d.fillRect(pixelX, pixelY, 5, 5);
        g2d.fillRect(pixelX + CELL_SIZE - 5, pixelY, 5, 5);
        g2d.fillRect(pixelX, pixelY + CELL_SIZE - 5, 5, 5);
        g2d.fillRect(pixelX + CELL_SIZE - 5, pixelY + CELL_SIZE - 5, 5, 5);

        g.setColor(color.darker().darker());
        g.drawRect(pixelX, pixelY, CELL_SIZE, CELL_SIZE);
    }

    private void drawLocked(Graphics g){
        int width = gameModel.getBoard().getSizeX();
        int height = gameModel.getBoard().getSizeY();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try{
                    TetrominoType cellType = gameModel.getBoard().getCell(x, y);
                    if (cellType != null) {
                        drawNeonBlock(g,getColor(cellType), x, y);
                    }
                }catch (BoardException e){
                    System.err.println("Incorrect board coordinates");
                }
            }
        }
    }
}
