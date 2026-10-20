import java.awt.*;
/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 *
 * Note: Provided as part of the core GeomLibrary class framework by Professor Antonio Hernandez.
 * Incorporated and adapted to support custom 2D transformations (translation, arbitrary pivot rotation)
 * required for COT4521 Project 1.
 *
 * This class represents a point given by its x and y coordinates.
 */
public class Point extends GeometricObject
{
    private double x;
    private double y;

    /**
     * Instantiates a Point object with coordinates (0, 0) and parent's
     * default options.
     */
    public Point()
    {
        x = y = 0;
    }

    /**
     * Instantiates a Point object with coordinates (x, y) and parent's
     * default options.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of this point.
     *
     * @return x-coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * Returns the y coordinate of this point.
     *
     * @return y-coordinate
     */
    public double getY()
    {
        return y;
    }

    /**
     * Sets the x-coordinate of this point.
     *
     * @param x x-coordinate
     */
    public void setX(double x)
    {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this point.
     *
     * @param y y-coordinate
     */
    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * Constructs a String description of this point.
     *
     * @return String containing a description of this point
     */
    @Override
    public String toString()
    {
        return "POINT (" + x + ", " + y + ") " + super.toString();
    }

    public void draw(Graphics g)
    {
        int radius = 5;

        g.setColor(getInteriorColor());
        g.fillOval((int)x - radius, (int)y - radius, 2 * radius, 2 * radius);
        g.setColor(getBoundaryColor());
        g.drawOval((int)x - radius, (int)y - radius, 2 * radius, 2 * radius);
    }


    public double distance(Point P )   //distance formula needs P2
    {
        //Needs to follow the formula
        //P1 is this current point P2 is the point to messure with
        double p1x = this.getX();
        double p1y = this.getY();
        double p2x = P.getX();
        double p2y = P.getY();
        double distance = Math.sqrt( Math.pow(p1x - p2x, 2 )+ Math.pow(p1y - p2y ,2 ));
        return distance;
    }

    //Mid point Method
    public Point midpoint (Point p)
    {
        //M = ( x1+x2/2 ; y1+y2/2)
        Point mid =new Point();
        mid.setX((this.getX()+p.getX())/2);
        mid.setY((this.getY()+p.getY())/2);
        return mid;
    }


    //Slope Method
    public double slope(Point p ) {
        // m=y2-y1/x2-x1
        double dy=p.getY()-this.getY();
        double dx=p.getX()-this.getX();
        //Exceptions if points are the same and if dx==0
        if(dx==0 && dy==0) {
            throw new IllegalArgumentException("Points are identical; slope is indeterminate.");}
        if (dx==0 ) {
            throw new ArithmeticException("Vertical line detected: slope is undefined.");}
        return dy/dx;
    }

    //converts point to vector
    public void translate(Vector v)
    {
        x+=v.getX();
        y+=v.getY();

    }

    /**
     * Rotates this point about the origin (0, 0) by a given angle in degrees.
     */
    public void rotate(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double x1 = x * cos - y * sin;
        double y1 = x * sin + y * cos;

        this.x = x1;
        this.y = y1;
    }

    /**
     * Rotates this point about a given pivot point p by an angle in degrees.
     * Translates p to the origin, rotates, then translates back.
     */
    public void rotate(Point p, double angle) {
        // 1. Shift point so pivot p acts as the origin
        double dx = this.x - p.getX();
        double dy = this.y - p.getY();

        // 2. Perform rotation
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double xRot = dx * cos - dy * sin;
        double yRot = dx * sin + dy * cos;

        // 3. Translate back to world coordinates
        this.x = p.getX() + xRot;
        this.y = p.getY() + yRot;
    }

    /**
     * computes the point c on given line segment that is closes to this point
     *
     * @param ls line segment
     * @return closes point on ls
     */

    public Point closestPoint(LineSegment ls)
    {
        //define teh vectors v and w

        Vector v = new Vector(ls.getEnd().getX()-ls.getBegin().getX(), ls.getEnd().getY()-ls.getBegin().getY() );
        Vector w = new Vector(this.getX() - ls.getBegin().getX(), this.getY() - ls.getBegin().getY());

        //find m , find the projection factor t
        double t = ( w.dotProduct(v))/ Math.abs(v.dotProduct(v));
        //clamp t to the segment boundries
        t = Math.max(0.0, Math.min(1.0, t));

        return new Point(ls.getBegin().getX() + t * v.getX(), ls.getBegin().getY() + t * v.getY());
    }

    /***
     * Computes the point in the given set of line segments that is closest to this point
     *
     * @param lsArray array of line segments
     * @return closes points on a set of line segments
     *
     */

    public Point ClosestPoint(LineSegment [] lsArray)
    {
        // From a given array of Line segments , check all the closest points , choose the closest one
        double min_distance = this.distance(this.closestPoint(lsArray[0])); //set min distance
        Point closest = this.closestPoint(lsArray[0]);                      // set initial point
        for(int i=0; i< lsArray.length; i++)
        {
            // Loop over each elemnt on this array , find closes one
            if(min_distance>this.distance(this.closestPoint(lsArray[i]))){
                min_distance=this.distance(this.closestPoint(lsArray[i]));
                closest= this.closestPoint(lsArray[i]);
            }
        }
        return closest;

    }
}

