import model.{ClicksTarget, ImpressionsTarget}
import org.apache.spark.sql.{Column, DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
import sparkutils.SparkSessionConfig

object T3TopFiveAdvertisers extends SparkSessionConfig{

  def calculateTopFiveAdvertisers(impression: List[ImpressionsTarget],
                                  clicks: List[ClicksTarget]): String  = {
    // GET SPARK SESSION
    val spark = getSparkSession("movie_ratings", "local")
    import spark.implicits._
    // INPUT JSON TO DATAFRAMES
    val impression_df = impression.toDF
    val clicks_df = clicks.toDF
    // TOP ADVERTISERS LOGIC
    val leads_df = impression_df.join(clicks_df
      , impression_df("id") === clicks_df("impression_id")
      , "outer")
    val groupby = leads_df.groupBy($"app_id", $"country_code", $"advertiser_id")
    val topAdvertisers = groupby.agg(sum($"revenue")
      .alias("total_revenue"))
      .orderBy($"country_code",$"app_id", $"total_revenue".desc)
    val topAdvertiserFilter = topAdvertisers.groupBy("app_id","country_code")
      .agg(collect_list("advertiser_id").alias("top_advertisers"))
    val topFiveAdvertisers = topAdvertiserFilter
      .select($"app_id", $"country_code",
        slice($"top_advertisers",1,5).alias("recommended_advertiser_ids"))
    val result = "[".concat(topFiveAdvertisers.toJSON.collect().mkString(",")).concat("]")
    spark.stop()
    result
  }
}
