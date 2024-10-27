# TicTacToe

## Game Design and Logic 

This game is the classic implementation of the TicTacToe board game. The user plays against the 
computer to either get three pieces in a row, column, or diagonal. Each game starts with the player 
starting first and choosing which spot they want to place their piece on the 3x3 grid.   

The computer AI makes use of Q-Learning and is trained at the beginning of every game.  

The game implements the libgdx game engine to draw all the sprites and handle the clicking events. 
All other code is implemented by me including the Q-Learning algorithm. 

## How to Run the Game 

Included in the repo is an executable jar file that contains the game. Just download the jar file to run it. =
Double click to run if a JRE is installed and if the java scripts are set up in the system environment 
variables directory. 

If double clicking is not working, typing java -jar -TicTacToe-1.0.0.jar into the terminal should get it to 
run as well. 

The reposiorty can also be cloned and opened with Android Studio. Use gradle to import dependencies and to run. 

## Q-Learning Algorithm  

The Q-learning algorithm makes use of some helper functions:

checkWinningState function to find a winner of a game.

checkDraw function to determine if the current board resulted in a draw. 

flattenString function that changes the current board to string to be used as a key for the 
hash map. 

emptySpots function returns a list of all nodes for valid positions of play.

maxQ function that returns the node with maximum q value for a particular state. 

The main Q-learning algorithm makes use of three specific functions:

chooseAction function that makes use of either exploration or exploitation. It returns either 
a random move or the move with the highest q value.

updateQTable function that updates the q table based on the current game state, the action 
taken, the next game state, and the reward.

qLearning function implements the training logic. 

The Q-learning algorithm starts to train the agent at the beginning of each game. Each game of 
TicTacToe starts with user player first and is always piece ‘X’. This way the AI agent can be trained on 
specific scenarios depending on the user’s random choices.  

Training the agent is done for 300,000 episodes with each episode simulating a game of TicTacToe. 
The training phase makes use of an exploration rate that decays after each episode. In the 
beginning the rate is very high to encourage exploration so the agent gets exposure to as many 
different game states as possible. As the exploration rate decays the agent uses exploitation to 
choose actions based on the values in the q table. During each turn of the simulated games of 
TicTacToe an action is chosen for the current player and is added to the board. A winning or draw 
situation is check and depending on the outcome a reward or penalty is given. If the user wins a 
penalty of -1 is given, if the AI agent wins a reward of 1 is given and if a draw 0 is given. The q table is 
updated with the current state, the action taken, the state of the board after the agent makes its 
move, and the reward given.  

In the updateQTable function, each q value of the given action is update based on the Bellman 
equation. This equation takes the current q value of that action then summed with the learning rate, 
discount factor, and the q value the next stat. The learning rate also decays per episode to 
encourage less change from each update to the q value.  
After all the training is completed the q table has upwards of 4,500 to 5,000 different game states 
with their q values for each position on the board. Since TicTacToe is considered a solved game, the 
game always results in a draw. 

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
