module charts {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires com.google.gson;
    requires commons.math3;
    requires jfreechart;
    requires jcommon;
    requires  jfreechart.fx;
    requires java.desktop;
    requires fxgraphics2d;

    opens yakofinance to javafx.fxml;
    exports yakofinance;
}