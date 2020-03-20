package services

import com.fasterxml.jackson.annotation.JsonValue
import javax.inject.Singleton

trait ITicTacToeService{
  def initGame(boardString: String): JsonValue
}
//todo;
@Singleton
class TicTacToeService(val board: String) extends ITicTacToeService {
  override def initGame(boardString: String): JsonValue = {
    return null
  }
}
