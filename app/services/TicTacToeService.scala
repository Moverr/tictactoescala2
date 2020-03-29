package services

import util.control.Breaks._
import com.fasterxml.jackson.annotation.JsonValue
import javax.inject.Singleton
import play.api.libs.json.{JsValue, Json}


trait ITicTacToeService {
  def initGame(boardString: String): JsValue

  def populateBoard(moves: String): Array[Array[String]]

  def playGame(boardString: String): Array[Array[String]]

  def findHorizontalMatch(board: Array[Array[String]]): Any

  def rotateThroughBoardColumns(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any

  def findVerticalMatch(board: Array[Array[String]]): Any

  def findIfExistsUnmatched(board: Array[Array[String]]): Boolean

  def findLeftRightDiagonalMatch(board: Array[Array[String]]): Any

  def findRightLeftDiagonalMatch(board: Array[Array[String]]): Any

  def validateBoardString(board: String): Array[String]

  //todo: THis function has a board array and a callback
  def placeHorizontalWin(board: Array[Array[String]]): Array[Array[String]]

  def placeVerticalWin(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any

  def playLeftRightDiagonalWin(board: Array[Array[String]]): Any

  def playRightLeftDiagonalWin(board: Array[Array[String]]): Any

  def placeHorizontalBlock(board: Array[Array[String]]): Any

  def placeVerticalBlock(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any

  def playLeftRightDiagonalBlock(board: Array[Array[String]]): Any

  def playRightLeftDiagonalBlock(board: Array[Array[String]]): Any

  def playHorizontalMove(board: Array[Array[String]]): Any

  def playVerticalMove(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any

  def playLeftRightDiagonalMove(board: Array[Array[String]]): Any

  def playRightLeftDiagonalMove(board: Array[Array[String]]): Any

  def shuffle(board: Array[Int]): Array[Int]

  def playNextMove(board: Array[Array[String]]): Any


}

//todo;
@Singleton
class TicTacToeService(val board: String) extends ITicTacToeService {
  val boardString: String = board;

  var player1 = "o";
  var player2 = "x";
  var initSymbol = "+";
  var draw = 0;

  def populateResponse(board: Array[Array[String]]): String = {

    var result = ""
    var boardRow: Array[String] = null
    var i = 0
    while (i < board.length) {
      boardRow = board(i)

      var j = 0
      while (j < boardRow.length) {
        breakable {
          if (boardRow(j) == player1) result = result + "" + player1
          else if (boardRow(j) == player2) result += player2
          else if (boardRow(j) == initSymbol) result += " "
          else {
            break
          }
          j += 1

        }


      }

      i += 1
    }


    result

  }

  @Override def initGame(boardString: String): JsValue = {
    //     var  boardJson = null
    //       Json.toJson(boardString)

    val result: Array[Array[String]] = playGame(boardString)
    println(result(0).mkString(""))
    //   val response:String = populateResponse(result)

    val boardJson = Json.toJson(boardString)
    boardJson

  }

  //populate Board
  override def populateBoard(moves: String): Array[Array[String]] = {
    if (moves.isEmpty()) {
      return null
    }
    val movesArray: Array[String] = moves.split("")
    val board: Array[Array[String]] = new Array[Array[String]](3);

    var index: Int = 0;
    var i: Int = 0

    while (i < 3) {
      val boardRow: Array[String] = new Array[String](3);
      index = 0
      var j = 0
      while (j < 3) {
        boardRow(j) = if (movesArray(index) == player1 || movesArray(index) == player2) movesArray(index) else initSymbol
        index += 1
        j += 1
      }
      board(i) = boardRow;
      i += 1

    }
    board
  }

  var resultantStatus: Boolean = false

  override def playGame(boardString: String): Array[Array[String]] = {
    var board: Array[Array[String]] = null

    // 1) if empty string  or undefined meaning, the computer is playing first
    validateBoardString(boardString)
    board = if (boardString.isEmpty()) populateBoard("    o    ") else populateBoard(boardString)


    //    Step 1

    var result: Any = null

    result = findHorizontalMatch(board)
    if (result.equals(player1) || result.equals(player2)) return board;

    result = findVerticalMatch(board)
    if (result.equals(player1) || result.equals(player2)) return board


    result = findLeftRightDiagonalMatch(board)
    if (result.equals(player1) || result.equals(player2)) return board


    result = findRightLeftDiagonalMatch(board)
    if (result.equals(player1) || result.equals(player2)) return board


    //    Step 2

    var resultObject: Array[Array[String]] = board;
    resultObject = placeHorizontalWin(board)
    if (resultantStatus == true) return resultObject
    resultantStatus = false


    resultObject = placeVerticalWin(board, 0, 0)
    if (resultantStatus == true) return resultObject
    resultantStatus = false


    resultObject = playLeftRightDiagonalWin(board)
    if (resultantStatus == true) return resultObject
    resultantStatus = false


    resultObject = playRightLeftDiagonalWin(board);
    if (resultantStatus == true) return resultObject
    resultantStatus = false

    //Step 3
    resultObject = placeHorizontalBlock(board)
    if (resultantStatus == true) return resultObject
    resultantStatus = false


    resultObject = placeVerticalBlock(board, 0, 0)
    if (resultantStatus == true) return resultObject
    resultantStatus = false


    resultObject = playLeftRightDiagonalBlock(board)
    if (resultantStatus == true) return resultObject
    resultantStatus = false


    resultObject = playRightLeftDiagonalBlock(board)
    if (resultantStatus == true) return resultObject
    resultantStatus = false


// Level 3
   board =  playNextMove(board)


    board

  }


  override def findHorizontalMatch(board: Array[Array[String]]): Any = {

    var unMatched: Int = 0
    var move_o: Int = 0
    var move_x: Int = 0


    var i: Int = 0
    while (i < board.length) {
      val boardRow = board(i)
      var j: Int = 0;
      while (j < boardRow.length) {
        if (boardRow(j).equals(player1)) move_o += 1
        else if (boardRow(j).equals(player2)) move_x += 1
        else unMatched += 1;
        j += 1
      }

      i += 1
    }


    if (move_o == 3) player1
    else if (move_x == 3) player2
    else if (unMatched > 0) board
    else draw
  }


  override def validateBoardString(board: String): Array[String] = {
    if (board.isEmpty()) {
      return null
    }
    val boardArray: Array[String] = board.split("")
    println("xxxxxxxxxxxxxxxx")
    println(boardArray.length)
    if (boardArray.length < 9) {
      throw new RuntimeException("Invalid Board length ")
    }

    for (index <- 0 until(boardArray.length - 1, 1)) {
      val character = boardArray(index)
      if (!character.equalsIgnoreCase(player1) && !character.equalsIgnoreCase(player2) && !character.equalsIgnoreCase(initSymbol)) throw new RuntimeException("Invalid Character, not acceptable ")
    }
    boardArray
  }


  override def rotateThroughBoardColumns(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Any = {

    var column_index = columnIndex

    if (columnIndex > 3) return board

    var move_o: Int = 0
    var move_x: Int = 0
    var un_matched: Int = unMatched

    val boardRow = board(column_index);

    var i: Int = 0;
    while (i < 3) {
      if (boardRow(i) == player1) move_o = move_o + 1
      if (boardRow(i) == player2) move_x = move_x + 1
      if (boardRow(i) == initSymbol) un_matched = un_matched + 1

      i += 1
    }


    if (move_o == 3) player1
    else if (move_x == 3) player2
    else if ((columnIndex >= 2) && un_matched > 0) board
    else if (columnIndex >= 2) draw
    else {
      column_index += 1
      rotateThroughBoardColumns(board, column_index, un_matched);
    }


  }

  override def findVerticalMatch(board: Array[Array[String]]): Any = {
    val columnIndex: Int = 0
    val unmatched: Int = 0
    val response = rotateThroughBoardColumns(board, columnIndex, unmatched)
    response
  }

  override def findIfExistsUnmatched(board: Array[Array[String]]): Boolean = {

    var boardRow: Array[String] = null
    var i = 0
    while (i < board.length) {
      boardRow = board(i)
      if (boardRow(i) == initSymbol) return true

      i += 1
    }

    false
  }

  override def findLeftRightDiagonalMatch(board: Array[Array[String]]): Any = {
    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null

    var i = 0
    while (i < board.length - 1) {
      boardRow = board(i)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1
      i += 1
    }
    if (move_o == 3) player1
    else if (move_x == 3) player2

    else {
      val isUnmatched = findIfExistsUnmatched(board)
      if (isUnmatched == true) board else draw
    }

  }

  override def findRightLeftDiagonalMatch(board: Array[Array[String]]): Any = {
    if (board == null)
      return null

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null

    var boardIndex = board.length - 1

    var i = 0
    while (i < board.length - 1) {
      boardRow = board(boardIndex)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1

      boardIndex -= 1

      i += 1
    }


    if (move_o == 3) player1
    else if (move_x == 3) player2
    else {
      val isUnmatched = findIfExistsUnmatched(board)
      if (isUnmatched == true) board else draw
    }

  }

  override def placeHorizontalWin(board: Array[Array[String]]): Array[Array[String]] = {

    var boardRow: Array[String] = null

    var i: Int = 0;
    while (i < board.length) {

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
      if ((move_o == 2) && (move_x == 0) && (unmatched == 1)) {

        var j = 0
        while (j < boardRow.length) {
          if (boardRow(j) == initSymbol) {
            boardRow(j) = player1
            board(i) = boardRow
            resultantStatus = true
            return board
          }

          j += 1
        }
      }
      i += 1
    }

    resultantStatus = false
    board

  }

  override def placeVerticalWin(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Array[Array[String]] = {
    var move_o = 0
    var move_x = 0
    var un_matched = unMatched
    var boardRow: Array[String] = new Array[String](3)
    var i = 0
    while (i < board.length - 1) {
      boardRow = board(i)
      if (boardRow(columnIndex) == player1) move_o += 1
      else if (boardRow(columnIndex) == player2) move_x += 1
      else if (boardRow(columnIndex) == initSymbol) un_matched += 1
      i += 1
    }


    if ((move_o == 2) && (move_x == 0) && (un_matched == 1)) {
      var j = 0
      while (j < boardRow.length - 1) {
        if (boardRow(columnIndex) == initSymbol) {
          boardRow(columnIndex) = player1
          resultantStatus = true
          return board
        }
        j += 1
      }
    }

    if (columnIndex >= 2) {
      resultantStatus = false
      return board
    }
    else {
      val column_Index = columnIndex + 1
        placeVerticalWin(board, column_Index, un_matched)

    }

  }

  override def playLeftRightDiagonalWin(board: Array[Array[String]]): Array[Array[String]] = {


    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null;

    var i = 0
    while (i < board.length) {
      boardRow = board(i)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1
      i += 1
    }


    if ((move_o == 2) && (move_x == 0) && (unmatched == 1)) {
      var i = 0
      while (i < board.length) {
        boardRow = board(i)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(i) = boardRow;

          resultantStatus = true
          return board

        }

        i += 1
      }
    }

    resultantStatus = false
    board

  }

  override def playRightLeftDiagonalWin(board: Array[Array[String]]): Array[Array[String]] = {

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null;
    var boardIndex = board.length - 1
    var i = 0
    while (i < board.length) {
      boardRow = board(boardIndex)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1

      boardIndex -= 1

      i += 1
    }


    if ((move_o == 2) && (move_x == 0) && (unmatched == 1)) {
      boardIndex = board.length - 1
      var i = 0
      while ( {
        i < board.length
      }) {
        boardRow = board(boardIndex)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(boardIndex) = boardRow;
          resultantStatus = true
          return board
        }
        boardIndex -= 1
        i += 1
      }
    }
    resultantStatus = false
    board

  }

  override def placeHorizontalBlock(board: Array[Array[String]]): Array[Array[String]] = {

    var boardRow: Array[String] = null
    //todo: look through the vertical selection to find if there are existing 3 items of same type, x or o
    var i = 0
    while (i < board.length) {
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

      if ((move_x == 2) && (move_o == 0) && (unmatched == 1)) {
        var j = 0
        while (j < boardRow.length) {
          if (boardRow(j) == initSymbol) {
            boardRow(j) = player1
            board(i) = boardRow

            resultantStatus = true
            return board

          }

          j += 1
        }
      }

      i += 1
    }
    resultantStatus = false
    board


  }

  override def placeVerticalBlock(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Array[Array[String]] = {


    var move_o = 0
    var move_x = 0
    var un_matched = unMatched
    var boardRow: Array[String] = null


    var i = 0
    while (i < board.length) {
      boardRow = board(i)
      if (boardRow(columnIndex) == player1) move_o += 1
      else if (boardRow(columnIndex) == player2) move_x += 1
      else if (boardRow(columnIndex) == initSymbol) un_matched += 1

      i += 1
    }


    if ((move_x == 2) && (move_o == 0) && (un_matched == 1)) { //todo: place  a move o to the unmatched
      var j = 0
      while (j < boardRow.length) {
        if (boardRow(columnIndex) == initSymbol) {
          boardRow(columnIndex) = player1
          board(i) = boardRow

          resultantStatus = true
          return board


        }

        j += 1
      }
    }

    // console.log(board.length);
    if (columnIndex < 3) {
      val column_index = columnIndex
      return placeVerticalWin(board, column_index, un_matched)
    }

    resultantStatus = false
    board


  }

  override def playLeftRightDiagonalBlock(board: Array[Array[String]]): Array[Array[String]] = {


    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null

    var i = 0
    while (i < board.length) {
      boardRow = board(i)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1

      i += 1
    }


    if ((move_x == 2) && (move_o == 0) && (unmatched == 1)) {
      var i = 0
      while (i < board.length) {
        boardRow = board(i)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(i) = boardRow
          resultantStatus = true
          return board

        }

        i += 1
      }
    }

    resultantStatus = false
    board
  }

  override def playRightLeftDiagonalBlock(board: Array[Array[String]]): Array[Array[String]] = {

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null

    var boardIndex = board.length - 1

    var i = 0
    while (i < board.length) {
      boardRow = board(boardIndex)
      if (boardRow(i) == player1) move_o += 1
      else if (boardRow(i) == player2) move_x += 1
      else if (boardRow(i) == initSymbol) unmatched += 1

      boardIndex -= 1

      i += 1
    }


    if ((move_x == 2) && (move_o == 0) && (unmatched == 1)) {
      boardIndex = board.length - 1
      var i = 0
      while (i < board.length) {
        boardRow = board(boardIndex)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          resultantStatus = true
          return board

        }
        boardIndex -= 1

        i += 1
      }
    }


    resultantStatus = false
    board

  }

  override def playHorizontalMove(board: Array[Array[String]]): Array[Array[String]] = {
     var boardRow: Array[String] = null

    var i = 0
    while (i < board.length) {
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
        while (j < boardRow.length) {
          if (boardRow(j) == initSymbol) {
            boardRow(j) = player1
            board(i) = boardRow

            resultantStatus = true
            return board


          }

          j += 1
        }
      }

      i += 1
    }

    resultantStatus = false
    board
  }

  override def playVerticalMove(board: Array[Array[String]], columnIndex: Int, unMatched: Int): Array[Array[String]] = {

    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null
    var unmatched = unMatched


    var i = 0
    while (i < board.length-1) {
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
      while ( j < boardRow.length-1) {
        if (boardRow(columnIndex) == initSymbol) {
          boardRow(columnIndex) = player1

          resultantStatus = true
          return  board


        }

        j += 1
      }
    }


    if(columnIndex > 2){
      resultantStatus = false
         board
    }else{
      val column_index = columnIndex
        placeVerticalWin(board, column_index, unmatched)
    }


  }

  override def playLeftRightDiagonalMove(board: Array[Array[String]]): Array[Array[String]] = {

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null

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

          resultantStatus = true
          return board

        }

        i += 1
      }
    }

