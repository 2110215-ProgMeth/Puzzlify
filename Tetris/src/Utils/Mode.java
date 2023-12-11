package Utils;


import static application.Tetris.scoreMode;

public class Mode {
    private static Thread Halfthread;
    private static Thread Doublethread;
    public static void activeDouble(){
        if(scoreMode==sMode.HALF){
            Halfthread.interrupt();
        }else if(scoreMode==sMode.DOUBLE)return;
        scoreMode = sMode.DOUBLE;
        System.out.println("You got Buff, you will get Double score in next 15 seconds!");
        Doublethread = new Thread(()->{
            try{
                Thread.sleep(15000);
                scoreMode = sMode.DEFAULT;
                System.out.println("Buff Gone!");
            }catch(InterruptedException e ){
                System.out.println("You got Debuff, Buff Gone!");
            }
        });
        Doublethread.start();
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