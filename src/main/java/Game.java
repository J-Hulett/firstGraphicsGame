import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public final String TITLE = "2D Space Game";

    private boolean running = false;
    private Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;

    private Player p;

    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            spriteSheet = loader.loadImage("Sprite_Sheet.png");

        }catch(IOException e){
            e.printStackTrace();
        }

        addKeyListener(new KeyInput(this));

        p = new Player(200,200, this);
    }

    //Call this to start the game thread
    private synchronized void start(){
        if(running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    //Call this to stop the game Thread
    private synchronized void stop(){
        if(!running)
            return;

            running = false;

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(1);
    }

    @Override
    public void run(){
        init();
        //System time in nanoseconds
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0; //Sets the tick rate for FPS
        double ns = 1000000000 / amountOfTicks;
        double delta = 0; //Sets a value so fps can be adjusted if it falls behind or gets ahead.
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(running){
            //this is the game loop
            long now = System.nanoTime(); //Gets the time now to compare it to "lastTime";
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);
                updates = 0;
                frames = 0;
            }

        }
        stop();
    }

    private void tick(){
        p.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        ///////////////////////////////////
        //////Draw Stuff In Here///////////

        g.drawImage(image, 0 ,0, getWidth(), getHeight(), this);

        p.render(g);

        ///////////////////////////////////
        g.dispose();
        bs.show();

    }


    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT){
            p.setX(p.getX() + 5);
        }else if(key == KeyEvent.VK_LEFT){
            p.setX(p.getX() - 5);
        }else if(key == KeyEvent.VK_DOWN){
            p.setY(p.getY() + 5);
        }else if(key == KeyEvent.VK_UP){
            p.setY(p.getY() - 5);
        }
    }


    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

    }

    public static void main(String[] args) {
        Game game = new Game();

        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public BufferedImage getSpriteSheet(){
        return spriteSheet;
    }

}
