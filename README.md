# Easy Animator
## **Assignment 5**    

### **Shapes:**      
### `IReadOnlyShape`   
This interface represents the read-only version of a shape. Declares all the observer methods and 
ensures that the client receiving these shapes can't modify the data in the shapes illegally.

### `IShape`      
This interface extends the `IReadOnlyShape` and inherits the observers, while also declaring the
mutator methods.    

### `AShape`       
This abstract class represents a shape with a unique shape name, shape type, position, color,
width, and height. It implements `IShape` and inherits both observer and mutator methods. 
We chose an abstract class because we might need to add other kinds of shapes in the future, and 
it reduces code duplication among the different kinds of shapes. It holds certain invariants: 
that the width and height must always be non-negative, and these invariants are maintained in both
the constructor and its mutator methods. It implements all mutators and all observers except for
`copy()`.

### `ShapeType`       
This enum holds the possible kinds of shapes.

### `Rectangle`  
This class extends `AShape` and represents a rectangle. It overrides `equals()` and `hashCode()`, 
and implements `copy()`, which produces a deep copy of itself.

### `Ellipse`         
This class extends `AShape` and represents an ellipse. It overrides `equals()` and `hashCode()`,
and implements `copy()`, which produces a deep copy of itself.

### **Motions:**  
### `IEvent`        
This interface represents a motion with initial time, ending time, and initial and final parameters.
It declares getter methods for all parameters.

### `Event`   
This class represents a motion an `IShape` can take, and inherits from `IEvent`. We chose to have it 
hold the starting time and starting parameters (position, color, width, height) as well as the 
corresponding ending parameters, as it was represented in the algorithmic description provided in
the assignment. Our other option was to create distinct motions (move, scale, color-change, etc) and
combine them via an abstract class implementing the `IEvent` interface. However, our model would be 
forced to store events of each kind of command separately (in order to keep track of gap/overlap 
invariants), which would make it difficult to add introduce new kinds of motions without modifying 
the model directly. With our implementation, if we wanted to add a new kind of motion, we could 
simply extend the `Event` class (or have a new interface extend the old one with an implementing 
class) that holds an additional field. Because `Event`s are immutable (its fields are private 
and final) and that there are no mutating methods, there was no need to create a read-only interface
for it. It implements the getters declared in `IEvent` and returns copies of its fields.

### **Model:**             
### `IReadOnlyAnimationModel`     
This interface represents the read-only version of the animation model. Declares all the observer 
methods and ensures that the client receiving this model can't modify the data in the model 
implementation in any way.

### `IAnimationModel`   
This interface extends the `IReadOnlyAnimationModel` interface and inherits the observers, while 
also declaring the mutator methods. 

### `Screen`    
This class represents the screen of the animation, and holds a width, height, and an origin point. 
We still don't have any information regarding where the origin is (top-left, bottom-left, etc), so
for now this class is still in consideration. Its fields are private and final, so it is immutable.
It contains getters that return copies of its fields.

### `AnimationModel`      
This class is the implementation of `IAnimationModel` and represents the model of the 
model-view-controller pattern. It implements all observer and mutator methods. It holds the list of
all `IShape`s in the model as well as a HashMap that maps each unique shape ID to its list of 
`IEvent`s. It currently has functionality to retrieve a list of all `IReadOnlyShape`s in the model, 
the list of `IReadOnlyShape`s at a given tick/frame, the `IEvent`s of a given `IShape`, the `IShape`
with the given ID, a copy of the `Screen`. It also has functionality to add an `IShape`, add an
`IEvent`, insert a frame at the given tick/frame, remove an `IShape`, remove an `IEvent`, and 
remove the `IEvent` at the given tick. It has several invariants:  
1. Events are increasingly ordered according to interval (which are distinct).
2. Intervals of event do not have overlaps or gaps.
3. The initial states of any event should agree with the final states of the previous event.

These invariants are first enforced at construction. The builder, which constructs the model, 
already enforces the invariants in the setters. So, when the model is constructed, its list of 
`IShape` and Hashmap follow the invariant. Each mutator method in the model similarly enforce
the maintenance of the invariants. See javadoc of each method in `AnimationModel` for more info. 

### `Builder`  
We created a nested public static Builder class in `AnimationModel` that builds our model up using
setters and a `build()` method. We wanted to make our constructor private and it seems much more 
intuitive that a complex animation will be built up bit-by-bit by feeding the Builder individual 
shape parameters, event parameters, and specific dimensions (width, height, x-coord of origin point,
y-coord of origin point). There are methods that allow the client to set the dimensions of the 
animation, add a shape by parameters, construct via list of shapes, add an event by parameters, and
build the model. The `addShape(params)` and `addEvent(params)` are nearly identical to the 
`addShape` and `addEvent` methods in the `AnimationModel` class, so we made private static helpers 
in `AnimationModel`that would be open to access by methods in the Builder class that abstracted out
the common functionality. Thus, the builder enforces the invariants of `AnimationModel`, and only 
allows valid shapes and events to be passed to the private constructor for `AnimationModel`.


### **View:**

### `IAnimationView`    
This interface represents the view of the model-view-controller pattern. For this assignment, we 
only implemented the textual view. So far, it contains a `render()` method that displays the 
animation.

### `TextualView`     
This class holds an IAnimationModel field and an Appendable field (both private and final). It 
holds two constructors: one where you pass in both an IAnimationModel and an Appendable, and one 
where you only pass in an IAnimationModel and the Appendable field is initialized to a 
StringBuilder. It implements the `render()` method by calling a private helper that iterates through
the shapes of the model (retrieved by the `List<IReadOnlyShape> getShapes()` method)


