import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Rectangle;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Main class for generating a bubble-sort animation. Can adjust number of bars in visualizer by
 * changing the constant value, and writes the canvas, shape, and motion declarations to a file.
 */
public class BubbleSort {

  /**
   * Main program that generates the BubbleSort text-file.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    int n = 7;
    Random rand = new Random();
    int canvasWidth = n * 40;
    int canvasHeight = canvasWidth / 2;

    try {
      FileWriter out = new FileWriter("bubble-sort.txt");
      String canvas = "canvas 0 " + canvasHeight + " " + canvasWidth + " " + canvasHeight + "\n";
      out.append(canvas);

      IShape[] shapes = new IShape[n];
      for (int i = 0; i < n; i++) {
        String name = "r" + i;
        String shape = "shape " + name + " rectangle\n";
        out.append(shape);

        int height = rand.nextInt(canvasHeight) + 1;
        shapes[i] = new Rectangle(name,
            new Double(40 * i, canvasHeight + (canvasHeight - height)),
            Color.getHSBColor(rand.nextFloat(), (rand.nextInt(2000) + 1000) / 10000f,
                0.9f),
            40,
            height);
      }

      int[] times = new int[n];
      StringBuilder result = new StringBuilder();

      for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {

          int v1 = shapes[j].getHeight();
          int v2 = shapes[j + 1].getHeight();

          String same1Init = generateMotion(true, shapes[j], times[j]);
          String same2Init = generateMotion(true, shapes[j + 1], times[j + 1]);

          times[j] += 20;
          times[j + 1] += 20;

          int timeEnd = 0;
          if (times[j] < times[j + 1]) {
            timeEnd = times[j + 1];
            times[j] = times[j + 1];
          }
          else {
            timeEnd = times[j];
            times[j + 1] = times[j];
          }

          String same1Final = generateMotion(false, shapes[j], timeEnd);
          String same2Final = generateMotion(false, shapes[j + 1], timeEnd);

          result.append(same1Init);
          result.append(same1Final);
          result.append(same2Init);
          result.append(same2Final);

          if (v1 > v2) {

            String padding = "";
            if (times[j] < times[j + 1]) {
              padding = generateMotion(true, shapes[j], times[j])
                  + generateMotion(false, shapes[j], times[j + 1]);
              times[j] = times[j + 1];
              result.append(padding);
            }
            else if (times[j] > times[j + 1]) {
              padding = generateMotion(true, shapes[j + 1], times[j + 1])
                  + generateMotion(false, shapes[j + 1], times[j]);
              times[j + 1] = times[j];
              result.append(padding);
            }

            String firstInit = generateMotion(true, shapes[j], times[j]);
            String secondInit = generateMotion(true, shapes[j + 1], times[j + 1]);

            times[j] += 20;
            times[j + 1] += 20;

            if (times[j] < times[j + 1]) {
              timeEnd = times[j + 1];
              times[j] = times[j + 1];
            }
            else {
              timeEnd = times[j];
              times[j + 1] = times[j];
            }

            IShape tempS1 = shapes[j].copy();
            IShape tempS2 = shapes[j + 1].copy();
            IShape newS1 = new Rectangle(tempS1.getName(),
                new Point2D.Double(tempS2.getPosn().getX(),
                    tempS1.getPosn().getY()),
                tempS1.getColor(),
                tempS1.getWidth(),
                tempS1.getHeight());
            IShape newS2 = new Rectangle(tempS2.getName(),
                new Point2D.Double(tempS1.getPosn().getX(),
                    tempS2.getPosn().getY()),
                tempS2.getColor(),
                tempS2.getWidth(),
                tempS2.getHeight());

            shapes[j] = newS2;
            shapes[j + 1] = newS1;

            String firstFinal = generateMotion(false, shapes[j + 1], timeEnd);
            String secondFinal = generateMotion(false, shapes[j], timeEnd);

            result.append(firstInit);
            result.append(firstFinal);
            result.append(secondInit);
            result.append(secondFinal);
          }
          int maxTick = 0;
          for (int time : times) {
            maxTick = Math.max(maxTick, time);
          }
          for (int k = 0; k < shapes.length; k++) {
            String lastMoveInit = generateMotion(true, shapes[k], times[k]);
            String lastMoveFinal = generateMotion(false, shapes[k], maxTick);
            times[k] = maxTick;
            result.append(lastMoveInit);
            result.append(lastMoveFinal);
          }
        }
      }
      out.append(result.toString());
      out.flush();
      out.close();
    }
    catch (IOException e) {
      throw new IllegalStateException("Output file could not be created!");
    }
  }

  /**
   * Generates the motion string with the given arguments.
   * @param start is this the start of the motion?
   * @param s shape to render
   * @param timer tick of current motion
   * @return the motion string representation
   */
  private static String generateMotion(boolean start, IShape s, int timer) {
    String motion = "";
    if (start) {
      motion = "motion " + s.getName() + " ";
    }
    motion += timer + " "
        + (int) s.getPosn().getX() + " "
        + (int) s.getPosn().getY() + " "
        + s.getWidth() + " "
        + s.getHeight() + " "
        + s.getColor().getRed() + " "
        + s.getColor().getGreen() + " "
        + s.getColor().getBlue();
    if (start) {
      return motion + "  ";
    }
    else {
      return motion + "\n";
    }
  }
}
