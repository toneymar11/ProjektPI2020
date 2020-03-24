
package pisproizvodjac;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author toni
 */
public class PISProizvodjac extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        
          //to make the window withOut borders
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setX(400);
        stage.setY(200);
        
        Scene scene = new Scene(root);
       
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
