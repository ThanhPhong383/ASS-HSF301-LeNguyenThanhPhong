import entities.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MoviesManagementController {

    @FXML
    private TextField movieNameField, durationField, actorField, statusField, directorIDField;

    @FXML
    private TableView<Movie> moviesTable;

    @FXML
    private TableColumn<Movie, Integer> movieIDColumn;
    @FXML
    private TableColumn<Movie, String> movieNameColumn;
    @FXML
    private TableColumn<Movie, Integer> durationColumn;
    @FXML
    private TableColumn<Movie, String> actorColumn;
    @FXML
    private TableColumn<Movie, String> statusColumn;
    @FXML
    private TableColumn<Movie, Integer> directorIDColumn;

    private ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private SessionFactory factory;

    public MoviesManagementController() {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    @FXML
    public void initialize() {
        movieIDColumn.setCellValueFactory(new PropertyValueFactory<>("movieID"));
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        actorColumn.setCellValueFactory(new PropertyValueFactory<>("actor"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        directorIDColumn.setCellValueFactory(new PropertyValueFactory<>("directorID"));
        loadMovieData();
    }

    public void loadMovieData() {
        try (Session session = factory.openSession()) {
            List<Movie> movies = session.createQuery("from Movie", Movie.class).list();
            movieList.setAll(movies);
            moviesTable.setItems(movieList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load data from the database: " + e.getMessage());
        }
    }

    @FXML
    private void handleCreateButton() {
        try {
            String movieName = movieNameField.getText();
            int duration = Integer.parseInt(durationField.getText());
            String actor = actorField.getText();
            String status = statusField.getText();
            int directorID = Integer.parseInt(directorIDField.getText());

            Movie movie = new Movie(0, movieName, duration, actor, status, directorID);

            try (Session session = factory.openSession()) {
                Transaction tx = session.beginTransaction();
                session.save(movie);
                tx.commit();
                showAlert("Success", "Movie created successfully.");
                loadMovieData();
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to create movie: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateButton() {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showAlert("Error", "No movie selected for update.");
            return;
        }

        try {
            String movieName = movieNameField.getText();
            int duration = Integer.parseInt(durationField.getText());
            String actor = actorField.getText();
            String status = statusField.getText();
            int directorID = Integer.parseInt(directorIDField.getText());

            selectedMovie.setMovieName(movieName);
            selectedMovie.setDuration(duration);
            selectedMovie.setActor(actor);
            selectedMovie.setStatus(status);
            selectedMovie.setDirectorID(directorID);

            try (Session session = factory.openSession()) {
                Transaction tx = session.beginTransaction();
                session.update(selectedMovie);
                tx.commit();
                showAlert("Success", "Movie updated successfully.");
                loadMovieData();
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to update movie: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteButton() {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showAlert("Error", "No movie selected for deletion.");
            return;
        }

        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(selectedMovie);
            tx.commit();
            showAlert("Success", "Movie deleted successfully.");
            loadMovieData();
        } catch (Exception e) {
            showAlert("Error", "Failed to delete movie: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
