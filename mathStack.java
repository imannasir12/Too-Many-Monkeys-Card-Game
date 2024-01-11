//Purpose: math stack class for too many monkeys
public class mathStack
{
    //deck instance variables
    //holds the number of cards in the deck
    private int count;
    //a deck of 12 cards
    private mathCards data[] = new mathCards [12];

    //returns a deck of math cards
    public mathStack ()
    {
	count = 0;
    }


    //adds a math card to the deck
    public void push (mathCards addMe)
    {
	data [count] = addMe;
	count++;
    }


    //returns the number of math cards remaining in the deck
    public int size ()
    {
	return count;
    }


    //returns true if the deck is full
    public boolean isFull ()
    {
	return (count == 12);
    }


    //removes a math card from the deck
    public mathCards pop ()
    {
	count--;
	return data [count];
    }


    //return the top math card in the deck
    public mathCards peek ()
    {
	return data [count--];
    }


    //returns true if there are no math cards left in the deck
    public boolean isEmpty ()
    {
	return count == 0;
    }


    //removes all math cards from deck
    public void clear ()
    {
	count = 0;
    }


    //adds all 12 cards to deck in random order
    public void shuffle ()
    {
	String names[] = {"math5", "math6", "math4", "math3", "math52", "math64", "math53", "math42", "math32", "math25", "math43", "math24"};
	int values[] = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};
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

	//push all (now in random order) into the math Deck
	for (int i = 0 ; i < names.length ; i++)
	{
	    mathCards c = new mathCards (names [i], values [i]);
	    push (c);
	}
    }
}
