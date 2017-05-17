package rondel.marc.antoine.pong;

import rondel.marc.antoine.neuronalNetwork.EncogMain;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Marcus on 12/05/2016.
 */
public class IAPlayer {

    private   BasicNetwork network;
    private ArrayList<Racket> rackets;
    private Ball ball;
    private NormalizedField positionX;
    private NormalizedField velocityX;
    private NormalizedField positionY;
    private NormalizedField velocityY;

    IAPlayer(ArrayList<Racket> rackets,Ball ball){


        // Nomralisaiton des valeurs pour faciliter l'apprentissage du réseau neuronale
        positionX = new NormalizedField(NormalizationAction.Normalize, "positionX", Constants.SIZE_X, 0, 1, 0);
        positionY = new NormalizedField(NormalizationAction.Normalize, "positionY", Constants.SIZE_Y, 0, 1, 0);
        velocityX = new NormalizedField(NormalizationAction.Normalize, "velocityX", 5,-5, 1, 0);
        velocityY = new NormalizedField(NormalizationAction.Normalize, "velocityY",5, -5, 1,0);
        this.ball=ball;
        this.rackets=rackets;


         network = EncogMain.createNetworkG();
        ArrayList<Double> weights=extractWeights();
        int weight=0;

        //
       for(int i=0;i<5;i++)
         for(int j=0; j<20;j++)
         {
             network.setWeight(0,i,j,weights.get(weight));
             weight++;
         }

        for(int i=0;i<20;i++)
            for(int j=0; j<1;j++)
            {
                network.setWeight(1,i,j,weights.get(weight));
                weight++;
            }

    }

    public void takeDecision(int prevX, int prevY){
        int dirX=ball.getPos().x -prevX;
        int dirY=ball.getPos().y -prevY;
        MLData input = new BasicMLData(5);
        input.setData(0, this.positionX.normalize(ball.getPos().x));
        input.setData(1, this.positionY.normalize(ball.getPos().y));
        input.setData(2, this.positionY.normalize(rackets.get(0).getPosRacket().y));
        input.setData(3, this.velocityX.normalize(dirX));
        input.setData(4, this.velocityY.normalize(dirY));









        MLData output = network.compute(input);
        double value = positionY.deNormalize(output.getData(0));




        if(value>0.5)
            rackets.get(1).upRacket();
        else if(value<0.5)
            rackets.get(1).downRacket();



    }

    private  ArrayList<Double> extractWeights() {

        ArrayList<Double> weights = new ArrayList<>();
        try {


            InputStream is = getClass().getResourceAsStream("NeuroneWeight 1473.0 date 2016-07-05 04-56-18");

           // DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(is))));
            DataInputStream dis = new DataInputStream(is);

            while (dis.available() > 0) {

                double c = dis.readDouble();
                weights.add(c);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return weights;
    }
    }
