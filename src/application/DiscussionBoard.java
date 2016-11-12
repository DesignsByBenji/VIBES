package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DiscussionBoard extends Application {
	@Override
	public void start(Stage primaryStage) {

		TabPane tabPane = new TabPane();
		FlowPane music = new FlowPane();
		FlowPane film = new FlowPane();
		GridPane grid = new GridPane();

		Scene scene = new Scene(grid, 400, 300, Color.BLACK);

		BorderPane borderPane = new BorderPane();
		VBox vbox = new VBox();

		Tab tab1 = new Tab();
		Tab tab2 = new Tab();
		tab1.setText("Music");
		tab2.setText("Film");
		tabPane.getTabs().addAll(tab1, tab2);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		TextField textField1 = new TextField();
		TextField textField2 = new TextField();
		TextArea textArea1 = new TextArea();
		TextArea textArea2 = new TextArea();
		textArea1.setEditable(false);
		textArea2.setEditable(false);

		Button button1 = new Button("Submit");
		Button button2 = new Button("Submit");
		button1.setOnAction((event) -> {
			textArea1.appendText(textField1.getText());
			textArea1.appendText("\n");
		});
		button2.setOnAction((event) -> {
			textArea2.appendText(textField2.getText());
			textArea2.appendText("\n");
		});
		vbox.getChildren().addAll(button1);
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);

		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		music.getChildren().addAll(textArea1, textField1, button1);
		film.getChildren().addAll(textArea2, textField2, button2);
		tab1.setContent(music);
		tab2.setContent(film);
		borderPane.setCenter(tabPane);

		grid.getChildren().addAll(borderPane);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Discussion Board");

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
