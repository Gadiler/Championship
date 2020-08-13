package view;

import java.util.Vector;

import javax.swing.JOptionPane;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.Match;

public class ChampionshipView implements AbstractChampionshipView {
	private final int FINAL_PARTICIPANTS = 8;
	private ObservableList<String> allParticipants;
	private Vector<Button> allButtons = new Vector<Button>();
	private Vector<TextField> allEighthFinals = new Vector<TextField>();
	private Vector<TextField> allQuarterFinals = new Vector<TextField>();
	private Vector<TextField> allSemiFinals = new Vector<TextField>();
	private Vector<Match> allMatches = new Vector<Match>();
	private TextField Final = new TextField();
	private Stage theStage;
	private GridPane gpRoot;	
	private int matchesSize = 0;
	private int allButtonSize = 0;
	private Vector<Line> allLines = new Vector<Line>();
	int numOfLine = 0;
	
	public ChampionshipView(ObservableList<String> cmbAllParticipants, String selectedToggle) throws Exception {
		if (cmbAllParticipants.size() != FINAL_PARTICIPANTS) {
			throw new Exception("There are not enough participants!");
		}
		this.allParticipants = cmbAllParticipants;

		Image image = new Image("file:logo.jpg");

		BackgroundSize backgroundSize = new BackgroundSize(500, 100, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);

		theStage = new Stage();
		theStage.setTitle("The " + selectedToggle + " Championship");

		gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setBackground(background);
		
		addLine();
		initializeWindow(selectedToggle);

		theStage.setScene(new Scene(gpRoot, 1000, 450));
		theStage.show();
	}
//	Initialize the button - "Play a Match"
	@Override
	public boolean playMatch(String team1, String team2, String selectedToggle) {
		for (Match M : allMatches) {
			if (M.getTeam1().equals(team1) && M.getTeam2().equals(team2)) {
				M.theMatchStage.show();
				return false;
			}
		}
		allMatches.add(new Match(team1, team2, selectedToggle));
		matchesSize++;
		return true;
	}
//	Create the main window 
	private void initializeWindow(String selectedToggle) {
		VBox vbEighth = new VBox();
		vbEighth.setSpacing(27);
		vbEighth.setAlignment(Pos.CENTER);
		
		VBox vbEighthButton = new VBox();
		vbEighthButton.setSpacing(80);
		vbEighthButton.setAlignment(Pos.CENTER);
		
		VBox vbQuarter = new VBox();
		vbQuarter.setSpacing(80);
		vbQuarter.setAlignment(Pos.CENTER);
		
		for (int i = 0, lineIndex = 0; i < allParticipants.size(); i++, lineIndex += 2) {
			allEighthFinals.add(new TextField());
			allEighthFinals.get(i).setText(allParticipants.get(i));
			allEighthFinals.get(i).setEditable(false);
			allEighthFinals.get(i).setStyle("-fx-text-box-border: blue; -fx-focus-color: blue;");
			vbEighth.getChildren().add(allEighthFinals.get(i));
			if (allParticipants.size() > lineIndex) {
				setQuarterFinals(i, lineIndex, selectedToggle);
				vbQuarter.getChildren().add(allQuarterFinals.get(i));
				vbEighthButton.getChildren().add(allButtons.get(allButtonSize++));
			}
		}
		VBox vbSemi = new VBox();
		vbSemi.setSpacing(180);
		vbSemi.setAlignment(Pos.CENTER);
		
		VBox vbQuarterButton = new VBox();
		vbQuarterButton.setSpacing(180);
		vbQuarterButton.setAlignment(Pos.CENTER);
		
		for (int k = 0, lineIndex = 0; k < allQuarterFinals.size(); k++, lineIndex += 2) {
			if (allQuarterFinals.size() >= lineIndex * 2) {
				setSemiFinals(k, lineIndex, selectedToggle);
				vbSemi.getChildren().addAll(allSemiFinals.get(k));
				vbQuarterButton.getChildren().add(allButtons.get(allButtonSize++));
			}
		}
		VBox vbFinal = new VBox();
		vbFinal.setSpacing(10);
		vbFinal.setAlignment(Pos.CENTER);
		
		VBox vbFinalButton = new VBox();
		vbFinalButton.setSpacing(10);
		vbFinalButton.setAlignment(Pos.CENTER);
		
		for (int i = 0, lineIndex = 0; i < allSemiFinals.size(); i++, lineIndex += 2) {
			if (allParticipants.size() > lineIndex * 4) {
				setFinal(lineIndex, selectedToggle);
				vbFinal.getChildren().addAll(Final);
				vbFinalButton.getChildren().add(allButtons.get(allButtonSize++));
			}
		}
		HBox hbAllLable = new HBox();
		hbAllLable.setSpacing(20);
		hbAllLable.getChildren().addAll(vbEighth, vbEighthButton, vbQuarter, vbQuarterButton, vbSemi, vbFinalButton, vbFinal);
		gpRoot.getChildren().add(hbAllLable);
	}
//	Checking if list of TextField is empty
	private boolean checkIfEmpty(Vector<TextField> allTextField) {
		for (TextField level : allTextField) {
			if (level.getText().isBlank()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void removeParticipant(int id) {
		ObservableList<String> allLines = allParticipants;
		for (int i = 0; i < allLines.size(); i++) {
			int lineId = getIdFromTeamString(allLines.get(i));
			if (lineId == id) {
				allParticipants.remove(i);
			}
		}
	}

	private int getIdFromTeamString(String str) {
		System.out.println(str);
		String[] arr = str.split("[()]");
		System.out.println(arr);
		return Integer.parseInt(arr[1]);
	}

	private void setQuarterFinals(int i, int lineIndex, String selectedToggle) {
		allQuarterFinals.add(new TextField());
		allQuarterFinals.get(i).setEditable(false);
		allQuarterFinals.get(i).setStyle("-fx-text-box-border: blue; -fx-focus-color: blue;");
		allButtons.add(allButtonSize, new Button("Play Match"));
		int index1 = i;
		int index2 = lineIndex;

		allButtons.get(allButtonSize).setOnAction(e -> {
			if (checkIfEmpty(allEighthFinals)) {
				String team1 = allParticipants.get(index2);
				String team2 = allParticipants.get(index2 + 1);
				playMatch(team1, team2, selectedToggle);
				setMatchButton(allQuarterFinals.get(index1));
			}
		});
	}

	private void setSemiFinals(int i, int lineIndex, String selectedToggle) {
		allSemiFinals.add(new TextField());
		allSemiFinals.get(i).setEditable(false);
		allSemiFinals.get(i).setStyle("-fx-text-box-border: blue; -fx-focus-color: blue;");
		allButtons.add(allButtonSize, new Button("Play Match"));
		int index1 = i;
		int index2 = lineIndex;

		allButtons.get(allButtonSize).setOnAction(e -> {
			if (checkIfEmpty(allQuarterFinals)) {
				String team3 = allQuarterFinals.get(index2).getText();
				String team4 = allQuarterFinals.get(index2 + 1).getText();
				playMatch(team3, team4, selectedToggle);
				setMatchButton(allSemiFinals.get(index1));
			}
		});
	}

	private void setFinal(int lineIndex, String selectedToggle) {
		Final.setEditable(false);
		Final.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
		allButtons.add(allButtonSize, new Button("Play Match"));
		int index2 = lineIndex;

		allButtons.get(allButtonSize).setOnAction(e -> {
			if (checkIfEmpty(allSemiFinals)) {
				String team5 = allSemiFinals.get(index2).getText();
				String team6 = allSemiFinals.get(index2 + 1).getText();
				playMatch(team5, team6, selectedToggle);
				setMatchButton(Final);
			}
		});
	}
	
	private void setMatchButton(TextField level) {
		allMatches.get(matchesSize - 1).btDone.setOnAction(r -> {
//			Setting the text field to Disable if 1 or more text field is filled
			for (int i = 0; i < allMatches.get(matchesSize - 1).allScoreTeam1.size()-1; i++) {
				allMatches.get(matchesSize - 1).allScoreTeam1.get(i).setDisable(true);
				allMatches.get(matchesSize - 1).allScoreTeam2.get(i).setDisable(true);
			}
//			initialize the next level text field with the winner
			level.setText(allMatches.get(matchesSize - 1).getTheWinner());
//			Setting the extra time text field to Disable if 1 or more text c is filled
			if (!(allMatches.get(matchesSize - 1).getTfTieBreakerTeam1().getText().isBlank() && allMatches.get(matchesSize - 1).getTfTieBreakerTeam2().getText().isBlank())) {
				allMatches.get(matchesSize - 1).getTfTieBreakerTeam1().setDisable(true);
				allMatches.get(matchesSize - 1).getTfTieBreakerTeam2().setDisable(true);
			}
//			Stop conditions
			boolean flag = true;
//			Setting the penalties text field to Disable if 1 or more text field is filled
			for (int i = 0; i < allMatches.get(matchesSize - 1).getTfPenaltiesTeam1().size() && flag; i++) {
				if (!allMatches.get(matchesSize - 1).getTfPenaltiesTeam1().get(i).getText().isBlank()) {
					for (int j = 0; j < allMatches.get(matchesSize - 1).getTfPenaltiesTeam1().size(); j++) {
						allMatches.get(matchesSize - 1).getTfPenaltiesTeam1().get(j).setDisable(true);
						allMatches.get(matchesSize - 1).getTfPenaltiesTeam2().get(j).setDisable(true);
					}flag = false;
				}
			}
//			Show winner message
			if (!Final.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "The Winner is: " + Final.getText());
			}
			allMatches.get(matchesSize - 1).theMatchStage.hide();
		});
	}
//	Setting the background lines
	private void addLine() {
		VBox vbEighthLines = new VBox();
		vbEighthLines.setSpacing(50);
		vbEighthLines.setAlignment(Pos.CENTER);
		for (int i = 0; i < 8; i++) {
			allLines.add(numOfLine, new Line());
			allLines.get(numOfLine).setStartX(0);
			allLines.get(numOfLine).setStartY(0);
			allLines.get(numOfLine).setEndX(50);
			allLines.get(numOfLine).setEndY(0);
			allLines.get(numOfLine).setStroke(Color.BLACK);
			allLines.get(numOfLine).setStrokeWidth(3);
			vbEighthLines.getChildren().add(allLines.get(numOfLine++));
		} 
		
		VBox widthLineEighth = new VBox();
		widthLineEighth.setSpacing(52);
		widthLineEighth.setAlignment(Pos.CENTER);
		
		VBox conctorLineEighth = new VBox();
		conctorLineEighth.setSpacing(105);
		conctorLineEighth.setAlignment(Pos.CENTER);
		for (int i = 0; i < 4; i++) {
			allLines.add(numOfLine, new Line());
			allLines.get(numOfLine).setStartX(0);
			allLines.get(numOfLine).setStartY(0);
			allLines.get(numOfLine).setEndX(0);
			allLines.get(numOfLine).setEndY(52);
			allLines.get(numOfLine).setStroke(Color.BLACK);
			allLines.get(numOfLine).setStrokeWidth(3);
			widthLineEighth.getChildren().add(allLines.get(numOfLine));
			allLines.add(numOfLine, new Line());
			allLines.get(numOfLine).setStartX(0);
			allLines.get(numOfLine).setStartY(0);
			allLines.get(numOfLine).setEndX(80);
			allLines.get(numOfLine).setEndY(0);
			allLines.get(numOfLine).setStroke(Color.BLACK);
			allLines.get(numOfLine).setStrokeWidth(3);
			conctorLineEighth.getChildren().add(allLines.get(numOfLine));
			
		}
		
		HBox firstLevel = new HBox();
		firstLevel.setSpacing(-3);
		firstLevel.setAlignment(Pos.CENTER);
		firstLevel.getChildren().addAll(vbEighthLines, widthLineEighth, conctorLineEighth);
		
//		-----------------------------------------------
		
		VBox vbQuarterLines = new VBox();
		vbQuarterLines.setSpacing(105);
		vbQuarterLines.setAlignment(Pos.CENTER);
		for (int i = 0; i < 4; i++) {
			allLines.add(numOfLine, new Line());
			allLines.get(numOfLine).setStartX(0);
			allLines.get(numOfLine).setStartY(0);
			allLines.get(numOfLine).setEndX(50);
			allLines.get(numOfLine).setEndY(0);
			allLines.get(numOfLine).setStroke(Color.BLACK);
			allLines.get(numOfLine).setStrokeWidth(3);
			vbQuarterLines.getChildren().add(allLines.get(numOfLine++));
		}	
		
		VBox widthLineQuarter = new VBox();
		widthLineQuarter.setSpacing(105);
		widthLineQuarter.setAlignment(Pos.CENTER);
		VBox conctorLineQuarter = new VBox();
		conctorLineQuarter.setSpacing(205);
		conctorLineQuarter.setAlignment(Pos.CENTER);
		
		for (int i = 0; i < 2; i++) {
			allLines.add(numOfLine, new Line());
			allLines.get(numOfLine).setStartX(0);
			allLines.get(numOfLine).setStartY(0);
			allLines.get(numOfLine).setEndX(0);
			allLines.get(numOfLine).setEndY(108);
			allLines.get(numOfLine).setStroke(Color.BLACK);
			allLines.get(numOfLine).setStrokeWidth(3);
			widthLineQuarter.getChildren().add(allLines.get(numOfLine));
			allLines.add(numOfLine, new Line());
			allLines.get(numOfLine).setStartX(0);
			allLines.get(numOfLine).setStartY(0);
			allLines.get(numOfLine).setEndX(80);
			allLines.get(numOfLine).setEndY(0);
			allLines.get(numOfLine).setStroke(Color.BLACK);
			allLines.get(numOfLine).setStrokeWidth(3);
			conctorLineQuarter.getChildren().add(allLines.get(numOfLine));
		}
		
		HBox secondLevel = new HBox();
		secondLevel.setSpacing(-1);
		secondLevel.getChildren().addAll(vbQuarterLines, widthLineQuarter, conctorLineQuarter);
		
//		-----------------------------------------------
		
		VBox vbSemiLines = new VBox();
		vbSemiLines.setSpacing(200);
		vbSemiLines.setAlignment(Pos.CENTER);
		for (int i = 0; i < 2; i++) {
			allLines.add(numOfLine, new Line());
			allLines.get(numOfLine).setStartX(0);
			allLines.get(numOfLine).setStartY(0);
			allLines.get(numOfLine).setEndX(50);
			allLines.get(numOfLine).setEndY(0);
			allLines.get(numOfLine).setStroke(Color.BLACK);
			allLines.get(numOfLine).setStrokeWidth(3);
			vbSemiLines.getChildren().add(allLines.get(numOfLine++));
		}
		
		VBox widthLineSemi = new VBox();
		widthLineSemi.setAlignment(Pos.CENTER);
		
		allLines.add(numOfLine, new Line());
		allLines.get(numOfLine).setStartX(0);
		allLines.get(numOfLine).setStartY(0);
		allLines.get(numOfLine).setEndX(0);
		allLines.get(numOfLine).setEndY(203);
		allLines.get(numOfLine).setStroke(Color.BLACK);
		allLines.get(numOfLine).setStrokeWidth(3);
		widthLineSemi.getChildren().add(allLines.get(numOfLine));
		
		VBox conctorLineSemi = new VBox();
		conctorLineSemi.setAlignment(Pos.CENTER);
		
		allLines.add(numOfLine, new Line());
		allLines.get(numOfLine).setStartX(0);
		allLines.get(numOfLine).setStartY(0);
		allLines.get(numOfLine).setEndX(80);
		allLines.get(numOfLine).setEndY(0);
		allLines.get(numOfLine).setStroke(Color.BLACK);
		allLines.get(numOfLine).setStrokeWidth(3);
		conctorLineSemi.getChildren().add(allLines.get(numOfLine));
		
		HBox thirdLevel = new HBox();
		thirdLevel.setSpacing(-1);
		thirdLevel.getChildren().addAll(vbSemiLines, widthLineSemi, conctorLineSemi);
		
//		-----------------------------------------------
		
		HBox hbAllLines = new HBox();
		hbAllLines.setSpacing(120);
		hbAllLines.setAlignment(Pos.CENTER);
		hbAllLines.getChildren().addAll(firstLevel, secondLevel, thirdLevel);
		gpRoot.getChildren().add(hbAllLines);
	}
	
}
