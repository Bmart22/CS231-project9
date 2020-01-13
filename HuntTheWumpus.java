/*
 * Name: HuntTheWumpus.java
 * Author: Brendan Martin
 * Date: 5/3/2019
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import java.util.*;

public class HuntTheWumpus {
	// Fields associated with structure of window, graph
    private JFrame win;
    private LandscapePanel canvas;    
    private Landscape scape; 
    private int scale;
    private int numRows;
    private int numCols;
    
    // Agent objects
    private Graph graph;
    private Hunter hunter;
    private Wumpus wumpus;
    
    // Record the wins
    private int score;
    private int totalGames;

	// Display Fields
    JLabel arrowDisplay; // Label field 3, displays the armed/unarmed status of the arrow
    JLabel successField; // Displays whether the hunter wins or loses a given game
    JLabel runningScore; // Displays the success rate of the player

    // controls whether the game is playing or exiting
    private enum PlayState { PLAY, PAUSE, STOP }
    private PlayState state;
    
    private enum ArrowState { ARMED, UNARMED }
    private ArrowState arrow;

    /**
     * Creates the main window
     * @param scape the Landscape that will hold the objects we display
     * @param scale the size of each grid element
     **/		 
    public HuntTheWumpus() {
        // The game should begin in the play state.
        // The arrow should begin unarmed
        // The score should be zero
        this.state = PlayState.PLAY; 
        this.arrow = ArrowState.UNARMED;
        this.score = 0;
        totalGames = 0;
        
        // Create the elements of the Landscape and the game.
        this.scale = 64; // determines the size of the grid
        this.numRows = 10;
        this.numCols = 9;
        this.scape = new Landscape(this.scale*this.numCols,this.scale*this.numRows);
        // This is test code that adds a few vertices.
        // You will need to update this to make a Graph first, then
        // add the vertices to the Landscape.
        
        // Board Setup: make graph and add randomized vertices
        this.graph = new Graph();
        this.setupGraph();
        
        // Agent Setup
        // Make hunter and wumpus objects
		this.wumpus = new Wumpus( (Vertex)this.scape.getRandomVertex() );
		this.hunter = new Hunter( (Vertex)this.scape.getRandomVertex() );
		// Place agents on the graph in a proper manner
        this.setupAgents();
        
        // Make the main window
        this.win = new JFrame("Hunt The Wumpus");
        win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);

        // make the main drawing canvas (this is the usual
        // LandscapePanel we have been using). and put it in the window
        this.canvas = new LandscapePanel( this.scape.getWidth(),
					                                        this.scape.getHeight() );
        this.win.add( this.canvas, BorderLayout.CENTER );
        this.win.pack();

        // make the labels and a button and put them into the frame
        this.arrowDisplay = new JLabel("Unarmed");
        this.successField = new JLabel(" ");
        this.runningScore = new JLabel("Score: 0/0");
        
        // Make buttons and displays
        JButton quit = new JButton("Quit");
        JButton replay = new JButton("Replay");
        JPanel panelBottom = new JPanel( new FlowLayout(FlowLayout.RIGHT));
        JPanel panelTop = new JPanel( new FlowLayout(FlowLayout.CENTER));
        
        // Add widgets to the panels
        panelBottom.add( this.arrowDisplay );
        panelBottom.add( replay );
        panelBottom.add( quit );
        panelTop.add( this.successField );
        panelTop.add( this.runningScore );
        
        // Add panels to the window, format size
        this.win.add( panelBottom, BorderLayout.SOUTH);
        this.win.add( panelTop, BorderLayout.NORTH);
        this.win.pack();

        // Add key and button controls.
        // We are binding the key control object to the entire window.
        // That means that if a key is pressed while the window is
        // in focus, then control.keyTyped will be executed.
        // Because we are binding quit (the button) to control, control.actionPerformed will
        // be called if the quit button is pressed. If you make more than one button,
        // then the same function will be called. Use an if-statement in the function
        // to determine which button was pressed (check out Control.actionPerformed and
        // this advice should make sense)
        Control control = new Control();
        this.win.addKeyListener(control);
        this.win.setFocusable(true);
        this.win.requestFocus();
        quit.addActionListener( control );
        replay.addActionListener( control );


        // The last thing to do is make it all visible.
        this.win.setVisible( true );

    } // end HuntTheWumpus constructor
    
    // Generate a random graph pattern
    private void setupGraph() {
    	this.arrow = ArrowState.UNARMED;
    	
    	// Board Setup
    	// Set up graph, add a vertex at coordinates (0,0)
        Vertex v = new Vertex(0,0);
        this.graph.clear();
        this.scape.clear();
        this.graph.addEdge( v, Direction.SOUTH, null);
        this.scape.addBackgroundAgent( v );
        
        // Use an array to store the latest row of added vertices
        // This array is used to make edges with new vertices
        Vertex[] tempVerts = new Vertex[this.numCols];
        tempVerts[0] = v;
        double density = 0.7;
        Random rand = new Random();
        
        // Add all vertices to the graph and landscape
        // Loop over the rows
        for ( int i = 0; i < this.numRows; i++ ) {
        	// Loop over the columns
        	for ( int j = 0; j < this.numCols; j++ ) {
        		// A percentage of caves are not created
        		if ( rand.nextDouble() <= density ) {
					// If not at coordinates (0,0)
					if ( !(i == 0 && j == 0) ) {
						v = new Vertex(i,j);
						// If not in first row, make edge between new vertex and vertex directly north
						if ( i != 0 ) {
							this.graph.addEdge( v, Direction.NORTH, tempVerts[j] );
						}
						// If not in first column, make edge between new vertex and vertex directly west
						if ( j != 0 ) {
							this.graph.addEdge( v, Direction.WEST, tempVerts[j-1] );
						}
					
						this.scape.addBackgroundAgent( v ); // Add vertex to landscape
						tempVerts[j] = v; // Update temporary array of vertices
						//v.setVisible( true );
					}
        		} else {
        			tempVerts[j] = null; // Indicate that no vertex was added here
        		}
        	}
        } // End Board Setup
        
    }
    
    // Assign the wumpus and hunter locations on the board
    private void setupAgents() {
    	// Agent Setup
        // Generate Wumpus location
		this.wumpus.setLocation( (Vertex)this.scape.getRandomVertex() );
		this.graph.shortestPath( this.wumpus.getLocation() ); // Calculate distances from wumpus
	
		// Generate Hunter location
		this.hunter.setLocation( (Vertex)this.scape.getRandomVertex() );
		
		// Loop until hunter and wumpus are on connected ground, hunter is not on top of wumpus
        while ( this.hunter.getLocation().getCost() == Double.POSITIVE_INFINITY || 
        								this.hunter.getLocation().getCost() < 3 ) {
			// Generate Wumpus location
			this.wumpus.setLocation( (Vertex)this.scape.getRandomVertex() );
			this.graph.shortestPath( this.wumpus.getLocation() ); // Calculate distances from wumpus
		
			// Generate Hunter location
			this.hunter.setLocation( (Vertex)this.scape.getRandomVertex() );
        }
        
        // Add agents to landscape, make hunter visible, make wumpus invisible
        this.scape.addForegroundAgent( this.wumpus );
        this.scape.addForegroundAgent( this.hunter );
        this.hunter.getLocation().setVisible( true );
        this.wumpus.setVisible( false );
    }
    

    private class LandscapePanel extends JPanel {
		
        /**
         * Creates the drawing canvas
         * @param height the height of the panel in pixels
         * @param width the width of the panel in pixels
         **/
        public LandscapePanel(int width, int height) {
            super();
            this.setPreferredSize( new Dimension( width, height ) );
            this.setBackground(Color.white);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g		the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            scape.draw( g, scale );
        }
    } // end class LandscapePanel

    private class Control extends KeyAdapter implements ActionListener {

        public void keyTyped(KeyEvent e) {
        	// Print key pressed, for purposes of demonstration on screen capture video
            System.out.println( "Key Pressed: " + e.getKeyChar() );
            
            // If we are playing the game
            if ( state == PlayState.PLAY ) {
            	// Use arrays to store wasd keys and the corresponding directions
            	String[] keys = new String[]{"w","a","s","d"};
            	Direction[] dirs = new Direction[]{Direction.NORTH,Direction.WEST,Direction.SOUTH,Direction.EAST};
            	
            	// If we are in movement mode
            	if (arrow == ArrowState.UNARMED) {
				// Check each key w,a,s,d.  Move hunter to the new cave
					for ( int i = 0; i < 4; i++ ) {
						if ( ("" + e.getKeyChar()).equalsIgnoreCase(keys[i]) ) {
							hunter.setLocation( hunter.getLocation().getNeighbor( dirs[i] ) );
						}
					}
					// Make the new cave visible
					hunter.getLocation().setVisible( true );
				
					// If we are on top of the wumpus, you die
					if (wumpus.getLocation() == hunter.getLocation()) {
						wumpus.setVisible( true );
						totalGames++;
						successField.setText( "You Lose!" );
						state = PlayState.PAUSE;
					}
				} else { // If we are in combat mode
					// Check each key w,a,s,d.  If the arrow hits the wumpus, enter PAUSE mode
					for ( int i = 0; i < 4; i++ ) {
						if( ("" + e.getKeyChar()).equalsIgnoreCase(keys[i]) ) {
							// If you hit the wumpus, you kill it
							if (wumpus.getLocation() == hunter.getLocation().getNeighbor( dirs[i] )) {
								wumpus.setVisible( true );
								score++;
								totalGames++;
								successField.setText( "You Win!" );
								state = PlayState.PAUSE;
							} else { // Otherwise, the wumpus eats you
								wumpus.setVisible( true );
								wumpus.setLocation( hunter.getLocation() );
								totalGames++;
								successField.setText( "You Lose!" );
								state = PlayState.PAUSE;
							}
						}
					}
				}
				runningScore.setText( "Score: "+score+"/"+totalGames );
				// If spacebar pressed, change the arrow state
				if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ) {
					if (arrow == ArrowState.UNARMED) {
						arrowDisplay.setText( "Armed" );
						arrow = ArrowState.ARMED;
					} else {
						arrowDisplay.setText( "Unarmed" );
						arrow = ArrowState.UNARMED;
					}
				}
            } // end PLAY mode
            
            
        } // End keyTyped method

        public void actionPerformed(ActionEvent event) {
            // If the Quit button was pressed
            if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
		        System.out.println("Quit button clicked");
                state = PlayState.STOP;
            }
            // If the replay button is pressed (Extension)
            if( event.getActionCommand().equalsIgnoreCase("Replay") ) {
		        System.out.println("Reseting game");
		        setupGraph();
		        setupAgents();
		        arrowDisplay.setText( "Unarmed" );
    			successField.setText( " " );
                state = PlayState.PLAY;
                win.requestFocus();
            }
            
        }
    } // end class Control

    public void repaint() {
    	this.win.repaint();
    }

    public void dispose() {
	    this.win.dispose();
    }


    public static void main(String[] argv) throws InterruptedException {
        HuntTheWumpus w = new HuntTheWumpus();
        
        while (w.state != PlayState.STOP) {
            w.repaint();
            Thread.sleep(33);
        }
    	
        System.out.println("Disposing window");
        w.dispose();
    }
}