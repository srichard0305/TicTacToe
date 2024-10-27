package io.github.StevenRichard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class GameOverScreen implements Screen {
    Game game;
    Viewport viewport;
    Stage stage;

    SpriteBatch spriteBatch;

    Sprite board;
    ArrayList<Sprite> playerList;
    ArrayList<Sprite> opponentList;

    public GameOverScreen(Game game, ArrayList<Sprite> playerList, ArrayList<Sprite> opponentList){

        this.game = game;
        this.playerList = playerList;
        this.opponentList = opponentList;
        board = new Sprite(new Texture("board.png"));
        board.setPosition(0,100);
        spriteBatch = new SpriteBatch();

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table startScreenTable = new Table();
        startScreenTable.center();
        startScreenTable.setFillParent(true);

        Label welcomeLabel = new Label("Game Over", font);
        Label clickToPlayLabel = new Label("CLICK ANYWHERE TO PLAY", font);

        startScreenTable.add(welcomeLabel).expandX();
        startScreenTable.row();
        startScreenTable.add(clickToPlayLabel).expandX().padTop(10f);

        stage.addActor(startScreenTable);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(125/255f,125/255f,125/255f,1);

        spriteBatch.begin();
        board.draw(spriteBatch);
        for(Sprite sprite : playerList){
            sprite.draw(spriteBatch);
        }
        for(Sprite sprite : opponentList){
            sprite.draw(spriteBatch);
        }
        spriteBatch.end();
        if(Gdx.input.justTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

