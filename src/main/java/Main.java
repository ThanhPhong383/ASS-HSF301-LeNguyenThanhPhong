import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Tạo đối tượng cấu hình Hibernate
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

            // Tạo đối tượng SessionFactory
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            // Mở session và bắt đầu giao dịch
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            // Nếu kết nối thành công, in thông báo
            System.out.println("Kết nối thành công!");

            // Đóng giao dịch và session
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Kết nối thất bại!");
        }

        // Load file FXML và thiết lập Scene
        Parent root = FXMLLoader.load(getClass().getResource("/LoginScreen.fxml"));
        Scene scene = new Scene(root, 450, 350);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
