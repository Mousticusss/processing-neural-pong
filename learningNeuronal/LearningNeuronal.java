package rondel.marc.antoine.learningNeuronal;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import rondel.marc.antoine.Main;

import java.util.ArrayList;

/**
 * Created by Marcus on 22/06/2016.
 */
public class LearningNeuronal {

    private  PApplet parent;

    private  Text textIntro;
    private Text textInputNeuron;
    private Text textHiddenLayer;
    private Text textOutpuNeuron;
    private Text textWeight;
    private Text textNeuron;
    private Text textResolveXOR;
    private Text textEnterNumbers;
    private Text textDontWork;
    private Text textFinal;

    private ArrayList<Neuron> neuronsInput;
    private ArrayList<Neuron> neuronsCouch1;


    private  ArrayList<Neuron>  neuronsOutput;
    private int startTime=0;
    //Pour connaitre la partie de l'animation
    private int state=0;
    private int numberEnter=-1;
    private boolean keyEventOpen=false;
    private int[] inputs= new int[]{-1,-1};


    private float scaler = 1;
    private boolean trueWeight=false;
    private Neuron finalNeuron;

    private Main main;
    private PImage img;

    public LearningNeuronal(PApplet parent,Main main){
        img = parent.loadImage("neuroneBackground.png");

        this.main=main;
        this.parent = parent;

        parent.registerMethod("keyEvent", this);


       neuronsInput=new ArrayList<Neuron>();
       neuronsCouch1=new ArrayList<Neuron>();
       neuronsOutput=new ArrayList<Neuron>();



       makeNeurons();
       makeText();
    }




// Méthode principale qui met en place les différents éléments de l'animaiton
    public  void learn (){



        zoom();

        parent.image(img, 0, 0);

        switch(state){
            case 0: intro(); break;
            case 1: presentationLayer();break;
            case 2: seeInsideNeurone();break;
            case 3: resolveXOR();break;


        }


                   }

    private void intro(){

        textIntro.printText();
        textIntro.fadeIn();
        textIntro.moveText(6000, 30, 0);
        if(textIntro.stateText() ==2  )
            state=1;
    }
    private void presentationLayer(){

        if(startTime==0)
            startTime=parent.millis();


        for (Neuron n:neuronsInput ) {
            n.printNeuron(false);
        }
        textInputNeuron.printText();
        textInputNeuron.fadeIn();


        if(timer()>3000){
            for (Neuron n:neuronsCouch1 ) {
                n.printNeuron(false);

            }
            textHiddenLayer.printText();
            textHiddenLayer.fadeIn();
        }
        if(timer()>6000) {

            neuronsOutput.get(0).printNeuron(false);
            textOutpuNeuron.printText();
            textOutpuNeuron.fadeIn();

        }
        if(timer()>9000)
            state=2;





    }

    private void seeInsideNeurone(){

        for (Neuron n:neuronsInput ) {
            n.printNeuron(false);
        }
        for (Neuron n:neuronsCouch1 ) {
            n.printNeuron(false);

        }
        neuronsOutput.get(0).printNeuron(false);
        if(timer()>9000)
        {
            if(scaler>7.8 && timer()<39000 )
            {

                neuronsCouch1.get(1).changeState(3);
                makeWeight();
            }
            //Zoom
            if(scaler<10 && timer()<15000)
                scaler=scaler*1.05f;

            if(!neuronsCouch1.get(1).insideNeuron && timer()>15000 ){
                System.out.println("avant dezzom");
                if(scaler>1){
                    //Dezoom
                    System.out.println("dezzom");
                    scaler=scaler*0.95f;
                }
                if(scaler>7.8 )


                {
                    System.out.println(scaler);
                    //On remet le neurone dans son état originel
                    neuronsCouch1.get(1).changeState(0);

                }

                if(scaler<1.1 )
                    state=3;


            }

        }




    }

