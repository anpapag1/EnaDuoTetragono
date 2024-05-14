module com.enaduotetragono {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.enaduotetragono to javafx.fxml;
    exports com.enaduotetragono;
}