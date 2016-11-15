
name := "NewPlayApp"

version := "1.0"

lazy val `newplayapp` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.5"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

val sparkVersion = "1.6.1"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % sparkVersion
)

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.3.0"

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

