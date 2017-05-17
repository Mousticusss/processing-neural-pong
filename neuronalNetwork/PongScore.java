package rondel.marc.antoine.neuronalNetwork;

import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.neural.networks.BasicNetwork;

/**
 * Created by Marcus on 11/05/2016.
 */

/***
 * Class pour renvoyer le calcul des scores pour l'algorithme génétique
  */
public class PongScore implements CalculateScore {

    @Override
    public double calculateScore(MLMethod network) {
        NeuralPlayerPong player = new NeuralPlayerPong((BasicNetwork)network);
        return player.scorePlayer();
    }

    @Override
    public boolean shouldMinimize() {
        return false;
    }


    @Override
    public boolean requireSingleThreaded() {
        return false;
    }
}
