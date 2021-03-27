# Function has O(NP) time complexity as the outer loop iterates P amount of times where P
# is the price limit and within that loop another loop iterates over the amount of products N.
# It also has O(N + P) space complexity.

def solve_task1(product_list, price_limit):
    Memo = [0] * (price_limit+1)

    # This list will hold all the products for each price limit
    products_lists = []

    for i in range(price_limit + 1):
        products_lists.append([])

    # loop from 0 to price limit used to find max val at every iteration
    # for a given price limit which is then stored in Memo
    for price in range(price_limit + 1):

        max_val = 0
        # array used to hold the items for the current price
        current_items = []
        # loop that iterates from 0 to len(p_price) which will be used to
        # iterate through products
        for i in range(len(product_list)):

            if product_list[i][2] <= price:
                # getting new value
                this_val = product_list[i][3] + Memo[price - product_list[i][2]]

                if this_val > max_val:
                    max_val = this_val

                    current_items = [product_list[i][0]]

                    if products_lists[price - product_list[i][2]] != []:
                        current_items = current_items + products_lists[price - product_list[i][2]]

        Memo[price] = max_val

        products_lists[price] = current_items


    # turns the lists of products for the price limit into the required format of to_sell
    # which is required in order to call the function print_solution(to_sell, product_list)
    # done by going through products_lists at the index of the price limit
    # then checks to_sell if the product is listed in there, if not it will assign iterator j
    # to the variable index which is used to append the product to the to_sell list
    to_sell = []

    for i in range(len(products_lists[price_limit])):
        # loop to convert products_lists to the correct format set by to_sell
        # start by setting the first element if to_sell is empty
        if len(to_sell) == 0:
            to_sell.append([1, products_lists[price_limit][i]])

        else:
            index = None
            j = 0

            # have a loop that runs and matches elements from products_lists and to_sell
            # in order to be able to increment the product count
            while j < len(to_sell):
                if products_lists[price_limit][i] == to_sell[j][1]:
                    index = j
                j += 1

            # check if index was found, if it wasn't found product will be appended.

            # if product not in to_sell list, it will be appended to it and the count will be set to 1
            if index is None:
                to_sell.append([1, products_lists[price_limit][i]])

            else:
                to_sell[index][0] += 1

    print_solution(to_sell, product_list)


def solve_task2(product_list, price_limit, item_limit):
    # Create a list of lists for Memo element 0 for pricelimit and element 1 for item_limit
    Memo = [[0 for x in range(price_limit + 1)] for x in range(item_limit + 1)]

    # This list will hold all the products for each price limit
    products_lists = []
    # Create products list of lists
    for i in range(item_limit + 1):
        products_lists.append([])
        for j in range(price_limit + 1):
            products_lists[i].append([0])

    # Create loop for item_limit
    for item in range(1, item_limit + 1):

        for price in range(1, price_limit + 1):
            maxVal = 0
            thisVal = 0
            current_items = [0]

            for i in range(0, len(product_list)):

                if product_list[i][2] <= price:

                    if price - product_list[i][2] < 0:
                        thisVal = product_list[i][3]

                    else:
                        thisVal = product_list[i][3] + Memo[item - 1][price - product_list[i][2]]

                if thisVal > maxVal:
                    maxVal = thisVal
                    current_items = [product_list[i][0]]

                    if products_lists[item][price - product_list[i][2]] != [0]:
                        current_items = current_items + products_lists[item - 1][price - product_list[i][2]]

            Memo[item][price] = maxVal
            products_lists[item][price] = current_items

    # this loop is used to find the correct sub group of elements
    item_limit_lists = []
    for i in products_lists:
        for j in i:
            if len(j) == item_limit:
                item_limit_lists = j


    # description in task 1
    to_sell = []
    for i in range(len(item_limit_lists)):
        if len(to_sell) == 0:
            to_sell.append([1, item_limit_lists[i]])

        else:
            index = None
            j = 0

            while j < len(to_sell):
                if item_limit_lists[i] == to_sell[j][1]:
                    index = j
                j += 1

            # check if index was found, if it wasn't found product will be appended.

            if index is None:
                to_sell.append([1, item_limit_lists[i]])

            else:
                to_sell[index][0] += 1

    print_solution(to_sell, product_list)



#####################################################
# DO NOT CHANGE/WRITE ANYTHING BELOW THIS LINE
#####################################################

# This function prints the results in the required format.
# to_sell is a list of lists representing the products to be sold. Each item in to_sell is a list where item[1] is the product ID and item[0] is the quanity of the product.
def print_solution(to_sell,product_list):
    total_items, total_price, total_profit = 0, 0, 0
    for value in to_sell:
        quantity = value[0]
        product = product_list[value[1]]
        print(str(quantity)+" X ["+str(value[1])+":"+product[1]+":"+str(product[2])+":"+str(product[3])+"]")
        total_items += quantity
        total_price += product[2]*quantity
        total_profit += product[3]*quantity
        
    print("Total items sold:",total_items)
    print("Total price of items sold:",total_price)
    print("Total profit of items sold:",total_profit)


input_file = open("products.txt")
product_list = []

i=0
for line in input_file:
    line = line.strip()
    line = line.split(":")
    product_list.append([i,line[1],int(line[2]),int(line[3])])
    i+=1

    
price_limit = int(input("Enter the price limit: "))
print() # the sample solution shown in assignment sheet doesn't have an extra line. But don't worry about it. This is required for the tester. Do not remove this.
item_limit = input("Enter the item limit: ")
print()


if item_limit == "infinity":
    solve_task1(product_list,price_limit)
else:
    try:
        item_limit = int(item_limit)
        solve_task2(product_list,price_limit,item_limit)
    except ValueError:
        print("You must either enter an integer OR infinity")
        
    

