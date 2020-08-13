package view;

import listeners.TeamUIEventListener;

public interface AbstractTournamentView {
	void registerListener(TeamUIEventListener listener);
	void addTeamToUI(String name, int id);
	void removeTeamFromUI(int id);
	void tournamentIsFull(String msg);
	void tournamentIsEmpty(String msg);
}
