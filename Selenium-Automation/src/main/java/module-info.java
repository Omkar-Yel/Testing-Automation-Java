module com.openlinksfoundation.org.selenium.automation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.support;
    requires org.seleniumhq.selenium.remote_driver;
    requires dev.failsafe.core;
    opens com.openlinksfoundation.org.selenium.automation to javafx.fxml;
    exports com.openlinksfoundation.org.selenium.automation;
    
}
