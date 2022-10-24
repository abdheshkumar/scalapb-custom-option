package server

import io.grpc._

class AuthorizationInterceptor extends ServerInterceptor {

  override def interceptCall[ReqT, RespT](
      serverCall: ServerCall[ReqT, RespT],
      metadata: Metadata,
      serverCallHandler: ServerCallHandler[ReqT, RespT]
  ): ServerCall.Listener[ReqT] = {
    //  Get token from Metadata
    val token = metadata.get(Constant.JWT_METADATA_KEY)
    println(s"AuthorizationInterceptor#interceptCall token: $token")
    val ctx = Context.current
      .withValue(Constant.USER_ID_CTX_KEY, s"my-value1-$token")
      .withValue(Constant.JWT_CTX_KEY, s"my-value2-$token")
    //logger.info(s"jwt.getPayload ${jwt.getPayload}")
    Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler)
  }
}

object AuthorizationInterceptor {
  val USERINFO_CONTEXT_KEY: Context.Key[String] = Context.key("user_info")
  val AUTHORIZATION = "Authorization"
  val AUTHORIZATION_METADATA_KEY =
    Metadata.Key.of(AUTHORIZATION, Metadata.ASCII_STRING_MARSHALLER)
}
