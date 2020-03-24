package Skladiste;

import baza.bazakonekcija;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import Proizvodi.Proizvodi;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author toni
 */
public class skladisteController implements Initializable {

    @FXML
    private Label ukupno;
    @FXML
    private ComboBox<String> boxproizvod;
    @FXML
    private TableView<Skladiste> skladisteTablica;
    @FXML
    private TableColumn<Skladiste, String> proizvod;
    @FXML
    private TableColumn<Skladiste, String> proizvodjac;
    @FXML
    private TableColumn<Skladiste, String> kolicina;
    @FXML
    private TextField txtSearch;

    Connection conn = null;
    PreparedStatement PS, PS1, PS2, PS3 = null;
    ResultSet RS, RS1, RS2, RS3 = null;
   
    

    private ObservableList<Skladiste> data = FXCollections.observableArrayList();

    FilteredList<Skladiste> filteredData = new FilteredList<>(data, e -> true);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            conn = bazakonekcija.spoji();
            setTable();
            boxproizvod.getItems().addAll(Proizvodi());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    int kolicinaodredjenog;
    
    @FXML
    void odaberiproizvod(ActionEvent event) throws SQLException {
        
         String string = boxproizvod.getValue();
         
         kolicinaodredjenog=0;
        
        try {
            String sql3 = "SELECT id FROM `proizvodi` WHERE naziv='"+string+"'";
            PS3 = (PreparedStatement) conn.prepareStatement(sql3);
            RS3 = PS3.executeQuery();
            data.clear();
            while(RS3.next()){
            
            String sql = "SELECT * FROM `skladiste` WHERE id_proizvoda='"+RS3.getInt(1)+"'";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            while (RS.next()) {

                String sql1 = "SELECT * FROM `proizvodi` WHERE id = '" + RS.getInt(2) + "'";
                String sql2 = "SELECT * FROM `proizvodjaci` WHERE id ='" + RS.getInt(3) + "'";

                PS1 = (PreparedStatement) conn.prepareStatement(sql1);
                RS1 = PS1.executeQuery();
                PS2 = (PreparedStatement) conn.prepareStatement(sql2);
                RS2 = PS2.executeQuery();

                while (RS1.next()) {
                    while (RS2.next()) {
                        data.add(new Skladiste(RS.getInt(1), RS1.getString(2),
                                RS2.getString(2) + " " + RS2.getString(3),
                                RS.getInt(4)
                        )
                        );
                        kolicinaodredjenog += RS.getInt(4);
                        
                    }
                }
            }
           }
            System.out.println(kolicinaodredjenog);

            proizvod.setCellValueFactory(new PropertyValueFactory<Skladiste, String>("id_proizvoda"));
            proizvodjac.setCellValueFactory(new PropertyValueFactory<Skladiste, String>("id_proizvodjaca"));
            kolicina.setCellValueFactory(new PropertyValueFactory<Skladiste, String>("kolicina"));

            skladisteTablica.setItems(data);
            
            ukupno.setText(Integer.toString(kolicinaodredjenog));

            PS.close();
            RS.close();
        } catch (Exception e) {
            System.out.println("Set Table Method");
            System.out.println(e);
        }
        

    }
    ObservableList<String> proizvodi=FXCollections.observableArrayList();

    private ObservableList<String> Proizvodi(){
        
      
        try {
            String sql = "SELECT * FROM `proizvodi`";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();
            
            while(RS.next()){
                proizvodi.add(RS.getString(2));
            }
            
            PS.close();
            RS.close();
        
        } catch (SQLException ex) {
            
            System.out.println("Sustav nije uspio izvršiti upit!");
        }
        
        
        return proizvodi;
    }
    

    public void setTable() throws SQLException {

        try {
            String sql = "SELECT * FROM `skladiste`";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            data.clear();

            while (RS.next()) {

                String sql1 = "SELECT * FROM `proizvodi` WHERE id = '" + RS.getInt(2) + "'";
                String sql2 = "SELECT * FROM `proizvodjaci` WHERE id ='" + RS.getInt(3) + "'";

                PS1 = (PreparedStatement) conn.prepareStatement(sql1);
                RS1 = PS1.executeQuery();
                PS2 = (PreparedStatement) conn.prepareStatement(sql2);
                RS2 = PS2.executeQuery();

                while (RS1.next()) {
                    while (RS2.next()) {
                        data.add(new Skladiste(RS.getInt(1), RS1.getString(2),
                                RS2.getString(2) + " " + RS2.getString(3),
                                RS.getInt(4)
                        )
                        );
                    }
                }
            }

            proizvod.setCellValueFactory(new PropertyValueFactory<Skladiste, String>("id_proizvoda"));
            proizvodjac.setCellValueFactory(new PropertyValueFactory<Skladiste, String>("id_proizvodjaca"));
            kolicina.setCellValueFactory(new PropertyValueFactory<Skladiste, String>("kolicina"));

            skladisteTablica.setItems(data);

            PS.close();
            RS.close();
        } catch (Exception e) {
            System.out.println("Set Table Method");
            System.out.println(e);
        }
    }

    @FXML
    private void searchSkladiste(KeyEvent event) {

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Skladiste>) skla -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (skla.getId_proizvoda().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (skla.getId_proizvodjaca().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Skladiste> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(skladisteTablica.comparatorProperty());
        skladisteTablica.setItems(sortedData);
    }

}
