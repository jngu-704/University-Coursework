# Johnson Nguyen 27860396

import sys


class Node:
    def __init__(self, key, parent, sibling):
        # array to hold children nodes for each letter
        # root nodes have no parent link but has a sibling link
        # to other root nodes
        self.key = key
        self.degree = 0
        self.parent = parent
        self.sibling = sibling
        self.child = None

    # this function links two nodes together when merged
    def link_node(self, node):
        # increase degree order
        self.degree += 1
        # assign this node as parent of subtree root node
        node.parent = self
        # assign link between new subtree and child of this node
        if self.child is not None:
            node.sibling = self.child
        # make new subtree root node the child of this node
        self.child = node


class BinomialHeap:
    def __init__(self):
        self.h1 = None
        self.h2 = None

    # merges two heaps into one
    # root_node will be node that is already in H1
    # root_node2 will be node that is not in H1
    def merge(self, root_node1, root_node2):
        # find the smaller key of the two
        if root_node1.key <= root_node2.key:
            root_node1.link_node(root_node2)
            return root_node1
        else:
            root_node2.link_node(root_node1)
            return root_node2
        # return new node

    # node key, parent, sibling
    # inserts a new element into the existing heaps
    def build_tree(self, key):
        # if there is no duplicate degree
        if self.h1 is None:
            node = Node(key, None, None)
            self.h1 = node
        else:
            node = Node(key, None, None)
            # remove current head and get next head
            self.insert(node, self.h1, 0)

    # finds a new spot for the trees in a heap
    # maintaining the heap properties
    def insert(self, node, head, num):
        # compare if new node is less than current_node
        if head is None:
            if num == 0:
                self.h1 = node
            elif num == 1:
                self.h2 = node

        else:
            new_node = node
            # holds current node being checked
            current_node = head
            # holds the previous node used to link new node
            prev_node = None
            node_inserted = False
            # find new spot in head for new node
            while node_inserted is False:

                if new_node.degree < current_node.degree:
                    # assign link from new node to current node
                    new_node.sibling = current_node
                    # assign link from prev node to new node if exist
                    if prev_node is not None:
                        prev_node.sibling = new_node
                    # else assign new_node to head1 as it is the lowest degree
                    else:
                        if num == 0:
                            self.h1 = new_node
                        elif num == 1:
                            self.h2 = new_node
                    node_inserted = True

                # if degrees are equal, merge
                elif new_node.degree == current_node.degree:
                    # remove node out of head
                    if prev_node is not None:
                        if current_node.sibling is not None:
                            prev_node.sibling = current_node.sibling
                        else:
                            prev_node.sibling = None

                    # if there was no previous_node
                    else:
                        if current_node.sibling is not None:
                            head = current_node.sibling
                        else:
                            head = None

                    # remove old links
                    current_node.sibling = None
                    # merge into new node
                    new_node = self.merge(current_node, new_node)
                    # reset current_node and prev_node
                    current_node = head
                    prev_node = None
                    if head is None:
                        if num == 0:
                            self.h1 = new_node
                        elif num == 1:
                            self.h2 = new_node
                        node_inserted = True

                else:
                    if current_node.sibling is not None:
                        prev_node = current_node
                        current_node = current_node.sibling

                    else:
                        current_node.sibling = new_node
                        node_inserted = True

    # find the min in the heap (comparing the root nodes of the orders)
    # returns min element of all roots in heap
    def find_min(self):
        min_node = self.h1
        min_prev = None
        if self.h1 is not None:
            min_key = self.h1.key
            current_node = self.h1
            # go through the sibling links to find lowest root node
            while current_node.sibling is not None:
                if current_node.sibling.key < min_key:
                    min_prev = current_node
                    min_node = current_node.sibling
                    min_key = current_node.sibling.key
                current_node = current_node.sibling
        return min_node, min_prev

    # finds and deletes the min element in the heap
    # returns the lowest element
    def extract_min(self):
        min_node, min_prev = self.find_min()
        if min_node is not None:
            # link prev node and sibling of current node
            if min_prev is not None:
                if min_node.sibling is not None:
                    min_prev.sibling = min_node.sibling
                else:
                    min_prev.sibling = None
            else:
                if min_node.sibling is not None:
                    self.h1 = min_node.sibling
                else:
                    self.h1 = None

            # remove min element from tree
            if min_node.child is not None:
                # assign child of the min node that is removed to current node
                current_node = min_node.child

                # insert split trees into h2
                while current_node is not None:
                    next_node = current_node.sibling
                    current_node.parent = None
                    current_node.sibling = None
                    self.insert(current_node, self.h2, 1)
                    current_node = next_node

                current_node = self.h2

                # remove the trees in h2 by order and insert into h1
                while current_node is not None:
                    next_node = current_node.sibling
                    self.h2 = next_node
                    current_node.parent = None
                    current_node.sibling = None
                    self.insert(current_node, self.h1, 0)
                    current_node = next_node

            return min_node.key


txt = []

if len(sys.argv) == 2:
    txt_file = open(sys.argv[1], 'r')
    for line in txt_file:
        txt.append(int(line))
    txt_file.close()

n = len(txt)
b_heap = BinomialHeap()

for i in range(n):
    b_heap.build_tree(txt[i])

for i in range(n):
    print(b_heap.extract_min())

