## **Changes from Assignment 5**     

We had initially created a builder to construct our model in Assignment 5, but had to remove/alter
the setter methods we had declared in our original builder. We implemented the AnimationBuilder
interface that we were given, and modified the signatures of both the `declareShape()` and 
`addMotion()` methods. We also updated our tests. We also modified `TextualView` slightly per
Assignment 6 instructions (more on that later).

 
## **Assignment 6**   

### **Running the program:**

### `Excellence`    
This is the entry point to our program. Takes in command-line arguments of the form:   

> `-in "animation-file-name" -view "view-type" -out "output-file-name" -speed "ticks-per-second"`

### `AnimatorViewCreator`    
This is a factory class that generates the correct view depending on the view type. Used in 
the `Excellence` class to build the view to pass into the controller.

### `AnimatorControllerCreator`   
This is a factory class that generates the correct controller instance depending on the provided
view type and uses the model and view built in `Excellence`.

### **View**

### `IAnimationView`  
This interface's single method was slightly modified to take in an `IReadOnlyAnimationModel`, which
is fed in by the controller. All concrete class implementations inherit this method, which allows
for interaction with the model via the controller. Rather than have implementing classes hold
the model as one of its fields (forcing the model to be coupled to the view at construction), we
felt that it would be a better design choice to allow the view access to the model via the
controller.

### `TextualView`   
This class was modified slightly from the previous assignment. It no longer holds the model
as a field. It still holds an Appendable, but also takes in a tempo (ticks/second). This allows
the animation rendering to be displayed in terms of seconds rather than ticks. `render()`,
called by the controller, renders the entire animation's textual output instantly, rather than 
frame-by-frame.

### `SVGView`   
This class renders the animation as an SVG file using XML formatting. It inherits `IAnimationView` 
and similarly holds an Appendable and tempo. `render()`, called by the controller, renders the 
entire animation's textual output instantly, rather than frame-by-frame.
 
### `IVisualAnimationView`  
This interface extends `IAnimationView` and inherits the `render()` method. However, because it
requires additional functionality to display Java Swing graphics, we created this interface
that declares the methods `drawShape()` and `refresh()`.  

### `VisualView`   
This class is responsible for providing the view that shows the animation playing inside a window. 
It inherits the `render()`, `drawShape()`, and `refresh()` methods from `IAnimationView` and 
`IVisualAnimationView`. It holds a `JPanel` that holds the animation rendering, a `JScrollPane`, 
as well as its origin's x and y-coordinates (instantiated in `render()` using the provided model). 
Uses Java Swing.

### `DrawingPanel`   
This class represents the component inside the view window that displays the actual animation. 
Extends the `JPanel` class and overrides its paintComponent() method. It holds the list of drawing 
panel shapes to be rendered, and contains a `drawRect()` and `drawEllipse()` method that enables 
rendering of the model via the controller.

### `IDrawingPanelShape`   
This interface represents a shape to be rendered onto the drawing panel. Has a method `draw()` that
allows the shape object to render itself onto the panel using the `Graphics` input.

### `DrawingPanelRectangle`  
This class represents a rectangle to be rendered onto the drawing panel. Implements 
`IDrawingPanelShape`, extends `Rectangle` and holds a `Color` as a field. 

### `DrawingPanelEllipse`     
This class represents an ellipse to be rendered onto the drawing panel. Implements 
`IDrawingPanelShape`, extends `Ellipse2D.Double` and holds a `Color` as a field.


### **Controller**  
### `IAnimationController`   
This interface represents the controller of the model-view-controller pattern. It contains 
a single method: `play()`, that communicates between the model and view by feeding the model
into the view's `render()` method. Depending on the type of controller and type of view, the 
"feeding" operates differently (i.e. all at once or one frame at a time).

### `AnimationTextualController`  
This class represents the controller that deals with text-based views (i.e. `TextualView` 
or `SVGView`). It holds an `IReadOnlyAnimationModel` and an `IAnimationView` as its fields. In its 
`play()` method, it simply calls the view's `render()` method and passes in the model for immediate 
rendering.

### `AnimationSwingController`   
This class represents the controller that deals with swing/graphics-based views
(i.e. `VisualView`). It inherits `IAnimationController` as well as `ActionListener`. 
It holds a tempo, the current tick, an `IReadOnlyAnimationModel`, and an `IAnimationView` as its 
fields. In its `play()` method, it renders the view and starts a `Timer`. It overrides the 
`actionPerformed()` method that allows it to iterate through and render all shapes at every tick, 
synchronized with its timer. 


## **Assignment 7**  

### **`View`**   
### `IInteractiveView`  
This interface extends `IVisualAnimationView` and inherits the `drawShape()` and `refresh()` 
methods. It requires additional methods that allow for starting, pausing, restarting, speeding up,
and slowing down the animation. It also allows for enabling/disabling looping.  

### `InteractiveView`   
This class is responsible for providing an interactive view. It holds a `JPanel` that holds the 
animation rendering, a `JScrollPane` and the list of buttons used for interaction (PLAY, PAUSE,
RESTART, ENABLE/DISABLE LOOPING). Uses Java Swing.

### **`Controller`**  
### `AnimationInteractiveController`      
This class represents the controller that deals with the interactive view. It inherits 
`IAnimationController` as well as `ActionListener`. It holds an `IReadOnlyAnimationModel`
and an `IAnimationView` as its fields. In its `play()` method, it renders the view. It overrides
actionPerformed() and responds to button presses in the view by calling the correct view method. 










 


