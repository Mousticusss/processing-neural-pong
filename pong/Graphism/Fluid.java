package rondel.marc.antoine.pong.Graphism;

import rondel.marc.antoine.pong.Ball;
import processing.core.PApplet;
import processing.core.PImage;


/***
 *
 * Class qui créer un effet de fluide lors du déplacement de la balle
 *
 ***/
public class  Fluid{

    PApplet parent;
    private Ball ball;
    private PImage img;

    public Fluid(PApplet parent, Ball ball){
        this.parent= parent;
        this.ball=ball;
        img = parent.loadImage("background.jpg");


    }


    int size;
    int hwidth,hheight;
    int riprad;

    int ripplemap[];
    int ripple[];
    int texture[];

    int oldind,newind, mapind;

    int i,a,b;



    public void setup(){


        hwidth = parent.width>>1;
        hheight = parent.height>>1;
        riprad=5;

        size = parent.width * (parent.height+2) * 2;

        ripplemap = new int[size];
        ripple = new int[parent.width*parent.height];
        texture = new int[parent.width*parent.height];

        oldind = parent.width;
        newind = parent.width * (parent.height+3);

        parent.image(img, 0, 0);
        parent. loadPixels();


    }

    public void draw() {
        parent.  image(img, 0, 0);

        parent.  loadPixels();
        texture = parent.pixels;
        itsRainingToday();
        disturb(ball.getPos().x, ball.getPos().y);
        newframe();

        for (int i = 0; i <parent. pixels.length; i++) {
            parent. pixels[i] = ripple[i];
        }

        parent.updatePixels();
    }

    public void disturb(int dx, int dy) {
        for (int j=dy-riprad;j<dy+riprad;j++) {
            for (int k=dx-riprad;k<dx+riprad;k++) {
                if (j>=0 && j<parent.height && k>=0 && k<parent.width) {
                    ripplemap[oldind+(j*parent.width)+k] += 512;
                }
            }
        }
    }


    public void newframe() {

        i=oldind;
        oldind=newind;
        newind=i;

        i=0;
        mapind=oldind;
        for (int y=0;y<parent.height;y++) {
            for (int x=0;x<parent.width;x++) {
                short data = (short)((ripplemap[mapind-parent.width]+ripplemap[mapind+parent.width]+ripplemap[mapind-1]+ripplemap[mapind+1])>>1);
                data -= ripplemap[newind+i];
                data -= data >> 5;
                ripplemap[newind+i]=data;


                data = (short)(1024-data);


                a=((x-hwidth)*data/1024)+hwidth;
                b=((y-hheight)*data/1024)+hheight;


                if (a>=parent.width) a=parent.width-1;
                if (a<0) a=0;
                if (b>=parent.height) b=parent.height-1;
                if (b<0) b=0;

                ripple[i]=texture[a+(b*parent.width)];
                mapind++;
                i++;
            }
        }
    }


    //Effet de pluie
    public void itsRainingToday(){

        int randW= (int)parent.random(parent.width-1);
        int randH= (int)parent.random(parent.height-1);
        disturb(randW,randH);
    }


        }