fun main(args: Array<String>) {

  val vertices = arrayOf(
    Vertex(1), Vertex(2), Vertex(3),
    Vertex(4), Vertex(5), Vertex(6),
    Vertex(7)
  )

  val graphEdges = arrayOf(
    Edge(1, 2), Edge(2, 3), Edge(3, 4),
    Edge(3, 5), Edge(4, 5), Edge(4, 6),
    Edge(4, 7), Edge(5, 6)
  )
  var testGraph = Graph(vertices, graphEdges)


  println("Numero de Vertices: " + testGraph.numVertices())
  println(testGraph)

  var vertexCover = VertexCover(testGraph, 1)
  vertexCover.bruteForce()
  vertexCover.optimizedBruteForce()
  vertexCover.firstSolution()
  vertexCover.greedyCover()
  vertexCover.randomApproxCover()

  println()



  val vertices2 = arrayOf(
    Vertex(1), Vertex(2), Vertex(3),
    Vertex(4), Vertex(5), Vertex(6),
    Vertex(7), Vertex(8), Vertex(9)
  )

  val graphEdges2 = arrayOf(
    Edge(1, 2), Edge(2, 3), Edge(3, 4),
    Edge(3, 5), Edge(4, 5), Edge(4, 6),
    Edge(4, 7), Edge(5, 6), Edge(4, 8),
    Edge(5, 9)
  )
  testGraph = Graph(vertices2, graphEdges2)

  println("Numero de Vertices: " + testGraph.numVertices())
  println(testGraph)

  vertexCover = VertexCover(testGraph, 7)
  vertexCover.bruteForce()
  vertexCover.optimizedBruteForce()
  vertexCover.firstSolution()
  vertexCover.greedyCover()
  vertexCover.randomApproxCover()



}