import java.awt.*;
import java.awt.event.*;

/**
 * Created by 13585 on 2017/2/22.
 */
public class Map extends Frame {

    PaintThread paintThread = new PaintThread();
    private static boolean gameOver = false;
    private boolean running = true;
    private static int score = 0;

    public static final int COLS = 30;
    public static final int ROWS = 30;
    public static final int BLOCK_SIZE =15;
    Snake snake = new Snake();
    Egg egg = new Egg(snake);

    Image offScreenImage = null;

    @Override
    public void update(Graphics graphics) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
        }
        Graphics graphicsOff = offScreenImage.getGraphics();
        paint(graphicsOff);
        graphics.drawImage(offScreenImage,0,0,null);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Map.score = score;
    }

    @Override
    public void paint(Graphics graphics) {
        Color c = graphics.getColor();
        graphics.setColor(Color.gray);
        graphics.fillRect(0,0,COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
        graphics.setColor(Color.DARK_GRAY);

        //draw lines
        for (int i=1;i<ROWS;i++){
            graphics.drawLine(0,BLOCK_SIZE*i,BLOCK_SIZE*COLS,BLOCK_SIZE*i);
        }
        for (int i=1;i<COLS;i++){
            graphics.drawLine(BLOCK_SIZE*i,0 ,BLOCK_SIZE*i,BLOCK_SIZE*ROWS);
        }
        graphics.setColor(Color.YELLOW);
        graphics.drawString("Score:"+score,10,60);
        if(gameOver){
            graphics.setFont(new Font("Arial",Font.BOLD,50));
            graphics.drawString("GAME OVER",80,180);
            paintThread.gameOver();
        }
        egg.draw(graphics);
        snake.eat(egg);
        this.snake.draw(graphics);
        //graphics.setColor(c);

    }

    public static void stop(){
        gameOver = true;
    }

    private class PaintThread implements Runnable{
        public void run() {
            while (running) {
                repaint();
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void gameOver(){
            running = false;
        }
    }

    private class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            snake.keyPressed(keyEvent);
        }
    }

    public void launch(){
        this.setLocation(200,200);
        this.setSize(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.addKeyListener(new KeyMonitor());
        paintThread.run();
    }


    public static void main(String[] args) {
        new Map().launch();
    }
}
