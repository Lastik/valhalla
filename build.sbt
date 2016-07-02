name := "valhalla"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "spray nightlies" at "http://nightlies.spray.io"

val akkaVersion = "2.3.6"
val sprayVersion = "1.3.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka"  %% "akka-actor"       % akkaVersion,
  "com.typesafe.akka"  %% "akka-slf4j"       % akkaVersion,
  "ch.qos.logback"      % "logback-classic"  % "1.0.13",
  "io.spray"            %% "spray-can"        % sprayVersion,
  "io.spray"            %% "spray-routing"    % sprayVersion,
  "io.spray"           %% "spray-json"       % sprayVersion,
  "org.specs2"         %% "specs2"           % "2.4.13"         % "test",
  "io.spray"            %% "spray-testkit"    % sprayVersion % "test",
  "com.typesafe.akka"  %% "akka-testkit"     % akkaVersion        % "test",
  "com.novocode"        % "junit-interface"  % "0.7"          % "test->default",
  "joda-time" % "joda-time" % "2.9.4"
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
