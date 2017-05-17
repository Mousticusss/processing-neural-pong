package rondel.marc.antoine.learningNeuronal;

import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.ArrayList;

/**
 * Created by Marcus on 23/06/2016.
 */
public class Neuron {

    private PApplet p;
    public int cX,cY,dH,dV;
    private int r=0,g=128,b=255,a=255;
    private ArrayList<Neuron> neuronsLinkedTo =null;
    private ArrayList<LinkNeuron> linksNeuronTo=null;
    private ArrayList<LinkNeuron> linksNeuronFrom=null;
    private int stateNeuron=0;
    private ArrayList<Text> textInNeuron=new ArrayList<>();
    private int startTime=0;
    public boolean insideNeuron=false;
    public int id;
    public double output;



    public  Neuron(PApplet parent,int cX,int cY,int dH,int dV,int id ){
        p=parent;
        this.cX=cX;
        this.cY=cY;
        this.dH=dH;
        this.dV=dV;
        this.id=id;
     //   parent.registerMethod("mouseEvent", this);

    }
    public  Neuron(PApplet parent,int cX,int cY,int dH,int dV,ArrayList<Neuron> neurons,int id ){
        p=parent;
        this.cX=cX;
        this.cY=cY;
        this.dH=dH;
        this.dV=dV;
        this.id=id;
        neuronsLinkedTo =neurons;
        linksNeuronTo = new ArrayList<>();
        setLinkNeuronTo();
        makeTextInsideNeuron();

    //    parent.registerMethod("mouseEvent", this);

    }


    public LinkNeuron getLink(int index){

        return linksNeuronTo.get(index);


    }

    public ArrayList<LinkNeuron>getAllLinks(){

        return linksNeuronTo;


    }


    public void changeState(int state){
        stateNeuron=state;
    }


    //Afficher neurone
    public void printNeuron(boolean printWeight){
        p.pushStyle();
        //Affiche Les connextions
        if(linksNeuronTo !=null)
            for (LinkNeuron l: linksNeuronTo) {
                l.printLink();
                if(printWeight)
                   l. printWeight();

            }

        switch (stateNeuron){
            case 0: sleepingNeuron();break;
            case 1: preActivateNeuron(); break;
            case 2: activateNeuron();break;
            case 3: insideNeuron();break;

        }
        p.popStyle();


    }
    // Neurone non activé
    private void sleepingNeuron(){


        p.fill(0,0,0);
        p. ellipse(cX, cY, dH, dV);


    }

    // Neurone activé
   private void  activateNeuron(){

       p.fill(r,g,b);
       p. ellipse(p.random(cX-1,cX+1), p.random(cY-1,cY+1), dH, dV);

   }

    // Neurone non activé en attente d'une interaction
    private void preActivateNeuron(){


        p.fill(0,0,0);
        p. ellipse(p.random(cX-1,cX+1), p.random(cY-1,cY+1), dH, dV);



    }


