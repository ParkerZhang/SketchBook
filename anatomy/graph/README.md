# README.md

## Using Graph Theory to Represent the Warring States/Spring and Autumn Periods

Using mathematical graph theory to represent the map of the Warring States/Spring and Autumn periods is an interesting and reasonable approach. Graph theory uses nodes (vertices) and edges to describe relationships and structures between entities, which can effectively abstract geographical locations and political relationships (such as proximity, alliances, or conflicts). Below are the steps and examples for constructing a graph-theoretic representation of the distribution of states during the Spring and Autumn and Warring States periods, based on the provided map and previous analysis.

### 1. Basic Definitions in Graph Theory

- **Nodes (Vertices):** Each node represents a state or an important small state (e.g., Qin, Qi, Chu, Yan, Zhao, Wei, Han, Song, Zheng, Lu, Qufu, Wu, Yue, Chen, Yu, Guo, and the Zhou Emperor).
- **Edges:** Edges represent relationships between nodes, which can be geographical proximity (adjacent states), political relationships (alliances or conflicts), or transportation links. Edges can be directed (representing one-way relationships, such as tributary relationships) or undirected (representing bidirectional proximity).
- **Weights:** Weights can be added to edges to represent distance, power balance, or relationship strength (e.g., the edge between Qin and Han might have a higher conflict weight).
- **Graph Types:**
  - **Undirected Graph:** Suitable for representing geographical proximity (e.g., Qin and Han are adjacent).
  - **Directed Graph:** Suitable for representing political or military relationships (e.g., Qin attacking Han).
  - **Weighted Graph:** Suitable for representing distance or relationship strength.

### 2. Constructing Nodes and Edges

Based on the provided map and previous descriptions, a simplified undirected graph will be constructed, focusing on geographical proximity (adjacent states). If needed, this can be expanded to a directed or weighted graph.

#### Nodes (Vertices):

Here is a list of the main nodes (labeled in Chinese, with simplified English abbreviations in parentheses for graph representation):

- Qin (Q)
- Qi (Z)
- Chu (C)
- Yan (Y)
- Zhao (Zh)
- Wei (W)
- Han (H)
- Song (S)
- Zheng (Zheng)
- Lu (L)
- Qufu (QF) (as part of Lu, can be merged or labeled separately)
- Wu (Wu)
- Yue (Yue)
- Chen (Ch)
- Yu (Yu)
- Guo (Guo)
- Zhou Emperor (ZT)

#### Edges:

