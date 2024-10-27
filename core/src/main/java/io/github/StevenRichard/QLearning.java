/*
*
*   Author: Steven Richard
*   This is the q learning algorithm as applied to the game of TicTacToe. The agent is trained on the specific
*   scenario that the user plays first and the play is always piece 'X'. In the beginning of training the chosen actions are chosen at
*   random to let the agent interact with as many game states as possible with most Q tables have 5,000 entries. The exploration
*   rate slowly decays to allow the agent to exploit its knowledge and train optimally. The learning rate also decays over time to allow
*   smaller transitions from q values as they are updated allowing for q values to converge to the maximum.
*
* */

package io.github.StevenRichard;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QLearning {
    double learningRate = 0.3; // alpha
    double discountFactor = 0.9;
    double explorationRate = 0.8; //epsilon
    int numberOfEpisodes = 300000;
    final String player = "X";
    final String opponent = "O";
    final int ROW = 3;
    final int COL = 3;
    HashMap<String, ArrayList<Node>> Q;

    public QLearning(){
        Q = new HashMap<>();

    }


    // Q learning algorithm that will be trained at the beginning of the game to populate the q table
    // Since the user always starts the game first the agent is trained only on scenarios with the player 'X' starting
    // and at randomly chosen spots. Over time the exploration rate decreases to gradually allow the agent to exploit its knowledge
    // the decay in exploration is very gradual to allow for enough states to occur.
    public void qLearning(){
        for(int i = 0; i < numberOfEpisodes; i++){
            //create new empty board each episode
            String [][] board = new String[ROW][COL];
            for(int m = 0; m < ROW; m++){
                for(int n = 0; n < COL; n++)
                    board[m][n] = "-";
            }

            String currentPlayer = player;

            boolean gameOver = false;
            while(!gameOver){

                // get current state of the board
                String currentState = flattenBoard(board);

                // get an action
                Node action = chooseAction(board, explorationRate);

                // add action to the board
                board[action.x][action.y] = currentPlayer;

                double reward = 0;

                // check if winner or draw
                boolean gameOverPlayerWon = checkWinningState(board, player);
                boolean gameOverOpponentWon = checkWinningState(board, opponent);
                boolean draw = checkDraw(board);

                if(gameOverPlayerWon || gameOverOpponentWon || draw){
                    // since the computer is always 'O' if 'X' wins reward is -1
                    if(gameOverPlayerWon)
                        reward = -1;
                    // if ai wins
                    else if(gameOverOpponentWon)
                        reward = 1;
                    else if(draw)
                        reward = 0;

                    updateQTable(currentState, action, board, reward);

                    gameOver = true;
                }
                else{
                    updateQTable(currentState, action, board, reward);

                    // check to see which player's turn it is
                    if(currentPlayer.equals(player))
                        currentPlayer = opponent;
                    else
                        currentPlayer = player;
                }

            }
            // reduce exploration rate
            explorationRate -= 0.00001;
            // reduce learning rate
            learningRate -= 0.001;
        }

        System.out.println(Q.size());

    }

    // this function is used to update the q table during the learning phase of the algorithm
    private void updateQTable(String state, Node action, String[][] nextState, double reward){

        ArrayList<Node> qValues = new ArrayList<>();
        // if state is not found in hashmap create empty set of nodes
        if(Q.get(state) != null)
            qValues = Q.get(state);
        else{
            for(int i = 0; i < ROW; i++){
                for(int j = 0; j < COL; j++){
                    qValues.add(new Node(i,j,0));
                }
            }
        }

        // get q values of the next state if not there create a list of empty nodes
        ArrayList<Node> nextQValues = new ArrayList<>();
        String nextStateFlattened = flattenBoard(nextState);
        if(Q.get(nextStateFlattened) != null)
            nextQValues = Q.get(nextStateFlattened);
        else{
            for(int i = 0; i < ROW; i++){
                for(int j = 0; j < COL; j++){
                    nextQValues.add(new Node(i,j,0));
                }
            }
        }

        //get node with a max q value
        Node maxQ = maxQ(nextQValues);

        // Q-learning update equation
        Node newQ = qValues.get((action.x*3) + action.y);
        newQ.qValue += learningRate * (reward + discountFactor * maxQ.qValue - newQ.qValue);
        qValues.set((action.x*3) + action.y, newQ);


        // add state to q table with all nodes and their q values
        Q.put(state, qValues);

    }

    // randomly choose an action based on the values in the q table
    public Node chooseAction(String [][] board, double explorationRate){

        String state = flattenBoard(board);
        Random rand = new Random();
        double rate = Math.random();

        //Exploration or if state is not in hashmap
        if(rate < explorationRate || Q.get(state) == null){
            //grab empty spots
            ArrayList<Node> emptySpots = getEmptySpots(board);

            if(emptySpots.isEmpty())
                return null;

            //grab a random spot and return as the action to choose
            int index = rand.nextInt(emptySpots.size());
            //System.out.println("Random exploration");
            return emptySpots.get(index);
        }
        else{
            // get the nodes in the q table for the current state of the game board
            ArrayList<Node> qValues = Q.get(state);
            //grab nodes that are empty spots
            ArrayList<Node> emptySpots = getEmptySpots(board);

            if(emptySpots.isEmpty())
                return null;

            //grab all empty spots and their q values
            ArrayList<Node> emptyQValues = new ArrayList<>();
            for(Node qNode : qValues){
                for(Node empty : emptySpots){
                    if(empty.compare(qNode))
                        emptyQValues.add(qNode);
                }
            }

            // get all nodes with a max q of the empty spots
            Node maxQ = maxQ(emptyQValues);

            return maxQ;

        }

    }

    // returns the node with the maximum q value
    private Node maxQ(ArrayList<Node> q){
        Node max = q.get(0);
        for(int i = 1; i < q.size(); i++ ){
            if(max.qValue < q.get(i).qValue)
                max = q.get(i);
        }
        return max;
    }

    // grabs empty spots on the board
    private ArrayList<Node> getEmptySpots(String [][] board){
        ArrayList<Node> temp = new ArrayList<>();
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                if(board[i][j].equals("-"))
                    temp.add(new Node(i,j));
            }
        }
        return temp;
    }

    // flattens board and returns as string to be used as key for hashmap
    public String flattenBoard(String [][] board){
        StringBuilder flattenedBoard = new StringBuilder();
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                flattenedBoard.append(board[i][j]);
            }
        }
        return flattenedBoard.toString();
    }

    // check winning state based on current player
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

    // checks the board for a draw
    public boolean checkDraw(String [][] board){
        for(int i = 0; i < ROW; i++ ){
            for(int j = 0; j < COL; j++){
                if(board[i][j].equals("-"))
                    return false;
            }
        }
        return true;
    }

}
