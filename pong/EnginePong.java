package rondel.marc.antoine.pong;

import com.intellij.openapi.Disposable;
import rondel.marc.antoine.pong.Graphism.Graphism;
import rondel.marc.antoine.pong.Graphism.SourceFire;
import processing.core.PApplet;
import processing.event.MouseEvent;

import processing.event.KeyEvent;

import java.util.ArrayList;

/**
 * Moteur pour le jeu pong
 */
public class EnginePong  {
    Ball ball;
    ArrayList<Racket>rackets=new ArrayList<>();

     private  PApplet parent;
    boolean graphismOn;

    private Graphism graphic;
    private boolean keyPressUp=false;
    private boolean keyPressDown=false;
    SourceFire source;
    IAPlayer ia;
    private boolean pause=true;
    private double startTime=0;


    public EnginePong(PApplet parent,boolean graphismOn) {
        this.parent = parent;
    this.graphismOn=graphismOn;



        //Création de la balle, des raquettes.
        ball = new Ball(parent, Constants.BALL_POS, Constants.SIZE_BALL);
        rackets.add(new Racket(parent,ball, Constants.RACKET1_POS, Constants.RACKET_WIDTH, Constants.RACKET_HEIGHT));
        rackets.add(new Racket(parent,ball, Constants.RACKET2_POS, Constants.RACKET_WIDTH, Constants.RACKET_HEIGHT));

        if(graphismOn) {
            //Creation des graphismes et lien avec les objets balle et raquette
            graphic=new Graphism(parent,ball);
        }

       ia = new IAPlayer(rackets,ball);

        parent.registerMethod("keyEvent", this);


    }


    public void play() {
        int prevPosX=ball.getPos().x;
        int prevPosY=ball.getPos().y;



        //Envoie au réseau neuronal les anciennes positions pour qu'il prenne un décision


        if(graphismOn)
            graphic.draw();
        if(!pause){
            ia. takeDecision(prevPosX,prevPosY);

      int loose=  ball.applyPhysic();
        ball.drawBall();


         if(loose==1) {
            gameOver(2);
            rackets.get(0).setLoose(true);
            parent.noLoop();
        }
        else if(loose==2) {
            gameOver(1);
            rackets.get(1).setLoose(true);
            parent.noLoop();
        }
           for (Racket r: rackets) {
            r.checkRacketBonce();
            r.applyPhysic();
        }

        int r= -25+(int)(Math.random()*((25+25) + 1));



       if(keyPressDown)
           rackets.get(0).downRacket();
        if(keyPressUp)
            rackets.get(0).upRacket();
        }
        if(pause){
            parent.textSize(30);
            parent.textAlign(parent.CENTER);
            parent.fill(223,222,150);
            parent.text("Affrontez l'ia, pour monter la raquette \n appuyez sur haut, pour la descendre bas",parent.width/2,parent.height/2);


            if(startTime==0)
                startTime= parent.millis();
            if(parent.millis()>startTime+7000)
                pause=false;
        }

    }




    public void keyEvent(KeyEvent e) {

        if(KeyEvent.PRESS==e.getAction())
        {
             if (e.getKeyCode()== parent.DOWN)
                keyPressDown=true;
            if (e.getKeyCode()== parent.UP)
                keyPressUp=true;
        }

        if(KeyEvent.RELEASE==e.getAction())
        {
                keyPressDown=false;
                keyPressUp=false;
        }




    }

public void gameOver(int winner)
{

   parent. textSize(32);
    parent. fill(0, 102, 153);
    parent.textAlign(parent.CENTER);
    parent. text("GAME OVER Player "+ winner +"  WINNER", Constants.SIZE_X /2, Constants.SIZE_Y/2);


}

    void keyEvent() {
        System.out.print("keyEvent");
    }
}
