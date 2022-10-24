package server

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.example.{GreeterFs2Grpc, HelloRequest, HelloWorldFs2Grpc}
import fs2.grpc.syntax.all._
import io.grpc.netty.NettyChannelBuilder
import io.grpc.{ManagedChannel, Metadata}

object GrpcClient extends IOApp {
  val managedChannelResource: Resource[IO, ManagedChannel] =
    NettyChannelBuilder
      .forAddress("127.0.0.1", 9999)
      .usePlaintext()
      .intercept(new LoggingClientInterceptor)
      .resource[IO]

  val greeterClient: Resource[IO, GreeterFs2Grpc[IO, Metadata]] =
    managedChannelResource.flatMap(GreeterFs2Grpc.stubResource(_))

  val helloWorldClient =
    managedChannelResource.flatMap(HelloWorldFs2Grpc.stubResource[IO])

  override def run(args: List[String]): IO[ExitCode] = {
    val metadata = new Metadata()
    metadata.put(
      Constant.HEADER_NAME_METADATA_KEY,
      "header value passed from the client"
    )
    metadata.put(Constant.JWT_METADATA_KEY, "admin_token")
    greeterClient
      .use(_.sayHello(HelloRequest("Greeter API: sas"), metadata))
      .flatMap(res => IO(println(res)))
      .flatMap(_ =>
        helloWorldClient.use(
          _.sayHello(HelloRequest("HelloWorld API: sas"), metadata)
        )
      )
      .flatMap(res => IO(println(res)).as(ExitCode.Success))
  }
}
