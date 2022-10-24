package server

import cats.effect.IO
import com.example.{GreeterFs2Grpc, HelloReply, HelloRequest}
import io.grpc.Metadata

class GreeterImp extends GreeterFs2Grpc[IO, Metadata] {
  override def sayHello(
      request: HelloRequest,
      ctx: Metadata
  ): IO[HelloReply] = {
    IO.consoleForIO.println(request) *> IO(HelloReply("Response from server"))
  }
}
