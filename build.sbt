name := "AppliftCaseStudy"
version := "0.1"
scalaVersion := "2.11.12"

scalacOptions += "-target:jvm-1.8"

val sparkVersion        = "2.4.4"
val scalaTestVersion    = "3.1.0"
val sparkFastTestVersion = "2.3.1_0.15.0"

scalacOptions += "-target:jvm-1.8"

// SPARK, CMD LINE PARSING AND SCALA TEST DEPENDENCIES
libraryDependencies ++= Seq(
  "org.kohsuke.args4j" % "args4j-maven-plugin" % "2.33",
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "io.spray" %% "spray-json" % "1.3.5"
)

// SPARK FAST TEST FOR DATAFRAME TO DATAFRAME COMPARISON
resolvers += "spark test" at "https://dl.bintray.com/spark-packages/maven"
libraryDependencies += "MrPowers" % "spark-fast-tests" % "0.20.0-s_2.11" % Test

// TO AVOID DE-DUPLICATION ISSUE WHILE ASSEMBLING
assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("org", "aopalliance", xs @ _*) => MergeStrategy.first
  case PathList("javax", "inject", xs @ _*) => MergeStrategy.first
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case "git.properties" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}