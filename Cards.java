//Iman Nasir
//Date: January 14th, 2022
//Purpose : Too many monkeys card class
public class Cards
{
    //monkey card instance variables
    private String name;
    private int value;

    //Default Constructor - generates a random monkey card
    public Cards ()
    {
	String names[] = {"monkey1", "monkey2", "monkey3", "monkey4", "monkey5", "monkey6", "giraffe", "elephant", "raccoon", "skip", "disturb", "wild", "math"};
	int values[] = {1, 2, 3, 4, 5, 6, 0, 0, 7, 9, 10, 8, 11};
	int rand = (int) (Math.random () * names.length);
	name = names [rand];
	value = values [rand];
    }


    //Construction - generates a random monkey card using parameters
    public Cards (String n, int v)
    {
	name = n;
	value = v;
    }


    //can see name on a monkey card using return types
    public String getName ()
    {
	return name;
    }


    //allows changes to a monkey card name using parameters
    public void setName (String n)
    {
	name = n;
    }


    //can see value on a monkey card using return types
    public int getValue ()
    {
	return value;
    }


    //allows changes to a monkey card value using parameters
    public void setValue (int v)
    {
	value = v;
    }


    //tests if two monkey cards are equal based on the picture name
    public boolean equals (Cards c)
    {
	if (c.getName ().equals (name))
	    return true;
	else
	    return false;
    }


    //based on monkey card picture name, return -1 if parameter is greater, 0 if parameter is equal, and 1 if parameter is smaller
    public int compareTo (Cards c)
    {

	if (c.getName ().compareTo (name) < 0)
	    return -1;
	else if (c.getName ().equals (name))
	    return 0;
	else
	    return 1;
    }


    //returns a strng with both instance variables
    public String toString ()
    {
	return name + ", " + value;
    }


    //returns picture name of the monkey card
    public String getPicName ()
    {
	if (name.equals ("monkey1"))
	    return "m1";
	else if (name.equals ("monkey2"))
	    return "m2";
	else if (name.equals ("monkey3"))
	    return "m3";
	else if (name.equals ("monkey4"))
	    return "m4";
	else if (name.equals ("monkey5"))
	    return "m5";
	else if (name.equals ("monkey6"))
	    return "m6";
	else if (name.equals ("giraffe"))
	    return "giraffe";
	else if (name.equals ("elephant"))
	    return "elephant";
	else if (name.equals ("raccoon"))
	    return "raccoon";
	else if (name.equals ("skip"))
	    return "skip";
	else if (name.equals ("disturb"))
	    return "disturb";
	else if (name.equals ("math"))
	    return "math";
	else
	    return "wild";
    }
}
