package io.github.StevenRichard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen implements Screen {
    Game game;
    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    Sprite boardSprite, X, O;
    SpriteBatch spriteBatch;
    ArrayList<Sprite> playerList, opponentList;
    String [][] board;
    final String player = "X";
    final String opponent = "O";
    
    Actor button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    GameScreen(Game game){
        this.game = game;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport);

        boardSprite = new Sprite(new Texture("board.png"));
        boardSprite.setPosition(0,100);
        X = new Sprite(new Texture("X.png"));
        O = new Sprite(new Texture("O.png"));
        spriteBatch = new SpriteBatch();

        playerList = new ArrayList<>();
        opponentList = new ArrayList<>();

        board = new String[3][3];
        // initialize board to empty spaces
        for(int i= 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++)
                board[i][j] = "-";
        }

        initClickableTiles();


    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(125/255f,125/255f,125/255f,1);
        spriteBatch.begin();
        boardSprite.draw(spriteBatch);
        spriteBatch.end();
        stage.draw();

    }

    public void initClickableTiles(){
        button0 = new Actor();
        //button0.setPosition(180,368);
        button0.setBounds(180, 368, 100,100);
        button0.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(0);
            }

        });
        stage.addActor(button0);

        button1 = new Actor();
        //button0.setPosition(180,368);
        button1.setBounds(300, 368, 100,100);
        button1.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(1);
            }

        });
        stage.addActor(button1);

        button2 = new Actor();
        //button0.setPosition(180,368);
        button2.setBounds(420, 368, 100,100);
        button2.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(2);
            }

        });
        stage.addActor(button2);

        button3 = new Actor();
        //button0.setPosition(180,368);
        button3.setBounds(180, 246, 100,100);
        button3.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(3);
            }

        });
        stage.addActor(button3);

        button4 = new Actor();
        //button0.setPosition(180,368);
        button4.setBounds(300, 246, 100,100);
        button4.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(4);
            }

        });
        stage.addActor(button4);

        button5 = new Actor();
        //button0.setPosition(180,368);
        button5.setBounds(420, 246, 100,100);
        button5.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(5);
            }

        });
        stage.addActor(button5);

        button6 = new Actor();
        //button0.setPosition(180,368);
        button6.setBounds(180, 124, 100,100);
        button6.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(6);
            }

        });
        stage.addActor(button6);

        button7 = new Actor();
        //button0.setPosition(180,368);
        button7.setBounds(300, 124, 100,100);
        button7.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(7);
            }

        });
        stage.addActor(button7);

        button8 = new Actor();
        //button0.setPosition(180,368);
        button8.setBounds(420, 124, 100,100);
        button8.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                addPiece(8);
            }

        });
        stage.addActor(button8);

        Gdx.input.setInputProcessor(stage);
    }

    private void addPiece(int piece){
        switch(piece){
            case 0:
                Gdx.app.log("", "button0");
                break;
            case 1:
                Gdx.app.log("", "button1");
                break;
            case 2:
                Gdx.app.log("", "button2");
                break;
            case 3:
                Gdx.app.log("", "button3");
                break;
            case 4:
                Gdx.app.log("", "button4");
                break;
            case 5:
                Gdx.app.log("", "button5");
                break;
            case 6:
                Gdx.app.log("", "button6");
                break;
            case 7:
                Gdx.app.log("", "button7");
                break;
            case 8:
                Gdx.app.log("", "button8");
                break;

        }

    }

    private boolean checkWinningState(String [][] board, String player){
        // check horizontal win
        for(int i= 0; i < 1; i++){
            if(board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player))
                return true;
        }

        //check vertical win
        for(int i= 0; i < 1; i++){
            if(board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player))
                return true;
        }

        //check diagonals
        if(board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player))
            return true;
        if(board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player))
            return true;

        return false;
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
    public void show() {

    }


    @Override
    public void dispose() {

    }
}
