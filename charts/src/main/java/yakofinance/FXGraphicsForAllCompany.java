package yakofinance;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class FXGraphicsForAllCompany extends Application {

    static class ChartCanvas extends Canvas {

        final JFreeChart chart;

        private final FXGraphics2D g2;

        public ChartCanvas(JFreeChart chart) {
            this.chart = chart;
            this.g2 = new FXGraphics2D(getGraphicsContext2D());
            widthProperty().addListener(e -> draw());
            heightProperty().addListener(e -> draw());
        }

        private void draw() {
            double width = getWidth();
            double height = getHeight();
            getGraphicsContext2D().clearRect(0, 0, width, height);
            this.chart.draw(this.g2, new Rectangle2D.Double(0, 0, width,
                    height));
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) { return getWidth(); }

        @Override
        public double prefHeight(double width) { return getHeight(); }
    }

    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Moving Average for 50 days",
                null,
                "US",
                dataset,true,true,true);

        String fontName = "Palatino";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(false);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font(fontName, Font.PLAIN, 14));
        chart.getLegend().setFrame(BlockBorder.NONE);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, new Color(14, 123, 108));
            renderer.setSeriesPaint(2, new Color(94, 251, 106));
            renderer.setSeriesPaint(3, new Color(11, 162, 209));
        }

        return chart;

    }

    private static XYDataset createDataset() throws IOException, ParseException {
        Parser parser =new Parser();
        List<Data> pep= parser.parse("src/main/resources/PEP.csv");
        List<Data> ko= parser.parse("src/main/resources/KO.csv");
        List<Data> mnst= parser.parse("src/main/resources/MNST.csv");
        List<Data> kdp= parser.parse("src/main/resources/KDP.csv");

        TimeSeries s1 = new TimeSeries("Adj Close");
        for (Data value:pep
             ) {
            s1.addOrUpdate(new Day(value.getDate()), value.getCurrency());
        }

        TimeSeries s2 = MovingAverage.createMovingAverage(s1,"Pepsi", 50, 0);

        TimeSeries s3 = new TimeSeries("Adj Close");
        for (Data value:ko
        ) {
            s3.addOrUpdate(new Day(value.getDate()), value.getCurrency());
        }
        TimeSeries s4 = MovingAverage.createMovingAverage(s3,"Coca-cola", 50, 0);

        TimeSeries s5 = new TimeSeries("Adj Close");
        for (Data value:mnst
        ) {
            s5.addOrUpdate(new Day(value.getDate()), value.getCurrency());
        }
        TimeSeries s6 = MovingAverage.createMovingAverage(s5,"Monster", 50, 0);

        TimeSeries s7 = new TimeSeries("Adj Close");
        for (Data value:kdp
        ) {
            s7.addOrUpdate(new Day(value.getDate()), value.getCurrency());
        }
        TimeSeries s8 = MovingAverage.createMovingAverage(s7,"Dr.Pepper", 50, 0);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s2);
        dataset.addSeries(s4);
        dataset.addSeries(s6);
        dataset.addSeries(s8);
        return dataset;
    }

    @Override
    public void start(Stage stage) throws IOException, ParseException {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartCanvas canvas = new ChartCanvas(chart);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(canvas);
        canvas.widthProperty().bind( stackPane.widthProperty());
        canvas.heightProperty().bind( stackPane.heightProperty());
        stage.setScene(new Scene(stackPane));
        stage.setTitle("Moving Average for All Company.java");
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}