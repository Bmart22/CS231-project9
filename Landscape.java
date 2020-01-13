/*
 * File: Landscape.java
 * Author: Brendan Martin
 * Date: 5/3/2019
 */
 
import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Random;
 
public class Landscape {
	//Fields
	private int width;
	private int height;
	private LinkedList<Agent> foreAgents;
	private LinkedList<Agent> backAgents;
	
	//Constructor
	public Landscape(int w, int h) {
		this.width = w;
		this.height = h;
		this.foreAgents = new LinkedList<Agent>();
		this.backAgents = new LinkedList<Agent>();
	}
	
	//Return the height
	public int getHeight() {
		return this.height;
	}
	
	//Return the width
	public int getWidth() {
		return this.width;
	}
	
	//Add an agent to the beginning of the background agents list
	public void addBackgroundAgent( Agent a ) {
		this.backAgents.addLast( a );
	}
	
	// Return a random vertex from the list of background agents
	// Range is 1 - end of list, because the hunter appears on vertex 0
	public Agent getRandomVertex() {
		Random rand = new Random();
		return this.backAgents.get( 1 + rand.nextInt( this.backAgents.size() - 1 ) );
	}
	
	// Return the bottom-right vertex from the list of background agents
	public Agent getStartingVertex() {
		return this.backAgents.get( 0 );
	}
	
	//Add an agent to the beginning of the foreground agents list
	public void addForegroundAgent( Agent a ) {
		this.foreAgents.addFirst( a );
	}
	
	// Erase pointers to the stored agents
	public void clear() {
		this.backAgents.clear();
		this.foreAgents.clear();
	}
	
	//Return the number of agents on the landscape as a string
	public String toString() {
		return "back: " + this.backAgents.size() + "\nfore: " + this.foreAgents.size();
	}
	
	//Call the draw method of every agent
	public void draw(Graphics g, int scale) {
		for (Agent age: this.backAgents) {
			age.draw(g, scale);
		}
		for (Agent age: this.foreAgents) {
			age.draw(g, scale);
		}
	}
	
	//Testing Method
	public static void main( String[] args ) {
		// Landscape scape = new Landscape( 100, 200 );
// 		SocialAgent age = new SocialAgent(5, 5);
// 		scape.addAgent( age );
// 		scape.addAgent( new SocialAgent( 10, 10));
// 		scape.addAgent( new SocialAgent( 15, 15));
// 		scape.addAgent( new SocialAgent( 20, 20));
// 		
// 		age.updateState( scape );
// 		System.out.println( scape.getNeighbors( age.getX(), age.getY(), 25 ).size() );
		
		
		// Landscape scape = new Landscape( 100, 200 );
// 		System.out.println( scape.getHeight() );
// 		System.out.println( scape.getWidth() );
// 		System.out.println( scape );
// 		scape.addAgent( new SocialAgent(4, 7) );
// 		scape.addAgent( new SocialAgent( 50, 40 ));
// 		scape.addAgent( new SocialAgent( 60, 40));
// 		System.out.println( scape );
// 		ArrayList<Agent> neigh = scape.getNeighbors( 25, 25, 30 );
// 		for( Agent a: neigh ) {
// 			System.out.println( a );
// 		}
	}
	
	
	
	
}