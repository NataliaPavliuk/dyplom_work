package org.pp.component;

import org.deeplearning4j.core.storage.StatsStorage;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.PerformanceListener;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.model.stats.StatsListener;
import org.deeplearning4j.ui.model.storage.FileStatsStorage;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public final class UIServerComponent {

    private final UIServer uiServer;
    private final StatsListener statsListener;
    private final PerformanceListener performanceListener;
    private MultiLayerNetwork currentNetwork;

    public UIServerComponent() {
        StatsStorage statsStorage = new FileStatsStorage(new File("ui-stats.dat"));
        statsListener = new StatsListener(statsStorage);
        performanceListener = new PerformanceListener(100, true);

        uiServer = UIServer.getInstance();
        uiServer.attach(statsStorage);
        uiServer.enableRemoteListener();
    }

    private final boolean useUI = true;

    public void reinitialize(MultiLayerNetwork network) {
        if (useUI) {
            if (currentNetwork != null) {
                currentNetwork.getListeners().remove(statsListener);
                currentNetwork.getListeners().remove(performanceListener);
            }
            if (network != null)
                network.addListeners(statsListener, performanceListener);

            currentNetwork = network;
            System.gc();
        }
    }


    public void stop() throws InterruptedException {
        if (useUI)
            uiServer.stop();
    }
}
