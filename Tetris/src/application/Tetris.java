package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import Block.BasicStructure.Block;
import Block.BasicStructure.Skillable;
import UI.*;
import Utils.Utils;
import Utils.sMode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Tetris extends Application {
    //variable ต่างๆ
    public static final int MOVE = 35;//เคลื่อนที่ครั้งละblock
    public static final int SIZE = 35;//ขนาดblock
    public static int XMAX = SIZE * 12;//ความยาวแกนxของช่องเล่นเกม
    public static int YMAX = SIZE * 24;//ความยาวแกนyของช่องเล่น
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];//เป็นการตีตาราง

    private Pane group = new Pane();//สร้างpane
    private VBox UI = new VBox();
    private StackPane gameLayerPane = new StackPane();
    private HBox gameROOT = new HBox();
    private Parent startROOT;
    private Parent guideROOT;
    private Form object;//ของชิ้นปัจจุบัน
    private Scene gamescene ;//XMAX + 150 เพราะส่วนขวามีที่ไม่ใช่พื้นที่เกมด้วย
    private Scene mainscene ;
    private Scene helpscene;
    private int score = 0;//คะแนนที่ได้ เพิ่มได้จากการกด เลื่อนลง || deleterow
    private int top = 0;//สำหรับดูว่าเกินหรือยัง
    private boolean game = false;//ยังรอดอยู่ไหม
    private Form nextObj ;//ของชิ้นต่อไป
    private int linesNo = 0;//จำนวนแถวที่deleteได้

    public static sMode scoreMode = sMode.DEFAULT;//ใช้มาเลือกการเพิ่มคะแนน

    private AudioClip hs;
    private AudioClip rotateSound;
    private AudioClip tap;
    private AudioClip clearLine;
    private AudioClip bgSong;

    public ScoreBox conScore;
    public ScoreBox conLv;

    public StartScene startSceneCon;
    private final int startTime = 4;
    private int seconds = startTime;

    private ImageView nextObjImg = new ImageView(new Image("/BlockSprite/FormSprite/Transparent64x64.png"));
    public CountDownBox countDownCon;
    public ExitBox exitBoxCon;
    public GuideScene guideSceneCon;

    private BuffUI x2UI;
    private BuffUI d2UI;

    private boolean isFirstTime = false;




    public static void main(String[] args) {//main
        launch(args);//จะไปเรียกstart
    }


    @Override
    public void start(Stage stage) throws Exception {

        stage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
            bgSong.stop();
        });
        stage.setResizable(false);

        // FXML LOADER
        // load start scene
        FXMLLoader loadStartScene = new FXMLLoader(getClass().getResource("/FXML/StartScene.fxml"));
        startROOT = loadStartScene.load();
        startSceneCon = loadStartScene.getController();

        startSceneCon.getStartBtn().addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            startSceneCon.OnStartBtnReleased();
            stage.setScene(gamescene);
        });

        startSceneCon.getGuideBtn().addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            startSceneCon.onGuidBtnReleased();
            stage.setScene(helpscene);
        });


        mainscene = new Scene(startROOT,480 + 300,960 + 50);
        // load guide scene
        FXMLLoader loadGuideScene = new FXMLLoader(getClass().getResource("/FXML/GuideScene.fxml"));
        guideROOT = loadGuideScene.load();
        guideSceneCon = loadGuideScene.getController();
        guideSceneCon.getBackBtn().addEventHandler(MouseEvent.MOUSE_PRESSED,e->{
            guideSceneCon.onBackBtnPressed();
        });
        guideSceneCon.getBackBtn().addEventHandler(MouseEvent.MOUSE_RELEASED,e->{
            stage.setScene(mainscene);
        });

        helpscene = new Scene(guideROOT,480 + 300,960 + 50);

        // load countdown
        FXMLLoader loadCountDown = new FXMLLoader(getClass().getResource("/FXML/CountDownBox.fxml"));
        loadCountDown.load();
        countDownCon = loadCountDown.getController();

        // load exit box
        FXMLLoader loadExitBox= new FXMLLoader(getClass().getResource("/FXML/ExitBox.fxml"));
        GridPane exitBox = loadExitBox.load();
        exitBoxCon = loadExitBox.getController();

        exitBoxCon.getRestartBtn().addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            exitBoxCon.onRestartBtnPressed();
        });
        exitBoxCon.getRestartBtn().addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            exitBoxCon.onRestartBtnReleased();
            stage.setScene(mainscene);
            exitBoxCon.getBox().setVisible(false);
            restartGame();
        });



        // load sound
        hs = new AudioClip(this.getClass().getResource("/SFX/harddrop.mp3").toExternalForm());
        hs.setVolume(0.2);
        rotateSound = new AudioClip(this.getClass().getResource("/SFX/rotate.mp3").toExternalForm());
        tap = new AudioClip(this.getClass().getResource("/SFX/tap.mp3").toExternalForm());
        tap.setVolume(0.2);
        clearLine = new AudioClip(this.getClass().getResource("/SFX/clearLine.wav").toExternalForm());
        clearLine.setVolume(.2);
        bgSong = new AudioClip(this.getClass().getResource("/Totoro.mp3").toExternalForm());
        bgSong.setVolume(0.2);
        bgSong.play();

        gamescene = new Scene(gameLayerPane, 480 + 300, 960+50);
        gamescene.getStylesheets().add(this.getClass().getResource("/main.css").toExternalForm());


        group.setId("GamePane");
        group.setPrefWidth(XMAX);
        group.setMaxHeight(YMAX);
        group.setPadding(new Insets(20));


        for (int[] a : MESH) {Arrays.fill(a, 0);}


        gameROOT.setPadding(new Insets(20));
        gameROOT.setSpacing(10);
        gameROOT.setAlignment(Pos.BOTTOM_CENTER);

         FXMLLoader loadScoreText= new FXMLLoader();
         Parent st = (Parent) loadScoreText.load(getClass().getResource("/FXML/ScoreBox.fxml").openStream());
         conScore = loadScoreText.getController();

         conScore.setLabelText("Score:");

        FXMLLoader loadLevelText= new FXMLLoader();
        Parent lv = (Parent) loadLevelText.load(getClass().getResource("/FXML/ScoreBox.fxml").openStream());
        conLv=  loadLevelText.getController();

        conLv.setLabelText("Level:");


        setBackground();

        HBox buffUIGroup = new HBox();
        buffUIGroup.setSpacing(10);
        x2UI = new BuffUI("/BlockSprite/x2.png", "/BlockSprite/x2D.png");
        x2UI.setFitWidth(50);
        x2UI.setFitHeight(50);
        d2UI = new BuffUI("/BlockSprite/d2.png", "/BlockSprite/d2d.png");
        d2UI.setFitWidth(50);
        d2UI.setFitHeight(50);
        buffUIGroup.getChildren().addAll(x2UI,d2UI);

        UI.setAlignment(Pos.TOP_CENTER);
        UI.setPadding(new Insets(10));
        UI.setSpacing(10);
        UI.setMaxHeight(YMAX);
        UI.setBorder(new Border(new BorderStroke(Color.WHITESMOKE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        Background UIBackGound= new Background(new BackgroundFill(Color.valueOf("#161A30"),CornerRadii.EMPTY,Insets.EMPTY));
        UI.setBackground(UIBackGound);

        UI.getChildren().addAll(nextObjImg,st, lv,buffUIGroup);//เพิ่มลงในpane

        nextObjImg.setFitWidth(200);
        nextObjImg.setFitHeight(200);

        gameROOT.getChildren().addAll(group,UI);
        gameROOT.setId("GameROOT");

        gameLayerPane.setAlignment(Pos.CENTER);
        gameLayerPane.getChildren().addAll(gameROOT,countDownCon.countDownImg,exitBox);


        stage.setScene(mainscene);
        stage.setTitle("Puzzlify");
        stage.show();

        Timer fall = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {//ใช้เพราะมีการเปลี่ยน user interface
                    public void run() {
                        nextObjImg.setImage(new Image(Utils.getFormSpritePath(nextObj.getBlockType())));

                        if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0
                                || object.d.getY() == 0)//ถึงแตะบนสุด + 1
                            top++;
                        else
                            top = 0; //ลดลงมาทันก็นับใหม่

                        if (top == 2) {//เกินไป 1 block = จบ
                            // GAME OVER
                            exitBoxCon.onPaneActive(score, linesNo);
                            game = false;//จบเกม
                        }

                        if (game) {
//                            Controller.moveDown(object);//เลื่อนลงเรื่อยๆ เสมออยู่แล้ว
                            moveDown(object);
                        }

                        x2UI.isActive(scoreMode == sMode.DOUBLE);
                        d2UI.isActive(scoreMode == sMode.HALF);
                    }
                });
            }
        };


        startSceneCon.getStartBtn().addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            startSceneCon.OnStartBtnPressed();
            countdown(fall, task);
        });
    }
    private void countdown(Timer fall, TimerTask task){
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        if(time!=null){
            time.stop();
        }
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                seconds--;
                System.out.println(seconds);
                countDownCon.countDown((int)seconds);
                if(seconds<=0){
                    time.stop();
                    game = true;
                    if(!isFirstTime){
                        fall.schedule(task, 0, 300);//period = เว้นว่างระหว่างรอบ จะtaskซ้ำๆหลังจากdelay
                        isFirstTime= true;
                    }
                    nextObj = Form.makeRect();

                    Form a = nextObj;
                    group.getChildren().addAll(a.a, a.b, a.c, a.d);

                    moveOnKeyPress(a);
                    object = a;
                    nextObj = Form.makeRect();
                }
            }
        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }



    public void moveOnKeyPress(Form form) {//แต่ละอันทำอะไรบ้าง

        gamescene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!game){return;}
                switch (event.getCode()) {
                    case RIGHT:
                        moveRight(form);
                        getTap().play();
                        break;
                    case DOWN:
                        moveDown(form);
                        getTap().play();
                        increaseScore(2);
                        break;
                    case LEFT:
                        moveLeft(form);
                        getTap().play();
                        break;
                    case UP:
                        moveTurn(form);
                        getRotateSound().play();
                        break;
                    case ENTER:// ทำให้ลงถึงสุดเลย
                        moveToBottom(form);
                        getHs().play();
                        break;
                }
            }
        });
    }

    private void moveRight(Form form) {
        if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE <= XMAX - SIZE
                && form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX - SIZE) {
            int movea = MESH[((int) form.a.getX() / SIZE) + 1][((int) form.a.getY() / SIZE)];
            int moveb = MESH[((int) form.b.getX() / SIZE) + 1][((int) form.b.getY() / SIZE)];
            int movec = MESH[((int) form.c.getX() / SIZE) + 1][((int) form.c.getY() / SIZE)];
            int moved = MESH[((int) form.d.getX() / SIZE) + 1][((int) form.d.getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) { // every mesh is till empty? (0)
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }
        }
    }

    private void moveLeft(Form form) {
        if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 && form.c.getX() - MOVE >= 0
                && form.d.getX() - MOVE >= 0) {
            int movea = MESH[((int) form.a.getX() / SIZE) - 1][((int) form.a.getY() / SIZE)];
            int moveb = MESH[((int) form.b.getX() / SIZE) - 1][((int) form.b.getY() / SIZE)];
            int movec = MESH[((int) form.c.getX() / SIZE) - 1][((int) form.c.getY() / SIZE)];
            int moved = MESH[((int) form.d.getX() / SIZE) - 1][((int) form.d.getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }
    private void moveTurn(Form form) {//หมุน แต่ละแบบหมุนได้ 4 form มี 7 แบบ รวม 28 form
        int f = form.form;//แยกแต่ละrectangleมาเลื่อน
        Block a = form.a;
        Block b = form.b;
        Block c = form.c;
        Block d = form.d;
        switch (form.getBlockType()) {
            case J:
                if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
                    moveRight(form.a);
                    moveDown(form.a);
                    moveDown(form.c);
                    moveLeft(form.c);
                    moveDown(form.d);
                    moveDown(form.d);
                    moveLeft(form.d);
                    moveLeft(form.d);
                    form.changeForm();//จาก 1->2
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
                    moveDown(form.a);
                    moveLeft(form.a);
                    moveLeft(form.c);
                    moveUp(form.c);
                    moveLeft(form.d);
                    moveLeft(form.d);
                    moveUp(form.d);
                    moveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
                    moveLeft(form.a);
                    moveUp(form.a);
                    moveUp(form.c);
                    moveRight(form.c);
                    moveUp(form.d);
                    moveUp(form.d);
                    moveRight(form.d);
                    moveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
                    moveUp(form.a);
                    moveRight(form.a);
                    moveRight(form.c);
                    moveDown(form.c);
                    moveRight(form.d);
                    moveRight(form.d);
                    moveDown(form.d);
                    moveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case L:
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
                    moveRight(form.a);
                    moveDown(form.a);
                    moveUp(form.c);
                    moveRight(form.c);
                    moveUp(form.b);
                    moveUp(form.b);
                    moveRight(form.b);
                    moveRight(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
                    moveDown(form.a);
                    moveLeft(form.a);
                    moveRight(form.b);
                    moveRight(form.b);
                    moveDown(form.b);
                    moveDown(form.b);
                    moveRight(form.c);
                    moveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
                    moveLeft(form.a);
                    moveUp(form.a);
                    moveDown(form.c);
                    moveLeft(form.c);
                    moveDown(form.b);
                    moveDown(form.b);
                    moveLeft(form.b);
                    moveLeft(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
                    moveUp(form.a);
                    moveRight(form.a);
                    moveLeft(form.b);
                    moveLeft(form.b);
                    moveUp(form.b);
                    moveUp(form.b);
                    moveLeft(form.c);
                    moveUp(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case O:
                break;
            case S:
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    moveDown(form.a);
                    moveLeft(form.a);
                    moveLeft(form.c);
                    moveUp(form.c);
                    moveUp(form.d);
                    moveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    moveUp(form.a);
                    moveRight(form.a);
                    moveRight(form.c);
                    moveDown(form.c);
                    moveDown(form.d);
                    moveDown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    moveDown(form.a);
                    moveLeft(form.a);
                    moveLeft(form.c);
                    moveUp(form.c);
                    moveUp(form.d);
                    moveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    moveUp(form.a);
                    moveRight(form.a);
                    moveRight(form.c);
                    moveDown(form.c);
                    moveDown(form.d);
                    moveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case T:
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
                    moveUp(form.a);
                    moveRight(form.a);
                    moveDown(form.d);
                    moveLeft(form.d);
                    moveLeft(form.c);
                    moveUp(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
                    moveRight(form.a);
                    moveDown(form.a);
                    moveLeft(form.d);
                    moveUp(form.d);
                    moveUp(form.c);
                    moveRight(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
                    moveDown(form.a);
                    moveLeft(form.a);
                    moveUp(form.d);
                    moveRight(form.d);
                    moveRight(form.c);
                    moveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
                    moveLeft(form.a);
                    moveUp(form.a);
                    moveRight(form.d);
                    moveDown(form.d);
                    moveDown(form.c);
                    moveLeft(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case Z:
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    moveUp(form.b);
                    moveRight(form.b);
                    moveLeft(form.c);
                    moveUp(form.c);
                    moveLeft(form.d);
                    moveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    moveDown(form.b);
                    moveLeft(form.b);
                    moveRight(form.c);
                    moveDown(form.c);
                    moveRight(form.d);
                    moveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    moveUp(form.b);
                    moveRight(form.b);
                    moveLeft(form.c);
                    moveUp(form.c);
                    moveLeft(form.d);
                    moveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    moveDown(form.b);
                    moveLeft(form.b);
                    moveRight(form.c);
                    moveDown(form.c);
                    moveRight(form.d);
                    moveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case I:
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    moveUp(form.a);
                    moveUp(form.a);
                    moveRight(form.a);
                    moveRight(form.a);
                    moveUp(form.b);
                    moveRight(form.b);
                    moveDown(form.d);
                    moveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    moveDown(form.a);
                    moveDown(form.a);
                    moveLeft(form.a);
                    moveLeft(form.a);
                    moveDown(form.b);
                    moveLeft(form.b);
                    moveUp(form.d);
                    moveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    moveUp(form.a);
                    moveUp(form.a);
                    moveRight(form.a);
                    moveRight(form.a);
                    moveUp(form.b);
                    moveRight(form.b);
                    moveDown(form.d);
                    moveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    moveDown(form.a);
                    moveDown(form.a);
                    moveLeft(form.a);
                    moveLeft(form.a);
                    moveDown(form.b);
                    moveLeft(form.b);
                    moveUp(form.d);
                    moveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
        }
    }

    public void removeRows(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();//เก็บว่าแถวไหนเต็มบ้าง
        ArrayList<Node> newrects = new ArrayList<Node>();
        ArrayList<Skillable> Specialblock = new ArrayList<Skillable>();

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
            clearLine.play();
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
                            pane.getChildren().remove(node);
                            if(a instanceof Skillable){
                                Specialblock.add((Skillable) a);
//                                ((Skillable) a).activeSkill(group);
                            }

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
                if(!Specialblock.isEmpty()){
                    for(Skillable a : Specialblock){
                        a.activeSkill(group);
                    }
                }
                conLv.setScore(linesNo);
                conScore.setScore(score);
            } while (lines.size() > 0);
        }
    }

    private void moveDown(Rectangle rect) {
        if (rect.getY() + MOVE < YMAX)
            rect.setY(rect.getY() + MOVE);
    }


    private void moveRight(Rectangle rect) {
        if (rect.getX() + MOVE <= XMAX - SIZE)
            rect.setX(rect.getX() + MOVE);
    }

    private void moveLeft(Rectangle rect) {
        if (rect.getX() - MOVE >= 0)
            rect.setX(rect.getX() - MOVE);
    }

    private void moveUp(Rectangle rect) {
        if (rect.getY() - MOVE > 0)
            rect.setY(rect.getY() - MOVE);
    }

    private void moveToBottom(Form form){
        while(form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX && form.d.getY() + MOVE < YMAX && !moveA(form) && !moveB(form) && !moveC(form) && !moveD(form)){
            moveDown(form);
            increaseScore(2);
        }
    }

    private void moveDown(Form form) {
        if (form.a.getY() == YMAX - SIZE ||
                form.b.getY() == YMAX - SIZE ||
                form.c.getY() == YMAX - SIZE ||
                form.d.getY() == YMAX - SIZE ||
                moveA(form) ||
                moveB(form) ||
                moveC(form) ||
                moveD(form))
        {

            // if this block at the bottom.
            // set Mesh
            MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;


            removeRows(group); // remove row, check inside.

            // create next block
            Form a = nextObj;
            nextObj = Form.makeRect();
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
                Rectangle wall = new Rectangle(SIZE-3,SIZE-3);
                wall.setStyle("-fx-fill:transparent; -fx-stroke: rgb(10,20, 40); -fx-stroke-width: 1");
                wall.setX(i*SIZE);
                wall.setY(j*SIZE);
                group.getChildren().add(wall);
            }
        }
    }
    public void increaseScore(int base){
        conScore.setScore(score);
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
    public static void clearGame(Pane pane){
        ArrayList<Node> rects = new ArrayList<Node>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
            pane.getChildren().remove(node);
        }
        rects.clear();
    }
    private void setScore(int i){
        if(i<0)i=0;
        score = 0;
    }
    private void setLinesNo(int i){
        if(i<0)i=0;
        linesNo = 0;
    }
    public AudioClip getTap(){
        return tap;
    }
    public AudioClip getHs(){
        return hs;
    }
    public AudioClip getRotateSound(){
        return rotateSound;
    }
    public Pane getGroup(){
        return group;
    }
    public void setObject(Form form){
        this.object = form;
    }
    public Form getObject(){
        return object;
    }
    public void setNextObj(Form form){
        this.nextObj = form;
    }
    public Form getNextObj(){
        return nextObj;
    }
    private void restartGame() {
        Platform.runLater(() -> {
            //clear all
            clearGame(group);
            setScore(0);
            setLinesNo(0);
            conScore.setScore(0);
            conLv.setScore(0);
            seconds=4;
        });
    }

}