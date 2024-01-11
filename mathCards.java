//Purpose : Too many monkeys math card class
public class mathCards
{
    //math card instance variables
    private String name;
    private int value;

    //Default Constructor - generates a random math card
    public mathCards ()
    {
	String names[] = {"math5", "math6", "math4", "math3", "math52", "math64", "math53", "math42", "math32", "math25", "math43", "math24"};
	int values[] = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};
	int rand = (int) (Math.random () * names.length);
	name = names [rand];
	value = values [rand];
    }


    //Construction - generates a random math card using parameters
    public mathCards (String n, int v)
    {
	name = n;
	value = v;
    }


    //can see name on a math card using return types
    public String getName ()
    {
	return name;
    }


    //allows changes to a math card name using parameters
    public void setName (String n)
    {
	name = n;
    }


    //can see value on a math card using return types
    public int getValue ()
    {
	return value;
    }


    //allows changes to a math card value using parameters
    public void setValue (int v)
    {
	value = v;
    }


    //tests if two math cards are equal based on the picture name
    public boolean equals (mathCards c)
    {
	if (c.getName ().equals (name))
	    return true;
	else
	    return false;
    }


    //based on math card picture name, return -1 if parameter is greater, 0 if parameter is equal, and 1 if parameter is smaller
    public int compareTo (mathCards c)
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


    //answers first type of math question - simple factorials
    public int question1 (int n)
    {
	if (n <= 1)
	    return 1;
	else
	    return question1 (n - 1) * n;
    }


    //answers second type of math question - dividing factorials
    public int question2 (int n, int m)
    {
	return ((question1 (n - 1) * n) / (question1 (m - 1) * m));
    }


    //answers third type of math question - multiplying factorials
    public int question3 (int n, int m)
    {
	if ((n * m) <= 1)
	    return 1;
	else
	    return (question3 (n - 1, 1) * n * question3 (m - 1, 1) * m);
    }


    //goes from char to integer and returns integer
    public int generateNumValue (char c)
    {
	int num = c;
	num = num - 48;
	return num;
    }


    //returns picture name of the math card
    public String getPicName ()
    {
	if (name.equals ("math5"))
	    return "math1";
	else if (name.equals ("math6"))
	    return "math2";
	else if (name.equals ("math4"))
	    return "math3";
	else if (name.equals ("math3"))
	    return "math4";
	else if (name.equals ("math52"))
	    return "math5";
	else if (name.equals ("math64"))
	    return "math6";
	else if (name.equals ("math53"))
	    return "math7";
	else if (name.equals ("math42"))
	    return "math8";
	else if (name.equals ("math32"))
	    return "math9";
	else if (name.equals ("math25"))
	    return "math10";
	else if (name.equals ("math43"))
	    return "math11";
	else
	    return "math12";
    }
}


