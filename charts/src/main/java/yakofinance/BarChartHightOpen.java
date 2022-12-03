package yakofinance;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class BarChartHightOpen extends Application
{ @Override
public void start(Stage stage) throws IOException, ParseException { stage.setTitle("Charts");
    Parser parser =new Parser();
    List<Data> pep= parser.parse("src/main/resources/PEP.csv");
    List<Data> ko= parser.parse("src/main/resources/KO.csv");
    List<Data> mnst= parser.parse("src/main/resources/MNST.csv");
    List<Data> kdp = parser.parse("src/main/resources/KDP.csv");
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis   yAxis = new   NumberAxis();
    final BarChart<String,Number> barChart =
            new BarChart<String,Number>(xAxis,yAxis);
    barChart.setTitle("BarChart open-hight");
    XYChart.Series s1 = new XYChart.Series();
    s1.setName("Pepsi Open");
    XYChart.Series s2 = new XYChart.Series();
    s2.setName("Pepsi Hight");
    int i=0;
        s1.getData().add(new XYChart.Data(String.valueOf(i), pep.get(0).getCurrency()));
        s2.getData().add(new XYChart.Data(String.valueOf(i++), pep.get(0).getHight()));

    XYChart.Series s3 = new XYChart.Series();
    s3.setName("KDP Open");
    XYChart.Series s4 = new XYChart.Series();
    s4.setName("KDP Hight");
        s3.getData().add(new XYChart.Data(String.valueOf(i), kdp.get(0).getCurrency()));
        s4.getData().add(new XYChart.Data(String.valueOf(i++), kdp.get(0).getHight()));

    XYChart.Series s5 = new XYChart.Series();
    s5.setName("Koka-kola Open");
    XYChart.Series s6 = new XYChart.Series();
    s6.setName("Koka-kola Hight");
        s5.getData().add(new XYChart.Data(String.valueOf(i), ko.get(0).getCurrency()));
        s6.getData().add(new XYChart.Data(String.valueOf(i++), ko.get(0).getHight()));

    XYChart.Series s7 = new XYChart.Series();
    s7.setName("Monster Open");
    XYChart.Series s8 = new XYChart.Series();
    s8.setName("Monster Hight");
        s7.getData().add(new XYChart.Data(String.valueOf(i), mnst.get(0).getCurrency()));
        s8.getData().add(new XYChart.Data(String.valueOf(i++), mnst.get(0).getHight()));

    barChart.getData().addAll(s1, s2, s3, s4, s5, s6, s7, s8);//

    HBox hBox = new HBox();
    hBox.getChildren().addAll(barChart);
    hBox.autosize();
    Scene scene = new Scene(hBox, 600, 600);
    stage.setScene(scene);
    stage.show();
}
    public static void main(String[] args) {launch(args);}
}
