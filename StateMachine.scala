package sol

import src.Graph
import src.Node

// ------------- DO NOT CHANGE --------------------
import org.apache.commons.csv._
import java.io.{File, FileReader, IOException}
import scala.collection.JavaConverters.iterableAsScalaIterableConverter
import scala.collection.mutable
// ------------------------------------------------

/**
 * Class of the State Machine.
 * @param stateGraph - an object which implements Graph stored inside a
 *                   stateMachine.
 */
class StateMachine(stateGraph: Graph[StateData]) {

  // field for the starting state of the state machine
  private var startState : Option[Node[StateData]] = None

  // field for checking reachable nodes
  private var alreadyChecked = mutable.HashMap[Node[StateData], Boolean]()

  /**
   * set the start node
   * @param startNode node to set as the start
   */
  def setStart(startNode: Node[StateData]): Unit = {
    startState = Some(startNode)
  }

  // TODO : Modify initCSV so that it raises an IOException if the edgesCSV
  //  tries to use a state that was not listed in the nodeCSV
  /**
   * Initializes a StateMachine from CSV files.
   * @param nodeCSV - the filename of the CSV containing node information
   * @param edgesCSV - the filename of the CSV containing edges information
   * @param startState - the starting state of the state machine (the id of the
   *                   node)
   */
  def initFromCSV(nodeCSV: String, edgesCSV: String, startState: String) {
    val format = CSVFormat.RFC4180.withHeader();

    // Parse nodeCSV
    val idMap = new mutable.HashMap[String, Node[StateData]]()
    try {
      val parser: CSVParser = new CSVParser(new FileReader(new File(nodeCSV)),
        format)
      val records = parser.getRecords().asScala.toList
      for (record <- records) {
        idMap(record.get(0))
          = stateGraph.createNode(new StateData(record.get(1).split(";").toList))

        // TODO: hashmap for checking reachable nodes
        alreadyChecked(idMap(record.get(0))) = false

        if(record.get(0).equals(startState)) {
          this.startState = Some(idMap(record.get(0)))
        }
      }
    }
    catch {
      case e: IOException => println("error reading " + nodeCSV)
    }

    // Parse edgeCSV
    try {
      val parser: CSVParser = new CSVParser(new FileReader(new File(edgesCSV)), format)
      val records = parser.getRecords().asScala.toList
      for (record <- records) {
        // TODO : Modify initCSV so that it raises an IOException if the edgesCSV
        //  tries to use a state that was not listed in the nodeCSV
        if(!idMap.contains(record.get(0)) || !idMap.contains(record.get(1))){
          throw new IOException
        }
        stateGraph.addEdge(idMap(record.get(0)), idMap(record.get(1)))
      }
    }
    catch {
      case e: IOException => println("error reading" + edgesCSV)
    }
    //stateGraph.show()
  }

  /**
   * Checks if input method returns true for all reachable states.
   * @param checkNode - method to check the state against
   * @return Some(node) if condition fails, None if condition passes
   */
  def checkAlways(checkNode: (StateData => Boolean)): Option[Node[StateData]] = {
    val start : Node[StateData ]= startState.get
    var result : Option[Node[StateData]] = None
    val condition = true
    if(checkNode(start.getContents()) == condition){
      result = reachNodes(start, checkNode, condition)
    } else{
      result = Some(start)
    }
    alreadyChecked = alreadyChecked.map{ case(k, v) => k -> false} // reset alreadyChecked to false
    result
  }

  /**
   * Checks if input method returns false for all reachable states.
   * @param checkNode method to check the state against
   * @return Some(node) if condition fails, None if condition passes
   */
  def checkNever(checkNode: StateData => Boolean): Option[Node[StateData]] = {
    val start : Node[StateData ]= startState.get
    var result : Option[Node[StateData]] = None
    val condition = false
    if(checkNode(start.getContents()) == condition){
      result = reachNodes(start, checkNode, condition)
    } else result = Some(start)
    alreadyChecked = alreadyChecked.map{ case(k, v) => k -> false} // reset alreadyChecked to false
    result
  }

  /**
   * Recursive function that checks whether every node
   * reachable from the start node (start) returns the desired condition
   * when passed into the input function (checkNode).
   * @param start - the start node
   * @param checkNode - method to check the node against
   * @param condition - desired condition (true for checkAlways, false for checkNever)
   * @return Some if condition fails, None if condition passes
   */
  private def reachNodes
  (start : Node[StateData], checkNode: (StateData => Boolean), condition : Boolean):
  Option[Node[StateData]] ={
    var result : Option[Node[StateData]] = None
    for(node <- start.getNexts()){
      if(!alreadyChecked(node)){
        alreadyChecked(node) = true // set checked to true
        if(checkNode(node.getContents()) != condition){ // if doesn't reach the condition
          return Some(node) // return some
        }
        if(!node.getNexts().isEmpty){ // if there are more nodes that it connects to
          val nodeResult = reachNodes(node, checkNode, condition)
          if(nodeResult != None){
            result = nodeResult
          }
        }
      }
    }
    result
  }
}