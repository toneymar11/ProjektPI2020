
package Proizvodi;

import Narudzbe.Narudzbe;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author toni
 */
public class proizvodiController implements Initializable {

    @FXML
    private TextField txtproizvod;
    @FXML
    private TextField txtcijena;
    @FXML
    private Label lbl;
    @FXML
    private TableView<Proizvodi> proizvodiTablica;
    @FXML
    private TableColumn<Proizvodi, String> proizvod;
    @FXML
    private TableColumn<Proizvodi, String> cijena;
    @FXML
    private TextField txtSearch;
    
    Connection conn = null;
    PreparedStatement PS = null;
    ResultSet RS = null;
    
    private ObservableList<Proizvodi> data = FXCollections.observableArrayList();

    FilteredList<Proizvodi> filteredData = new FilteredList<>(data, e -> true);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            conn = bazakonekcija.spoji();
            setTable();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void setTable() throws SQLException {

        try {
            String sql = "SELECT * FROM `proizvodi`";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            data.clear();

            while (RS.next()) {
           
                data.add(new Proizvodi(RS.getInt(1),
                        RS.getString(2),
                        RS.getInt(4)
                )
                );
            }
    
            proizvod.setCellValueFactory(new PropertyValueFactory<Proizvodi, String>("naziv"));
            cijena.setCellValueFactory(new PropertyValueFactory<Proizvodi, String>("cijena"));

            proizvodiTablica.setItems(data);

            PS.close();
            RS.close();
        } catch (Exception e) {
            System.out.println("Set Table Method");
            System.out.println(e);
        }
    }
    
    @FXML
    private void dataClear() {
        
        txtproizvod.setEditable(true);
        txtproizvod.clear();
        txtcijena.clear();
        lbl.setText("");
    }
    
    int id_proizvoda;
    
     @FXML
    private void prikazi(MouseEvent event) throws SQLException {
        
        txtproizvod.setEditable(false);
        
        try {
            Proizvodi pro = (Proizvodi) proizvodiTablica.getSelectionModel().getSelectedItem();
            String sql = "SELECT * FROM `proizvodi`";
            PS = (PreparedStatement) conn.prepareStatement(sql);

            txtproizvod.setText(pro.getNaziv());
            txtcijena.setText(Integer.toString(pro.getCijena()));
            id_proizvoda = pro.getId();
            

        } catch (Exception e) {
            System.out.println("Show method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();
          
        }
    }
    
    @FXML
    private void unesiProizvod(ActionEvent event) throws SQLException {
        
          //Check if all feilds are not empty
        if (txtproizvod.getText().trim().isEmpty() || txtcijena.getText().trim().isEmpty() ) {

            lbl.setText("Unesite sva polja");
        } else {
            lbl.setText("");

            //Validate if the Proizvod is exist or not
            String sqlValidate = "SELECT `naziv` FROM `proizvodi`";
            PS = (PreparedStatement) conn.prepareStatement(sqlValidate);
            RS = PS.executeQuery();
            boolean flag = true;

            while (RS.next()) {
                if (RS.getString("naziv").equals(txtproizvod.getText())) {
                    flag = false;
                    break;
                }
            }

            PS.close();
            RS.close();
            
            if (flag) {
                try {
                    String sql = "INSERT INTO `proizvodi`(`naziv`, `url`, `cijena`)"
                            + "VALUES(?,?,?)";

                    PS = (PreparedStatement) conn.prepareStatement(sql);
                    PS.setString(1, txtproizvod.getText());
                    PS.setString(2,"images/default.jpg");
                    PS.setString(3, txtcijena.getText());
                    
                    lbl.setText("Unesen je proizvod");
                    PS.execute();

                    
                } catch (Exception e) {
                    System.out.println("insert Method");
                    System.out.println(e);
                } finally {
                    PS.close();
                    
                    setTable();
                    dataClear();
                }
            } else {
                lbl.setText("Proizvod postoji");
            }
        }
      
    }

   
    @FXML
    private void izbrisiProizvod(ActionEvent event) throws SQLException {
        
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Želite li izbrisati proizvod " + txtproizvod.getText() + " ?!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try {
                Proizvodi pro = (Proizvodi) proizvodiTablica.getSelectionModel().getSelectedItem();
                String sql = "DELETE FROM `proizvodi` WHERE id=?";
                PS = (PreparedStatement) conn.prepareStatement(sql);
                PS.setString(1, Integer.toString(id_proizvoda));
                PS.execute();
                lbl.setText("Deleted");
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                PS.close();
                RS.close();
                setTable();
                dataClear();
            }

        }
    }

    @FXML
    private void azurirajProizvod(ActionEvent event) throws SQLException {
         
         //Varible U is the selected user
        String sql = "UPDATE proizvodi SET cijena=? where id='" + id_proizvoda + "'";

        try {
            PS = (PreparedStatement) conn.prepareStatement(sql);
            
            PS.setString(1, txtcijena.getText());

            PS.execute();

            lbl.setText("Ažurirano");
            
        } catch (SQLException e) {
            System.out.println("Update Method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();
            setTable();
            dataClear();
        }
    }

   

    @FXML
    private void searchProizvod(KeyEvent event) {
        
         txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Proizvodi>) pro -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (pro.getNaziv().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (pro.getNaziv().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Proizvodi> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(proizvodiTablica.comparatorProperty());
        proizvodiTablica.setItems(sortedData);
    }
    
}
