package controllers

import com.fasterxml.jackson.databind.util.JSONPObject
import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc._

class Employee( var firstName:String,  var lastName:String);
case class Location(lat: Double, long: Double)
case class Resident(name: String, age: Int, role: Option[String])
case class Place(name: String, location: Location, residents: Seq[Resident])


class Tictac  @Inject()(cc:ControllerComponents) extends  AbstractController(cc){


  def getEmployee(): JsValue ={

    implicit  val employeeWrites = new Writes[Employee] {
      override def writes(employee: Employee)  = Json.obj(
        "firstName" ->employee.firstName, "lastName" ->employee.lastName
      )
    }


    val empl = new Employee("Muyinda","Rogers")
    return Json.toJson(empl);
  }

  def index =Action{



    val empl = getEmployee()

    implicit request: Request[AnyContent] =>
//          Ok(views.html.tic())
      Ok((empl));

  }

  def postExample =Action{
    val empl = getEmployee()

    implicit request: Request[AnyContent] => Ok(empl);
  }

}
