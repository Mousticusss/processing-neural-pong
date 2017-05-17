package rondel.marc.antoine.pong;


import processing.core.PApplet;

import java.awt.*;

/***
 * Class qui crée un objet ball
 *
 */


public class Ball {

    PApplet parent;


    Point posBall;
    int ballSize;
    private int velocityBallY=1;
    private int velocityBallX=1;

    //Constructeur pour réseau neuronale
    public Ball(Point posBall, int ballSize){

        // Paramètre mis en dur pour eviter des erreur (la variable point n'était pas toujours présente)
        this.posBall= new Point(400,300);
        this.ballSize=ballSize;

    }


    public Ball(PApplet parent, Point posBall, int ballSize){

       this.parent=parent;
       this.posBall=posBall;
       this.ballSize=ballSize;

    }

    //Permet de faire avancer la balle;
    public int applyPhysic (){


        applyConstantVelocityX();
       applyConstantVelocityY();
      return  keepInScreen(Constants.SIZE_Y,Constants.SIZE_X);


    }


    public void drawBall() {
        parent.fill(200,40,40);
        parent.ellipse(posBall.x,posBall.y, ballSize, ballSize);
    }

    public void makeBounceTop(int surface) {
        posBall.y = surface+(ballSize/2);
        velocityBallY *=-1;
    }

    public void makeBounceBottom(int surface) {
        posBall.y = surface-(ballSize/2);
        velocityBallY *=-1;
    }


    public  void makeBounceLeft(int surface,int posRacketY){
        posBall.x = surface+(ballSize/2);
        velocityBallY= (posBall.y  - posRacketY)/5;

      //  System.out.println(" bonce Right  "  +  ((posBall.y  - posRacketY)/5) + " " );

       velocityBallX*=-1;

    }
    public  void makeBounceRight(int surface,int posRacketY){
        posBall.x = surface-(ballSize/2);
       velocityBallY= (posBall.y  - posRacketY)/5;
      //  System.out.println("Bonce Left  "  +  ((posBall.y  - posRacketY)/5) + "  ");

        velocityBallX*=-1;
    }


    //On vérifie que la balle ne sorte pas de l'écran.
    private int keepInScreen(int height, int width) {
        // ball hits floor
        if (posBall.y + (ballSize / 2) > height) {
            makeBounceBottom(height);
        }
        // ball hits ceiling
        if (posBall.y - (ballSize / 2) < 0) {
            makeBounceTop(0);
        }
        if (posBall.x-(ballSize/2) < 0){
            return 1;
        }
        if (posBall.x+(ballSize/2) > width){
           return 2;
        }

        return 0;
    }

    //
    private void  applyConstantVelocityX() {

        posBall.x += velocityBallX;
    }
    private void   applyConstantVelocityY() {

        posBall.y += velocityBallY;
    }

    public Point getPos() {
        return posBall;
    }



}
