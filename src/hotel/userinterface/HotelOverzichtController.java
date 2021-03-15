package hotel.userinterface;

import hotel.model.Boeking;
import hotel.model.Hotel;
import hotel.model.Kamer;
import hotel.model.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

public class HotelOverzichtController {
    @FXML private Label hotelnaamLabel;
    @FXML private ListView boekingenListView;
    @FXML private DatePicker overzichtDatePicker;

    private Hotel hotel = Hotel.getHotel();

    public void initialize() {
        hotelnaamLabel.setText("Boekingen hotel " + hotel.getNaam());
        overzichtDatePicker.setValue(LocalDate.now());
        toonBoekingen();
    }

    public void toonVorigeDag(ActionEvent actionEvent) {
        LocalDate dagEerder = overzichtDatePicker.getValue().minusDays(1);
        overzichtDatePicker.setValue(dagEerder);
    }

    public void toonVolgendeDag(ActionEvent actionEvent) {
        LocalDate dagLater = overzichtDatePicker.getValue().plusDays(1);
        overzichtDatePicker.setValue(dagLater);
    }

    public void nieuweBoeking(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Boekingen.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Boeking");
        newStage.setScene(new Scene(root));
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        toonBoekingen();
    }

    public void toonBoekingen() {
        ObservableList<String> boekingen = FXCollections.observableArrayList();

        // Vraag de boekingen op bij het Hotel-object.
        // Voeg voor elke boeking in nette tekst (string) toe aan de boekingen-lijst.
        for (Boeking boeking : hotel.getBoekingen()){
            Klant klant = boeking.getBoeker();
            Kamer kamer = boeking.getKamer();
            if ((boeking.getAankomstDatum().isBefore(overzichtDatePicker.getValue()) || boeking.getAankomstDatum().isEqual(overzichtDatePicker.getValue())) &&
                    (boeking.getVertrekDatum().isAfter(overzichtDatePicker.getValue()) || boeking.getVertrekDatum().isEqual(overzichtDatePicker.getValue()))){
                boekingen.add(boeking.getAankomstDatum() + " tot " + boeking.getVertrekDatum() + ": Kamernummer " + kamer.getKamerNummer() + " gehuurd door: "+klant.getNaam());
            }
        }
        boekingenListView.setItems(boekingen);
    }
}