import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MovieVisualizer extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Read movies from CSV
        List<Movie> movies = MovieReader.readMoviesFromCSV("ratings.csv");

        // Create TableView
        TableView<Movie> tableView = new TableView<>();

        // Create columns for Name, Year, and Rating
        TableColumn<Movie, String> nameColumn = new TableColumn<>("Movie Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Movie, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());

        TableColumn<Movie, Double> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRating()).asObject());

        // Add columns to the TableView
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(yearColumn);
        tableView.getColumns().add(ratingColumn);

        // Add movie data to the TableView
        tableView.getItems().addAll(movies);

        // Set up the layout (VBox)
        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 600, 400);

        // Apply styling (optional)
        scene.getStylesheets().add("styles.css");  // If you want to add custom styles

        primaryStage.setTitle("Movie Ratings Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}