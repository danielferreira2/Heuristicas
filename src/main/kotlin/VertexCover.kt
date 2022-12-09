import java.time.Duration
import java.time.Instant
import java.util.*

class VertexCover(private val graph: Graph,private var k: Int) {
  private var min = 0
  private var cover: ArrayList<Vertex?>? = null
  private var possibleCovers = ArrayList<ArrayList<Vertex>>()


  init {
    k = k
  }

  private fun removeEdges(vertex: Vertex?, edgeList: ArrayList<Edge>) {
    val removeEdge = ArrayList<Edge>()
    for (i in edgeList.indices) {
      if (edgeList[i].hasVertex(vertex!!))
        removeEdge.add(edgeList[i])
    }
    for (i in removeEdge.indices)
      edgeList.remove(removeEdge[i])
  }

  // Quantificar o peso de cada vertice em relação ao numero de arestas que ele possui
  // cria um hashmap com o vertice e o peso, sendo este o numero de arestas que ele possui
  // percorre a lista de vértices restantes na pesquisa
  // adiciona cada vértice ao hash com um valor inicial de 0
  // percorre a lista de arestas restantes na pesquisa
  // determina se uma aresta contém um vértice específico
  // se a aresta contém o vértice, a contagem associada a esse vértice é incrementada
  // determina se o vértice atual tem mais arestas associadas do que o máximo atual
  // caso afirmativo, define o novo valor máximo e
  // definir  maxVertex para o novo vértice que possui o máximo atual de arestas associadas
  // retorna o vértice com o maior número de arestas associadas


  private fun getMaxDegree(vertices: ArrayList<Vertex>, edges: ArrayList<Edge>): Vertex {
    val map: MutableMap<Vertex, Int> =
      HashMap()
    var max = 0
    var maxVertex = Vertex()
    for (vertex in vertices) {
      map[vertex] = 0
      for (edge in edges) {
        if (edge.hasVertex(vertex)) {
          map[vertex] =
            map[vertex]!! + 1
        }
      }
      if (map[vertex]!! > max) {
        max = map[vertex]!!
        maxVertex = vertex
      }
    }
    return maxVertex
  }

  // Verificar combinaçoes de vertices
  // determinar se a permutação atual contém k ou mais valores
  // criar uma lista temporário para manter a permutação atual
  // adicionar cada vértice na permutação atual a uma lista temporária
  // adicionar a permutação atual do vertice à lista de coberturas possíveis
  // retornar da iteração atual do método recursivo
  // se não, define o i-ésimo vértice na permutação atual, incrementa a contagem de vértices
  // definir o i-ésimo vértice como visitado
  // chama a si mesmo recursivamente com valores atualizados
  // definir o i-ésimo vértice como não visitado
  // decrementa a contagem de vértices

  private fun combinations(vertices: ArrayList<Vertex>,
    k: Int,
    current: ArrayList<Vertex>,
    currentTotal: Int,
    visit: BooleanArray
  ) {
    var currentTotal = currentTotal
    if (currentTotal >= k - 1) {
      val temp = ArrayList<Vertex>()
      for (i in current.indices)
        temp.add(current[i])
      possibleCovers.add(temp)
      return
    }

    for (i in vertices.indices) {
      if (!visit[i]) {
        current[++currentTotal] =
          vertices[i]
        visit[i] = true
        combinations(vertices, k, current, currentTotal, visit)
        visit[i] = false
        currentTotal--
      }
    }
  }

  // Abordagem de força bruta para encontrar uma cobertura de vértices de k ou menos vértices
  // cria uma lista para armazenar as coberturas de vértices possíveis
  // cria uma lista para usar com o método de combinações
  // percorre k valores
  // adiciona novos vértices que manterão cada permutação de valores de vértice
  // criar um array booleano para usar com o método de combinação
  // utilizar o método de combinações para definir todas as permutações possíveis de coberturas de vértices
  // obter todas as arestas do grafo
  // definir min para o valor inicial de k + 1
  // será usado para armazenar o índice da cobertura mínima do Vertex na lista cobertura de vértices possíveis
  // percorrer todas as possíveis permutações de cobertura do vertice
  // percorrer os k valores da cobertura potencial
  // remover todas as arestas associadas ao vértice atual da cobertura de vértice potencial
  // determinar se uma cobertura de vertices foi encontrada para o grafo
  // determinar se a nova cobertura de vertices é menor que o mínimo atual
  // caso afirmativo, define o novo mínimo para o tamanho da cobertura atual do vertice 3
  // definir o valor do índice para a cobertura atual do vertice
  // redefinir a lista de arestas para conter todas as arestas do grafo, prontas para a próxima cobertura potencial do vértice
  // determina se a menor cobertura de vértice encontrada atende ao requisito k

