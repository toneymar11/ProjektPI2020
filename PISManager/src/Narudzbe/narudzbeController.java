package Narudzbe;

import java.lang.Math;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class narudzbeController implements Initializable {

    @FXML
    private TextField txtkupac;

    @FXML
    private TextField txtproizvod;

    @FXML
    private TextField txtkolicina;

    @FXML
    private ComboBox<String> boxstanje;

    @FXML
    private Label lbl;

    @FXML
    private TableView<Narudzbe> narudzbeTablica;

    @FXML
    private TableColumn<Narudzbe, String> kupac;

    @FXML
    private TableColumn<Narudzbe, String> proizvod;

    @FXML
    private TableColumn<Narudzbe, String> kolicina;

    @FXML
    private TableColumn<Narudzbe, String> odobreno;

    @FXML
    private TextField txtSearch;

    Connection conn = null;
    PreparedStatement PS, PS1, PS2, PS3 = null;
    ResultSet RS, RS1, RS2, RS3 = null;
    

    private ObservableList<Narudzbe> data = FXCollections.observableArrayList();

    FilteredList<Narudzbe> filteredData = new FilteredList<>(data, e -> true);
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
         try {
            // TODO
            conn = bazakonekcija.spoji();
            setTable();
            boxstanje.getItems().addAll("da", "ne");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    
    

     @FXML
    void dataClear() {
        
        txtkupac.clear();
        txtproizvod.clear();
        txtkolicina.clear();
        lbl.setText("");

    }
    //fill table with data from database Customers data

    public void setTable() throws SQLException {

        try {
            String sql = "SELECT * FROM `narudzbe` WHERE odobreno='ne'";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            data.clear();

            while (RS.next()) {
                 
                String sql1 = "SELECT * FROM `kupci` WHERE id = '"+ RS.getInt(2)+"'";
                String sql2 = "SELECT * FROM `proizvodi` WHERE id ='"+RS.getInt(3)+"'";
                
                PS1 = (PreparedStatement) conn.prepareStatement(sql1);
                RS1 = PS1.executeQuery();
                PS2 = (PreparedStatement) conn.prepareStatement(sql2);
                RS2 = PS2.executeQuery();
                
               
               
                while(RS1.next()){
                while(RS2.next()){
                data.add(new Narudzbe(RS.getInt(1),RS1.getString(2)+" "+RS1.getString(3),
                        RS2.getString(2),
                        RS.getString(4),
                        RS.getString(5)
                )
                );
                }
                }
            }
            

            kupac.setCellValueFactory(new PropertyValueFactory<Narudzbe, String>("id_kupca"));
            proizvod.setCellValueFactory(new PropertyValueFactory<Narudzbe, String>("id_proizvoda"));
            kolicina.setCellValueFactory(new PropertyValueFactory<Narudzbe, String>("kolicina"));
            odobreno.setCellValueFactory(new PropertyValueFactory<Narudzbe, String>("odobreno"));
            

            narudzbeTablica.setItems(data);

            PS.close();
            RS.close();
        } catch (Exception e) {
            System.out.println("Set Table Method");
            System.out.println(e);
        }
    }

    @FXML
    void azuzirajNarudzbu(ActionEvent event) throws SQLException {
       

        try {
            
            if(boxstanje.getValue().equals("da")){
                try{
                    
                    String sql1 ="SELECT * FROM `narudzbe` WHERE id_narudzbe='" + id_narudzbe + "'";
                    PS1 = (PreparedStatement) conn.prepareStatement(sql1);
                    RS1 = PS1.executeQuery();


                    while(RS1.next()){
                        int a = Integer.parseInt(RS1.getString(4));
                        String sql2 = "SELECT * FROM `skladiste` WHERE id_proizvoda='"+RS1.getString(3)+"'"
                                + "HAVING kolicina > '"+a+"' "
                                + "ORDER BY `kolicina` DESC";
                        PS2 = (PreparedStatement) conn.prepareStatement(sql2);
                        RS2 = PS2.executeQuery();  



                        while(RS2.next()){

                           int koli = Integer.parseInt(RS2.getString(4));

                            String sql3 = "UPDATE skladiste SET kolicina=? where id='" + RS2.getString(1) + "'";
                            PS3 = (PreparedStatement) conn.prepareStatement(sql3);

                            int c= koli-Integer.parseInt(RS1.getString(4));

                            PS3.setString(1,Integer.toString(c));
                            PS3.execute();

                            String sql = "UPDATE narudzbe SET odobreno=? where id_narudzbe='" + id_narudzbe + "'";
                            PS = (PreparedStatement) conn.prepareStatement(sql);

                            PS.setString(1, boxstanje.getValue());


                            PS.execute();

                            break;

                        }
                    }
                
                }catch(Exception e){
                    System.out.println("U skladistu nema dovoljno kolicine");
                    System.out.println(e);
                }
                  
            }else{
                setTable();
                dataClear();
            }
            
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
    
    int id_narudzbe;
     @FXML
    void prikazi(MouseEvent event) throws SQLException {
        
         try {
            Narudzbe nar = (Narudzbe) narudzbeTablica.getSelectionModel().getSelectedItem();
            String sql = "SELECT * FROM `narudzbe`";
            PS = (PreparedStatement) conn.prepareStatement(sql);

            txtkupac.setText(nar.getId_kupca());
            txtproizvod.setText(nar.getId_proizvoda());
            txtkolicina.setText(nar.getKolicina());
            boxstanje.setValue(nar.getOdobreno());
            id_narudzbe = nar.getId_narudzbe();
           // System.out.println(id_narudzbe);
            

        } catch (Exception e) {
            System.out.println("Show method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();
           
        }
    }

    @FXML
    void searchNarudzbe(KeyEvent event) {
        
         txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Narudzbe>) nar -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (nar.getId_kupca().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (nar.getId_proizvoda().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Narudzbe> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(narudzbeTablica.comparatorProperty());
        narudzbeTablica.setItems(sortedData);

    }

    

}
