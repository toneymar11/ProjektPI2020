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
import pisproizvodjac.LoginFormController;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author toni
 */
public class skladisteController implements Initializable {

    @FXML
    private TextField txtkolicina;
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
    
    @FXML
    private Label lbl;

    public int id = LoginFormController.getID();

    Connection conn = null;
    PreparedStatement PS, PS1, PS2, PS3, PS4 = null;
    ResultSet RS, RS1, RS2, RS3, RS4 = null;

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
    
    public int kolicinauskladistu(int id) throws SQLException{
    
      
            int count=0;
            String sql2 = "SELECT * FROM `skladiste` WHERE id='"+id+"'";
            PS4 = (PreparedStatement) conn.prepareStatement(sql2);
            RS4 = PS4.executeQuery();
            while(RS4.next()){
                 count = Integer.parseInt(RS4.getString(4));
            }
           
            RS4.close();
            PS4.close();
            
        return count;
    }
    int kolicinaodredjenog;

    @FXML
    void odaberiproizvod(ActionEvent event) throws SQLException {

        String string = boxproizvod.getValue();

        kolicinaodredjenog = 0;

        try {
            String sql3 = "SELECT id FROM `proizvodi` WHERE naziv='" + string + "'";
            PS3 = (PreparedStatement) conn.prepareStatement(sql3);
            RS3 = PS3.executeQuery();
            data.clear();
            while (RS3.next()) {

                String sql = "SELECT * FROM `skladiste` WHERE id_proizvoda='" + RS3.getInt(1) + "' and id_proizvodjaca='" + id + "'";
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
            //System.out.println(kolicinaodredjenog);

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
    ObservableList<String> proizvodi = FXCollections.observableArrayList();

    private ObservableList<String> Proizvodi() {

        try {
            String sql = "SELECT * FROM `proizvodi`";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            while (RS.next()) {
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
            String sql = "SELECT * FROM `skladiste` WHERE id_proizvodjaca= '" + id + "'";
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

    @FXML
    void azurirajkolicinu(ActionEvent event) throws SQLException {
        
        String sql = "UPDATE skladiste SET kolicina=? where id='" + idsklad + "'";

        try {
            PS = (PreparedStatement) conn.prepareStatement(sql);
            
            PS.setString(1, txtkolicina.getText());

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
    void dataClear() throws SQLException {

        txtkolicina.clear();
        boxproizvod.setValue("Odaberite proizvod");
        ukupno.setText("0");
        lbl.setText("");
        setTable();
    }

    @FXML
    void izbrisiIzSkladista(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Želite li izbrisati proizvod iz skladišta " + boxproizvod.getValue() + " ?!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try {
                Skladiste skl = (Skladiste) skladisteTablica.getSelectionModel().getSelectedItem();
                String sql = "DELETE FROM `skladiste` WHERE id=?";
                PS = (PreparedStatement) conn.prepareStatement(sql);
                PS.setString(1, Integer.toString(idsklad));
                PS.execute();
                lbl.setText("Izbrisano");
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
    String proizv;
    int idsklad;

    @FXML
    void prikazi(MouseEvent event) throws SQLException {

        try {
            Skladiste skl = (Skladiste) skladisteTablica.getSelectionModel().getSelectedItem();
            String sql = "SELECT * FROM `skladiste`";
            PS = (PreparedStatement) conn.prepareStatement(sql);

            txtkolicina.setText(Integer.toString(skl.getKolicina()));

            boxproizvod.setValue(skl.getId_proizvoda());
            idsklad = skl.getId();
            
            proizv = skl.getId_proizvoda();

        } catch (Exception e) {
            System.out.println("Show method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();

        }

    }
    
    public String dohvatiNaziv(int id) throws SQLException{
    
    String naziv="";
    
        String sql2 = "SELECT `naziv` FROM `proizvodi` WHERE id='"+id+"'";
        PS4 = (PreparedStatement) conn.prepareStatement(sql2);
        RS4 = PS4.executeQuery();
        while(RS4.next()){
            naziv = RS4.getString("naziv");
        }
        
        RS4.close();
        PS4.close();
    
    return naziv;
    }
    
    public int dohvatiID(String naziv) throws SQLException{
    
        int idd = 0;
    
        String sql2 = "SELECT `id` FROM `proizvodi` WHERE naziv='"+naziv+"'";
        PS4 = (PreparedStatement) conn.prepareStatement(sql2);
        RS4 = PS4.executeQuery();
        while(RS4.next()){
            idd = Integer.parseInt(RS4.getString("id"));
        }
        
        RS4.close();
        PS4.close();
    
    return idd;
    }
    
    int id_proizv;
    int is;

   @FXML
    void unesiUSkladiste(ActionEvent event) throws SQLException {

        if (txtkolicina.getText().trim().isEmpty() || boxproizvod.getValue().trim().isEmpty()) {

            lbl.setText("Unesite sva polja");
        } else {
            lbl.setText("");

            //Validate if the Proizvod is exist or not
            String sqlValidate = "SELECT * FROM `skladiste` WHERE id_proizvodjaca='"+id+"'";
            PS = (PreparedStatement) conn.prepareStatement(sqlValidate);
            RS = PS.executeQuery();
            boolean flag = true;
            String naziv;
            

            while (RS.next()) {
                
                   id_proizv= Integer.parseInt(RS.getString(2));
                  // System.out.println(id_proizv);
                   
                   naziv = dohvatiNaziv(id_proizv);
                  
                   
                if (naziv.equals(boxproizvod.getValue())) {
                    is = Integer.parseInt(RS.getString(1));
                    flag = false;
                    break;
                }
            }

            PS.close();
            RS.close();

            if (flag) {
                try {
                    String sql = "INSERT INTO `skladiste`(`id_proizvoda`, `id_proizvodjaca`, `kolicina`)"
                            + "VALUES(?,?,?)";
                    int idproiz = dohvatiID(boxproizvod.getValue());
                    
                    PS = (PreparedStatement) conn.prepareStatement(sql);
                    
                    PS.setString(1, Integer.toString(idproiz));
                    PS.setString(2, Integer.toString(id));
                    PS.setString(3, txtkolicina.getText());
                    PS.execute();

                    lbl.setText("Unesen je proizvod u skladište");
                } catch (Exception e) {
                    System.out.println("insert Method");
                    System.out.println(e);
                } finally {
                    PS.close();
                    setTable();
                    dataClear();
                }
            } else {
                
                try {
                    
                    String sql = "UPDATE skladiste SET kolicina=? WHERE id='" +is+ "'";
                    
                    int koll = kolicinauskladistu(is);
                    int lkol = Integer.parseInt(txtkolicina.getText());
                    int upkol = koll + lkol;
                    
                    PS = (PreparedStatement) conn.prepareStatement(sql);
                    PS.setString(1, Integer.toString(upkol));
                    PS.execute();

                    lbl.setText("Nadodana je kolicina proizvoda u skladište");
                } catch (Exception e) {
                    System.out.println("update Method");
                    System.out.println(e);
                } finally {
                    PS.close();
                    setTable();
                    dataClear();
                }
                
            }
        }

    }

}
