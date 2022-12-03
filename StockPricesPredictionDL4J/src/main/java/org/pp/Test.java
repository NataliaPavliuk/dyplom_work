package org.pp;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.nd4j.evaluation.regression.RegressionEvaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.BaseDatasetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.pp.data.CustomDataPrePreprocessor;
import org.pp.data.StockCSVDataSetFetcher;

import java.io.File;

@Slf4j
public class Test {

    static int inpNum = 50;
    static int outNum = 25;

    static CustomDataPrePreprocessor normalizer = new CustomDataPrePreprocessor();

    @SneakyThrows
    public static void main(String[] args) {
        File csvFile = new File("KO.csv");

        MultiLayerNetwork network = MultiLayerNetwork.load(new File("networkKO.zip"), true);
        network.init();

        StockCSVDataSetFetcher dataSetFetcher = new StockCSVDataSetFetcher(csvFile, inpNum, outNum);

        BaseDatasetIterator datasetIterator = new BaseDatasetIterator(1, dataSetFetcher.totalExamples(), new StockCSVDataSetFetcher(csvFile, inpNum, outNum));
        normalizer.fit(datasetIterator);
        datasetIterator.setPreProcessor(normalizer);


        DataSet dataSet = datasetIterator.next();

        INDArray output = predictSteps(network, dataSet.getFeatures(), outNum);

        RegressionEvaluation regressionEvaluation = new RegressionEvaluation();
        regressionEvaluation.eval(dataSet.getLabels(), output);

        log.info("\n"+regressionEvaluation.stats());

        normalizer.revert(dataSet);
        output = normalizer.revert(output);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(generateExpectedXYSeries(dataSet));
        dataset.addSeries(generateOutputXYSeries(output));


        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "KO",
                "Days",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);


        int width = 640;
        int height = 480;
        File XYChart = new File( "TestKO.png" );
        ChartUtilities.saveChartAsPNG(XYChart, xylineChart, width, height);
    }

    private static INDArray predictSteps(MultiLayerNetwork network, INDArray input, int steps){
        INDArray tempInput = input.dup();
        INDArray stepsValues = Nd4j.create(1, steps);

        double outputNorm = 0.0;

        for (int i = 0; i < steps; i++) {
            if (i == 0)
                outputNorm = calcOutputNorm(network, tempInput);

            double output = network.output(tempInput).getDouble(0, 0) - outputNorm;
            //double output = network.output(tempInput).getDouble(0, 0);

            stepsValues.putScalar(0, i, output);

            for (int j = 0; j < inpNum-1; j++)
                tempInput.putScalar(0, j,0, tempInput.getDouble(0, j+1, 0));

            tempInput.putScalar(0,inpNum-1,0, output);
        }
        return stepsValues;
    }

    private static double calcOutputNorm(MultiLayerNetwork network, INDArray input){
        return Math.round(Math.abs(network.output(input).getDouble(0, 0) - input.getDouble(0, inpNum - 1, 0)) * 100.0) / 100.0;
    }

    private static XYSeries generateExpectedXYSeries(DataSet dataSet){
        XYSeries expectedSeries = new XYSeries("Expected");

        INDArray expInput = dataSet.getFeatures();
        INDArray expOutput= dataSet.getLabels();

        for (int i = 0; i < inpNum; i++)
            expectedSeries.add(i, expInput.getDouble(0, i, 0));
        for (int i = 0; i < outNum; i++)
            expectedSeries.add(i+(inpNum-1), expOutput.getDouble(0, i));

        return expectedSeries;
    }

    private static XYSeries generateOutputXYSeries(INDArray expOutput){
        XYSeries expectedSeries = new XYSeries("Predicted");

        for (int i = 0; i < outNum; i++)
            expectedSeries.add(i+(inpNum-1), expOutput.getDouble(0, i));

        return expectedSeries;
    }
}
