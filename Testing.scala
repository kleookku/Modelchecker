package sol

import tester.Tester

/**
 * test suite
 */
object Testing {

  /**
   * test AdjacencyLisGraph class
   * @param t Tester
   */
  def testALGraph(t : Tester): Unit = {
    val graph = new AdjacencyListGraph[String]
    val node1 = graph.createNode("a")
    val node2 = graph.createNode("b")
    graph.addEdge(node1, node2)
    t.checkExpect(node1.getNexts().size, 1)
    t.checkExpect(node1.getNexts(), List(node2))
    t.checkExpect(node2.getNexts().size, 0)

    t.checkExpect(node1.getNexts().head, node2)

    val node3 = graph.createNode("c")
    graph.addEdge(node1, node3)

    t.checkExpect(node1.getNexts().size, 2)
    t.checkExpect(node1.getNexts(), List(node3, node2))
    t.checkExpect(node2.getNexts().size, 0)
    t.checkExpect(node3.getNexts().size, 0)

    t.checkExpect(node1.getContents(), "a")
    t.checkExpect(node2.getContents(), "b")

    node3.addEdge(node2)
    node1.addEdge(node2)

    t.checkExpect(node3.getNexts().size, 1)
    t.checkExpect(node3.getNexts(), List(node2))
    t.checkExpect(node1.getNexts().size, 2)

    //graph.show()
  }

  /**
   * test StateMachine class
   * @param t Tester
   */
  def testStateMachine(t : Tester) : Unit = {
    val test2Edges =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/test2-edges.csv"
    val test2Nodes =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/test2-nodes.csv"
    val test2 = new StateMachine(new AdjacencyListGraph[StateData])
    test2.initFromCSV(test2Nodes, test2Edges, "0")
    t.checkExpect(
      test2.checkNever(state => state.hasLabel("X")).get.getContents().labels,
      List("o", "X"))
    t.checkExpect(
      test2.checkNever(state => state.hasLabel("o")).get.getContents().labels,
      List("o"))
    t.checkExpect(test2.checkAlways(state => state.hasLabel("o")), None)
    test2.initFromCSV(test2Nodes, test2Edges, "9")
    t.checkExpect(test2.checkNever(state => state.hasLabel("X")), None)
    t.checkExpect(
      test2.checkNever(state => state.hasLabel("o")).get.getContents().labels,
      List("o"))
    t.checkExpect(test2.checkAlways(state => state.hasLabel("o")), None)

    val test1Edges =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/test1-edges.csv"
    val test1Nodes =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/test1-nodes.csv"
    val test1 = new StateMachine(new AdjacencyListGraph[StateData])
    test1.initFromCSV(test1Nodes, test1Edges, "0")
    t.checkExpect(
      test1.checkNever(state => state.hasLabel("X")).get.getContents.labels,
      List("o", "X"))
    t.checkExpect(
      test1.checkNever(state => state.hasLabel("o")).get.getContents.labels,
      List("o"))
    t.checkExpect(test1.checkAlways(state => state.hasLabel("o")), None)
    test1.initFromCSV(test1Nodes, test1Edges, "4")
    t.checkExpect(
      test1.checkNever(state => state.hasLabel("X")), None)
    t.checkExpect(
      test1.checkNever(state => state.hasLabel("o")).get.getContents.labels,
      List("o"))
    t.checkExpect(test1.checkAlways(state => state.hasLabel("o")), None)

    val edgesCSV =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/street-light-edges.csv"
    val nodesCSV =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/street-light-nodes.csv"
    val streetlight = new StateMachine(new AdjacencyListGraph[StateData])
    streetlight.initFromCSV(nodesCSV, edgesCSV, "4")
    t.checkExpect(
      streetlight.checkAlways(state => !state.hasLabel("not a label")), None)
    t.checkExpect(streetlight.checkNever(
      state => state.hasLabel("bigAveGreen")
        && state.hasLabel("smallStGreen")).get.getContents.labels,
      List("bigAveGreen", "smallStGreen"))
    streetlight.initFromCSV(nodesCSV, edgesCSV, "3")
    t.checkExpect(streetlight.checkNever(
      state => state.hasLabel("bigAveGreen")
        && state.hasLabel("smallStGreen")), None)
    streetlight.initFromCSV(nodesCSV, edgesCSV, "0")
    t.checkExpect(streetlight.checkNever(
      state => state.hasLabel("bigAveGreen")
        && state.hasLabel("smallStGreen")), None)
    t.checkExpect(streetlight.checkAlways(
      state => !state.hasLabel("bigAveGreen")
        || !state.hasLabel("smallStGreen")), None)
    streetlight.initFromCSV(nodesCSV, edgesCSV, "8")
    t.checkExpect(streetlight.checkNever(
      state => state.hasLabel("bigAveGreen")
        && state.hasLabel("smallStGreen")).get.getContents.labels,
      List("bigAveGreen", "smallStGreen"))
    streetlight.initFromCSV(nodesCSV, edgesCSV, "7")
    t.checkExpect(streetlight.checkNever(
      state => state.hasLabel("bigAveGreen")
        && state.hasLabel("smallStGreen")).get.getContents.labels,
      List("bigAveGreen", "smallStGreen"))

    val test3Edges =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/test3-edges.csv"
    val test3Nodes =
      "/Users/kleoku/Desktop/brown/spring2021/CS0180/IntelliJ/" +
        "modelchecking-kleookku/data/test3-nodes.csv"
    val test3 = new StateMachine(new AdjacencyListGraph[StateData])
    test3.initFromCSV(test3Nodes, test3Edges, "0")
    t.checkExpect(
      test3.checkNever(state => state.hasLabel("o")), None)
    t.checkExpect(
      test3.checkNever(state => state.hasLabel("X")).get.getContents.labels,
      List("X"))
  }

    /**
   * main method
   * @param args
   */
  def main(args: Array[String]): Unit ={
    Tester.run(Testing);
  }

}
