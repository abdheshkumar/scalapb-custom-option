import scalapb.compiler.Version.scalapbVersion

scalaVersion := "2.12.10"

/*Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value
)*/
/*Compile / PB.targets := Seq(
  scalapb.gen(
    grpc = true,
    javaConversions = true
  ) -> (Compile / sourceManaged).value,
  PB.gens.java -> (Compile / sourceManaged).value
)*/
enablePlugins(Fs2Grpc)


libraryDependencies ++= Seq(
  // For finding google/protobuf/descriptor.proto
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf",
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)
