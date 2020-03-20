package services

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._


class TicTacToeServiceTest extends PlaySpec  with GuiceOneAppPerTest with Injecting {

 "Testing the InitGame" should  {
   var board:String = "++++++++++";
    var service:TicTacToeService = new TicTacToeService(board);

 }

}