    private void resolveXOR(){


        trainWeightForLinks();

        textResolveXOR.printText();
        textResolveXOR.moveText(3000, 30, 0);
        if(textResolveXOR.stateText() ==2  ) {
         //   if(textEnterNumbers.stateText()!=2)
            textEnterNumbers.printText();
            //active le clavier
            keyEventOpen=true;
            neuronsInput.get(0).changeState(1);
        }




        if(numberEnter>=0){
            neuronsInput.get(1).changeState(1);
            neuronsInput.get(0).output=inputs[0];
            neuronsInput.get(0).activateSendData(true);
        }
        if(numberEnter>=1) {
            neuronsInput.get(1).output = inputs[1];
            neuronsInput.get(1).activateSendData(true);
            textEnterNumbers.moveText(500, 0, 15);
        }
        for (Neuron n:neuronsInput ) {

            n.printNeuron(true);
            textNeuron.setText(n.output);
            textNeuron.setPosText(n.cX,n.cY);
            textNeuron.printText();

        }
        for (Neuron n:neuronsCouch1 ) {

            n.activateSendData(false);
            n.printNeuron(true);
            n.calculateOutput();
           textNeuron.setText(roundDown2(n.output));
            textNeuron.setPosText(n.cX,n.cY);
            textNeuron.printText();



        }



       if( neuronsOutput.get(0).activateSendData(false) )
       {
           if(!trueWeight) {
               textDontWork.printText();

               textDontWork.moveText(5000, -30, 0);
               if (textDontWork.stateText() == 2) {
                   trueWeight = true;
                   reset();

               }
           }
           else
           {
               textFinal.printText();
               finalNeuron.printNeuron(false);
               finalNeuron.changeState(1);
               if(finalNeuron.pressNeuron())
                  main.screen=1;
           }
       }



        neuronsOutput.get(0).printNeuron(true);
        neuronsOutput.get(0).calculateOutput();
         textNeuron.setText(roundDown2(neuronsOutput.get(0).output));
        textNeuron.setPosText(neuronsOutput.get(0).cX,neuronsOutput.get(0).cY);
        textNeuron.printText();

    }
    // Supprime des chiffres après la virgule
    public static double roundDown2(double d) {
        return Math.floor(d * 1e2) / 1e2;
    }

    private void reset(){

        numberEnter=-1;
        textEnterNumbers.resetStartTime();
        textEnterNumbers.setPosText(parent.width/2,parent.height-parent.height/6);
        for (Neuron n:neuronsInput ) {

            n.changeState(0);
            for (LinkNeuron l: n.getAllLinks()
                 ) {
                l.hasSendingData=false;

            }

        }
        for (Neuron n:neuronsCouch1 ) {


            n.changeState(0);
            for (LinkNeuron l: n.getAllLinks()
                    ) {
                l.hasSendingData=false;

            }

        }
        neuronsOutput.get(0).changeState(0);



    }

