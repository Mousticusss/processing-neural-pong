package rondel.marc.antoine.learningNeuronal;

import processing.core.PApplet;

/**
 * Class pour l'utilsation de texte avec effets visuels
 */
public class Text    {
    private int align;
    private int alignC;
    private PApplet p;
    private int startTime;
    private String text;
    private   int  r,g,b,a;
    public int posX,posY;
    //Etat du texte, 0 pas encore affiché, 1 affiché, 2 plus affiché
    private int stateText=0;


    private int size;
    public Text(PApplet parent,String text,int r,int g, int b,int a,int size,int posX,int posY){

        this.p = parent;


        this.text=text;
        this.r=r;
        this.g=g;
        this.b=b;
        this. a=a;
        this.size=size;


        this.posX=posX;
        this.posY=posY;

        align=p.CENTER;

    }

        public void printText(){

            if(stateText==0) {
                startTime = p.millis();
                stateText = 1;
            }


            p.textSize(size);
            p.fill(r,g,b,a);
            setAlignText(align);
            p.text(text,posX,posY);


    }
    //Methode pour changer la position du texte
    public void setPosText(int x, int y){

        this.posX=x;
        this.posY=y;

    }
        //Methode pour aligner le texte si non définit le texte est centré
    public void setAlignText(int a){

        align=a;
        p.textAlign(align,  p.CENTER);




    }


    //Transparence
    public void fadeIn(){
        //Si le texte est encore un peu transparent on augmente la valeur alpha

            if (a < 255) {
                a+=5;



            }





    }
    //Transparence avec timer
    public void fadeOut(){



        if (a > 0) {
            a-=5;

        }




    }

    public void resetStartTime(){
        startTime=0;

    }
    public void moveText(int time,int x,int y){

        //Si le texte est affiché depuis x seconde il se déplace
        if (timer() > time){
            posX+=x;
            posY+=y;
            //Après une seconde on considère que le texte n'est plus affiché
           if(timer()>1000+time)
               stateText=2;
        }




    }
    public int stateText(){
        return stateText;
    }


    private int timer(){
        return   p.millis() - startTime;
         }

    public void setText(double text) {
        this.text = String.valueOf(text);
    }
}
