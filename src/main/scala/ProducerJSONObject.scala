import java.io.{File, FileNotFoundException}
import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer._

import scala.io.Source

object ProducerJSONObject {

  def main(args: Array[String]): Unit = {
    val fileName= "src/main/resources/customer.JSON"
      val data = loadJSONObject(fileName)
      val result = writeToKafka(data, "vivektest")

  }
  def loadJSONObject(url:String): String = {
    val data= Source.fromFile(url).mkString;
    return data
  }

  def loadProperties(value:String): Properties = {
    val url = getClass.getResource(value)
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

  def writeToKafka(topicData: String, topic: String): String = {

    val properties: Properties = loadProperties("application.properties")
    val props = new Properties()
    props.put("bootstrap.servers", properties.getProperty("bootstrap.servers"))
    props.put("key.serializer", properties.getProperty("key.serializer"))
    props.put("value.serializer", properties.getProperty("value.serializer"))
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String](topic, "key", topicData)
    producer.send(record)
    producer.close()
    return "Success"
  }
}