    //Neurone vue de "l'intérieur"
    private void insideNeuron()
    {


        insideNeuron=true;
        p.strokeWeight(1);
        p.fill(255,255,255,a);
        p.stroke(0,0,0);
        p. ellipse(cX, cY, dH, dV);
        p.line( cX, cY+dH/2,cX, cY-dH/2);
        p.textAlign(p.CENTER,p.CENTER);
        //Les i s'affichent mal dû  à la taille des lettres


        textInNeuron.get(0).printText();
        textInNeuron.get(1).printText();
        textInNeuron.get(2).printText();
        textInNeuron.get(3).printText();
        textInNeuron.get(4).printText();
        textInNeuron.get(5).printText();
        if(startTime==0)
            startTime=p.millis();
        if(timer()<5000) {
            textInNeuron.get(0).fadeIn();
            textInNeuron.get(1).fadeIn();
        }
        else if(timer()<8000)
        {
            textInNeuron.get(0).fadeOut();
            textInNeuron.get(1).fadeOut();
        }else  if(timer()<15000) {
            textInNeuron.get(2).fadeIn();
            textInNeuron.get(3).fadeIn();
        } else if(timer()<18000)
        {
            textInNeuron.get(2).fadeOut();
            textInNeuron.get(3).fadeOut();
        }
        else  if(timer()<25000) {
            textInNeuron.get(4).fadeIn();
            textInNeuron.get(5).fadeIn();
        } else if(timer()<28000)
        {
            textInNeuron.get(4).fadeOut();
            textInNeuron.get(5).fadeOut();

        }
        else insideNeuron=false;



    }
    private void makeTextInsideNeuron(){

        textInNeuron.add(new Text(p,"Fonction  \n     de       \n combinaison",150,198,99,0,3,cX-dV/4,cY));
        textInNeuron.add(new Text(p,"Fonction  \n d'activation",150,198,99,0,3,cX+dV/4,cY));


        textInNeuron.add(new Text(p,"Elle combine\n les entrées et\n les poids",150,198,99,0,3,cX+dV/4,cY));
        textInNeuron.add(new Text(p,"Elle calcule\n la sortie\n du neurone ",150,198,99,0,3,cX-dV/4,cY));


        textInNeuron.add(new Text(p," Exemple de\n combinaison :\nc=∑ Wi Ei",150,198,99,0,3,cX+dV/4,cY));
        textInNeuron.add(new Text(p,"Exemple\n d'activation\n a=1/(1+ \ne exposant c)",150,198,99,0,3,cX-dV/4,cY));

    }

    public void setLinkNeuronTo(){



        if(neuronsLinkedTo !=null){

            for (Neuron n: neuronsLinkedTo) {

                LinkNeuron l=new LinkNeuron(p,0,0,0,this,n);
                linksNeuronTo.add(l);
                n.setLinksNeuronFrom(l);

            }
        }

    }
    public void setLinksNeuronFrom(LinkNeuron linkNeuron){

        if(linksNeuronFrom==null)
        linksNeuronFrom = new ArrayList<>();

        linksNeuronFrom.add(linkNeuron);


    }

    public ArrayList<LinkNeuron> getLinksNeuronTo(){

        return linksNeuronTo;


    }


//Enclenche l'envoie de données entre les neurones
    public boolean activateSendData(boolean isInput){

        boolean hasSendingAllData=true;
        if(linksNeuronFrom !=null)
            for (LinkNeuron l: linksNeuronFrom
                    ) {



                if( ! l.hasSendingData)
                hasSendingAllData=false;


            }
       if( hasSendingAllData  || isInput) {

           this.changeState(2);
           if(linksNeuronTo!=null)
           for (LinkNeuron l : linksNeuronTo
                   ) {

                l.n2.changeState(1);
               l.sendingData = true;
               l.sendData();

           }
        return true;
       }
        return false;
    }

    public void calculateOutput(){
        double sum=0;

        for (LinkNeuron l: linksNeuronFrom
                ) {

               sum+= l.weight*l.n1.output;



        }

        output=function(sum);

    }

    // Fonction sigmoide
    public  double function(double x) {
        return 1.0 / (1.0 +  Math.exp(-x));
    }





    private int timer(){
        return   p.millis() - startTime;
    }

    public void mouseEvent(MouseEvent event) {
        // if (event.getAction() == MouseEvent.CLICK || event.getAction() == MouseEvent.PRESS) {

        if(p.mousePressed)
        {

            if(pressNeuron()&& stateNeuron==1){

                stateNeuron=2;

            }


        }


    }


    //Déclenché lors d'un clique sur un neurone
    public boolean pressNeuron(){

        if (p.mouseButton == p.LEFT)
            if (p.sq(p.mouseX - cX) + p.sq(p.mouseY - cY) < dH / 2 * dH / 2)
                return true;



        return false;
    }




}
