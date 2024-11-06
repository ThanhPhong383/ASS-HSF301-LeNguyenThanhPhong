module PE_HSF301_SU24_202466_LeNguyenThanhPhong {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;

    opens entities to org.hibernate.orm.core, javafx.fxml;
    opens controller to javafx.fxml;
}
