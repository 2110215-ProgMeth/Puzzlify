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

                        if (object.getA().getY() == 0 || object.getB().getY() == 0 || object.getC().getY() == 0
                                || object.getD().getY() == 0)//ถึงแตะบนสุด + 1
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
                    group.getChildren().addAll(a.getA(), a.getB(), a.getC(), a.getD());

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
        if (form.getA().getX() + MOVE <= XMAX - SIZE && form.getB().getX() + MOVE <= XMAX - SIZE
                && form.getC().getX() + MOVE <= XMAX - SIZE && form.getD().getX() + MOVE <= XMAX - SIZE) {
            int movea = MESH[((int) form.getA().getX() / SIZE) + 1][((int) form.getA().getY() / SIZE)];
            int moveb = MESH[((int) form.getB().getX() / SIZE) + 1][((int) form.getB().getY() / SIZE)];
            int movec = MESH[((int) form.getC().getX() / SIZE) + 1][((int) form.getC().getY() / SIZE)];
            int moved = MESH[((int) form.getD().getX() / SIZE) + 1][((int) form.getD().getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) { // every mesh is till empty? (0)
                form.getA().setX(form.getA().getX() + MOVE);
                form.getB().setX(form.getB().getX() + MOVE);
                form.getC().setX(form.getC().getX() + MOVE);
                form.getD().setX(form.getD().getX() + MOVE);
            }
        }
    }

    private void moveLeft(Form form) {
        if (form.getA().getX() - MOVE >= 0 && form.getB().getX() - MOVE >= 0 && form.getC().getX() - MOVE >= 0
                && form.getD().getX() - MOVE >= 0) {
            int movea = MESH[((int) form.getA().getX() / SIZE) - 1][((int) form.getA().getY() / SIZE)];
            int moveb = MESH[((int) form.getB().getX() / SIZE) - 1][((int) form.getB().getY() / SIZE)];
            int movec = MESH[((int) form.getC().getX() / SIZE) - 1][((int) form.getC().getY() / SIZE)];
            int moved = MESH[((int) form.getD().getX() / SIZE) - 1][((int) form.getD().getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.getA().setX(form.getA().getX() - MOVE);
                form.getB().setX(form.getB().getX() - MOVE);
                form.getC().setX(form.getC().getX() - MOVE);
                form.getD().setX(form.getD().getX() - MOVE);
            }
        }
    }
    private void moveTurn(Form form) {//หมุน แต่ละแบบหมุนได้ 4 form มี 7 แบบ รวม 28 form
        int f = form.getForm();//แยกแต่ละrectangleมาเลื่อน
        Block a = form.getA();
        Block b = form.getB();
        Block c = form.getC();
        Block d = form.getD();
        switch (form.getBlockType()) {
            case J:
                if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
                    moveRight(form.getA());
                    moveDown(form.getA());
                    moveDown(form.getC());
                    moveLeft(form.getC());
                    moveDown(form.getD());
                    moveDown(form.getD());
                    moveLeft(form.getD());
                    moveLeft(form.getD());
                    form.changeForm();//จาก 1->2
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
                    moveDown(form.getA());
                    moveLeft(form.getA());
                    moveLeft(form.getC());
                    moveUp(form.getC());
                    moveLeft(form.getD());
                    moveLeft(form.getD());
                    moveUp(form.getD());
                    moveUp(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
                    moveLeft(form.getA());
                    moveUp(form.getA());
                    moveUp(form.getC());
                    moveRight(form.getC());
                    moveUp(form.getD());
                    moveUp(form.getD());
                    moveRight(form.getD());
                    moveRight(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
                    moveUp(form.getA());
                    moveRight(form.getA());
                    moveRight(form.getC());
                    moveDown(form.getC());
                    moveRight(form.getD());
                    moveRight(form.getD());
                    moveDown(form.getD());
                    moveDown(form.getD());
                    form.changeForm();
                    break;
                }
                break;
            case L:
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
                    moveRight(form.getA());
                    moveDown(form.getA());
                    moveUp(form.getC());
                    moveRight(form.getC());
                    moveUp(form.getB());
                    moveUp(form.getB());
                    moveRight(form.getB());
                    moveRight(form.getB());
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
                    moveDown(form.getA());
                    moveLeft(form.getA());
                    moveRight(form.getB());
                    moveRight(form.getB());
                    moveDown(form.getB());
                    moveDown(form.getB());
                    moveRight(form.getC());
                    moveDown(form.getC());
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
                    moveLeft(form.getA());
                    moveUp(form.getA());
                    moveDown(form.getC());
                    moveLeft(form.getC());
                    moveDown(form.getB());
                    moveDown(form.getB());
                    moveLeft(form.getB());
                    moveLeft(form.getB());
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
                    moveUp(form.getA());
                    moveRight(form.getA());
                    moveLeft(form.getB());
                    moveLeft(form.getB());
                    moveUp(form.getB());
                    moveUp(form.getB());
                    moveLeft(form.getC());
                    moveUp(form.getC());
                    form.changeForm();
                    break;
                }
                break;
            case O:
                break;
            case S:
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    moveDown(form.getA());
                    moveLeft(form.getA());
                    moveLeft(form.getC());
                    moveUp(form.getC());
                    moveUp(form.getD());
                    moveUp(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    moveUp(form.getA());
                    moveRight(form.getA());
                    moveRight(form.getC());
                    moveDown(form.getC());
                    moveDown(form.getD());
                    moveDown(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    moveDown(form.getA());
                    moveLeft(form.getA());
                    moveLeft(form.getC());
                    moveUp(form.getC());
                    moveUp(form.getD());
                    moveUp(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    moveUp(form.getA());
                    moveRight(form.getA());
                    moveRight(form.getC());
                    moveDown(form.getC());
                    moveDown(form.getD());
                    moveDown(form.getD());
                    form.changeForm();
                    break;
                }
                break;
            case T:
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
                    moveUp(form.getA());
                    moveRight(form.getA());
                    moveDown(form.getD());
                    moveLeft(form.getD());
                    moveLeft(form.getC());
                    moveUp(form.getC());
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
                    moveRight(form.getA());
                    moveDown(form.getA());
                    moveLeft(form.getD());
                    moveUp(form.getD());
                    moveUp(form.getC());
                    moveRight(form.getC());
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
                    moveDown(form.getA());
                    moveLeft(form.getA());
                    moveUp(form.getD());
                    moveRight(form.getD());
                    moveRight(form.getC());
                    moveDown(form.getC());
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
                    moveLeft(form.getA());
                    moveUp(form.getA());
                    moveRight(form.getD());
                    moveDown(form.getD());
                    moveDown(form.getC());
                    moveLeft(form.getC());
                    form.changeForm();
                    break;
                }
                break;
            case Z:
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    moveUp(form.getB());
                    moveRight(form.getB());
                    moveLeft(form.getC());
                    moveUp(form.getC());
                    moveLeft(form.getD());
                    moveLeft(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    moveDown(form.getB());
                    moveLeft(form.getB());
                    moveRight(form.getC());
                    moveDown(form.getC());
                    moveRight(form.getD());
                    moveRight(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    moveUp(form.getB());
                    moveRight(form.getB());
                    moveLeft(form.getC());
                    moveUp(form.getC());
                    moveLeft(form.getD());
                    moveLeft(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    moveDown(form.getB());
                    moveLeft(form.getB());
                    moveRight(form.getC());
                    moveDown(form.getC());
                    moveRight(form.getD());
                    moveRight(form.getD());
                    form.changeForm();
                    break;
                }
                break;
            case I:
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    moveUp(form.getA());
                    moveUp(form.getA());
                    moveRight(form.getA());
                    moveRight(form.getA());
                    moveUp(form.getB());
                    moveRight(form.getB());
                    moveDown(form.getD());
                    moveLeft(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    moveDown(form.getA());
                    moveDown(form.getA());
                    moveLeft(form.getA());
                    moveLeft(form.getA());
                    moveDown(form.getB());
                    moveLeft(form.getB());
                    moveUp(form.getD());
                    moveRight(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    moveUp(form.getA());
                    moveUp(form.getA());
                    moveRight(form.getA());
                    moveRight(form.getA());
                    moveUp(form.getB());
                    moveRight(form.getB());
                    moveDown(form.getD());
                    moveLeft(form.getD());
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    moveDown(form.getA());
                    moveDown(form.getA());
                    moveLeft(form.getA());
                    moveLeft(form.getA());
                    moveDown(form.getB());
                    moveLeft(form.getB());
                    moveUp(form.getD());
                    moveRight(form.getD());
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
        while(form.getA().getY() + MOVE < YMAX && form.getB().getY() + MOVE < YMAX && form.getC().getY() + MOVE < YMAX && form.getD().getY() + MOVE < YMAX && !moveA(form) && !moveB(form) && !moveC(form) && !moveD(form)){
            moveDown(form);
            increaseScore(2);
        }
    }

    private void moveDown(Form form) {
        if (form.getA().getY() == YMAX - SIZE ||
                form.getB().getY() == YMAX - SIZE ||
                form.getC().getY() == YMAX - SIZE ||
                form.getD().getY() == YMAX - SIZE ||
                moveA(form) ||
                moveB(form) ||
                moveC(form) ||
                moveD(form))
        {

            // if this block at the bottom.
            // set Mesh
            MESH[(int) form.getA().getX() / SIZE][(int) form.getA().getY() / SIZE] = 1;
            MESH[(int) form.getB().getX() / SIZE][(int) form.getB().getY() / SIZE] = 1;
            MESH[(int) form.getC().getX() / SIZE][(int) form.getC().getY() / SIZE] = 1;
            MESH[(int) form.getD().getX() / SIZE][(int) form.getD().getY() / SIZE] = 1;


            removeRows(group); // remove row, check inside.

            // create next block
            Form a = nextObj;
            nextObj = Form.makeRect();
            object = a;
            group.getChildren().addAll(a.getA(), a.getB(), a.getC(), a.getD());
            moveOnKeyPress(a);
        }

        if (form.getA().getY() + MOVE < YMAX && form.getB().getY() + MOVE < YMAX && form.getC().getY() + MOVE < YMAX
                && form.getD().getY() + MOVE < YMAX) {
            int movea = MESH[(int) form.getA().getX() / SIZE][((int) form.getA().getY() / SIZE) + 1];
            int moveb = MESH[(int) form.getB().getX() / SIZE][((int) form.getB().getY() / SIZE) + 1];
            int movec = MESH[(int) form.getC().getX() / SIZE][((int) form.getC().getY() / SIZE) + 1];
            int moved = MESH[(int) form.getD().getX() / SIZE][((int) form.getD().getY() / SIZE) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.getA().setY(form.getA().getY() + MOVE);
                form.getB().setY(form.getB().getY() + MOVE);
                form.getC().setY(form.getC().getY() + MOVE);
                form.getD().setY(form.getD().getY() + MOVE);
            }
        }
    }

    // move Block function.
    // check if this below has data?
    private boolean moveA(Form form) {
        return (MESH[(int) form.getA().getX() / SIZE][((int) form.getA().getY() / SIZE) + 1] == 1);
    }

    private boolean moveB(Form form) {
        return (MESH[(int) form.getB().getX() / SIZE][((int) form.getB().getY() / SIZE) + 1] == 1);
    }

    private boolean moveC(Form form) {
        return (MESH[(int) form.getC().getX() / SIZE][((int) form.getC().getY() / SIZE) + 1] == 1);
    }

    private boolean moveD(Form form) {
        return (MESH[(int) form.getD().getX() / SIZE][((int) form.getD().getY() / SIZE) + 1] == 1);
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