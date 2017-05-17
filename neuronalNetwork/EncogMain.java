package rondel.marc.antoine.neuronalNetwork;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.MLMethod;
import org.encog.ml.MLResettable;
import org.encog.ml.MethodFactory;
import org.encog.ml.genetic.MLMethodGeneticAlgorithm;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;

/**
 *
 * MainClass qui permet d'entrainer les différents réseaux neuronnales.
 * Les poids des neurones sont enregistrés dans un fichier pour ensuite être réutilisé pour
 * l' Ia de rondel.marc.antoine.pong
 *
 **/

public class EncogMain  {

    //Crétation du réseau de neurone pour l'algo neuronal entrainé avec un algo génétique
    public static BasicNetwork createNetworkG()
    {
        FeedForwardPattern pattern = new FeedForwardPattern();
        pattern.setInputNeurons(5);
        pattern.addHiddenLayer(20);


        pattern.setOutputNeurons(1);
        pattern.setActivationFunction(new ActivationSigmoid());
        BasicNetwork network = (BasicNetwork)pattern.generate();
        network.reset();
        return network;

    }


    public static void geneticAlgo(){



        MLTrain train;

        train = new MLMethodGeneticAlgorithm(new MethodFactory(){
            @Override
            public MLMethod factor() {
                final BasicNetwork result = createNetworkG();
                ((MLResettable)result).reset();
                return result;
            }},new PongScore(),200);


        int epoch = 1;

        // Commence l'algorithme génétique pour trouver les poids pour l'algorithme neuronal
        while(train.getError()<500) {
            train.iteration();
             epoch++;
            System.out.println("epoch "+epoch +" error "+train.getError());

        }
        BasicNetwork mlm =(BasicNetwork) train.getMethod();


        //mlm.getWeight(0, fromNeuron, toNeuron)
        int c=0;
        int da=0;
        train.finishTraining();

        double[] data = new double[5*20+20];

        for(int id0=0;id0<5;id0++)
            for(int id1=0;id1<20;id1++){

                data[c]=mlm.getWeight(0, id0, id1);
                c++;da++;
            }
        for(int id0=0;id0<20;id0++)
            for(int id1=0;id1<1;id1++){
                data[c]=	mlm.getWeight(1, id0, id1);
                c++;
            }



        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;





        File f = new File ("NeuroneWeight " +train.getError()+" date " + dateFormat.format(date));

        try
        {

            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(  f)));


            for (double d : data)
            {
                dos.writeDouble(d);
            }


            dos.close();
        }
        catch (IOException exception)
        {
            System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
        }

        Encog.getInstance().shutdown();

    }
    public static void main(String _args[])
    {

        geneticAlgo();


    }

}
