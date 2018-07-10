package edu.tridenttech.cpt237.cards.view;

/* AUTHOR: ADAM CHURCHWELL
 * PROGRAM 4 */

import edu.tridenttech.cpt237.cards.model.Deck;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		HandDisplayWindow ui = new HandDisplayWindow(primaryStage, 13);
		Deck deck = new Deck();
		deck.shuffle();
		ui.show(deck);
	}

	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
