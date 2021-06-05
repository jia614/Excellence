package cs3500.animator.view.interactive.outline;

import cs3500.animator.model.IReadOnlyAnimationModel;
import cs3500.animator.model.shapes.IReadOnlyShape;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.interactive.InteractiveView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
// TODO: 4/22/2021 NEW
/**
 * An interactive representation with the ability to show the animation in outline mode. Renders
 * the animation by making all shapes outlined, and retains the abilities of the original
 * interactive view. Holds a boolean that toggles between outline and normal mode and a new
 * JButton to set outline mode on and off.
 */
public class OutlineInteractiveView extends InteractiveView implements IOutlineInteractiveView,
    ActionListener {
  private boolean outline;
  private final static String OUTLINE = "ENABLE/DISABLE OUTLINE";
  private JButton outlineBtn;

  /**
   * Constructs a {@code OutlineInteractiveView} object.
   * @param tempo tempo of animation
   */
  public OutlineInteractiveView(int tempo) {
    super(tempo);
    this.outline = false;
    initOutlineButton();
  }

  /**
   * Initializes the toggle button for enabling/disable outline view.
   */
  private void initOutlineButton() {
    this.outlineBtn = new JButton(OUTLINE);
    this.outlineBtn.setActionCommand(OUTLINE);
  }

  @Override
  public void render(IReadOnlyAnimationModel model) {
    super.render(model);
    super.buttonPanel.add(outlineBtn);
    super.add(buttonPanel, BorderLayout.SOUTH);
  }

  @Override
  public void play() {
    super.play();
  }

  @Override
  public void pause() {
    super.pause();
  }

  @Override
  public void restart() {
    super.restart();
  }

  @Override
  public void speedUp() {
    super.speedUp();
  }

  @Override
  public void slowDown() {
    super.slowDown();
  }

  @Override
  public void setLoop() {
    super.setLoop();
  }

  @Override
  public void setListener(ActionListener listener) throws IllegalArgumentException {
    super.setListener(listener);
    this.outlineBtn.addActionListener(listener);
  }

  @Override
  public void setOutline() {
    this.outline = !this.outline;
  }

  @Override
  public void drawShape(ShapeType type, double x, double y, int w, int h, Color color)
      throws IllegalArgumentException {
    if (w < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }
    if (h < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }
    if (!outline) {
      super.drawShape(type, x, y, w, h, color);
    }
    else {
      double mx = model.getScreen().getOrigin().getX();
      double my = model.getScreen().getOrigin().getY();
      switch (type) {
        case RECTANGLE:
          super.panel.drawRectOutline(x - mx, y - my, w, h, color);
          break;
        case ELLIPSE:
          super.panel.drawEllipseOutline(x - mx, y - my, w, h, color);
          break;
        case PLUS:
          super.panel.drawPlusOutline(x - mx, y - my, w, h, color);
          break;
        default:
          throw new IllegalArgumentException("Shape type not supported!");
      }
    }
  }

  @Override
  public void refresh() {
    super.refresh();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (!outline) {
      super.actionPerformed(e);
    }
    else {
      List<IReadOnlyShape> toRender = new ArrayList<>(model.getShapesAtTick(tick));
      for (IReadOnlyShape shape : toRender) {
        ShapeType type = shape.getType();
        this.drawShape(type,
            (int) shape.getPosn().getX(),
            (int) shape.getPosn().getY(),
            shape.getWidth(),
            shape.getHeight(),
            shape.getColor());
      }
      super.loopBack();
      refresh();
      tick++;
    }
  }
}
