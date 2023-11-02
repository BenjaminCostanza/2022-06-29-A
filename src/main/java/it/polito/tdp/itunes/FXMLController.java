/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	txtResult.clear();
    	Album a1 = cmbA1.getValue();
        List<Album> adiacenze = this.model.getAdiacenze(a1);
        for(Album a : adiacenze) {
        	txtResult.appendText(a + " bilancio: " + a.getBilancio() + "\n");
        }
        
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Album albumPartenza = cmbA1.getValue();
    	Album albumArrivo = cmbA2.getValue();
    	if(albumPartenza == null || albumArrivo == null) {
    		txtResult.appendText("Selezionare due albume dalle combo box! \n");
    		return;
    	}
    	
    	Integer x = 0;
    	
    	try {
    	x = Integer.parseInt(txtX.getText());
    	}
    	catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero! \n");
    		return;
    	}
    	List<Album> path = this.model.getPath(albumPartenza, albumArrivo, x);
    	
    	if(path.isEmpty()) {
    		txtResult.appendText("Nessun percorso fra i due albume selezionati!\n" );
    		return;
    	}
    	
    	txtResult.appendText("Stampo il percorso fra " + albumPartenza +" e " + albumArrivo + "\n");
    	
    	for(Album a : path) {
    		txtResult.appendText("" +a + "\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	cmbA1.getItems().clear();
    	int n = 0;
    try {	
      n = Integer.parseInt(txtN.getText());
      if(n<0) {
    	  txtResult.appendText("Inserire un numero intero positivo!\n");
    	  return;
      }
    }
    catch(NumberFormatException e){
    	txtResult.appendText("Inserire un numero intero!\n");
    	return;
    }
   this.model.createGraph(n);
   cmbA1.getItems().addAll(this.model.getVertexSetAlphabetically());
   cmbA2.getItems().addAll(this.model.getVertexSetAlphabetically());
   txtResult.appendText("Grafo creato! \n");
   txtResult.appendText("# Vertici: " + this.model.getNVertici() + "\n");
   txtResult.appendText("# Archi: " + this.model.getNArchi()+ "\n");
    
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
