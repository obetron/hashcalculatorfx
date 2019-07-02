package com.gelecex;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author eren.basaran
 */
public class HashCalculatorController implements Initializable {

    @FXML
    private Label lblAlgorithms;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblFileChooser;
    @FXML
    private Label lblValue;
    @FXML
    private Label lblHashed;
    @FXML
    private Button btnBrowse;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCalculate;
    @FXML
    private ComboBox<String> cbLang;
    @FXML
    private ComboBox<String> cbAlgorithms;
    @FXML
    private TextField txtHashed;

    private String hashAlgorithm = "SHA-256";
    private String fileChooserTitle = "";
    private String chooserWarning = "";
    private String contentInfo = "";
    private File selectedFile;

    @FXML
    private void operationButtonAction(ActionEvent event) {
        if (event.getTarget().equals(btnCancel)) {
            System.exit(0);
        } else if (event.getTarget().equals(btnCalculate)) {
            HashCalculator hash = new HashCalculator();
            if (selectedFile != null) {
                String calculatedHashValue = hash.calculate(selectedFile, hashAlgorithm);
                lblValue.setText(calculatedHashValue);
//                if (txtHashed.getText() != null) {
//                    if (!txtHashed.getText().equals(calculatedHashValue)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(chooserWarning);
                        alert.setContentText("Dogrulama tamam");
//                    }
//                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(chooserWarning);
                alert.setContentText(contentInfo);
            }
        }
    }

    @FXML
    private void browseButtonAction(ActionEvent event) {
        if (event.getTarget().equals(btnBrowse)) {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(fileChooserTitle);
            selectedFile = fileChooser.showOpenDialog(stage);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Locale turkishLocale = new Locale("tr", "TR");
        ResourceBundle trBundle = ResourceBundle.getBundle("lang_tr_TR", turkishLocale);
        Locale englishLocale = new Locale("en", "US");
        ResourceBundle enBundle = ResourceBundle.getBundle("lang_en_US", englishLocale);

        ObservableList<String> cbLangList = FXCollections.observableArrayList("Turkish", "English");
        cbLang.setItems(cbLangList);
        cbLang.setValue("Turkish");
        setBundle(trBundle);
        cbLang.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Turkish")) {
                    setBundle(trBundle);
                } else if (newValue.equalsIgnoreCase("English")) {
                    setBundle(enBundle);
                }
            }
        });

        ObservableList<String> algorithmList = FXCollections.observableArrayList("MD2", "MD5", "SHA1", "SHA-256", "SHA-384", "SHA-512");
        cbAlgorithms.setItems(algorithmList);
        cbAlgorithms.setValue("SHA-256");
        cbAlgorithms.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                hashAlgorithm = newValue;
            }
        });

    }

    private void setBundle(ResourceBundle bundle) {
        lblAlgorithms.setText(bundle.getString("algorithms"));
        lblResult.setText(bundle.getString("result"));
        lblFileChooser.setText(bundle.getString("fileChooser"));
        lblHashed.setText(bundle.getString("hashed"));
        btnBrowse.setText(bundle.getString("browse"));
        btnCancel.setText(bundle.getString("cancel"));
        btnCalculate.setText(bundle.getString("calculate"));
        fileChooserTitle = bundle.getString("chooserTitle");
        chooserWarning = bundle.getString("chooserWarning");
        contentInfo = bundle.getString("contentInfo");
    }

}
