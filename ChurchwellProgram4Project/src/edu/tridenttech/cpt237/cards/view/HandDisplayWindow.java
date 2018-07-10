package edu.tridenttech.cpt237.cards.view;

/* AUTHOR: ADAM CHURCHWELL
 * PROGRAM 4 */

import java.util.ArrayList;

import edu.tridenttech.cpt237.cards.model.Card;
import edu.tridenttech.cpt237.cards.model.Deck;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HandDisplayWindow
{
	private final int SUIT_SIZE = 13;
	private Image[] cardImages = new Image[4*SUIT_SIZE];
	private Stage myStage;
	ListView<Card> listView = new ListView<>();
	ArrayList<Card> hand = new ArrayList<>();
	
	Deck deck;
	int handSize;
	
	Button dealButton = new Button("Deal Me");
	Button fishSortButton = new Button("Go Fish");
	Button spadeSortButton = new Button("Spades");
	Button bridgeSortButton = new Button("Bridge");
	Label reverseLbl = new Label("Reverse  ");
	CheckBox reverseChk = new CheckBox();

	public HandDisplayWindow(Stage stage, int size)
	{
		handSize = size;
		myStage = stage;
		myStage.setTitle("Card Hand");
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane);
		myStage.setScene(scene);
		
		// deal button (inner class)
		dealButton.setOnAction(new dealListener());
		
		// fish sort (lambda expression)
		fishSortButton.setOnAction(e -> {
			listView.getItems().setAll(deck.rankSort(hand, reverseChk.selectedProperty()));
		});
		
		// spade sort (anonymous inner class)
		spadeSortButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				listView.getItems().setAll(deck.suitSort(hand, reverseChk.selectedProperty()));
			}
		});
		
		// bridge sort (anonymous inner class)
		bridgeSortButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				listView.getItems().setAll(deck.bridgeSort(hand, reverseChk.selectedProperty()));
				deck.clearCardGroupings(hand);
			}
		});
		
		// loads images into ListView and outputs to the screen
		listView.setCellFactory(param -> new ListCell<Card>() 
		{
			private ImageView imageView = new ImageView();

			@Override
			public void updateItem(Card card, boolean empty)
			{
				super.updateItem(card, empty);
				if (empty) {
					setGraphic(null);
				} else {
					// determine the index of the card
					int index = card.getSuit() * SUIT_SIZE + card.getRank();
					imageView.setImage(cardImages[index]);
					imageView.setPreserveRatio(true);
					imageView.setFitWidth(56);
					setGraphic(imageView);
				}
			}
		});

		// ListView and Button layouts and styling...
		listView.setOrientation(Orientation.HORIZONTAL);
		listView.setPadding(new Insets(20, 20, 10, 20));;
		pane.setCenter(listView);
		HBox reverse = new HBox(2);
		reverse.getChildren().addAll(reverseLbl, reverseChk);
		reverse.setPadding(new Insets(7));
		reverseLbl.setTextFill(Color.web("white"));
		dealButton.setStyle("-fx-background-color: black;");
		dealButton.setTextFill(Color.web("white"));
		fishSortButton.setStyle("-fx-background-color: black;");
		fishSortButton.setTextFill(Color.web("white"));
		spadeSortButton.setStyle("-fx-background-color: black;");
		spadeSortButton.setTextFill(Color.web("white"));
		bridgeSortButton.setStyle("-fx-background-color: black;");
		bridgeSortButton.setTextFill(Color.web("white"));
		VBox left = new VBox(5);
		left.getChildren().addAll(dealButton, fishSortButton, spadeSortButton, bridgeSortButton, reverse);
		left.setAlignment(Pos.CENTER);
		left.setPadding(new Insets(20, 20, 20, 20));
		left.setStyle("-fx-background-color: #ff0010;");
		pane.setLeft(left);
		
		// card layout
		myStage.setHeight(230);
		myStage.setWidth(240 + (68 * handSize));
		
		// load images from project directory
		loadImages();
	}

	private void loadImages()
	{
		String resourceDir = "file:resources/cardspng/";
		char[] suits = { 'c', 'd', 'h', 's' };
		char[] rank = { '2', '3', '4', '5', '6', '7', '8', '9', '0', 'j', 'q', 'k', 'a' };
		int slot = 0;
		// load images
		for (int s = 0; s < 4; s++) {
			for (int r = 0; r < SUIT_SIZE; r++) {
				String path = resourceDir + suits[s] + rank[r] + ".png";
				cardImages[slot] = new Image(path);
				slot++;
			}
		}
	}

	// accepts new shuffled deck and deals from that deck number of cards specified in MainApplication class, shows window
	public void show(Deck deck)
	{
		this.deck = deck;
		deal();
		myStage.show();
	}
	
	// inner class passed to deal button
	private class dealListener implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == dealButton)
			{
				deal();
			}
		}
	}
	
	// deal method, used at hand window object instantiation and upon deal button event
	private void deal()
	{
		if (deck != null)
		{
			hand = deck.deal(handSize);
			deck.suitCounter(hand);
			deck.suitGrouper(hand);
			listView.getItems().setAll(hand);
		}
	}
}
