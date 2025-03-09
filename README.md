## What is ForceDirectedGraph
This project implements the Frucherman Reingold force-directed graph algorithm. This algorithm is designed to orient an arbitrary graph in a visually pleasing and natural way. This is accomplished by treating the graph's edges and nodes as physical objects and applying the following forces.
1. An attractive "spring" force between connected nodes.
1. A repulsive "column" force between all nodes.
1. An attractive "gravitational" force pulling all nodes to the center of the graph.

By implementing these forces and treating the system like any other physics simulation, the graph will orient itself into a stable equilibrium position, with nodes well spaced apart, and connected nodes pulled closer together.

<img src ="https://github.com/user-attachments/assets/717a5dff-ee44-4c48-955e-7272b3bf1d31" width="50%" height="50%" />

## My simulation
My implementation is simply a demonstration of the algorithm. The app generates a random graph generated as follows:
1. The user specifies the number of desired nodes and a specific p-value between 0 and 1
2. For each pair of nodes, generate a random float between 0 and 1
3. If p is less than our random number, assign an edge between those nodes

Initial positions are generated randomly within the grid. After generating a random graph, the Frucherman-Reingold algorithm is applied to it. 

The user has three variables they can control, the number of nodes, k-value, and p-value.

## Running the simulation
One can run Driver.java from your IDE of choice or execute the program by running the ForceDirectedGraph.jar.



