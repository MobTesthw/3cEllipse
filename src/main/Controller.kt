package main

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
import javafx.scene.transform.Transform
import shape2D.ShapesGroup
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign
import kotlin.math.sqrt



class Controller{

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

//       viewportPane.setOnMouseClicked {e->repaintViewport()  }

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
       radDistanceSlider.setOnScroll { e -> radDistanceSlider.value+=radDistanceSlider.blockIncrement*e.deltaY }
       radMultiplierSlider.setOnScroll { e -> radMultiplierSlider.value+=radMultiplierSlider.blockIncrement* sign(e.deltaY) }
       splitPane.setDividerPosition(0,.9)

   }

   private fun repaintViewport()    {
       val w=viewportPane.width
       val h=viewportPane.height

       ta.text=""

       viewportPane.children.clear()

       viewportPane.children.addAll(ShapesGroup.drawEllipse(w,h,radDistance,radMultiplier),ShapesGroup.screenCross(w,h))
   }
    @FXML private fun btnDuplicateClick(){
        val w=viewportPane.width
        val h=viewportPane.height
        val gr1=Group()
        gr1.children.addAll(ShapesGroup.drawEllipse(w,h,radDistance,radMultiplier),ShapesGroup.screenCross(w,h))
        gr1.transforms.add((Transform.rotate(40.0,w/2,h/2)))

        val gr2=Group()
        gr2.children.addAll(ShapesGroup.drawEllipse(w,h,radDistance,radMultiplier),ShapesGroup.screenCross(w,h))
        gr2.transforms.addAll(Transform.rotate(80.0,w/2,h/2))

        viewportPane.children.addAll(gr1, gr2)
    }

}


