import T1ReadEvents.readEvents
import cli.CliArgs
import model._
import org.kohsuke.args4j.{CmdLineException, CmdLineParser}
import parser.{ClicksParser, ImpressionsParser}
import spray.json._

object Main {
  def main(args: Array[String]): Unit = {
    // PARSING CMD LINE ARGUMENTS
    val parser = new CmdLineParser(CliArgs)
    try {
      parser.parseArgument(args: _*)
    } catch {
      case e: CmdLineException =>
        print(s"Error:${e.getMessage}\n Usage:\n")
        parser.printUsage(System.out)
        System.exit(1)
    }
    val clickFiles = CliArgs.clickFiles.split(",").map(x => x.trim).toList
    val impressionFiles = CliArgs.impressionFiles.split(",").map(x => x.trim).toList

    // READING THE JSON EVENTS
    val clicks = readEvents[ClicksTarget](clickFiles, ClicksParser.parser)
    val impressions = readEvents[ImpressionsTarget](impressionFiles,ImpressionsParser.parser)
    println("Impressions count: " + impressions.size)
    println("Clicks count: " + clicks.size)

    //APP PERFORMANCE METRICS
    val leads = T2AppPerformanceMetrics.getLeads(clicks = clicks, impressions = impressions)
    val appPerformanceByCountry = T2AppPerformanceMetrics.appPerformanceByCountry(leads)
    println(appPerformanceByCountry.prettyPrint)

    //TOP FIVE ADVERTISERS
    val topFiveAdvertisers = T3TopFiveAdvertisers.calculateTopFiveAdvertisers(impressions, clicks)
    val topFiveAdvertisersJson = JsonParser(topFiveAdvertisers)
    println(topFiveAdvertisersJson.prettyPrint)
  }
}
