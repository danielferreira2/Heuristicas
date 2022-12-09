class Vertex {
  private var value = 0

  constructor(vertex: Int) {
    value = vertex
  }

  constructor(vararg vertices: Int) {
    for (vertex in vertices) {
      Vertex(vertex)
    }
  }

  override fun toString(): String {
    return value.toString()
  }

  fun getValue(): Int {
    return value
  }


}
