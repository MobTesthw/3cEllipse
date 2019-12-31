package shape2D

import javafx.scene.Group
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.transform.Transform
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
        fun drawEllipse(
                w:Double,
                h:Double,
                radDistance:Double,
                radMultiplier:Double=1.0,
                threshold:Double=0.5,
                fill:Boolean=false,
                border:Double=3.0,
                showCenters:Boolean=true,
                centersRadius:Double=3.0,
                angle:Double=0.0
                ):Group{

//            val ellipseColor=javafx.scene.paint.Color.BLUE

            val a=h/(0.00000001+radDistance) // a - triangle height a=a1+a2 top and bottom parts

            val b=2.0*a/ sqrt(3.0)
//            val a2 = b/4 //lowest triangle height part
//            val a1=a-a2
            val a1= sqrt(3.0)*b/3
            val a2=a-a1

            val deltaHeight= abs(a1-a2)/2   //amendment to locate trangle to the center of screen

            var radius=3* sqrt(b*b+a*a) /4
            radius*=radMultiplier

            val xl=w/2-b/2
            val yl=h/2+a2 + deltaHeight

            val xr=w/2+b/2
            val yr=h/2+a2 + deltaHeight

            val xt=w/2
            val yt=h/2-a1 + deltaHeight

            val group= Group()

            group.children.add(triangleMedians(w/2,h/2 + deltaHeight))
//
            if (showCenters){
                val circleL = Circle(xl,yl,centersRadius/*, javafx.scene.paint.Color.RED*/)
                val circleR = Circle(xr,yr,centersRadius/*, javafx.scene.paint.Color.BLUE*/)
                val circleT = Circle(xt,yt,centersRadius/*, javafx.scene.paint.Color.YELLOW*/)
//                val circleC = Circle(w/2,h/2,centersRadius, javafx.scene.paint.Color.CORAL)

                group.children.addAll(circleT,circleR,circleL/*,circleC*/)
            }


            for (i in 0..w.toInt()) {

                for (j in 0..h.toInt()) {
                    val rl= sqrt((i-xl)*(i-xl)+(j-yl)*(j-yl))
                    val rr= sqrt((i-xr)*(i-xr)+(j-yr)*(j-yr))
                    val rt= sqrt((i-xt)*(i-xt)+(j-yt)*(j-yt))

                    val rad= rl+rr+rt



                    if(fill) {
                        if (abs(rad - radius) <= threshold)                     // Draw shape with filling
                            group.children.add(Line(i.toDouble(), j.toDouble(), i.toDouble(), j.toDouble() ))
                    }
                    else if(abs(abs(rad - radius)-threshold )<=border){     //Draw borders only
                        group.children.add(Line(i.toDouble(), j.toDouble(), i.toDouble(), j.toDouble() ))
                    }
                }
            }
            if(angle != 0.0) group.transforms.add(Transform.rotate(angle,w/2,h/2+deltaHeight))
            return group
        }
    private fun triangleMedians(x: Double, y: Double): Group{
        val radiusGroup=Group()
        val r = x.coerceAtMost(y)

        val r1=Line(x,y,x+r,y)
        r1.transforms.add(Transform.rotate(90.0,x,y))

        val r2=Line(x,y,x+r,y)
        r2.transforms.add(Transform.rotate(210.0,x,y))

        val r3=Line(x,y,x+r,y)
        r3.transforms.add(Transform.rotate(330.0,x,y))
        radiusGroup.children.addAll(r1,r2,r3)

        return radiusGroup
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