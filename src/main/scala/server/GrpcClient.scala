package server

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.example.{GreeterFs2Grpc, HelloRequest}
import fs2.grpc.syntax.all._
import io.grpc.netty.NettyChannelBuilder
import io.grpc.{ManagedChannel, Metadata}

object GrpcClient extends IOApp {
  val managedChannelResource: Resource[IO, ManagedChannel] =
    NettyChannelBuilder
      .forAddress("127.0.0.1", 9999)
      .usePlaintext()
      .resource[IO]

  val client: Resource[IO, GreeterFs2Grpc[IO, Metadata]] =
    managedChannelResource.flatMap(GreeterFs2Grpc.stubResource(_))

  override def run(args: List[String]): IO[ExitCode] = {
    client
      .use(_.sayHello(HelloRequest("sas"), new Metadata()))
      .flatMap(res => IO(println(res)).as(ExitCode.Success))
  }
}
