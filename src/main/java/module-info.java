module com.geospotter.geospotter {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.maxmind.geoip2;


    opens lk.CodePro.fx to javafx.fxml;
    exports lk.CodePro.fx;
}