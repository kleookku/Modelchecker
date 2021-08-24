An outline of your code and how it works together
Documentation of each test file that you created (if any).
Your answers to the “Written Questions”.

OUTLINE
AdjacencyListGraph:
   - graph that contains nodes; each node contains a list of nodes it connects to
StateMachine:
   - creates the graph (AdjacencyListGraph) by parsing the CSV files
   - checkAlways and checkNever use the helper method reachNodes to check all the reachable nodes
   - reachNodes is a recursive function that uses dynamic programming to store whether or not each node has already been checked to prevent redundant and repetitive function calls.
   - hasLabel is passed into checkAlways and checkNever (shown in tester file) to check if the stateData nodes' labels violate certain conditions.
   - hasLabel checked whether the stateData's list of labels contains the input String.

TEST FILES
test1-edges + test1-nodes
   - all the nodes contain the label "o". the node with id = 1 has an additional label "X", which is looked for in testing.
   - the structure of the graph is shown in the graph pdf as a drawing
   - tests the breadth-search functionality
   - the node with the X label is one of the many that is directly connected to the node with id 0
   - in order to find this node, the program must search through the entire next layer
   - tests that each connection goes in the right direction
test2-edges + test2-nodes
   - all the nodes contain the label "o". the node with id = 10 has an additional label "X", which is looked for in testing.
   - the structure of the graph is shown in the graph pdf as a drawing
   - tests the depth-search functionality
   - the node with the X label is the farthest, depth wise, from the node with the id 0
   - in order to find this node, the program must search through every node's connection's connections, until it reaches the end
   - this graph also tests that no redundant calls are made (program doesn't run forever)
   - tests that each connection goes in the right direction
test3-edges + test3-nodes
   - single node with label "X" that has an edge to itself
   - tests single-node-graph edge-case

WRITTEN QUESTIONS
1. What is the worst-case running time of your checkNever method? State your answer and briefly justify it.
Your answer should explicitly state what the variable is (as in “quadratic in n where n is the number of
turtles in the marina”. Either terms like “quadratic” or formulas like n2 are fine.) [Objective: time/space]

The worst-case running time of my checkNever method is O(n), where n is the number of
nodes in the graph, or linear. This is because, in the worst case, none of the nodes
fail, and thus all the nodes need to be checked. Thus, since every node is checked only
once to see it if passes the condition, the worst-case running time is linear.

2. Assume that you knew that your program had an error (it previously crashed, but you didn’t know why).
Would that change the worst-case running time? Why or why not? [Objective: time/space]

This would change the worst-case running case because the error could cause the program to run forever. This is because the error could be causing there to be repetitive, redundant checks on each of the nodes, instead of only checking each node once. Thus, the worst-case running time would increase, possibly to infinity.

3. Assume that we had asked you to modify checkNever and checkAlways to return not just a boolean
(indicating whether the check passes/fails), but also the path from the start state showing how
to get to the error state (if one exists). How would this change the running time? Explain your answer.
[Objective: time/space]

This would not change the running time because the only difference would be storing the
nodes in a data structure. There would not need to be any additional traversal through the graph.
The run time would still be O(n), or linear.

4. Continue with the previous question’s setup about returning the path to a violating state.
What would you use as the return type of checkNever and checkAlways in this situation? [Objective: OOdesign]

The return type would be a hashmap that maps a node to another node.

5. You implemented one of the two Graph representations (Adjacency List or Adjacency Matrix).
   - Why did you implement the one that you did (honesty here is just fine)? [Objective: choosingDataStructures]
   - State one performance-related advantage to each representation as a contrast the other [Objective: time/space].
   - Briefly explain any programmer/coding-facing tradeoffs between them [Objective: choosingDataStructures]

I implemented Adjacency List because I found it simpler.

Adjacency Matrix takes up less space, because it only requires an array to store all the information
about which nodes connect and a hashmap to map the nodes to their indeces. Adjacency List takes up more
space because every single node has its own list.

Adjacency List makes it more efficient to find which nodes a node connects to, since it just
stores it in a list. In this case, getting the connected nodes would be constant time. However,
in Adjacency Matrix, it would take linear time, because an entire row of the matrix would have to
be looked through, because each slot that contains true means that the node connects, and every slot
needs to be checked.

Adjacency List is more straightforward for the methods that we have to implements, such as
getNexts, because the connected nodes are all stored in a list. Adjacency Matrix is more complex
since we have to traverse the array in different ways.


