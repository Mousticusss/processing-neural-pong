package rondel.marc.antoine.neuronalNetwork;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import rondel.marc.antoine.pong.Ball;
import rondel.marc.antoine.pong.Constants;
import rondel.marc.antoine.pong.Racket;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class qui simule des parties pour calculer le score du réeseau neuronal
 */
public class NeuralPlayerPong {

    private BasicNetwork network;

    private NormalizedField positionX;
    private NormalizedField velocityX;
    private NormalizedField positionY;
    private NormalizedField velocityY;

    public NeuralPlayerPong(BasicNetwork network)
    {
        // Nomralisaiton des valeurs pour faciliter l'apprentissage du réseau neuronale
        positionX = new NormalizedField(NormalizationAction.Normalize, "positionX", Constants.SIZE_X, 0, 1, 0);
        positionY = new NormalizedField(NormalizationAction.Normalize, "positionY", Constants.SIZE_Y, 0, 1, 0);
        velocityX = new NormalizedField(NormalizationAction.Normalize, "velocityX", 5,-5, 1, 0);
        velocityY = new NormalizedField(NormalizationAction.Normalize, "velocityY",5, -5, 1,0);
        this.network = network;
    }



    public int scorePlayer()
    {

        // Création des objets
        ArrayList<Racket> rackets=new ArrayList<>();
        Ball     ball = new Ball( Constants.BALL_POS, Constants.SIZE_BALL);
        rackets.add(new Racket(ball, new Point(800/10,600/2),  20, 50,0));
        rackets.add(new Racket(ball,new Point(800-800/10,600/2),  20, 50,1));

        int score=0;
        int time=0;
        //Joue pendant 1 heure (frames*60secondes *60min)
        while(time<50*60*60){
        time++;


            int prevPosX=ball.getPos().x;
            int prevPosY=ball.getPos().y;
            int loose=ball.applyPhysic();
            int dirX=ball.getPos().x -prevPosX;
            int dirY=ball.getPos().y -prevPosY;


            //Vérifie si la balle a touché une raquette;
            for (Racket r: rackets) {

                if(  r.checkRacketBonce()){

                    score++;
                }



            }


        // Arrete la boucle si l'un des joueurs perd.
            if(loose==1)
               break;
            if(loose==2)
                break;


               //Nourrit le réseu neuronal avec les informations du jeu.
            MLData input = new BasicMLData(5);
            input.setData(0, this.positionX.normalize(ball.getPos().x));
            input.setData(1, this.positionY.normalize(ball.getPos().y));
            input.setData(2, this.positionY.normalize(rackets.get(1).getPosRacket().y));
            input.setData(3, this.velocityX.normalize(dirX));
            input.setData(4, this.velocityY.normalize(dirY));
            MLData output = this.network.compute(input);
            double value = output.getData(0);




            //En fonction de l'output du réseau neuronal la raquette montera ou descendra
            if(value>0.5)
               rackets.get(1).upRacket();
            else if(value<0.5)
             rackets.get(1).downRacket();


            int r= -15+(int)(Math.random()*((15) + 1));
            rackets.get(0).getPosRacket().y=ball.getPos().y+r;


    if(rackets.get(0).getScore()+5<rackets.get(1).getScore())
        time=50*60*60;

    }

        // Le réseau neuronal va utiliser la moindre faille pour augmenter son score, pour le coup c'est de rebondir avec le mur
        //on exclut donc ces résultats en prenant le score de l'autre racket
       // System.out.println(rackets.get(0).getScore() + " racket 1 " +rackets.get(1).getScore());
        return rackets.get(0).getScore() ;//rackets.get(1).getScore() ;

    }




}
