package zsd

import scala.swing._
import scala.swing.BorderPanel.Position
import zsd.MyDomain._

// all the scala-react types below are accessed through the object 'MyDomain'

object ScalaReactSwingDemo extends ReactiveSimpleSwingApplication with Observing {
  override def top: Frame = new MainFrame() {

    contents = new BorderPanel() {
      val label = new Label()
      add(label, Position.North)

      val textField = new TextArea()
      add(textField, Position.Center)

      val button = new Button()
      add(button, Position.South)
      button.action = new Action("Next") {
        override def apply(): Unit = nextButton << Unit // emitting to the event
      }

      // event that is triggered when the button is clicked
      val nextButton = EventSource[Unit]

      // a stream that contains the responses
      val response = Var[String]("")

      // whenever the event 'nextButton' is triggered, we update the Signal 'response'
      observe(nextButton) { _ =>
        response() = textField.text
        textField.text = ""
      }

      val programFlow = Reactor.flow {
        self =>

          display("Welcome to the demo app")
          self awaitNext nextButton

          display("What is your first name?")
          val firstName = self awaitNext response

          display("What is your last name?")
          val lastName = self awaitNext response

          display("Hello, " + firstName + " " + lastName + "!")
          self awaitNext nextButton

          display("The end")
      }

      def display(message : String) {
        label.text = message
      }
    }

    preferredSize = new Dimension(200, 100)
    pack()
  }
}
