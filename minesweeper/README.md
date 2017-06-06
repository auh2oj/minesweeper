#minesweeper

----------------------
Command Line Arguments
----------------------

Compile: javac minesweeper/*.java

Run: java minesweeper.Main --text

--text is required, and it specifies to the program to use the text mode of the game, run in the command line.

The game was originally meant to have a GUI, but I have not implemented that yet.

------
Design
------

The game has three difficulty levels (Easy, Medium, Hard); each higher difficulty level has a larger grid and more mines to clear. For example, the Easy level is on an 8x8 grid with 10 mines to clear.Each square in the grid is represented by a Square object, and the grid itself is represented as a 2D array of Squares. Each column is marked by a lowercase letter, starting at a. Each row is marked by a number, starting at 1.

The design was heavily inspired by the Android Minesweeper game.

The grid on the command line in the text mode on Easy mode at the start of the game appears thus:

===  
 8 - - - - - - - -  
 7 - - - - - - - -  
 6 - - - - - - - -  
 5 - - - - - - - -  
 4 - - - - - - - -  
 3 - - - - - - - -  
 2 - - - - - - - -  
 1 - - - - - - - -  
   a b c d e f g h   
Flags left: 10

The format of the grid was inspired by and taken from skeleton code of a project for my Data Structures & Algorithms class.

The game is designed so that the user's initial move will never be on a mine, and all Squares adjacent to the initial move will never have mines either. To accomplish this, at the start of the game, the grid is empty. I then place a Square at the user's first move, and I store the coordinates of all of that Square's neighboring positions in the grid. I then create n sets of two random numbers, where n is the number of mines to be cleared, and if those two random numbers are the same as any of the coordinates stored earlier, I ignore them and create more random numbers, until I have n mines placed.

-----
Input
-----

Upon entering the game, the user is asked to select a difficulty. The user must enter a, b, or c to specify the difficulty level, and press Enter. The user is then brought to the grid and the game begins.

The user inputs the letter for the column followed by the number for the row. For example, user input for the Easy difficulty level must be in the following regex format:

"^[a-h][1-8]$"

To flag a Square, the user inputs the word "flag" followed by the position of the Square on the grid as described by the above regular expression. For example, to flag the Square at position a1, the user would type, "flag a1". While a Square is flagged, it cannot be modified, and any input by the user to that Square will do nothing. The user inputs the same string to un-flag that Square.

----------
Challenges
----------

The mine placement procedure was difficult to get right, and it took me a long time to debug. Originally, I had a method to calculate the coordinates of all neighboring Square objects to a given grid position, but this didn't work for mine placement, because, at that stage, all positions in the grid except for one (the user's initial move) were null. I therefore had to change that method to get the coordinates of positions neighboring a given grid position, cutting out the middle step of finding neighboring Squares. This not only solved the problem, but also streamlined and simplified my code.
