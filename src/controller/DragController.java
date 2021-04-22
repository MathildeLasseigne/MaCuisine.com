package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * Encapsulate the drag responsibility of a node in a class.
 * <br/> Only move the node within its parent
 * <br/>Dragging movement can be canceled by pressing secondary button/
 * <br/>This is a javaFX class ! <a href="https://openjfx.io/">Download javaFX here</a>
 * <br/><a href="https://edencoding.com/drag-shapes-javafx/">Source</a>
 */
public class DragController {

    /**The target node to drag**/
    private final Node target;

    /**The orginal position of the node in the scene**/
    private double anchorX, anchorY;

    /**The distance between the position of the mouse and the position of the node when the node is catched**/
    private double mouseOffsetFromNodeZeroX, mouseOffsetFromNodeZeroY;

    /**Event Handler on mouse pressed**/
    private EventHandler<MouseEvent> setAnchor;
    /**Event Handler on mouse dragged**/
    private EventHandler<MouseEvent> updatePositionOnDrag;
    /**Event Handler on mouse realeased**/
    private EventHandler<MouseEvent> commitPositionOnRelease;

    /**State of the dragging cycle (currently dragging or not)**/
    private final int ACTIVE = 1;
    private final int INACTIVE = 0;
    private int cycleStatus = INACTIVE;

    private BooleanProperty isDraggable;

    /**
     * Encapsulate the drag responsibility of a node in a class.
     * <br/> - Only move the node within its parent
     * <br/>- Dragging movement can be canceled by pressing secondary button
     * <br/><br/><b>Warning : Do not work if target node has coordinates other than layout coordinates.
     * Use setLayoutX & Y to place the node</b>
     * <br/>If there is a gap when releasing the node, check if the (0,0) point in its coordinate system is on the upperleft corner.
     * If not, this is probably where the problem is.
     * (The Circle case is handled by the class)
     * @param target the node to drag
     */
    public DragController(Node target) {
        this(target, false);
    }

    /**
     * Encapsulate the drag responsibility of a node in a class.
     * <br/> - Only move the node within its parent
     * <br/>- Dragging movement can be canceled by pressing secondary button
     * <br/><br/><b>Warning : Do not work if target node has coordinates other than layout coordinates.
     * Use setLayoutX & Y to place the node</b>
     * <br/>If there is a gap when releasing the node, check if the (0,0) point in its coordinate system is on the upperleft corner.
     * If not, this is probably where the problem is.
     * (The Circle case is handled by the class)
     * @param target the node to drag
     * @param isDraggable the initial state of the draggable bool
     */
    public DragController(Node target, boolean isDraggable) {
        this.target = target;
        createHandlers();
        createDraggableProperty();

        this.isDraggable.set(isDraggable);
    }


    /**
     * Initialise the 3 eventHandlers.
     */
    private void createHandlers() {
        setAnchor = event -> {
            if (event.isPrimaryButtonDown()) {
                cycleStatus = ACTIVE;
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                if(target instanceof Circle){//Use upper left corner coordonate. Circle use center coordonates
                    Circle c = (Circle) target;
                    mouseOffsetFromNodeZeroX = event.getX()+c.getRadius();
                    mouseOffsetFromNodeZeroY = event.getY()+c.getRadius();
                } else {
                    mouseOffsetFromNodeZeroX = event.getX(); //Distance to the coordinate of the node (inside the node)
                    mouseOffsetFromNodeZeroY = event.getY();
                }

            }

            if (event.isSecondaryButtonDown()) {
                cycleStatus = INACTIVE;
                target.setTranslateX(0);
                target.setTranslateY(0);
            }
        };

        updatePositionOnDrag = event -> {
            if (cycleStatus != INACTIVE) {
                target.setTranslateX(event.getSceneX() - anchorX);
                target.setTranslateY(event.getSceneY() - anchorY);
            }
        };

        commitPositionOnRelease = event -> {
            if (cycleStatus != INACTIVE) {
                //commit changes to LayoutX and LayoutY

                target.relocate(event.getSceneX() - mouseOffsetFromNodeZeroX,event.getSceneY() - mouseOffsetFromNodeZeroY);

                //clear changes from TranslateX and TranslateY
                target.setTranslateX(0);
                target.setTranslateY(0);
                cycleStatus = INACTIVE;
            }
        };
    }

    /**
     * Bind and unbind the eventHandlers depending on the state of the draggable propertie.
     * <br/> Bind a listener to isDraggable to automaticaly bind and unbind in case of a change
     */
    private void createDraggableProperty() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                target.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            } else {
                target.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }

    /**
     * Check if the target node is set to be draggable
     * @return boolean
     */
    public boolean isIsDraggable() {
        return isDraggable.get();
    }

    /**
     * Return the boolean property checking if the target node can be dragged.
     * Modification add or remove event handlers on the target node.
     * <br/> To end draggable state, set return object to false
     * <br/> Bind example : <i>dragController.isDraggableProperty().bind(isDraggableBox.selectedProperty());</i>
     * @return BooleanProperty. Can be set or binded to other properties
     */
    public BooleanProperty getDraggableProperty() {
        return isDraggable;
    }
}