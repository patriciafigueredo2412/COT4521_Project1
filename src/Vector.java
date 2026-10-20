import java.awt.*;
/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 *
 * Note: Provided as part of the core GeomLibrary class framework by Professor Antonio Hernandez.
 * Incorporated and adapted to support custom 2D transformations (translation, arbitrary pivot rotation)
 * required for COT4521 Project 1.
 *
 */
public class Vector extends GeometricObject {
    //variables of the vector are x and y
    private double x;
    private double y;

    //Initiate null vector
    public Vector()
    {
        y=x=0;
    }

    @Override
    public void draw(Graphics g) {

    }

    //Paramethized constructor
    public Vector(double x , double y)
    {
        this.x=x ;
        this.y =y;
    }


    //Constructor whos paramethers are two points

    public Vector(Point p1 , Point p2)
    {
        this.x=p2.getX()-p1.getX();
        this.y=p2.getY()-p1.getY();

    }


    //Vector SUM

    public Vector add(Vector v )
    {
        return new Vector(this.x+ v.x , this.y + v.y);
    }

    //Get direction (angle)

    public double direction()
    {
        double angle = Math.toDegrees(Math.atan2(y , x));
        if (angle<0)
        {angle=angle+360;  }  //account for negative angle
        return angle;
    }

    //Return components

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    //Get the magnitude

    public double magnitude ()
    {
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }

    //Oposite direction

    public Vector neg()
    {
        return new Vector(-x , -y);
    }

    //setters

    public void setX(double x)
    {
        this.x=x;
    }

    public void setY(double y)
    {
        this.y=y;
    }

    //substract
    public Vector substract(Vector v)
    {
        return new Vector(this.x-v.x , this.y - v.y);
    }
    // String description
    @Override
    public String toString()
    {
        return "VECTOR (" + x + ", " + y  + " ) ";
    }

    //Returns Dot Product u*v=uxvx + uyvy
    public double dotProduct(Vector v ) {
        return this.x * v.x + this.y * v.y ;
    }

    //Using the dot product to define the angle classification
    public void dotProduct_angle(Vector v) {
        double EPSILON = 1e-9;  //to prevent mathematical errors
        double dot_prod=this.dotProduct(v);
        if(Math.abs(dot_prod) < EPSILON )
        {
            System.out.println("Right angle");
        }else if (dot_prod>0)
        {
            System.out.println("Acute angle");
        }
        else {
            System.out.println("Obtuse angle");
        }
    }

    //Cross Product
    public double crossProduct(Vector v)
    {
        return this.x * v.y - this.y * v.x;
    }

    //Return relative position of vectors depending on crossProduct
    public void rel_position(Vector v) {
        double crossP = this.crossProduct(v);
        double EPSILON = 1e-9;

        if (Math.abs(crossP) < EPSILON) {
            System.out.println("V is collinear with the vector");
        } else if (crossP > 0) {
            System.out.println("V is counter-clockwise relative to the vector");
        } else {
            System.out.println("V is clockwise relative to the vector");
        }
    }




}


















