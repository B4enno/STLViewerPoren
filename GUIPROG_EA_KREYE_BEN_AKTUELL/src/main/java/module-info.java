module com.example.guiprog_ea_kreye_ben_aktuell {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires vecmath;

    opens com.example.guiprog_ea_kreye_ben_aktuell to javafx.fxml;
    exports com.example.guiprog_ea_kreye_ben_aktuell;
}