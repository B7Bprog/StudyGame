import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;


//OFFLINE mod: see numbers.


public class SceneController implements Initializable {

    static Stage dialogStage;

    //String host = "86.1.51.222";

    //DatagramSocket socket = new DatagramSocket();
    // 1. mod: GuiMessageReceiver receiver = new GuiMessageReceiver(socket);
    //2. mod: GuiMessageSender guiMessageSender = new GuiMessageSender(socket, host);

    static boolean checkBoxChecked = false;

    protected static Label myDisplay;

    private static Button myExit;
    private static Button mySave;

    protected static Rectangle myPartnerState;
    protected static Circle mySign;
    protected static CheckBox myCheckBox;
    protected static Circle myMyState;
    protected static Rectangle myViewChart;
    protected static boolean currentState = false;
    private static TextField myGoal;
    protected static Label myCurrentGoal;
    private static Label myPerformanceLabel;
    private static ProgressIndicator myProgressIndicator;
    static long currentTime = 0;
    protected static boolean stageIsOff = true;
    private static ImageView myCogwheele1;
    private static ImageView myCogwheele2;
    private static ImageView myShadow;
    private static ImageView myInsignia;
    private static Label myLevel;
    public static ProgressBar myPump;
    static double current = 1.0;



    //private static int freshGoal;

    /*static {
        try {
            freshGoal = ReadGoal.readMyGoal();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    private ProgressBar pump;

    @FXML
    private Label performanceLabel;

    @FXML
    private Label display;

    @FXML
    private Label currentGoal;

    @FXML
    private Label level;

    @FXML
    private Button save;

    @FXML
    private Circle myState;

    @FXML
    private Rectangle viewChart;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TextField goal;

    @FXML
    private Circle sign;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private ImageView cogwheel1;

    @FXML
    private ImageView cogwheel2;

    @FXML
    private ImageView shadow;

    @FXML
    private ImageView insignia;

    @FXML

    private Button exit;
    public SceneController() throws SocketException {
    }


    static RotateTransition rt;
    static RotateTransition rt2;
    static RotateTransition rt3;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        myCheckBox = checkBox;
        myDisplay = display;
        myExit = exit;
        //myPartnerState = partnerState;
        myMyState = myState;
        mySign = sign;
        myViewChart = viewChart;
        mySave = save;
        myGoal = goal;
        myCurrentGoal = currentGoal;
        myPerformanceLabel = performanceLabel;
        myProgressIndicator = progressIndicator;
        myCogwheele1 = cogwheel1;
        myCogwheele2 = cogwheel2;
        myShadow = shadow;
        myInsignia = insignia;
        myLevel = level;
        myPump = pump;

        //myPump.setStyle(" -fx-control-inner-background: palegreen;" );
        myPump.setStyle(" -fx-accent: blue;");
        myProgressIndicator.setStyle(" -fx-progress-color: orange;");










        Trigger trigger = new Trigger();


        PumpThread pumpThread = new PumpThread();


        new Thread(trigger).start();
        new Thread(pumpThread).start();

        TimeCounter timeCounter = new TimeCounter();
        new Thread(timeCounter).start();

        try {
            ReadGoal.readMyGoal();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setMyCurrentGoal();
        try {
            setAveragePerformanceLabel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        //System.out.println("This is time here: " + time);
        /*try {
            int goal = getFreshGoal();
            System.out.println("This is progress parts: " + time + goal);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        //((((getCurrentTime() * 100) / (getFreshGoal()))) / 100)


        try {
            setProgressBar();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       rt = new RotateTransition(Duration.millis(3000), myCogwheele1);
        rt2 = new RotateTransition(Duration.millis(5500), myCogwheele2);
        rt3 = new RotateTransition(Duration.millis(5150), myShadow);


        try {
            setExperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setLabelText(String text){
        myDisplay.setText(text);
    }




    public static void setDisplayServerOffline() throws InterruptedException {


    }



    public void checkBox() throws InterruptedException, FileNotFoundException {



        if(checkBoxChecked){
            checkBoxChecked = false;
        }
        else{
            checkBoxChecked = true;
        }
        if(checkBoxChecked){
            myMyState.setFill(Color.GREEN);
            currentState = true;
            Main.loadTime();
            rotateCogwheel1(1);
            setExperties();
            //LoadFile.timeAtLoad = 0;
            //guiMessageSender.sendState(true);

        }else{
            myMyState.setFill(Color.rgb(117,1,1));
            currentState = false;

            rotateCogwheel1(2);
            //guiMessageSender.sendState(false);

        }

    }
    public void setPartnerSquareToGreen(){
        myPartnerState.setFill(Color.GREEN);

        //guiMessageSender.sendOnConfirmation();

    }
    public void setPartnerSquareToRed(){
        myPartnerState.setFill(Color.RED);

       // guiMessageSender.sendOffConfirmation();

    }

    public void popup() throws FileNotFoundException, InterruptedException {



        if(stageIsOff) {


            dialogStage = new Stage();
            //System.out.println("in popup");
            //Stage dialogStage = new Stage();
            Parent root = null;
            ReadGoal.readMyGoal();


            try {
                root = FXMLLoader.load(getClass().getResource("chart.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);

            }


            //dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);


            if (TextToInt.averageDaily < ReadGoal.freshGoal - 2) {
                scene.getStylesheets().add("chartStyleRed.css");
            } else if (TextToInt.averageDaily >= ReadGoal.freshGoal) {
                scene.getStylesheets().add("chartStyleGreen.css");
            } else {
                scene.getStylesheets().add("chartStyleOrange.css");
            }


            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);

            dialogStage.show();

            SceneControllerChart.setDailyAverage();


        }
        stageIsOff = false;

    }
    public void saveGoal() throws IOException {
        SaveGoal.SaveMyGoal("myGoal.txt",Integer.parseInt(goal.getText()));
        ReadGoal.readMyGoal();
        setMyCurrentGoal();
        setAveragePerformanceLabel();
        setProgressBar();
        setExperties();
    }
    public static void setMyCurrentGoal(){
        myCurrentGoal.setText((Integer.toString(ReadGoal.freshGoal)) +" hours/day.");
    }

    public static void setAveragePerformanceLabel() throws FileNotFoundException {
        ReadGoal.readMyGoal();
        if(TextToInt.averageDaily < ReadGoal.freshGoal-2) {
            myPerformanceLabel.setText("Not bad!");
        }
        else if(TextToInt.averageDaily >= ReadGoal.freshGoal){
            myPerformanceLabel.setText("Excellent!");
        }
        else{
            myPerformanceLabel.setText("Quite Good!");
        }
    }

    public static void setProgressBar() throws FileNotFoundException {
        if(getFreshGoal() != 0) {
           // System.out.println("We're in set progress bar method");
            myProgressIndicator.setProgress((((getCurrentTime() * 100) / (getFreshGoal()))) / 100f);

            //System.out.println("This is calculation: " + ((((getCurrentTime() * 100) / (getFreshGoal()))))/100f);


            //System.out.println("Progress value is: " + getCurrentTime() + " and " + (getFreshGoal()));
        }

    }

    public static long getCurrentTime() throws FileNotFoundException {
        LoadFile.readFile();
        return Long.parseLong(LoadFile.splitWords2[1]);

    }
    public static int getFreshGoal() throws FileNotFoundException {
        ReadGoal.readMyGoal();
        return (ReadGoal.freshGoal * 60);
    }



    public static void setExperties() throws FileNotFoundException {
        setMyLevel((int)((((getCurrentTime() * 100) / (getFreshGoal()))) ));
    }

    public static void rotateCogwheel1(int in) throws InterruptedException, FileNotFoundException {
        /*while(true) {
            for (int i = 1; i < 361; i++) {
                myCogwheele1.setRotate(myCogwheele1.getRotate() + i);
            }
            Thread.sleep(100);
        }*/

