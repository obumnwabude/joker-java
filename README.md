# Joker

The *joker* card game is a shedding-type card game for two or more players. The players will normally not exceed seven or ten because the game will get boring. The object of the game is to be the first to get rid of all the player's cards to a discard pile. 

A [standard pack (deck) of 52 playing cards](https://wikipedia.org/wiki/Standard_52-card_deck) with addition of two joker cards (red and black joker) is used in playing Joker. The players could also opt to use two decks, that is 104 playing cards, plus two pairs of jokers making it 108 cards used in playing. 

The origin of the game is not clear to the author of this README.md, but the game is common in [Cameroon](https://wikipedia.org/wiki/Cameroon).

## Rules of the Joker card game

Any specific number of cards (this number is chosen by the players themselves, however five cards for the start is most common) are dealt to each player turn by turn at the start of the game. One card is then played to the board (face up) and the remaining cards of the deck(s) are placed face down at the center of the table.

***Board*** in this game is the name given to the Draw Pile and Discard Pile. It is usually given the personality of a judge, where every player obeys its commands. The Draw Pile is the pile of cards placed face down on the board while the Discard pile is the pile that will be formed as players take turns to discard cards on the face up card. 

Players discard by matching rank or suit with the top card of the discard pile, starting with the first player to whom was dealt a playing card. 

They can also play any J command (Jack) at any time. If a player is unable to match the rank or suit of the top card of the discard pile and does not have an J command, they draw a card from the draw pile (board). When a player plays an J command, they must command the suit that the next player is to play; that player must then follow the named suit or play another J command. If they don't do any of the two options, then the player 'goes board' (draws a card from the draw pile). The next players are expected to play the named suit or another J command until that command is met, the person who initially played that J command and requested the demanded suit is bound to play a card of that suit or another J command, if not they go board too. 

When a player plays a 7, the next player picks two cards from the board and ends their turn. When a player plays either red or black Joker card, the next player picks four cards and ends their turn. 

When a player plays an Ace, the next player is simply skipped, and they do not do anything. 

***Adding*** is a situation where the next player who was to pick 2 or to pick 4 passes the cards to be picked from the board to the player after who is to play after them, by playing a 7 (pick 2) or a joker (pick 4) and 'adding' the number of cards to be picked by that next player. 

***Cooling*** on the other hand is playing an Ace (A) or a Jack (J Command) to cancel the cards to be picked. If a player cools any number of picks with an Ace, the next turn will demand a card matching the suit of the Ace or an Ace too. If the cooling was done with a J command then the next turn can accept any card from any suit

Adding and Cooling is optional and it being mandatory in a round of the *joker* card game, is usually declared at the start of the game.

If the first card played by the board (the card played face up after dealing at the start), is a pick 2, pick 4 or skip you, the above rules are observed. If the card is a J command, then the suit of that J command is the demanded suit, however the players could agree that the board will not have such power, just at that time, in that if the first card is a J command, then the demanded suit could be any suit (In other words, the players could decide at the start of the round that the J Command played by the board at the board could mean play any suit of choice).

There are two types of joker cards, the red and the black. Red jokers match the Diamonds and Hearts suits while Black jokers match the Clubs and Spades suits. Conventionally, the names of these 4 suits are different. Diamonds are known as Squares, Hearts are known as Reds, Clubs are known as Cassava while Spades are known as Groundnuts. This was maintained while creating the game and the standard way of naming suits was however preserved in the game and simply commented out.

The game ends as soon as one player has emptied their hand. 

At times, the cards of other players are counted according to points and summed up and the player with the highest points is eliminated. Then the winner of that round and the other players still in the game will keep playing and eliminating one person till there is a final winner. 

The points of the cards are such that from two to ten represent their respective numerical value. Then Ace, Queen and King all represent eleven, Jack represents twenty and joker represents forty points. 

## How to play the game
To play this game in the command line or shell: 

Make sure [JDK](https://https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) and [Git](https://git-scm.com/downloads) are installed and working on your computer, then open the command prompt or shell and do the following 

* Clone this repo in your computer. Use
	```shell
      git clone https://github.com/obumnwabude/joker.git
       

* Change the current directory into the repo's directory. Use 
	```shell 
    cd joker
    ```

* Create a build folder where all the compile classes will be found. Use 
	```shell
    mkdir build
    ```

* Change the current directory into the folder with source files. Use
	```shell
    cd src
    ```

* Compile all the files into the build folder. Use
	```shell 
    javac -d ../build joker/app/*.java joker/board/*.java joker/board/situe/*.java joker/playing/*.java joker/shellgame/*.java joker/util/*.java
    

* Change the current directory to the build folder. Use 
	```shell
    cd ..
    cd build
    ```

* Run the shell version of the game and start playing. Follow the prompt and play. Use 
	```shell
    java joker.shellgame.ShellGame
    ```


## Note About the code 
Great efforts were made to document each class, field and method, except the private ones so that the code could be understood 

Indentations and naming conventions were greatly observed 

Efforts have been made to put in place a working version of the logic of the game with the game being playable via the command line 

The joker.app package was meant to extend functionality to give the game a graphical user interface with JavaFX. 

The joker.board.situe package was used to make sure all rules are observed

The Command and BoardManager classes help in enabling undo and redo whenever the game is played with a Graphical User Interface. They've been tested but their implementation in the shell game version is rather messy and was not maintained. 

There are different places where players can decide to make the game more interesting viz, by changing the number of cards to start with (handSize), by using two decks, by allowing or not accepting adding and cooling, by allowing any card to be played after cooling was done with J command or allowing any card to be played after the board played J command. To change these rules for now while playing, edit the source code and recompile. Notably, edit the JoBoard class in joker.board and maybe the number of cards dealt at the start in the ShellGame class in joker.shellgame. 

This project is open to contributions :)
