package cs3500.animator.view.text;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.events.IEvent;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.IAnimationView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// TODO: 4/19/2021 MODIFIED
/**
 * Represents the SVG-view of an animation. Formats the animation as a textual description using
 * XML formatting. Holds an appendable (where it outputs to), a tempo (fps), the model's top-left
 * origin x and y-coordinates (instantiated in the render() method), and a static lookup
 * table that maps shape type to its corresponding XML string representation (to avoid excessive
 * switch cases).
 */
public class SVGView implements IAnimationView {
  private final Appendable out;
  private final int tempo;
  private double mx;
  private double my;
  private final Map<ShapeType, String> lookup = new HashMap<>();

  /**
   * Constructs a {@code AbstractTextualView} object and initializes the lookup table.
   * @param out appendable to append to
   * @param tempo ticks per second
   * @throws IllegalArgumentException if out is null or if tempo is negative.
   */
  public SVGView(Appendable out, int tempo) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null!");
    }
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo cannot be negative!");
    }
    this.out = out;
    this.tempo = tempo;
    initializeLookup();
  }

  /**
   * Initializes the lookup table with the XML shape strings.
   */
  private void initializeLookup() {
    this.lookup.put(ShapeType.RECTANGLE, "rect");
    this.lookup.put(ShapeType.ELLIPSE, "ellipse");
    this.lookup.put(ShapeType.PLUS, "polygon");
  }

  /**
   * Overrides render() in IAnimationView.
   * @throws IllegalArgumentException if given model is null
   * @throws IllegalStateException if rendering fails for some reason
   */
  @Override
  public void render(IReadOnlyAnimationModel model) throws IllegalArgumentException,
      IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    mx = model.getScreen().getOrigin().getX();
    my = model.getScreen().getOrigin().getY();
    int width = model.getScreen().getWidth();
    int height = model.getScreen().getHeight();
    String header = "<svg height=\"" + height
        + "\" width=\"" + width
        + "\" version=\"1.1\""
        + " xmlns=\"http://www.w3.org/2000/svg\">\n";
    try {
      out.append(header);
      List<IReadOnlyShape> shapes = model.getShapes();
      for (IReadOnlyShape shape : shapes) {
        out.append(shapeToXML(shape));
        List<IEvent> events = new ArrayList<>(model.getEventsById(shape.getName()));
        for (IEvent event : events) {
          out.append(eventToXML(shape.getType(), event));
        }
        String close = "\n</" + lookup.get(shape.getType()) + ">";
        out.append(close);
      }
      out.append("\n</svg>");
    }
    catch (IOException e) {
      throw new IllegalStateException("Rendering failed!");
    }
  }

  /**
   * Returns the XML representation of the given shape.
   * @param shape to render in XML
   * @return XML string representation of given shape
   * @throws IllegalArgumentException if given shape is null or if shape type not yet supported
   */
  private String shapeToXML(IReadOnlyShape shape) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null!");
    }
    StringBuilder xml = new StringBuilder("\n<" + lookup.get(shape.getType()) + " ");
    String params = "";
    switch (shape.getType()) {
      case RECTANGLE:
        xml.append(rShapeToXML(shape.getName(),
            shape.getPosn().getX(),
            shape.getPosn().getY(),
            shape.getWidth(),
            shape.getHeight()));
        break;
      case ELLIPSE:
        xml.append(eShapeToXML(shape.getName(),
            shape.getPosn().getX(),
            shape.getPosn().getY(),
            shape.getWidth() / 2,
            shape.getHeight() / 2));
        break;
      case PLUS:
        xml.append(pShapeToXML(shape.getName(),
            shape.getPosn().getX(),
            shape.getPosn().getY(),
            shape.getWidth(),
            shape.getHeight()));
        break;
      default:
        throw new IllegalArgumentException("Unsupported shape type!");
    }
    xml.append(params);
    String close = "\" fill=\"rgb("
        + shape.getColor().getRed() + ","
        + shape.getColor().getGreen() + ","
        + shape.getColor().getBlue()
        + ")\" visibility=\"visible\" >";
    xml.append(close);
    return xml.toString();
  }

  /**
   * Renders a rectangle declaration in XML using the given parameters.
   * @param id shape's unique identifier
   * @param x x-position (center)
   * @param y y-position (center)
   * @param width rectangle width
   * @param height rectangle height
   * @return the rectangle declaration as an XML string
   */
  private String rShapeToXML(String id, double x, double y, int width, int height) {
    return "id=\"" + id
        + "\" x=\"" + x
        + "\" y=\"" + y
        + "\" width=\"" + width
        + "\" height=\"" + height;
  }

  /**
   * Renders an ellipse declaration in XML using the given parameters.
   * @param id shape's unique identifier
   * @param cx x-position (center)
   * @param cy y-position (center)
   * @param rx ellipse x-radius
   * @param ry ellipse y-radius
   * @return the ellipse declaration as an XML string
   */
  private String eShapeToXML(String id, double cx, double cy, int rx, int ry) {
    return "id=\"" + id
        + "\" cx=\"" + cx
        + "\" cy=\"" + cy
        + "\" rx=\"" + rx
        + "\" ry=\"" + ry;
  }

  /**
   * Renders a polygon declaration that represents a plus shape in XML using the given
   * parameters.
   * @param id shape's unique identifier
   * @param x x-position (center)
   * @param y y-position (center)
   * @param width plus width
   * @param height plus height
   * @return the plus declaration as an XML string
   */
  private String pShapeToXML(String id, double x, double y, int width, int height) {
    double x1 = x;
    double x2 = x + (double) width / 4;
    double x3 = x + (double) (width / 4 + width / 2);
    double x4 = x + (double) width;

    double y1 = y;
    double y2 = y + (double) height / 4;
    double y3 = y + (double) (height / 4 + height / 2);
    double y4 = y + height;
    return "id =\"" + id
        + "\" points=\""
        + x1 + "," + y2 + " "
        + x2 + "," + y2 + " "
        + x2 + "," + y1 + " "
        + x3 + "," + y1 + " "
        + x3 + "," + y2 + " "
        + x4 + "," + y2 + " "
        + x4 + "," + y3 + " "
        + x3 + "," + y3 + " "
        + x3 + "," + y4 + " "
        + x2 + "," + y4 + " "
        + x2 + "," + y3 + " "
        + x1 + "," + y3;
  }

  /**
   * Returns the XML representation of the given event.
   * @param event to render in XML
   * @return XML string representation of given event
   * @throws IllegalArgumentException if given event is null or if shape type not yet supported
   */
  private String eventToXML(ShapeType type, IEvent event) throws IllegalArgumentException {
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null!");
    }
    StringBuilder xml = new StringBuilder();
    switch (type) {
      case RECTANGLE:
        xml.append(rEventToXML(event));
        break;
      case ELLIPSE:
        xml.append(eEventToXML(event));
        break;
      case PLUS:
        xml.append(pEventToXML(event));
        break;
      default:
        throw new IllegalArgumentException("Shape type not yet supported!");
    }
    return xml.toString();
  }

  /**
   * Converts an event (rectangle) to XML string.
   * @param event event to render
   * @return given event rendered in XML
   * @throws IllegalArgumentException if given event is null
   */
  private String rEventToXML(IEvent event) throws IllegalArgumentException {
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null!");
    }
    StringBuilder xml = new StringBuilder();
    double begin = tickToMilliseconds(event.getStartTime());
    double dur = tickToMilliseconds(event.getEndTime() - event.getStartTime());

    String xStart = Double.toString(event.getStartPosn().getX() - mx);
    String xEnd = Double.toString(event.getEndPosn().getX() - mx);
    String yStart = Double.toString(event.getStartPosn().getY() - my);
    String yEnd = Double.toString(event.getEndPosn().getY() - my);
    String wStart = Integer.toString(event.getStartWidth());
    String wEnd = Integer.toString(event.getEndWidth());
    String hStart = Integer.toString(event.getStartHeight());
    String hEnd = Integer.toString(event.getEndHeight());
    String cStart = "rgb(" + event.getStartColor().getRed() + ","
        + event.getStartColor().getGreen() + ","
        + event.getStartColor().getBlue() + ")";
    String cEnd = "rgb(" + event.getEndColor().getRed() + ","
        + event.getEndColor().getGreen() + ","
        + event.getEndColor().getBlue() + ")";

    xml.append(animateXML("x", begin, dur, xStart, xEnd));
    xml.append(animateXML("y", begin, dur, yStart, yEnd));
    xml.append(animateXML("width", begin, dur, wStart, wEnd));
    xml.append(animateXML("height", begin, dur, hStart, hEnd));
    xml.append(animateXML("fill", begin, dur, cStart, cEnd));

    return xml.toString();
  }

  /**
   * Converts an event (ellipse) to XML string.
   * @param event event to render
   * @return given event rendered in XML
   * @throws IllegalArgumentException if given event is null
   */
  private String eEventToXML(IEvent event) throws IllegalArgumentException {
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null!");
    }
    StringBuilder xml = new StringBuilder();
    double begin = tickToMilliseconds(event.getStartTime());
    double dur = tickToMilliseconds(event.getEndTime() - event.getStartTime());

    String xStart = Double.toString(event.getStartPosn().getX() - mx);
    String xEnd = Double.toString(event.getEndPosn().getX() - mx);
    String yStart = Double.toString(event.getStartPosn().getY() - my);
    String yEnd = Double.toString(event.getEndPosn().getY() - my);
    String wStart = Integer.toString(event.getStartWidth() / 2);
    String wEnd = Integer.toString(event.getEndWidth() / 2);
    String hStart = Integer.toString(event.getStartHeight() / 2);
    String hEnd = Integer.toString(event.getEndHeight() / 2);
    String cStart = "rgb(" + event.getStartColor().getRed() + ","
        + event.getStartColor().getGreen() + ","
        + event.getStartColor().getBlue() + ")";
    String cEnd = "rgb(" + event.getEndColor().getRed() + ","
        + event.getEndColor().getGreen() + ","
        + event.getEndColor().getBlue() + ")";

    xml.append(animateXML("cx", begin, dur, xStart, xEnd));
    xml.append(animateXML("cy", begin, dur, yStart, yEnd));
    xml.append(animateXML("rx", begin, dur, wStart, wEnd));
    xml.append(animateXML("ry", begin, dur, hStart, hEnd));
    xml.append(animateXML("fill", begin, dur, cStart, cEnd));

    return xml.toString();
  }

  /**
   * Converts an event (plus) to XML string.
   * @param event event to render
   * @return given event rendered in XML
   * @throws IllegalArgumentException if given event is null
   */
  private String pEventToXML(IEvent event) throws IllegalArgumentException {
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null!");
    }
    StringBuilder xml = new StringBuilder();
    double begin = tickToMilliseconds(event.getStartTime());
    double dur = tickToMilliseconds(event.getEndTime() - event.getStartTime());

    double xStart = event.getStartPosn().getX() - mx;
    double xEnd = event.getEndPosn().getX() - mx;
    double yStart = event.getStartPosn().getY() - my;
    double yEnd = event.getEndPosn().getY() - my;
    int wStart = event.getStartWidth();
    int wEnd = event.getEndWidth();
    int hStart = event.getStartHeight();
    int hEnd = event.getEndHeight();

    int x1 = (int) xStart;
    int x2 = (int) xStart + wStart / 4;
    int x3 = (int) xStart + wStart / 4 + wStart / 2;
    int x4 = (int) xStart + wStart;

    int y1 = (int) yStart;
    int y2 = (int) yStart + hStart / 4;
    int y3 = (int) yStart + hStart / 4 + hStart / 2;
    int y4 = (int) yStart + hStart;

    String pStart = getPoints(x1, x2, x3, x4, y1, y2, y3, y4);

    int x5 = (int) xEnd;
    int x6 = (int) xEnd + wEnd / 4;
    int x7 = (int) xEnd + wEnd / 4 + wEnd / 2;
    int x8 = (int) xEnd + wEnd;

    int y5 = (int) yEnd;
    int y6 = (int) yEnd + hEnd / 4;
    int y7 = (int) yEnd + hEnd / 4 + hEnd / 2;
    int y8 = (int) yEnd + hEnd;

    String pEnd = getPoints(x5, x6, x7, x8, y5, y6, y7, y8);

    String cStart = "rgb(" + event.getStartColor().getRed() + ","
        + event.getStartColor().getGreen() + ","
        + event.getStartColor().getBlue() + ")";
    String cEnd = "rgb(" + event.getEndColor().getRed() + ","
        + event.getEndColor().getGreen() + ","
        + event.getEndColor().getBlue() + ")";

    xml.append(animateXML("points", begin, dur, pStart, pEnd));
    xml.append(animateXML("fill", begin, dur, cStart, cEnd));
    return xml.toString();
  }

  /**
   * Returns the given points as an attribute value.
   * @param x1 first x-pos
   * @param x2 second x-pos
   * @param x3 third x-pos
   * @param x4 fourth x-pos
   * @param y1 first y-pos
   * @param y2 second y-pos
   * @param y3 third y-pos
   * @param y4 fourth y-pos
   * @return string rendering of list of points
   */
  private String getPoints(double x1, double x2, double x3, double x4,
      double y1, double y2, double y3, double y4) {
    return x1 + "," + y2 + " "
        + x2 + "," + y2 + " "
        + x2 + "," + y1 + " "
        + x3 + "," + y1 + " "
        + x3 + "," + y2 + " "
        + x4 + "," + y2 + " "
        + x4 + "," + y3 + " "
        + x3 + "," + y3 + " "
        + x3 + "," + y4 + " "
        + x2 + "," + y4 + " "
        + x2 + "," + y3 + " "
        + x1 + "," + y3;
  }

  /**
   * Converts the given number of ticks to milliseconds.
   * @param tick tick to be converted
   * @return ticks in milliseconds
   */
  private double tickToMilliseconds(int tick) {
    return 1000 * ((double) tick / tempo);
  }

  /**
   * Renders an animate string in XML.
   * @param attribute attribute type
   * @param begin starting time in ms
   * @param dur duration of event in ms
   * @param start beginning value
   * @param end ending value
   * @return the rendered XML animate string
   */
  private String animateXML(String attribute, double begin, double dur, String start, String end) {
    return "\n\t<animate attributeType=\"xml\""
        + " begin=\"" + begin + "ms\""
        + " dur=\"" + dur + "ms\""
        + " attributeName=\"" + attribute + "\""
        + " from=\"" + start + "\" to=\"" + end + "\" />";
  }
}
