package no.ntnu.idata2001.PostalCodeRegister.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import no.ntnu.idata2001.PostalCodeRegister.model.NorwayPostTown;
import no.ntnu.idata2001.PostalCodeRegister.model.PostTownRegister;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * The type Main controller.
 */
public class MainController {

    private final PostTownRegister postTownRegister;

    private ObservableList<NorwayPostTown> norwayPostTownListWrapper;

    /**
     * Instantiates a new Main controller.
     */
    public MainController(){
        this.postTownRegister = new PostTownRegister();
        this.norwayPostTownListWrapper = initializePostTownListWrapper();
    }


    /**
     * Do show exit dialogue boolean.
     *
     * @return the boolean
     */
    public boolean doShowExitDialogue() {
        boolean exitConfirmed = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Are you sure you want to exit the app?");


        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            exitConfirmed = (result.get() == ButtonType.OK);
        }
        return exitConfirmed;
    }

    /**
     * Filter the register with the search text and returns the matches as an observable list
     *
     * @param searchText the search text
     * @return the observable list
     */
    public ObservableList<NorwayPostTown> filterList(String searchText){
        List<NorwayPostTown> list = postTownRegister.getPostTownList();
        List<NorwayPostTown> filteredList = new ArrayList<>();
        for (NorwayPostTown norwayPostTown : list){
            if(searchFindsOrder(norwayPostTown, searchText)){
                filteredList.add(norwayPostTown);
            }
        }
        return FXCollections.observableList(filteredList);
    }

    /**
     * Will return true if the search text matches either the postal code or name of place of any
     * PostTown in the register.
     * @param norwayPostTown the norway post-town
     * @param searchText the search text
     * @return the boolean
     */
    private boolean searchFindsOrder(NorwayPostTown norwayPostTown, String searchText){
        return (norwayPostTown.getPostalCode().toLowerCase().contains(searchText.toLowerCase())) ||
                (norwayPostTown.getNameOfPlace().toLowerCase().contains(searchText.toLowerCase()));
    }

    /**
     * Do import from file. Will open a FileChooser and the selected file will be passed in to
     * the readFromFile method in PostTownRegister class.
     * If the file is in wrong format the user will be prompted to select a new valid file,
     * until the user selects a valid file or presses cancel.
     * When a file is chosen and is valid the tableview will update and display the info collected
     * from the .txt file.
     *
     * @throws IOException          the io exception
     * @throws NullPointerException the null pointer exception
     */
    public void doImportFromFile() throws IOException, NullPointerException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        boolean done = false;
        boolean foundFile = false;
        while(!done){
            if (selectedFile.getName().endsWith(".txt")){
                done = true;
                foundFile = true;
            }else{
                if (showWrongFileTypeAlert()){
                    selectedFile = fileChooser.showOpenDialog(null);
                }else{
                    done = true;
                }

            }
        }
        if (foundFile) {
            try {
                int errors = postTownRegister.readFromFile(selectedFile);
                updateObservableList();
                if (this.postTownRegister.getPostTownList().isEmpty()) {
                    doShowFaultyFileDialogue();
                } else {
                    doShowImportDoneDialogue(errors);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                doShowFaultyFileDialogue();
            }
        }
    }

    private void doShowFaultyFileDialogue() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText("File is corrupted");
        alert.setContentText("Please select a new file");

        alert.showAndWait();
        try {
            doImportFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doShowImportDoneDialogue(int errors) {
        String errorString = "";
        Alert.AlertType alertType;
        if (errors != 0){
            errorString = errors + " post-towns where not added due to file errors or duplicates.";
            alertType = Alert.AlertType.WARNING;
        }else{
            alertType = Alert.AlertType.INFORMATION;
        }

        Alert alert = new Alert(alertType);
        alert.setTitle("Complete");
        alert.setHeaderText("Import Completed");
        alert.setContentText("The import was completed with " + errors + " errors.\n" + errorString);

        alert.showAndWait();
    }

    /**
     * Update observable list.
     * Updates the observable list to update the tableview
     * this method should be called whenever the register is changed
     */
    public void updateObservableList() {
        this.norwayPostTownListWrapper.setAll(this.postTownRegister.getPostTownList());
    }

    /**
     * A Window to alert the user that the selected file type is not valid
     * and prompts the user to select a new valid file.
     *
     * @return boolean. True if user chose a new valid file, false if user pressed cancel, and canceled the file choosing.
     */
    private boolean showWrongFileTypeAlert() {
        AtomicBoolean ok = new AtomicBoolean(false);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("The chosen file type is not valid");
        alert.setContentText("Please choose another file by clicking on Select File or cancel the operation. By canceling the operation no elements will be added.");

        ButtonType selectButton = new ButtonType("Select file", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(selectButton,cancelButton);

        alert.showAndWait().ifPresent(type -> ok.set(type == selectButton));

        return ok.get();
    }


    /**
     * Gets post-town list wrapper.
     *
     * @return the post-town list wrapper
     */
    public ObservableList<NorwayPostTown> getPostTownListWrapper() {
        return this.norwayPostTownListWrapper;
    }


    /**
     * Initializes the postTownListWrapper and sets the wrapper to the posTownRegister.
     *
     * @return an observable list with the postTownRegister
     */
    private ObservableList<NorwayPostTown> initializePostTownListWrapper() {
        // Create an ObservableArrayList wrapping the PostTownRegister.
        norwayPostTownListWrapper
                = FXCollections.observableArrayList(this.postTownRegister.getPostTownList());
        return norwayPostTownListWrapper;
    }

    /**
     * Do show about dialogue.
     */
    public void doShowAboutDialogue() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog - About");
        alert.setHeaderText("Information Dialog - About");
        alert.setContentText("A brilliant application created by\n"
                + "(C)Sebastian Nilsen\n"
                + "\nv1.0-SNAPSHOT");

        alert.showAndWait();
    }

    /**
     * Do show welcome dialogue.
     */
    public void doShowWelcomeDialogue() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Welcome");
        alert.setHeaderText("Please select file to upload");
        alert.setContentText("Please press \"Select file\"  and upload file with data to the application or press \"Use dummy data\" to use the dummy information.");

        ButtonType selectButton = new ButtonType("Select file", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Use dummy data", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(selectButton,cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == selectButton) {
                try {
                    doImportFromFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (NullPointerException e){
                    this.postTownRegister.fillRegisterWithDummies();
                    updateObservableList();
                }
            }else{
                this.postTownRegister.fillRegisterWithDummies();
                updateObservableList();
            }
        });
    }

    /**
     * Do remove all post-towns.
     */
    public void doRemoveAllPostTowns() {
        if (doShowDeleteDialogue()){
            this.postTownRegister.getPostTownList().clear();
            updateObservableList();
        }
    }

    /**
     * Do show welcome dialogue.
     * returns true if ok is pressed or false if cancel is pressed.
     */
    private boolean doShowDeleteDialogue() {
        boolean deleteConfirmed = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Delete confirmation");
        alert.setContentText("Are you sure you want to delete all items?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            deleteConfirmed = (result.get() == ButtonType.OK);
        }

        return deleteConfirmed;
    }
}
