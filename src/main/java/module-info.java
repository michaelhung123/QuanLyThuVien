module com.mycompany.qlthuvien {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;

    opens com.mycompany.qlthuvien to javafx.fxml;
    exports com.mycompany.qlthuvien;
    exports com.mycompany.services;
    exports com.mycompany.pojo;

}
