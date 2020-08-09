package model
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class ClicksSource(
                         impression_id: Option[String],
                         revenue: Option[Double]
                       )

case class ClicksTarget(
                         impression_id: String,
                         revenue: Double
                       )

object ClicksProtocol extends DefaultJsonProtocol {
  implicit val doubleTestFormat: RootJsonFormat[ClicksSource] = jsonFormat2(ClicksSource)
}