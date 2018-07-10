package edu.tridenttech.cpt237.cards.model;

/* AUTHOR: ADAM CHURCHWELL
 * PROGRAM 4 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.beans.property.BooleanProperty;

public class Deck
{
	// list of cards still in the deck
	private ArrayList<Card> deck = new ArrayList<>();

	// list of cards being used
	private ArrayList<Card> used = new ArrayList<>();

	// used to shuffle the deck
	Random dealer = new Random();
	
	// used in bridge sort
	int clubCount = 0;
	int diamondCount = 0;
	int heartCount = 0;
	int spadeCount = 0;
	
	public Deck()
	{
		// builds the deck
		for (int i = 0; i < 52; i++) {
			deck.add(new Card(i));
		}
	}
	
	public ArrayList<Card> deal(int handSize)
	{
		ArrayList<Card> hand = new ArrayList<>();
		// do we need more cards?  If so, shuffle
		if (deck.size() < handSize) {
			shuffle();
		}

		for (int i=0; i < handSize; i++) {
			Card next = deck.remove(deck.size() - 1);
			hand.add(next);
			used.add(next);
		}
		
		return hand;
	}
	
	public void shuffle()
	{
		deck.addAll(used);
		for (int i=0; i < deck.size() - 1; i++) {
			int swap = dealer.nextInt(deck.size() - i) + i;
			if (swap != i) {
				Card tmp1 = deck.get(i);
				Card tmp2 = deck.get(swap);
				deck.set(i, tmp2);
				deck.set(swap, tmp1);
			}
		}
	}
	
	// just used for testing
	public static void showHand(ArrayList<Card> hand)
	{
		for (Card c : hand) {
			System.out.printf(" %s",c);
		}
		System.out.println();
	}
	
	// comparable, fish sort
	public ArrayList<Card> rankSort(ArrayList<Card> cardsToSort, BooleanProperty booleanProperty)
	{
		if (booleanProperty.getValue() == true)
		{
			Collections.sort(cardsToSort);
		}
		else
		{
			Collections.sort(cardsToSort, Collections.reverseOrder());
		}
		
		return cardsToSort;
	}

	// comparator, spade sort
	public ArrayList<Card> suitSort(ArrayList<Card> cardsToSort, BooleanProperty booleanProperty)
	{
		if (booleanProperty.getValue() == true)
		{
			Collections.sort(cardsToSort, new Card.Spade());
		}
		else
		{
			Collections.sort(cardsToSort, Collections.reverseOrder(new Card.Spade()));
		}
	
		return cardsToSort;
	}
	
	// comparator, spade sort
	public ArrayList<Card> bridgeSort(ArrayList<Card> cardsToSort, BooleanProperty booleanProperty)
	{		
		if (booleanProperty.getValue() == true)
		{
			Collections.sort(cardsToSort, new Card.Bridge());
		}
		else
		{
			Collections.sort(cardsToSort, Collections.reverseOrder(new Card.Bridge()));
		}
	
		return cardsToSort;
	}
	
	// creates data only used in Bridge Sort
	public void suitCounter(ArrayList<Card> cardsToGroup)
	{
		for (Card c : cardsToGroup)
		{
			if (c.getSuit() == 0)
			{
				clubCount++;
			}
			else if (c.getSuit() == 1)
			{
				diamondCount++;
			}
			else if (c.getSuit() == 2)
			{
				heartCount++;
			}
			else 
			{
				spadeCount++;
			}
		}
	}
		
	// creates data only used in Bridge Sort
	public void suitGrouper(ArrayList<Card> cardsToGroup)
	{
		for (Card c : cardsToGroup)
		{
			if (c.getSuit() == 0)
			{
				c.setSuitCount(clubCount);
			}
			else if (c.getSuit() == 1)
			{
				c.setSuitCount(diamondCount);
			}
			else if (c.getSuit() == 2)
			{
				c.setSuitCount(heartCount);
			}
			else 
			{
				c.setSuitCount(spadeCount);
			}
		}
	}

	// resets card counts
	public void clearCardGroupings(ArrayList<Card> cardsToGroup)
	{
		clubCount = 0;
		diamondCount = 0;
		heartCount = 0;
		spadeCount = 0;
		
		for (Card c : cardsToGroup)
		{
			if (c.getSuit() == 0)
			{
				c.setSuitCount(clubCount);
			}
			else if (c.getSuit() == 1)
			{
				c.setSuitCount(diamondCount);
			}
			else if (c.getSuit() == 2)
			{
				c.setSuitCount(heartCount);
			}
			else 
			{
				c.setSuitCount(spadeCount);
			}
		}
	}

}
