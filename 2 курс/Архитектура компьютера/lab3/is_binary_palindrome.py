def is_binary_palindrome(n):
    """Check if the 32-bit binary representation of a number is a palindrome.

    Args:
        n (int): The integer to check.

    Returns:
        int: 1 if the binary representation is a palindrome, otherwise 0.
    """
    binary_str = f"{n:032b}"  # Convert to 32-bit binary string
    res = binary_str == binary_str[::-1]
    return 1 if res else 0


assert is_binary_palindrome(5) == 0
assert is_binary_palindrome(15) == 0
assert is_binary_palindrome(4026531855) == 1
assert is_binary_palindrome(3221225474) == 0
# numio[0x80]: [] >>> [101, 1111, 11110000000000000000000000001111, 11000000000000000000000000000010]
#