Based on geographical proximity (referencing the map's boundaries and locations), here are the connections between nodes (undirected edges, assuming adjacent states are connected):

- Qin (Q) ↔ Han (H), Guo (Guo), Zhao (Zh)
- Qi (Z) ↔ Yan (Y), Zhao (Zh), Lu (L), Song (S)
- Chu (C) ↔ Wu (Wu), Yue (Yue), Chen (Ch), Zheng (Zheng), Song (S)
- Yan (Y) ↔ Zhao (Zh), Qi (Z)
- Zhao (Zh) ↔ Yan (Y), Qi (Z), Qin (Q), Han (H), Wei (W), Yu (Yu)
- Wei (W) ↔ Zhao (Zh), Han (H), Song (S), Zheng (Zheng)
- Han (H) ↔ Qin (Q), Zhao (Zh), Wei (W), Zheng (Zheng), Guo (Guo)
- Song (S) ↔ Qi (Z), Wei (W), Zheng (Zheng), Lu (L), Chen (Ch), Chu (C)
- Zheng (Zheng) ↔ Song (S), Wei (W), Han (H), Chen (Ch), Chu (C)
- Lu (L) ↔ Qi (Z), Song (S)
- Qufu (QF): Merged with Lu (L) or treated as a sub-node of Lu, connected to Lu.
- Wu (Wu) ↔ Chu (C), Yue (Yue)
- Yue (Yue) ↔ Wu (Wu), Chu (C)
- Chen (Ch) ↔ Chu (C), Song (S), Zheng (Zheng)
- Yu (Yu) ↔ Zhao (Zh), Han (H), Guo (Guo)
- Guo (Guo) ↔ Qin (Q), Han (H), Yu (Yu)
- Zhou Emperor (ZT) ↔ Song (S), Zheng (Zheng), Wei (W) (symbolic connection, reflecting its nominal overlord status)

### 3. Graph Representation

Since I cannot directly generate graphical files, I will represent this graph in text form using an adjacency list and adjacency matrix for your understanding and visualization.

#### Adjacency List (Simplified Representation):

```
Q: H, Guo, Zh
Z: Y, Zh, L, S
C: Wu, Yue, Ch, Zheng, S
Y: Zh, Z
Zh: Y, Z, Q, H, W, Yu
W: Zh, H, S, Zheng
H: Q, Zh, W, Zheng, Guo
S: Z, W, Zheng, L, Ch, C
Zheng: S, W, H, Ch, C
L: Z, S
QF: L (sub-node or merged)
Wu: C, Yue
Yue: Wu, C
Ch: C, S, Zheng
Yu: Zh, H, Guo
Guo: Q, H, Yu
ZT: S, Zheng, W
```

#### Adjacency Matrix (17x17 Matrix, 0 indicates no connection, 1 indicates a connection):

```
    Q  Z  C  Y  Zh W  H  S  Zheng L  QF Wu Yue Ch Yu Guo ZT
Q   0  0  0  0  1  0  1  0  0    0  0  0  0  0  0  1  0
Z   0  0  0  1  1  0  0  1  0    1  0  0  0  0  0  0  0
C   0  0  0  0  0  0  0  1  1    0  0  1  1  1  0  0  0
Y   0  1  0  0  1  0  0  0  0    0  0  0  0  0  0  0  0
Zh  1  1  0  1  0  1  1  0  0    0  0  0  0  1  0  0  0
W   0  0  0  0  1  0  1  1  1    0  0  0  0  0  0  1  0
H   1  0  0  0  1  1  0  0  1    0  0  0  0  1  1  0  0
S   0  1  1  0  0  1  0  0  1    1  0  0  1  0  0  0  1
Zheng 0  0  1  0  0  1  1  1  0    0  0  0  1  0  0  0  1
L   0  1  0  0  0  0  0  1  0    0  0  0  0  0  0  0  0
QF  0  0  0  0  0  0  0  0  0    1  0  0  0  0  0  0  0
Wu  0  0  1  0  0  0  0  0  0    0  0  0  1  0  0  0  0
Yue 0  0  1  0  0  0  0  0  0    0  0  1  0  0  0  0  0
Ch  0  0  1  0  0  0  0  1  1    0  0  0  0  0  0  0  0
Yu  0  0  0  0  1  0  1  0  0    0  0  0  0  0  1  0  0
Guo 1  0  0  0  0  0  1  0  0    0  0  0  0  1  0  0  0
ZT  0  0  0  0  0  1  0  1  1    0  0  0  0  0  0  0  0
```

### 4. Graph Analysis and Applications

- **Connectivity:** Check if the graph is connected (whether all nodes are connected via paths). In this graph, the states are connected through geographical proximity, reflecting their potential for interaction.
- **Degree:** The degree of each node represents the number of adjacent states. For example, Qin (Q) is connected to Qin, Han, Guo, and Zhao, with a degree of 4.
- **Shortest Path:** Algorithms like Dijkstra or Floyd can be used to calculate the "geographical distance" or relationship path between two states (e.g., the shortest path from Qin to Qi might pass through Han and Zhao).
- **Subgraphs and Partitions:** Analyze subgraphs of the major states and smaller states to identify core regions (e.g., the Central Plains) and peripheral regions (e.g., Wu and Yue).

### 5. Visualization Suggestions

Although I cannot generate graphical files, you can use the following tools to visualize this graph:

- **Python's NetworkX:** Write Python code to input the adjacency list or matrix and generate a graphical visualization.
  - Install NetworkX: `pip install networkx matplotlib`
  - Example code (simplified to major states):

```python
import networkx as nx
import matplotlib.pyplot as plt

G = nx.Graph()
nodes = ['Qin', 'Qi', 'Chu', 'Yan', 'Zhao', 'Wei', 'Han']
edges = [('Qin', 'Han'), ('Qin', 'Zhao'), ('Qi', 'Yan'), ('Qi', 'Zhao'), ('Qi', 'Lu'), ('Qi', 'Song'), 
         ('Chu', 'Wu'), ('Chu', 'Yue'), ('Chu', 'Chen'), ('Chu', 'Zheng'), ('Chu', 'Song'), ('Yan', 'Zhao'),
         ('Zhao', 'Yan'), ('Zhao', 'Qi'), ('Zhao', 'Qin'), ('Zhao', 'Han'), ('Zhao', 'Wei'), ('Wei', 'Zhao'),
         ('Wei', 'Han'), ('Wei', 'Song'), ('Wei', 'Zheng'), ('Han', 'Qin'), ('Han', 'Zhao'), ('Han', 'Wei'),
         ('Han', 'Zheng'), ('Song', 'Qi'), ('Song', 'Wei'), ('Song', 'Zheng'), ('Song', 'Lu'), ('Song', 'Chen'),
         ('Song', 'Chu'), ('Zheng', 'Song'), ('Zheng', 'Wei'), ('Zheng', 'Han'), ('Zheng', 'Chen'), ('Zheng', 'Chu'),
         ('Lu', 'Qi'), ('Lu', 'Song'), ('Wu', 'Chu'), ('Wu', 'Yue'), ('Yue', 'Wu'), ('Yue', 'Chu'),
         ('Chen', 'Chu'), ('Chen', 'Song'), ('Chen', 'Zheng')]

G.add_nodes_from(nodes)
G.add_edges_from(edges)

pos = nx.spring_layout(G)  # Automatic layout
nx.draw(G, pos, with_labels=True, node_color='lightblue', node_size=500, font_size=8, font_family='SimHei')
plt.title("Geographical Proximity Graph of the Spring and Autumn/Warring States Period")
plt.show()
```

- **Other Tools:** Graphviz, Cytoscape, or Mathematica can also be used to draw and analyze graphs.

### 6. Limitations

- Graph theory focuses on relationships and structures, so precise geographical locations (e.g., boundary shapes) are difficult to fully express. However, this can be improved by optimizing node layouts (e.g., using geographical coordinates as position parameters).
- Political relationships (e.g., alliances, conflicts) are not reflected in this simplified graph. If needed, it can be expanded into a weighted or directed graph, with weights representing relationship strength or direction.

If you need more detailed graph theory analysis (e.g., weighted graphs, directed graphs, subgraph analysis) or specific code support, please let me know, and I can further expand on this!


```bash
fdp -Tsvg 20250228.dot -o 20250228.svg
neato -Tsvg 20250228.dot -o 20250228.svg
dot -Tsvg 20250228.dot -o 20250228.svg
```
