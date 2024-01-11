//Purpose: stack class for too many monkeys
public class monkeyStack
{
    //deck instance variables
    //holds the number of cards in the deck
    private int count;
    //a deck of 60 cards
    private Cards data[] = new Cards [60];

    //returns a deck of monkey cards
    public monkeyStack ()
    {
	count = 0;
    }


    //adds a monkey card to the deck
    public void push (Cards addMe)
    {
	data [count] = addMe;
	count++;
    }


    //returns the number of monkey cards remaining in the deck
    public int size ()
    {
	return count;
    }


    //returns true if the deck is full
    public boolean isFull ()
    {
	return (count == 60);
    }


    //removes a monkey card from the deck
    public Cards pop ()
    {
	count--;
	return data [count];
    }


    //return the top monkey card in the deck
    public Cards peek ()
    {
	return data [count--];
    }


    //returns true if there are no monkey cards left in the deck
    public boolean isEmpty ()
    {
	return count == 0;
    }


    //removes all monkey cards from deck
    public void clear ()
    {
	count = 0;
    }


    //adds all 59 cards to deck in random order
    public void shuffle ()
    {
	String names[] = {"monkey1", "monkey1", "monkey1", "monkey1", "monkey1", "monkey1", "monkey2", "monkey2", "monkey2", "monkey2", "monkey2", "monkey2", "monkey3", "monkey3", "monkey3", "monkey3", "monkey3", "monkey3", "monkey4", "monkey4", "monkey4", "monkey4", "monkey4", "monkey4", "monkey5", "monkey5", "monkey5", "monkey5", "monkey5", "monkey5",
	    "monkey6", "monkey6", "monkey6", "monkey6", "monkey6", "monkey6", "giraffe", "giraffe", "giraffe", "elephant", "elephant", "elephant", "raccoon", "raccoon", "raccoon", "skip", "skip", "skip", "skip", "disturb", "disturb", "wild", "wild", "wild", "wild", "math", "math", "math", "math", "math"};
	int values[] = {1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 7, 7, 7, 9, 9, 9, 9, 10, 10, 8, 8, 8, 8, 11, 11, 11, 11, 11};
	//Randomize the order of the arrays
	for (int i = 0 ; i < 100 ; i++)
	{
	    int r1 = (int) (Math.random () * names.length);
	    int r2 = (int) (Math.random () * names.length);
	    //swap names array
	    String temp = names [r1];
	    names [r1] = names [r2];
	    names [r2] = temp;
	    //swap value array
	    int temp1 = values [r1];
	    values [r1] = values [r2];
	    values [r2] = temp1;
	}
	count = 0;

	//push all (now in random order) into the Deck
	for (int i = 0 ; i < names.length ; i++)
	{
	    Cards c = new Cards (names [i], values [i]);
	    push (c);
	}
    }
}
