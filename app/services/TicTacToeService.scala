package services

import com.fasterxml.jackson.annotation.JsonValue
import javax.inject.Singleton
import play.api.libs.json.{JsValue, Json}


trait ITicTacToeService{
  def initGame(): JsValue
}
//todo;
@Singleton
class TicTacToeService(val board: String) extends ITicTacToeService {
  val boardString :String = board;

    @Override def initGame(): JsValue = {
    val  result = Json.toJson(this.boardString)
    return result;
  }
}
