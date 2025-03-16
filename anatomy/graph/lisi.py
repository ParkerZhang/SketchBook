import networkx as nx
import matplotlib.pyplot as plt

# 创建有向图
G = nx.DiGraph()

# 添加节点
nodes = [
    "秦穆公", "秦孝公", "秦惠王", "秦昭王", "李斯",
    "由余", "百里奚", "蹇叔", "丕豹", "公孙支", "商鞅", "张仪", "范雎",
    "秦国", "戎", "宛", "宋", "晋", "楚", "魏", "巴", "蜀", "上郡", "汉中", "鄢", "郢", "成皋",
    "求士", "变法", "拔三川之地", "蚕食诸侯", "逐客令"
]
G.add_nodes_from(nodes)

# 添加边（关系）
edges = [
    ("秦穆公", "由余"), ("秦穆公", "百里奚"), ("秦穆公", "蹇叔"), ("秦穆公", "丕豹"), ("秦穆公", "公孙支"),
    ("秦孝公", "商鞅"), ("秦惠王", "张仪"), ("秦昭王", "范雎"),
    ("由余", "戎"), ("百里奚", "宛"), ("蹇叔", "宋"), ("丕豹", "晋"), ("公孙支", "晋"),
    ("秦孝公", "秦国"), ("秦惠王", "秦国"), ("秦昭王", "秦国"), ("李斯", "秦国"),
    ("秦穆公", "求士"), ("秦孝公", "变法"), ("秦惠王", "拔三川之地"), ("秦昭王", "蚕食诸侯"), ("李斯", "逐客令"),
    ("秦国", "逐客令")
]
G.add_edges_from(edges)

# 可以添加带权重的边，例如：
G.add_edge("秦穆公", "百里奚", weight=10)


# 绘制图
pos = nx.spring_layout(G)  # 选择布局算法
nx.draw(G, pos, with_labels=True, node_size=1500, node_color="skyblue", font_size=10)
plt.show()


# 度中心性
degree_centrality = nx.degree_centrality(G)
print("度中心性:", degree_centrality)

# 介数中心性
betweenness_centrality = nx.betweenness_centrality(G)
print("介数中心性:", betweenness_centrality)

# 特征向量中心性
eigenvector_centrality = nx.eigenvector_centrality(G)
print("特征向量中心性:", eigenvector_centrality)

