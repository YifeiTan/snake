import java.awt.*;
import java.util.Random;

/**
 * Created by 13585 on 2017/2/22.
 */
public class Egg {
    int row, col;
    int width = Map.BLOCK_SIZE;
    int heigth = Map.BLOCK_SIZE;
    Color color = Color.DARK_GRAY;
    private static Random r = new Random();

    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Egg(Snake snake){
        reAppear(snake);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void reAppear(Snake snake){
        this.row = r.nextInt(Map.ROWS);
        this.col = r.nextInt(Map.COLS);
        if(row<3||col<3||row>Map.ROWS-4||col>Map.COLS-4){
            reAppear(snake);
        }
        for(int i = 0;i<snake.nodeList.size();i++){
            if(snake.nodeList.get(i).getCol()==this.col&&snake.nodeList.get(i).getRow()==this.row){
                reAppear(snake);
            }
        }
    }

    public Rectangle getRect(){
        return new Rectangle(Map.BLOCK_SIZE*this.col,Map.BLOCK_SIZE*this.row,this.width,this.heigth);
    }



    public void draw(Graphics graphics){
        Color c = graphics.getColor();
        graphics.setColor(color);
        graphics.fillOval(Map.BLOCK_SIZE*col,Map.BLOCK_SIZE*row,width,heigth);
        graphics.setColor(c);

    }
}
