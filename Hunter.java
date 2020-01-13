/*
 * Name: Hunter.java
 * Author: Brendan Martin
 * Date: 5/3/2019
 */

import java.awt.Graphics;
import java.awt.Color;

public class Hunter extends Agent {
	// Fields
	private Vertex location;
	
	// Constructor
	public Hunter( Vertex loc ) {
		super( loc.getRow(), loc.getCol() );
		this.location = loc;
		this.setVisible( true );
	}
	
	// Return the Vertex this object is located on
	public Vertex getLocation() {
		return this.location;
	}
	
	// Change the vertex the hunter is located on
	public void setLocation( Vertex loc ) {
		if (loc != null) {
			this.location = loc;
			this.setRow( this.location.getRow() );
			this.setCol( this.location.getCol() );
		}
	}
	
	// Draw the hunter as a blue rectangle
	public void draw(Graphics g, int scale) {
		int xpos = (int)this.getCol()*scale;
        int ypos = (int)this.getRow()*scale;
        int border = 5;
        
        g.setColor(Color.blue);
		g.fillRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
	}
	
}