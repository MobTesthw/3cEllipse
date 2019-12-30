package main

import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.control.*
import javafx.scene.layout.Pane
import javafx.scene.transform.Transform
import shape2D.ShapesGroup
import kotlin.math.roundToInt
import kotlin.math.sign


class Controller{

    var radius = 130.0
    var radMultiplier=1.0
    var radDistance=4.0
    var radThreshold=0.5
    var centerRad=3.0

    @FXML lateinit var viewportPane:Pane
    @FXML lateinit var ta:TextArea
    @FXML lateinit var splitPane:SplitPane
    @FXML lateinit var radDistanceSlider:Slider
    @FXML lateinit var radMultiplierSlider:Slider
    @FXML lateinit var radDistanceLabel: Label
    @FXML lateinit var radMultiplierLabel:Label
    @FXML lateinit var cbCenters:CheckBox
    @FXML lateinit var cbDuplicate:CheckBox
    @FXML lateinit var cbCross:CheckBox
    @FXML lateinit var sThreshold:Slider
    @FXML lateinit var cbLittleCross:CheckBox
    @FXML lateinit var sCenterRad:Slider
    @FXML lateinit var lThreshold:Label
    @FXML lateinit var lCenterRad:Label
    @FXML lateinit var cbAutoDraw:CheckBox

   fun initialize() {

       sCenterRad.tooltip= Tooltip("Centers radius")
       sThreshold.tooltip= Tooltip("Locus threshold")
       radMultiplierSlider.tooltip= Tooltip("Distance between the centers")
       radDistanceSlider.tooltip= Tooltip("Radius")

//       viewportPane.setOnMouseClicked {e->repaintViewport()  }

       radDistanceSlider.valueProperty().addListener { _, _, _ ->
           radDistance = radDistanceSlider.value
           val v:Double=(radDistanceSlider.value*1000).roundToInt().toDouble()/1000
           radDistanceLabel.text=v.toString()
           if(cbAutoDraw.isSelected)repaintViewport()
           ta.appendText("\nnew distance: ${radDistanceSlider.value} ")
       }
       radMultiplierSlider.valueProperty().addListener { _, _, _ ->
           radMultiplier = radMultiplierSlider.value
           val v:Double=( radMultiplierSlider.value*1000).roundToInt().toDouble()/1000
           radMultiplierLabel.text=v.toString()
           if(cbAutoDraw.isSelected)repaintViewport()
           ta.appendText("\nnew radius mult: ${radMultiplierSlider.value} ")
       }
       radDistanceSlider.setOnScroll { e -> radDistanceSlider.value+=radDistanceSlider.blockIncrement*e.deltaY }
       radMultiplierSlider.setOnScroll { e -> radMultiplierSlider.value+=radMultiplierSlider.blockIncrement* sign(e.deltaY) }
       splitPane.setDividerPosition(0,.9)

       sThreshold.valueProperty().addListener { _, _, _ ->
           radThreshold = sThreshold.value
           if(cbAutoDraw.isSelected)repaintViewport()
           lThreshold.text=(((sThreshold.value*100).roundToInt()).toDouble()/100).toString()

       }
       sThreshold.setOnScroll { e -> sThreshold.value+=sThreshold.blockIncrement* sign(e.deltaY) }
       sCenterRad.valueProperty().addListener { _, _, _ ->
           centerRad = sCenterRad.value
           if(cbAutoDraw.isSelected)repaintViewport()
           lCenterRad.text=(((sCenterRad.value*100).roundToInt()).toDouble()/100).toString()

       }
       sCenterRad.setOnScroll { e -> sCenterRad.value+=sCenterRad.blockIncrement* sign(e.deltaY) }
   }

   @FXML private fun repaintViewport()    {

       val w=viewportPane.width
       val h=viewportPane.height

       ta.text=""
       ta.appendText("w =  $w ,  h =  $h\n")

       viewportPane.children.clear()

       viewportPane.children.addAll(ShapesGroup.drawEllipse(w,h,radDistance,radMultiplier,sThreshold.value,cbCenters.isSelected,centerRad))
       if (cbCross.isSelected)viewportPane.children.addAll(ShapesGroup.screenCross(w,h))

       if(cbDuplicate.isSelected)btnDuplicateClick()
   }
    @FXML private fun btnDuplicateClick(){
        val w=viewportPane.width
        val h=viewportPane.height
        val gr1=Group()
        gr1.children.addAll(ShapesGroup.drawEllipse(w,h,radDistance,radMultiplier,sThreshold.value,cbCenters.isSelected,centerRad))
        if (cbCross.isSelected)viewportPane.children.addAll(ShapesGroup.screenCross(w,h))
        gr1.transforms.add((Transform.rotate(40.0,w/2,h/2)))

        val gr2=Group()
        gr2.children.addAll(ShapesGroup.drawEllipse(w,h,radDistance,radMultiplier,sThreshold.value,cbCenters.isSelected,centerRad))
        if (cbCross.isSelected)viewportPane.children.addAll(ShapesGroup.screenCross(w,h))
        gr2.transforms.addAll(Transform.rotate(80.0,w/2,h/2))

        viewportPane.children.addAll(gr1, gr2)
    }

}


