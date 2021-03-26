# Johnson Nguyen 27860392
# Encodes text using LZSS + Z algorithm

import sys
import heapq


class Node:
    def __init__(self, char, freq):
        self.chars = char     # holds the char
        self.freq = freq    # holds the frequency
        self.array = []     # list of chars

    # this function allows sorting by chosen attribute
    def __lt__(self, node2):
        return self.freq < node2.freq


class LZSS:
    def __init__(self, window, lookahead):
        self.W = window
        self.L = lookahead

    # need to incorporate Z algo for matching
    def encode(self, a_str):
        n = len(a_str)
        buffer = 1
        dict_start = 0  # holds the index of dictionary start
        encoded = []  # list to hold the compressed values

        encoded.append([1, a_str[0]])  # append first triple

        # loop until all elements have been encoded
        while buffer < n:
            offset = None  # length between current element and matched element
            longest_len = 0  # current longest length
            current_c = None  # mismatched character

            # set dictionary start index
            if buffer < self.W:
                dict_start = 0
            else:
                dict_start = buffer - self.W

            # returns longest_len and index
            longest_len, index = z_algorithm(a_str[buffer: buffer + self.L], a_str[dict_start: buffer])
            offset = index

            if longest_len < 3:  # < 3 : (1-bit, char)
                current_c = a_str[buffer]
                encoded.append([1, current_c])
                buffer += 1

            else:  # >= 3 : (0-bit, offset, length)
                encoded.append([0, offset, longest_len])
                buffer += longest_len

        return encoded


def z_algorithm(pat, s1):
    string = pat + s1
    left = 0
    right = 0

    longest_length = 0
    index = 0

    # q iterates through while matching
    q = len(pat)

    Z = [0] * (len(string))

    # k iterates through the string

    for k in range(len(pat), len(string)):
        q = k

        # Case 1
        if k > right:
            while q < len(string) and string[q] == string[q-k]:
                q += 1

            if q == len(string) and string[0] == string[q-k]:
                Z[k] = q - k + 1
            else:
                Z[k] = q - k

            if Z[k] > 0:
                right = q - 1
                left = k

            if Z[k] > longest_length:
                longest_length = Z[k]
                index = k

        # Case 2
        else:
            # Case 2A
            if Z[k - left] < right - k + 1:
                Z[k] = Z[k - left]

                if Z[k] > longest_length:
                    longest_length = Z[k]
                    index = k

            # Case 2B
            else:
                while q < len(string) and string[q - k] == string[q]:
                    q += 1

                if q == len(string) and string[0] == string[q - k]:
                    Z[k] = q - k + 1
                else:
                    Z[k] = q - k

                if Z[k] > longest_length:
                    longest_length = Z[k]
                    index = k



    # print("index: ", len(string) - index)
    # print(string)
    # print(Z)
    # print("")
    return longest_length, len(string) - index


def huffman(a_list):
    # this list holds the reversed huffman code of all characters
    ascii_list = [''] * 128

    # joining the two lowest frequency nodes
    # runs while there are 2 or more nodes in the list
    while len(a_list) >= 2:

        node1 = heapq.heappop(a_list)
        # this loop adds the bit for every char it contains
        for i in node1.chars:
            ascii_list[ord(i)] += '0'

        node2 = heapq.heappop(a_list)
        # this loop adds the bit for every char it contains
        for i in node2.chars:
            ascii_list[ord(i)] += '1'

        total_freq = node1.freq + node2.freq
        new_chars = node1.chars + node2.chars
        new_node = Node(new_chars, total_freq)
        # push new node in list
        heapq.heappush(a_list, new_node)

    return ascii_list


def elias_code(num):
    elias_num = str(bin(num)[2:])[::-1]
    code = elias_num
    # repeat until length == 1, changing the lead number to 0 at every loop
    while num != 1:
        num = len(code) - 1
        code = str(bin(num)[2:])
        code = '0' + code[1:]
        elias_num += code[::-1]

    elias_num = elias_num[::-1]
    return elias_num


def main():
    # get arguments

    if len(sys.argv) == 4:
        txt_file = open(sys.argv[1], 'r')
        string = txt_file.read()
        txt_file.close()
        window = int(sys.argv[2])
        lookahead = int(sys.argv[3])

    lzss = LZSS(window, lookahead)

    # list of LZSS encoded values
    encoded_list = lzss.encode(string)

    ascii_c = [0] * 128     # list for counting frequency
    heap = []   # list for holding all the nodes

    # get frequency values
    for c in string:
        ascii_c[ord(c)] += 1

    # get characters used and create a node and push into a heap
    for i in range(len(ascii_c)):
        if ascii_c[i] != 0:
            new_node = Node(chr(i), ascii_c[i])
            heapq.heappush(heap, new_node)

    # get unique_chars and reverse
    unique_chars = elias_code(len(heap))
    ascii_list = huffman(heap)      # list of all the huffman ascii values

    # Create the header
    header = unique_chars
    for i in range(len(ascii_list)):
        if ascii_list[i] != '':
            # ascii code of unique char
            binary = bin(i)
            binary = binary[:1] + binary[2:]
            elias_length = elias_code(len(ascii_list[i]))
            header += str(binary) + elias_length + ascii_list[i][::-1]

    # Create the data part
    data = elias_code(len(encoded_list))
    for i in range(len(encoded_list)):
        if len(encoded_list[i]) == 2:
            ascii_val = ascii_list[ord(encoded_list[i][1])]
            ascii_val = ascii_val[::-1]     # get reversed value
            data += str(encoded_list[i][0]) + ascii_val
        else:
            data += str(encoded_list[i][0]) + elias_code(encoded_list[i][1]) + elias_code(encoded_list[i][2])

    # concatenate header and data
    encoded_code = header + data

    file = open("output_lzss_encoder.bin", "w+")
    file.write(encoded_code)
    file.close()

main()