package services

import com.fasterxml.jackson.annotation.JsonValue
import javax.inject.Singleton
import play.api.libs.json.{JsValue, Json}


trait ITicTacToeService{
  def initGame(): JsValue
  def populateBoard( moves:String): Array[Array[String]]
  def playGame(boardString:String):  Array[Array[String]]
  def findHorizontalMatch(board:Array[Array[String]]):Unit
  def rotateThroughBoardColumns(board:Array[Array[String]],columnIndex:Int,unMatched:Int):Unit
  def findVerticalMatch(board:Array[Array[String]]):Unit
  def findIfExistsUnmatched(board:Array[Array[String]]):Boolean
  def findLeftRightDiagonalMatch(board:Array[Array[String]]):Any
  def findRightLeftDiagonalMatch(board:Array[Array[String]]):Any
  def validateBoardString(board:String):Array[String]
  //todo: THis function has a board array and a callback
  def placeHorizontalWin(board:Array[Array[String]], callBack:(Boolean,Array[String]) )
  def placeVerticalWin(board:Array[Array[String]],columnIndex:Int,unMatched:Int,callBack:(Boolean,Array[String]) );
  def playLeftRightDiagonalWin(board:Array[Array[String]], callBack:(Boolean,Array[String]) )
  def playRightLeftDiagonalWin(board:Array[Array[String]], callBack:(Boolean,Array[String]) )
  def placeHorizontalBlock(board:Array[Array[String]], callBack:(Boolean,Array[String]) )
  def placeVerticalBlock(board:Array[Array[String]],columnIndex:Int,unMatched:Int,callBack:(Boolean,Array[String]) )
  def playLeftRightDiagonalBlock(board:Array[Array[String]], callBack:(Boolean,Array[String]) )
  def playRightLeftDiagonalBlock(board:Array[Array[String]], callBack:(Boolean,Array[String]) )
  def playHorizontalMove(board:Array[Array[String]], callBack:(Boolean,Array[String]) )

  def playVerticalMove(board:Array[Array[String]],columnIndex:Int,unMatched:Int,callBack:(Boolean,Array[String]) )
  def playLeftRightDiagonalMove(board:Array[Array[String]], callBack:(Boolean,Array[String]) )
  def playRightLeftDiagonalMove(board:Array[Array[String]], callBack:(Boolean,Array[String]) )

  def shuffle(board:Array[String]):Array[String]
  def playNextMove(board:Array[String]): Array[String]





}
//todo;
@Singleton
class TicTacToeService(val board: String) extends ITicTacToeService {
  val boardString :String = board;

  var player1 = "o";
  var player2 = "x";
  var initSymbol = " ";
  var draw = 0;



  @Override def initGame(): JsValue = {
     val  boardJson = Json.toJson(this.boardString)
   //  val result = playGame(this.boardString)
      return boardJson
  }

  //populate Board
  override def populateBoard(moves: String): Array[Array[String]] = {
    if(moves.isEmpty()){
       return null
    }
    val movesArray:Array[String] = moves.split("")
    val board: Array[Array[String]] = new Array[Array[String]](3);

    var boardIndex:Int = 0;
    for(i<-0 until(3,1)){
        var boardRow:Array[String]= new Array[String](3);
        for(j<-0 until(3,1)){
          if(movesArray(boardIndex) == player1 || movesArray(boardIndex) == player2){
              boardRow(j) = movesArray(boardIndex);
          }else{
             boardRow(j) = initSymbol;
          }
          boardIndex += 1
        }
      board(i) = boardRow;
     }
    board
  }

  override def playGame(boardString: String):  Array[Array[String]]= {
    var board: Array[Array[String]] = null
    var resultstatus:Boolean = false
    // 1) if empty string  or undefined meaning, the computer is playing first
    if(boardString.isEmpty()){
      board = populateBoard("++++o++++");
    }
    validateBoardString(boardString);
    board = populateBoard(boardString)

    var result = null
    {
      result = findHorizontalMatch(board)
    }

    result
  }

  override def findHorizontalMatch(board: Array[Array[String]]): Unit = {

    var unMatched:Int = 0
    for(i<-0 until(board.length,1)){
      var boardRow = board(i)
      var move_o :Int = 0
      var move_x  :Int = 0
      for(j<-0 until(board.length,1)){
         if(boardRow(j).equals(player1)){
           move_o +=1
         }
         else if(boardRow(j).equals(player2)){
            move_x += 1
         }else{
           unMatched += unMatched;
         }
      }

      if (move_o eq 3) return player1

      if (move_x eq 3) return player2

    }

    if (unMatched > 0){
      return board
    } else{
      return draw
    }

  }

  override def rotateThroughBoardColumns(board: Array[String], columnIndex: Int, unMatched: Int): Unit = ???

  override def findVerticalMatch(board: Array[String]): Unit = ???

  override def findIfExistsUnmatched(board: Array[String]): Boolean = ???

  override def findLeftRightDiagonalMatch(board: Array[String]): Any = ???

  override def findRightLeftDiagonalMatch(board: Array[String]): Any = ???

  override def validateBoardString(board:String ):Array[String] = {
    if(board.isEmpty()) {
      throw new RuntimeException("")
    }
    var boardArray:Array[String] = board.split("")
    if(boardArray.length <  9 ){
      throw new RuntimeException("Invalid Board length ")
    }

    for(index <-0 until(boardArray.length,1)){
      var character = boardArray(index)
      if(!character.equalsIgnoreCase(player1) && !character.equalsIgnoreCase(player2)  && !character.equalsIgnoreCase(initSymbol)  ){
        throw new RuntimeException("Invalid Character, not acceptable ")
      }
    }
      boardArray
  }

  override def placeHorizontalWin(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def placeVerticalWin(board: Array[String], columnIndex: Int, unMatched: Int, callBack: (Boolean, Array[String])): Unit = ???

  override def playLeftRightDiagonalWin(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def playRightLeftDiagonalWin(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def placeHorizontalBlock(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def placeVerticalBlock(board: Array[String], columnIndex: Int, unMatched: Int, callBack: (Boolean, Array[String])): Unit = ???

  override def playLeftRightDiagonalBlock(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def playRightLeftDiagonalBlock(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def playHorizontalMove(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def playVerticalMove(board: Array[String], columnIndex: Int, unMatched: Int, callBack: (Boolean, Array[String])): Unit = ???

  override def playLeftRightDiagonalMove(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def playRightLeftDiagonalMove(board: Array[String], callBack: (Boolean, Array[String])): Unit = ???

  override def shuffle(board: Array[String]): Array[String] = ???

  override def playNextMove(board: Array[String]): Array[String] = ???
}




/*
 SOMETHING ON THE SIDE TO

// three args are passed in:
// 1) 'f' - a function that takes an Int and returns an Int
// 2) 'a' - an Int
// 3) 'b' - an Int
def sum(f: Int => Int, a: Int, b: Int): Int = if (a > b) 0 else f(a) + sum(f, a + 1, b)

// these three functions use the sum() function
def sumInts(a: Int, b: Int): Int = sum(id, a, b)
def sumSquares(a: Int, b: Int): Int = sum(square, a, b)
def sumPowersOfTwo(a: Int, b: Int): Int = sum(powerOfTwo, a, b)

// three functions that are passed into the sum() function
def id(x: Int): Int = x
def square(x: Int): Int = x * x
def powerOfTwo(x: Int): Int = if (x == 0) 1 else 2 * powerOfTwo(x - 1)

// this simply prints the number 10
println("sum ints 1 to 4 = " + sumInts(1,4))

 */