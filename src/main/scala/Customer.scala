case class Customer(var firstName: String = "",
                    var lastName: String = "",
                    var age: String = "",
                    var address:Address = new Address,
                    var phoneNumber:Array[PhoneNumber]= new Array[PhoneNumber](2))
{
}

