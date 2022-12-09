fun main() {

  val vertices = arrayOf(
    Vertex(0),Vertex(1), Vertex(2), Vertex(3),
    Vertex(4), Vertex(5), Vertex(6),
    Vertex(7))

  val graphEdges = arrayOf(
    Edge(1,0)
    ,Edge(1,5)
    ,Edge(1,3)
    ,Edge(3,7)
    ,Edge(3,2)
    ,Edge(7,6)
    ,Edge(7,5)
    ,Edge(5,4)
    ,Edge(2,0)
    ,Edge(4,0)
    ,Edge(4,6)
  )

  var testGraph = Graph(vertices, graphEdges)

  println("Numero de Vertices: " + testGraph.numVertices())
  println(testGraph)

  var vertexCover = VertexCover(testGraph, 4)
  vertexCover.bruteForce()
  vertexCover.optimizedBruteForce()
  vertexCover.firstSolution()
  vertexCover.greedyCover()
  vertexCover.randomApproxCover()
  vertexCover.tabuSearch(10)



}