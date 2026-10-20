import java.awt.Color;
import java.awt.Graphics;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 *
 * Note: Composite class created for COT4521 Project 1 representing a robot car.
 * Composes 5 Rectangle components (1 chassis body, 4 exterior flush wheels) and 1
 * Triangle component (front bumper flush with body vertices v2 and v3).
 * Coordinates translation, rotation, and multi-component blue fill rendering.
 *
 */
public class Car extends GeometricObject {
    private Rectangle body;
    private Rectangle wheelFL;
    private Rectangle wheelFR;
    private Rectangle wheelRL;
    private Rectangle wheelRR;
    private Triangle bumper;
    private Point center;

    public Car(Point p) {
        this.center = new Point(p.getX(), p.getY());

        double cx = p.getX();
        double cy = p.getY();
        double bodyW = 100.0;
        double bodyH = 50.0;
        double wheelW = 20.0;
        double wheelH = 10.0;
        double bumperH = 30.0;

        // 1. Main Body Rectangle
        this.body = new Rectangle(p, bodyW, bodyH);

        // 2. Front Bumper Triangle: Anchored to the front/right edge (v2 and v3)
        Point baseTop = new Point(body.getV2().getX(), body.getV2().getY());       // Top-right corner
        Point baseBottom = new Point(body.getV3().getX(), body.getV3().getY());    // Bottom-right corner
        Point apex = new Point(body.getV2().getX() + bumperH, cy);                 // Points forward (+X)
        this.bumper = new Triangle(baseTop, baseBottom, apex);

        // 3. Four External Wheels: Inner edge flush against chassis exterior
        double wheelYOffset = (bodyH / 2.0) + (wheelH / 2.0); // 25 + 5 = 30.0
        double wheelXInset = (bodyW / 2.0) - 20.0;            // 30.0

        this.wheelFL = new Rectangle(new Point(cx + wheelXInset, cy - wheelYOffset), wheelW, wheelH); // front-left
        this.wheelFR = new Rectangle(new Point(cx + wheelXInset, cy + wheelYOffset), wheelW, wheelH); // front-right
        this.wheelRL = new Rectangle(new Point(cx - wheelXInset, cy - wheelYOffset), wheelW, wheelH); // rear-left
        this.wheelRR = new Rectangle(new Point(cx - wheelXInset, cy + wheelYOffset), wheelW, wheelH); // rear-right

        // 4. Default Fill & Boundary Styling: Blue
        setInteriorColor(Color.BLUE);
        setBoundaryColor(Color.BLUE);
    }

    public Car() {
        this(new Point(0.0, 0.0));
    }

    public Rectangle getBody() { return body; }
    public Rectangle getWheelFL() { return wheelFL; }
    public Rectangle getWheelFR() { return wheelFR; }
    public Rectangle getWheelRL() { return wheelRL; }
    public Rectangle getWheelRR() { return wheelRR; }
    public Triangle getBumper() { return bumper; }
    public Point getCenter() { return center; }

    public void translate(Vector v) {
        center.translate(v);
        body.translate(v);
        bumper.translate(v);
        wheelFL.translate(v);
        wheelFR.translate(v);
        wheelRL.translate(v);
        wheelRR.translate(v);
    }

    public void rotate(Point p, double angle) {
        center.rotate(p, angle);
        body.rotate(p, angle);
        bumper.rotate(p, angle);
        wheelFL.rotate(p, angle);
        wheelFR.rotate(p, angle);
        wheelRL.rotate(p, angle);
        wheelRR.rotate(p, angle);
    }

    public void rotate(double angle) {
        rotate(this.center, angle);
    }

    @Override
    public void draw(Graphics g) {
        // Enforce blue fill and boundary across all constituent shapes
        Color interior = (getInteriorColor() != null) ? getInteriorColor() : Color.BLUE;
        Color boundary = (getBoundaryColor() != null) ? getBoundaryColor() : Color.BLUE;

        applyColor(body, interior, boundary);
        applyColor(bumper, interior, boundary);
        applyColor(wheelFL, interior, boundary);
        applyColor(wheelFR, interior, boundary);
        applyColor(wheelRL, interior, boundary);
        applyColor(wheelRR, interior, boundary);

        body.draw(g);
        wheelFL.draw(g);
        wheelFR.draw(g);
        wheelRL.draw(g);
        wheelRR.draw(g);
        bumper.draw(g);
    }

    private void applyColor(GeometricObject obj, Color interior, Color boundary) {
        obj.setInteriorColor(interior);
        obj.setBoundaryColor(boundary);
    }

    @Override
    public String toString() {
        return "CAR center: " + center + "\n"
                + "Body: " + body + "\n"
                + "Bumper: " + bumper;
    }
}