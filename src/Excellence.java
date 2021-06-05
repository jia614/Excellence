import cs3500.animator.controller.AnimatorControllerCreator;
import cs3500.animator.controller.IAnimationController;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.utils.AnimationBuilder;
import cs3500.animator.utils.AnimationReader;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.view.IAnimationView;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Main class for running the animator and acts as an entry-point to the program. Parses the
 * command line arguments and throws a pop-up error message via JOptionPane if any error occurs
 * by catching any exceptions thrown.
 */
public class Excellence {

  /**
   * Runs the Excellence animator.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    String file = "";
    String slomo = "";
    boolean slomoExists = false;
    String outType = "";
    Appendable out = System.out;
    String viewType = "";
    int tempo = 1;

    try {
      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case "-in":
            file = args[i + 1];
            break;
          case "-out":
            outType = args[i + 1];
            out = getOut(args[i + 1]);
            break;
          case "-view":
            viewType = args[i + 1];
            break;
          case "-speed":
            tempo = Integer.parseInt(args[i + 1]);
            break;
          case "-slomo":
            slomo = args[i + 1];
            slomoExists = true;
            break;
          default:
            throw new IllegalArgumentException("Invalid args!");
        }
        i++;
      }
    }
    catch (IllegalStateException e) {
      popupError("Output file could not be created!");
    }
    catch (IndexOutOfBoundsException | IllegalArgumentException e) {
      popupError("Invalid command-line arguments/formatting!");
    }

    IReadOnlyAnimationModel model = buildModel(file);

    int slomoTempo = 0;
    List<Point> intervals = new ArrayList<>();

    // parsing the slomo file
    try {
      if (!slomo.equals("")) {
        Readable slomoFile = new FileReader(slomo);
        Scanner s = new Scanner(slomoFile);
        slomoTempo = Integer.parseInt(s.nextLine());
        while (s.hasNextLine()) {
          String line = s.nextLine();
          String[] arr = line.split(" ");
          if (arr[0].equals("slomo")) {
            intervals.add(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
          }
          else if (slomoExists){
            throw new IllegalArgumentException("Slomo file could not be parsed!");
          }
        }
      }
    }
    catch (FileNotFoundException e) {
      popupError("Slo-mo file not found!");
    }
    catch (IndexOutOfBoundsException | IllegalArgumentException e) {
      popupError("Slomo file could not be parsed!");
    }

    // TODO: 4/23/2021 MODIFIED 
    try {
      IAnimationView view = AnimatorViewCreator.create(viewType, out, tempo, slomoTempo, intervals);
      IAnimationController controller = AnimatorControllerCreator.create(viewType,
          tempo, model, view);
      controller.play();
      if (!outType.equals("")) {
        closeOut(outType, out);
      }
    }
    catch (IllegalArgumentException e) {
      popupError("View type not yet supported!");
    }
    catch (IllegalStateException e) {
      popupError("Output file could not be written to!");
    }
  }

  /**
   * Generates the correct appendable to be passed to the view depending the given outType.
   * @param outType name of the output file
   * @return an appendable depending on outType
   * @throws IllegalStateException if output file could not be created for some reason
   */
  private static Appendable getOut(String outType) throws IllegalStateException {
    if (outType.equals("System.out")) {
      return System.out;
    }
    try {
      return new FileWriter(outType);
    }
    catch (IOException e) {
      throw new IllegalStateException("Output file could not be created!");
    }
  }

  /**
   * Closes the write file if outType is not {@code System.Out}.
   * @param outType name of the output file
   * @param out the appendable to be closed
   * @throws IllegalStateException if output file fails to be closed for some reason
   */
  private static void closeOut(String outType, Appendable out) throws IllegalStateException {
    if (!outType.equals("System.out")) {
      FileWriter output = (FileWriter) out;
      try {
        output.flush();
        output.close();
      }
      catch (IOException e) {
        throw new IllegalStateException("Write file could not be closed!");
      }
    }
  }

  /**
   * Builds the correct model instance using the given file name to be parsed by the
   * {@code AnimationReader}.
   * @param file name of the file
   * @return the new model instance
   */
  private static IAnimationModel buildModel(String file) {
    AnimationBuilder<IAnimationModel> builder = new Builder();
    try {
      AnimationReader.parseFile(new FileReader(file), builder);
    }
    catch (FileNotFoundException e) {
      popupError("File not found!");
    }
    return builder.build();
  }

  /**
   * Displays a popup error message.
   * @param message message to render
   */
  private static void popupError(String message) {
    JOptionPane.showMessageDialog(new JFrame(), message,
        "ERROR", JOptionPane.ERROR_MESSAGE);
  }
}
