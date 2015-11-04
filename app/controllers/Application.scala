package controllers

import Models.CustomersModel.{Customer, Customers}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import slick.driver.MySQLDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Application extends Controller {

  val db = Database.forConfig("mysqldb")

  val customers : TableQuery[Customers] = TableQuery[Customers]

  def index = Action {
    Ok(views.html.index())
  }

  def getCats = Action {
    //try {

    Ok(Json.toJson(Await.result(db.run(customers.result), Duration.Inf)))
    //} catch {
    //  case e: Exception => InternalServerError("Some errors have occured while retriving all cats from database")
    //}

  }

  implicit val customerWrites: Writes[Customer] = (
    (JsPath \ "id").write[Option[Int]] and
      (JsPath \ "name").write[String] and
      (JsPath \ "surname").write[Option[String]] and
      (JsPath \ "gender").write[Option[String]]
    )(unlift(Customer.unapply))

}
