/*
 * Name: Vertex.java
 * Author: Brendan Martin
 * Date: 4/30/2019
 */

import java.util.ArrayList;
import java.util.Collection;
import java.awt.Graphics;
import java.awt.Color;

// Direction enum type
enum Direction { NORTH, SOUTH, EAST, WEST };

// Vertex class
public class Vertex extends Agent implements Comparable<Vertex> {
	// Fields
	private Vertex[] adjRooms;
	private int numNeighbors;
	private double cost;
	private boolean marked;
	
	// Constructor
	public Vertex( int row, int col ) {
		super( row, col );
		this.adjRooms = new Vertex[4];
		this.cost = 0;
		this.marked = false;
		this.numNeighbors = 0;
	}
	
	// Return the opposite cardinal direction for each Direction value
	public Direction opposite( Direction d ) {
		if ( d == Direction.NORTH ) { return Direction.SOUTH; }
		if ( d == Direction.SOUTH ) { return Direction.NORTH; }
		if ( d == Direction.EAST ) { return Direction.WEST; }
		if ( d == Direction.WEST ) { return Direction.EAST; }
		return null;
	}
	
	// Connect this vertex with a new vertex (directed edge)
	public void connect(Vertex other, Direction dir) {
		this.adjRooms[ dir.ordinal() ] = other;
		if ( other != null ) {
			this.numNeighbors++;
		}
	}
	
	// Return the neighboring vertex along this cardinal direction.
	// If there is no neighbor here, return null
	public Vertex getNeighbor(Direction dir) {
		return this.adjRooms[ dir.ordinal() ];
	}
	
	// Returns an ArrayList of all this vertex's neighbors
	public ArrayList<Vertex> getNeighbors() {
		ArrayList<Vertex> neighborList = new ArrayList<Vertex>(4);
		for ( Vertex v: this.adjRooms ) {
			if ( v != null ) {
				neighborList.add( v );
			}
		}
		return neighborList;
	}
	
	// Change the value of marked to true or false
	public void setMarked( boolean status ) {
		this.marked = status;
	}
	
	// Return the current value of marked
	public boolean getMarked() {
		return this.marked;
	}
	
	// Set the cost field to a number (stored as a double because one possible value is infinity)
	public void setCost( double c ) {
		this.cost = c;
	}
	
	// Return the cost of the vertex
	public double getCost() {
		return this.cost;
	}
	
	// Draw the room
	public void draw( Graphics g, int scale ) {
		if (!this.visible) { return; }
		
        int xpos = (int)this.getCol()*scale;
        int ypos = (int)this.getRow()*scale;
        int border = 2;
        int half = scale / 2;
        int eighth = scale / 8;
        int sixteenth = scale / 16;
        
        // draw rectangle for the walls of the room
        if (this.cost <= 2) {
            // wumpus is nearby
            g.setColor(Color.red);
        } else {
            // wumpus is not nearby
            g.setColor(Color.black);
        }
        
        g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
        
        // draw doorways as boxes
        g.setColor(Color.black);
        if (this.adjRooms[Direction.NORTH.ordinal()] != null) {
            g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
        }
        if (this.adjRooms[Direction.SOUTH.ordinal()] != null) {
            g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
                       eighth, eighth + sixteenth);
        }
        if (this.adjRooms[Direction.WEST.ordinal()] != null) {
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        }
        if (this.adjRooms[Direction.EAST.ordinal()] != null) {
            g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
                       eighth + sixteenth, eighth);
        }
	}
	
	// Compares the values of the cost fields (smaller costs are better)
	// Returns positive int if this.cost > v.cost
	// Necessary for the Comparable interface
	public int compareTo( Vertex v ) {
		return (int)( this.cost - v.getCost() );
	}
	
	// Return string containing the number of neighbors, cost, and marked status
// 	public String toString() {
// 		String str = "Number of Neighbors: " + this.numNeighbors + "\n";
// 		str += "Cost: " + this.cost + "\n";
// 		str += "Marked: " + this.marked;
// 		return str;
// 	}
	
	// Testing method
	public static void main( String args[] ) {
		Vertex v = new Vertex(0,0);
		
		System.out.println( v );
		System.out.println("");
		v.connect( new Vertex(1,3), Direction.NORTH );
		v.connect( new Vertex(2,7), Direction.SOUTH );
		v.connect( new Vertex(1,2), Direction.WEST );
		v.connect( new Vertex(4,5), Direction.EAST );
		
		System.out.println( v.opposite( Direction.NORTH ) );
		System.out.println( v.opposite( Direction.SOUTH ) );
		System.out.println( v.opposite( Direction.EAST ) );
		System.out.println( v.opposite( Direction.WEST ) );
		
		System.out.println( v );
		System.out.println("");
		System.out.println( v.getMarked() );
		System.out.println( v.getCost() );
		System.out.println("");
		
		v.setMarked( true );
		v.setCost( 25 );
		System.out.println( v );
		System.out.println("");
		System.out.println( v.getMarked() );
		System.out.println( v.getCost() );
		
		
		v.getNeighbor( Direction.SOUTH ).setCost( 30 );
		v.getNeighbor( Direction.WEST ).setMarked( true );
		System.out.println( v.getNeighbors() );
		System.out.println("");
		System.out.println( v.getNeighbor( Direction.SOUTH ).compareTo( v ) );
		
	}
	
}