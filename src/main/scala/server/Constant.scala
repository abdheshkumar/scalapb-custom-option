package server

import io.grpc.Metadata.ASCII_STRING_MARSHALLER
import io.grpc.{Context, Metadata}

object Constant {
  // Add a JWT_METADATA_KEY
  val JWT_METADATA_KEY: Metadata.Key[String] =
    Metadata.Key.of("jwt", ASCII_STRING_MARSHALLER)
  val HEADER_NAME_METADATA_KEY: Metadata.Key[String] =
    Metadata.Key.of("header-name", Metadata.ASCII_STRING_MARSHALLER)
  // Add a JWT Context Key
  val JWT_CTX_KEY: Context.Key[String] = Context.key("jwt")

  // Add a JWT Context Key
  val USER_ID_CTX_KEY: Context.Key[String] = Context.key("userId")
}
