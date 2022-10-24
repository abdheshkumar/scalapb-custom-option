addSbtPlugin("org.typelevel" % "sbt-fs2-grpc" % "2.4.12")
// addSbtPlugin("org.lyranthe.fs2-grpc" % "sbt-java-gen" % "0.10.0")
/*addSbtPlugin("com.thesamet" % "sbt-protoc" % "1.0.6")
libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.11.1"*/

addSbtPlugin("com.thesamet" % "sbt-protoc" % "1.0.6")

dependencyOverrides += "com.thesamet.scalapb" %% "compilerplugin" % "0.11.11"
