/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import it.polito.tdp.meteo.model.Rilevamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

//COMMENTI LABORATORIO 6

/*
 	Il codice e' molto molto incasinato perche' ho proseguito su una strada complicata in cui ho fatto le variabili distinte
 	per le citta' invece di crearle dentro le classi quindi non era la maniera piu' intelligente. Pero' secondo me funziona quindi
 	tolto alcune cose inutili, l'essenziale del codice dovrebbe esserci.
 	
 	Il calcolo dell'umdita' media la potevamo direttamente delegare al database mediante un'interrogazione diretta al database 
 	usando il comando AVG nella select e ritornando un double la situazione era risolta.
 	
 	
 */

public class FXMLController {
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<String> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	txtResult.clear();
    	
    	List<Rilevamento> parziale=new ArrayList<>();
    	parziale=model.trovaSequenza(Integer.parseInt(boxMese.getValue()));
		for(Rilevamento r: parziale) {
			
			txtResult.appendText("\n"+r.getLocalita()+" "+r.getData()+" "+r.getUmidita());
			
			
		}
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	
    	//preso il mese restituiamo la media di umidita' epr ogni citta'
    	txtResult.clear();
    	/*
    	 	Se qui invece che importare in un 'int', importiamo in un 'Integer', possiamo controllare che 
    	 	non siamo nel caso di nessuna selezione controllando che non sia 'null'. Questa cosa invece
    	 	con un 'int' non la possiamo in realta' controllare.
    	 */
    	int mese=Integer.parseInt(boxMese.getValue());
    	
    	Float[] ritorna= model.getAllRilevamentiMese(mese);
    	
    	txtResult.appendText("Genova  "+ritorna[0]);
    	txtResult.appendText("\nMilano  "+ritorna[1]);
    	txtResult.appendText("\nTorino  "+ritorna[2]);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        ObservableList<String> cursors = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12"); 
        boxMese.setItems(cursors);

    }
    
    
    public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model=model;
	}
}

