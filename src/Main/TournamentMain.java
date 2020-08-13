package Main;
import controller.TournamentController;
import javafx.application.*;
import javafx.stage.Stage;
import model.Tournament;
import view.AbstractTournamentView;
import view.TournamentView;

public class TournamentMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Tournament theModel = new Tournament();
		AbstractTournamentView theView1 = new TournamentView(arg0);
		TournamentController controller1 = new TournamentController(theModel, theView1);
	}

}
