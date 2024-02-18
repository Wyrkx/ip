package gulie.gui;

import gulie.Gulie;
import gulie.GulieInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

/**
 * The application for Gulie's GUI.
 */
public class GulieApp extends Application implements GulieInterface {

    private static final String NAME = "Güliedistodiez";

    private boolean isRunning;
    private VBox container;
    private TextField inputBox;
    private Button enterButton;
    private Scene scene;
    private CompletableFuture<String> input;
    private Gulie gulie;

    @Override
    public void start(Stage stage) {
        isRunning = true;

        ScrollPane scrollPane = new ScrollPane();
        container = new VBox();
        scrollPane.setContent(container);

        inputBox = new TextField();
        enterButton = new Button("Enter");

        AnchorPane layout = new AnchorPane();
        layout.getChildren().addAll(scrollPane, inputBox, enterButton);
        scene = new Scene(layout);


        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        layout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        container.setPrefHeight(Region.USE_COMPUTED_SIZE);

        inputBox.setPrefWidth(325.0);

        enterButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(enterButton, 1.0);
        AnchorPane.setRightAnchor(enterButton, 1.0);

        AnchorPane.setLeftAnchor(inputBox , 1.0);
        AnchorPane.setBottomAnchor(inputBox, 1.0);

        enterButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        inputBox.setOnAction((event) -> {
            handleUserInput();
        });

        container.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){

        }
        gulie = new Gulie(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                gulie.run();
            }
        }).start();

        stage.setScene(scene);
        stage.show();
    }

    private void handleUserInput() {
        if (input == null) {
            return;
        }
        String inp = inputBox.getText();
        inputBox.clear();
        container.getChildren().add(new UserDialog(inp));
        input.complete(inp);
    }

    @Override
    public String getString() {
        input = new CompletableFuture<>();
        String inp = input.join();
        input = null;
        return inp;
    }

    @Override
    public void print(String str) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                container.getChildren().add(new GulieDialog(str));
            }
        });
    }

    @Override
    public void close() {
        Platform.exit();
    }

    @Override
    public boolean isOpen() {
        return isRunning;
    }

    @Override
    public void stop(){
        try {
            super.stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            isRunning = false;
        }
    }

    public static void main(String[] args) {
        Application.launch(GulieApp.class, args);
    }
}
