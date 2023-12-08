package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import Block.BasicStructure.Block;
import Block.BasicStructure.Skillable;
import Utils.sMode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Tetris extends Application {
    //variable ต่างๆ
    public static final int MOVE = 40;//เคลื่อนที่ครั้งละblock
    public static final int SIZE = 40;//ขนาดblock
    public static int XMAX = SIZE * 12;//ความยาวแกนxของช่องเล่นเกม
    public static int YMAX = SIZE * 24;//ความยาวแกนyของช่องเล่น
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];//เป็นการตีตารางมั้ง??
    public static Pane group = new Pane();//สร้างpane
    private static VBox UI = new VBox();
    private static HBox ROOT = new HBox();
    private static Form object;//ของชิ้นปัจจุบัน
    private static Scene scene = new Scene(ROOT, XMAX + 300, YMAX+50);//XMAX + 150 เพราะส่วนขวามีที่ไม่ใช่พื้นที่เกมด้วย
    public static int score = 0;//คะแนนที่ได้ เพิ่มได้จากการกด เลื่อนลง || deleterow
    private static int top = 0;//สำหรับดูว่าเกินหรือยัง
    private static boolean game = true;//ยังรอดอยู่ไหม
    private static Form nextObj ;//ของชิ้นต่อไป
    private static int linesNo = 0;//จำนวนแถวที่deleteได้
    public static int times = 0;//เวลาใช้มาคำนวณเวลาBuff
    public static sMode scoreMode = sMode.DEFAULT;//ใช้มาเลือกการเพิ่มคะแนน

    private static AudioClip hs;



    public Button startButton = new Button("Start");
    public Button restartButton = new Button();
    public static void main(String[] args) {//main
        launch(args);//จะไปเรียกstart
    }

    @Override
    public void start(Stage stage) throws Exception {

        scene.getStylesheets().add(this.getClass().getResource("/main.css").toExternalForm());
        hs = new AudioClip(this.getClass().getResource("/SFX/hs.wav").toExternalForm());

        stage.setResizable(false);
        group.setPrefWidth(XMAX);

        for (int[] a : MESH) {//unknowed
            Arrays.fill(a, 0);
        }
        group.setPrefWidth(XMAX);
        ROOT.setPadding(new Insets(10));
        group.setPadding(new Insets(2));

        Text scoretext = new Text("Score: 0");//text for score
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);//ไม่ให้ติดกับเส้นแบ่ง
        Text level = new Text("Level: 0");//text for level
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.GREEN);

        setBackground();

        UI.setAlignment(Pos.CENTER_LEFT);
        UI.setPadding(new Insets(10));
        UI.getChildren().addAll(scoretext, level,startButton);//เพิ่มลงในpane

        ROOT.getChildren().addAll(group,UI);


        stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.show();

        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {//ใช้เพราะมีการเปลี่ยน user interface
                    public void run() {


                        if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0
                                || object.d.getY() == 0)//ถึงแตะบนสุด + 1
                            top++;
                        else
                            top = 0; //ลดลงมาทันก็นับใหม่

                        if (top == 2) {//เกินไป 1 block = จบ
                            // GAME OVER
                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arial;");
                            over.setY(250);
                            over.setX(10);
                            group.getChildren().add(over);
                            game = false;//จบเกม
                        }

                        // Exit
                        if (top == 15) {//เวลาในการExitGame automatically
                            System.exit(0);
                        }

                        if (game) {
                            MoveDown(object);//เลื่อนลงเรื่อยๆ เสมออยู่แล้ว
//                            if(DoubleNow){circle.setVisible(true);}
//                            else{circle.setVisible(false);}
                            scoretext.setText("Score: " + Integer.toString(score));
                            level.setText("Lines: " + Integer.toString(linesNo));
                        }
                    }
                });
            }
        };
        startButton.setOnAction(e->{
            fall.schedule(task, 0, 300);//period = เว้นว่างระหว่างรอบ จะtaskซ้ำๆหลังจากdelay
            startButton.setDisable(true);
            nextObj = Controller.makeRect();

            Form a = nextObj;
            group.getChildren().addAll(a.a, a.b, a.c, a.d);

            moveOnKeyPress(a);

            object = a;
            nextObj = Controller.makeRect();
        });
    }



    private void moveOnKeyPress(Form form) {//แต่ละอันทำอะไรบ้าง
//        hitSound.play();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        Controller.MoveRight(form);
                        break;
                    case DOWN:
                        MoveDown(form);
                        increaseScore(2);
                        break;
                    case LEFT:
                        Controller.MoveLeft(form);
                        break;
                    case UP:
                        MoveTurn(form);
                        break;
                    case ENTER:// ทำให้ลงถึงสุดเลย
                        MoveToBottom(form);
                        break;
                }
            }
        });
    }

    private void MoveTurn(Form form) {//หมุน แต่ละแบบหมุนได้ 4 form มี 7 แบบ รวม 28 form
        int f = form.form;//แยกแต่ละrectangleมาเลื่อน
        Block a = form.a;
        Block b = form.b;
        Block c = form.c;
        Block d = form.d;
        switch (form.getBlockType()) {
            case J:
                if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();//จาก 1->2
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case L:
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case O:
                break;
            case S:
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case T:
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case Z:
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case I:
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
        }
    }

    private void RemoveRows(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();//เก็บว่าแถวไหนเต็มบ้าง
        ArrayList<Node> newrects = new ArrayList<Node>();

        int full = 0;//นับว่าแต่ละrowมีblockเท่าไหร่
        for (int i = 0; i < MESH[0].length; i++) {//each row
            for (int j = 0; j < MESH.length; j++) {//each column
                if (MESH[j][i] == 1)//ช่องนี้มีblockไหม
                    full++;//ถ้ามีเพิ่ม
            }
            if (full == MESH.length)//ถ้ามีทั้งแถว
                lines.add(i);//บอกว่าแถวนี้เต็มนะ
            //lines.add(i + lines.size());
            full = 0;//ทำให้เป็นศูนย์เพื่อไปนับแถวต่อไป
        }
        if (lines.size() > 0){//มีแถวเต็ม
            // if have line to remove
            do {
                // add all Block to rects
                for (Node node : pane.getChildren()) {
                    if (node instanceof Block)
                        rects.add(node);
                }
                // update score and lineNo score
                increaseScore(50);
                linesNo++;

                // get all node that recently from pane
                for (Node node : rects) {
                    if(node instanceof Block){
                        Block a = (Block) node;

                        // if this node has y the same as the line we gonna delete
                        if (a.getY() == lines.get(0) * SIZE) {
                            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                            if(a instanceof Skillable){
                                ((Skillable) a).activeSkill(group);
                            }
                            pane.getChildren().remove(node);
                        } else
                            newrects.add(node);

                    }
                }

                for (Node node : newrects) {
                    Block a = (Block) node;
                    // node that higher than the line -> move down 1 block
                    if (a.getY() < lines.get(0) * SIZE) {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() + SIZE);
                    }
                }
                lines.remove(0);
                rects.clear();
                newrects.clear();
                /// get all rectangle in pane
                for (Node node : pane.getChildren()) {
                    if (node instanceof Block)
                        rects.add(node);
                }
                // บอกในMESHว่าตรงนี้มีBlock เพราะข้างบนลบออกแล้วยังไม่ได้เพิ่มใหม่
                for (Node node : rects) {
                    Block a = (Block) node;
                    try {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                rects.clear();
            } while (lines.size() > 0);
        }
    }
    public static void removeBlock(Node block){
        if(block instanceof Block){
            Block b = (Block)block;
            int x = (int)b.getX();
            int y = (int)b.getY();
            MESH[x/SIZE][y/SIZE] = 0;
            group.getChildren().remove(block);
        }
    }

    private void MoveDown(Rectangle rect) {
        if (rect.getY() + MOVE < YMAX)
            rect.setY(rect.getY() + MOVE);
    }


    private void MoveRight(Rectangle rect) {
        if (rect.getX() + MOVE <= XMAX - SIZE)
            rect.setX(rect.getX() + MOVE);
    }

    private void MoveLeft(Rectangle rect) {
        if (rect.getX() - MOVE >= 0)
            rect.setX(rect.getX() - MOVE);
    }

    private void MoveUp(Rectangle rect) {
        if (rect.getY() - MOVE > 0)
            rect.setY(rect.getY() - MOVE);
    }

    private void MoveToBottom(Form form){
        while(form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX && form.d.getY() + MOVE < YMAX && !moveA(form) && !moveB(form) && !moveC(form) && !moveD(form)){
            MoveDown(form);
            increaseScore(2);
        }
    }

    private void MoveDown(Form form) {
        if (form.a.getY() == YMAX - SIZE ||
                form.b.getY() == YMAX - SIZE ||
                form.c.getY() == YMAX - SIZE ||
                form.d.getY() == YMAX - SIZE ||
                moveA(form) ||
                moveB(form) ||
                moveC(form) ||
                moveD(form))
        {
            hs.play();

            // if this block at the bottom.
            // set Mesh
            MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;


            RemoveRows(group); // remove row, check inside.

            // create next block
            Form a = nextObj;
            nextObj = Controller.makeRect();
            object = a;
            group.getChildren().addAll(a.a, a.b, a.c, a.d);
            moveOnKeyPress(a);
        }

        if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX
                && form.d.getY() + MOVE < YMAX) {
            int movea = MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1];
            int moveb = MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1];
            int movec = MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1];
            int moved = MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setY(form.a.getY() + MOVE);
                form.b.setY(form.b.getY() + MOVE);
                form.c.setY(form.c.getY() + MOVE);
                form.d.setY(form.d.getY() + MOVE);
            }
        }
    }

    // move Block function.
    // check if this below has data?
    private boolean moveA(Form form) {
        return (MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1);
    }

    private boolean moveB(Form form) {
        return (MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1);
    }

    private boolean moveC(Form form) {
        return (MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1);
    }

    private boolean moveD(Form form) {
        return (MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
    }

    private boolean cB(Rectangle rect, int x, int y) {//check bounder ดูว่าเลื่อนในแกนx x move/แกนy y move หลุดขอบไหม
        boolean xb = false;//X-axis bounder
        boolean yb = false;//Y-axis bounder
        if (x >= 0)//เช็คเทียบขอบขวามือ
            xb = rect.getX() + x * MOVE <= XMAX - SIZE;
        if (x < 0)//เช็คเทียบขอบซ้ายมือ
            xb = rect.getX() + x * MOVE >= 0;
        if (y >= 0)//เช็คล่าง
            yb = rect.getY() - y * MOVE > 0;
        if (y < 0)//เช็คบน
            yb = rect.getY() + y * MOVE < YMAX;
        return xb && yb && MESH[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
    }

    private void setBackground(){
        for(int i =0;i<12;i++){
            for(int j = 0;j<24;j++){
                Rectangle wall = new Rectangle(SIZE-1,SIZE-1);
                wall.setStyle("-fx-fill: rgb(255, 246, 220); -fx-stroke: rgb(158, 159, 165); -fx-stroke-width: 1");
                wall.setX(i*SIZE);
                wall.setY(j*SIZE);
                group.getChildren().add(wall);
            }
        }
    }
    private void increaseScore(int base){
        if(scoreMode == sMode.DEFAULT){
            System.out.println("You on Default Mode score+="+Integer.toString(base));
            score+=base;
        }else if(scoreMode==sMode.DOUBLE){
            System.out.println("You on Double Mode score+="+Integer.toString(2*base));
            score+=(2*base);
        }else if(scoreMode==sMode.HALF){
            System.out.println("You on Half Mode score+="+Integer.toString((int)(0.5*base)));
            score+= (int) (0.5*base);
        }
        System.out.println("Current Score :" + Integer.toString(score));
    }


}