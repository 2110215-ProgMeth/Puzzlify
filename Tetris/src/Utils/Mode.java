package Utils;


import java.util.Timer;
import java.util.TimerTask;

import static application.Tetris.scoreMode;

public class Mode {
    public static double currHalfTime = 0;
    public static Timer HalfTimer = new Timer();
    public static double currDoubleTime = 0;
    public static Timer DoubleTimer = new Timer();
    public static void activeDouble(){
        if(scoreMode==sMode.HALF){
            HalfTimer.cancel();
            System.out.println("You got Buff, Debuff Gone!");
        }else if(scoreMode==sMode.DOUBLE)return;
        scoreMode = sMode.DOUBLE;
        System.out.println("You got buff, you will get double score in next 15 seconds!");
        TimerTask Doubletask = new TimerTask() {
            @Override
            public void run() {
                currDoubleTime+=0.01;
                if(currDoubleTime == 15){
                    currDoubleTime = 0;
                    System.out.println("Buff Gone!");
                    DoubleTimer.cancel();
                }
            }
        };
        DoubleTimer.schedule(Doubletask, 0, 10);
//        Doublethread = new Thread(()->{
//            try{
//                Thread.sleep(15000);
//                scoreMode = sMode.DEFAULT;
//                System.out.println("Buff Gone!");
//            }catch(InterruptedException e ){
//                /*if(scoreMode==sMode.DOUBLE) {
//                    System.out.println("You got Buff again, 15 second one more time!");
//                }else if(scoreMode==sMode.HALF){*/
//                    System.out.println("You got Debuff, Buff Gone!");
//                //}
//            }
//        });
//        Doublethread.start();
    }


    public static void activeHalf(){
            if(scoreMode==sMode.DOUBLE){
                Doublethread.interrupt();
            }else if(scoreMode==sMode.HALF)return;
            scoreMode = sMode.HALF;
            System.out.println("You got Debuff, Only get half score in next 15 seconds!");
            Halfthread = new Thread(()->{
                try{
                    Thread.sleep(15000);
                    scoreMode = sMode.DEFAULT;
                    System.out.println("Debuff end!");
                }catch(InterruptedException e ){
                   if(scoreMode==sMode.DOUBLE) {
                        System.out.println("You got Buff, Debuff Gone!");
                  }else if(scoreMode==sMode.HALF){System.out.println("No~~, you got Debuff again");}
                }
            });
            Halfthread.start();
    }
}