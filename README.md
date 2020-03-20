# TIC TAC TOE SCALA
![Scala CI](https://github.com/Moverr/tictactoescala2/workflows/Scala%20CI/badge.svg?branch=master)


Write and deploy a stateless API that correctly answers the question, “What move will result in the best outcome for O on this tic-tac-toe board?” (You may use any language.)

Tic-tac-toe can also be called naughts and crosses. Instructions for how to play the game are here if you’ve never played before: http://www.exploratorium.edu/brain_explorer/tictactoe.html

Note: We’d like to preserve our ability to reuse this project with other candidates without bias. Don’t share this spec or your code (if you want to use Github, please keep your repo private). Thanks!
Specification
“The best outcome” means a win, if you can force one. Otherwise, play for a tie.

Request Handling and Validation
Your server will be provided the current board in a GET request, using the 'board' parameter in the query string.
If the board string doesn't represent a valid tic-tac-toe board, or it’s not plausibly o’s turn, your server should return an HTTP response code 400 (Bad Request)
Your server always plays as o.
Either player can go first.
If the board is a valid tic-tac-toe board and it is plausibly o's turn, your server should return a string representation of the same board with one ‘o’ added.

Strategy

Please implement the optimal play algorithm outlined on Wikipedia, which we’ve pasted here (from https://en.wikipedia.org/wiki/Tic-tac-toe#Strategy):

Win: If the player has two in a row, they can place a third to get three in a row.
Block: If the opponent has two in a row, the player must play the third themselves to block the opponent.
Fork: Create an opportunity where the player has two threats to win (two non-blocked lines of 2).
Blocking an opponent's fork: If there is only one possible fork for the opponent, the player should block it. Otherwise, the player should block any forks in any way that simultaneously allows them to create two in a row. Otherwise, the player should create a two in a row to force the opponent into defending, as long as it doesn't result in them creating a fork. For example, if "X" has two opposite corners and "O" has the center, "O" must not play a corner in order to win. (Playing a corner in this scenario creates a fork for "X" to win.)
Center: A player marks the center. (If it is the first move of the game, playing on a corner gives the second player more opportunities to make a mistake and may therefore be the better choice; however, it makes no difference between perfect players.)
Opposite corner: If the opponent is in the corner, the player plays the opposite corner.
Empty corner: The player plays in a corner square.
Empty side: The player plays in a middle square on any of the 4 sides.

Board representation
The board is encoded as a string of nine characters where each character is either 'o' (letter o), 'x’, or a space. The nine characters are the tic-tac-toe board read left to right, top to bottom -- for example:
x|o|
-+-+- 
o| | 
-+-+- 
 |x| 
would be encoded with the string "xo o   x ", and an empty board would be a string of nine spaces.

Example
If I run
    curl YOUR_URL?board=+xxo++o++
I should get the exact string oxxo  o   (that’s o-x-x-o-space-space-o-space-space) as the entire contents of the HTTP response body. If your api returns anything else, our unit tests will fail when run on your code.
 
