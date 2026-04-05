import itertools

overflow_error_value = 0xCCCCCCCC


def read_line(s, buf_size):
    """Read line from input with buffer size limits."""
    assert "\n" in s, "input should have a newline character"
    line = "".join(itertools.takewhile(lambda x: x != "\n", s))

    if len(line) > buf_size - 1:
        return None, s[buf_size:]

    return line, s[len(line) + 1:]


def hello_user_pstr(input):
    """Greet the user with Pascal string: ask the name and greet by `Hello, <name>!` message.

    - Result string with greet message should be represented as a correct Pascal string.
    - Buffer size for the message -- `0x20`, starts from `0x00`.
    - End of input -- new line.
    - Initial buffer values -- `_`.

    Python example args:
        input (str): The input string containing the user's name.

    Returns:
        tuple: A tuple containing the greeting message and the remaining input.
    """
    line, rest = read_line(input, 0x20 - len("Hello, " + "!") - 1)

    q = "What is your name?\n"
    if not line:
        return [q, overflow_error_value], rest

    greet = "Hello, " + line + "!"
    return q + greet, rest


assert hello_user_pstr('Alice\n') == ('What is your name?\nHello, Alice!', '')
# and mem[0..31]: 0d 48 65 6c 6c 6f 2c 20 41 6c 69 63 65 21 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f
assert hello_user_pstr('Bob\n') == ('What is your name?\nHello, Bob!', '')
# and mem[0..31]: 0b 48 65 6c 6c 6f 2c 20 42 6f 62 21 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f 5f
