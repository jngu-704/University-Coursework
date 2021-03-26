# Johnson Nguyen 27860392

import sys

class Node:
    def __init__(self, bit):
        self.char = None    # holds the char
        self.bit = bit    # holds the bits
        self.left_child = None
        self.right_child = None

    def set_char(self, c):
        self.char = c

def elias_decode(codeword):
    read_len = 1
    pos = 1
    component = ''
    # code implementation based on lecture 9 notes
    while len(component) == 0 or component[0] != '1':
        component = codeword[pos - 1:pos + read_len - 1]
        if(component[0] != '1'):
            decoded = component.replace('0', '1', 1)
            pos = pos + read_len
            read_len = int(decoded, 2) + 1

    length = int(component, 2)
    index = pos + read_len - 1

    return length, index


class Huffman_Decode:
    def __init__(self):
        self.root_node = Node(None)


    def add_list(self,a_list):

        while a_list:
            current_node = self.root_node

            code, char = a_list.pop()
            # create node in correct space for new char
            for bit in code:
                if bit == '0':
                    if current_node.left_child is None:
                        current_node.left_child = Node('0')
                    current_node = current_node.left_child

                else:
                    if current_node.right_child is None:
                        current_node.right_child = Node('1')
                    current_node = current_node.right_child

            current_node.char = char

    def return_tree(self):
        return self.root_node



if len(sys.argv) == 2:
    txt_file = open(sys.argv[1], 'r')
    encoded = txt_file.read()
    txt_file.close()


#encoded = '01101100001110110001001000011000110100100011111111010011000100100001101111'

# get number of unique characters
no_unique_c, index = elias_decode(encoded)
encoded = encoded[index:]

huffman_list = []

# get all unique characters and their huffman code
for i in range(no_unique_c):

    # get 8 bits of ascii binary
    ascii_binary = encoded[:8]
    # remove those 8 bits used for ascii
    encoded = encoded[8:]

    # get dec value of ascii binary
    ascii_value = int(ascii_binary, 2)

    # get elias decoded huffman len
    huffman_len, index = elias_decode(encoded)
    # remove decoded bits
    encoded = encoded[index:]

    # get huffman_codeword
    huffman_codeword = encoded[:huffman_len]
    # remove decoded bits
    encoded = encoded[huffman_len:]

    # add huffman codeword to the correct ascii cell for each unique char
    huffman_list.append((huffman_codeword, chr(ascii_value)))


huffman_decode = Huffman_Decode()
# insert values into tree
huffman_decode.add_list(huffman_list)

# get huffman root
huffman_root = huffman_decode.return_tree()

# list that holds all decoded values
decoded_txt = ''

# get format length
format_len, index = elias_decode(encoded)
encoded = encoded[index:]


for i in range(format_len):
    # format 1
    if encoded[0] == '1':
        char = None
        i = 1
        current_node = huffman_root
        # decodes character
        while char is None:

            if encoded[i] == '0':
                current_node = current_node.left_child
            else:
                current_node = current_node.right_child

            if current_node.char is not None:
                char = current_node.char
            else:
                i += 1

        decoded_txt += char
        encoded = encoded[i+1:]

    # format 0
    else:
        encoded = encoded[1:]

        offset, index = elias_decode(encoded)
        encoded = encoded[index:]

        length, index2 = elias_decode(encoded)
        encoded = encoded[index2:]

        offset_index = len(decoded_txt) - offset

        while length > 0:
            decoded_txt += decoded_txt[offset_index]
            offset_index += 1
            length -= 1

file = open("output_lzss_decoder.txt", "w+")
file.write(decoded_txt)
file.close()


