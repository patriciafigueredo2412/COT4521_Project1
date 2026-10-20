import java.awt.*;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 *
 * Abstract class representing a geometric object. It is the apex class in the
 * hierarchy of geometric objects.
 *
 * Note: Provided as part of the core GeomLibrary class framework by Professor Antonio Hernandez.
 * Incorporated and adapted to support custom 2D transformations (translation, arbitrary pivot rotation)
 * required for COT4521 Project 1.
 *
 */
abstract public class GeometricObject
{
    private Color boundaryColor;    //boundary color
    private Color interiorColor;    //interior color

    /**
     * Default constructor; sets object to default colors.
     */
    public GeometricObject()
    {
        interiorColor = Color.white;
        boundaryColor = Color.black;
    }

    /**
     * Parameterized constructor; sets object to user colors.
     */
    public GeometricObject(Color iC, Color bC)
    {
        interiorColor = iC;
        boundaryColor = bC;
    }

    /**
     * Abstract drawing method, to be implemented in subclasses.
     *
     * @param g device context where object is drawn in
     */
    abstract public void draw(Graphics g);

    /**
     * Accessor method.
     */
    public Color getBoundaryColor()
    {
        return boundaryColor;
    }

    /**
     * Accessor method.
     */
    public Color getInteriorColor()
    {
        return interiorColor;
    }

    /**
     * Mutator method.
     */
    public void setBoundaryColor(Color boundaryColor)
    {
        this.boundaryColor = boundaryColor;
    }

    /**
     * Mutator method.
     */
    public void setInteriorColor(Color interiorColor)
    {
        this.interiorColor = interiorColor;
    }

    @Override
    public String toString()
    {
        String str;
        str = "BOUNDARY (" +
                boundaryColor.getRed() + ", " +
                boundaryColor.getGreen() + ", " +
                boundaryColor.getBlue() + ", " +
                boundaryColor.getAlpha() + ") " +
                "INTERIOR (" +
                interiorColor.getRed() + ", " +
                interiorColor.getGreen() + ", " +
                interiorColor.getBlue() + ", " +
                interiorColor.getAlpha() + ")";
        return str;
    }
}
