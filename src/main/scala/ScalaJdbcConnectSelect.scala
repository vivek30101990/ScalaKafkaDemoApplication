import java.sql.{Connection,DriverManager}

object ScalaJdbcConnectSelect {
  val url = "jdbc:mysql://localhost:3306/mysecondapplication"
  val driver = "com.mysql.cj.jdbc.Driver"
  val username = "root"
  val password = "password"
  def queryResult(): Unit = {
    // connect to the database named "mysql" on port 8889 of localhost
    var connection:Connection =null.asInstanceOf[Connection]
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * from appl_user")
      while (rs.next) {
        val host = rs.getString("userid")
        val user = rs.getString("username")
        println("host = %s, user = %s".format(host,user))
      }
      connection.close
    } catch {
      case e: Exception => e.printStackTrace
    }

  }
  def insertJSONData(customer: Customer): Int = {
    // connect to the database named "mysql" on port 8889 of localhost
    var result= -1;
    var connection:Connection =null.asInstanceOf[Connection]
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      result = statement.executeUpdate("insert into customer(firstname,lastname,age,addr_street,addr_city,addr_state,addr_postcode,phone_home,phone_fax) values('" + customer.firstName + "','" + customer.lastName + "','" + customer.age + "','" + customer.address.streetAddress + "','"+customer.address.city+"','"+customer.address.state+"','"+customer.address.postalCode+"','"+customer.phoneNumber(0).number+"','"+customer.phoneNumber(1).number+"')")
      connection.close
    } catch {
      case e: Exception => e.printStackTrace
    }
    return result

  }


}