  fun bruteForce() {
    val startTime = Instant.now()
    possibleCovers = ArrayList()
    val v = ArrayList<Vertex>()
    for (i in 0 until k) {
      v.add(Vertex(i))
    }
    val visit = BooleanArray(graph.getVertices().size)
    for (i in graph.getVertices().indices) {
      visit[i] = false
    }
    combinations(
      graph.getVertices(),
      k,
      v,
      -1,
      visit
    )
    var edges = graph.getEdges()
    cover = ArrayList()
    var min = k + 1
    var coverIndex = 0
    for (i in possibleCovers.indices) {
      for (j in 0 until k) {
        removeEdges(
          possibleCovers[i][j],
          edges
        )
        if (edges.isEmpty()) {
          if (j + 1 < min) {
            min = j + 1
            coverIndex = i
          }
        }
      }
      edges = graph.getEdges()
    }
    if (min <= k) {
      for (i in 0 until min)
        cover!!.add(possibleCovers[coverIndex][i])
      println("------------------------------------------------------------------------")
      println("----------Força-Bruta---------------------------------------------------")
      println("Foi encontrada uma cobertura que satisfaz k = $k método força-bruta")
      println("Os vértices da cobertura minima são: " + cover.toString())
    } else
      println("Nenhuma cobertura encontrada que satisfaça = $k por esse método")
    val endTime = Instant.now()
    println(
      "Tempo de Execução: " + Duration.between(startTime, endTime).toMillis() + "ms"
    )
    println("------------------------------------------------------------------------")
    possibleCovers.clear()
  }

  // Abordagem de força bruta otimizada para encontrar uma cobertura de vértice de k ou menos vértices
  // criar uma lista de vertices para usar com o método de combinações
  // adiciona novos vértices que manterão cada permutação de valores de vértice
  // cria um array booleano para verificar se o vértise já foi adicionado a cobertura
  // percorre o número de vértices no gráfico vezes
  // define cada valor booleano no array para um valor inicial de false
  // definir min para k + 1
  // será usado para armazenar o índice da cobertura mínima do Vertex na lista cobertura de vértices possíveis
  // utilizar o método de combinações para definir todas as permutações possíveis de coberturas de vértices
  // obter todas as arestas do grafo
  // percorre todas as possíveis permutações de cobertura do Vertex
  // percorre os k valores da cobertura potencial
  // remover todas as arestas associadas ao vértice atual da cobertura de vértice potencial
  // determinar se uma cobertura de vertices foi encontrada para o grafo
  // determinar se a nova cobertura de vertices é menor que o mínimo atual
  // caso afirmativo, define o novo mínimo para o tamanho da cobertura atual do vertice para k-1
  // definir o valor do índice para a cobertura atual do vertice
  // redefinir a lista de arestas para conter todas as arestas do grafo, prontas para a próxima cobertura potencial do vértice
  // determinar se a menor cobertura de vértice encontrada atende ao requisito k

  fun optimizedBruteForce() {
    val startTime = Instant.now()
    val v = ArrayList<Vertex>()
    for (i in 0 until k) {
      v.add(Vertex(i))
    }
    val visit = BooleanArray(graph.getVertices().size)
    for (i in graph.getVertices().indices) {
      visit[i] = false
    }
    min = k + 1
    cover = ArrayList()
    this.optimizedBruteForce(
      graph.getVertices(),
      k,
      v,
      -1,
      visit
    )
    if (cover!!.isNotEmpty()) {
      println("----------Força-Bruta-Otimizada------------------------------------------")
      println("Foi encontrada uma cobertura que satisfaz k = $k método força-bruta otimizada")
      println("Os vértices da cobertura minima são:  " + cover.toString())
    }
    else println("Nenhuma cobertura encontrada que satisfaça = $k por esse método")
    val endTime = Instant.now()
    println(
      "Tempo de Execução: " + Duration.between(startTime, endTime).toMillis() + "ms"
    )
    println("------------------------------------------------------------------------")
  }

  private fun optimizedBruteForce(
    vertices: ArrayList<Vertex>,
    k: Int,
    current: ArrayList<Vertex>,
    currentTotal: Int,
    visit: BooleanArray
  ) {
    var currentTotal = currentTotal
    var edges = graph.getEdges()
    if (currentTotal < k - 1) {
      if (currentTotal < min) {
        for (j in current.indices) {
          removeEdges(current[j], edges)
          if (edges.isEmpty()) {
            if (j + 1 < min) {
              min = j + 1
              cover!!.clear()
              for (i in 0..j) {
                cover!!.add(current[i])
              }
            }
          }
        }
      }
      edges = graph.getEdges()
    } else if (currentTotal == k - 1 && currentTotal < min) {
      for (j in current.indices) {
        removeEdges(current[j], edges)
        if (edges.isEmpty()) {
          if (j + 1 < min) {
            min = j + 1
            cover!!.clear()
            for (i in 0..j) {
              cover!!.add(current[i])
            }
          }
        }
      }
      edges = graph.getEdges()
      return
    } else {
      return
    }
    for (i in vertices.indices) {
      if (!visit[i]) {
        current[++currentTotal] = vertices[i]
        visit[i] = true
        optimizedBruteForce(vertices, k, current, currentTotal, visit)
        visit[i] = false
        currentTotal--
      }
    }
  }

