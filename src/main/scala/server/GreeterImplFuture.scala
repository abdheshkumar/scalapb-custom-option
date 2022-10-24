package server

import com.example.{HelloReply, HelloRequest, HelloWorldGrpc}

import scala.concurrent.Future

class GreeterImplFuture extends HelloWorldGrpc.HelloWorld {
  override def sayHello(
      req: HelloRequest
  ): Future[HelloReply] = {
    println("***GreeterImplFuture*****" + Constant.JWT_CTX_KEY.get())
    val reply = HelloReply(
      "Hello " + req.name + " " + Constant.JWT_CTX_KEY.get()
    )
    Future.successful(reply)
  }
}
