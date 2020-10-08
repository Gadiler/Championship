package view;

import java.util.Vector;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MatchView {
	private final int NUM_OF_TENNIS_MATCHES = 5;
	private final int NUM_OF_SOCCER_MATCHES = 2;
	private final int NUM_OF_BASCKETBALL_MATCHES = 4;

	private GridPane gpRoot = new GridPane();
	private GridPane gpRoot2 = new GridPane();
	private Label lblTeam1 = new Label();
	private Label lblTeam2 = new Label();
	private HBox hbMatches1 = new HBox();
	private HBox hbMatches2 = new HBox();
	private VBox vbMatches = new VBox();
	private String team1;
	private String team2;
	private Image image;
	private String selectedToggle;
	private int TieBreaker = 0;
	private int numOfMatchs = 0;

	public Vector<TextField> allScoreTeam1 = new Vector<TextField>();
	public Vector<TextField> allScoreTeam2 = new Vector<TextField>();
	public Vector<TextField> allPenaltiesTeam1 = new Vector<TextField>();
	public Vector<TextField> allPenaltiesTeam2 = new Vector<TextField>();
	public Button btDone = new Button("Done");
	public Stage theMatchStage = new Stage();

	public MatchView(String team1, String team2, String selectedToggle) {
		this.team1 = team1;
		this.team2 = team2;
		this.selectedToggle = selectedToggle;

		hbMatches1.setSpacing(20);
		hbMatches1.setAlignment(Pos.CENTER);
		hbMatches2.setSpacing(20);
		hbMatches2.setAlignment(Pos.CENTER);
		vbMatches.setSpacing(10);
		vbMatches.setAlignment(Pos.CENTER);

		if (numOfMatchs == 0)
			getTypeOfGame();

		BackgroundSize backgroundSize = new BackgroundSize(250, 100, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);

		theMatchStage.setTitle("The " + selectedToggle + " Match");

		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setBackground(background);
		lblTeam1.setText(team1);
		lblTeam2.setText(team2);

		btDone.setOnAction(e -> {
//			System.out.println(allPenaltiesTeam1.isEmpty());
			for (int i = 0; i < allScoreTeam1.size(); i++) {
				allScoreTeam1.get(i).setDisable(true);
				allScoreTeam2.get(i).setDisable(true);
			}
			theMatchStage.hide();
		});
		theMatchStage.setScene(new Scene(gpRoot, 500, 160));
		theMatchStage.show();
	}

	public String getTeam1() {
		return team1;
	}

	public String getTeam2() {
		return team2;
	}

	public TextField getTfTieBreakerTeam1() {
		return allScoreTeam1.get(TieBreaker);
	}

	public TextField getTfTieBreakerTeam2() {
		return allScoreTeam2.get(TieBreaker);
	}
	
	public Vector<TextField> getTfPenaltiesTeam1() {
		return allPenaltiesTeam1;
	}

	public Vector<TextField> getTfPenaltiesTeam2() {
		return allPenaltiesTeam2;
	}

	public void getStage() {
		theMatchStage.show();
	}

	private void getTypeOfGame() {
		hbMatches1.getChildren().add(lblTeam1);
		hbMatches2.getChildren().add(lblTeam2);

		if (selectedToggle.matches("Basketball")) {
			image = new Image("file:basketball match.jpg");
			numOfMatchs = NUM_OF_BASCKETBALL_MATCHES;
		} else if (selectedToggle.matches("Soccer")) {
			image = new Image("file:soccer match.jpg");
			numOfMatchs = NUM_OF_SOCCER_MATCHES;
		} else if (selectedToggle.matches("Tennis")) {
			image = new Image("file:tennis match.jpg");
			numOfMatchs = NUM_OF_TENNIS_MATCHES;
		}
		for (int i = 0; i < numOfMatchs; i++) {
			allScoreTeam1.add(new TextField());
			allScoreTeam2.add(new TextField());
			allScoreTeam1.get(i).setPrefWidth(50);
			allScoreTeam2.get(i).setPrefWidth(50);
			allScoreTeam1.get(i).setStyle("-fx-text-box-border: purple; -fx-focus-color: purple;");
			allScoreTeam2.get(i).setStyle("-fx-text-box-border: purple; -fx-focus-color: purple;");
			hbMatches1.getChildren().add(allScoreTeam1.get(i));
			hbMatches2.getChildren().add(allScoreTeam2.get(i));
		}

		vbMatches.getChildren().addAll(hbMatches1, hbMatches2, btDone);
		TieBreaker = allScoreTeam1.size();
		allScoreTeam1.add(new TextField());
		allScoreTeam2.add(new TextField());
		allScoreTeam1.get(TieBreaker).setStyle("-fx-text-box-border: yellow; -fx-focus-color: yellow;");
		allScoreTeam2.get(TieBreaker).setStyle("-fx-text-box-border: yellow; -fx-focus-color: yellow;");
		allScoreTeam1.get(TieBreaker).setPrefWidth(50);
		allScoreTeam2.get(TieBreaker).setPrefWidth(50);
		gpRoot.getChildren().add(vbMatches);
	}
//	Get the match - winner 
	public String getTheWinner() {
//		There's a difference between any type of sport and tiebreaker. 
//		for Soccer we created extra time and if still there's a tie -> penalties.
//		for Basketball and Tennis if there's a tie -> extra time.
		
		if (sumScore().equals("Tie") && selectedToggle.equals("Soccer")) {
			if (allScoreTeam1.get(TieBreaker).getText().isBlank() && allScoreTeam2.get(TieBreaker).getText().isBlank()) {
				theMatchStage.setTitle("The Extra Time");
				hbMatches1.getChildren().add(allScoreTeam1.get(TieBreaker));
				hbMatches2.getChildren().add(allScoreTeam2.get(TieBreaker));
				btDone.setOnAction(e -> {
					theMatchStage.hide();
				});
				return sumScore();
			} else {
				gpRoot2.setPadding(new Insets(10));
				gpRoot2.setAlignment(Pos.CENTER);
				gpRoot2.setHgap(10);
				gpRoot2.setVgap(10);
				gpRoot2.setBackground(gpRoot.getBackground());
				theMatchStage.setTitle("Penalties");
				
				hbMatches1 = new HBox();
				hbMatches2 = new HBox();
				vbMatches = new VBox();
				hbMatches1.setSpacing(20);
				hbMatches1.setAlignment(Pos.CENTER);
				hbMatches2.setSpacing(20);
				hbMatches2.setAlignment(Pos.CENTER);
				vbMatches.setSpacing(10);
				vbMatches.setAlignment(Pos.CENTER);

				hbMatches1.getChildren().addAll(lblTeam1);
				hbMatches2.getChildren().addAll(lblTeam2);
				
				final int numOfPenalties = 5;

				for (int i = 0; i < numOfPenalties; i++) {
					allPenaltiesTeam1.add(new TextField());
					allPenaltiesTeam2.add(new TextField());
					allPenaltiesTeam1.get(i).setPrefWidth(50);
					allPenaltiesTeam2.get(i).setPrefWidth(50);
					allPenaltiesTeam1.get(i).setStyle("-fx-text-box-border: green; -fx-focus-color: green;");
					allPenaltiesTeam2.get(i).setStyle("-fx-text-box-border: green; -fx-focus-color: green;");
					hbMatches1.getChildren().addAll(allPenaltiesTeam1.get(i));
					hbMatches2.getChildren().addAll(allPenaltiesTeam2.get(i));
				}
				vbMatches.getChildren().addAll(hbMatches1, hbMatches2, btDone);
				gpRoot2.getChildren().addAll(vbMatches);
				theMatchStage.setScene(new Scene(gpRoot2, 500, 160));
				theMatchStage.show();
				btDone.setOnAction(e -> {
					theMatchStage.hide();
				});
				return sumScore();
			}
		} else if (sumScore().equals("Tie")) {
			theMatchStage.setTitle("The Extra Time");
			hbMatches1.getChildren().add(allScoreTeam1.get(TieBreaker));
			hbMatches2.getChildren().add(allScoreTeam2.get(TieBreaker));
			TieBreaker = allScoreTeam1.size();
			allScoreTeam1.add(new TextField());
			allScoreTeam2.add(new TextField());
			allScoreTeam1.get(TieBreaker).setStyle("-fx-text-box-border: yellow; -fx-focus-color: yellow;");
			allScoreTeam2.get(TieBreaker).setStyle("-fx-text-box-border: yellow; -fx-focus-color: yellow;");
			allScoreTeam1.get(TieBreaker).setPrefWidth(50);
			allScoreTeam2.get(TieBreaker).setPrefWidth(50);
			return sumScore();
		} else {
			btDone.setOnAction(e -> {
				theMatchStage.hide();
			});
			return sumScore();
		}
	}
	
//	Sum the scores of two teams
	private String sumScore() {
		int scoreTeam1 = 0;
		int scoreTeam2 = 0;

		if (selectedToggle.equals("Tennis")) {
			int winCounterTeam1 = 0;
			int winCounterTeam2 = 0;
			for (int i = 0; i < allScoreTeam1.size() && (winCounterTeam1 != 3 || winCounterTeam2 != 3 || winCounterTeam1 + winCounterTeam2 != NUM_OF_TENNIS_MATCHES); i++) {
				scoreTeam1 = (allScoreTeam1.get(i).getText().isBlank() ? 0	: Integer.parseInt(allScoreTeam1.get(i).getText()));
				scoreTeam2 = (allScoreTeam2.get(i).getText().isBlank() ? 0	: Integer.parseInt(allScoreTeam2.get(i).getText()));
				if (scoreTeam1 > scoreTeam2) {
					winCounterTeam1++;
				} else if (scoreTeam1 < scoreTeam2) {
					winCounterTeam2++;
				}
			}
			if (winCounterTeam1 > winCounterTeam2) {
				return team1;
			} else if (winCounterTeam1 < winCounterTeam2) {
				return team2;
			} else {
				return "Tie";
			}
		} else {
			for (int i = 0; i < allScoreTeam1.size(); i++) {
				scoreTeam1 += (allScoreTeam1.get(i).getText().isBlank() ? 0 : Integer.parseInt(allScoreTeam1.get(i).getText()));
				scoreTeam2 += (allScoreTeam2.get(i).getText().isBlank() ? 0 : Integer.parseInt(allScoreTeam2.get(i).getText()));

			}
			if (selectedToggle.equals("Soccer") && (!allPenaltiesTeam1.equals(null) || !allPenaltiesTeam2.equals(null))) {
				for (int j = 0; j < allPenaltiesTeam1.size(); j++) {
					scoreTeam1 += (allPenaltiesTeam1.get(j).getText().isBlank() ? 0 : Integer.parseInt(allPenaltiesTeam1.get(j).getText()));
					scoreTeam2 += (allPenaltiesTeam2.get(j).getText().isBlank() ? 0 : Integer.parseInt(allPenaltiesTeam2.get(j).getText()));
				}
			}
			if (scoreTeam1 > scoreTeam2) {
				return team1;
			} else if (scoreTeam1 < scoreTeam2) {
				return team2;
			} else {
				return "Tie";
			}
		}
	}

}
