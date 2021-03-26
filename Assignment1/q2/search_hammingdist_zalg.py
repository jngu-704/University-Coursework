#Johnson Nguyen 27860396
"""This script output matches within a Hamming distance <= 1. This is 
done by using a modded Z-algorithm which is modded so that it allows 
for one mismatch while matching the txt with the pattern. This algorithm 
has a time complexity O(M + N)where M is the size of the text and N 
is the size of the pattern using the Z-Algorithm.

"""

import sys


# Z Algorithm
def z_algorithm(txt, pat, Z):
    string = pat + "$" + txt
    z_pos = len(pat) + 1
    left = 0
    right = 0
    q = 0

    for k in range(z_pos, len(string)):
        mismatch = 0
        # Case 1
        if k > right:
            q = k
            count = 0
            # The mismatch mod that allows for one mismatch while searching
            while mismatch <= 1 and q < len(string) and count <= len(pat):
                if string[q] != string[q-k]:
                    if mismatch < 1:
                        q += 1
                    mismatch += 1
                elif string[q] == string[q-k]:
                    q += 1
                    count += 1
            q -= 1
            Z[k - z_pos] = q - k

            if Z[k - z_pos] > 0:
                right = q - 1
                left = k

        # Case 2
        else:
            # Case 2A
            if Z[k - z_pos - left] < right - k + 1:
                Z[k - z_pos] = Z[k - left - z_pos]

            # Case 2B
            else:
                count = 0
                # The mismatch mod that allows for one mismatch while searching
                while mismatch <= 1 and q < len(string) and count < len(pat):
                    if string[q] != string[q - k]:
                        if mismatch < 1:
                            q += 1
                        mismatch += 1

                    elif string[q] == string[q - k]:
                        q += 1
                        count += 1

                Z[k - z_pos] = q - k
                right = q - 1
                left = k


def get_output(pat, Z):
    # get output
    for i in range(len(Z)):
        if Z[i] >= len(pat) - 1:
            print(i + 1, len(pat) - Z[i])


def hammingdist(txt, pat):
    Z = [0] * (len(txt))
    z_algorithm(txt, pat, Z)
    get_output(pat, Z)


if len(sys.argv) == 3:
    txt_file = open(sys.argv[1], 'r')
    txt = txt_file.read()
    txt_file.close()

    pat_file = open(sys.argv[2], 'r')
    pat = pat_file.read()
    pat_file.close()

    hammingdist(txt, pat)