package Tests.DragNodes;

import controller.DragController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DragTest extends Application {

    BorderPane root;
    Pane top = new Pane();
    Pane bottom = new Pane();
    Pane center = new Pane();
    Pane left = new Pane();
    Pane right = new Pane();

    Node circle = new Rectangle(50,50);


    int count = 0;

    private double anchorX;
    private double anchorY;

    private double mouseOffsetFromNodeZeroX;
    private double mouseOffsetFromNodeZeroY;

    private EventHandler<MouseEvent> setAnchor;
    private EventHandler<MouseEvent> updatePositionOnDrag;
    private EventHandler<MouseEvent> commitPositionOnRelease;

    boolean centerIsPane = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new BorderPane();
        primaryStage.setTitle("Hello World");

        if(!centerIsPane){
            Canvas canvas = new Canvas(300,300);
            //canvas.setLayoutX(100);
            //canvas.setLayoutY(100);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.YELLOW);
            gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
            root.setCenter(canvas);
        }
        //StackPane stp = new StackPane();

        initRoot();
        //center.setPrefSize(300,300);
        //center.getChildren().addAll(circle);

        //Pane tmp = new Pane();
        //tmp.setStyle("-fx-background-color: beige;");
        //tmp.setPrefSize(300,300);
        //root.setCenter(tmp);
        root.getChildren().add(circle);
        placeNode(150,150,circle);
        DragController circleCtrl = new DragController(circle, true);
        /*createHandlers();

        circle.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
        circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
        circle.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);


         */

        //primaryStage.setScene(new Scene(root, 500, 500));
        //primaryStage.setScene(new Scene(center));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    void initRoot(){
        top.setMinSize(500,100);
        top.setPrefSize(500,100);
        bottom.setMinSize(500,100);
        bottom.setPrefSize(500,100);
        left.setMinSize(100,300);
        left.setPrefSize(100,300);
        right.setMinSize(100,300);
        right.setPrefSize(100,300);
        center.setMinSize(300,300);
        center.setPrefSize(300,300);
        if(centerIsPane){
            root.setCenter(center);
        }
        root.setBottom(bottom);
        root.setTop(top);
        root.setLeft(left);
        root.setRight(right);

        //Rectangle fond = new Rectangle(100, 100);
        //fond.relocate(150,200);
        //fond.setFill(Color.AQUAMARINE);
        center.setStyle("-fx-background-color: beige;");
        //center.getChildren().addAll(fond,circle);
    }

    private void createHandlers() {
        setAnchor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown()) {
                    anchorX = event.getSceneX();
                    anchorY = event.getSceneY();
                    //anchorX = target.getLayoutX();
                    //anchorY = target.getLayoutY();
                    double layX = circle.getLayoutX();
                    //mouseOffsetFromNodeZeroX = event.getSceneX()-circle.getLayoutX();
                    //mouseOffsetFromNodeZeroY = event.getSceneY()-circle.getLayoutY();
                    /*if(circle instanceof Circle){ //Use upper left corner coordonate. Circle use center coordonates
                        Circle c = (Circle) circle;
                        mouseOffsetFromNodeZeroX = event.getX()+circle.getRadius();
                        mouseOffsetFromNodeZeroY = event.getY()+circle.getRadius();
                    } else {

                     */
                        mouseOffsetFromNodeZeroX = event.getX();
                        mouseOffsetFromNodeZeroY = event.getY();
                   // }

                }

                if (event.isSecondaryButtonDown()) {
                    circle.setTranslateX(0);
                    circle.setTranslateY(0);
                }
            }
        };
        /*setAnchor = event -> {
            if (event.isPrimaryButtonDown()) {
                cycleStatus = ACTIVE;
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                //anchorX = target.getLayoutX();
                //anchorY = target.getLayoutY();
                mouseOffsetFromNodeZeroX = event.getX()-target.getLayoutX();
                mouseOffsetFromNodeZeroY = event.getY()-target.getLayoutY();
            }

            if (event.isSecondaryButtonDown()) {
                cycleStatus = INACTIVE;
                target.setTranslateX(0);
                target.setTranslateY(0);
            }
        };

         */

        updatePositionOnDrag = event -> {
                circle.setTranslateX(event.getSceneX() - anchorX);
                circle.setTranslateY(event.getSceneY() - anchorY);

        };

        commitPositionOnRelease = event -> {
                //commit changes to LayoutX and LayoutY
                //circle.setLayoutX(event.getSceneX() - mouseOffsetFromNodeZeroX);
                //circle.setLayoutY(event.getSceneY() - mouseOffsetFromNodeZeroY);
                //target.setLayoutX(anchorX);
                //target.setLayoutY(anchorY);
            count++;
            if(count == 2){
                System.out.println("release");
            }


                circle.relocate(event.getSceneX() - mouseOffsetFromNodeZeroX,event.getSceneY() - mouseOffsetFromNodeZeroY);

                //clear changes from TranslateX and TranslateY
                circle.setTranslateX(0);
                circle.setTranslateY(0);
        };
    }

    public void placeNode(double x, double y, Node target){
        target.setLayoutX(x);
        target.setLayoutY(y);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
