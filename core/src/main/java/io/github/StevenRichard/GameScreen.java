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
import java.util.Objects;

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
    boolean isPlayerTurn;

    final int ROW = 3;
    final int COL = 3;

    QLearning qLearning;

    Actor button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    GameOverScreen gameOverScreen;
    GameScreen(Game game){
        this.game = game;

        qLearning = new QLearning();
        // initiate learning
        qLearning.qLearning();

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

        isPlayerTurn = true;


    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(125/255f,125/255f,125/255f,1);
        stage.draw();

        if(checkWinningState(board, player))
            gameOver();
        else if(checkWinningState(board, opponent))
            gameOver();
        else if(checkDraw(board))
            gameOver();
        
        if(!isPlayerTurn){
            Node action = qLearning.chooseAction(board, 0);
            if(action == null)
                return;
            setOpponentAction((action.x*3) + action.y);
            isPlayerTurn = true;
        }

        spriteBatch.begin();
        boardSprite.draw(spriteBatch);
        for(Sprite sprite : playerList){
            sprite.draw(spriteBatch);
        }
        for(Sprite sprite : opponentList){
            sprite.draw(spriteBatch);
        }
        spriteBatch.end();



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

    private void setOpponentAction(int space){
        switch(space){
            case 0:
                board[0][0] = opponent;
                Sprite piece1 = new Sprite(new Texture("O.png"));
                piece1.setPosition(200, 395);
                opponentList.add(piece1);
                break;
            case 1:
                board[0][1] = opponent;
                Sprite piece2 = new Sprite(new Texture("O.png"));
                piece2.setPosition(323, 395);
                opponentList.add(piece2);
                break;
            case 2:
                board[0][2] = opponent;
                Sprite piece3 = new Sprite(new Texture("O.png"));
                piece3.setPosition(446, 395);
                opponentList.add(piece3);
                break;
            case 3:
                board[1][0] = opponent;
                Sprite piece4 = new Sprite(new Texture("O.png"));
                piece4.setPosition(200, 275);
                opponentList.add(piece4);
                break;
            case 4:
                board[1][1] = opponent;
                Sprite piece5 = new Sprite(new Texture("O.png"));
                piece5.setPosition(323, 275);
                opponentList.add(piece5);
                break;
            case 5:
                board[1][2] = opponent;
                Sprite piece6 = new Sprite(new Texture("O.png"));
                piece6.setPosition(446, 275);
                opponentList.add(piece6);
                break;
            case 6:
                board[2][0] = opponent;
                Sprite piece7 = new Sprite(new Texture("O.png"));
                piece7.setPosition(200, 155);
                opponentList.add(piece7);
                break;
            case 7:
                board[2][1] = opponent;
                Sprite piece8 = new Sprite(new Texture("O.png"));
                piece8.setPosition(323, 155);
                opponentList.add(piece8);
                break;
            case 8:
                board[2][2] = opponent;
                Sprite piece9 = new Sprite(new Texture("O.png"));
                piece9.setPosition(446, 155);
                opponentList.add(piece9);
                break;
        }


    }

    private void addPiece(int space){
        switch(space){
            case 0:
                if(board[0][0].equals("-")){
                    board[0][0] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(200, 395);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 1:
                if(board[0][1].equals("-")){
                    board[0][1] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(323, 395);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 2:
                if(board[0][2].equals("-")){
                    board[0][2] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(446, 395);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 3:
                if(board[1][0].equals("-")){
                    board[1][0] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(200, 275);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 4:
                if(board[1][1].equals("-")){
                    board[1][1] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(323, 275);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 5:
                if(board[1][2].equals("-")){
                    board[1][2] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(446, 275);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 6:
                if(board[2][0].equals("-")){
                    board[2][0] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(200, 155);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 7:
                if(board[2][1].equals("-")){
                    board[2][1] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(323, 155);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;
            case 8:
                if(board[2][2].equals("-")){
                    board[2][2] = player;
                    Sprite piece = new Sprite(new Texture("X.png"));
                    piece.setPosition(446, 155);
                    playerList.add(piece);
                    isPlayerTurn = false;
                }
                break;

        }

    }

    private boolean checkWinningState(String [][] board, String player){
        // check horizontal win
        for(int i= 0; i < ROW; i++){
            if(board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player))
                return true;
        }

        //check vertical win
        for(int i= 0; i < COL; i++){
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

    public boolean checkDraw(String [][] board){
        for(int i = 0; i < ROW; i++ ){
            for(int j = 0; j < COL; j++){
                if(board[i][j].equals("-"))
                    return false;
            }
        }
        return true;
    }

    private void gameOver() {
        gameOverScreen = new GameOverScreen(game, playerList, opponentList);
        game.setScreen(gameOverScreen);
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
