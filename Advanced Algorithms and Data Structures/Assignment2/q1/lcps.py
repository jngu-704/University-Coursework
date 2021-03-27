# Johnson Nguyen 27860396

# Class Node
class Node:
    def __init__(self, parent, label, start, end, leaf):
        # array to hold children nodes for each letter

        self.parent_node = parent
        self.children = [None] * 62
        self.leaf = None
        self.label = label
        self.index_start = start
        self.index_end = end
        self.suffix_link = None

    def get_leaf(self):
        return self.leaf

    def set_leaf(self, leaf):
        self.leaf = leaf

    def get_index_start(self):
        return self.index_start

    def set_index_start(self, new_start):
        self.index_start = new_start

    def get_index_end(self):
        return self.index_end

    def set_index_end(self, new_end):
        self.index_end = new_end

    def get_suffix_link(self):
        return self.suffix_link

    def set_suffix_link(self, node):
        self.suffix_link = node

    def add_children(self, node, char):
        index = self.get_indexofchild(char)
        self.children[index] = node

    def get_child(self, char):
        index = self.get_indexofchild(char)
        return self.children[index]

    def get_indexofchild(self, char):
        if char.isdigit():  # [0..9] 48 ascii value
            index = ord(char) - 48
        elif char.isupper():  # [A..Z] 65 ascii value
            index = ord(char) - 55
        else:  # [a..z] 97 ascii value
            index = ord(char) - 61
        return index



"""
Construct implicitST
for i = 1 to n - 1      (Phase i + 1)
        For j from 1 to i + 1       (Suffix Extension j)
            Find end of path from root denoting str[j..i]
            Apply one of the three suffix extension rules

        End of extension step j (i.e., str[j..i + 1] extension computed)
    End of phase i + 1 (implicitST(i+1) computed)
"""

# def rule_1():


# update leaf nodes with end
s = "abba"
n = len(s)

root_node = Node(None, None, None, None, None)

# keeps track of current node
current_node = root_node

# TEST
nodes = []

for i in range(n):
    # when a node's index_end is -1 it references global_end
    global global_end
    global_end = i
    print("====================================")
    print("i:", i)
    print("----")

    # keeps track of current edge
    current_edge = -1

    # keeps track of current length in edge
    current_len = 0

    # increment at the start of each phase
    remainder = 0  # decrements when it creates a lead node

    next_node = current_node.get_child(s[i])

    # internal node
    new_node = None

    while remainder > 0:
        if current_len == 0:
            current_edge = i

        if next_node is None:
            new_node = Node(current_node, s[i], i, -1, i)
            # testing
            nodes.append(new_node)
            # ****
            current_node.add_children(next_node, s[i])

        else:

            start = current_node.get_index_start()




































