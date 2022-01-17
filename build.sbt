import scalapb.compiler.Version.scalapbVersion

scalaVersion := "2.12.10"

//1.-------------It doesn't work------------------
/*Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value
)*/

//2.------------It works without Fs2Grpc plugin----
/*Compile / PB.targets := Seq(
  scalapb.gen(
    grpc = true,
    javaConversions = true
  ) -> (Compile / sourceManaged).value,
  PB.gens.java -> (Compile / sourceManaged).value
)*/

//3.-----------It works with Fs2Grpc with below settings---
Compile / PB.targets ++= Seq(
  PB.gens.java -> (Compile / sourceManaged).value
)
enablePlugins(Fs2Grpc)
scalapbCodeGeneratorOptions ++= Seq(
  CodeGeneratorOption.FlatPackage,
  CodeGeneratorOption.JavaConversions
)
//--------------------------------------------
libraryDependencies ++= Seq(
  // For finding google/protobuf/descriptor.proto
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf",
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)