        /*RotateTransition rt = new RotateTransition(Duration.millis(3000), myCogwheele1);
        RotateTransition rt2 = new RotateTransition(Duration.millis(5500), myCogwheele2);
        RotateTransition rt3 = new RotateTransition(Duration.millis(5150), myShadow);*/

        switch (in){
            case 1:
            //setMyLevel((int)((((getCurrentTime() * 100) / (getFreshGoal()))) ));


            rt.setByAngle(360);
            rt.setCycleCount(Animation.INDEFINITE);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.play();


            rt2.setByAngle(-360);
            rt2.setCycleCount(Animation.INDEFINITE);
            rt2.setInterpolator(Interpolator.LINEAR);
            rt2.play();

                rt3.setByAngle(-360);
                rt3.setCycleCount(Animation.INDEFINITE);
                rt3.setInterpolator(Interpolator.LINEAR);
                rt3.play();
            break;
            case 2:
            System.out.println("Inside stoprotate");
            //rt.setDuration(Duration.millis(3000));
            rt.stop();
            rt2.stop();
            rt3.stop();

            break;
        }


    }

    public static void setInsignia(){
        Image img1 = new Image(Main.class.getResourceAsStream("1Private.png"));
        Image img2 = new Image(Main.class.getResourceAsStream("12SergeantMajorOfTheArmy.png"));

        myInsignia.setImage(img1);

    }
    public static void setMyLevel(int level){

        System.out.println("This is level now: " + level);

        if (level < 10) {


            String basic = "Fundamental Awareness \n - progress started...";
            myLevel.setText("   Your current level today: \n \n " + basic);
        }else if(level <20) {

            String better = "Great start. \n - Keep up the good work!";
            myLevel.setText("   Your current level today: \n \n " + better);
        }
        else if(level <30) {

            String better = "Going strong. \n - Very good!";
            myLevel.setText("   Your current level today: \n \n " + better);
        }
        else if(level <40) {

            String better = "You are doing great here! \n - Great effort so far.";
            myLevel.setText("   Your current level today: \n \n " + better);
        }
        else if(level <50) {

            String better = "That's the spirit! \n - You are getting there!";
            myLevel.setText("   Your current level today: \n \n " + better);
        }
        else if(level <60) {

            String better = "Done great so far! \n - Show what you can do!";
            myLevel.setText("   Your current level today: \n \n " + better);
        }else if(level <70) {

            String better = "Excellent performance up to now. \n - Carry on!";
            myLevel.setText("   Your current level today: \n \n " + better);
        }else if(level <80) {

            String better = "Amazing job. \n - So far very well done.";
            myLevel.setText("   Your current level today: \n \n " + better);
        }else if(level <90) {

            String better = "Almost there! \n - Do a little more!";
            myLevel.setText("   Your current level today: \n \n " + better);
        }else {
            String better = "You are awesome!!!  \n A real achiever and WINNER!";
            myLevel.setText("   Your current level today: \n \n " + better);
        }

    }

    public static void pumping (double in){


        current += in;

        myPump.setProgress(current);
        if(current < 0.1){
            current = 1.0;
        }
    }


    public void exit() {
        //guiMessageSender.sendState(false);
        //guiMessageSender.sendState(false);


        System.exit(0);
    }

}
