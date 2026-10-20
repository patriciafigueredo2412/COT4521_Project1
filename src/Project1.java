import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Color;

/**
 * Author: Patricia Figueredo Ventura
 * Panther ID: 6613415
 * Course: COT 4521 - Computational Geometry
 * Assignment: Project 1
 *
 * Note: Main driver class for the robot car simulation adhering to the
 * GeomLibrary architecture established in class by Professor Antonio Hernandez.
 * Coordinates user input via JOptionPane, parses trajectory files containing
 * starting coordinates and alternating rotation angles and translation distances,
 * initializes the FrameDisplay and GraphDisplay environments, and executes
 * the sequential animation loop across all geometric transformations.
 *
 * Academic Integrity & Tooling Disclosure:
 * Generative AI tooling (Gemini) was consulted strictly as an interactive thought
 * partner for debugging, syntax verification, and mathematical edge cases during
 * development. All class designs, architectural restructuring, and final source
 * code implementations were independently assembled, tested, and verified by the author.
 */


public class Project1 {

    public Project1() {
        // Step 1: Prompt user for input file name using JOptionPane
        String fileName = JOptionPane.showInputDialog(
                null,
                "Enter input file name (e.g., input.txt):",
                "Project 1 Input",
                JOptionPane.QUESTION_MESSAGE
        );

        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No file specified. Exiting.");
            System.exit(0);
        }

        File file = new File(fileName.trim());
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "File not found: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextInt()) {
                JOptionPane.showMessageDialog(null, "Invalid file format.");
                return;
            }

            // Line count header
            int numLines = scanner.nextInt();

            // Initial robot location
            int initX = scanner.nextInt();
            int initY = scanner.nextInt();

            // Step 2: Initialize Car, GraphDisplay, and FrameDisplay
            Point initialPos = new Point(initX, initY);
            Car car = new Car(initialPos);
            car.setBoundaryColor(Color.BLUE);


            GraphDisplay graphDisplay = new GraphDisplay(car);
            FrameDisplay frameDisplay = new FrameDisplay(graphDisplay);

            // Brief pause before motion starts
            Thread.sleep(800);

            // Track current heading vector (starts facing positive X)
            double currentHeadingDeg = 0.0;

            // Step 3: Animation Loop over input commands
            while (scanner.hasNextInt()) {
                int angleDeg = scanner.nextInt();
                if (!scanner.hasNextInt()) break;
                int distance = scanner.nextInt();

                // 1. Rotate in increments
                int rotateSteps = Math.max(1, Math.abs(angleDeg));
                double dAngle = (double) angleDeg / rotateSteps;

                for (int s = 0; s < rotateSteps; s++) {
                    car.rotate(car.getCenter(), dAngle);
                    graphDisplay.repaint();
                    Thread.sleep(15);
                }
                currentHeadingDeg += angleDeg;

                // 2. Translate forward along current heading in increments
                double rad = Math.toRadians(currentHeadingDeg);
                double unitX = Math.cos(rad);
                double unitY = Math.sin(rad);

                int transSteps = Math.max(1, Math.abs(distance));
                double stepDist = (double) distance / transSteps;
                Vector stepVector = new Vector(unitX * stepDist, unitY * stepDist);

                for (int s = 0; s < transSteps; s++) {
                    car.translate(stepVector);
                    graphDisplay.repaint();
                    Thread.sleep(15);
                }

                // Pause between motion segments
                Thread.sleep(300);
            }

            JOptionPane.showMessageDialog(frameDisplay, "Animation completed successfully.");

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error opening file: " + e.getMessage());
        } catch (java.util.NoSuchElementException e) {   // also covers InputMismatchException
            JOptionPane.showMessageDialog(null, "Invalid file format: all values must be integers.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        new Project1();
    }
}