class Graph {
  private var vertices: ArrayList<Vertex>
  private var edges: ArrayList<Edge>

  constructor(newVertices: IntArray, vararg newEdges: IntArray) {
    edges = ArrayList()
    for (edgeList in newEdges)
      edges.add(Edge(Vertex(edgeList[0]), Vertex(edgeList[1])))

    vertices = ArrayList()

    for (vertexList in newVertices)
      vertices.add(Vertex(vertexList))
  }

  constructor(newVertices: Array<Vertex>, newEdges: Array<Edge>) {
    edges = ArrayList()
    for (edgeList in newEdges)
      edges.add(edgeList)

    vertices = ArrayList()

    for (vertexList in newVertices)
      vertices.add(vertexList)
  }


  private fun getVertex(i: Int): Vertex {
    return vertices[i]
  }

  fun getEdges(): ArrayList<Edge> {
    val edges = ArrayList<Edge>()
    for (edge in this.edges)
      edges.add(edge)
    return edges
  }

  fun getVertices(): ArrayList<Vertex> {
    val vertices = ArrayList<Vertex>()
    for (vertex in this.vertices)
      vertices.add(vertex)
    return vertices
  }


  fun numVertices(): Int {
    return vertices.size
  }

  override fun toString(): String {
    return "Vertices: $vertices\nArestas : $edges"
  }
}
