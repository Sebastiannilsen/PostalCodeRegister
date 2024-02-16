module no.ntnu.idata2001 {
    requires javafx.controls;
    requires java.logging;
    exports no.ntnu.idata2001.PostalCodeRegister.ui.views;
    opens no.ntnu.idata2001.PostalCodeRegister.model to javafx.base;
}