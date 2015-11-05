package controllers

import Models.CustomersModel.{Customer, Customers}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
//import slick.driver.H2Driver.api._
import slick.driver.MySQLDriver.api._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Application extends Controller {

  val db = Database.forConfig("mysqldb")
  /*System.setProperty("javax.net.ssl.keyStore","path_to_keystore_file")
  System.setProperty("javax.net.ssl.keyStorePassword","password")
  System.setProperty("javax.net.ssl.trustStore","path_to_truststore_file")
  System.setProperty("javax.net.ssl.trustStorePassword","password")
*/

  val customers : TableQuery[Customers] = TableQuery[Customers]

  def index = Action {
    var list: List[Customer] = List[Customer](new Customer(Option(1),"crap", Option("crapovitch"), Option("male")), new Customer(Option(2),"vasya", Option("vasilievits"), Option("male")),
    new Customer(Option(3),"kolya", Option("kolyanovitch"), Option("male")))
    Ok(Json.toJson(list))
  }

  def getCust = Action {
    Ok(Json.toJson(Await.result(db.run(customers.result), Duration.Inf)))
  }

  implicit val customerWrites: Writes[Customer] = (
    (JsPath \ "id").write[Option[Int]] and
      (JsPath \ "name").write[String] and
      (JsPath \ "surname").write[Option[String]] and
      (JsPath \ "gender").write[Option[String]]
    )(unlift(Customer.unapply))

}
