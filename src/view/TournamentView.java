package view;

import java.util.Vector;

import javax.swing.JOptionPane;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import listeners.TeamUIEventListener;

public class TournamentView implements AbstractTournamentView {
	private Vector<TeamUIEventListener> allListeners = new Vector<TeamUIEventListener>();
	private ComboBox<String> cmbAllTeams = new ComboBox<String>();
	Label lblName;
	TextField tfName;
	Button btnAddTeam;
	Button btnStartChamp;
	Label lblAllTeams;
	Button btnRemoveTeam;

	ToggleGroup tglSportType = new ToggleGroup();

	public TournamentView(Stage theStage) {
		theStage.setTitle("The Initialize");

		Image image = new Image("file:logo.jpg");

		BackgroundSize backgroundSize = new BackgroundSize(180, 100, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);

		RadioButton rdoTenis = new RadioButton("Tennis");
		RadioButton rdoSoccer = new RadioButton("Soccer");
		RadioButton rdoBasketball = new RadioButton("Basketball");

		rdoTenis.setToggleGroup(tglSportType);
		rdoSoccer.setToggleGroup(tglSportType);
		rdoBasketball.setToggleGroup(tglSportType);

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setBackground(background);

		lblName = new Label();
		tfName = new TextField();
		btnAddTeam = new Button();
		btnStartChamp = new Button();
		lblAllTeams = new Label();
		btnRemoveTeam = new Button();
		btnStartChamp.setVisible(false);
		tfName.setVisible(false);
		lblName.setVisible(false);
		btnAddTeam.setVisible(false);
		lblAllTeams.setVisible(false);
		btnRemoveTeam.setVisible(false);
		cmbAllTeams.setVisible(false);

		rdoTenis.setOnAction(e -> {
			singleGame();
		});

		rdoSoccer.setOnAction(e -> {
			teamGame();
		});

		rdoBasketball.setOnAction(e -> {
			teamGame();
		});

		btnAddTeam.setOnAction(e -> {
			for (TeamUIEventListener l : allListeners)
				l.addTeamToUI(tfName.getText());

			tfName.setText("");
		});

		btnStartChamp.setOnAction(e -> {
			
			ChampionshipView champ;
			try {
				if (tglSportType.getSelectedToggle().equals(rdoTenis)) {
					champ = new ChampionshipView(cmbAllTeams.getItems(), "Tennis");
				}else if (tglSportType.getSelectedToggle().equals(rdoSoccer)) {
					champ = new ChampionshipView(cmbAllTeams.getItems(), "Soccer");
				}else {
					champ = new ChampionshipView(cmbAllTeams.getItems(), "Basketball");
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}
			
			
		});

		btnRemoveTeam.setOnAction(e -> {
			for (TeamUIEventListener l : allListeners) {
				if (cmbAllTeams.getValue() == null) {
					tournamentIsEmpty("There's not marked participant!");
				} else {
					l.removeTeamFromUI(getIdFromTeamString(cmbAllTeams.getValue()));
				}
			}
		});

		gpRoot.add(lblName, 0, 0);
		gpRoot.add(tfName, 1, 0);
		gpRoot.add(btnAddTeam, 2, 0);
		gpRoot.add(btnStartChamp, 1, 2);
		gpRoot.add(lblAllTeams, 0, 1);
		gpRoot.add(cmbAllTeams, 1, 1);
		gpRoot.add(btnRemoveTeam, 2, 1);
		gpRoot.add(rdoTenis, 4, 0);
		gpRoot.add(rdoSoccer, 4, 1);
		gpRoot.add(rdoBasketball, 4, 2);
		theStage.setScene(new Scene(gpRoot, 500, 120));
		theStage.show();
	}

	@Override
	public void registerListener(TeamUIEventListener newListener) {
		allListeners.add(newListener);
	}

	@Override
	public void addTeamToUI(String name, int id) {
		cmbAllTeams.getItems().add(name + "(" + id + ")");
	}

	@Override
	public void removeTeamFromUI(int id) {
		ObservableList<String> allLines = cmbAllTeams.getItems();
		for (int i = 0; i < allLines.size(); i++) {
			int lineId = getIdFromTeamString(allLines.get(i));
			if (lineId == id) {
				cmbAllTeams.getItems().remove(i);
			}
		}
	}

	private int getIdFromTeamString(String str) {
		System.out.println(str);
		String[] arr = str.split("[()]");
		System.out.println(arr);
		return Integer.parseInt(arr[1]);
	}

	@Override
	public void tournamentIsFull(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void tournamentIsEmpty(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	private void singleGame() {
		btnStartChamp.setVisible(true);
		btnStartChamp.setText("Start Championship");
		cmbAllTeams.setVisible(true);
		tfName.setVisible(true);
		lblName.setVisible(true);
		lblName.setText("Enter Player's name:");
		btnAddTeam.setVisible(true);
		btnAddTeam.setText("Add Player");
		lblAllTeams.setVisible(true);
		lblAllTeams.setText("All Players:");
		btnRemoveTeam.setVisible(true);
		btnRemoveTeam.setText("Remove Player");
	}

	private void teamGame() {
		btnStartChamp.setVisible(true);
		btnStartChamp.setText("Start Championship");
		cmbAllTeams.setVisible(true);
		tfName.setVisible(true);
		lblName.setVisible(true);
		lblName.setText("Enter Team's name:");
		btnAddTeam.setVisible(true);
		btnAddTeam.setText("Add Team");
		lblAllTeams.setVisible(true);
		lblAllTeams.setText("All Teams:");
		btnRemoveTeam.setVisible(true);
		btnRemoveTeam.setText("Remove Team");
	}
}