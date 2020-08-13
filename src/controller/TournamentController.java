      package controller;

import listeners.TeamUIEventListener;
import listeners.TournamentEventListener;
import model.Tournament;
import view.AbstractTournamentView;

public class TournamentController implements TeamUIEventListener, TournamentEventListener {
	private Tournament tournamentModel;
	private AbstractTournamentView tournamentView;

	public TournamentController(Tournament model, AbstractTournamentView view) {
		tournamentModel = model;
		tournamentView = view;
		
		tournamentModel.registerListener(this);
		tournamentView.registerListener(this);
	}

	@Override
	public void addTeamToUI(String name) {
		try {
			tournamentModel.addTeam(name);
		} catch (Exception e) {
			tournamentView.tournamentIsFull(e.getMessage());
		}
	}

	@Override
	public void removeTeamFromUI(int id) {
		try {
			tournamentModel.removeTeam(id);
		} catch (Exception e) {
			tournamentView.tournamentIsEmpty(e.getMessage());
		}
	}
	
	@Override
	public void addTeamToModelEvent(String name, int id) {
		tournamentView.addTeamToUI(name, id);
	}

	@Override
	public void removeTeamFromModelEvent(int id) {
			tournamentView.removeTeamFromUI(id);
	}

	@Override
	public void failedAddingPassengerToModelEvent() {
		tournamentView.tournamentIsFull("The tournament is full");
	}




}
