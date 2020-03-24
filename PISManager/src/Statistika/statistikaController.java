
package Statistika;

import Narudzbe.Narudzbe;
import baza.bazakonekcija;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author toni
 */
public class statistikaController implements Initializable {

    @FXML
    private Label brojneodobrenih;
    @FXML
    private Label brojodobrenih;
    @FXML
    private TableView<Statistika> statistikaTablica;
    @FXML
    private TableColumn<Statistika, String> naziv;
    @FXML
    private TableColumn<Statistika, String> kolicina;
    @FXML
    private PieChart Chart;
    @FXML
    private Label brojkupaca;
    @FXML
    private Label brojproizvodjaca;
    
     Connection conn = null;
    PreparedStatement PS, PS1, PS2, PS3 = null;
    ResultSet RS, RS1, RS2, RS3 = null;
    
     private ObservableList<Statistika> data = FXCollections.observableArrayList();

    FilteredList<Statistika> filteredData = new FilteredList<>(data, e -> true);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<PieChart.Data> pieChart = FXCollections.observableArrayList();
        
        
        try {
            // TODO
            conn = bazakonekcija.spoji();
            setTable();
            
            kupciKol();
            proizvodjaciKol();
            odobreneNarudzbe();
            neodobreneNarudzbe();
           
           String sql = "SELECT\n" +
                         "  MIN(id_proizvoda) as id_proizvoda,\n" +
                        "  SUM(`kolicina`) as kolicina\n" +
                        "FROM narudzbe\n" +
                        "WHERE odobreno=\"da\"\n" +
                        "GROUP BY id_proizvoda\n" +
                        "HAVING kolicina > 0";
           
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();
            
             while (RS.next()) {
             
                String sql1 = "SELECT naziv FROM `proizvodi` WHERE id = '"+ RS.getInt(1)+"'";
                PS1 = (PreparedStatement) conn.prepareStatement(sql1);
                RS1 = PS1.executeQuery();
                
                while(RS1.next()){
                        
                     String im = RS1.getString(1) +" " + RS.getString("kolicina") ;
                    
                     
                    pieChart.add( new PieChart.Data(im, Double.parseDouble(RS.getString("kolicina"))));
                         
                    }
                    
                }

           Chart.setData(pieChart);
    
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
      
    }
   
    
     public void setTable() throws SQLException {

        try {
            String sql = "SELECT\n" +
                         "  MIN(id_proizvoda) as id_proizvoda,\n" +
                        "  SUM(`kolicina`) as kolicina\n" +
                        "FROM narudzbe\n" +
                        "WHERE odobreno=\"da\"\n" +
                        "GROUP BY id_proizvoda\n" +
                        "HAVING kolicina > 0";
            
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();


            while (RS.next()) {
                 
                String sql1 = "SELECT naziv FROM `proizvodi` WHERE id = '"+ RS.getInt(1)+"'";
                PS1 = (PreparedStatement) conn.prepareStatement(sql1);
                RS1 = PS1.executeQuery();
                
                while(RS1.next()){
                    
                     data.add(new Statistika(RS1.getString(1),RS.getInt(2)));
                
                }
            }
            
           

          
            naziv.setCellValueFactory(new PropertyValueFactory<Statistika, String>("id_proizvoda"));
            kolicina.setCellValueFactory(new PropertyValueFactory<Statistika, String>("kolicina"));
            
            

            statistikaTablica.setItems(data);
            
            PS.close();
            RS.close();
        } catch (Exception e) {
            System.out.println("Set Table Method");
            System.out.println(e);
        }
    }
     
     public void kupciKol() throws SQLException{
         
         int count=0;
         String sql = "SELECT * FROM `kupci`";
         PS = (PreparedStatement) conn.prepareStatement(sql);
         RS = PS.executeQuery();
         
         while (RS.next()) {
             count++;
             
         }
         brojkupaca.setText(Integer.toString(count));
         
     }
     
     public void proizvodjaciKol() throws SQLException{
         
         int count=0;
         String sql = "SELECT * FROM `proizvodjaci`";
         PS = (PreparedStatement) conn.prepareStatement(sql);
         RS = PS.executeQuery();
         
         while (RS.next()) {
             count++;
             
         }
         brojproizvodjaca.setText(Integer.toString(count));
     }
     
     public void odobreneNarudzbe() throws SQLException{
         
         int count=0;
          String sql = "SELECT * FROM `narudzbe` WHERE odobreno='da'";
         PS = (PreparedStatement) conn.prepareStatement(sql);
         RS = PS.executeQuery();
         
         while (RS.next()) {
             count++;
             
         }
         brojodobrenih.setText(Integer.toString(count));
     }
     
     public void neodobreneNarudzbe() throws SQLException{
         
         int count=0;
         String sql = "SELECT * FROM `narudzbe` WHERE odobreno='ne'";
         PS = (PreparedStatement) conn.prepareStatement(sql);
         RS = PS.executeQuery();
         
         while (RS.next()) {
             count++;
             
         }
         brojneodobrenih.setText(Integer.toString(count));
     }

    
}
