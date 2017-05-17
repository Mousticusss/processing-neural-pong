package rondel.marc.antoine.pong.Graphism;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * Class pour l'effet de feu qui suit la balle
 */
public class SourceFire {

    private float x, y;
    private float lastx, lasty;
    private PApplet parent;
    private int width;
    private int height;
    private ArrayList particles;
    private int MAX_PARTICLES = 5000;

   public SourceFire(PApplet parent, float x, float y) {
        this.x = x;
        this.y = y;


        this.lastx = this.x;
        this.lasty = this.y;

        this.parent = parent;

       height=parent.height;
       width=parent.width;
        particles = new ArrayList();
    }

   public void update(float x, float y, int n,int[] bufferA,int[] bufferR,int[] bufferG,int[] bufferB) {

        this.lastx = this.x;
        this.lasty = this.y;
        this.x = x;
        this.y = y;

        float coef;
       //Ajoute des particules
        for (int i = 0; i < n && particles.size() < MAX_PARTICLES; i++) {
            coef = parent.random(1);
            particles.add(new FireParticule(parent,this.x * coef + this.lastx * (1.0f - coef), this.y * coef + this.lasty * (1.0f - coef)));
        }

       //Vérifie si les particules sont toujours visibles
        for (int i = particles.size() - 1; i >= 0; i--) {
            FireParticule p = (FireParticule) particles.get(i);
            p.update();
            if (!p.alive)
                particles.remove(i);
            else
                p.draw(bufferA,bufferR,bufferG,bufferB);
        }


    }

   public void getImage(PImage img,int[] bufferA,int[] bufferR,int[] bufferG,int[] bufferB) {
        img.loadPixels();

        for (int i = width * height - 1; i >= 0; i--) {
            img.pixels[i] = bufferA[i] << 24 | bufferR[i] << 16 | bufferG[i] << 8 | bufferB[i];
        }
        img.updatePixels();
    }

}

