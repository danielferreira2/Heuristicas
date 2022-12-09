class Edge {
  private var vertex1: Vertex? = null
  private var vertex2: Vertex? = null


  constructor(vertex1: Vertex?, vertex2: Vertex?) {
    this.vertex1 = vertex1
    this.vertex2 = vertex2
  }

  constructor(vertex1: Int, vertex2: Int) {
    this.vertex1 = Vertex(vertex1)
    this.vertex2 = Vertex(vertex2)
  }

  constructor(edge: Array<Vertex?>) {
    vertex1 = edge[0]
    vertex2 = edge[1]
  }

  constructor(edge: IntArray) {
    vertex1 = Vertex(edge[0])
    vertex2 = Vertex(edge[1])
  }

  constructor(vararg edges: Array<Vertex?>?) {
    for (edge in edges) {
      Edge(edge)
    }
  }

  constructor(vararg edges: IntArray) {
    for (edge in edges) {
      Edge(edge)
    }
  }

  fun getVertex1(): Vertex? {
    return vertex1
  }

  fun getVertex2(): Vertex? {
    return vertex2
  }

  fun hasVertex(vertex: Vertex): Boolean {
    return vertex1!!.getValue() === vertex.getValue()
            || vertex2!!.getValue() === vertex.getValue()
  }

  override fun toString(): String {
    return "($vertex1, $vertex2)"
  }
}
