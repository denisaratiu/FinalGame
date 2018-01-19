package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

    SpriteBatch batch;
    private float x;
    private float y;
    private float dx;
    private float dy;
    Texture img;

    @Override
    public void create() {
        this.x = x;
        this.y = y;

        this.dx = 395;
        this.dy = 10;
        batch = new SpriteBatch();
        img = new Texture("ball.png");

        

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, dx, dy);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.dx += 5;
            this.dy += 5;
            batch.draw(img, dx, dy);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.dx += -5;
            this.dy += 5;
            batch.draw(img, dx, dy);
        }
        this.x = this.x + this.dx;
                this.y = this.y + this.dy;
        
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
