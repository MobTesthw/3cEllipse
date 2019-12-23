package sample

import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.control.SplitPane
import javafx.scene.control.TextArea
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt


class Controller{
    val delta=0.5
    var radius = 130.0
    var radMultiplier=1.0
    var radDistance=4.0

    @FXML lateinit var viewportPane:Pane
    @FXML lateinit var ta:TextArea
    @FXML lateinit var splitPane:SplitPane
    @FXML lateinit var radDistanceSlider:Slider
    @FXML lateinit var radMultiplierSlider:Slider
    @FXML lateinit var radDistanceLabel: Label
    @FXML lateinit var radMultiplierLabel:Label

   fun initialize() {
//       viewportPane.heightProperty().addListener { observable, oldValue, newValue -> repaintViewport() }
//       repaintViewport()
       viewportPane.setOnMouseClicked {e->repaintViewport()  }
       radDistanceSlider.valueProperty().addListener { observable, oldValue, newValue ->
           radDistance = radDistanceSlider.value
           val v:Double=(radDistanceSlider.value*1000).roundToInt().toDouble()/1000
           radDistanceLabel.text=v.toString()
           repaintViewport()
           ta.appendText("\nnew distance: ${radDistanceSlider.value} ")
       }
       radMultiplierSlider.valueProperty().addListener { observable, oldValue, newValue ->
           radMultiplier = radMultiplierSlider.value
           val v:Double=( radMultiplierSlider.value*1000).roundToInt().toDouble()/1000
           radMultiplierLabel.text=v.toString()
           repaintViewport()
           ta.appendText("\nnew radius mult: ${radMultiplierSlider.value} ")
       }
   }

   fun repaintViewport()    {
       ta.text=""

        splitPane.setDividerPosition(0,.9)

        viewportPane.children.clear()
        val w=viewportPane.width
        val h=viewportPane.height

        val a=h/(0.00000001+radDistance)
        val b=2.0*a/ sqrt(3.0)

       radius=3* sqrt(b*b+a*a)/4
       radius*=radMultiplier

        val xl=w/2-b/2
        val yl=h/2+a/2

        val xr=w/2+b/2
        val yr=h/2+a/2

        val xt=w/2
        val yt=h/2-a/2

        val group= Group()
//
        val circleL = Circle(3.0, Color.RED)
        circleL.relocate(xl, yl)

        val circleR = Circle(3.0, Color.BLUE)
        circleR.relocate(xr, yr)

        val circleT = Circle(3.0, Color.YELLOW)
        circleT.relocate(xt, yt)

        val rectangle = Rectangle(100.0, 100.0, Color.RED)
        rectangle.relocate(70.0, 70.0)



        group.children.addAll(circleT,circleR,circleL)


       ta.appendText("w= $w  h=$h  xl= $xl  yl= $yl ")

       ta.appendText("\nw = ${w.toInt()}  h= ${h.toInt()}")
//       var n=0
//       var br=0

       for (i in 0..w.toInt()) {

           for (j in 0..h.toInt()) {
               val rl= sqrt((i-xl)*(i-xl)+(j-yl)*(j-yl))
               val rr= sqrt((i-xr)*(i-xr)+(j-yr)*(j-yr))
               val rt= sqrt((i-xt)*(i-xt)+(j-yt)*(j-yt))



               val rad= rl+rr+rt

//               n++
//               br++
//               if(br>100000)break
//               if(n>1000){
//                   ta.appendText("\nrad - radius  = ${rad - radius}")
//                   n=0
//               }

//               ta.appendText("delta = ${rad-radius}")
//               ta.appendText("\nrad = ${rad}")

               if (abs(rad - radius) <= delta) {
                   group.children.add(Line(i.toDouble(), j.toDouble(), i.toDouble(), j.toDouble() ))
//                   ta.appendText("i= $i  j= $j")
               }
           }
       }
       viewportPane.children.addAll(group)
    }

}


