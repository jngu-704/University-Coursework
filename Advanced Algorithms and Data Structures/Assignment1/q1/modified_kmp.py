# Johnson Nguyen 27860396
"""This script is completed with a time complexity of linear time O(M + N)
where M is the size of the text and N is the size of the pattern using KMP.

"""
import sys


def z_algorithm(string, Z):
    # Z Algorithm
    left = 0
    right = 0
    for k in range(1, len(string)):

        # Case 1
        if k > right:
            q = k
            while q < len(string) - 1 and string[q] == string[q - k]:
                q += 1
            Z[k] = q - k
            if Z[k] > 0:
                right = q - 1
                left = k

        # Case 2
        else:
            # Case 2A
            if Z[k - left] < right - k + 1:
                Z[k] = Z[k - left]

            # Case 2B
            else:
                while q < len(string) and string[q-k] == string[q]:
                    q += 1
                Z[k] = q - k


# This function gets the SP Values
def get_SP(string, Z, SP):
    for j in range(len(string) - 1, 0, -1):
        i = j + Z[j] - 1
        SP[i] = Z[j]


def kmp(txt, pat, SP):
    m = len(pat)
    n = len(txt)
    i = 0
    j = 0
    while j < len(txt):
        if pat[i] == txt[j]:
            i += 1
            j += 1


# the driver function
def modified_kmp(txt, pat):
    Z = [0] * (len(pat))
    z_algorithm(pat, Z)

    SP = [0] * len(pat)
    get_SP(pat, Z, SP)
    kmp(txt, pat, SP)


    if len(sys.argv) == 3:
        txt_file = open(sys.argv[1], 'r')
        txt = txt_file.read()
        txt_file.close()

        pat_file = open(sys.argv[2], 'r')
        pat = pat_file.read()
        pat_file.close()

        modified_kmp(txt, pat)














