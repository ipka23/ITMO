# import Element class, tostring function
# from xml.etree.ElementTree library
from xml.etree.ElementTree import Element, tostring


# define a function to
# convert a simple dictionary
# of key/value pairs into XML
def dict_to_xml(tag, d):
    elem = Element(tag)
    for key, val in d.items():
        # create an Element
        # class object
        child = Element(key)
        child.text = str(val)
        elem.append(child)

    return elem


# Driver Program
s = {'name': 'geeksforgeeks',
     'city': 'noida', 'stock': 920}

# e stores the element instance
e = dict_to_xml('company', s)

# Element instance is different
# every time you run the code
print(e)

# converting into a byte string
print(tostring(e))

# We can attach attributes
# to an element using
# set() method
e.set('_id', '1000')

print(tostring(e))
