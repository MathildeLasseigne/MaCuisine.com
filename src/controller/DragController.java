package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * Encapsulate the drag responsibility of a node in a class.
 * <br/> Only move the node within its parent
 * <br/>Dragging movement can be canceled by pressing secondary button
 * <br/>This is a javaFX class !
 * @see <a href="https://openjfx.io/">Download javaFX here</a>
 * @see <a href="https://edencoding.com/drag-shapes-javafx/">Original class</a>
 * @author mathilde
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

    /**Define if the target node can be dragged**/
    private BooleanProperty isDraggable;

    /**Define the boundaries where the node can be dragged to and released into.**/
    private Bounds dragBoundary, releaseBoundary = null;

    /**Check conditions for grab, drag and release action on the target node**/
    private SimpleChecker grabChecker, dragChecker, releaseChecker = null;

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

    /*---------------------Events-----------------------*/

    /**
     * Initialise the 3 eventHandlers.
     */
    private void createHandlers() {
        setAnchor = event -> {
            if(this.grabChecker != null){
                if(this.grabChecker.check()){
                    grabHandler(event);
                }
            } else {
                grabHandler(event);
            }
        };

        updatePositionOnDrag = event -> {
            if(this.dragChecker != null){
                if(this.dragChecker.check()){
                    dragHandler(event);
                }
            } else {
                dragHandler(event);
            }
        };

        commitPositionOnRelease = event -> {
            if(this.releaseChecker != null){
                if(this.releaseChecker.check()){
                    releaseHandler(event);
                }
            } else {
                releaseHandler(event);
            }
        };
    }

    /**
     * Handler when grabbing the target node
     * @param event mouseEvent
     */
    private void grabHandler(MouseEvent event){
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
    }

    /**
     * Handler when dragging the target node
     * @param event mouseEvent
     */
    private void dragHandler(MouseEvent event){
        if (cycleStatus != INACTIVE) {
            if(this.dragBoundary != null){
                double oldTranslateX = target.getTranslateX();
                double oldTranslateY = target.getTranslateY();
                target.setTranslateX(event.getSceneX() - anchorX);
                target.setTranslateY(event.getSceneY() - anchorY);
                Bounds bounds = target.getBoundsInParent();
                if(! this.dragBoundary.contains(bounds)){
                    target.setTranslateX(oldTranslateX);
                    target.setTranslateY(oldTranslateY);
                }
            } else {
                target.setTranslateX(event.getSceneX() - anchorX);
                target.setTranslateY(event.getSceneY() - anchorY);
            }
        }
    }

    /**
     * Handler when releasing the target node
     * @param event mouseEvent
     */
    private void releaseHandler(MouseEvent event){
        if (cycleStatus != INACTIVE) {
            //commit changes to LayoutX and LayoutY

            if(this.releaseBoundary != null){
                Bounds bounds = target.getBoundsInParent();
                if(this.releaseBoundary.contains(bounds)){
                    Parent parent = this.target.getParent();
                    Point2D p = parent.sceneToLocal(event.getSceneX(), event.getSceneY());
                    Point2D oldPos = getCurrentPos(target);

                    target.relocate(p.getX() - mouseOffsetFromNodeZeroX,p.getY() - mouseOffsetFromNodeZeroY);
                    Bounds newBounds = target.getBoundsInParent();
                    if( ! this.releaseBoundary.contains(newBounds)){
                        target.relocate(oldPos.getX(), oldPos.getY());
                    }
                }
            } else {
                Parent parent = this.target.getParent();
                Point2D p = parent.sceneToLocal(event.getSceneX(), event.getSceneY());
                target.relocate(p.getX() - mouseOffsetFromNodeZeroX,p.getY() - mouseOffsetFromNodeZeroY);
            }

            //target.relocate(event.getSceneX() - mouseOffsetFromNodeZeroX,event.getSceneY() - mouseOffsetFromNodeZeroY);
            //clear changes from TranslateX and TranslateY
            target.setTranslateX(0);
            target.setTranslateY(0);
            cycleStatus = INACTIVE;
        }
    }

    /*---------------------------Draggable property-----------------------*/

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

    /**
     * Return the node managed by the DragController
     * @return the target node
     */
    public Node getManagedNode(){
        return this.target;
    }

    /*----------------------------------Boundaries----------------------*/

    /**
     * Define the boundaries where the node can be dragged to and released into.
     * The nodes bounds must be contained entirely by the boundaries to do the action
     * (If outsite of parent bounds, no change will be seen)
     * <br/>If only dragBoundary is defined, releaseBoundary will be considered to be equal to dragBoundary
     * <br/>All boundaries must be from the target node s parent coordinate system.
     * <br/><i>Help : Boundaries inside a layout will only be computed after the scene was shown.</i>
     * @param dragBoundary Clip the position of the node inside the boundary
     * @param releaseBoundary Allow release of the node only inside boundary
     */
    public void setBoundaries(Bounds dragBoundary, Bounds releaseBoundary){
        this.dragBoundary = dragBoundary;
        this.releaseBoundary = releaseBoundary;
        if(this.releaseBoundary == null){
            this.releaseBoundary = this.dragBoundary;
        }
    }

    /**
     * Define the boundaries where the node can be dragged to and released into.
     * The nodes bounds must be contained entirely by the boundaries to do the action
     * (If outsite of parent bounds, no change will be seen)
     * <br/>All boundaries must be from the target node s parent coordinate system.
     * <br/><i>Help : Boundaries inside a layout will only be computed after the scene was shown.</i>
     * @param moveBoundary Clip the position of the node inside the boundary
     */
    public void setBoundaries(Bounds moveBoundary){
        setBoundaries(moveBoundary, null);
    }

    /**
     * Get the boundary where the node can be dragged to.
     * <br/>null by default
     * @return the Bounds
     */
    public Bounds getDragBoundary(){
        return this.dragBoundary;
    }

    /**
     * Get the boundary where the node can be released into.
     * <br/>null by default
     * @return the Bounds
     */
    public Bounds getReleaseBoundary() {
        return releaseBoundary;
    }


    /*--------------------------Position------------------------*/

    /**
     * Calculate the coordinate in the layout where the node is drawn
     * <br/><a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#translateXProperty--">Source of calculation</a>
     * @param node the node which position is calculated
     * @return the position of the node in the layout
     */
    public Point2D getCurrentPos(Node node){
        return new Point2D(node.getLayoutX()+node.getTranslateX(), node.getLayoutY()+node.getTranslateY());
    }

    /**
     * Snap the target node back to its last position
     */
    public void snapToOrigin(){
        this.target.setTranslateX(0);
        this.target.setTranslateY(0);
    }


    /*------------------Checker-------------------------*/

    /**
     * Check condition of grab action on the target node
     * @param grabChecker the condition
     */
    public void setGrabChecker(SimpleChecker grabChecker){
        this.grabChecker = grabChecker;
    }

    /**
     * Check condition of drag action on the target node
     * @param dragChecker the condition
     */
    public void setDragChecker(SimpleChecker dragChecker) {
        this.dragChecker = dragChecker;
    }

    /**
     * Check condition of release action on the target node
     * @param releaseChecker the condition
     */
    public void setReleaseChecker(SimpleChecker releaseChecker) {
        this.releaseChecker = releaseChecker;
    }

    /**
     * Check conditions of grab, drag and release actions on the target node
     * @param grabChecker condition in the check method. Can be null
     * @param dragChecker condition in the check method. Can be null
     * @param releaseChecker condition in the check method. Can be null
     */
    public void setCheckers(SimpleChecker grabChecker, SimpleChecker dragChecker, SimpleChecker releaseChecker){
        this.grabChecker = grabChecker;
        this.dragChecker = dragChecker;
        this.releaseChecker = releaseChecker;
    }

    /**
     * Return the condition of the grab action on the target node
     * @return condition as a SimpleChecker
     * @see SimpleChecker
     */
    public SimpleChecker getGrabChecker() {
        return grabChecker;
    }

    /**
     * Return the condition of the drag action on the target node
     * @return condition as a SimpleChecker
     * @see SimpleChecker
     */
    public SimpleChecker getDragChecker() {
        return dragChecker;
    }

    /**
     * Return the condition of the release action on the target node
     * @return condition as a SimpleChecker
     * @see SimpleChecker
     */
    public SimpleChecker getReleaseChecker() {
        return releaseChecker;
    }

    /**
     * A class to do a check on a condition.
     * <br/>Useful to give a check order from an external class
     */
    public abstract class SimpleChecker{

        /**Create a simple checker to do a check on a condition.**/
        public SimpleChecker(){

        }

        /**
         * Check the condition of the SimpleChecker.
         * <br/>The condition must be defined in this function
         * @return the result of the check
         */
        public abstract boolean check();
    }
}