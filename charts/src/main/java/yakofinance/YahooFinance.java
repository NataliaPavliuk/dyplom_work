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

public class YahooFinance extends Application
{ @Override
public void start(Stage stage) throws IOException, ParseException { stage.setTitle("Charts");
    Parser parser =new Parser();
    List<Data> pep= parser.parse("src/main/resources/PEP.csv");
    List<Data> ko= parser.parse("src/main/resources/KO.csv");
    List<Data> mnst= parser.parse("src/main/resources/MNST.csv");
    ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList
                    ( new PieChart.Data("A", 13),
                            new PieChart.Data("B",  8),
                            new PieChart.Data("C",  6)
                    );
    final PieChart pieChart = new PieChart(pieChartData);
    pieChart.setLegendVisible(true);
    pieChart.setTitle("PieChart");
    //-------------------------------------------------------------
    XYChart.Series<Number, Number> sF = new XYChart.Series<Number, Number>();
    XYChart.Series<Number, Number> sG = new XYChart.Series<Number, Number>();
    XYChart.Series<Number, Number> sH = new XYChart.Series<Number, Number>();
    sF.setName("Pepsi");
    sG.setName("Coca-cola");
    sH.setName("Monster");
    int k=0;
    for (Data value : pep) {
        sF.getData().add(new XYChart.Data(k, value.getCurrency()));
        k += 25;
    }
    k=0;
    for (Data value : ko) {
        sG.getData().add(new XYChart.Data(k, value.getCurrency()));
        k += 25;
    }
    k=0;
    for (Data value : mnst) {
        sH.getData().add(new XYChart.Data(k, value.getCurrency()));
        k += 25;
    }
    final NumberAxis xAxisArea = new NumberAxis(-1, 200, 25);
    final NumberAxis yAxisArea = new NumberAxis(40, 170, 10);
    final AreaChart<Number, Number> areaChart =
            new AreaChart<Number, Number>(xAxisArea, yAxisArea);
    xAxisArea.setTickLabelFill(Color.WHITE);
    yAxisArea.setTickLabelFill(Color.GREEN);
    areaChart.getXAxis().setStyle("-fx-border-color: Red transparent transparent; -fx-border-width:5");
    areaChart.getYAxis().setStyle("-fx-border-color: transparent Green transparent transparent; -fx-border-width:5");
    areaChart.getData().addAll(sF, sG, sH);
    areaChart.setTitle("AreaChart");
    //------------------------------------------------------
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis   yAxis = new   NumberAxis();
    final BarChart<String,Number> barChart =
            new BarChart<String,Number>(xAxis,yAxis);
    barChart.setTitle("BarChart");
    XYChart.Series s1 = new XYChart.Series();
    s1.setName("Pepsi");
    for (Data data: pep) {
        s1.getData().add(new XYChart.Data(data.getDate().toString(), data.getCurrency()));
    }

    XYChart.Series s2 = new XYChart.Series();
    s2.setName("Coca-cola");
    for (Data data: ko) {
        s2.getData().add(new XYChart.Data(data.getDate().toString(), data.getCurrency()));
    }

    XYChart.Series s3 = new XYChart.Series();
    s3.setName("Monster");
    for (Data data: mnst) {
        s3.getData().add(new XYChart.Data(data.getDate().toString(), data.getCurrency()));
    }

    barChart.getData().addAll(s1, s2, s3);//
    //-----------------------------------------------------------

    BubbleChart<Number, Number> bubbleChart = new BubbleChart<>
            (new NumberAxis(0, 5, 5), new NumberAxis(0, 5, 1));
    bubbleChart.setTitle("BubbleChart");

    XYChart.Series sb1 = new XYChart.Series();
    sb1.setName("name of chart 1");
    sb1.getData().add(new XYChart.Data(0.5, 4.5, 0.4));
    sb1.getData().add(new XYChart.Data(  2,   3, 1.8));
    sb1.getData().add(new XYChart.Data(  4,   1, 0.8));

    XYChart.Series sb2 = new XYChart.Series();
    sb2.setName("name of chart 2");
    sb2.getData().add(new XYChart.Data(1, 0, 0.7));
    sb2.getData().add(new XYChart.Data(3, 2, 0.5));
    sb2.getData().add(new XYChart.Data(5, 4, 0.3));

    bubbleChart.getData().addAll(sb1, sb2);

    ScatterChart scatterChart = new ScatterChart(new NumberAxis(150, 200, 10), new NumberAxis(150, 200, 10));

    XYChart.Series dataSeries1 = new XYChart.Series();
    dataSeries1.setName("Pepsi");

    for(int i=0; i<pep.size()-1;i++) {
        dataSeries1.getData().add(new XYChart.Data(pep.get(i).getCurrency(), pep.get(i++).getCurrency()));
    }

    scatterChart.getData().add(dataSeries1);

    ScatterChart scatterChart1 = new ScatterChart(new NumberAxis(150, 180, 10), new NumberAxis(50, 70, 10));
    scatterChart1.setTitle("ScatterChart");
    XYChart.Series dataSeries2 = new XYChart.Series();
    dataSeries2.setName("Pepsi and Coca-cola");

    for(int i=0; i<pep.size();i++) {
        dataSeries2.getData().add(new XYChart.Data(pep.get(i).getCurrency(), ko.get(i).getCurrency()));
    }

    scatterChart1.getData().add(dataSeries2);


    HBox hBox = new HBox();
    hBox.getChildren().addAll(areaChart,barChart, scatterChart1);
    hBox.setSpacing(4);
    hBox.setPadding(new Insets(5,5,5,5));
    hBox.autosize();
    Scene scene = new Scene(hBox, 1200, 350);
    scene.getStylesheets().add("Charts.css");
    stage.setScene(scene);
    stage.show();
}
    public static void main(String[] args) {launch(args);}
}