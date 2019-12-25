package shape2D

import javafx.scene.Group
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import java.awt.Color
import kotlin.math.abs
import kotlin.math.sqrt

class ShapesGroup {
    companion object{
        fun screenCross(w:Double,h:Double, color: Color = Color.GREEN):Group{

            val group=Group()

            val lineHorizontal = Line(0.0,h/2,w,h/2)
            lineHorizontal.stroke= convertColorAwtToJfx(color)

            val lineVertical = Line(w/2,0.0,w/2,h)
            lineVertical.stroke= convertColorAwtToJfx(color)

            group.children.addAll( lineHorizontal , lineVertical   )

            return group
        }
        fun drawEllipse(w:Double,h:Double,radDistance:Double, radMultiplier:Double=1.0,showCenters:Boolean=true):Group{
            val delta=0.5 //threshold to define point to draw

            val a=h/(0.00000001+radDistance) // a - triangle height a=a1+a2 top and bottom parts

            val b=2.0*a/ sqrt(3.0)
//            val a2 = b/4 //lowest triangle height part
//            val a1=a-a2
            val a1= sqrt(3.0)*b/3
            val a2=a-a1

            var radius=3* sqrt(b*b+a*a) /4
            radius*=radMultiplier

            val xl=w/2-b/2
            val yl=h/2+a2

            val xr=w/2+b/2
            val yr=h/2+a2

            val xt=w/2
            val yt=h/2-a1

            val group= Group()
//
            if (showCenters){
                val circleL = Circle(3.0, javafx.scene.paint.Color.RED)
                circleL.relocate(xl, yl)

                val circleR = Circle(3.0, javafx.scene.paint.Color.BLUE)
                circleR.relocate(xr, yr)

                val circleT = Circle(3.0, javafx.scene.paint.Color.YELLOW)
                circleT.relocate(xt, yt)

                val circleC = Circle(1.0, javafx.scene.paint.Color.CORAL)
                circleC.relocate(w/2, h/2)

                group.children.addAll(circleT,circleR,circleL)
            }

            for (i in 0..w.toInt()) {

                for (j in 0..h.toInt()) {
                    val rl= sqrt((i-xl)*(i-xl)+(j-yl)*(j-yl))
                    val rr= sqrt((i-xr)*(i-xr)+(j-yr)*(j-yr))
                    val rt= sqrt((i-xt)*(i-xt)+(j-yt)*(j-yt))

                    val rad= rl+rr+rt

                    if (abs(rad - radius) <= delta) {
                        group.children.add(Line(i.toDouble(), j.toDouble(), i.toDouble(), j.toDouble() ))
                    }
                }
            }
            return group
        }
    fun convertColorAwtToJfx(awtColor:Color):javafx.scene.paint.Color{
//        java.awt.Color awtColor = ... ;
        val r = awtColor.red
        val g = awtColor.green
        val b = awtColor.blue
        val a = awtColor.alpha
        val opacity = a / 255.0
       return  javafx.scene.paint.Color.rgb(r, g, b, opacity)
        /*Get each component from the awt Color object and use the javafx.scene.paint.Color.rgb(...) static method. Note that the awt Color has a getAlpha() method that returns the alpha as an int in the range 0-255, whereas javafx.scene.paint.Color.rgb(...) expects the alpha value as a double in the range 0.0-1.0:*/
    }

    }

}