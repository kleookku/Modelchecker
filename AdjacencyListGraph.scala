package sol

import src.{Graph, Node}

/**
 * Class for the adjacency list representation of a Graph.
 *
 * @tparam T the type of the nodes' contents
 */
class AdjacencyListGraph[T] extends Graph[T] {

  private var nodeList = List[ALNode]()

  /**
   * An AdjacencyList implementation of a graph node
   * @param contents - the value stored at that node
   * @param getsTo - the list of nodes which this node gets to
   */
  class ALNode(private val contents: T, private var getsTo: List[Node[T]]) extends Node[T] {
    @Override
    override def addEdge(toNode: Node[T]): Unit = {
      if(!getsTo.contains(toNode)){
        getsTo = toNode :: getsTo
      }
    }

    @Override
    override def getContents(): T = {
      contents
    }

    @Override
    override def getNexts(): List[Node[T]] = {
      getsTo
    }

    override def toString: String = {
      contents.toString
    }

}
  @Override
  override def createNode(contents: T): Node[T] = {
    val newNode = new ALNode(contents, List())
    nodeList = newNode :: nodeList
    newNode
  }

  @Override
  override def addEdge(fromNode: Node[T], toNode: Node[T]): Unit = {
    fromNode.addEdge(toNode)
  }

  @Override
  override def show(): Unit = {
    for(node <- nodeList){
      println("Node " + node.getContents + " gets to " + node.getNexts())
    }
  }
}
