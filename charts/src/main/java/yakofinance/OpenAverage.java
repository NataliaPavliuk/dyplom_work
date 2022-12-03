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

public class OpenAverage extends Application
{ @Override
public void start(Stage stage) throws IOException, ParseException { stage.setTitle("Charts");
    Parser parser =new Parser();
    List<Data> pep= parser.parse("src/main/resources/PEP.csv");
    List<Data> ko= parser.parse("src/main/resources/KO.csv");
    List<Data> mnst= parser.parse("src/main/resources/MNST.csv");
    List<Data> kdp = parser.parse("src/main/resources/KDP.csv");
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> barChart =
            new BarChart<String,Number>(xAxis,yAxis);
    barChart.setTitle("BarChart open");
    XYChart.Series s1 = new XYChart.Series();
    s1.setName("Pepsi Open");
    int i=0;
    double sum=0;
    s1.getData().add(new XYChart.Data(String.valueOf(i), pep.get(0).getCurrency()));
    sum+=pep.get(0).getCurrency();
    XYChart.Series s3 = new XYChart.Series();
    s3.setName("KDP Open");
    s3.getData().add(new XYChart.Data(String.valueOf(i++), kdp.get(0).getCurrency()));
    sum+=kdp.get(0).getCurrency();
    XYChart.Series s5 = new XYChart.Series();
    s5.setName("Koka-kola Open");
    s5.getData().add(new XYChart.Data(String.valueOf(i++), ko.get(0).getCurrency()));
    sum+=ko.get(0).getCurrency();
    XYChart.Series s7 = new XYChart.Series();
    s7.setName("Monster Open");
    s7.getData().add(new XYChart.Data(String.valueOf(i++), mnst.get(0).getCurrency()));
    sum+=mnst.get(0).getCurrency();
    XYChart.Series<Number, Number> sF = new XYChart.Series<Number, Number>();
    sF.setName("Open Average");
    for (int j = 0; j <i ; j++) {
        sF.getData().add(new XYChart.Data(j, sum));
    }
    final NumberAxis xAxisArea = new NumberAxis(0, i, 1);
    final AreaChart<Number, Number> areaChart =
            new AreaChart<Number, Number>(xAxisArea, yAxis);
    xAxisArea.setTickLabelFill(Color.WHITE);
    areaChart.getData().addAll(sF);
    barChart.getData().addAll(s1, s3, s5, s7);
    areaChart.setTitle("AreaChart");

    HBox hBox = new HBox();
    hBox.getChildren().addAll(barChart, areaChart);
    hBox.autosize();
    Scene scene = new Scene(hBox, 600, 600);
    stage.setScene(scene);
    stage.show();
}
    public static void main(String[] args) {launch(args);}
}

