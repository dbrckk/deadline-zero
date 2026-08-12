package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.config.GameConfig;

public final class MenuScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private float t;
    public MenuScreen(DeadlineZeroGame game) { this.game = game; font.getData().setScale(2.2f); }
    @Override public void render(float delta) {
        t += delta;
        Gdx.gl.glClearColor(.012f,.018f,.027f,1); Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for(int i=0;i<14;i++) { float y=(i+1)*h/15f; float a=.025f + .015f*(float)Math.sin(t*1.3+i); shapes.setColor(.15f,.7f,.9f,a); shapes.rect(0,y,w,1.5f); }
        shapes.setColor(.05f,.75f,1f,.12f); shapes.circle(w*.5f,h*.48f,Math.min(w,h)*.24f,96);
        shapes.end();
        batch.begin();
        font.setColor(Color.WHITE); font.draw(batch, GameConfig.TITLE, 0,h*.68f,w, Align.center,false);
        font.getData().setScale(.72f); font.setColor(.52f,.9f,1f,1); font.draw(batch,"SURVIVE THE LAST PROTOCOL",0,h*.59f,w,Align.center,false);
        font.setColor(Color.LIGHT_GRAY); font.draw(batch,"TAP TO DEPLOY  •  WASD / ARROWS",0,h*.34f,w,Align.center,false);
        font.getData().setScale(2.2f); batch.end();
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) game.startRun();
    }
    @Override public void dispose(){ batch.dispose(); font.dispose(); shapes.dispose(); }
}
