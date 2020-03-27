package services

import com.fasterxml.jackson.annotation.JsonValue
import javax.inject.Singleton
import play.api.libs.json.{JsValue, Json}


trait ITicTacToeService{
  def initGame(): JsValue
  def populateBoard( moves:String): Array[Array[String]]
  def playGame(boardString:String):  Array[Array[String]]
  def findHorizontalMatch(board:Array[Array[String]]):Any
  def rotateThroughBoardColumns(board:Array[Array[String]],columnIndex:Int,unMatched:Int):Any
  def findVerticalMatch(board:Array[Array[String]]):Any
  def findIfExistsUnmatched(board:Array[Array[String]]):Boolean
  def findLeftRightDiagonalMatch(board:Array[Array[String]]):Any
  def findRightLeftDiagonalMatch(board:Array[Array[String]]):Any
  def validateBoardString(board:String):Array[String]
  //todo: THis function has a board array and a callback
  def placeHorizontalWin(board:Array[Array[String]]  ):Any
  def placeVerticalWin(board:Array[Array[String]],columnIndex:Int,unMatched:Int ):Any
  def playLeftRightDiagonalWin(board:Array[Array[String]]):Any
  def playRightLeftDiagonalWin(board:Array[Array[String]]):Any
  def placeHorizontalBlock(board:Array[Array[String]]):Any
  def placeVerticalBlock(board:Array[Array[String]],columnIndex:Int,unMatched:Int):Any
  def playLeftRightDiagonalBlock(board:Array[Array[String]] ):Any
  def playRightLeftDiagonalBlock(board:Array[Array[String]]):Any
  def playHorizontalMove(board:Array[Array[String]] ):Any

