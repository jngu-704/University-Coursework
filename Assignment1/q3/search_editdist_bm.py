# Johnson Nguyen 27860396
"""This script outputs matches with an edit distance <= 1. This is done by calculating the Z values
 and the reverse Z values which is done by reversing the txt and patttern and then searches for matches.
 The two resulting Z lists are then join together and incremented through to find all the matches.
 This is done by adding up the matches ensuring that they are within the length of pat in distance.
 If the values add up to the length of pat or length of pat minus one, it is a match and outputted.
 This script is completed with a time complexity of linear time O(M + N) where M is the size of the
 text and N is the size of the pattern using the Z-Algorithm.

"""
import sys


# Z Algorithm
def z_algorithm(pat, txt, Z):
    string = pat + "$" + txt
    z_pos = len(pat) + 1

    left = 0
    right = 0
    q = 0

    for k in range(z_pos, len(string)):
        # Case 1
        if k > right:
            # print("case 1")
            q = k
            while q < len(string) - 1 and string[q] == string[q - k]:
                q += 1
            Z[k - z_pos] += q - k

            if Z[k - z_pos] > 0:
                right = q - 1
                left = k

        # Case 2
        else:
            # print("case 2a")
            # Case 2A
            if Z[k - z_pos - left] < right - k + 1:
                Z[k - z_pos] += Z[k - left - z_pos]

            # Case 2B
            else:
                # print("case 2b")
                while q < len(string) - 1 and string[q - k] == string[q]:
                    q += 1
                q -= 1
                Z[k - z_pos] += q - k
                right = q - 1
                left = k


# This function is used to get the output from the Z values
def get_output(Z, pat):
    counter = 0
    matches = 0
    initial_index = 0
    prev_val = 0

    # This loop will run M times where M is the length of the txt
    for i in range(len(Z)):
        if initial_index != -1:
            counter += 1

        # check if a perfect match is found if so skip next match which will also be a
        # perfect match as there will be a duplicate due to joining the two Z lists
        if Z[i] == len(pat) and prev_val == len(pat):
            matches = 0
            counter = 0
            initial_index = -1

        else:
            if Z[i] > 0:
                if initial_index == -1:
                    initial_index = i
                    counter += 1
                    matches += Z[i]
                else:
                    matches += Z[i]
                prev_val = Z[i]

            # print out the match index and edit distance
            if counter == len(pat) or matches >= len(pat) - 1:
                # if Z[i] is a perfect match where the value equals to length of pat
                if prev_val == len(pat):
                    print(initial_index + 1, 0)
                    matches = 0
                    counter = 0
                    initial_index = -1

                # else if run this for other values where an edit distance of one was needed.
                elif matches >= len(pat) - 1:
                    print(initial_index + 1, 1)
                    matches = 0
                    counter = 0
                    initial_index = -1


# this is the driver function
def editdist(txt, pat):
    # reverse the txt and pat
    r_txt = txt[::-1]
    r_pat = pat[::-1]

    Z = [0] * (len(txt))
    # calculate the Z lists and join them
    z_algorithm(r_pat, r_txt, Z)
    Z.reverse()
    z_algorithm(pat, txt, Z)
    # get the output using the New Z list
    get_output(Z, pat)


if len(sys.argv) == 3:
    txt_file = open(sys.argv[1], 'r')
    txt = txt_file.read()
    txt_file.close()

    pat_file = open(sys.argv[2], 'r')
    pat = pat_file.read()
    pat_file.close()

    editdist(txt, pat)





