package rondel.marc.antoine.pong.Graphism;

import rondel.marc.antoine.pong.Ball;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Marcus on 11/05/2016.
 */
public class Graphism {
    PApplet parent;
    private PImage img;


    private int width;
    private  int height;
    private  int[] bufferR;
    private   int[] bufferG;
    private int[] bufferB;
    private  int[] bufferA;
    private SourceFire source;
    private Ball ball;
    private  Fluid fluid;


    public Graphism( PApplet parent,Ball ball){

        this.parent=parent;
        img = parent.createImage (parent.width,parent.height,parent.ARGB);
        this.ball=ball;
        width = parent.width;
        height = parent.height;
        bufferR = new int[width * height];
        bufferG = new int[width * height];
        bufferB = new int[width * height];
        bufferA = new int[width * height];
        source = new SourceFire (parent,50,50);
        fluid= new Fluid(parent,ball);
        fluid.setup();
    }

    public void fastBlur (int[] buf) {
        int id, k;
        int lim = width*(height-1);

        id = width+1;
        k=0;
        for (; id<lim; id+=1+k+k) {
            for (int i=1+k; i<width-1; i+=2, id+=2) {
                buf [id] >>= 1;
                buf [id] += (buf[id-1]+buf[id+1]+buf[id+width]+buf[id-width])>>3;
            }

            k = 1-k;
        }

        id = width+2;
        k=1;
        for (; id<lim; id+=1+k+k) {
            for (int i=1+k; i<width-1; i+=2, id+=2) {
                buf [id] >>= 1;
                buf [id] += (buf[id-1]+buf[id+1]+buf[id+width]+buf[id-width])>>3;
            }
            k = 1-k;
        }
    }

    public  void fastBigBlur (int[] buf) {
        int id, k;
        int lim = width*(height-1);

        id = width+1;
        k=0;
        for (; id<lim; id+=1+k+k) {
            for (int i=1+k; i<width-1; i+=2, id+=2) {
                buf [id] = (buf[id-1]+buf[id+1]+buf[id+width]+buf[id-width])>>2;
            }

            k = 1-k;
        }

        id = width+2;
        k=1;
        for (; id<lim; id+=1+k+k) {
            for (int i=1+k; i<width-1; i+=2, id+=2) {
                buf [id] = (buf[id-1]+buf[id+1]+buf[id+width]+buf[id-width])>>2;
            }
            k = 1-k;
        }
    }

    public void cleanBorders (int[] buf) {
        int id,lim;

        lim = width-1;
        for (id=1; id<lim; id++)
            buf[id] = 0;

        lim = width*height-1;
        for (id=width*(height-1)+1; id<lim; id++)
            buf[id] = 0;

        lim = width*height;
        for (id=0; id<lim; id+=width)
            buf[id] = 0;

        lim = width*height;
        for (id=width-1; id<lim; id+=width)
            buf[id] = 0;
    }
    public void draw(){

       fluid.draw();
        source.update (ball.getPos().x,ball.getPos().y,15,bufferA,bufferR,bufferG,bufferB);

        cleanBorders (bufferA);
        cleanBorders (bufferR);
        cleanBorders (bufferG);
        cleanBorders (bufferB);

        fastBlur (bufferR);
        fastBlur (bufferG);
        fastBlur (bufferB);

        fastBlur (bufferA);
        fastBlur (bufferA);
        fastBigBlur (bufferA);

        source.getImage(img,bufferA,bufferR,bufferG,bufferB);
        parent.image (img,0,0);

    }


}
