package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.badlogic.gdx.math.MathUtils.random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static javax.swing.text.StyleConstants.Background;

public class Background extends ApplicationAdapter {
    // texture atlas that will help load in the images from the big image
    // this was created from running the texture packer (in Desktop Launcher)
    private TextureAtlas atlas;
    // sprite batch
    private SpriteBatch batch;
    // diamond image as the blocks
    private TextureRegion diamond;
    // current block's x and y positions
    private int currentY;
    private int currentX;
    
    // width and size of the diamond
    private int currentH;
    private int currentW;
    
    // width and height of screen
    public int width;
    public int height;
    // array of coordinates for every block added
    private Coordinates[] coordinates;
        
    // camera and viewport
    private OrthographicCamera camera;
    private Viewport view;
    private int camSpeed;
    
    // make a variable that shows the maximum number of squares on the screen at once
    private int maxOnScreen;
    
    // x and y coordinates
    public int x;
    public int y;
    
    // for the player movement
    private float playerx;
    private float playery;
    private float dx;
    private float dy;
    //image of the ball
    Texture img;


    private Background gameManager;
 
    private int score;

    // SCREEN IMAGES

    private Texture Background;

    private Texture start; 
    
    
    // boolean statements to switch screens
    // game screen
    private boolean gameScreen;
 
    private boolean startScreen;
    
    //score updater
    private int prePos;
    private Background game;

    @Override
    public void create() {

       this.gameManager = game;
        // set up the camera and view
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // move the camera to the center
        this.camera.position.set(width / 2, height / 2, 0);
        // make sure to apply the changes
        this.camera.update();

        this.view = new FitViewport(width, height, camera);
        view.apply();
//        batch.begin();
        // diamond image
        this.atlas = new TextureAtlas("blocks/blocks.atlas");
        batch = new SpriteBatch();
        diamond = atlas.findRegion("Diamond");

        // keep track of the diamonds
        int numberOfDiamonds = 10000;
        coordinates = new Coordinates[20000];
        
        // path can be either up right or up left
        String path = "upRight";
        
        // coordinates of first diamond (starting point)
        currentX = 400;
        currentY = 0;
        
        // size of the diamond
        currentH = 100;
        currentW = 100;
        
        // keeps track of how many diamond's are drawn
        int tracker = 0;

        // the max number of diamonds on the height of the screen divided by 50 because the blocks move up 50 y-coords every time
        maxOnScreen = height/50;
        
        // set the camera speed
        camSpeed = 0;
        
        // coordinates to draw the diagram
        for (int i = maxOnScreen; i < numberOfDiamonds; i++) {
            int randomPath = random(1, 2);
            // the path is going up and right
            if (path == "upRight") {
                for (int j = 0; j <= randomPath - 1; j++) {
                    // add 100 to the x coordinate to right
                    currentX = currentX + (j * 50);
                    // add 100 to the y coordinate to move up
                    currentY = currentY + (j * 50);
                    // make the coordinates at the specific tracker the current coordinates so that it is the current image
                    // coordinates of next image rely on current image, and keep making next image current image
                    coordinates[tracker] = new Coordinates(currentX, currentY);
                    // add to the tracker
                    tracker++;
                }
                // make the path go left next so it is alternating between up left and up right
                path = "upLeft";
                // the path is going up and left
            } else {
                // the path is up left
                for (int j = 0; j <= randomPath - 1; j++) {
                    // subtract 100 from the x coordinate to move left
                    currentX = currentX - (j * 50);
                    // add 100 to the y coordinate to move up
                    currentY = currentY + (j * 50);
                    // make the coordinates at the specific tracker the current coordinates so that it is the current image
                    // coordinates of next image rely on current image, and keep making next image current image
                    coordinates[tracker] = new Coordinates(currentX, currentY);
                    // add to the tracker
                    tracker++;
                }
                path = "upRight";
            }
            // make sure the path does not go off the margins of the page
            // make the path turn right if it is too far left
            if (currentX <= 100) {
                path = "upRight";
            }
            // make the path turn left if it is too far right
            if (currentX >= 700) {
                path = "upLeft";
            
            }
        }
        
        this.playerx = playerx;
        this.playery = playery;

        this.dx = 420;
        this.dy = 30;
        img = new Texture("ball.png");
   
        Background = new Texture("background.jpg");
        // rules button
        // start button
        start = new Texture("start.png");
        
        // boolean variables default set
        this.gameScreen = false;

        this.startScreen = true;

  

    }
    
    
    @Override
    public void render() {

        // draw the background
        // make the background clear
        Gdx.gl.glClearColor(1, 1, 1, 1);
        //fill in the background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //make the blocks

        //drawing the background 
        //begin the drawing here
        batch.begin();
        // use a for loop to go through the positions of the blocks
        for (int i = 0; i < coordinates.length; i++) {
            // make sure the coordinates are not null
            if (coordinates[i] != null) {
                // drawing the blocks 
                batch.draw(diamond, coordinates[i].x, coordinates[i].y, currentH, currentW);
                }
            }
        batch.draw(img, dx, dy);
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.dx += 2;
            this.dy += 2;
            batch.draw(img, dx, dy);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.dx += -2;
            this.dy += 2;
            batch.draw(img, dx, dy);
        }

        // keyboard input
        // go to the game
          if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                gameScreen = true;
                startScreen = false;
                
            }
           
        // start screen
        if (gameScreen == false && startScreen == true) {

            // draw homepage background
            batch.draw(Background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            // draw start button
            batch.draw(start, 335, 235, 480, 70);

      batch.end();

    }
    }

    @Override
    public void dispose() {
    
    }

    // randomize the path
    int random(int min, int max) {
        int random = (int) (Math.random() * 2);
        int entry;
        if (1 <= 2) {
            entry = 1;
        } else {
            entry = 2;
        }
        return random + entry;
    }

    // create the coordinates
    public class Coordinates {
        public int x;
        public int y;

        public Coordinates(int X, int Y) {
            x = X;
            y = Y;
        }
    }
    
}
