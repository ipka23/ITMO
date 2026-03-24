overflow_error_value = 0xCCCCCCCC


def cstr():
    pass


def read_line():
    pass


def text_word_counter(input):
    """Count word frequencies in text with max word length of 3 symbols.

    Separators: space, comma, dot
    Max word length: 3 symbols
    Max total unique words: 12
    Output: counts in order of first appearance

    Examples:
    - "a bb ccc a ccc a" -> "3 1 2" (a appears 3 times, bb once, ccc twice)
    - "word" -> return -1 (word too long)
    - More than 12 unique words -> return -1

    - Result string should be represented as a correct C string.
    - Buffer size for the result -- `0x40`, starts from `0x00`.
    - End of input -- new line.
    - Initial buffer values -- `_`.

    Python example args:
        input (str): The input string containing text to analyze.

    Returns:
        tuple: A tuple containing the word counts and the remaining input.
    """
    line, rest = read_line(input, 0x40)
    if line is None:
        return [overflow_error_value], rest

    if not line:
        return "", rest

    try:
        # Split text by separators (space, comma, dot)
        words = []
        current_word = ""

        for char in line:
            if char in " ,.":
                if current_word:
                    words.append(current_word)
                    current_word = ""
            else:
                current_word += char

        # Add last word if exists
        if current_word:
            words.append(current_word)

        # Check for words longer than 3 symbols
        for word in words:
            if len(word) > 3:
                return [-1], rest

        # Count words in order of first appearance
        word_order = []
        word_counts = {}

        for word in words:
            if word not in word_counts:
                word_order.append(word)
                word_counts[word] = 0
                # Check if we exceed 12 unique words
                if len(word_order) > 12:
                    return [-1], rest
            word_counts[word] += 1

        # Build result string
        if not word_order:
            result = ""
        else:
            counts = [str(word_counts[word]) for word in word_order]
            result = " ".join(counts)

        if len(result) + 1 > 0x40:  # +1 for null terminator
            return [overflow_error_value], rest

        return cstr(result, 0x40)[0], rest

    except Exception:
        return [-1], rest


assert text_word_counter('a bb ccc a ccc a\n') == ('3 1 2', '')
assert text_word_counter('cat dog cat\n') == ('2 1', '')
assert text_word_counter('a,b.c a\n') == ('2 1 1', '')
