package parser

import model.{ImpressionsSource, ImpressionsTarget}
import spray.json.JsonParser

import scala.collection.mutable.ListBuffer

object ImpressionsParser {
  def parser(jsonString : String) : List[ImpressionsTarget] = {
    val ast = JsonParser(jsonString)
    import model.ImpressionsProtocol._
    val DTs = ast.convertTo[List[ImpressionsSource]]
    DTs.map { dt =>
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
        case None => "null"
      }
      val id = dt.id match {
        case Some(d) => d
        case None => "null"
      }
      ImpressionsTarget(app_id = app_id
        ,advertiser_id = advertiser_id
        ,country_code = country_code
        ,id= id)
    }
  }
}
