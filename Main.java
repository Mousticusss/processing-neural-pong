package rondel.marc.antoine;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import rondel.marc.antoine.learningNeuronal.LearningNeuronal;
import rondel.marc.antoine.pong.Constants;
import rondel.marc.antoine.pong.EnginePong;
import processing.core.PApplet;




public class Main   extends PApplet {
    Minim minim;
    AudioPlayer player;
    public int screen=1;
    private EnginePong  enginePong;
    private LearningNeuronal learning;

    //void setup(){
    //    minim = new Minim(this);
    public static void main(String _args[]) {
        PApplet.main(new String[] { Main.class.getName() });
    }


    public void settings() {
        size(Constants.SIZE_X, Constants.SIZE_Y,P2D );
        //pour limiter l'effet d'aliasing
        smooth(8);


    }




    public void setup() {

        minim = new Minim(this);
         frameRate(50);
        player = minim.loadFile("suite-bergamasque-iii-claire-de-lune.mp3");
        player.play();
        learning= new LearningNeuronal(this,this);
        enginePong= new EnginePong(this,true);


    }

    public void draw() {


        background(255);
        if(screen==0)
            learning.learn();
        if(screen==1)
            enginePong.play();


    }

    public void stop()
    {

        player.close();
        minim.stop();
        super.stop();
    }







}
