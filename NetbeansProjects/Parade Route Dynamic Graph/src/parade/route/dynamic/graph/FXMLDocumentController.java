/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Michael
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextArea currentTime;
    
    @FXML
    private Button play;
    
    @FXML
    private AnchorPane ui;
    
    @FXML
    private Button pause;
    
    @FXML
    public void onPlayClicked(ActionEvent event){
        //currentTime.setText("shiet");
        currentTime.setEditable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*pause.setOnMouseClicked( event -> { 
            currentTime.setText("wdwdwd");
        });*/
    }    
}
