package model;

import java.util.Vector;

import listeners.TournamentEventListener;

public class Tournament {
	private final int MAX_PARTICIPANTS = 8;
	private String name;
	private Vector<Team> allParticipants;
	private Vector<TournamentEventListener> listeners;

	public Tournament() {
		name = new String("Championship");
		allParticipants = new Vector<Team>();
		listeners = new Vector<TournamentEventListener>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getParticipantById(int id) {
		for (Team t : allParticipants) {
			if (t.getId() == id) {
				return t;
			}
		}return null;
	}

	public void registerListener(TournamentEventListener listener) {
		listeners.add(listener);
	}

	public Team addTeam(String name) throws Exception {
		if (allParticipants.size() == MAX_PARTICIPANTS) {
			fireFailedAddingPassengerEvent();
			return null;
		}else {
			Team newTeam = new Team(name);
			allParticipants.add(newTeam);
			fireAddTeamEvent(newTeam);
			return newTeam;
		}
	}

	public Team removeTeam(int id) {
		Team teamToRemove = getParticipantById(id);
		allParticipants.remove(teamToRemove);
		fireRemoveTeamEvent(teamToRemove);
		return teamToRemove;
	}

	private void fireFailedAddingPassengerEvent() {
		for (TournamentEventListener l : listeners) {
			l.failedAddingPassengerToModelEvent();
		}
	}

	private void fireAddTeamEvent(Team team) {
		for (TournamentEventListener l : listeners) {
			l.addTeamToModelEvent(team.getName(), team.getId());
		}
	}

	private void fireRemoveTeamEvent(Team team) {
		for (TournamentEventListener l : listeners) {
			l.removeTeamFromModelEvent(team.getId());
		}
	}

}
