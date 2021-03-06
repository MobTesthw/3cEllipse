package shape2D

import javafx.scene.Group
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import java.awt.Color
import kotlin.math.abs
import kotlin.math.sqrt

class ShapesGroup {
    companion object{
        //Cross at the center of the screen
        fun screenCross(w:Double,h:Double, small:Boolean=false, color: Color = Color.GREEN):Group{

            val group=Group()
            val mutlpier=20.0
            val hStart= if (small) 0.0 else h/2 - h/mutlpier
            val hEndD = if (small) h   else h/2 + h/mutlpier

            val wStart= if (small) 0.0 else w/2 - w/mutlpier
            val wEndD = if (small) w   else w/2 + w/mutlpier

            val lineHorizontal = Line(wStart,h/2,wEndD,h/2)
            lineHorizontal.stroke= convertColorAwtToJfx(color)

            val lineVertical = Line(w/2,hStart,w/2,hEndD)
            lineVertical.stroke= convertColorAwtToJfx(color)

            group.children.addAll( lineHorizontal , lineVertical   )

            return group
        }
        fun drawEllipse(w:Double,h:Double,radDistance:Double, radMultiplier:Double=1.0,threshold:Double=0.5, showCenters:Boolean=true,centersRadius:Double=3.0):Group{
//            val threshold=0.5 //threshold to define point to draw

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
                val circleL = Circle(xl,yl,centersRadius/*, javafx.scene.paint.Color.RED*/)
                val circleR = Circle(xr,yr,centersRadius/*, javafx.scene.paint.Color.BLUE*/)
                val circleT = Circle(xt,yt,centersRadius/*, javafx.scene.paint.Color.YELLOW*/)
//                val circleC = Circle(w/2,h/2,centersRadius, javafx.scene.paint.Color.CORAL)

                group.children.addAll(circleT,circleR,circleL,circleC)
            }

            for (i in 0..w.toInt()) {

                for (j in 0..h.toInt()) {
                    val rl= sqrt((i-xl)*(i-xl)+(j-yl)*(j-yl))
                    val rr= sqrt((i-xr)*(i-xr)+(j-yr)*(j-yr))
                    val rt= sqrt((i-xt)*(i-xt)+(j-yt)*(j-yt))

                    val rad= rl+rr+rt

                    if (abs(rad - radius) <= threshold) {
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