import math
import heapq

def get_edges(filename):
    edges = []
    max_v = 0
    file = open(filename, 'r')
    for line in file:
        line = line.split()
        if len(line) == 3:
            v, u, w = line
            edges.append([int(v), int(u), int(w)])
        elif len(line) == 1:
            max_v = int(line[0])
    file.close()
    return edges, max_v


###################################################
# DO NOT MODIFY THE LINES IN THE BLOCK BELOW.
# YOU CAN WRITE YOUR CODE ABOVE OR BELOW THIS BLOCK
###################################################
def get_customers(filename):
    file = open(filename)
    customers = []
    for line in file:
        line = line.strip()
        customers.append(int(line))
    return customers

# path is a list of vertices on the path, distance is the total length of the path
# task_id must be 1 or 2 (corresponding to the task for which solution is being printed
def print_solution(path,distance,task_id):
    print()
    if task_id == 1:
        print("Shortest path: ", end ="")
    else:
        print("Minimum detour path: ",end="")

    customers = get_customers("customers.txt")
    
    vertices = []
    for item in path:
        if item in customers: 
            vertices.append(str(item)+ "(C)")
        else:
            vertices.append(str(item))

    print(" --> ".join(vertices))
    if task_id == 1:
        print("Shortest distance:", distance)
    else:
        print("Minimum detour distance:",distance)

source = int(input("Enter source vertex: "))
target = int(input("Enter target vertex: "))
####################################################
# DO NOT MODIFY THE LINES IN THE ABOVE BLOCK.
# YOU CAN WRITE YOUR CODE ABOVE OR BELOW THIS BLOCK
###################################################

# Dijkstra's algorithm using min-heap
# Time Complexity of O(E Log V)
# Space Complexity of O(V + E)
def get_shortest_path(location, edges, max_v):
    discovered = []
    # list for prev vertex passed
    prev = [None] * (max_v + 1)
    distances = [None] * (max_v + 1)
    finalized = [math.inf] * (max_v + 1)

    # push starting vertex into heap
    heapq.heappush(discovered, [0, location])

    while len(discovered) > 0:
        # get lowest element
        current_v = heapq.heappop(discovered)

        # check if vertex has been finalized or not
        if finalized[current_v[1]] == math.inf:
            valid_e = []

            # filter out the edge list
            for edge in edges:

                # find the vertex in edge line and reverse if needed

                if edge[0] == current_v[1]:
                    v, u, w = edge
                    valid_e.append([v, u, w])

                elif edge[1] == current_v[1]:
                    u, v, w = edge
                    valid_e.append([v, u, w])

            # check if edge is finalised if not add to discovered
            for edge in valid_e:
                if finalized[edge[1]] == math.inf:
                    heapq.heappush(discovered, [edge[2] + current_v[0], edge[1]])
                    # make the index of corresponding prev of vertex2 to equal vertex1
                    new_d = edge[2] + current_v[0]

                    if distances[edge[1]] is None or distances[edge[1]] > new_d:
                        distances[edge[1]] = new_d
                        prev[edge[1]] = edge[0]

            finalized[current_v[1]] = current_v[0]

    return finalized, prev


def get_path(prev, vertex, reverse):
    shortest_path = []
    # get the shortest path using prev list by looping through all the prev vertex until the source vertex is found
    current_v = vertex
    # checks if the given vertex is the only path
    if prev[vertex] is not None:
        while prev[current_v] is not None:
            if reverse == 1:
                shortest_path.append(current_v)
                current_v = prev[current_v]
            elif reverse == 0:
                current_v = prev[current_v]
                shortest_path.append(current_v)
    if reverse == 1:
        shortest_path.reverse()
    return shortest_path


# Task 1
edges, max_v = get_edges("edges.txt")
source_finalized, prev = get_shortest_path(source, edges, max_v)

shortest_path = []
shortest_path.append(source)
shortest_path = shortest_path + get_path(prev, target, 1)
shortest_dist = source_finalized[target]

print_solution(shortest_path, shortest_dist, 1)


# Task 2
def get_customer_path(source_finalized, prev, max_v, edges, customers, target, source):
    target_finalized, prev2 = get_shortest_path(target, edges, max_v)

    min_detour = source_finalized[customers[0]] + target_finalized[customers[0]]
    customer = None

    # find minimum detour
    for c in customers:
        dist = source_finalized[c] + target_finalized[c]
        if dist < min_detour:
            min_detour = dist
            customer = c

    # get path from source to customer and customer to target
    source_to_customer = get_path(prev, customer, 1)
    customer_to_target = get_path(prev2, customer, 0)

    path = []
    path.append(source)
    path = path + source_to_customer + customer_to_target

    return path, min_detour

customers = get_customers("customers.txt")
path, distance = get_customer_path(source_finalized, prev, max_v, edges, customers, target, source)
print_solution(path, distance, 2)
