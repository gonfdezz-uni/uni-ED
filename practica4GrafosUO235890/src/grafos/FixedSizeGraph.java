package grafos;

import java.util.ArrayList;
import java.util.Arrays;

import grafos.exceptions.ElementNotPresentException;
import grafos.exceptions.FullStructureException;

public class FixedSizeGraph<T> implements Graph<T>{
	//Atributos
	private T[] nodes;
	private boolean [][] edges;
	public boolean[][] getEdges() {
		return edges;
	}
	public double[][] getWeights() {
		return weights;
	}
	private double [][] weights;
	//Número de nodos actuales del nodo (no confundir con capacidad)
	private int size;
	
	//Constructor
	public FixedSizeGraph(int capacity) {
		if(capacity <= 0) { 
			throw new IllegalArgumentException("La capacidad tiene que ser mayor que 0");
		}
		this.nodes = (T[]) new Object[capacity]; //este warning da igual, no usar supreswarnings
		this.edges = new boolean[capacity][capacity];
		this.weights = new double[capacity][capacity];
		this.size = 0;
	}
	
	//Aquí irá otro constructor con más parámetros
	public FixedSizeGraph(int size, T[] nodes, boolean[][] edges, double[][] weights) {
		this.size = size;
		this.nodes = nodes;
		this.edges = edges;
		this.weights = weights;
	}
	
	
	/**
	 * Retorna la posición en la que está element
	 * @param element
	 * @return posición de element en el grafo o -1 si no está
	 */
	public int find (T element) {
		for(int i = 0; i<size;i++) {
			if(nodes[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}
	@Override
	public boolean addNode(T element) {
		checkNull(element);
		if(size == nodes.length) {
			throw new FullStructureException(element);
		}
		if(find(element)!=-1) {
			return false;
		}
		for(int i=0;i<size;i++) {
			edges[i][size] =false;
			edges[size][i] = false;
		}
		
		
		//nodes[size++] = element; esto hace lo mismo (accede e incrementa)
		nodes[size] = element; 
		size++;
		return true;
	}
	/*
	 * Lógica vista en la teoría 
	 */
	@Override
	public boolean removeNode(T element) {
		//Comprobaciones
		checkNull(element);
		int posNodoAEliminar = find(element);
		if(posNodoAEliminar ==-1) {
			return false;
		}
		//Reduzco tamaño
		size--;
		//Último nodo a la posición del nodo a borrar
		nodes[posNodoAEliminar] = nodes[size];
		//Ajusto las posiciones de las tablas
		for(int i = 0;i<size;i++) {
			edges[posNodoAEliminar][i] = edges[size][i];
			edges[i][posNodoAEliminar] = edges[i][size];
			weights[posNodoAEliminar][i] = weights[size][i];
			weights[i][posNodoAEliminar] = weights[i][size];
			//Para el codo
			edges[posNodoAEliminar][posNodoAEliminar] = edges[size][size];
			weights[posNodoAEliminar][posNodoAEliminar] = weights[size][size];
		}
		return true;
	}
	
	@Override
	public boolean existsNode(T element) {
		checkNull(element);
		return find(element)!=-1;
		/*
		 * NOTA: Aquí es muy importante usar el find en vez de un bucle.
		 * No complicarse y hacer uso de los métodos que tengo para aprovechar el 
		 * código.
		 * Lo comentado aquí debajo no funciona
		 */
		
//		for(T node: nodes) {
//			if(node.equals(element)) {
//				return true;
//			}
//		}
//		return false;
	}
	@Override
	public boolean addEdge(T originElement, T destinationElement, double weight) {
		checkNull(originElement);
		checkNull(destinationElement);
		if(weight <=0) {
			throw new IllegalArgumentException("El peso tiene que ser positivo");
		}
		int originPos = getValidIndexOf(originElement);
		int destinationPos = getValidIndexOf(destinationElement);
		
		if(edges[originPos][destinationPos]) {
			return false;
		}
		
		edges[originPos][destinationPos] = true;
		weights[originPos][destinationPos] = weight;
		
		return true;
	}
	
	@Override
	public boolean removeEdge(T originElement, T destinationElement) {
		checkNull(originElement); checkNull(destinationElement);
		int originPos = getValidIndexOf(originElement);
		int destinationPos = getValidIndexOf(destinationElement);
		if(edges[originPos][destinationPos] == true) {
			edges[originPos][destinationPos] = false;
			return true;
		}
		return false;
	}
	@Override
	public boolean existsEdge(T originElement, T destinationElement) {
		//Usar el find para ahorrar iteraciones
		checkNull(originElement);
		checkNull(destinationElement);
		
		int originPos =find(originElement);
		int destinationPos =find(destinationElement);
		
		if(originPos==-1||destinationPos ==-1) {
			return false;
		}
		return this.edges[originPos][destinationPos];
	}
	
	private void checkNull(T element) {
		if(element==null ) {
			throw new NullPointerException();
		}
	}
	@Override
	public double getWeight(T originElement, T destinationElement) {
		int originPos = getValidIndexOf(originElement);
		int destinationPos = getValidIndexOf(destinationElement);
		
		if(edges[originPos][destinationPos]) {
			return weights[originPos][destinationPos];
		}
		return -1;
	}
	private int getValidIndexOf(T element) {
		checkNull(element);
		int elementPos = find(element);
		if(elementPos == -1) {
			throw new ElementNotPresentException(element);
		}
		return elementPos;
	}
	@Override
	public int getSize() {
		return this.size;
	}
	public int getCapacity() {
		return nodes.length;
	}
	public T[] getNodes() {
		return this.nodes;	}
	
	
	
	@Override
	public DijkstraDataClass dijkstra(T originElement) {
		int indexOfOriginElement = getValidIndexOf(originElement);
		//checkNull(originElement);
		DijkstraDataClass data = new DijkstraDataClass(size, edges, weights, indexOfOriginElement);
		int numVertices = nodes.length;
		boolean [] visitados = new boolean[numVertices];
		double[] dist = data.getDijkstraCostsD();
		//int[] prev = data.getDijkstraPathsP();
		
		//Procesar todos los vértices
		for(int i=0;i<numVertices -1;i++) {
			//Vértice con la menor distancia
			int u = getMinimumUnvisitedVertex(dist, visitados);
			if(u==-1) break;
			
			for (int v = 0; v < size; v++) {
	            if (getEdges()[u][v] && !visitados[v]) {
	                double weight = getWeights()[u][v];
	                if (dist[u] + weight < dist[v]) {
	                    data.updateDijkstraCostsD(v, dist[u] + weight);
	                    data.updateDijkstraPathsP(v, u);
	                }
	            }
	        }
			
		}
		return data;
	}
	
	private int getMinimumUnvisitedVertex(double[] dist, boolean[] visited) {
	    double min = Double.POSITIVE_INFINITY;
	    int minIdx = -1;
	    for (int i = 0; i < dist.length; i++) {
	        if (!visited[i] && dist[i] < min) {
	            min = dist[i];
	            minIdx = i;
	        }
	    }
	    return minIdx;
	}
	
	
	
	@Override
	public double minimumCostPathDijkstra(T originElement, T destinationElement) {
		checkNull(originElement);
		int indexOfDestinationElement = getValidIndexOf(destinationElement);
		DijkstraDataClass dijkstraGraph = dijkstra(originElement);
		
		return dijkstraGraph.getDijkstraCostsD()[indexOfDestinationElement];
	}
	
	
}
