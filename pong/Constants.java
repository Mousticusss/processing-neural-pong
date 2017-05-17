package rondel.marc.antoine.pong;

import java.awt.*;

/**
 * Created by Marcus on 10/05/2016.
 */
final  public  class Constants {

    public static final int SIZE_X =800;
    public static final int SIZE_Y =600;

    public static final int SIZE_BALL =10;
    public static final Point BALL_POS =new Point(SIZE_X /2,SIZE_Y/2);
    public static final int RACKET_HEIGHT =50;
    public static final int RACKET_WIDTH =20;
    public static final Point RACKET1_POS = new Point(SIZE_X /10,SIZE_Y/2);
    public static final Point RACKET2_POS =new Point(SIZE_X - SIZE_X /10,SIZE_Y/2);
    public static final float scaleNoise = 0.01f;

}