    resultantStatus = false
    return board


  }

  override def playRightLeftDiagonalMove(board: Array[Array[String]]): Array[Array[String]] = {

    var unmatched = 0
    var move_o = 0
    var move_x = 0
    var boardRow: Array[String] = null

    var boardIndex = board.length - 1

    var i = 0
    while (i < board.length-1) {
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
      while ( i < board.length) {
        boardRow = board(boardIndex)
        if (boardRow(i) == initSymbol) {
          boardRow(i) = player1
          board(i) = boardRow

          resultantStatus = true
          return  board


        }
        boardIndex -= 1

        i += 1
      }
    }

    resultantStatus = false
     board

  }

  override def shuffle(a: Array[Int]): Array[Int] = {
    var j: Int = 0
    var x: Int = 0
    var i: Int = 0
    i = a.length - 1
    while (i > 0) {
      j = Math.floor(Math.random * (i + 1)).asInstanceOf[Int]
      x = a(i)
      a(i) = a(j)
      a(j) = x
      i -= 1
    }
    a
  }

  override def playNextMove(board: Array[Array[String]]):  Array[Array[String]] = {
    var playMoves: Array[Int] = Array[Int](1, 2, 3, 4)
    playMoves = shuffle(playMoves)
    var x = 0
    while (x < playMoves.length) {
      val playMove = playMoves(x)
      playMove match {
        case 1 => return playHorizontalMove(board)
        case 2 => return playVerticalMove(board, 0, 0)
        case 3 => return playLeftRightDiagonalMove(board)
        case 4 => return playRightLeftDiagonalMove(board)
        case _ => return board
      }

      x += 1
    }
    board

  }
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