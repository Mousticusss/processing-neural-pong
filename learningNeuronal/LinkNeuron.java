package rondel.marc.antoine.learningNeuronal;

import processing.core.PApplet;

import java.awt.*;

/**
 * Created by Marcus on 23/06/2016.
 */
public class LinkNeuron {
    private  PApplet p;
    private int r,g,b;


    private float dataX,dataY;
    private int startTime=0;
    private double  finalDistance;
    private double dist=0;
   private double angle=0;
   public boolean sendingData =false;
    public boolean hasSendingData =false;
    public   Neuron n1;
    public  Neuron n2;
    public double weight;

    public LinkNeuron(PApplet parent,int r,int g, int b, Neuron n1, Neuron n2){
        p=parent;
        this.r=r;
        this.g=g;
        this.b=b;
        this.n1=n1;
        this.n2=n2;
        dataX=n1.cX;
        dataY=n1.cY;
        weight=p.random(1);

        finalDistance=p.dist(n1.cX, n1.cY, n2.cX, n2.cY);
        angle= (Math.atan2(n2.cY - n1.cY, n2.cX - n1.cX));



    }

    public void printLink(){
        //Commence un nouveau style
        p.pushStyle();
        p.stroke(r,g,b);
         expandLine();
        p.stroke(0);
        //Restaure le style originale
        p.popStyle();

    }
    // Deplace des rond représentant les informations sur une ligne
    public void sendData(){


        if (sendingData && !hasSendingData) {
            dataX = p.lerp(dataX, n2.cX, 0.02f);
            dataY = p.lerp(dataY, n2.cY, 0.02f);


            p.fill(0);
            p. strokeWeight(1);
            p. ellipse(dataX, dataY, 16, 16);

            if(dataX+n1.dV/4>n2.cX)

            {
                hasSendingData=true;
                dataX=n1.cX;
                dataY=n1.cY;
            }
        }

    }

    // Agrandit la ligne petit à petit
    public void expandLine(){

        if(startTime==0)
            startTime=p.millis();
        if(finalDistance>dist) {
            dist=timer()/3000d*finalDistance;

            Point point = getNextPoint(angle, dist);
            p. line( n1.cX, n1.cY,point.x, point.y);
        }else
            p. line( n1.cX, n1.cY,n2.cX, n2.cY);



    }

    public void printWeight(){

        int x=((n2.cX/2+n1.cX/2)/2+n2.cX/2);
        int y=((n1.cY/2+n2.cY/2)/2+ n2.cY/2)-6;

        Text t= new Text(p,"W="+roundDown4(weight),0,0,0,255,10,x,y);


        p.pushMatrix();
        p.translate(x, y);
        p.rotate((float)getAngle());
        p.translate(-x, -y);
        t.printText();
        p.popMatrix();

    }

    // Supprime des chiffres après la virgule
    public static double roundDown4(double d) {
        return Math.floor(d * 1e4) / 1e4;
    }
    //calcul de l'angle  en theta
    public double getAngle() {


        return angle;
    }

    //Calcul du point suivant grâce à l'angle et la longueur en plus
    private Point getNextPoint(double angle,double distance){

        Point nextPoint = new Point();
      //    distance=distance/2;

        nextPoint.x = (int)(n1.cX + distance * Math.cos(angle));
        nextPoint.y = (int)( n1.cY + distance * Math.sin(angle));

    return  nextPoint;

    }


    private int timer(){
        return   p.millis() - startTime;
    }


}
