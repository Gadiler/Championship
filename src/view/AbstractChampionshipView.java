package view;

public interface AbstractChampionshipView {
	boolean playMatch(String t1, String t2, String selectedToggle);
	void removeParticipant(int id);
}
