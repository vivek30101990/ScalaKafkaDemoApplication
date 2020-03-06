import java.io.FileNotFoundException
import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties

import ProducerJSONObject.{getClass, loadProperties}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._
import net.liftweb.json._

import scala.io.Source
object ConsumerObject  {

  implicit val formats = DefaultFormats



  def main(args: Array[String]): Unit = {
    consumeFromKafka("vivektest")
  }

  def insertJSONDataintoDB(cust: Customer) = {
    ScalaJdbcConnectSelect.insertJSONData(cust)
  }
  def isNotBlank(str: Option[String]): Boolean =
    str.exists(_.trim.nonEmpty)

  def isNotBlank(str: String): Boolean =
    isNotBlank(Option(str))

  def validateandStore(customer: Customer): Boolean = {
    var error = false;
    if(!isNotBlank(customer.firstName))error=true
    if(!isNotBlank(customer.lastName))error=true
    if(customer.age.toInt<=0)error=true

    if(!error){
      println("Inserting Data into Database")
      //val cust = new Customer(firstName,lastName,age,addr_street,addr_city,addr_state,addr_postcode,phone_home,phone_fax)
      insertJSONDataintoDB(customer)
    }else{
      println("Did not Insert as Input Validation Failed")
    }
    return error
  }

  def consumeFromKafka(topic: String) = {
    val props = getProps()
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList(topic))
    while (true) {
      var jsonData = new ListBuffer[String]()
      val record = consumer.poll(1000).asScala
      for (data <- record.iterator) {
        jsonData+=data.value()
      }
      if(!jsonData.isEmpty) {

        val jValue = parse(jsonData.mkString)
        val customer = jValue.extract[Customer]
       /* println("Obj::"+Obj.address.state)
        val pn = (Obj.phoneNumber)
        println("Obj::"+(pn(0)).`type`)*/
        validateandStore(customer)

      }
    }
  }

  def getProps(): Properties = {
    val properties: Properties = loadProperties("application.properties")
    val props = new Properties()
    props.put("bootstrap.servers", properties.getProperty("bootstrap.servers"))
    props.put("key.deserializer", properties.getProperty("key.deserializer"))
    props.put("value.deserializer", properties.getProperty("value.deserializer"))
    props.put("auto.offset.reset", properties.getProperty("auto.offset.reset"))
    props.put("group.id", properties.getProperty("group.id"))
    return props
  }

  def loadProperties(value: "application.properties"): Properties = {
    val url = getClass.getResource("application.properties")
    val properties: Properties = new Properties()

    if (url != null) {
      val source = Source.fromURL(url)
      properties.load(source.bufferedReader())
    }
    else {
      println("properties file cannot be loaded at path ")
      throw new FileNotFoundException("Properties file cannot be loaded")
    }
    return properties
  }
}
