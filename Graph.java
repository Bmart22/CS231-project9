/*
 * Name: Graph.java
 * Author: Brendan Martin
 * Date: 5/3/2019
 */

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph {
	// Fields
	private ArrayList<Vertex> vertices;
	
	// Constructor
	public Graph() {
		this.vertices = new ArrayList<Vertex>();
	}
	
	// Return the number of vertices in the graph
	public int vertexCount() {
		return this.vertices.size();
	}
	
	// Add two vertices to the graph (if necessary) and make an edge between them
	public void addEdge(Vertex v1, Direction dir, Vertex v2) {
		if ( !(this.vertices.contains( v1 )) && (v1 != null) ) {
			this.vertices.add( v1 );
		}
		if ( !(this.vertices.contains( v2 )) && (v2 != null)) {
			this.vertices.add( v2 );
		}
		if (v1 != null) {
			v1.connect( v2, dir );
		}
		if (v2 != null) {
			v2.connect( v1, v2.opposite( dir ) );
		}
	}
	
	// Determine the shortest from vertex v0 to every other vertex in the graph
	public void shortestPath(Vertex v0) {
		
		// Set all marked values to false, all cost values to 0
		for ( Vertex v: this.vertices ) {
			v.setCost( Double.POSITIVE_INFINITY );
			v.setMarked( false );
		}
	
		// Their natural ordering, provided by Comparable interface, is used as the comparator
		// in priority queue
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>( this.vertices );
		
		v0.setCost( 0 );
		pq.add( v0 );
	
		// Dijkstra's Algorithm
		Vertex v;
		while ( pq.size() > 0 ) {
			v = pq.remove();
			//System.out.println( "Center: " + v );
			//System.out.println( "Center cost: " + v.getCost() );
			v.setMarked( true );
			for ( Vertex w: v.getNeighbors() ) {
				//System.out.println( "Neighbor: " + w );
				//System.out.println( "Cost: " + w.getCost() );
				if ( !w.getMarked() && (v.getCost() + 1 < w.getCost() ) ) {
					w.setCost( v.getCost() + 1 );
					pq.remove( w );
					pq.add( w );
				}
			}
		} // end while loop
				
	} // end shortestPath
	
	// Clear the ArrayList storing the graph
	public void clear() {
		this.vertices = new ArrayList<Vertex>();
	}
	
	// Testing method
	public static void main( String[] args ) {
		Vertex[] verts = new Vertex[6];
		for (int i = 0; i < 6; i++) {
			verts[i] = new Vertex( i, i);
		}
		Graph g = new Graph();
		g.addEdge( verts[0], Direction.EAST, verts[1] );
		g.addEdge( verts[0], Direction.SOUTH, verts[2] );
		g.addEdge( verts[1], Direction.SOUTH, verts[3] );
		g.addEdge( verts[2], Direction.EAST, verts[3] );
		g.addEdge( verts[3], Direction.EAST, verts[4] );
		g.addEdge( verts[1], Direction.EAST, verts[5] );
		g.addEdge( verts[5], Direction.SOUTH, verts[4] );
		
		System.out.println( g.vertexCount() );
		
		g.shortestPath( verts[3] );
		System.out.println( "0: " + verts[0].getCost() );
		System.out.println( "1: " + verts[1].getCost() );
		System.out.println( "2: " + verts[2].getCost() );
		System.out.println( "3: " + verts[3].getCost() );
		System.out.println( "4: " + verts[4].getCost() );
		System.out.println( "5: " + verts[5].getCost() );
	}
	
	
}