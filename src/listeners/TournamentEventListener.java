package listeners;

public interface TournamentEventListener {
	void addTeamToModelEvent(String name, int id);
	void removeTeamFromModelEvent(int id);
	void failedAddingPassengerToModelEvent();
}
