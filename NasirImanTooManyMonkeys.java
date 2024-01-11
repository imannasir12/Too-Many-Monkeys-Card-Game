//libraries required in game
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.text.*;
import java.applet.*;
public class NasirImanTooManyMonkeys extends Applet implements ActionListener
{
    //audio file is set up
    AudioClip soundfile;

    //card layout is set up
    Panel p_card;
    Panel card1, card2, card3, card4;
    CardLayout cdLayout = new CardLayout ();

    //stacks used for deck and discard pile
    monkeyStack deck = new monkeyStack ();
    monkeyStack discard = new monkeyStack ();
    monkeyStack tempDeck = new monkeyStack ();
    monkeyStack tempDiscard = new monkeyStack ();

    //stack used for math cards
    mathStack deck1 = new mathStack ();

    //menu bar
    JMenuBar menuBar;

    //holds current card in game
    Cards temp;

    //array to track card placement for players
    int p1p2[] [] = {{0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0}};

    //array when searching through discard pile
    int search[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    //variables to track how many cards each player will be given
    int p1cards = 6;
    int p2cards = 6;

    //track number of cards in deck and discard pile
    int num;
    int num1;

    //variables to track cards player has selected
    int click = 0;
    int pos = 0;
    int selectedValue = -1;

    //turn variable
    int turn = 1;

    //variable that coordinates with search array
    int searchD = 0;

    //variable that decides if deck needs to be shuffled
    char first = 'n';

    //variables to help with saving and opening
    char chosen;
    String topPic;
    int cardUp;

    //buttons for each players cards
    JButton play1[] = new JButton [6];
    JButton play2[] = new JButton [6];

    //deck and discard picture
    JButton deckPic;
    JButton discardPic;

    //turn label
    JLabel turnlabel;

    //picture label for math cards
    JLabel mathCard;

    //buttons for math screen
    JButton a1;
    JButton a2;
    JButton a3;
    JButton a4;

    //variables for answer to math question
    int ans;
    boolean correct = false;

    //variable to track instructions pictures
    int instruct = 1;

    //picture for instructions screen
    JButton next;

    //init method to hold all screens
    public void init ()
    {
	//plays sound file on loop
	soundfile = getAudioClip (getDocumentBase (),
		"monkeysound.snd");
	soundfile.loop ();
	//resize screen to 745,620
	resize (745, 620);
	//new panel is created to hold all screens
	p_card = new Panel ();
	p_card.setLayout (cdLayout);
	//all screens are added
	screen1 ();
	screen2 ();
	screen3 ();
	screen4 ();
	setLayout (new BorderLayout ());
	add ("Center", p_card);
    }


    //screen 1 of game - opening screen
    public void screen1 ()
    {
	card1 = new Panel ();
	//opening screen picture is added
	JButton start = new JButton (createImageIcon ("images/opening.png"));
	start.setBorderPainted (false);
	start.setActionCommand ("s2");
	start.addActionListener (this);
	card1.add (start);
	p_card.add ("1", card1);

    }


    //screen 2 of game - instructions screen
    public void screen2 ()
    {
	card2 = new Panel ();
	//opening screen picture is added
	next = new JButton (createImageIcon ("images/instruct1.png"));
	next.setBorderPainted (false);
	next.setActionCommand ("updatePic");
	next.addActionListener (this);
	card2.add (next);
	p_card.add ("2", card2);

    }


    //menu items are added
    public void menuItems ()
    {
	JMenu menu;
	JMenuItem menuItem;
	//general section added in menu
	menu = new JMenu ("General");
	menuBar.add (menu);
	menuItem = new JMenuItem ("Discard Pile");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("searchDiscard");
	menu.add (menuItem);
	menuItem = new JMenuItem ("Cards Left");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("cardsLeft");
	menu.add (menuItem);
	menuItem = new JMenuItem ("Instructions");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("s2");
	menu.add (menuItem);
	//information on all cards
	menu = new JMenu ("Card Information");
	menuBar.add (menu);
	//number cards info
	menuItem = new JMenuItem ("Monkey Number Cards");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("numInfo");
	menu.add (menuItem);
	//skip card info
	menuItem = new JMenuItem ("Skip Card");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("skip");
	menu.add (menuItem);
	//wild monkey card info
	menuItem = new JMenuItem ("Wild Monkey Card");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("wild");
	menu.add (menuItem);
	//do not disturb card info
	menuItem = new JMenuItem ("Do Not Disturb Card");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("disturb");
	menu.add (menuItem);
	//raccoon card info
	menuItem = new JMenuItem ("Raccoon Card");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("raccoon");
	menu.add (menuItem);
	//elephant card info
	menuItem = new JMenuItem ("Elephant Card");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("elephant");
	menu.add (menuItem);
	//giraffe card info
	menuItem = new JMenuItem ("Giraffe Card");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("giraffe");
	menu.add (menuItem);
	//math skip card info
	menuItem = new JMenuItem ("Math Skip Card");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("mSkip");
	menu.add (menuItem);
    }


    //screen 3 of game - game screen
    public void screen3 ()
    {
	card3 = new Panel ();
	//changes background color
	card3.setBackground (new Color (255, 129, 126));
	menuBar = new JMenuBar ();
	menuItems ();
	//adds in title
	JLabel title = new JLabel ("     Too Many Monkeys");
	title.setFont (new Font ("Ink Free", Font.BOLD, 40));
	title.setForeground (new Color (255, 255, 255));
	//labels for players
	turnlabel = new JLabel ("                 Player: 1      ");
	turnlabel.setFont (new Font ("Ink Free", Font.BOLD, 15));
	turnlabel.setForeground (new Color (255, 255, 255));
	JLabel player1 = new JLabel ("Player 1:");
	player1.setFont (new Font ("Ink Free", Font.BOLD, 20));
	player1.setForeground (new Color (255, 255, 255));
	JLabel player2 = new JLabel ("Player 2:");
	player2.setFont (new Font ("Ink Free", Font.BOLD, 20));
	player2.setForeground (new Color (255, 255, 255));
	//buttons for screens
	JLabel blank = new JLabel ("             ");
	blank.setFont (new Font ("Ink Free", Font.BOLD, 20));
	blank.setForeground (new Color (255, 129, 126));
	JLabel deck = new JLabel ("Deck:");
	deck.setFont (new Font ("Ink Free", Font.BOLD, 25));
	deck.setForeground (new Color (255, 255, 255));
	JLabel discard = new JLabel ("Discard:");
	discard.setFont (new Font ("Ink Free", Font.BOLD, 25));
	discard.setForeground (new Color (255, 255, 255));
	JButton reset = new JButton ("   Reset   ");
	reset.setFont (new Font ("Ink Free", Font.BOLD, 18));
	reset.setForeground (new Color (255, 255, 255));
	reset.setBackground (new Color (55, 55, 90));
	reset.addActionListener (this);
	reset.setActionCommand ("reset");
	JButton save = new JButton ("   Save   ");
	save.setFont (new Font ("Ink Free", Font.BOLD, 18));
	save.setForeground (new Color (255, 255, 255));
	save.setBackground (new Color (55, 55, 90));
	save.addActionListener (this);
	save.setActionCommand ("save");
	JButton pop = new JButton ("Next Card");
	pop.setFont (new Font ("Ink Free", Font.BOLD, 18));
	pop.setForeground (new Color (255, 255, 255));
	pop.setBackground (new Color (55, 55, 90));
	pop.addActionListener (this);
	pop.setActionCommand ("pop");
	JButton open = new JButton ("   Open   ");
	open.setFont (new Font ("Ink Free", Font.BOLD, 18));
	open.setForeground (new Color (255, 255, 255));
	open.setBackground (new Color (55, 55, 90));
	open.addActionListener (this);
	open.setActionCommand ("open");
	JButton quit = new JButton ("   Quit   ");
	quit.setFont (new Font ("Ink Free", Font.BOLD, 18));
	quit.setForeground (new Color (255, 255, 255));
	quit.setBackground (new Color (55, 55, 90));
	quit.addActionListener (this);
	quit.setActionCommand ("quit");
	//grid layout for player 1 buttons
	Panel grid1 = new Panel (new GridLayout (1, 6));
	int m1 = 0;
	for (int i = 0 ; i < 1 ; i++)
	{
	    for (int j = 0 ; j < 6 ; j++)
	    {
		play1 [m1] = new JButton (createImageIcon ("images/back.png"));
		play1 [m1].setPreferredSize (new Dimension (100, 133));
		play1 [m1].addActionListener (this);
		play1 [m1].setActionCommand ("" + (m1 + 1));
		grid1.add (play1 [m1]);
		m1++;
	    }
	}
	//grid layout for layer 2 buttons
	Panel grid2 = new Panel (new GridLayout (1, 6));
	int m2 = 0;
	for (int i = 0 ; i < 1 ; i++)
	{
	    for (int j = 0 ; j < 6 ; j++)
	    {
		play2 [m2] = new JButton (createImageIcon ("images/back.png"));
		play2 [m2].setPreferredSize (new Dimension (100, 133));
		play2 [m2].addActionListener (this);
		play2 [m2].setActionCommand ("" + (m2 + 7));
		grid2.add (play2 [m2]);
		m2++;
	    }
	}
	//deck and discard pictures
	deckPic = new JButton (createImageIcon ("images/back.png"));
	deckPic.addActionListener (this);
	deckPic.setActionCommand ("newCard");
	deckPic.setPreferredSize (new Dimension (100, 133));
	discardPic = new JButton (createImageIcon ("images/placeholder.png"));
	discardPic.addActionListener (this);
	discardPic.setActionCommand ("discard");
	discardPic.setPreferredSize (new Dimension (100, 133));
	//labels added to coordinate with cards
	JLabel numbers = new JLabel ("            1                  2                    3                   4                   5                  6");
	numbers.setFont (new Font ("Ink Free", Font.BOLD, 16));
	numbers.setForeground (new Color (255, 255, 255));
	JLabel numbers1 = new JLabel ("            1                  2                    3                   4                   5                  6");
	numbers1.setFont (new Font ("Ink Free", Font.BOLD, 16));
	numbers1.setForeground (new Color (255, 255, 255));
	Panel top = new Panel ();
	top.add ("North", menuBar);
	top.add (title);
	top.add (turnlabel);
	card3.add (top);
	Panel f = new Panel ();
	f.add (player1);
	f.add (grid1);
	card3.add (numbers);
	card3.add (f);
	Panel decks = new Panel ();
	decks.add (deck);
	decks.add (deckPic);
	decks.add (discard);
	decks.add (discardPic);
	card3.add (decks);
	card3.add (numbers1);
	Panel e = new Panel ();
	e.add (player2);
	e.add (grid2);
	card3.add (e);
	Panel p = new Panel ();
	p.add (blank);
	p.add (reset);
	p.add (save);
	p.add (pop);
	p.add (open);
	p.add (quit);
	card3.add (p);
	p_card.add ("3", card3);
    }


    //screen 4 of game - game screen 2
    public void screen4 ()
    { //screen 4 is set up.
	card4 = new Panel ();
	//background color is changed
	card4.setBackground (new Color (255, 129, 126));
	//title for math questions
	JLabel title = new JLabel ("   Math Time");
	title.setFont (new Font ("Ink Free", Font.BOLD, 60));
	title.setForeground (new Color (255, 255, 255));
	JLabel question = new JLabel ("Your question is:   ");
	question.setFont (new Font ("Ink Free", Font.BOLD, 40));
	question.setForeground (new Color (255, 255, 255));
	JLabel answers = new JLabel ("     Possible answers:          ");
	answers.setFont (new Font ("Ink Free", Font.BOLD, 40));
	answers.setForeground (new Color (255, 255, 255));
	//possible answer buttons
	a1 = new JButton ("   Answer 1:    ");
	a1.setFont (new Font ("Ink Free", Font.BOLD, 18));
	a1.setForeground (new Color (255, 255, 255));
	a1.setBackground (new Color (55, 55, 90));
	a1.addActionListener (this);
	a1.setActionCommand ("a1");
	a2 = new JButton ("   Answer 2:    ");
	a2.setFont (new Font ("Ink Free", Font.BOLD, 18));
	a2.setForeground (new Color (255, 255, 255));
	a2.setBackground (new Color (55, 55, 90));
	a2.addActionListener (this);
	a2.setActionCommand ("a2");
	a3 = new JButton ("   Answer 3:   ");
	a3.setFont (new Font ("Ink Free", Font.BOLD, 18));
	a3.setForeground (new Color (255, 255, 255));
	a3.setBackground (new Color (55, 55, 90));
	a3.addActionListener (this);
	a3.setActionCommand ("a3");
	a4 = new JButton ("   Answer 4:   ");
	a4.setFont (new Font ("Ink Free", Font.BOLD, 18));
	a4.setForeground (new Color (255, 255, 255));
	a4.setBackground (new Color (55, 55, 90));
	a4.addActionListener (this);
	a4.setActionCommand ("a4");
	mathCard = new JLabel (createImageIcon ("images/math1.png"));
	card4.add (title);
	Panel f = new Panel ();
	f.add (question);
	f.add (mathCard);
	Panel b1 = new Panel ();
	b1.add (a1);
	b1.add (a2);
	Panel b2 = new Panel ();
	b2.add (a3);
	b2.add (a4);
	Panel b = new Panel (new GridLayout (2, 1));
	b.add (b1);
	b.add (b2);
	card4.add (f);
	card4.add (answers);
	card4.add (b);
	p_card.add ("4", card4);
    }


    //draws blank card on deck
    public void drawblank ()
    {
	deckPic.setIcon (createImageIcon ("images/back.png"));
	topPic = "back";
    }


    //draws blank card on discard pile
    public void drawblankDiscard ()
    {
	discardPic.setIcon (createImageIcon ("images/back.png"));
    }


    //draws black card on discard pile
    public void drawbackDiscard ()
    {
	discardPic.setIcon (createImageIcon ("images/placeholder.png"));
    }


    //shows card on deck
    public void showCard (Cards d)
    {
	deckPic.setIcon (createImageIcon ("images/" + d.getPicName () + ".png"));
	selectedValue = d.getValue ();
	topPic = "";
    }


    //shows the card on discard pile
    public void showDiscard (Cards c)
    {
	discardPic.setIcon (createImageIcon ("images/" + c.getPicName () + ".png"));
    }


    //pop up messages for user
    public void popUp (String a, String b)
    {
	JOptionPane.showMessageDialog (null, createImageIcon ("images/" + a + ".png"), b, JOptionPane.INFORMATION_MESSAGE);

    }


    //holds both redraw methods
    public void redraw ()
    {
	redraw1 ();
	redraw2 ();
    }


    //redraw for player 1
    public void redraw1 ()
    {
	int n = 0;
	for (int j = 0 ; j < 6 ; j++)
	{
	    //depending on value in array, sets picture.
	    if (p1p2 [0] [n] == -7)
		play1 [n].setIcon (createImageIcon ("images/placeholder.png"));
	    else if (p1p2 [0] [n] == 1)
		play1 [n].setIcon (createImageIcon ("images/m1.png"));
	    else if (p1p2 [0] [n] == 2)
		play1 [n].setIcon (createImageIcon ("images/m2.png"));
	    else if (p1p2 [0] [n] == 3)
		play1 [n].setIcon (createImageIcon ("images/m3.png"));
	    else if (p1p2 [0] [n] == 4)
		play1 [n].setIcon (createImageIcon ("images/m4.png"));
	    else if (p1p2 [0] [n] == 5)
		play1 [n].setIcon (createImageIcon ("images/m5.png"));
	    else if (p1p2 [0] [n] == 6)
		play1 [n].setIcon (createImageIcon ("images/m6.png"));
	    else if (p1p2 [0] [n] == 8)
		play1 [n].setIcon (createImageIcon ("images/wild.png"));
	    else
		play1 [n].setIcon (createImageIcon ("images/back.png"));
	    n++;
	}
    }


    //redraw for player 2
    public void redraw2 ()
    {
	int m = 0;
	for (int k = 0 ; k < 6 ; k++)
	{
	    //depending on value in arrary, sets picture
	    if (p1p2 [1] [m] == -7)
		play2 [m].setIcon (createImageIcon ("images/placeholder.png"));
	    else if (p1p2 [1] [m] == 1)
		play2 [m].setIcon (createImageIcon ("images/m1.png"));
	    else if (p1p2 [1] [m] == 2)
		play2 [m].setIcon (createImageIcon ("images/m2.png"));
	    else if (p1p2 [1] [m] == 3)
		play2 [m].setIcon (createImageIcon ("images/m3.png"));
	    else if (p1p2 [1] [m] == 4)
		play2 [m].setIcon (createImageIcon ("images/m4.png"));
	    else if (p1p2 [1] [m] == 5)
		play2 [m].setIcon (createImageIcon ("images/m5.png"));
	    else if (p1p2 [1] [m] == 6)
		play2 [m].setIcon (createImageIcon ("images/m6.png"));
	    else if (p1p2 [1] [m] == 8)
		play2 [m].setIcon (createImageIcon ("images/wild.png"));
	    else
		play2 [m].setIcon (createImageIcon ("images/back.png"));
	    m++;
	}
    }


    //pops a card out of the deck
    public void popDeck ()
    {
	if (!deck.isEmpty ())
	{
	    Cards c = deck.pop ();
	    //stores the card in temp
	    temp = c;
	    selectedValue = c.getValue ();
	    num += -1;
	    click = 1;
	    //displays the card
	    showCard (c);
	}
	else
	{
	    num = 0;
	    drawblank ();
	    //if cards are finished reset the game
	    popUp ("back", "Deck is empty!");
	    popUp ("back", "The game will reset!");
	    resetGame ();
	}
    }


    //pops a card out of the discard pile
    public void popDiscard (int pop)
    {
	if (!discard.isEmpty ())
	{
	    click = 1;
	    Cards d = discard.pop ();
	    //display card
	    showDiscard (d);
	    num1 += -1;
	    //pop the deck in certain situations
	    if (pop == 1)
		popDeck ();
	}
	else
	{
	    num1 = 0;
	    //draw black card if discard pile has no cards
	    drawbackDiscard ();
	    popUp ("wild", "Discard pile is empty!");
	    popDeck ();
	}
    }


    //discards a card player does not want
    public void discard (Cards c)
    {
	click = 0;
	discard.push (c);
	//displays discarded card
	showDiscard (c);
	num1 += 1;
	drawblank ();
	if (chosen == 'd')
	    switchTurn ();
    }


    //various messages throughout the game
    public void messages (int a)
    {
	//invalid move for do not disturb card
	if (a == 1)
	{
	    JOptionPane.showMessageDialog (null, "* * *I N V A L I D    M O V E *  * * \n \n"
		    + " Click on any face up card of the opposite player to\n"
		    + " turn their card over! \n"
		    + " If the opposite player has no face up cards, \n"
		    + " simply discard this card.. \n"
		    + "", "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
	}
	//invalid move for do not disturb card
	if (a == 2)
	{
	    JOptionPane.showMessageDialog (null, "* * *I N V A L I D    M O V E *  * * \n \n"
		    + " There is no card here.\n"
		    + " You must flip over a FACE UP card. \n"
		    + "", "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
	}
	//tells user to search the pile for a number card
	if (a == 3)
	{
	    JOptionPane.showMessageDialog (null, "* * *S E A R C H   T H E   P I L E*  * * \n \n"
		    + " This is a super special card!\n"
		    + " You get to search the discard pile! \n"
		    + " Click on the 'general' tab at the top \n"
		    + " and then click on 'discard' \n"
		    + "", "Raccoon", JOptionPane.INFORMATION_MESSAGE);
	}
	//if the user already has the requested card
	if (a == 4)
	{
	    JOptionPane.showMessageDialog (null, "* * *D U P L I C A T E*  * * \n \n"
		    + " Looks like you have this card already!\n"
		    + " Please choose a different card! \n"
		    + "", "Invalid", JOptionPane.INFORMATION_MESSAGE);
	}
	//if the user tries to take a non-number card from the discard pile
	if (a == 5)
	{
	    JOptionPane.showMessageDialog (null, "* * *I N V A L I D*  * * \n \n"
		    + " You can only choose number cards!\n"
		    + " Invalid move.\n"
		    + "", "Invalid", JOptionPane.INFORMATION_MESSAGE);
	}
	//if user moves on wrong turn
	if (a == 6)
	{
	    JOptionPane.showMessageDialog (null, "* * *N O T    Y O U R   T U R N*  * * \n \n"
		    + " It's not your turn!\n"
		    + "", "Invalid", JOptionPane.INFORMATION_MESSAGE);
	}
	//if there are no number cards in discard pile
	if (a == 7)
	{
	    JOptionPane.showMessageDialog (null, "* * *N O   N U M B E R   C A R D S*  * * \n \n"
		    + " Oh no! No number cards in deck.\n"
		    + " Please discard the card.\n"
		    + "", "No cards", JOptionPane.INFORMATION_MESSAGE);
	}
    }


    //do not disturb card for player 1
    public void disturb1 ()
    {
	//if the user tries to flip over their own card
	if (pos >= 1 && pos <= 6)
	{
	    messages (1);
	    click = 2;
	}
	//if the user clicks on a face down card
	else if (p1p2 [1] [pos - 7] == 0)
	{
	    messages (2);
	    click = 2;
	}
	//if the user clicks on a face up card of player 2
	else
	{
	    int a = p1p2 [1] [pos - 7];
	    a = a * -1;
	    p1p2 [1] [pos - 7] = a;
	    redraw ();
	    discard (temp);
	}
    }


    //do not disturb card for player 2
    public void disturb2 ()
    {
	//if the user tries to flip over their own card
	if (pos >= 7 && pos <= 12)
	{
	    messages (1);
	    click = 2;
	}
	//if the user clicks on a face down card
	else if (p1p2 [0] [pos - 1] == 0)
	{
	    messages (2);
	    click = 2;
	}
	//if the user clicks on a face up card of player 1
	else
	{
	    int a = p1p2 [0] [pos - 1];
	    a = a * -1;
	    p1p2 [0] [pos - 1] = a;
	    redraw ();
	    discard (temp);
	}
    }


    //holds do not disturb for each player
    public void doNotDisturb ()
    {
	if (turn == 1)
	    disturb1 ();
	else
	    disturb2 ();
    }


    //player 1's turn
    public void turn1 ()
    {
	//if the player has not won yet let them move
	if (!win ())
	{
	    //do not disturb and math problem card
	    if (selectedValue == 10)
		doNotDisturb ();
	    else if (selectedValue == 11)
		mathProblem ();
	    //various situations where user might choose a number card or wild monkey
	    else if (pos == selectedValue && p1p2 [0] [pos - 1] == 8)
		turn (0);
	    else if ((pos == selectedValue || selectedValue == 8) && p1p2 [0] [pos - 1] == -8)
		turn (-2);
	    else if (pos == selectedValue && p1p2 [0] [pos - 1] == (pos * -1))
		turn (-1);
	    else if (selectedValue == 8 && p1p2 [0] [pos - 1] == (pos * -1))
		turn (-3);
	    else if (pos == selectedValue && (p1p2 [0] [pos - 1] == 0 || p1p2 [0] [pos - 1] == 8))
		turn (1);
	    else if (selectedValue == 8 && p1p2 [0] [pos - 1] != 8 && p1p2 [0] [pos - 1] != pos)
		turn (1);
	    //if user gets skip card
	    else if (selectedValue == 9)
		skipCard ();
	    //if the user gets the raccoon
	    else if (selectedValue == 7)
		messages (3);
	    //if the user already has the card
	    else if (p1p2 [0] [pos - 1] == selectedValue && p1p2 [0] [pos - 1] != 8)
		showStatus ("move to discard pile");
	    //any other moves are not valid
	    else
	    {
		showStatus ("not a valid move");
		click = 1;
	    }
	}
	//if user has not won, check if round needs to be updated
	if (!win ())
	    roundUpdate ();
	//if player 1 wins reset the game
	else
	{
	    popUp ("back", "PLAYER 1 WINS! Play Again!");
	    resetGame ();
	}
    }


    //player 2's turn
    public void turn2 ()
    {
	//if user has not won, let player move
	if (!win ())
	{ //do not disturb and math problem card
	    if (selectedValue == 10)
		doNotDisturb ();
	    else if (selectedValue == 11)
		mathProblem ();
	    //various situations for number and wild cards
	    else if ((pos - 6) == selectedValue && p1p2 [1] [pos - 7] == 8)
		turn (0);
	    else if (((pos - 6) == selectedValue || selectedValue == 8) && p1p2 [1] [pos - 7] == -8)
		turn (-2);
	    else if ((pos - 6) == selectedValue && p1p2 [1] [pos - 7] == ((pos - 6) * -1))
		turn (-1);
	    else if (selectedValue == 8 && p1p2 [1] [pos - 7] == ((pos - 6) * -1))
		turn (-3);
	    else if ((pos - 6) == selectedValue && (p1p2 [1] [pos - 7] == 0 || p1p2 [1] [pos - 7] == 8))
		turn (1);
	    else if (selectedValue == 8 && p1p2 [1] [pos - 7] != 8 && p1p2 [1] [pos - 7] != (pos - 6))
		turn (1);
	    //user gets skip card
	    else if (selectedValue == 9)
		skipCard ();
	    //user gets raccoon
	    else if (selectedValue == 7)
		messages (3);
	    //if the user already has the card
	    else if (p1p2 [1] [pos - 7] == selectedValue && p1p2 [1] [pos - 7] != 8)
		showStatus ("move to discard pile");
	    //all other moves are not valid
	    else
	    {
		showStatus ("not a valid move");
		click = 2;
	    }
	}
	//if player 2 has not won, check for a round update
	if (!win ())
	    roundUpdate ();
	//if player 2 won, reset the game
	else
	{
	    popUp ("back", "PLAYER 2 WINS! Play Again!");
	    resetGame ();
	}
    }


    //carries out the turn for both players
    public void turn (int a)
    {
	if (turn == 1)
	    p1p2 [0] [pos - 1] = selectedValue;
	else
	    p1p2 [1] [pos - 7] = selectedValue;
	redraw ();
	//pops the deck
	if (a == 1 && chosen == 'd')
	    popDeck ();
	//pops the discard pile
	else if (a == 1 && chosen == 'c')
	{
	    click--;
	    popDiscard (1);
	    chosen = 'd';
	}

	else if (a == -1 && chosen == 'c')
	{
	    click = 1;
	    push ("monkey" + selectedValue, selectedValue);
	    popUp ("m" + selectedValue, "You got your card back");
	    popDiscard (0);
	    chosen = 'd';
	}
	//pushes in a new number card
	else if (a == -1)
	{
	    click = 1;
	    push ("monkey" + selectedValue, selectedValue);
	    popUp ("m" + selectedValue, "You got your card back");

	}
	//pushes in a wild card
	else if (a == -2)
	{
	    JOptionPane.showMessageDialog (null, createImageIcon ("images/wild.png"), "You got your wild back!", JOptionPane.INFORMATION_MESSAGE);
	    click = 1;
	    push ("wild", 8);
	    popDiscard (0);
	    chosen = 'd';
	}
	//pushes in card of corresponding position
	else if (a == -3)
	{
	    click = 1;
	    pushCard (pos);
	}
	//pushes in a wild monkey
	else
	{
	    click = 1;
	    push ("wild", 8);
	    chosen = 'd';
	}
	roundUpdate ();
    }


    //method to push in cards if value is unknown
    public void pushCard (int card)
    {
	if (card == 1 || card == 7)
	    push ("monkey1", 1);
	else if (card == 2 || card == 8)
	    push ("monkey2", 2);
	else if (card == 3 || card == 9)
	    push ("monkey3", 3);
	else if (card == 4 || card == 10)
	    push ("monkey4", 4);
	else if (card == 5 || card == 11)
	    push ("monkey5", 5);
	else if (card == 6 || card == 12)
	    push ("monkey6", 6);
    }


    //method to push in cards if value is known
    public void push (String a, int b)
    {
	if (!deck.isFull ())
	{
	    //creates new custom card of players choice
	    Cards p = new Cards (a, b);
	    deck.push (p);
	    num += 1;
	    popDeck ();
	}
	else
	    showStatus ("deck is full");
    }


    //method to switch turn between players
    public void switchTurn ()
    {
	if (turn == 1)
	    turn = 2;
	else
	    turn = 1;
	turnlabel.setText ("                 Player: " + turn + "  ");
    }


    //checks for players winning
    public boolean win ()
    {
	int c = 0;
	for (int i = 0 ; i < 6 ; i++)
	{
	    if (p1p2 [0] [c] == -7)
		c++;
	}
	int b = 0;
	for (int j = 0 ; j < 6 ; j++)
	{
	    if (p1p2 [1] [b] == -7)
		b++;
	}
	if (c == 6)
	    return true;
	else if (b == 6)
	    return true;
	else
	    return false;
    }


    //messages to pop up whenever a new round begins
    public void roundMessages (int w)
    {
	if (turn == 1)
	    popUp ("wild", "New Round. Player one has " + w + " cards.");
	else
	    popUp ("wild", "New Round. Player two has " + w + " cards.");
    }


    //tracks player 1's progress
    public int countP1 ()
    {
	int w = 0;
	for (int i = 0 ; i < p1cards ; i++)
	{
	    if (p1p2 [0] [i] > 0)
		w++;
	}
	return w;
    }


    //tracks player 2's progress
    public int countP2 ()
    {
	int x = 0;
	for (int i = 0 ; i < p2cards ; i++)
	{
	    if (p1p2 [1] [i] > 0)
		x++;
	}
	return x;
    }


    //checks if a new round is going to begin
    public void roundUpdate ()
    {
	//checks for player 1
	if (countP1 () == p1cards)
	{
	    roundMessages (countP1 ());
	    p1cards--;
	    resetRound ();
	    p1p2 [0] [p1cards] = -7;
	    redraw ();
	    switchTurn ();
	}
	//checks for player 2
	if (countP2 () == p2cards)
	{
	    roundMessages (countP2 ());
	    p2cards--;
	    resetRound ();
	    p1p2 [1] [p2cards] = -7;
	    redraw ();
	    switchTurn ();
	}
    }


    //reset of entire game
    public void resetGame ()
    {
	//global variables are set back to initial values
	num = 0;
	num1 = 0;
	//decks are cleared out
	deck.clear ();
	discard.clear ();
	drawblank ();
	drawblankDiscard ();
	//arrays are cleared out
	for (int i = 0 ; i < 6 ; i++)
	{
	    p1p2 [0] [i] = 0;
	    p1p2 [1] [i] = 0;
	}
	redraw ();
	//variables are reset
	click = 0;
	first = 'n';
	p1cards = 6;
	p2cards = 6;
	pos = 0;
	selectedValue = -1;
	turn = 1;
	searchD = 0;
	chosen = 'd';
	topPic = "";
	cardUp = 0;
	//turn label is reset
	turnlabel.setText ("                 Player: " + turn + "  ");
    }


    //resets game after a round is complete (not entire game reset)
    public void resetRound ()
    {
	//sets num and num1 back to 0
	num = 0;
	num1 = 0;
	//clear out decks
	deck.clear ();
	discard.clear ();
	drawblank ();
	drawblankDiscard ();
	//set all cards back to 0 unless they are finished a round
	for (int i = 0 ; i < 6 ; i++)
	{
	    if (p1p2 [0] [i] != -7)
		p1p2 [0] [i] = 0;
	    if (p1p2 [1] [i] != -7)
		p1p2 [1] [i] = 0;
	}
	redraw ();
	click = 0;
	first = 'n';
    }


    //tracks skips for each player
    public void skipCard ()
    {
	if (turn == 1)
	    popUp ("skip", "Player 2 your turn is skipped!!");
	else
	    popUp ("skip", "Player 1 your turn is skipped!!");
	discard (temp);
	switchTurn ();
    }


    //if user wants to take the top card from the discard pile
    public void discardCard1 ()
    {
	if (!discard.isEmpty ())
	{
	    chosen = 'c';
	    Cards d = discard.pop ();
	    temp = d;
	    //if the top card is a number card
	    if (d.getValue () > 0 && d.getValue () < 7)
	    {
		//if the user alerady has this card
		if ((turn == 1 && p1p2 [0] [d.getValue () - 1] == d.getValue ()) || (turn == 2 && p1p2 [1] [d.getValue () - 1] == d.getValue ()))
		{
		    messages (4);
		    discard.push (d);
		    click--;
		}
		//else let the player take the card
		else
		{
		    num1 += -1;
		    selectedValue = d.getValue ();
		    click++;
		}
	    }
	    //if the top card is not a number card
	    else
	    {
		click--;
	    }
	}
	else
	    popUp ("back", "No cards in discard pile");
    }


    //everytime discard pile is clicked
    public void discardCard ()
    {
	click++;
	//on the first click check if card be be used by player
	if (click == 1)
	    discardCard1 ();
	//on the second click remind the player to click on a card first
	else if (click == 2)
	{
	    showStatus ("Pick a card to discard first");
	    click--;
	}
	//on the third click, discard whatever card they wish to
	else
	    discardCard3 ();
    }


    //if discard pile is clicked on click 3, move the card into the discard pile
    public void discardCard3 ()
    {
	num1 += 1;
	discard.push (temp);
	showDiscard (temp);
	drawblank ();
	click = 0;
	switchTurn ();
    }


    //shuffles the math cards
    public void shuffleMath ()
    {
	//shuffles the math deck and pops out a random card
	deck1.shuffle ();
	mathCards c = deck1.pop ();
	//displays the card
	showMath (c);
	int n = 0;
	int m = 0;
	//stores name of the card in string
	String n1 = c.getName ();
	//depending on the value, there are 3 different forms of questions
	if (c.getValue () == 1)
	{
	    //sends in char to convert to integer using ASCII value
	    n = c.generateNumValue ((n1.charAt (4)));
	    ans = c.question1 (n);

	}
	else if (c.getValue () == 2)
	{
	    //sends ins char to convert to integers using ASCII values
	    n = c.generateNumValue ((n1.charAt (4)));
	    m = c.generateNumValue ((n1.charAt (5)));
	    ans = c.question2 (n, m);
	}
	else if (c.getValue () == 3)
	{
	    //sends ins char to convert to integers using ASCII values
	    n = c.generateNumValue ((n1.charAt (4)));
	    m = c.generateNumValue ((n1.charAt (5)));
	    ans = c.question3 (n, m);
	}
	//right and wrong answers are displayed on buttons
	a1.setText ("    " + (ans + 8) + "    ");
	a2.setText ("    " + (ans - 10) + "    ");
	a3.setText ("    " + ans + "    ");
	a4.setText ("    " + (ans - 5) + "    ");
    }


    //shows the math card
    public void showMath (mathCards d)
    {
	mathCard.setIcon (createImageIcon ("images/" + d.getPicName () + ".png"));
    }


    //shuffles the deck the first time the user clicks 'new card'
    public void shuffle ()
    {
	chosen = 'd';
	deck.clear ();
	deck.shuffle ();
	num = deck.size ();
	//pop out the top card and store it in temp
	Cards c = deck.pop ();
	temp = c;
	selectedValue = temp.getValue ();
	showCard (temp);
	first = 'y';
	num += -1;
    }


    //new card is popped out (pop)
    public void newCard ()
    {
	//on first click pop out a card
	if (click == 1)
	{
	    chosen = 'd';
	    popDeck ();
	}
	//other than that wait for user to make a different move
	else if (click == 2)
	{
	    showStatus ("pick a position to place the card");
	    click = 1;
	}
	else if (click == 3)
	{
	    showStatus ("pick a position to place the card");
	    click--;
	}
    }


    //when the user is on their third click
    public void click3 ()
    {
	//handles giraffe and elephant cards
	if (selectedValue == 0 && temp.getName ().equals ("giraffe"))
	    popUp ("giraffe", "No giraffe's allowed!! Discard");
	else if (selectedValue == 0 && temp.getName ().equals ("elephant"))
	    popUp ("elephant", "No elephant's allowed!! Discard");
	//if the user clicks on the wrong player's buttons and they don't have the do not disturb card, move is invalid
	if ((turn == 1 && pos > 6 || turn == 2 && pos < 7) && selectedValue != 10)
	{
	    messages (6);
	    click = 1;
	}
	//all other moves might be valid, send them into turn1/turn2 method to check
	else if (turn == 1)
	    turn1 ();
	else if (turn == 2)
	    turn2 ();
    }


    //saves all variables and cards to file
    public void save (String filename)
    {
	int choice = 0;
	if (chosen == 'd')
	    choice = 1;
	else
	    choice = 2;
	//checks if the deck had a blank card when progress was saved
	if (topPic.equals ("back"))
	{
	    cardUp = 0;
	}
	else
	{
	    deck.push (temp);
	    num++;
	    cardUp = 1;
	}
	PrintWriter out;
	try
	{
	    //start printing to file
	    out = new PrintWriter (new FileWriter (filename));
	    //prints out numbers in array to save position of players
	    for (int i = 0 ; i < 6 ; i++)
		out.println ("" + p1p2 [0] [i]);
	    for (int i = 0 ; i < 6 ; i++)
		out.println ("" + p1p2 [1] [i]);
	    out.println ("" + num);
	    //pushes in cards from deck in the opposite order
	    while (!deck.isEmpty ())
		tempDeck.push (deck.pop ());
	    for (int i = 0 ; i < num ; i++)
	    {
		Cards d = tempDeck.pop ();
		deck.push (d);
		out.println (d.getName ());
		out.println (d.getValue ());
	    }
	    out.println ("" + num1);
	    //pushes in cards from discard in the opposite order
	    while (!discard.isEmpty ())
		tempDiscard.push (discard.pop ());
	    //puts cards back in discard pile
	    for (int i = 0 ; i < num1 ; i++)
	    {
		Cards c = tempDiscard.pop ();
		discard.push (c);
		out.println (c.getName ());
		out.println (c.getValue ());
	    }
	    //prints out other variables in game
	    out.println ("" + turn);
	    out.println ("" + click);
	    out.println ("" + cardUp);
	    out.println ("" + selectedValue);
	    out.println ("" + choice);
	    out.println ("" + p1cards);
	    out.println ("" + p2cards);
	    out.close ();
	    showStatus ("Cards " + deck.size () + " Discard " + discard.size ());
	    if (cardUp == 1)
		popDeck ();
	}
	catch (IOException e)
	{
	    System.out.println ("Error opening file " + e);
	}
    }


    //opens old progress using file
    public void open (String filename)
    {
	String name = "";
	int value = 0;
	int choice = 0;
	//clear out decks
	deck.clear ();
	discard.clear ();
	BufferedReader in;
	try
	{
	    //start reading from file
	    in = new BufferedReader (new FileReader (filename));
	    String input = in.readLine ();
	    //read in the numbers and store them in the arrays
	    for (int i = 0 ; i < 6 ; i++)
	    {
		p1p2 [0] [i] = Integer.parseInt (input);
		input = in.readLine ();
	    }
	    for (int i = 0 ; i < 6 ; i++)
	    {
		p1p2 [1] [i] = Integer.parseInt (input);
		input = in.readLine ();
	    }

	    num = Integer.parseInt (input);
	    input = in.readLine ();
	    //read in the names and values of the deck
	    int d = 0;
	    while (d != num)
	    {
		name = (input);
		input = in.readLine ();
		value = Integer.parseInt (input);
		input = in.readLine ();
		Cards c = new Cards (name, value);
		deck.push (c);
		d++;
	    }
	    num1 = Integer.parseInt (input);
	    input = in.readLine ();
	    //read the names and values of the discard pile
	    int a = 0;
	    while (a != num1)
	    {
		name = (input);
		input = in.readLine ();
		value = Integer.parseInt (input);
		input = in.readLine ();
		Cards b = new Cards (name, value);
		discard.push (b);
		a++;
	    }
	    //read other variables in game
	    turn = Integer.parseInt (input);
	    input = in.readLine ();
	    click = Integer.parseInt (input);
	    input = in.readLine ();
	    cardUp = Integer.parseInt (input);
	    input = in.readLine ();
	    selectedValue = Integer.parseInt (input);
	    input = in.readLine ();
	    choice = Integer.parseInt (input);
	    input = in.readLine ();
	    p1cards = Integer.parseInt (input);
	    input = in.readLine ();
	    p2cards = Integer.parseInt (input);
	    input = in.readLine ();
	    in.close ();
	    if (choice == 1)
		chosen = 'd';
	    else
		chosen = 'c';
	    //check the status of the discard pile and deck when they were saved
	    updateDeck ();
	    updateDiscard ();
	    first = 'y';
	}
	catch (IOException e)
	{
	    System.out.println ("Error opening file " + e);
	}
	//redraw changes onto screen
	redraw ();
    }



    //pops out a card from the discard pile if it's not empty after open is clicked
    public void updateDiscard ()
    {
	//if the discard pile is not empty, pop out the stop card and dispay it
	if (num1 != 0)
	{
	    Cards n = discard.pop ();
	    showDiscard (n);
	    discard.push (n);
	}
	//if discard pile is empty draw a blank card
	else
	    drawblankDiscard ();
    }


    //pops out a card from the deck if its not empty after opening
    public void updateDeck ()
    {
	if (cardUp == 1)
	    popDeck ();
	else
	{
	    drawblank ();
	    click = 0;
	}
    }


    //sorts the search array which holds the discard number cards
    public int[] sortDiscard ()
    {
	//uses selection sort to search
	for (int left = (search.length - 1) ; left > 0 ; left--)
	{
	    int max = 0;
	    for (int i = 1 ; i <= left ; i++)
	    {
		if (search [max] < search [i])
		    max = i;
	    }
	    int temp = search [max];
	    search [max] = search [left];
	    search [left] = temp;
	}
	//returns sorted array
	return search;
    }


    //finds all the number cards in the discard pile
    public void searchDiscard ()
    {
	tempDiscard.clear ();
	int a = 0;
	int track = 0;
	for (int i = 0 ; i < (num1) ; i++)
	{
	    Cards d = discard.pop ();
	    tempDiscard.push (d);
	    a = d.getValue ();
	    //pushes out all number card values from discard pile
	    if (a >= 1 && a <= 6)
	    {
		search [i] = a;
		track++;
	    }
	}
	//reverses the order
	while (!tempDiscard.isEmpty ())
	    discard.push (tempDiscard.pop ());
	//if there are number cards sort the array
	if (track > 0)
	{
	    search = sortDiscard ();
	    findCard ();
	}
	//if there are no number cards display message
	else
	    messages (7);

    }


    //takes out a card from the discard pile after user selects a card
    public void takeOutCard (int card)
    {
	tempDiscard.clear ();
	int b = 0;
	while (!discard.isEmpty ())
	{
	    Cards c = discard.pop ();
	    //if the card is being found for the first time, pop it out of the deck
	    if (c.getValue () == card && b == 0)
		b = 1;
	    //all other times push the card into the temporary discard
	    else
		tempDiscard.push (c);
	}
	//reverse the order
	while (!tempDiscard.isEmpty ())
	    discard.push (tempDiscard.pop ());
	num1 += -1;
    }


    //replace card for player 1
    public void replaceCard1 (int card)
    {
	//if user chooses an empty position
	if (p1p2 [0] [card - 1] == 0)
	{
	    p1p2 [0] [card - 1] = card;
	    redraw ();
	    discard (temp);
	    takeOutCard (card);
	    switchTurn ();
	    popDeck ();
	}
	//if user chooses a wild or flipped over wild position
	else if (p1p2 [0] [card - 1] == 8 || p1p2 [0] [card - 1] == -8)
	{
	    p1p2 [0] [card - 1] = card;
	    redraw ();
	    discard (temp);
	    click = 1;
	    takeOutCard (card);
	    push ("wild", 8);
	    chosen = 'd';
	    switchTurn ();
	}
	//other possiblities for turn 1
	else
	{
	    p1p2 [0] [card - 1] = card;
	    redraw ();
	    discard (temp);
	    click = 1;
	    takeOutCard (card);
	    pushCard (card);
	    chosen = 'd';
	    switchTurn ();
	}
    }


    //replace card for player 2
    public void replaceCard2 (int card)
    {
	//if user chooses position with no card
	if (p1p2 [1] [card - 1] == 0)
	{
	    p1p2 [1] [card - 1] = card;
	    redraw ();
	    discard (temp);
	    takeOutCard (card);
	    switchTurn ();
	    popDeck ();
	}
	//if user chooses position with a wild or flipped over wild
	else if (p1p2 [1] [card - 1] == 8 || p1p2 [1] [card - 1] == -8)
	{
	    p1p2 [1] [card - 1] = card;
	    redraw ();
	    discard (temp);
	    click = 1;
	    takeOutCard (card);
	    push ("wild", 8);
	    chosen = 'd';
	    switchTurn ();
	}
	//other possiblities for player 2
	else
	{
	    p1p2 [1] [card - 1] = card;
	    redraw ();
	    discard (temp);
	    click = 1;
	    takeOutCard (card);
	    pushCard (card);
	    chosen = 'd';
	    switchTurn ();
	}
    }


    //replaces the card after user chooses from the discard pile
    public void replaceCard (int card)
    {
	if (turn == 1)
	    replaceCard1 (card);
	else
	    replaceCard2 (card);
    }


    //finds the card the user wants
    public void findCard ()
    {
	int fcard = 0;
	//generates the choices of cards for the user using pull down box
	String[] possibleValues = {"Monkey 1", "Monkey 2", "Monkey 3", "Monkey 4", "Monkey 5", "Monkey 6"};
	String selectedValue = (String) JOptionPane.showInputDialog (null,
		"What card are you looking for?", "Input", JOptionPane.INFORMATION_MESSAGE, null,
		possibleValues, possibleValues [0]);
	if (selectedValue.equals ("Monkey 1"))
	    fcard = 1;
	else if (selectedValue.equals ("Monkey 2"))
	    fcard = 2;
	else if (selectedValue.equals ("Monkey 3"))
	    fcard = 3;
	else if (selectedValue.equals ("Monkey 4"))
	    fcard = 4;
	else if (selectedValue.equals ("Monkey 5"))
	    fcard = 5;
	else if (selectedValue.equals ("Monkey 6"))
	    fcard = 6;
	//checks if user choice is valid
	if (choiceValid (fcard))
	{
	    //does a binary search for the card
	    if (binarySearch (fcard))
	    {
		//display message for player
		displayCardMessage (fcard);
		replaceCard (fcard);
	    }
	    //if the card is not the in the discard pile, display message
	    else
		popUp ("back", "Not in discard pile!");
	}
	//if user already has the card
	else
	    popUp ("back", "You already have this card! Choose another card!");
    }


    //displays message to let player know card has been found
    public void displayCardMessage (int fcard)
    {
	if (fcard == 1)
	    popUp ("m1", "Found it!");
	if (fcard == 2)
	    popUp ("m2", "Found it!");
	if (fcard == 3)
	    popUp ("m3", "Found it!");
	if (fcard == 4)
	    popUp ("m4", "Found it!");
	if (fcard == 5)
	    popUp ("m5", "Found it!");
	if (fcard == 6)
	    popUp ("m6", "Found it!");
    }


    //checks if the user choice for their card is valid
    public boolean choiceValid (int choice)
    {
	if (turn == 1 && p1p2 [0] [choice - 1] == 0)
	    return true;
	else if (turn == 1 && p1p2 [0] [choice - 1] == 8)
	    return true;
	else if (turn == 1 && p1p2 [0] [choice - 1] == -8)
	    return true;
	else if (turn == 1 && p1p2 [0] [choice - 1] == (choice * -1))
	    return true;
	else if (turn == 2 && p1p2 [1] [choice - 1] == 0)
	    return true;
	else if (turn == 2 && p1p2 [1] [choice - 1] == 8)
	    return true;
	else if (turn == 2 && p1p2 [1] [choice - 1] == -8)
	    return true;
	else if (turn == 2 && p1p2 [1] [choice - 1] == (choice * -1))
	    return true;
	else
	    return false;
    }


    //does a binary search for the card user is looking for
    public boolean binarySearch (int f)
    {
	//set high to 35
	int high = search.length - 1;
	//set low to 0
	int low = 0;
	//start with found it being false
	boolean foundit = false;
	int mid = 0;
	while (high >= low && !foundit)
	{
	    mid = (high + low) / 2;
	    if (search [mid] == f)
		foundit = true;
	    else if (f > search [mid])
		low = mid + 1;
	    else if (f < search [mid])
		high = mid - 1;
	}
	if (foundit)
	    return true;
	else
	    return false;
    }


    //takes user to screen 4 where a math problem card is generated
    public void mathProblem ()
    {
	cdLayout.show (p_card, "4");
	shuffleMath ();
    }


    //pop up message with number cards
    public void cardsInfoNum ()
    {
	JOptionPane.showMessageDialog (null, "* * *N U M B E R     C A R D S*  * * \n \n"
		+ " There are 6 different number cards, 1 through 6. \n"
		+ " There are 6 of each number card in the deck. \n"
		+ " You can gain a number card 4 different ways! \n"
		+ " 1) You flip it over from the deck.\n "
		+ " 2) You flip it over from your row of face down cards\n "
		+ " 3) You take it from the discard pile, IF it is the card at the top! \n"
		+ " 4) If you get the card with the raccoon on it, \n"
		+ " you get to search the discard pile for 1 number card. \n"
		+ " To use a number card, click on it and then on the corresponding position \n"
		+ " you wish to place it. \n"
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //pop up message for skip card
    public void skipInfo ()
    {
	JOptionPane.showMessageDialog (null, "* * *S K I P    C A R D *  * * \n \n"
		+ " There are 4 skip cards in the deck. \n"
		+ " If you get a skip card, the other player's turn is skipped, \n"
		+ " and you get to draw another card! \n"
		+ " To use the skip card, click on the card and then any random position in your row.\n "
		+ " This can be any row position from 1-6.\n "
		+ " If you do not wish to use the skip card,\n "
		+ " you can simply place it in the discard pile. \n"
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //pop up message for math skip card
    public void mathskipInfo ()
    {
	JOptionPane.showMessageDialog (null, "* * *M A T H    S K I P    C A R D *  * * \n \n"
		+ " There are 5 math skip cards in the deck. \n"
		+ " If you get a math skip card, and you wish to use it, click \n"
		+ " the card, and then click any position in your row (1 through 6) \n"
		+ " After that, you will be taken to a different screen whre you will\n "
		+ " have to answer a math question. If you get the question right, \n "
		+ " you skip your opponents turn, and you get to have another turn.\n "
		+ " If you get it wrong, your turn is over. \n"
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //pop up message for wild card
    public void wildInfo ()
    {
	JOptionPane.showMessageDialog (null, "* * *W I L D   M O N K E Y    C A R D *  * * \n \n"
		+ " There are 4 wild monkey cards in the deck. \n"
		+ " If you get a wild monkey, you are in luck! \n"
		+ " The wild monkey can act as a substitution for any number card! \n"
		+ " Bonus: If you place a wild monkey in a position, and then later draw\n "
		+ " the number card for that corresponding position, you can swap it for \n "
		+ " the wild monkey, and use the wild monkey somewhere else!,\n "
		+ " To use the card, click on the card, and then click on the position you,\n "
		+ " wish to place it (make sure that position is empty!)\n "
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //pop up message for disturb card
    public void disturbInfo ()
    {
	JOptionPane.showMessageDialog (null, "* * *D O   N O T   D I S T U R B   C A R D *  * * \n \n"
		+ " There are 2 Do Not Disturb cards in the deck. \n"
		+ " If you get a Do Not Disturb card, you get to flip over \n"
		+ " 1 face up card from the other player. \n"
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //pop up message for raccoon card
    public void raccoonInfo ()
    {
	JOptionPane.showMessageDialog (null, "* * *R A C C O O N   C A R D *  * * \n \n"
		+ " There are 3 raccoon cards in the deck.\n"
		+ " If you get a raccoon card, you get to search the discard \n"
		+ " pile for a number card of your choice. \n"
		+ " Click the 'general' tab at the top of the screen, \n"
		+ " and then click on 'discard pile'. From there you will make your choice. \n"
		+ " If the pile is empty, or if the card you want is not in the deck,\n"
		+ " a message pops up.\n"
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //pop up message for elephant card
    public void elephantInfo ()
    {
	JOptionPane.showMessageDialog (null, "* * *E L E P H A N T   C A R D *  * * \n \n"
		+ " There are 3 elephant cards in the deck.\n"
		+ " Haven't you heard? No elephant allowed! \n"
		+ " If you get this card, you must discard it. \n"
		+ " If you don't discard it, an error message pops up. \n"
		+ " After you discard it, your turn is over. \n"
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //pop up message for giraffe card
    public void giraffeInfo ()
    {
	JOptionPane.showMessageDialog (null, "* * *G I R A F F E   C A R D *  * * \n \n"
		+ " There are 3 giraffe cards in the deck.\n"
		+ " Haven't you heard? No giraffe's allowed! \n"
		+ " If you get this card, you must discard it. \n"
		+ " If you don't discard it, an error message pops up. \n"
		+ " After you discard it, your turn is over. \n"
		+ "", "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


    //resets text on answer buttons
    public void resetButtonText ()
    {
	a1.setText ("   Answer 1:   ");
	a2.setText ("   Answer 2:   ");
	a3.setText ("   Answer 3:   ");
	a4.setText ("   Answer 4:   ");
    }


    //if user clicks buttons 1,2,4
    public void wrongAnswer ()
    {
	popUp ("math", "Wrong!");
	turnlabel.setText ("                 Player: " + turn + "  ");
	cdLayout.show (p_card, "3");
	popUp ("math", "Player " + turn + ". Turn over!");
	discard (temp);
	resetButtonText ();
    }


    //action performed
    public void actionPerformed (ActionEvent e)
    {
	//save and open
	if (e.getActionCommand ().equals ("save"))
	    save ("file.txt");
	else if (e.getActionCommand ().equals ("open"))
	    open ("file.txt");
	//use this do display pictures on instructions screen
	else if (e.getActionCommand ().equals ("updatePic"))
	{
	    instruct++;
	    if (instruct == 2)
		next.setIcon (createImageIcon ("images/instruct2.png"));
	    if (instruct == 3)
		next.setIcon (createImageIcon ("images/instruct3.png"));
	    else if (instruct == 4)
	    {
		cdLayout.show (p_card, "3");
		next.setIcon (createImageIcon ("images/instruct1.png"));
		instruct = 1;
	    }
	}
	//info for various cards
	else if (e.getActionCommand ().equals ("numInfo"))
	    cardsInfoNum ();
	else if (e.getActionCommand ().equals ("skip"))
	    skipInfo ();
	else if (e.getActionCommand ().equals ("mSkip"))
	    mathskipInfo ();
	else if (e.getActionCommand ().equals ("wild"))
	    wildInfo ();
	else if (e.getActionCommand ().equals ("disturb"))
	    disturbInfo ();
	else if (e.getActionCommand ().equals ("raccoon"))
	    disturbInfo ();
	else if (e.getActionCommand ().equals ("elephant"))
	    elephantInfo ();
	else if (e.getActionCommand ().equals ("giraffe"))
	    giraffeInfo ();
	//user resets game
	else if (e.getActionCommand ().equals ("reset"))
	    resetGame ();
	//user quits playing
	else if (e.getActionCommand ().equals ("quit"))
	    System.exit (0);
	//user wants to do something that involes the discard pile
	else if (e.getActionCommand ().equals ("discard"))
	    discardCard ();
	//user clicks on the deck picture
	else if (e.getActionCommand ().equals ("newCard"))
	{
	    click++;
	    if (click == 1)
	    {
		showStatus ("click the new card button");
		click--;
	    }
	    else if (click == 3)
	    {
		showStatus ("Pick a position for the card to go!");
		click--;
	    }
	}
	//user clicks next card button
	else if (e.getActionCommand ().equals ("pop"))
	{
	    click++;
	    if (first == 'n')
		shuffle ();
	    else
		newCard ();
	}
	//user wants to serach the pile
	else if (e.getActionCommand ().equals ("searchDiscard"))
	{
	    searchD++;
	    //checks if they have the right card
	    if (selectedValue != 7)
		showStatus ("invalid move");
	    else if (num1 == 0)
		popUp ("raccoon", "Empty");
	    else if (searchD > 1)
		findCard ();
	    else
		searchDiscard ();
	}
	//switches between screens
	else if (e.getActionCommand ().equals ("s2"))
	    cdLayout.show (p_card, "2");
	else if (e.getActionCommand ().equals ("s3"))
	    cdLayout.show (p_card, "3");
	//user checks the number of cards left
	else if (e.getActionCommand ().equals ("cardsLeft"))
	    popUp ("back", deck.size () + "");
	//user clicks on math answer buttons
	else if (e.getActionCommand ().equals ("a1"))
	    wrongAnswer ();
	else if (e.getActionCommand ().equals ("a2"))
	    wrongAnswer ();
	else if (e.getActionCommand ().equals ("a3"))
	{
	    correct = true;
	    popUp ("math", "Correct! Yay!");
	    turnlabel.setText ("                 Player: " + turn + "  ");
	    cdLayout.show (p_card, "3");
	    skipCard ();
	    correct = false;
	    resetButtonText ();

	}
	else if (e.getActionCommand ().equals ("a4"))
	    wrongAnswer ();
	//all other clicks - 6 for each players button row
	else
	{
	    click++;
	    if (click == 3)
	    {
		pos = Integer.parseInt (e.getActionCommand ());
		click3 ();
	    }
	    else
	    {
		if (click == 2)
		    showStatus ("Click on the card you want first!");
		else if (click == 1)
		    showStatus ("Click on the deck to reveal a card first!");
		click--;
	    }
	}
    }


    //generates pictuers
    protected static ImageIcon createImageIcon (String path)
    {
	java.net.URL imgURL = NasirImanTooManyMonkeys.class.getResource (path);
	if (imgURL != null)
	    return new ImageIcon (imgURL);
	else
	    return null;
    }
}


