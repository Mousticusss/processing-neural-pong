package rondel.marc.antoine.pong.Graphism;

import rondel.marc.antoine.pong.Constants;
import processing.core.PApplet;

/**
 *  Class qui crée une particule pour l'effet de feu de la balle.
 */
public class FireParticule {

    PApplet parent;
    float x, y;
    float dirx,diry;
    float speed;
    boolean alive;
    float perlinPosX, perlinPosY;
    float angle;
    int amountA,amountR,amountG,amountB;




    FireParticule (PApplet parent, float x, float y) {
        this.parent=parent;
        this.x = x;
        this.y = y;
        this.speed = 0.7f+parent.random(0.7f);
        this.angle = parent.random(parent.TWO_PI);

        //Définit la couleur
        amountA = 140+(int)parent.random (80);
        amountR = 65+(int)parent.random (65);
        amountG = 30+(int)parent.random (30);
        amountB = 20+(int)parent.random (20);

        //Définit la position
        perlinPosX = parent.random (100);
        perlinPosY = parent.random (100);

        // Définint la vitesse
        this.dirx = parent.random(-speed,speed);
        this.diry = parent.random(-speed,speed);

        this.alive = true;
    }

    //Mise à jour des effets de partiucule, diminue les couleurs et de l'alpha jusqu'à atteindre 0 pour rgba
    void update () {


        if (amountR>0)
        {
            amountR=amountR-2;
            if (amountR<0)
                amountR=0;
        }

        if (amountG>0){
            amountG=amountG-2;
            if (amountG<0)
                amountG=0;
        }

        if (amountB>0)
        {
            amountB=amountB-2;
            if (amountB<0)
                amountB=0;
        }
        if (amountA>0)
        {
            amountA=amountA-2;
            if (amountA<0)
                amountA=0;
        }

        if (amountR==0 && amountG==0 && amountB==0 && amountA==0) {

            alive= false;
            return;
        }

        float dx =  (float)(2.0f*(0.5-parent.noise (perlinPosX)));
        float dy =  (float)(2.0f*(0.5-parent.noise (perlinPosY)));
        perlinPosX += Constants.scaleNoise*speed;
        perlinPosY += Constants.scaleNoise*speed;


        dirx = (dirx+dx*speed)/2.0f;
        diry = (diry+dy*speed)/2.0f;

        //Diminuz la vitesse
        speed *= 0.99f;
        //Actulise la position
        x += dirx;
        y += diry;

        if (x<0 || x>=parent.width || y<0 || y>=parent.height)
            alive = false;
    }

    void draw (int bufferA[],int bufferR[],int bufferG[],int bufferB[]) {
        int id = (int)x+(int)y*parent.width;
        bufferR [id] = parent.min (255,(bufferR [id])+amountR);
        bufferG [id] = parent.min (255,(bufferG [id])+amountG);
        bufferB [id] = parent.min (255,(bufferB [id])+amountB);
        bufferA [id] = parent.min (255,(bufferA [id])+amountA);
    }
}