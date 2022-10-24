package server

import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.kernel.Async
import com.example.GreeterFs2Grpc
import fs2.grpc.syntax.all._
import io.grpc.Metadata
import io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

object GrpcServer extends IOApp {

  def resource[F[_]: Async](
      service: GreeterFs2Grpc[F, Metadata]
  ): F[Nothing] = {
    (for {
      serviceDef <- GreeterFs2Grpc.bindServiceResource(service)
      server <- NettyServerBuilder
        .forPort(9999)
        .addService(ProtoReflectionService.newInstance())
        .addService(serviceDef)
        .resource[F]
        .evalMap(server => Async[F].delay(server.start()))
    } yield println(server)).useForever
  }

  override def run(args: List[String]): IO[ExitCode] = {
    resource[IO](new GreeterImp) *> IO(ExitCode.Success)
  }
}
