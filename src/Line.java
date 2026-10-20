import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 *
 * Note: Provided as part of the core GeomLibrary class framework by Professor Antonio Hernandez.
 * Incorporated and adapted to support custom 2D transformations (translation, arbitrary pivot rotation)
 * required for COT4521 Project 1.
 *
 * This class represents a line in the plane, given by the equation
 * ax + by + c = 0.
 */
public class Line extends GeometricObject
{

    private double a;   //x coefficient
    private double b;   //y coefficient
    private double c;   //constant term

    /**
     * Instantiates a line object as -x + y = 0 or y = x.
     */
    public Line()
    {
        a = -1;
        b = 1;
        c = 0;
    }

    /**
     * Instantiates a line object as ax + by + c = 0 or y = (-a/b) x + (-c/b).
     */
    public Line(double a, double b, double c) throws IllegalArgumentException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && b==0) throw new IllegalArgumentException();
        else
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    /**
     * Draws this line.
     *
     * @param g graphic context
     */
    @Override
    public void draw(Graphics g)
    {
        //finds the window size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double maxX = screenSize.width;
        double maxY = screenSize.height;

        //obtain two points on this line, (x1, y1) and (x2, y2)
        double x1, y1, x2, y2;

        if (!isVertical()) //line is not vertical
        {
            x1 = 0.0;
            y1 = getY(x1);
            x2 = maxX;
            y2 = getY(x2);
        }
        else //line is vertical
        {
            y1 = 0.0;
            x1 = getX(y1);
            y2 = maxY;
            x2 = getX(y2);
        }

        g.setColor(getBoundaryColor());
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    /**
     * Returns the coefficient of the X variable in the equation of this line
     * object.
     *
     * @return coefficient of the X variable in the equation of this line object
     */
    public double getA()
    {
        return a;
    }

    /**
     * Returns the coefficient of the Y variable in the equation of this line
     * object.
     *
     * @return coefficient of the Y variable in the equation of this line object
     */
    public double getB()
    {
        return b;
    }

    /**
     * Returns the independent term of the equation of this line object.
     *
     * @return independent term of the equation of this line object
     */
    public double getC()
    {
        return c;
    }

    /**
     * Given x, calculates the value of y.
     *
     * @param x x value
     * @return value of y
     * @throws IllegalStateException
     */
    public double getY(double x) throws IllegalStateException
    {
        if (b != 0)
            return (-a / b) * x + (-c / b);
        else
            throw new IllegalStateException("Vertical line cannot be used here.");
    }

    /**
     * Given y, calculates the value of x.
     *
     * @param y y value
     * @return value of x
     * @throws IllegalStateException
     */
    public double getX(double y) throws IllegalStateException
    {
        if (a != 0)
            return (-b / a) * y + (-c / a);
        else throw new IllegalStateException("Horizontal line cannot be used here.");
    }

    /**
     * Determines if this line is vertical.
     *
     * @return true if this line is vertical, false otherwise
     */
    boolean isVertical()
    {
        return b == 0;
    }

    /**
     * Sets the coefficient of the X variable in the equation of this line
     * object.
     *
     * @param a value of the coefficient
     *
     * @throws IllegalStateException if a=0 and b=0
     */
    public void setA(double a) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && this.b==0) throw new IllegalStateException();
        else
        {
            this.a = a;
        }
    }

    /**
     * Sets the coefficient of the Y variable in the equation of this line
     * object
     *
     * @param b value of the coefficient
     *
     * @throws IllegalStateException if a=0 and b=0
     */
    public void setB(double b) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (this.a==0 && b==0) throw new IllegalStateException();
        else
        {
            this.b = b;
        }
    }

    /**
     * Sets the independent term of the equation of this line object.
     *
     * @param c independent term
     */
    public void setC(double c)
    {
        this.c = c;
    }

    /**
     * Sets this line object as ax + by + c = 0 or y = (-a/b) x + (-c/b).
     */
    public void setLine(double a, double b, double c) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && b==0) throw new IllegalStateException();
        else
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    /**
     * Constructs a String description of this line.
     *
     * @return String containing a description of this line
     */
    @Override
    public String toString()
    {
        String str = "LINE " + a + " " + b + " " + c + " " + super.toString();

        return str;
    }

    /*
     *  Constructor with 2 points
     * @param point p and point q
     * @return line
     * Calculates a, b , c using the points
     *slope  y=mx+b  m= y2-y1/x2/x1   b = y1-mx1
     *Non vertical line: a=deltay/deltax (m) ,b=-1 , c=y-mx1
     *Vertical line : x1=x1
     *
     */

    public Line (Point p , Point q)  throws IllegalStateException {
        //x and y
        double x1=p.getX();
        double x2=q.getX();
        double y1=p.getY();
        double y2=q.getY();

        // Two identical points cannot define a line
        if (x1 == x2 && y1 == y2) {
            throw new IllegalArgumentException("Points p and q must be distinct.");
        }

        //slope
        double m = (y2-y1) / ( x2-x1 );
        //for non vertical , define how to get a , b , c
        double a , b , c;
        if(x1==x2)
        {
            //Line is vertical
            this.a=1;
            this.b=0;
            this.c= -x1 ;
        }else{
            //Line is not vertical
            this.a=m;
            this.b=-1.0;
            this.c=y1-(m*x1);

        }

    }

    /**
     *  Calculate distance from point p to this line
     *
     * @param p point
     * @return distance from p to this line
     */

    public double distanceFromPoint(Point p){
        double d = Math.abs((this.a * p.getX()) + (this.b * p.getY()) + c ) /Math.sqrt(Math.pow(a ,2 )+ Math.pow(b,2));
        return d;
    }

    /**
     * Returns -1 , o , 1 , depending on the position relative to the point
     * checks if the point is in the line , use the x to check for y on the line
     * Line is not vertical
     * py< yl return -1
     * py = yl return 0
     * py > yl return 1
     *
     * Line is vertical
     *px<xl return -1
     *px= xl  return 0
     *px>xl return 1
     */

    public int pointRelativePosition(Point p)
    {
        double px=p.getX();
        double py=p.getY();

        if(this.isVertical())
        {
            double linex= this.getX(py);
            if(px<linex){
                return -1;
            }else if(px == linex){
                return 0;
            } else {
                return 1;
            }
        } else {
            double liney=this.getY(px);
            if(py<liney){
                return -1;
            }else if(py == liney){
                return 0;
            } else {
                return 1;
            }
        }
    }

    /***
     * Translation of the line given a vector
     *
     * @param v given vector
     * l : ax + by + c =0
     * v(vx, vy)
     *v : ax+by + c -avx -bxy
     */

    public void translate(Vector v){
        double vx = v.getX();
        double vy = v.getY();

        this.c=this.c- (this.a*vx) - (this.b*vy) ;

        System.out.println("Equation of the line translated by vector: " + this.a + "x + " + this.b + "y + " + this.c + " = 0");
        this.setC(this.c);

    }

    /***
     * Find the normal vector (i.e vector that is perpedicular to this line)
     * v= (a , b)
     *
     */

    public Vector normalVector(){
        return new Vector(this.a , this.b);
    }

    /**
     * Intersection points
     *-determine if lines are parallel or non coincident
     * -their normal vectors must be paralel hence cross product = 0
     * -if not , intersection (x,y) follows formula
     */

    public Point intersection(Line line){
        //first check if parallel
        double crossNormVect = this.normalVector().crossProduct(line.normalVector());
        if( crossNormVect ==0 )
        {
            System.out.println("No intersection , lines are parallel ");
            return null;
        } else
        {
            double x = (this.b*line.c  -  this.c*line.b)/ crossNormVect;
            double y = (this.c*line.a - this.a*line.c) / crossNormVect;
            return new Point(x , y);
        }

    }
}

