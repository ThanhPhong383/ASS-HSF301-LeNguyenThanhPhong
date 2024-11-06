import entities.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private SessionFactory factory;

    public LoginController() {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    @FXML
    private void handleLoginButton() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try (Session session = factory.openSession()) {
            Account account = session.createQuery("from Account where email = :email and password = :password", Account.class)
                                     .setParameter("email", email)
                                     .setParameter("password", password)
                                     .uniqueResult();

            if (account != null) {
                showAlert("Success", "Login successful!");
                loadMoviesManagementScreen();
            } else {
                showAlert("Error", "Invalid email or password!");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to authenticate: " + e.getMessage());
        }
    }

    private void loadMoviesManagementScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MoviesManagement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Movies Management");
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load Movies Management screen: " + e.getMessage());
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
