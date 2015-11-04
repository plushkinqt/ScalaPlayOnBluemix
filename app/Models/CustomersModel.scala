package Models

import slick.driver.MySQLDriver.api._
/**
 * Created by yevgen on 11/4/15.
 */
object CustomersModel {

  case class Customer(id: Option[Int], name: String, surname: Option[String], gender: Option[String])

  class Customers(tag: Tag) extends Table[Customer](tag, "customers"){

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def surname = column[String]("surname")
    def gender = column[String]("gender")

    def * = (id.?, name, surname.?, gender.?) <> (Customer.tupled, Customer.unapply)

  }
}
