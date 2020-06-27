import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    protected static Rectangle myMyState;
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


    //private static int freshGoal;

    /*static {
        try {
            freshGoal = ReadGoal.readMyGoal();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    private Label performanceLabel;

    @FXML
    private Label display;

    @FXML
    private Label currentGoal;

    @FXML
    private Button save;

    @FXML
    private Rectangle myState;

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

    private Button exit;
    public SceneController() throws SocketException {
    }


    //static RotateTransition rt = new RotateTransition(Duration.millis(3000), myCogwheele1);
    //static RotateTransition rt2 = new RotateTransition(Duration.millis(5500), myCogwheele2);


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

        myProgressIndicator.setStyle(" -fx-progress-color: gray;");









        Trigger trigger = new Trigger();
        new Thread(trigger).start();

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


    }

    public static void setLabelText(String text){
        myDisplay.setText(text);
    }




    public static void setDisplayServerOffline() throws InterruptedException {


    }



    public void checkBox() throws InterruptedException {



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
            //LoadFile.timeAtLoad = 0;
            //guiMessageSender.sendState(true);

        }else{
            myMyState.setFill(Color.RED);
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
    }
    public static void setMyCurrentGoal(){
        myCurrentGoal.setText((Integer.toString(ReadGoal.freshGoal)) +" hours/day.");
    }

    public static void setAveragePerformanceLabel() throws FileNotFoundException {
        ReadGoal.readMyGoal();
        if(TextToInt.averageDaily < ReadGoal.freshGoal-2) {
            myPerformanceLabel.setText("Very Poor!");
        }
        else if(TextToInt.averageDaily >= ReadGoal.freshGoal){
            myPerformanceLabel.setText("Excellent!");
        }
        else{
            myPerformanceLabel.setText("Weak");
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

    public static void rotateCogwheel1(int in) throws InterruptedException {
        /*while(true) {
            for (int i = 1; i < 361; i++) {
                myCogwheele1.setRotate(myCogwheele1.getRotate() + i);
            }
            Thread.sleep(100);
        }*/

        RotateTransition rt = new RotateTransition(Duration.millis(3000), myCogwheele1);
        RotateTransition rt2 = new RotateTransition(Duration.millis(5500), myCogwheele2);
        RotateTransition rt3 = new RotateTransition(Duration.millis(5150), myShadow);

        switch (in){
            case 1:
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
            rt.pause();
            rt2.stop();
            break;
        }


    }


    public void exit() {
        //guiMessageSender.sendState(false);
        //guiMessageSender.sendState(false);


        System.exit(0);
    }

}
