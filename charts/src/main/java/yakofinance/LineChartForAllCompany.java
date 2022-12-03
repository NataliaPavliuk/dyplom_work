package yakofinance;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LineChartForAllCompany extends Application {

        @Override public void start(Stage stage) throws IOException, ParseException {
            Parser parser =new Parser();
            List<Data> pep= parser.parse("src/main/resources/PEP.csv");
            List<Data> ko= parser.parse("src/main/resources/KO.csv");
            List<Data> mnst= parser.parse("src/main/resources/MNST.csv");
            List<Data> kdp= parser.parse("src/main/resources/KDP.csv");
            stage.setTitle("Порівняння усіх компаній по коректованих цінах закриття акцій");
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Month");
            final LineChart<String,Number> lineChart =
                    new LineChart<String,Number>(xAxis,yAxis);

            lineChart.setTitle("Stock Monitoring, 2021");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Pepsi");
            String mon="Oct";
            for (Data value : pep) {
                SimpleDateFormat month = new SimpleDateFormat("MMM", Locale.US);
                String currentMonth = month.format(value.getDate());
                if (!currentMonth.equals(mon)) {
                    series1.getData().add(new XYChart.Data(currentMonth, value.getCurrency()));
                    mon=currentMonth;
                }
            }

            XYChart.Series series2 = new XYChart.Series();
            series2.setName("Coca-cola");
            mon="Oct";
            for (Data value : ko) {
                SimpleDateFormat month = new SimpleDateFormat("MMM", Locale.US);
                String currentMonth = month.format(value.getDate());
                if (!currentMonth.equals(mon)) {
                    series2.getData().add(new XYChart.Data(currentMonth, value.getCurrency()));
                    mon=currentMonth;
                }
            }

            XYChart.Series series3 = new XYChart.Series();
            series3.setName("Monster");
            mon="Oct";
            for (Data value : mnst) {
                SimpleDateFormat month = new SimpleDateFormat("MMM", Locale.US);
                String currentMonth = month.format(value.getDate());
                if (!currentMonth.equals(mon)) {
                    series3.getData().add(new XYChart.Data(currentMonth, value.getCurrency()));
                    mon=currentMonth;
                }
            }


            XYChart.Series series4 = new XYChart.Series();
            series4.setName("Dr.Pepper");
            mon="Nov";
            for (Data value : kdp) {
                SimpleDateFormat month = new SimpleDateFormat("MMM", Locale.US);
                String currentMonth = month.format(value.getDate());
                if (!currentMonth.equals(mon)) {
                    series4.getData().add(new XYChart.Data(currentMonth, value.getCurrency()));
                    mon=currentMonth;
                }
            }

            Scene scene  = new Scene(lineChart,800,600);
            lineChart.getData().addAll(series1, series2, series3, series4);

            stage.setScene(scene);
            stage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }
    }

