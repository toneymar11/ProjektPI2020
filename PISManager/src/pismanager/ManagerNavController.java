package pismanager;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author toni
 */
public class ManagerNavController {

    @FXML
    Label lbl;

    /*
    * Style
     */
    private double xOffset = 0;
    private double yOffset = 0;

    private void Style(Stage stage, Parent root) throws IOException {

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setX(300);
        stage.setY(110);
        
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
    
    
    
    @FXML
    public void btnLogout(ActionEvent event) throws IOException {
       
         
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Želite li izaći iz programa !!",
                ButtonType.NO,ButtonType.YES);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
       
    }

    // Narudzbe function
    Stage narudzbeStage = new Stage();
    boolean narudzbeCount = true;
    
    @FXML
    public void narudzbe(ActionEvent e) throws IOException {
        //System.out.println(customerCount);
        //Stage stage = new Stage();
        if (!proizvodiCount) {
            proizvodiCount = true;
            proizvodiStage.hide();
            proizvodiStage = new Stage();
        } else if (!skladisteCount) {
            skladisteCount = true;
            skladisteStage.hide();
            skladisteStage = new Stage();
        } else if (!statistikaCount) {
            statistikaCount = true;
            statistikaStage.hide();
            statistikaStage = new Stage();
        }

        if (narudzbeCount) {
            narudzbeCount = false;
             try {
                FXMLLoader loadNarudzbe = new FXMLLoader(getClass().getResource("/Narudzbe/narudzbe.fxml"));
                Parent rootNarudzbe = (Parent) loadNarudzbe.load();
                narudzbeStage.setScene(new Scene(rootNarudzbe));
                rootNarudzbe.requestFocus();

                Style(narudzbeStage, rootNarudzbe);

            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            narudzbeStage.hide();
            narudzbeStage = new Stage();
            narudzbeCount = true;
        }
    }

    // Proizvodi function
    Stage proizvodiStage = new Stage();
    boolean proizvodiCount = true;
    
    @FXML
    public void proizvodi(ActionEvent e) throws IOException {
        
        if (!narudzbeCount) {
            narudzbeCount = true;
            narudzbeStage.hide();
            narudzbeStage = new Stage();
        } else if (!skladisteCount) {
            skladisteCount = true;
            skladisteStage.hide();
            skladisteStage = new Stage();
        } else if (!statistikaCount) {
            statistikaCount = true;
            statistikaStage.hide();
            statistikaStage = new Stage();
        }

        if (proizvodiCount) {
            proizvodiCount = false;
            try {
                FXMLLoader loadProizvodi = new FXMLLoader(getClass().getResource("/Proizvodi/proizvodi.fxml"));
                Parent rootProizvodi = (Parent) loadProizvodi.load();
                proizvodiStage.setScene(new Scene(rootProizvodi));
                rootProizvodi.requestFocus();

                Style(proizvodiStage, rootProizvodi);

            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            proizvodiStage.hide();
            proizvodiStage = new Stage();
            proizvodiCount = true;
        }
    }

    //Skladiste Function
    Stage skladisteStage = new Stage();
    boolean skladisteCount = true;
    
    @FXML
    public void skladiste(ActionEvent e) throws IOException {
        if (!narudzbeCount) {
            narudzbeCount = true;
            narudzbeStage.hide();
            narudzbeStage = new Stage();
        } else if (!proizvodiCount) {
            proizvodiCount = true;
            proizvodiStage.hide();
            proizvodiStage = new Stage();
        } else if (!statistikaCount) {
            statistikaCount = true;
            statistikaStage.hide();
            statistikaStage = new Stage();
        }

        if (skladisteCount) {
            skladisteCount = false;
            try {
                FXMLLoader loadExports = new FXMLLoader(getClass().getResource("/Skladiste/skladiste.fxml"));
                Parent rootSkladiste = (Parent) loadExports.load();
                skladisteStage.setScene(new Scene(rootSkladiste));
                rootSkladiste.requestFocus();

                Style(skladisteStage, rootSkladiste);
                
            } catch (IOException ex) {
                System.out.println(ex);
            }

        } else {
            skladisteStage.hide();
            skladisteStage = new Stage();
            skladisteCount = true;
        }
    }

    //Report Function
    Stage statistikaStage = new Stage();
    boolean statistikaCount = true;
    
    @FXML
    public void statistika() {

        if (!narudzbeCount) {
            narudzbeCount = true;
            narudzbeStage.hide();
            narudzbeStage = new Stage();
        } else if (!proizvodiCount) {
            proizvodiCount = true;
            proizvodiStage.hide();
            proizvodiStage = new Stage();
        } else if (!skladisteCount) {
            skladisteCount = true;
            skladisteStage.hide();
            skladisteStage = new Stage();
        }

        if (statistikaCount) {
            statistikaCount = false;
            try {
                FXMLLoader loadStatistika = new FXMLLoader(getClass().getResource("/Statistika/statistika.fxml"));
                Parent rootStatistika = (Parent) loadStatistika.load();
                statistikaStage.setScene(new Scene(rootStatistika));
                rootStatistika.requestFocus();

                Style(statistikaStage, rootStatistika);
            } catch (IOException ex) {
                System.out.println(ex);
            }

        } else {
            statistikaStage.hide();
            statistikaStage = new Stage();
            statistikaCount = true;
        }
    }

}