    //Les textes  crées ici le sont par simplicité, on pourrait très bien utiliser un même objet Text dans toute l'animation.
    private void makeText(){

        textIntro=new Text(parent,"Les réseaux neuronales",150,198,99,0,50,parent.width/2,parent.height/2);

        textInputNeuron=new Text(parent,"Couche de neurones d'entrée",150,198,99,0,16,parent.width/6,parent.height-parent.height/6);;
        textHiddenLayer=new Text(parent,"Couche  de neurones cachéee",150,198,99,255,16,parent.width/6+parent.width/3,parent.height-parent.height/6);;
        textOutpuNeuron=new Text(parent,"Couche de sortie",150,198,99,0,16,parent.width/6+parent.width/3*2,parent.height-parent.height/6);
        textWeight=new Text(parent,"Poid(W)",150,198,99,255,3,parent.width/6+parent.width/3,parent.height/8+parent.height/4);

        textNeuron=new Text(parent," 1 ",0,0,0,255,12,parent.width/6+parent.width/3,parent.height/8+parent.height/4);
        textResolveXOR=new Text(parent," Résolvons l'opération XOR grâce à ce réseau ",0,0,0,255,12,parent.width/2,parent.height-parent.height/6);
        textEnterNumbers=new Text(parent," Entrez deux chiffres sur le clavier (0 ou 1) ",0,0,0,255,12,parent.width/2,parent.height-parent.height/6);
        textDontWork=new Text(parent," Le résultat n'est pas très concluant... \n Les poids du réseau n'ont pas été entrainé \n Changeons cela et réessayez ",0,0,0,255,12,parent.width/2,parent.height-parent.height/6);
        textFinal=new Text(parent," Voilà, le résultat n'a qu'une marge d'erreur de 0.1 \n Maintenant en cliquant sur le neurone \n vous pourrez affronter un algorithme neuronal à pong ",0,0,0,255,12,parent.width/2,parent.height-parent.height/6);





    }
    private void makeNeurons(){

        //Creation de l'output Neuron
        int  posX=parent.width/6+parent.width*2/3;
        int  posY=parent.height/8+parent.height/4;
        neuronsOutput.add( new Neuron(parent,posX,posY,50,50,0));



        posX=parent.width/6+parent.width/3;
        posY=parent.height/8;
        for(int i=0;i<3;i++){

            neuronsCouch1.add(new Neuron(parent,posX,posY,50,50,neuronsOutput,i));


            posY+=parent.height/4;
        }

        posX=parent.width/6;
        posY=parent.height/4;
        for(int i=0;i<2;i++){

            neuronsInput.add(new Neuron(parent,posX,posY,50,50,neuronsCouch1,i));


            posY+=parent.height/4;
        }

        //Neurone pour le final
        finalNeuron = new Neuron(parent, parent.width- parent.width/10,parent.height-parent.height/8,50,50,0);



    }
    private void makeWeight()
    {

        int dV=neuronsInput.get(0).dV;
        int[] vX= {neuronsCouch1.get(1).cX-dV/2- 1, neuronsCouch1.get(1).cX-dV/2 - 2, neuronsCouch1.get(1).cX+dV/2+1};
        int[] vY= {neuronsCouch1.get(1).cY-10 , neuronsCouch1.get(1).cY + 5, neuronsCouch1.get(1).cY-2};
        double[] vR= {neuronsInput.get(0).getLink(1).getAngle() , neuronsInput.get(1).getLink(1).getAngle(), neuronsCouch1.get(1).getLink(0).getAngle()};


        for(int i=0;i<3;i++) {

            parent.pushMatrix();
            parent.translate(vX[i], vY[i]);
            parent.rotate((float)vR[i]);
            parent.translate(-vX[i], -vY[i]);
            textWeight.setPosText(vX[i], vY[i]);
            if(i!=2)
                textWeight.setAlignText(parent.RIGHT);
            else
                textWeight.setAlignText(parent.LEFT);
            textWeight.printText();

            textWeight.fadeIn();
            parent.popMatrix();

        }

    }

    //Zoom sur un neurone pour l'examiner de plus preès.
    public void zoom(){

     int   posX=parent.width/6+parent.width/3;
        int  posY=parent.height/8+parent.height/4;
        //Efface l'image préceédente
        parent.background(255);
        //Déplace la fênnetre
        parent.translate(posX,posY+6); // use translate around scale
        //augment la taille des formes
        parent.scale(scaler);
        parent.translate(-posX,-posY+6); // to scale from the center




    }
    // Permet de donner à chaque lien le poid d'un réseau neuronal entraîné.
    public void  trainWeightForLinks(){

        if(trueWeight){

 double []weights=new double[]{-4.524838140406328,6.961640320522546,5.7799511319384065,6.844382240499076,-4.565019294925179,5.755477227494378,-13.226843675728679,-13.221271161477668,18.095827000809035};
        int i=0;
            for (Neuron n: neuronsInput
                 ) {
                for (LinkNeuron l:n.getAllLinks()
                     ) {
                    l.weight=weights[i];
                    i++;

                }


            }
            for (Neuron n: neuronsCouch1
                    ) {
                for (LinkNeuron l:n.getAllLinks()
                        ) {
                    l.weight=weights[i];
                    i++;

                }

            }



        }



    }


    public  void keyEvent(KeyEvent event){


        if(KeyEvent.RELEASE ==event.getAction() && keyEventOpen && numberEnter<1)
            if (event.getKey() == '0') {


                numberEnter++;
                inputs[numberEnter]=0;

        }
        else if(event.getKey() == '1'){

                numberEnter++;
                inputs[numberEnter]=1;

        }

        if(numberEnter == 1){

        }


    }



    private int timer(){
        return   parent.millis() - startTime;
    }
}
