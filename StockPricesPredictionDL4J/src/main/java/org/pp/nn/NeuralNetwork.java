package org.pp.nn;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NeuralNetwork {
    //create the neural network
    public static MultiLayerNetwork getNetModel(int inputNum, int outNum) {
        int hiddenLayerNum = 100;

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .trainingWorkspaceMode(WorkspaceMode.ENABLED).inferenceWorkspaceMode(WorkspaceMode.ENABLED)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                //.seed(1630946263486L)
                .updater(new RmsProp(0.0001))
                .list()

                .layer(new LSTM.Builder().name("LSTM1")
                        .activation(Activation.TANH)
                        .nIn(inputNum)
                        .nOut(hiddenLayerNum).build())

                .layer(new LSTM.Builder().name("LSTM2")
                        .activation(Activation.TANH)
                        .nIn(hiddenLayerNum)
                        .nOut(hiddenLayerNum).build())

                .layer(new LSTM.Builder().name("LSTM3")
                        .activation(Activation.TANH)
                        .nIn(hiddenLayerNum)
                        .nOut(hiddenLayerNum).build())

                .layer(new DenseLayer.Builder().name("Dense1")
                        .activation(Activation.IDENTITY)
                        .nIn(hiddenLayerNum)
                        .nOut(hiddenLayerNum).build())

                .layer(new DenseLayer.Builder().name("Dense2")
                        .activation(Activation.IDENTITY)
                        .nIn(hiddenLayerNum)
                        .nOut(hiddenLayerNum).build())

                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(hiddenLayerNum)
                        .nOut(outNum).build())
                .build();

        return new MultiLayerNetwork(conf);
    }
}
