/*
 * File: Agent.java
 * Author: Brendan Martin
 * Date: 5/3/2019
 */
 
import java.awt.Graphics;

public class Agent {
	//Fields
	protected int row;
	protected int col;
	protected boolean visible;

	//Constructor
	public Agent(int row, int col) {
		this.row = row;
		this.col = col;
		this.visible = false;
	}

	//Return the x position
	public int getRow() {
		return this.row;
	}

	//Return the y position
	public int getCol() {
		return this.col;
	}

	//Change the x position
	public void setRow( int row ) {
		this.row = row;
	}

	//Change the y position
	public void setCol( int col ) {
		this.col = col;
	}
	
	// Change the visibility status of this agent
	public void setVisible( boolean bool ) {
		this.visible = bool;
	}
	
	// Return the visibility status of this agent
	public boolean getVisible() {
		return this.visible;
	}

	//Return the the position as a string "(x, y)"
	public String toString() {
		return "(" + col + ", " + row + ")";
	}
	
	//Placeholder for the draw method
	public void draw(Graphics g, int scale) {
		return;
	}

	//Testing method
	public static void main( String[] args ) {
		Agent age = new Agent(3, 5);
		System.out.println( age.getCol() );
		System.out.println( age.getRow() );
		System.out.println( age );
		age.setRow( 4 );
		age.setCol( -1);
		System.out.println( age );
	}

}