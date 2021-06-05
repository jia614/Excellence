import static org.junit.Assert.assertEquals;

import cs3500.animator.model.shapes.Ellipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.shapes.ShapeType;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import org.junit.Test;

/**
 * Abstract testing class to test shape methods.
 */
public abstract class ShapeTest {
  Point2D p613 = new Double(6, 13);
  Color testColor = new Color(80, 100, 120);
  IShape testShape = baseShape("testShape", p613, Color.BLUE, 8, 7);

  // Shape constructor tests
  @Test (expected = IllegalArgumentException.class)
  // tests negative width argument when constructing a shape
  public void testNegWidth() {
    baseShape("bad", p613, testColor, -1, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  // tests negative height argument when constructing a shape
  public void testNegHeight() {
    baseShape("bad", p613, testColor, 10, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  // tests null name argument when constructing a shape
  public void testNullName() {
    baseShape(null, p613, testColor, 10, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  // tests null posn argument when constructing a shape
  public void testNullPosn() {
    baseShape("bad", null, testColor, 10, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  // tests null color argument when constructing a shape
  public void testNullColor() {
    baseShape("bad", p613, null, 10, 10);
  }

  // Shape getter tests
  @Test
  public void testGetName() {
    assertEquals("testShape", testShape.getName());
  }

  @Test
  public abstract void testGetType();

  @Test
  public void testGetPosn() {
    assertEquals(p613, testShape.getPosn());
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.BLUE, testShape.getColor());
  }

  @Test
  public void testGetWidth() {
    assertEquals(8, testShape.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(7, testShape.getHeight());
  }

  // Shape modifier tests
  @Test
  public void testChangePosn() {
    testShape.changePosn(10, -4);
    assertEquals(new Double(10, -4), testShape.getPosn());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testChangeColorInvalidRed() {
    testShape.changeColor(-81, 0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testChangeColorInvalidGreen() {
    testShape.changeColor(0, -50, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testChangeColorInvalidBlue() {
    testShape.changeColor(0, 0, 1000);
  }

  @Test
  public void testChangeColorValid() {
    testShape.changeColor(80, 155, 20);
    assertEquals(new Color(80, 155, 20), testShape.getColor());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testChangeWidthNeg() {
    testShape.changeWidth(-100);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testChangeWidthValid() {
    testShape.changeWidth(-7);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testChangeHeightNeg() {
    testShape.changeHeight(-100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeHeightValid() {
    testShape.changeWidth(-1);
  }

  protected abstract IShape baseShape(String name, Point2D posn,
      Color color, int width, int height);

  /**
   * Concrete class for testing Ellipse implementation of Shape.
   */
  public static final class EllipseTest extends ShapeTest {
    protected IShape baseShape(String name, Point2D posn, Color color, int width, int height) {
      return new Ellipse(name, posn, color, width, height);
    }

    @Test
    public void testGetType() {
      assertEquals(ShapeType.ELLIPSE, testShape.getType());
    }

    @Test
    public void testEquals() {
      assertEquals(testShape.copy(), testShape);
    }

    @Test
    public void testHashCode() {
      assertEquals(-1091039860, testShape.hashCode());
    }

    @Test
    public void testCopy() {
      assertEquals(baseShape("testShape", p613, Color.BLUE, 8, 7),
          testShape.copy());
    }
  }

  /**
   * Concrete class for testing Rectangle implementation of Shape.
   */
  public static final class RectangleTest extends ShapeTest {
    protected IShape baseShape(String name, Point2D posn, Color color, int width, int height) {
      return new Rectangle(name, posn, color, width, height);
    }

    @Test
    public void testGetType() {
      assertEquals(ShapeType.RECTANGLE, testShape.getType());
    }

    @Test
    public void testEquals() {
      assertEquals(testShape.copy(), testShape);
    }

    @Test
    public void testHashCode() {
      assertEquals(-1091039860, testShape.hashCode());
    }

    @Test
    public void testCopy() {
      assertEquals(baseShape("testShape", p613, Color.BLUE, 8, 7),
          testShape.copy());
    }
  }
}
