import java.io.FileNotFoundException
import scala.io._

object T1ReadEvents {
  def readEvents[EventType](files : List[String], parser: String => List[EventType]): List[EventType] = {
      files.flatMap(file => {
        try {
          val jsonFile = Source.fromFile(file)
          val jsonString = jsonFile.getLines().mkString
          jsonFile.close()
          parser(jsonString)
        }catch {
          case e: FileNotFoundException => println(e); Nil;
          case e: Exception => println(s"[INFO] file name: $file. Exception Info: $e"); Nil;
        }
      })
  }
}