  // Abordagem construtiva para encontrar uma cobertura de vértices de k ou menos vértices através da primeira solução
  // cria uma lista de possiveis coberturas
  // cria uma lista de vertices para uso do método de combinações
  // percorrer e preencher k vértices na lista criada acima
  // adicionar um novo vértice para cada elemento na lista de vértices
  // cria um array booleano do tamanho do número de vértices no gráfico para usar no método de combinações
  // criar um array de booleanos para verificar se um vértice já foi visitado
  // utilizar o metodo de combinações para obter todas as possíveis permutações para cobertura de vertices
  // obter todas as Arestas no Grafo
  // percorrer as possíveis permutações da cobertura de vértices
  // percorrer os vértices na permutação das possíveis coberturas
  // remover todas as arestas que contêm o vértice atual como um ponto final
  // verificar se uma cobertura foi encontrada
  // verificar se a cobertura atende ao requisito k
  // adiciona cada vértice a uma lista de cobertura

  fun firstSolution() {
    possibleCovers = ArrayList()
    val startTime = Instant.now()
    val v = ArrayList<Vertex>()
    for (i in 0 until k) {
      v.add(Vertex(i))
    }
    val visit = BooleanArray(graph.getVertices().size)
    for (i in graph.getVertices().indices) {
      visit[i] = false
    }
    combinations(
      graph.getVertices(),
      k,
      v,
      -1,
      visit
    )
    var edges = graph.getEdges()
    cover = ArrayList()
    for (i in possibleCovers.indices) {
      for (j in 0 until k) {
        removeEdges(possibleCovers[i][j], edges)
        if (edges.isEmpty()) {
          if (j < k) {
            for (l in 0 until j + 1)
              cover!!.add(possibleCovers[i][l])
            println("----------Solução-construtiva------------------------------------------------")
            println("Foi encontrada uma cobertura que satisfaz k = $k")
            println("Vértices da cobertura: " + cover.toString())
            val endTime = Instant.now()
            println(
              "Tempo de Execução: " + Duration.between(startTime, endTime).toMillis() + "ms"
            )
            println("------------------------------------------------------------------------")
            return
          }
        }
      }
      edges = graph.getEdges()
    }
    println("Nenhuma cobertura encontrada que satisfaça = $k por esse método")
    val endTime = Instant.now()
    println(
      "Tempo de Execução: " + Duration.between(startTime, endTime).toMillis() + "ms"
    )
    println("------------------------------------------------------------------------") // for formatting purposes
  }

  // Abordagem de aproximação por seleção  aleatória
  // obter todas as Arestas do Grafo
  // fazer um loop até que uma cobertura de vertices seja encontrada
  // gerar um número aleatório entre 0 e o último índice na lista de arestas
  // obter a aresta associada ao número aleatório gerado
  // adiciona o primeiro vertice da aresta encontrada na lista de vertices da cobertura
  // adiciona o segundo vertice da aresta encontrada na lista de vertices da cobertura
  // remover todas as arestas que contém o primeiro vértice na aresta selecionada acima
  // remover todas as arestas que contém o segundo vértice na aresta selecionada acima

  fun randomApproxCover(): ArrayList<Vertex> {
    val startTime = Instant.now()
    val edges = graph.getEdges()
    cover = ArrayList()
    while (edges.isNotEmpty()) {
      val random = Random()
      val edge = random.nextInt(edges.size)
      val e = edges[edge]
      cover!!.add(e.getVertex1())
      cover!!.add(e.getVertex2())
      removeEdges(
        e.getVertex1(),
        edges
      )
      removeEdges(
        e.getVertex2(),
        edges
      )

    }

    val endTime = Instant.now()
    println("----------Aproximação-Aleatória--------------------------------------------------")
    println("Solução para aproximação-aleatória:")
    println("Vértices da cobertura: " + cover.toString())
    println("Tempo de Execução: " + Duration.between(startTime, endTime).toMillis() + "ms" )
    println("------------------------------------------------------------------------")
    return cover!!.clone() as ArrayList<Vertex>
  }
  // Abordagem de aproximação Gulosa
  // obter todas as Arestas no Grafo
  // obter todos os Vértices no Grafo
  // fazer um loop até que uma cobertura de vertices seja encontrada
  // obter o vértice com o maior número de arestas associadas
  // adicionar o vertice encontrado à lista de cobertura de vértices
  // remover o vértice encontrado da lista de vértices disponíveis
  // remover todas as arestas que contêm o vértice encontrado acima.

  fun greedyCover(): ArrayList<Vertex>? {
    val startTime = Instant.now()
    val edges = graph.getEdges()
    val vertices = graph.getVertices()
    cover = ArrayList()
    while (edges.isNotEmpty()) {
      val vertex = getMaxDegree(vertices, edges)
      cover!!.add(vertex)
      vertices.remove(vertex)
      removeEdges(vertex, edges)
    }
    val endTime = Instant.now()
    println("----------Busca-Gulosa--------------------------------------------------")
    println("Solução para busca gulosa:")
    println("Vértices da cobertura: " + cover.toString())
    println("Tempo de Execução: " + Duration.between(startTime, endTime).toMillis()+ "ms")
    println("------------------------------------------------------------------------")
    return cover!!.clone() as ArrayList<Vertex>
  }



}