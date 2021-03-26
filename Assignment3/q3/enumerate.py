# Johnson Nguyen 27860392

import sys

nodes = None

if len(sys.argv) == 2:
    nodes = int(sys.argv[1])

L = []
if nodes == 0:
    L.append("1")

elif nodes >= 1:
    L.append("1")
    L.append("011")

# keeps track of the previous n nodes
count = 1
index_current = 2

for i in range(2, nodes + 1):

    index_start = index_current - count
    index_end = index_start + count
    count = 0

    while index_start != index_end:

        string = L[index_start]
        iterator = 0
        n = len(string)
        # keeps track of the number of 1's passed
        counter = 0

        # finds all N node trees
        while counter != i:
            if string[iterator] == '1':
                counter += 1
                new_string = string[:iterator] + '011' + string[iterator + 1:]

                if new_string not in L:
                    L.append(new_string)
                    index_current += 1
                    count += 1

            iterator += 1
        index_start += 1


file = open("output_enumerate.txt", "w+")

for k in range(len(L)):

    file.write(str(k + 1) + "\t" + "\t" + str(L[k]) + "\n")

file.close()