package custom_options

import com.example.{GreeterGrpc, OneMessage, UseOptsProto}
import com.example.options.{MyMessageOption, MyOptsProto, Tag, Wrapper}

object Main extends App {

  assert(
    MyOptsProto.myFileOption.get(
      UseOptsProto.scalaDescriptor.getOptions
    ) == "hello!"
  )

  assert(
    MyOptsProto.myMessageOption
      .get(OneMessage.scalaDescriptor.getOptions)
      .get ==
      MyMessageOption().update(_.priority := 17)
  )

  val numberField = OneMessage.scalaDescriptor.findFieldByName("number").get
  assert(
    Wrapper.tags.get(numberField.getOptions) == Seq(
      Tag(name = "tag1"),
      Tag(name = "tag2")
    )
  )

  // If you prefer to start with the descriptor, you use can the `extension`
  // method available through implicit conversion:
  assert(
    UseOptsProto.scalaDescriptor.getOptions
      .extension(MyOptsProto.myFileOption) == "hello!"
  )

  assert(
    OneMessage.scalaDescriptor.getOptions
      .extension(MyOptsProto.myMessageOption)
      .get ==
      MyMessageOption().update(_.priority := 17)
  )
//Message
  val message = OneMessage.scalaDescriptor.getOptions
    .extension(MyOptsProto.messageAuthPolicy)

  println(s"Message: $message")

//Service
  val service = GreeterGrpc.Greeter.scalaDescriptor.getOptions.extension(
    MyOptsProto.serviceAuthPolicy
  )
  println(s"Service: $service")

//Method
  val method = GreeterGrpc.Greeter.scalaDescriptor.methods
    .map(
      _.getOptions.extension(
        MyOptsProto.methodAuthPolicy
      )
    )
    .toList

  println(s"Method: $method")

  val aa = GreeterGrpc.SERVICE.getSchemaDescriptor
  println(aa)
  assert(
    numberField.getOptions.extension(Wrapper.tags) == Seq(
      Tag(name = "tag1"),
      Tag(name = "tag2")
    )
  )
}
