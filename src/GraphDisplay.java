import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 * Canvas panel responsible for rendering geometric objects such as the Car robot.
 */

public class GraphDisplay extends JPanel {
    private Car car;

    public GraphDisplay(Car car) {
        this.car = car;
        setBackground(Color.WHITE);
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (car != null) {
            car.draw(g);
        }
    }
}