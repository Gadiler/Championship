Course: Object-Oriented Programming.
Final project: Championship

Guidelines:

- First window: 	Select tournament's type. 
			After choosing, is revealed the rest of the window (add \ remove participant, ComboBox of all the participants)

- Second window: 	Eighth finals, Quarterfinals, Semifinals, and the last, The Final. 
			Any steps have his button ("Play a Match") to start the match. 
	            	The winner  - the one with the highest score, or with more sets victory (Tennis) - 
			pass to the next level and his name or team's name move to the next TextField.

- Third window:		Score window, any sports, and his scoring method. 
			If there is a tie;	- at Soccer: the first tie is an Extra time text field. The second tie is penalties. (penalties can't end with tie)
						- at Tennis & Basketball: Ties will be looped indefinitely until a clear winner is crowned.
			The winner's name moves to the next text field.		



Known Issues: (Exceptions)
1. When tie after first extra time -> only with Soccer.
2. Switching between different sports via radio buttons will preserve the teams/players.
3. Failing to fill penalty kicks in soccer might end up with an exception.
