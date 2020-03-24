
package pisproizvodjac;

import baza.bazakonekcija;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author toni
 */
public class LoginFormController implements Initializable {

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private Label status;
    
    public static int logirani;
    
    Connection conn = null;
    String user, pass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            conn = bazakonekcija.spoji();
        } catch (SQLException ex) {
            System.out.println("Login form : " + ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }    

    @FXML
    private void logirajse(ActionEvent event) throws IOException {
        user = txtUsername.getText();
        pass = txtPassword.getText();

        if (txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            status.setText("Unesite username i lozinku");
        } else {
            String SQL = "SELECT * FROM `proizvodjaci` WHERE username = ? and lozinka = ?";
            PreparedStatement PS = null;
            ResultSet RS = null;
            try {
                PS = (PreparedStatement) conn.prepareStatement(SQL);
                PS.setString(1, user);
                PS.setString(2, pass);
                RS = PS.executeQuery();
                if (!RS.next()) {
                    
                    status.setText("Pogreška username ili lozinka !!");
                    
                } else {
                    
                    logirani = Integer.parseInt(RS.getString(1));
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    FXMLLoader loadMain = new FXMLLoader(getClass().getResource("ProizvodjacNav.fxml"));
                    Parent rootMain = (Parent) loadMain.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(rootMain));
                    stage.setX(300);
                    stage.setY(10);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                   
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    @FXML
    private void izlazak(ActionEvent event) throws SQLException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Želite li izaći iz programa !!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            conn.close();
            System.exit(0);
        }
    }
    
    public static int getID(){
        
        return logirani;
    
    }
    
}
