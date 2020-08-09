package sparkutils

import org.apache.spark.sql.SparkSession

trait SparkSessionConfig {
  def getSparkSession(appName : String, mode : String) : SparkSession = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName(appName)
      .config("spark.master", mode)
      .getOrCreate()

    spark
  }
}
