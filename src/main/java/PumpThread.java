import javafx.application.Platform;
import javafx.concurrent.Task;

public class PumpThread extends Task {
    @Override
    protected Object call() throws Exception {

        while(true){
            Thread.sleep(50);


            if(SceneController.currentState) {
                Platform.runLater(() -> {
                    try {
                        //FX Stuff done here
                        SceneController.pumping(-0.005);
                    } finally {

                    }
                });
            }

        }

    }
}
