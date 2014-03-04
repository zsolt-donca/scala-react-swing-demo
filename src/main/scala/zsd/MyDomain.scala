package zsd

import scala.react.Domain
import scala.swing.SimpleSwingApplication

object MyDomain extends Domain {

  val scheduler = new SwingScheduler()
  val engine = new Engine

  trait ReactiveSimpleSwingApplication extends SimpleSwingApplication {
    override def main(args: Array[String]) {
      schedule {
        startup(args)
      }
      start()
    }
  }

}