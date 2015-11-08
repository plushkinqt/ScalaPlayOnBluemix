package Models

import slick.driver.MySQLDriver.api._
/**
 * Created by yevgen on 11/4/15.
 */
object CustomersModel {
  case class Customer(id: Option[Int], name: String, surname: Option[String], gender: Option[String], addressId: Int)

  type Person = (Option[Int],String,Option[String],Option[String], Int)
  class Customers(tag: Tag) extends Table[Person](tag, "customer"){
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def surname = column[String]("surname")
    def gender = column[String]("gender")
    def addressId = column[Int]("address_id")
    def * = (id.?, name, surname.?, gender.?, addressId) <> ((Customer.apply _).tupled, Customer.unapply)
    def address = foreignKey("address",addressId,addresses)(_.id)
  }

  case class Address (id: Int, street: String,city: String)

  class Addresses(tag: Tag) extends Table[Address](tag, "address") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def street = column[String]("street")
    def city = column[String]("city")
    def * = (id,street,city) <> (Address.tupled, Address.unapply)
  }
  lazy val addresses = TableQuery[Addresses]


}
