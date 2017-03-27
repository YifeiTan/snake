import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * Created by 13585 on 2017/2/22.
 */
public class Snake {
    private Node head = null;
    private Node tail = null;
    List<Node> nodeList  = new LinkedList<>();


    public Snake() {
        head = new Node(20,20,Dir.LEFT);
        tail = new Node( 20,20,Dir.LEFT);
        nodeList.add(head);
        nodeList.add(tail);
        this.addToTail();
    }

    public void addToTail(){
        Node node = null;
        switch (tail.dir){
            case LEFT:
                node = new Node(tail.row,tail.col+1,tail.dir);
                break;
            case RIGHT:
                node = new Node(tail.row,tail.col-1,tail.dir);
                break;
            case UP:
                node = new Node(tail.row+1,tail.col,tail.dir);
                break;
            case DOWN:
                node = new Node(tail.row-1,tail.col,tail.dir);
                break;
        }
        nodeList.add(node);
        tail = node;
    }

    public void addToHead(){
        Node node = null;
        switch (head.dir){
            case LEFT:
                node = new Node(head.row,head.col-1,head.dir);
                break;
            case RIGHT:
                node = new Node(head.row,head.col+1,head.dir);
                break;
            case UP:
                node = new Node(head.row-1,head.col,head.dir);
                break;
            case DOWN:
                node = new Node(head.row+1,head.col,head.dir);
                break;
        }
        nodeList.add(0,node);
        head = node;
    }

    public void draw(Graphics graphics){
        if (this.nodeList.size() <= 0) return ;
        move();
        for (int i =0; i<this.nodeList.size();i++){
            nodeList.get(i).draw(graphics);
        }


    }

    private void move(){
        addToHead();
        nodeList.remove(nodeList.size()-1);
        tail = nodeList.get(nodeList.size()-1);
        checkDead();
    }

    private void checkDead() {
        if(head.row<3 || head.col<1 ||head.row>Map.ROWS-2|| head.col>Map.COLS-2){
            Map.stop();
        }
        for (int i = 1;i<nodeList.size();i++){
            if(head.row == nodeList.get(i).row && head.col == nodeList.get(i).col){
                Map.stop();

            }
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                if (head.dir!=Dir.RIGHT)
                    head.dir = Dir.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if (head.dir!=Dir.LEFT)
                    head.dir = Dir.RIGHT;
                break;
            case KeyEvent.VK_UP:
                if (head.dir!=Dir.DOWN)
                    head.dir = Dir.UP;
                break;
            case KeyEvent.VK_DOWN:
                if (head.dir!=Dir.UP)
                    head.dir = Dir.DOWN;
                break;
        }
    }

    public void eat(Egg egg){
        if(this.getRect().intersects(egg.getRect())){
            egg.reAppear(this);
            addToTail();
            Map.setScore(Map.getScore()+1);
        }
    }

    private Rectangle getRect(){
        return new Rectangle(Map.BLOCK_SIZE*head.col,Map.BLOCK_SIZE*head.row,head.width,head.heigth);
    }

    public class Node{
        int width = Map.BLOCK_SIZE;
        int heigth = Map.BLOCK_SIZE;
        int row , col;
        Dir dir = Dir.LEFT;

        Node(int row, int col,Dir dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeigth() {
            return heigth;
        }

        public void setHeigth(int heigth) {
            this.heigth = heigth;
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

        void draw(Graphics graphics){
            Color c = graphics.getColor();
            graphics.setColor(Color.BLACK);
            graphics.fillRect(Map.BLOCK_SIZE*col,Map.BLOCK_SIZE*row,width,heigth);
            graphics.setColor(c);
        }
    }
}
