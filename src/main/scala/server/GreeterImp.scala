package server

import cats.effect.IO
import com.example.{GreeterFs2Grpc, HelloReply, HelloRequest}
import io.grpc.Metadata

class GreeterImp extends GreeterFs2Grpc[IO, Metadata] {
  override def sayHello(
      request: HelloRequest,
      ctx: Metadata
  ): IO[HelloReply] = {
    //fs2-grpc doesn't propagate  grpc's Context
    // https://github.com/typelevel/fs2-grpc/pull/115
    // https://github.com/typelevel/fs2-grpc/pull/86/commits/25456d44e84fc3e14a9961dc88d6704584724918
    println("****HelloWorldImplIO****" + Constant.JWT_CTX_KEY.get())
    val reply =
      HelloReply(name =
        s"Hello ${request.name} JWT_CTX_KEY=${Constant.JWT_CTX_KEY.get()}, HEADER_NAME_METADATA_KEY=${ctx
          .get(Constant.HEADER_NAME_METADATA_KEY)}"
      )
    IO.consoleForIO.println(request) *> IO(reply)
  }
}
