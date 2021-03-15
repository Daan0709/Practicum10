package hotel.userinterface;

import hotel.model.Boeking;
import hotel.model.Hotel;
import hotel.model.Kamer;
import hotel.model.KamerType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BoekingenController {
    @FXML private Label voerGegevensInLabel;
    @FXML private TextField naamTextfield;
    @FXML private TextField adresTextfield;
    @FXML private DatePicker aankomstDatumDatepicker;
    @FXML private DatePicker vertrekDatumDatepicker;
    @FXML private ComboBox kamertypeCombobox;
    @FXML private Button resetButton;
    @FXML private Button boekButton;

    public void initialize(){
        voerGegevensInLabel.setText("Voer uw gegevens in!");
        voerGegevensInLabel.setStyle("-fx-text-fill: black;");
        naamTextfield.setText("");
        adresTextfield.setText("");
        aankomstDatumDatepicker.setValue(null);
        vertrekDatumDatepicker.setValue(null);
        kamertypeCombobox.setValue(null);
        Hotel hotel = Hotel.getHotel();
        ObservableList<KamerType> data = FXCollections.observableArrayList(hotel.getKamerTypen());
        kamertypeCombobox.setItems(data);
    }

    public void handleResetButton(ActionEvent actionEvent) {
        initialize();
    }

    public void handleBoekButton(ActionEvent actionEvent) {
            Hotel hotel = Hotel.getHotel();
        try {
            if (naamTextfield.getText() == "" || adresTextfield.getText() == "") {
                voerGegevensInLabel.setText("Naam en/of adres ontbreken nog!");
                voerGegevensInLabel.setStyle("-fx-text-fill: red;");
            }
            else if (aankomstDatumDatepicker.getValue().isBefore(LocalDate.now()) ||
                vertrekDatumDatepicker.getValue().isBefore(LocalDate.now()) ||
                aankomstDatumDatepicker.getValue().isAfter(vertrekDatumDatepicker.getValue()) ||
                aankomstDatumDatepicker.getValue() == null ||
                vertrekDatumDatepicker.getValue() == null) {
                voerGegevensInLabel.setText("Voer geldige data in!");
                voerGegevensInLabel.setStyle("-fx-text-fill: red;");
            }
            else {
                try {
                    hotel.voegBoekingToe(aankomstDatumDatepicker.getValue(), vertrekDatumDatepicker.getValue(), naamTextfield.getText(), adresTextfield.getText(), (KamerType) kamertypeCombobox.getValue());
                    voerGegevensInLabel.setText("De boeking is succesvol toegevoegd!");
                    voerGegevensInLabel.setStyle("-fx-text-fill: black;");
                } catch (Exception e) {
                    voerGegevensInLabel.setText("Geen kamers beschikbaar!");
                    voerGegevensInLabel.setStyle("-fx-text-fill: red;");
                }
            }
        } catch (Exception e) {
            voerGegevensInLabel.setText("Vul alle data en het kamertype in!");
            voerGegevensInLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
