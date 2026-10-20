import java.awt.Graphics;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 *
 * Note: Custom class created specifically for COT4521 Project 1 to represent
 * the car's front bumper component within the GeomLibrary framework. Implements
 * rotation, translation, and dense line interpolation for filled rendering.
 *
 */
public class Triangle extends GeometricObject {
    private Point v1, v2, v3;

    public Triangle(Point v1, Point v2, Point v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Triangle(Point p, double baseWidth, double height) {
        double halfBase = baseWidth / 2.0;
        this.v1 = new Point(p.getX() - halfBase, p.getY());
        this.v2 = new Point(p.getX() + halfBase, p.getY());
        this.v3 = new Point(p.getX(), p.getY() + height);
    }

    public Triangle() {
        this(new Point(0, 0), new Point(50, 0), new Point(25, 40));
    }

    public Point getV1() { return v1; }
    public Point getV2() { return v2; }
    public Point getV3() { return v3; }

    public void setV1(Point v1) { this.v1 = v1; }
    public void setV2(Point v2) { this.v2 = v2; }
    public void setV3(Point v3) { this.v3 = v3; }

    public void translate(Vector v) {
        v1.translate(v);
        v2.translate(v);
        v3.translate(v);
    }

    public void rotate(Point p, double angle) {
        v1.rotate(p, angle);
        v2.rotate(p, angle);
        v3.rotate(p, angle);
    }

    @Override
    public void draw(Graphics g) {
        // 1. Fill interior: Sweep lines radiating from apex (v3) to the base edge (v1 -> v2)
        if (getInteriorColor() != null) {
            g.setColor(getInteriorColor());

            double baseLen = Math.hypot(v2.getX() - v1.getX(), v2.getY() - v1.getY());
            int steps = Math.max(1, (int) Math.ceil(baseLen * 2.5));

            int x3 = (int) Math.round(v3.getX());
            int y3 = (int) Math.round(v3.getY());

            for (int i = 0; i <= steps; i++) {
                double t = (double) i / steps;
                int xb = (int) Math.round(v1.getX() + t * (v2.getX() - v1.getX()));
                int yb = (int) Math.round(v1.getY() + t * (v2.getY() - v1.getY()));
                g.drawLine(x3, y3, xb, yb);
            }
        }

        // 2. Draw border perimeter
        if (getBoundaryColor() != null) {
            g.setColor(getBoundaryColor());

            int x1 = (int) Math.round(v1.getX());
            int y1 = (int) Math.round(v1.getY());
            int x2 = (int) Math.round(v2.getX());
            int y2 = (int) Math.round(v2.getY());
            int x3 = (int) Math.round(v3.getX());
            int y3 = (int) Math.round(v3.getY());

            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x3, y3, x1, y1);
        }
    }

    public double smallestX() { return Math.min(v1.getX(), Math.min(v2.getX(), v3.getX())); }
    public double greatestX() { return Math.max(v1.getX(), Math.max(v2.getX(), v3.getX())); }
    public double smallestY() { return Math.min(v1.getY(), Math.min(v2.getY(), v3.getY())); }
    public double greatestY() { return Math.max(v1.getY(), Math.max(v2.getY(), v3.getY())); }

    @Override
    public String toString() {
        return "TRIANGLE " + super.toString() + "\n"
                + "v1: " + v1 + "\n"
                + "v2: " + v2 + "\n"
                + "v3: " + v3;
    }
}