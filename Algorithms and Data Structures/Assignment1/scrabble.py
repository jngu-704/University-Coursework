# This function reads in a file and appends each line to an array
def read_file(array, filename):
    file = open(filename, 'r')
    for word in file:
        array.append(word.rstrip())
    file.close()

"""
This function is an implementation of Counting sort which converts letters into their ascii number
The ascii number is used to get the index number the array, done by minusing 97 with ascii number
Has O(N) time complexity
"""
def counting_sort(array, index):
    count = []
    for i in range(26):
        count.append([])
    output = [0]*len(array)

    for next in range(0, len(array)):
        if index is not None:
            number = ord(array[next][0][index]) - 97
            count[number].append(array[next])
        else:
            number = ord(array[next]) - 97
            count[number].append(array[next])

    index = 0
    for length in count:
        if length != []:
            for j in length:
                output[index] = j
                index += 1
    return output


# function that finds the max length word O(N) time complexity
def max_length(array):
    max = len(array[0])
    for index in range(len(array)):
        if len(array[index][0]) > max:
            max = len(array[index][0])
    return max


# function that create groups by finding max and appending empty list
def length_groups(array):
    groups = []
    max = max_length(array)
    for i in range(max):
        groups.append([])
    return groups


"""
Radix sort that uses stable counting sort to sort elements
This function will first split the dictionary into groups with the same length word
Then it will call counting sort to sort these words
This function has O(MN) time complexity as it sorts each character O(M) using counting sort which is O(N)
"""
def radix_sort(array):
    groups = length_groups(array)

    for i in range(len(array)):
        groups[len(array[i][0]) - 1].append(array[i])

    for group in range(len(groups)):
        if len(groups[group]) > 1:
            for column in range(-1, (group * -1) - 2, -1):
                groups[group] = counting_sort(groups[group], column)
    return groups


# This function is used to sort characters in a word using counting sort therefore takes O(MN)
def sort_characters(array):
    sorted_array = []
    for word in array:
        sorted = counting_sort(word, None)
        sorted_array.append(''.join(sorted))
    return sorted_array


# This function joins two arrays together by appending same element index together
def append_array(array1, array2):
    output = []
    for i in range(len(array1)):
        output.append([array1[i], array2[i]])
    return output


"""
TASK 1
This function finds the largest Anagram by searching through a sorted list and counting the adjacent
elements until the next word adjacent to current word does not match then it will record the results
O(MN) Time complexity
"""
def largestAnagram(array):
    largest_count = 0
    group = 0
    index_end = 0

    for i in range(len(array)):
        if array[i]:
            counter = 1
            current_word = array[i][0][0]

            for j in range(len(array[i]) - 1):
                # checks if the words match
                if array[i][j+1][0] == current_word:
                    counter += 1
                else:
                    if counter > largest_count:
                        largest_count = counter
                        index_end = j
                        group = i
                    counter = 1
                    current_word = array[i][j][0]

    # splicing the array using the index recorded for longest anagram found
    largest_anagram = array[group][index_end-largest_count:index_end+1]
    for i in range(len(largest_anagram)):
        largest_anagram[i] = largest_anagram[i][1]

    print("The largest group of anagrams: " + ' '.join(largest_anagram))


# Binary Search from FIT1045 modified to output first occurence
def binary_search(array, key):
    low = 0
    high = len(array) - 1

    while low <= high:
        mid = (low + high)//2
        if array[mid] < key:
            low = mid + 1
        elif array[mid] > key:
            high = mid - 1
        elif low != mid:
            high = mid
        else:
            return mid

    return False


"""
TASK 2
This function is used to find anagram of a given query, done by sorting the characters in the query
then the new sorted query is matched with an array of words (sorted by character) with the same length.
When matches are found using a modified binary search that returns first occurence, the index is matched 
up with the original words and then appended to words list.
O(klogN + W) time complexity where W is the output size, log N is the binary search and K is the query length
"""
def getScrabbleWords(array, query, wild):
    query_sorted = ''.join(counting_sort(query, None))
    query_length = len(query_sorted)
    sorted_group, group = find_group(array, query_length)
    query_index = binary_search(sorted_group, query_sorted)
    words = []
    wildcard = wild

    if query_index is not None:
        # loop to find anagrams
        while sorted_group[query_index] == query_sorted:
            if group[query_index] != query or wildcard is True:
                words.append(group[query_index])
            query_index += 1

    return words


#This function finds the group that the query belongs to and returns the groups
def find_group(array, query_length):
    sorted_group = []
    group = []
    for i in array[query_length - 1]:
        sorted_group.append(i[0])
        group.append(i[1])
    return sorted_group, group


"""
TASK 3
This function is used to return all the words in the dictionary that can be made up with letters plus 
one wildcard tile. This was achieved by creating an array of the alphabet and adding each letter it to 
the query word and sorting this query word and matching it up with other words using getScrabbleWords()
O(klogN + W) time complexity where W is the output size, log N is the binary search and K is the query length
"""
def getWildcardWords(array, query):
    wild_cards = []
    az = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
          'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
          'u', 'v', 'w', 'x', 'y', 'z']

    for i in az:
        wild_cards.extend(getScrabbleWords(array, query + i, True))

    wild_cards.sort()
    return wild_cards


# main function that is the driver
def main():

    L = []
    read_file(L, "Dictionary.txt")
    sorted_L = sort_characters(L)
    sorted_pair = append_array(sorted_L, L)
    groups = radix_sort(sorted_pair)
    largestAnagram(groups)

    notfinish = True
    while notfinish:
        query = input("\nEnter the query string: ")
        if query == "***":
            notfinish = False
        else:
            non_wild = getScrabbleWords(groups, query, False)
            wild_cards = getWildcardWords(groups, query)

            print("\nWords without using a wildcard: " + ' '.join(non_wild))
            print("Words using a wildcard: " + ' '.join(wild_cards))


main()