  def playVerticalMove(board:Array[Array[String]],columnIndex:Int,unMatched:Int ):Any
  def playLeftRightDiagonalMove(board:Array[Array[String]] ):Any
  def playRightLeftDiagonalMove(board:Array[Array[String]]):Any

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
     val  boardJson = Json.toJson(boardString)
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
          boardRow(j)  =   if(movesArray(boardIndex) == player1 || movesArray(boardIndex) == player2)  movesArray(boardIndex) else initSymbol
          boardIndex += 1
        }
      board(i) = boardRow;
     }
    board
  }

  override def playGame(boardString: String):  Array[Array[String]]= {
    var board: Array[Array[String]] = null

    // 1) if empty string  or undefined meaning, the computer is playing first
     validateBoardString(boardString);
     board =   if (boardString.isEmpty())  populateBoard("++++o++++") else  populateBoard(boardString)

    var result:Any = null

      result = findHorizontalMatch(board)
      if(result.equals(player1) || result.equals(player2)){
          return board;
      }

      {
        result = findVerticalMatch(board)

        if(result.equals(player1) || result.equals(player2)) return board

        result = findLeftRightDiagonalMatch(board)

        if(result.equals(player1) || result.equals(player2)) return board

        result = findRightLeftDiagonalMatch(board)

        if(result.equals(player1) || result.equals(player2)) return board

      }


    var resultObject:Array[Any] = null;
    {
      resultObject  = placeHorizontalWin(board).asInstanceOf[Array[Any]]
      board =  resultObject(1).asInstanceOf[Array[Array[String]]]
      if(resultObject(0).asInstanceOf[Boolean]== true)   return  board

      resultObject = placeVerticalWin(board,0,0).asInstanceOf[Array[Any]]
      board =  resultObject(1).asInstanceOf[Array[Array[String]]]
      if(resultObject(0).asInstanceOf[Boolean]== true)   return  board

      resultObject = playLeftRightDiagonalWin(board).asInstanceOf[Array[Any]]
      board =  resultObject(1).asInstanceOf[Array[Array[String]]]
      if(resultObject(0).asInstanceOf[Boolean]== true)   return  board

      resultObject =  playRightLeftDiagonalWin(board).asInstanceOf[Array[Any]];
      board =  resultObject(1).asInstanceOf[Array[Array[String]]]
      if(resultObject(0).asInstanceOf[Boolean]== true)   return  board

    }



    {

      resultObject =  placeHorizontalBlock(board).asInstanceOf[Array[Any]]
      board =  resultObject(1).asInstanceOf[Array[Array[String]]]
      if(resultObject(0).asInstanceOf[Boolean]== true)   return  board


      resultObject =  placeVerticalBlock(board, 0, 0).asInstanceOf[Array[Any]]
      board =  resultObject(1).asInstanceOf[Array[Array[String]]]
      if(resultObject(0).asInstanceOf[Boolean]== true)   return  board



      resultObject = playLeftRightDiagonalBlock(board).asInstanceOf[Array[Any]]
      board =  resultObject(1).asInstanceOf[Array[Array[String]]]
      if(resultObject(0).asInstanceOf[Boolean]== true)   return  board



      playRightLeftDiagonalBlock(board, (status, result) => {
        if (status === true) {
          resultstatus = true
          return result;
        }
      });



    }





    return null
  }

  override def findHorizontalMatch(board: Array[Array[String]]): Any = {

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

      if (move_o ==  3) return player1

      if (move_x == 3) return player2

    }

    if(unMatched > 0 )   board else   draw
  }


  override def validateBoardString(board:String ):Array[String] = {
    if(board.isEmpty()) {
      return null
    }
    val boardArray:Array[String] = board.split("")
    if(boardArray.length <  9 ){
      throw new RuntimeException("Invalid Board length ")
    }

    for(index <-0 until(boardArray.length,1)){
      val character = boardArray(index)
      if(!character.equalsIgnoreCase(player1) && !character.equalsIgnoreCase(player2)  && !character.equalsIgnoreCase(initSymbol)  ) throw new RuntimeException("Invalid Character, not acceptable ")
    }
      boardArray
  }


  override def rotateThroughBoardColumns(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any = {

    var move_o: Int = 0
    var move_x: Int = 0
    var un_matched:Int = 0;

    for (i <- 0 until(board.length, 1)) {
      var boardRow = board(i)

      if (boardRow(columnIndex).equals(player1)) {
        move_o +=1
      }
      else if (boardRow(columnIndex).equals(player2) ) {
        move_x +=1
      } else if (boardRow(columnIndex).equals(initSymbol)) {
        un_matched += 1
      }

    }

    if (move_o ==  3) return player1

    if (move_x ==  3) return player2

    if ( (columnIndex == 3)  &&  un_matched > 0)  return board

    if (columnIndex ==  3)  return draw

    var  column_index = columnIndex + 1;

    rotateThroughBoardColumns(board, column_index, un_matched);

  }

  override def findVerticalMatch(board: Array[Array[String]]): Any = {
    val columnIndex: Int = 0
    val unmatched: Int = 0
    val result: Any = rotateThroughBoardColumns(board, columnIndex, unmatched)
    result

  }

  override def findIfExistsUnmatched(board: Array[Array[String]]): Boolean = ???

  override def findLeftRightDiagonalMatch(board: Array[Array[String]]): Any = ???

  override def findRightLeftDiagonalMatch(board: Array[Array[String]]): Any = ???

  override def placeHorizontalWin(board: Array[Array[String]]   ):Any={

    val result:Array[Any] = null
    var boardRow:Array[String] = null

    var i:Int = 0;
    while(i < board.length){

      boardRow = board(i)
      var move_o = 0
      var move_x = 0
      var unmatched = 0
      var j = 0
      while ( j < boardRow.length ) {

        if (boardRow(j) eq player1) move_o += 1
        else if (boardRow(j) eq player2) move_x += 1
        else unmatched += 1

        j += 1
      }
      if ((move_o == 2) && (move_x == 0) && (unmatched == 1)) {

        var j = 0
        while (    j < boardRow.length  ) {
          if (boardRow(j) ==  initSymbol) {
            boardRow(j) = player1
            result(0) = true;
            result(1) = board;

            return result;
          }

          j += 1
        }
      }


      i  += 1
    }

    result(0) = false;
    result(1) = board;

    result
   }

  override def placeVerticalWin(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any ={

    val result:Array[Any] = null

    var move_o = 0
    var move_x = 0
    var un_matched = unMatched
    var boardRow:Array[String] = new Array[String](3)
    var i = 0
    while (  i < board.length ) {
      boardRow = board(i)
      if (boardRow(columnIndex) == player1) move_o += 1
      else if (boardRow(columnIndex) == player2) move_x += 1
      else if (boardRow(columnIndex) == initSymbol) un_matched += 1

      i += 1
    }


    if ((move_o == 2) && (move_x == 0) && (un_matched == 1)) {
      var j = 0
      while ( {
        j < boardRow.length
      }) {
        if (boardRow(columnIndex) == initSymbol) {
          boardRow(columnIndex) = player1

          result(0) = true
          result(1) = board
          return result

        }

        j += 1
      }
    }

    if (columnIndex < 3) {
      val column_Index = columnIndex + 1
      return placeVerticalWin(board, column_Index, un_matched)
    }

    result(0) = false
    result(1) = board

    result
  }

  override def playLeftRightDiagonalWin(board: Array[Array[String]]): Any = {

    val result:Array[Any] = null

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow:Array[String] = null;

    var i = 0
    while ( i < board.length) {
      boardRow = board(i)
      if (boardRow(i) eq player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1
      i += 1
    }


    if ((move_o == 2) && (move_x == 0) && (unmatched == 1)) {
      var i = 0
      while (   i < board.length) {
        boardRow = board(i)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(i) = boardRow;

          result(0) = true
          result(1) = board
          return result
        }

        i += 1
      }
    }

    result(0) = false
    result(1) = board
    result
  }

  override def playRightLeftDiagonalWin(board: Array[Array[String]]): Any = {
    val result:Array[Any] = null

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow:Array[String] = null;

    var boardIndex = board.length - 1

    var i = 0
    while (  i < board.length) {
      boardRow = board(boardIndex)
      if (boardRow(i) ==  player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) ==  initSymbol) unmatched += 1

      boardIndex -= 1

      i += 1
    }


    if ((move_o eq 2) && (move_x eq 0) && (unmatched eq 1)) {
      boardIndex = board.length - 1
      var i = 0
      while ( {
        i < board.length
      }) {
        boardRow = board(boardIndex)
        if (boardRow(i) ==  initSymbol) {
          boardRow(i) = player1
          board(boardIndex) = boardRow;

          result(0) = true
          result(1) = board
          return result


        }
        boardIndex -= 1

        i += 1
      }
    }

    result(0) = true
    result(1) = board
    result


  }

  override def placeHorizontalBlock(board: Array[Array[String]]): Any ={
    val result:Array[Any] = null
    var boardRow:Array[String] = null
    //todo: look through the vertical selection to find if there are existing 3 items of same type, x or o
    var i = 0
    while ( i < board.length) {
      boardRow = board(i)
      var move_o = 0
      var move_x = 0
      var unmatched = 0
      var j = 0
      while (  j < boardRow.length) {
        if (boardRow(j) == player1) move_o += 1
        else if (boardRow(j) == player2) move_x += 1
        else unmatched += 1
        j += 1
      }

      if ((move_x == 2) && (move_o == 0) && (unmatched == 1)) {
        var j = 0
        while (  j < boardRow.length) {
          if (boardRow(j) == initSymbol) {
            boardRow(j) = player1
            board(i) = boardRow
            result(0) == true
            result(1) = board

          }

          j += 1
        }
      }

      i += 1
    }

    result(0) == false
    result(1) = board

  }

  override def placeVerticalBlock(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any = {
    val result:Array[Any] = null

    var move_o = 0
    var move_x = 0
    var un_matched = unMatched
    var boardRow:Array[String] = null


    var i = 0
    while (  i < board.length) {
      boardRow = board(i)
      if (boardRow(columnIndex) == player1) move_o += 1
      else if (boardRow(columnIndex) == player2) move_x += 1
      else if (boardRow(columnIndex) == initSymbol) un_matched += 1

      i += 1
    }


    if ((move_x == 2) && (move_o == 0) && (un_matched == 1)) { //todo: place  a move o to the unmatched
      var j = 0
      while (  j < boardRow.length) {
        if (boardRow(columnIndex) == initSymbol) {
          boardRow(columnIndex) = player1
          board(i) = boardRow
          result(0) = true
          result(1) = board
          return result

        }

        j += 1
      }
    }

    // console.log(board.length);
    if (columnIndex < 3) {
      val column_index = columnIndex
      return placeVerticalWin(board, column_index, un_matched)
    }

    result(0) = true
    result(1) = board
    result


  }

  override def playLeftRightDiagonalBlock(board: Array[Array[String]]): Any = {
    val result:Array[Any] = null


    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow:Array[String] = null

    var i = 0
    while (i < board.length) {
      boardRow = board(i)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1

      i += 1
    }


    if ((move_x eq 2) && (move_o eq 0) && (unmatched eq 1)) {
      var i = 0
      while ( i < board.length) {
        boardRow = board(i)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(i) = boardRow
          result(0) = true
          result(1) = board;
          return result;

        }

        i += 1
      }
    }

    result(0) = false
    result(1) = board;
    result;


  }

  override def playRightLeftDiagonalBlock(board: Array[Array[String]]): Any = {
    val result:Array[Any] = null

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow:Array[String] = null

    var boardIndex = board.length - 1

    var i = 0
    while ( i < board.length) {
      boardRow = board(boardIndex)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) ==  initSymbol) unmatched += 1

      boardIndex -= 1

      i += 1
    }


    if ((move_x == 2) && (move_o == 0) && (unmatched ==  1)) {
      boardIndex = board.length - 1
      var i = 0
      while (  i < board.length) {
        boardRow = board(boardIndex)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1

          result(0) == true
          result(1) == board

          return result
        }
        boardIndex -= 1

        i += 1
      }
    }


    result(0) == false
    result(1) == board
    result

  }

  override def playHorizontalMove(board: Array[Array[String]]): Any = {
    val result:Array[Any] = null
    var boardRow:Array[String] = null

    var i = 0
    while ( i < board.length) {
      boardRow = board(i)
      var move_o = 0
      var move_x = 0
      var unmatched = 0
      var j = 0
      while (j < boardRow.length) {
        if (boardRow(j) == player1) move_o += 1
        else if (boardRow(j) == player2) move_x += 1
        else unmatched += 1 
        j += 1
      }
      if (move_o >= 0 && unmatched >= 1) {
        var j = 0
        while (  j < boardRow.length) {
          if (boardRow(j) == initSymbol) {
            boardRow(j) = player1
            board(i) = boardRow
            result(0) = true
            result(1) = board
            return result
          }

          j += 1
        }
      }

      i += 1
    }
    result(0) = true
    result(1) = board
    result

  }

  override def playVerticalMove(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any = {
    val result:Array[Any] = null

    var move_o = 0
    var move_x = 0
    var boardRow:Array[String] = null
    var unmatched = unMatched


    var i = 0
    while (  i < board.length) {
      boardRow = board(i)
      if (boardRow(columnIndex) == player1) move_o += 1
      else if (boardRow(columnIndex) == player2) move_x += 1
      else if (boardRow(columnIndex) == initSymbol) unmatched += 1
      else {
      }

      i += 1
    }


    if (move_o >= 0 && unmatched >= 1) {

      var j = 0
      while ( {
        j < boardRow.length
      }) {
        if (boardRow(columnIndex) ==  initSymbol) {
          boardRow(columnIndex) = player1
          result(0) = true
          result(1) = board

           return result

        }

        j += 1
      }
    }


    if (columnIndex < 3) {
      val column_index = columnIndex
      return placeVerticalWin(board, column_index,unmatched)
    }

    result(0) = false
    result(1) = board

    result


  }

  override def playLeftRightDiagonalMove(board: Array[Array[String]]): Any= {
    val result:Array[Any] = null

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow:Array[String] = null

    var i = 0
    while (i < board.length) {
      boardRow = board(i)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1
      i += 1
    }


    if (move_o >= 0 && unmatched >= 1) {
      var i = 0
      while (i < board.length) {
        boardRow = board(i)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(i) = boardRow
          result(0) = true
          result(1) = board
          return result

        }

        i += 1
      }
    }

    result(0) = true
    result(1) = board
    result
  }

  override def playRightLeftDiagonalMove(board: Array[Array[String]]): Any = {

    val result:Array[Any] = null


    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow:Array[String] = null

    var boardIndex = board.length - 1

    var i = 0
    while ( i < board.length) {
      boardRow = board(boardIndex)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1
      boardIndex -= 1
      i += 1
    }


    if (move_o >= 0 && unmatched >= 1) {
      boardIndex = board.length - 1
      var i = 0
      while ( {
        i < board.length
      }) {
        boardRow = board(boardIndex)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(i) = boardRow

          result(0) = true
          result(1) = board
          return result

        }
        boardIndex -= 1

        i += 1
      }
    }

    result(0) = false
    result(1) = board
    result

  }

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