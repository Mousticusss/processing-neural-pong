package rondel.marc.antoine.pong;

import processing.core.PApplet;
import processing.core.PConstants;

import java.awt.*;

/**
 * Created by Marcus on 10/05/2016.
 */
public class Racket {
    private PApplet parent;
    private  Point posRacket;
    private  int widthRacket, heightRacket;
    private  int up=-4;
    private  int down=4;
    private  Ball ball;
    private  boolean blockUp=false;
    private boolean blockDown=false;
    private int id;

    private int score=0;
    private  boolean loose= false;


    /*


   Pout la création d'une raquette pour le réseau de neurones.


    */
    public Racket(Ball ball, Point posRacket, int widthRacket, int heightRacket, int num){


        if (num==1)
            this.posRacket=   new   Point(800-800/10,600/2);
        if (num==0)
            this.posRacket=   new   Point(800/10,600/2);

        this.ball=ball;
        //   this.posRacket=posRacket;
        this.widthRacket = widthRacket;
        this.heightRacket = heightRacket;

    }


    public Racket(PApplet parent, Ball ball, Point posRacket, int widthRacket, int heightRacket){

        this.parent=parent;
        this.ball=ball;
        this.posRacket=posRacket;
        this.widthRacket = widthRacket;
        this.heightRacket = heightRacket;

    }

    public Point getPosRacket() {
        return posRacket;
    }


    public void setPosRacket(Point posRacket) {
        this.posRacket = posRacket;
    }



    public int getScore() {
        return score;
    }

    public boolean isLoose() {
        return loose;
    }

    public void setLoose(boolean loose) {
        this.loose = loose;
    }

    public void applyPhysic(){
        drawRacket();

    }

    private void drawRacket(){
        parent.fill(245);
        parent.rectMode(PConstants.CENTER);
        parent.rect(posRacket.x,posRacket.y, widthRacket, heightRacket);

    }

    public void upRacket(){
        if(posRacket.y- heightRacket /2+up>0 && !blockUp)
            posRacket.y+=up;
    }

    public  void downRacket(){

        if(posRacket.y+ heightRacket /2+down<Constants.SIZE_Y&& !blockDown)
            posRacket.y+=down;


    }
    public boolean checkRacketBonce() {



        if ( posRacket.x  > (ball.posBall.x + (ball.ballSize / 2)) && (ball.posBall.x + (ball.ballSize / 2))  > posRacket.x - (widthRacket / 2) )

            if (  posRacket.y + heightRacket /2 > (ball.posBall.y-ball.ballSize/2) && (posRacket.y- heightRacket < ball.posBall.y+ball.ballSize/2  )){
                ball.makeBounceRight(posRacket.x - widthRacket / 2,posRacket.y);

                score++;
                return true;
            }
        if (  posRacket.x < (ball.posBall.x - (ball.ballSize / 2)) && (ball.posBall.x - (ball.ballSize / 2) < posRacket.x + (widthRacket / 2)))
            if (  posRacket.y + heightRacket /2 > (ball.posBall.y-ball.ballSize/2) && (posRacket.y- heightRacket < ball.posBall.y+ball.ballSize/2  ))
            {           ball.makeBounceLeft(posRacket.x + widthRacket / 2,posRacket.y);
                score++;

                return true;
            }

        if (  posRacket.y + heightRacket /2 >= (ball.posBall.y - (ball.ballSize / 2)) && (ball.posBall.y-ball.ballSize/2 > posRacket.y))
        {
            if (posRacket.x+ widthRacket /2>=ball.posBall.x && posRacket.x- widthRacket /2<= ball.posBall.x ) {
                ball.makeBounceTop(posRacket.y + heightRacket / 2);

                return true;
            }           }
        if (  posRacket.y - heightRacket /2 <= (ball.posBall.y + (ball.ballSize / 2)) && (ball.posBall.y+ball.ballSize/2 < posRacket.y))
        {
            if (posRacket.x+ widthRacket /2>=ball.posBall.x && posRacket.x- widthRacket /2<= ball.posBall.x ) {
                ball.makeBounceBottom(posRacket.y - heightRacket / 2);
                return true;
            }           }


        return false;

    }









}
