
package pisproizvodjac;

import baza.bazakonekcija;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import pisproizvodjac.LoginFormController;


/**
 * FXML Controller class
 *
 * @author toni
 */
public class proizvodjacNavController implements Initializable{

    @FXML
    private Label lbl;
    
    public int id = LoginFormController.getID();
    Connection conn = null;
    PreparedStatement PS, PS1, PS2, PS3, PS4 = null;
    ResultSet RS, RS1, RS2, RS3, RS4 = null;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         try {
            // TODO
            conn = bazakonekcija.spoji();
            
            String sql2 = "SELECT * FROM `proizvodjaci` WHERE id='"+id+"'";
            PS4 = (PreparedStatement) conn.prepareStatement(sql2);
            RS4 = PS4.executeQuery();
            while(RS4.next()){
                
                 lbl.setText(RS4.getString(2));
                
            }
           
            RS4.close();
            PS4.close();
           

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    } 
    
     /*
    *Style
     */
    private double xOffset = 0;
    private double yOffset = 0;

    private void Style(Stage stage, Parent root) throws IOException {

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setX(300);
        stage.setY(110);
        //To Drage the window any where
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        
        root.requestFocus();
        stage.show();
    }

    /*
    *End Style
     */
    
    // mojeskladiste function
    Stage mojeskladisteStage = new Stage();
    boolean mojeskladisteCount = true;

    @FXML
    private void mojeskladiste(ActionEvent event) {
        
          if (mojeskladisteCount) {
            mojeskladisteCount = false;
             try {
                FXMLLoader loadmojeskladiste = new FXMLLoader(getClass().getResource("/Skladiste/skladiste.fxml"));
                Parent rootmojeskladiste = (Parent) loadmojeskladiste.load();
                mojeskladisteStage.setScene(new Scene(rootmojeskladiste));
                rootmojeskladiste.requestFocus();

                Style(mojeskladisteStage, rootmojeskladiste);

            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            mojeskladisteStage.hide();
            mojeskladisteStage = new Stage();
            mojeskladisteCount = true;
        }
        
    }
    
    
    @FXML
    private void btnLogout(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Želite li izaći iz programa !!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }
    
}
