/**
 *
 * @author Muhammad Ali Arafah
 *    https://github.com/ZaTribune
 */
package shadow.dental_chart;

import com.jfoenix.controls.JFXScrollPane;
import java.io.File;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Base64;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static String ad="x";
    public static String soyad="x";
    public static String tc="x";
    public static String protokol="x";
     public static String port="x";
    public static String link="x";
     public static String kullid="x";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(DentalChartController.class.getSimpleName()), 1650, 768);
        stage.setScene(scene);
       stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/"+name + ".fxml"));
        /* I had performance and lag issues  using the usuall javafx ScrollPane specially after wrapping the chart content inside a Group
          ScrollPane
               -> Group
                         -> VBox
                                   -> ....chart content

        */
        MyScrollPane scrollPane=new MyScrollPane();
        scrollPane.setContent(fxmlLoader.load());        
        scrollPane.getStylesheets().add(App.class.getResource("/css/chart.css").toExternalForm());
        return scrollPane;
    }

    public static void main(String[] args) throws IOException {
      try {

    if (args.length > 0) {
if(args[0].length()>15) {
    String gelenstr = args[0].substring(args[0].lastIndexOf("//") + 2);
    if (containsChar(gelenstr,'/'))
        gelenstr=gelenstr.substring(0,gelenstr.indexOf('/'));
  byte[] decodedBytes = Base64.getDecoder().decode(gelenstr);
String decodedString = new String(decodedBytes);

    
   
    String[] parts = decodedString.split(" ");
    protokol=parts[0];
    link=parts[1];
    port=parts[2];
    kullid=parts[3];
    ad = parts[4];
    soyad = parts[5];
    tc = parts[6];
}
    }

    launch();
}
catch (Exception ex)
{
    showAlert(ex.getMessage());

    FileWriter fileWriter = new FileWriter("c:\\DentalChart\\Error.txt");
    PrintWriter printWriter = new PrintWriter(fileWriter);
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
Date date = new Date(System.currentTimeMillis());

    printWriter.print(date+" : "+ ex.getMessage());
    printWriter.close();
   // System.exit(0);
}
    }
    public static void showAlert(String message) {
    Platform.startup(new Runnable() {
      public void run() {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Hata...");
          alert.setHeaderText("Bu hata ilk Açılış hatası");
          alert.setContentText(message);
          alert.show();
      }
    });
    }
      public static void showAlertOut(String message) {
    Platform.runLater(new Runnable() {
      public void run() {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Hata...");
          alert.setHeaderText("Bu hata Servis Bağlantı Hatasi");
          alert.setContentText(message);
          alert.showAndWait();
      }
    });
}
    public static boolean containsChar(String s, char search) {
    if (s.length() == 0)
        return false;
    else
        return s.charAt(0) == search || containsChar(s.substring(1), search);
}
  
}