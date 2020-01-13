/*
 * Name: Wumpus.java
 * Author: Brendan Martin
 * Date: 5/3/2019
 */

import java.awt.Graphics;
import java.awt.Color;

public class Wumpus extends Agent {
	// Fields
	private Vertex location;
	
	// Constructor
	public Wumpus( Vertex loc ) {
		super( loc.getRow(), loc.getCol() );
		this.location = loc;
	}
	
	// Return the Vertex this object is located on
	public Vertex getLocation() {
		return this.location;
	}
	
	// Change the vertex the wumpus is located on
	public void setLocation( Vertex loc ) {
		this.location = loc;
		this.setRow( this.location.getRow() );
		this.setCol( this.location.getCol() );
	}
	
	// The game is over, so make the wumpus visible
	public void endGame() {
		this.setVisible( true );
	}
	
	// Draw the Wumpus
	public void draw(Graphics g, int scale) {
		if (!this.visible) { return; }
		
		int xpos = (int)this.getCol()*scale;
        int ypos = (int)this.getRow()*scale;
        int border = 2;
        
        g.setColor(Color.red);
		g.fillRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
	}
}