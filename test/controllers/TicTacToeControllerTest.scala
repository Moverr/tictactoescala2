package controllers


import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._



class TicTacToeControllerTest extends PlaySpec  with GuiceOneAppPerTest with Injecting {



   "render the index page from a new instance of controller" in {
     val controller = new TicTacToeController(stubControllerComponents())
     val board:String = "+++";
     val tic = controller.index(board).apply(FakeRequest(GET, "/"))

     status(tic) mustBe OK
     contentType(tic) mustBe Some("application/json")

    // var result = {"firstName":"Muyinda","lastName":"Rogers"}
     //contentAsString(home) must include ()

//


 }
//
//
//  test("testPostExample") {
//
//  }
//
//  test("testIndex") {
//
//  }
//
//  test("testGetEmployee") {
//
//  }

}
