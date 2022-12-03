package charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Charts extends Application
{ @Override
public void start(Stage stage)
{ stage.setTitle("Charts");

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
    sF.setName("   f   ");
    sG.setName("   g   ");
    sH.setName("   h   ");
    sF.getData().add(new XYChart.Data(0,  5));
    sF.getData().add(new XYChart.Data(3,  1));
    sF.getData().add(new XYChart.Data(6,  3));
    sF.getData().add(new XYChart.Data(9,  2));
    sF.getData().add(new XYChart.Data(12, 4));

    sG.getData().add(new XYChart.Data(0, 1));
    sG.getData().add(new XYChart.Data(3, 4));
    sG.getData().add(new XYChart.Data(6, 2));
    sG.getData().add(new XYChart.Data(9, 5));
    sG.getData().add(new XYChart.Data(12,1));

    sH.getData().add(new XYChart.Data(0, 1));
    sH.getData().add(new XYChart.Data(3, 2));
    sH.getData().add(new XYChart.Data(6, 3));
    sH.getData().add(new XYChart.Data(9, 4));
    sH.getData().add(new XYChart.Data(12,5));
    final NumberAxis xAxisArea = new NumberAxis
            (-1, 13, 2);
    final NumberAxis yAxisArea = new NumberAxis();
    final AreaChart<Number, Number> areaChart =
            new AreaChart<Number, Number>(xAxisArea, yAxisArea);
    xAxisArea.setTickLabelFill(Color.RED);
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
    s1.setName("   X   ");
    s1.getData().add(new XYChart.Data("2020", 2560));
    s1.getData().add(new XYChart.Data("2021", 2676));

    XYChart.Series s2 = new XYChart.Series();
    s2.setName("   Y   ");
    s2.getData().add(new XYChart.Data("2020", 2027));
    s2.getData().add(new XYChart.Data("2021", 1900));

    XYChart.Series s3 = new XYChart.Series();
    s3.setName("   Z   ");
    s3.getData().add(new XYChart.Data("2020", 2456));
    s3.getData().add(new XYChart.Data("2021", 2567));

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

    HBox hBox = new HBox();
    hBox.getChildren().addAll(pieChart,areaChart,barChart,bubbleChart);
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