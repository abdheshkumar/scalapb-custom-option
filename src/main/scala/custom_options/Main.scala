package custom_options

object Main extends App {

  assert(
    my_opts.MyOptsProto.myFileOption.get(use_opts.UseOptsProto.scalaDescriptor.getOptions) == Some(
      "hello!"
    )
  )

  assert(
    my_opts.MyOptsProto.myMessageOption.get(use_opts.OneMessage.scalaDescriptor.getOptions).get ==
      my_opts.MyMessageOption().update(_.priority := 17)
  )

  val numberField = use_opts.OneMessage.scalaDescriptor.findFieldByName("number").get
  assert(
    my_opts.Wrapper.tags.get(numberField.getOptions) == Seq(
      my_opts.Tag(name = Some("tag1")),
      my_opts.Tag(name = Some("tag2"))
    )
  )

  // If you prefer to start with the descriptor, you use can the `extension`
  // method available through implicit conversion:
  assert(
    use_opts.UseOptsProto.scalaDescriptor.getOptions
      .extension(my_opts.MyOptsProto.myFileOption) == Some("hello!")
  )

  assert(
    use_opts.OneMessage.scalaDescriptor.getOptions
      .extension(my_opts.MyOptsProto.myMessageOption)
      .get ==
      my_opts.MyMessageOption().update(_.priority := 17)
  )
//Message
  val message = use_opts.OneMessage.scalaDescriptor.getOptions
    .extension(my_opts.MyOptsProto.messageAuthPolicy)

  println(s"Message: $message")

//Service
  val service = use_opts.GreeterGrpc.Greeter.scalaDescriptor.getOptions.extension(
    my_opts.MyOptsProto.serviceAuthPolicy
  )
  println(s"Service: $service")

//Method
  val method = use_opts.GreeterGrpc.Greeter.scalaDescriptor.methods
    .map(
      _.getOptions.extension(
        my_opts.MyOptsProto.methodAuthPolicy
      )
    )
    .toList

  println(s"Method: $method")

  assert(
    numberField.getOptions.extension(my_opts.Wrapper.tags) == Seq(
      my_opts.Tag(name = Some("tag1")),
      my_opts.Tag(name = Some("tag2"))
    )
  )
}
