import spray.json.{DefaultJsonProtocol, JsonParser, RootJsonFormat}

import scala.collection.mutable.ListBuffer
import scala.io.Source

case class ImpressionsSource(app_id: Option[Either[Int, String]], advertiser_id: Option[Either[Int, String]]
                       , country_code: Option[String], id: Option[String])
object ImpressionsProtocol extends DefaultJsonProtocol {
  implicit val doubleTestFormat: RootJsonFormat[ImpressionsSource] = jsonFormat4(ImpressionsSource)
}

case class ImpressionsTarget(app_id: Int, advertiser_id: Int, country_code: String, id: String)


object spraytest {
  def main(args: Array[String]): Unit = {
    val json = """[{"a":"123"}, {"a":123}, {"a":123.0}]"""
    val f = Source.fromFile("src/main/resources/impressions.json").getLines().mkString
    val ast = JsonParser(f)
    import ImpressionsProtocol._
    val DTs = ast.convertTo[List[ImpressionsSource]]
    DTs.map { dt =>
      dt.app_id match {
        case Some(d) => d match {
          case Left(d) => println("found Int"); d
          case Right(d) => println("found String");d.toInt
        }
        case None => null
      }
    }

  var impressions_list = new ListBuffer[ImpressionsTarget]()
  val k = DTs.map { dt =>
    val app_id = dt.app_id match {
      case Some(d) => d match {
        case Left(d) => d
        case Right(d) => d.toInt
      }
      case None => 0
    }
    val advertiser_id = dt.advertiser_id match {
      case Some(d) => d match {
        case Left(d) => d
        case Right(d) => d.toInt
      }
      case None => 0
    }
    val country_code = dt.country_code match {
      case Some(d) => d
      case None => null
    }
    val id = dt.id match {
      case Some(d) => d
      case None => null
    }
    impressions_list += ImpressionsTarget(app_id = app_id
      ,advertiser_id = advertiser_id
      ,country_code = country_code
      ,id= id)
  }
  println(impressions_list(1459))
  }
}