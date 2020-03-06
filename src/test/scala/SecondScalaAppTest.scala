import java.util.Properties

import ProducerJSONObject.loadJSONObject
import org.scalatest.FunSuite
import scala.util.Properties
class SecondScalaAppTest extends FunSuite{
/*  test(testName = "ConsumerObject.insertJSONDataintoDB"){
    var result = ConsumerObject.insertJSONDataintoDB(new Customer("test","test","29","221Baker Street","","London","221","111","222"))
    assert(result==1)
  }*/
  test(testName = "ConsumerObject.isNotBlank"){
    var result = ConsumerObject.isNotBlank("abcd")
    assert(result===true)
  }
  test(testName = "ConsumerObject.validateandStore_firstName Validation"){
    var cust = new Customer("","aaa","1",new Address("",""),new Array[PhoneNumber](2))
    var result = ConsumerObject.validateandStore(cust)
    assert(result==true)
  }
  test(testName = "ConsumerObject.validateandStore_lastName Validation"){
    var cust = new Customer("aaa","","1",new Address("",""),new Array[PhoneNumber](2))
    var result = ConsumerObject.validateandStore(cust)
    assert(result==true)
  }
  test(testName = "ConsumerObject.validateandStore_age Validation"){
    var cust = new Customer("aaa","sss","0",new Address("",""),new Array[PhoneNumber](2))
    var result = ConsumerObject.validateandStore(cust)
    assert(result==true)
  }
  test(testName = "ProducerJSONObject.writeToKafka"){
    val fileName= "src/main/resources/customer.JSON"
    val data = loadJSONObject(fileName)
    var result = ProducerJSONObject.writeToKafka(data,"vivek-test")
    assert(result.equalsIgnoreCase("Success")==true)
  }
  test(testName = "ProducerJSONObject.loadJSONObject"){
    val fileName= "src/main/resources/customer.JSON"
    var result = ProducerJSONObject.loadJSONObject(fileName)
    assert(result.length>0)
  }
  test(testName = "ProducerJSONObject.loadProperties"){
    val prop = ProducerJSONObject.loadProperties("application.properties")
    assert((prop.isInstanceOf[Properties]))
  }

}
