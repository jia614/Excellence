import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.AnimationTextualController;
import cs3500.animator.controller.IAnimationController;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.utils.AnimationBuilder;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.text.SVGView;
import cs3500.animator.view.text.TextualView;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the textual controller methods.
 */
public class TextualControllerTest {
  AnimationBuilder<IAnimationModel> builderMT;
  AnimationBuilder<IAnimationModel> builderShapes;
  AnimationBuilder<IAnimationModel> builderMotions;
  Appendable out;
  int tempo;
  IAnimationModel emptyModel;
  IAnimationModel modelJustShapes;
  IAnimationModel modelWithMotions;
  IAnimationView viewText;
  IAnimationView viewSVG;
  IAnimationController controllerMTText;
  IAnimationController controllerMTSVG;
  IAnimationController controllerShapeText;
  IAnimationController controllerShapeSVG;
  IAnimationController controllerMotionText;
  IAnimationController controllerMotionSVG;

  @Before
  public void initData() {
    builderMT = new Builder();
    builderShapes = new Builder();
    builderMotions = new Builder();
    out = new StringBuilder();
    tempo = 200;

    emptyModel = builderMT.setBounds(0, 0, 50, 50)
        .build();
    modelJustShapes = builderShapes.setBounds(0, 0, 50, 50)
        .declareShape("r1", "rectangle")
        .declareShape("e1", "ellipse")
        .build();
    modelWithMotions = builderMotions.setBounds(0, 0, 50, 50)
        .declareShape("r1", "rectangle")
        .declareShape("e1", "ellipse")
        .addMotion("r1", 1, 0, 0, 10, 10, 255, 0, 0,
            3, 3, 4, 5, 5, 0, 255, 0)
        .addMotion("r1", 3, 3, 4, 5, 5, 0, 255, 0,
            10, 4, -2, 1, 2, 0, 255, 0)
        .addMotion("e1", 0, 4, -2, 12, 50, 255, 0, 0,
            10, 0, 0, 30, 30, 0, 0, 255)
        .build();

    viewText = new TextualView(out, tempo);
    viewSVG = new SVGView(out, tempo);

    controllerMTText = new AnimationTextualController(emptyModel, viewText);
    controllerMTSVG = new AnimationTextualController(emptyModel, viewSVG);
    controllerShapeText = new AnimationTextualController(modelJustShapes, viewText);
    controllerShapeSVG = new AnimationTextualController(modelJustShapes, viewSVG);
    controllerMotionText = new AnimationTextualController(modelWithMotions, viewText);
    controllerMotionSVG = new AnimationTextualController(modelWithMotions, viewSVG);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullModel() {
    new AnimationTextualController(null, viewText);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullView() {
    new AnimationTextualController(modelJustShapes, null);
  }

  @Test
  public void testPlayMTText() {
    initData();
    controllerMTText.play();
    assertEquals("# describes the motions of a shape between two moments of animation:\n"
        + "# t == tick\n"
        + "# (x,y) == position\n"
        + "# (w,h) == dimensions\n"
        + "# (r,g,b) == color (with values between 0 and 255)\n"
        + "#                               start                  "
        + "                  end\n"
        + "#                ------------------------------------  "
        + "  -------------------------------------\n"
        + "#                t    x    y    w    h    r    g    b  "
        + "   t    x    y    w    h    r    g    b\n", out.toString());
  }

  @Test
  public void testPlayMTSvg() {
    initData();
    controllerMTSVG.play();
    assertEquals("<svg height=\"50\" width=\"50\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "</svg>", out.toString());
  }

  @Test
  public void testPlayShapeText() {
    initData();
    controllerShapeText.play();
    assertEquals("# describes the motions of a shape between two moments of animation:\n"
        + "# t == tick\n"
        + "# (x,y) == position\n"
        + "# (w,h) == dimensions\n"
        + "# (r,g,b) == color (with values between 0 and 255)\n"
        + "shape r1 RECTANGLE\n"
        + "shape e1 ELLIPSE\n"
        + "#                               start                  "
        + "                  end\n"
        + "#                ------------------------------------  "
        + "  -------------------------------------\n"
        + "#                t    x    y    w    h    r    g    b  "
        + "   t    x    y    w    h    r    g    b\n", out.toString());
  }

  @Test
  public void testPlayShapeSVG() {
    initData();
    controllerShapeSVG.play();
    assertEquals("<svg height=\"50\" width=\"50\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "<rect id=\"r1\" x=\"0.0\" y=\"0.0\" width=\"0\" height=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" >\n"
        + "</rect>\n"
        + "<ellipse id=\"e1\" cx=\"0.0\" cy=\"0.0\" rx=\"0\" ry=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" >\n"
        + "</ellipse>\n"
        + "</svg>", out.toString());
  }

  @Test
  public void testPlayMotionText() {
    initData();
    controllerMotionText.play();
    assertEquals("# describes the motions of a shape between two moments of animation:\n"
        + "# t == tick\n"
        + "# (x,y) == position\n"
        + "# (w,h) == dimensions\n"
        + "# (r,g,b) == color (with values between 0 and 255)\n"
        + "shape r1 RECTANGLE\n"
        + "shape e1 ELLIPSE\n"
        + "#                               start                  "
        + "                  end\n"
        + "#                ------------------------------------  "
        + "  -------------------------------------\n"
        + "#                t    x    y    w    h    r    g    b  "
        + "   t    x    y    w    h    r    g    b\n"
        + "motion r1        0.01 0.0  0.0  10   10   255  0    0  "
        + "   0.01 3.0  4.0  5    5    0    255  0    \n"
        + "motion r1        0.01 3.0  4.0  5    5    0    255  0  "
        + "   0.05 4.0  -2.0 1    2    0    255  0    \n"
        + "motion e1        0    4.0  -2.0 12   50   255  0    0  "
        + "   0.05 0.0  0.0  30   30   0    0    255  \n", out.toString());
  }

  @Test
  public void testPlayMotionSVG() {
    initData();
    controllerMotionSVG.play();
    assertEquals("<svg height=\"50\" width=\"50\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "<rect id=\"r1\" x=\"0.0\" y=\"0.0\" width=\"0\" height=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"5.0ms\" dur=\"10.0ms\" attributeName=\"x\" "
        + "from=\"0.0\" to=\"3.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"5.0ms\" dur=\"10.0ms\" attributeName=\"y\" "
        + "from=\"0.0\" to=\"4.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"5.0ms\" dur=\"10.0ms\" attributeName=\"width\" "
        + "from=\"10\" to=\"5\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"5.0ms\" dur=\"10.0ms\" "
        + "attributeName=\"height\" from=\"10\" to=\"5\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"5.0ms\" dur=\"10.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(0,255,0)\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"15.0ms\" dur=\"35.0ms\" "
        + "attributeName=\"x\" from=\"3.0\" to=\"4.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"15.0ms\" dur=\"35.0ms\" "
        + "attributeName=\"y\" from=\"4.0\" to=\"-2.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"15.0ms\" dur=\"35.0ms\" "
        + "attributeName=\"width\" from=\"5\" to=\"1\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"15.0ms\" dur=\"35.0ms\" "
        + "attributeName=\"height\" from=\"5\" to=\"2\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"15.0ms\" dur=\"35.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(0,255,0)\" />\n"
        + "</rect>\n"
        + "<ellipse id=\"e1\" cx=\"0.0\" cy=\"0.0\" rx=\"0\" ry=\"0\" fill=\"rgb(255,255,255)\" "
        + "visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"50.0ms\" "
        + "attributeName=\"cx\" from=\"4.0\" to=\"0.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"50.0ms\" "
        + "attributeName=\"cy\" from=\"-2.0\" to=\"0.0\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"50.0ms\" "
        + "attributeName=\"rx\" from=\"6\" to=\"15\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"50.0ms\" "
        + "attributeName=\"ry\" from=\"25\" to=\"15\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"50.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(0,0,255)\" />\n"
        + "</ellipse>\n"
        + "</svg>", out.toString());
  }
}
