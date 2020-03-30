package services

import com.typesafe.sslconfig.util.PrintlnLogger
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._



class TicTacToeServiceTest extends PlaySpec  with GuiceOneAppPerTest with Injecting {

 "Testing the InitGame" in   {
   val board:String = "+oo++xx++";
   val service:TicTacToeService =   new TicTacToeService(board)
   val response = service.initGame(board)
   val expectedResult = Json.toJson(board)
   response mustEqual expectedResult

 }
/*
  "Testing populating board with empty string " in {
    var board:String = "";
    var service:TicTacToeService =   new TicTacToeService(board)
    var response = service.populateBoard(board)
    response mustEqual null

  }

  "Testing populating board with   string " in {
    var board:String = " xxo  o  ";
    var service:TicTacToeService =   new TicTacToeService(board)
    var response = service.populateBoard(board)

    var expectedResult:Array[Array[String]] = new Array[Array[String]](3);

    var innerArray:Array[String] = new Array[String](3)

    innerArray(0) = " "
    innerArray(1) = "x"
    innerArray(2) = "x"
    expectedResult(0) = innerArray

   var innerArray1:Array[String] = new Array[String](3)
    innerArray1(0) = "o"
    innerArray1(1) = " "
    innerArray1(2) = " "
    expectedResult(1) = innerArray1


    var innerArray2:Array[String] = new Array[String](3)
    innerArray2(0) = "o"
    innerArray2(1) = " "
    innerArray2(2) = " "
    expectedResult(2) = innerArray2


    // var expectedResult = [[' ', 'x', 'x'], ['o', ' ', ' '], ['o', ' ', ' ']]

    info("Wanted response ");
    info(expectedResult(2).length.toString());
    response.length mustEqual 3
    response mustEqual expectedResult

  }


  "Testing Validate Board String  " in {
    var board:String = " xxo  o  ";
    var service:TicTacToeService =   new TicTacToeService(board)
    var response = service.validateBoardString(board)
    var expectedResult= board.split("");
    info("Wanted response ");
    response mustEqual expectedResult
  }

*/


}
