package edu.tridenttech.cpt237.cards.model;

/* AUTHOR: ADAM CHURCHWELL
 * PROGRAM 4 */

import java.util.Comparator;

public class Card implements Comparable<Card>
{ 
	public final int SUIT_SIZE = 13;
	public static final int CLUB = 0;
	public static final int DIAMOND = 1;
	public static final int HEART = 2;
	public static final int SPADE = 3;

    private int suit;      // clubs = 0, diamonds = 1, hearts = 2, spades = 3
    private int rank;      // deuce = 0, three = 1, four = 2, ..., king = 11, ace = 12
    private int suitCount;
    private boolean isFaceUp = true; // not used for our program
    
    // create a new card based on integer 0 = 2C, 1 = 3C, ..., 51 = AS
    public Card(int card) {
        rank = card % SUIT_SIZE;
        suit = card / SUIT_SIZE;
    }

    public int getRank() 
    {
    		return rank;
    }

    public int getSuit() 
    {
    		return suit;
    }
    
    public int getSuitCount()
    {
    		return suitCount;
    }
    
    public boolean isFaceUp()
	{
		return isFaceUp;
	}

	public void flip()
    {
    	isFaceUp = !isFaceUp;
    }
	
	public void setSuitCount(int suitCount)
	{
		this.suitCount = suitCount;
	}

    // represent cards like "2H", "9C", "JS", "AD"
    public String toString() {
        String ranks = "23456789TJQKA";
        String suits = "CDHS";
        return ranks.charAt(rank) +  "" + suits.charAt(suit);
    }

	@Override
	public int compareTo(Card other) {
		
		if (this.getRank() > other.getRank())
		{
			return 1;
		}
		else if (this.getRank() < other.getRank())
		{
			return -1;
		}
		else if (this.getSuit() > other.getSuit())
		{
			return 1;
		}
		else if (this.getSuit() < other.getSuit())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	static class Spade implements Comparator<Card>
	{
		@Override
		public int compare(Card c1, Card c2) 
		{
			if (c1.getSuit() > c2.getSuit())
			{
				return 1;
			}
			else if (c1.getSuit() < c2.getSuit())
			{
				return -1;
			}
			else if (c1.getRank() > c2.getRank())
			{
				return 1;
			}
			else if (c1.getRank() < c2.getRank())
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
	}
	
	static class Bridge implements Comparator<Card>
	{
		@Override
		public int compare(Card c1, Card c2) {
			if (c1.getSuitCount() > c2.getSuitCount())
			{
				return 1;
			}
			else if (c1.getSuitCount() < c2.getSuitCount())
			{
				return -1;
			}
			else if (c1.getSuit() > c2.getSuit())
			{
				return 1;
			}
			else if (c1.getSuit() < c2.getSuit())
			{
				return -1;
			}
			else if (c1.getRank() > c2.getRank())
			{
				return 1;
			}
			else if (c1.getRank() < c2.getRank())
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
	}
}