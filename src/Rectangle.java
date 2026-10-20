import java.awt.Graphics;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 *
 * Note: Originally developed as part of GeomLibrary by Professor Antonio Hernandez.
 * Refactored for COT4521 Project 1 to represent a rectangle using four explicit
 * vertices (v1, v2, v3, v4) rather than two axis-aligned opposing points (begin/end).
 * This architectural change enables continuous arbitrary-angle rotation and dense
 * vector line interpolation for solid interior filling without relying on AWT Polygons.
 *
 */
public class Rectangle extends GeometricObject {
    // Four vertices ordered sequentially: v1 (top-left), v2 (top-right), v3 (bottom-right), v4 (bottom-left)
    private Point v1, v2, v3, v4;

    /**
     * Constructs a rectangle given all four explicit vertices.
     */
    public Rectangle(Point v1, Point v2, Point v3, Point v4) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
    }

    /**
     * Constructs a rectangle centered at point p with the given width and height.
     */
    public Rectangle(Point p, double width, double height) {
        double halfW = width / 2.0;
        double halfH = height / 2.0;

        this.v1 = new Point(p.getX() - halfW, p.getY() + halfH); // Top-left
        this.v2 = new Point(p.getX() + halfW, p.getY() + halfH); // Top-right
        this.v3 = new Point(p.getX() + halfW, p.getY() - halfH); // Bottom-right
        this.v4 = new Point(p.getX() - halfW, p.getY() - halfH); // Bottom-left
    }

    /**
     * Constructs an axis-aligned rectangle from two opposing corners.
     */
    public Rectangle(Point topLeft, Point bottomRight) {
        double minX = Math.min(topLeft.getX(), bottomRight.getX());
        double maxX = Math.max(topLeft.getX(), bottomRight.getX());
        double minY = Math.min(topLeft.getY(), bottomRight.getY());
        double maxY = Math.max(topLeft.getY(), bottomRight.getY());

        this.v1 = new Point(minX, maxY);
        this.v2 = new Point(maxX, maxY);
        this.v3 = new Point(maxX, minY);
        this.v4 = new Point(minX, minY);
    }

    public Rectangle(Point p) {
        this(p, 100.0, 50.0);
    }

    public Rectangle() {
        this(new Point(0, 0), 100.0, 50.0);
    }

    // --- Getters & Setters ---

    public Point getV1() { return v1; }
    public Point getV2() { return v2; }
    public Point getV3() { return v3; }
    public Point getV4() { return v4; }

    public void setV1(Point v1) { this.v1 = v1; }
    public void setV2(Point v2) { this.v2 = v2; }
    public void setV3(Point v3) { this.v3 = v3; }
    public void setV4(Point v4) { this.v4 = v4; }

    // --- Geometric Transformations ---

    public void translate(Vector v) {
        v1.translate(v);
        v2.translate(v);
        v3.translate(v);
        v4.translate(v);
    }

    public void rotate(Point p, double angle) {
        v1.rotate(p, angle);
        v2.rotate(p, angle);
        v3.rotate(p, angle);
        v4.rotate(p, angle);
    }

    // --- Rendering ---

    @Override
    public void draw(Graphics g) {
        // 1. Fill interior via dense parallel line interpolation between opposing edges
        if (getInteriorColor() != null) {
            g.setColor(getInteriorColor());

            double edgeLen = Math.hypot(v4.getX() - v1.getX(), v4.getY() - v1.getY());
            int steps = Math.max(1, (int) Math.ceil(edgeLen * 2.5)); // Over-sample to guarantee zero gaps

            for (int i = 0; i <= steps; i++) {
                double t = (double) i / steps;

                // Interpolate along edge (v1 -> v4) and edge (v2 -> v3)
                int xa = (int) Math.round(v1.getX() + t * (v4.getX() - v1.getX()));
                int ya = (int) Math.round(v1.getY() + t * (v4.getY() - v1.getY()));
                int xb = (int) Math.round(v2.getX() + t * (v3.getX() - v2.getX()));
                int yb = (int) Math.round(v2.getY() + t * (v3.getY() - v2.getY()));

                g.drawLine(xa, ya, xb, yb);
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
            int x4 = (int) Math.round(v4.getX());
            int y4 = (int) Math.round(v4.getY());

            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x3, y3, x4, y4);
            g.drawLine(x4, y4, x1, y1);
        }
    }

    // --- Metrics & Bounds ---

    public double width() {
        return Math.hypot(v2.getX() - v1.getX(), v2.getY() - v1.getY());
    }

    public double height() {
        return Math.hypot(v4.getX() - v1.getX(), v4.getY() - v1.getY());
    }

    public double smallestX() {
        return Math.min(Math.min(v1.getX(), v2.getX()), Math.min(v3.getX(), v4.getX()));
    }

    public double greatestX() {
        return Math.max(Math.max(v1.getX(), v2.getX()), Math.max(v3.getX(), v4.getX()));
    }

    public double smallestY() {
        return Math.min(Math.min(v1.getY(), v2.getY()), Math.min(v3.getY(), v4.getY()));
    }

    public double greatestY() {
        return Math.max(Math.max(v1.getY(), v2.getY()), Math.max(v3.getY(), v4.getY()));
    }

    public Rectangle buildingbox(Rectangle r) {
        double minX = Math.min(this.smallestX(), r.smallestX());
        double minY = Math.min(this.smallestY(), r.smallestY());
        double maxX = Math.max(this.greatestX(), r.greatestX());
        double maxY = Math.max(this.greatestY(), r.greatestY());

        return new Rectangle(new Point(minX, maxY), new Point(maxX, minY));
    }

    public Rectangle sum(Rectangle r) {
        Vector a1 = new Vector(this.smallestX(), this.smallestY());
        Vector a2 = new Vector(r.smallestX(), r.smallestY());
        Vector b1 = new Vector(this.greatestX(), this.greatestY());
        Vector b2 = new Vector(r.greatestX(), r.greatestY());

        Vector sumMin = a1.add(a2);
        Vector sumMax = b1.add(b2);

        return new Rectangle(new Point(sumMin.getX(), sumMin.getY()), new Point(sumMax.getX(), sumMax.getY()));
    }

    public boolean isPointInRectangle(Point p) {
        double x = p.getX();
        double y = p.getY();
        return (x >= smallestX() && x <= greatestX() && y >= smallestY() && y <= greatestY());
    }

    @Override
    public String toString() {
        return "RECTANGLE " + super.toString() + "\n"
                + "v1: " + v1 + "\n"
                + "v2: " + v2 + "\n"
                + "v3: " + v3 + "\n"
                + "v4: " + v4;
    }
}