package no.ntnu.idata2001.PostalCodeRegister.ui.views;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idata2001.PostalCodeRegister.model.NorwayPostTown;
import no.ntnu.idata2001.PostalCodeRegister.ui.controllers.MainController;

import java.io.IOException;

/**
 * The type Main window.
 */
public class MainWindow extends Application {

    /**
     * The Table view.
     */
    TableView<NorwayPostTown> tableView;
    /**
     * The controller class of the application.
     */
    private MainController mainController;

    @Override
    public void init() {
        tableView = new TableView<>();
    }

    /**
     * The stop() method is being called by the JavaFX-platform when the
     * platform stops, are being terminated. This would typically happen as a
     * result of the last open window being closed. Override this method to make
     * sure that the application is terminated.
     */
    @Override
    public void stop() {
        System.exit(0);
    }


    /**
     * The start method of the application. When the program starts a Welcome
     * alert will be displayed.
     *
     * @param primaryStage the primary stage.
     */
    @Override
    public void start(Stage primaryStage) {
        this.mainController = new MainController();

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();
        MenuBar mainMenu = createMenus();
        TextField searchField = createSearchField();

        topContainer.getChildren().addAll(mainMenu, searchField);

        root.setTop(topContainer);
        root.setCenter(createTableView());

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Postal Code Register v0.1-SNAPSHOT");
        primaryStage.setScene(scene);
        primaryStage.show();


        mainController.doShowWelcomeDialogue();

    }

    /**
     * Creates the search field that updates when something is typed.
     *
     * @return the search field as a TextField
     */
    private TextField createSearchField() {
        TextField searchText = new TextField();
        searchText.setPromptText("Search for post info");
        searchText.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setItems(mainController.filterList(newValue))
        );
        return searchText;
    }

    /**
     * Creates the menus
     *
     * @return the menus as MenuBar
     */
    private MenuBar createMenus() {
        // ----- The File-menu ------
        Menu menuFile = new Menu("File");

        MenuItem importFile = new MenuItem("Import from file");

        importFile.setOnAction(event -> {
            try {
                mainController.doImportFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException ignored) {
                // when the user selects cancel in File chooser, then do nothing.
            }
        });

        MenuItem exitApp = new MenuItem("Exit");
        exitApp.setOnAction(event -> {
            if (mainController.doShowExitDialogue()) {
                stop();
            }
        });

        menuFile.getItems().addAll(importFile, new SeparatorMenuItem(), exitApp);

        //------ The Edit-menu -------

        Menu menuEdit = new Menu("Edit");
        MenuItem deleteAll = new MenuItem("Remove all");
        deleteAll.setOnAction(event -> mainController.doRemoveAllPostTowns());
        menuEdit.getItems().add(deleteAll);

        //----- The Help-menu ------
        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> mainController.doShowAboutDialogue());
        menuHelp.getItems().add(about);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);

        return menuBar;
    }

    /**
     * Creates the tableview
     *
     * @return the tableview as a Node
     */
    private Node createTableView() {

        // Define the columns
        // The post number-column
        TableColumn<NorwayPostTown, String> postNumberColumn = new TableColumn<>("Post-nummer");
        postNumberColumn.setMinWidth(200);
        postNumberColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        // The name of place-column
        TableColumn<NorwayPostTown, String> nameOfPlaceColumn = new TableColumn<>("Post-sted");
        nameOfPlaceColumn.setMinWidth(200);
        nameOfPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfPlace"));

        // The municipality -column
        TableColumn<NorwayPostTown, String> municipalityColumn = new TableColumn<>("Kommune");
        municipalityColumn.setMinWidth(200);
        municipalityColumn.setCellValueFactory(new PropertyValueFactory<>("municipality"));

        tableView.setItems(this.mainController.getPostTownListWrapper());

        tableView.getColumns().addAll(postNumberColumn, nameOfPlaceColumn, municipalityColumn);


        // Add the TableView to a VBox
        VBox vbox = new VBox();
        vbox.getChildren().add(tableView);

        return vbox;
    }
}
