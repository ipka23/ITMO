using Newtonsoft.Json;
using proglangstask2;
using JsonSerializer = System.Text.Json.JsonSerializer;

class Program
{
    static void Main(string[] args)
    {
        RunLibraryCatalog();
    }

    static void RunLibraryCatalog()
    {
        string baseDirectory = AppDomain.CurrentDomain.BaseDirectory;
        string filePath = Path.Combine(baseDirectory, "bookCollection.json");
        LinkedList<Book> bookCollection;
        if (!File.Exists(filePath))
        {
            File.WriteAllText(filePath, "[]");
        }
        using (StreamReader r = new StreamReader(filePath))
        {
            string jsonString = r.ReadToEnd();
            bookCollection = JsonConvert.DeserializeObject<LinkedList<Book>>(jsonString);
            if (bookCollection == null) bookCollection = new LinkedList<Book>();
        }
        for (;;)
        {
            Console.Write("Enter the number of action and press [Enter]. Then follow instructions.\nMenu:\n1 Add book\n2 Get book by ISBN\n3 Search books\n4 Exit\n> ");
            string input = Console.ReadLine();
            if (input == "1")
            {
                string title;
                string author;
                string annot;
                string isbn;
                string date;
                for (;;)
                {
                    Console.Write("New book\nTitle: ");
                    title = Console.ReadLine();
                    if (!String.IsNullOrEmpty(title)) break;
                }

                for (;;)
                {
                    Console.Write("Author: ");
                    author = Console.ReadLine();
                    if (!String.IsNullOrEmpty(author)) break;
                }

                for (;;)
                {
                    Console.Write("Annotation: ");
                    annot = Console.ReadLine();
                    if (!String.IsNullOrEmpty(annot)) break;
                }

                for (;;)
                {
                    Console.Write("ISBN: ");
                    isbn = Console.ReadLine();
                    if (!String.IsNullOrEmpty(isbn)) break;
                }

                for (;;)
                {
                    Console.Write("Publication date: ");
                    date = Console.ReadLine();
                    if (!String.IsNullOrEmpty(date)) break;
                }

                Book b = new Book(title, author, annot, isbn, date);
                bookCollection.AddLast(b);
            }
            else if (input == "2")
            {
                string isbn;
                for (;;)
                {
                    Console.Write("Enter ISBN: ");
                    isbn = Console.ReadLine();
                    if (!String.IsNullOrEmpty(isbn)) break;
                }
                Book book = GetBookByISBN(bookCollection, isbn);
                if (book != null)
                {
                    Console.WriteLine("----------Result----------");
                    Console.WriteLine("Book information: ");
                    Console.WriteLine($"Title: {book.Title}");
                    Console.WriteLine($"Author: {book.Author}");
                    Console.WriteLine($"Annotation: {book.Annot}");
                    Console.WriteLine($"ISBN: {book.Isbn}");
                    Console.WriteLine($"Publication date: {book.Date}");
                }
                else
                {
                    Console.Write($"No book found with ISBN: {isbn}");
                }
            }
            else if (input == "3")
            {
                string request;
                for (;;)
                {
                    Console.Write("Request: ");
                    request = Console.ReadLine();
                    if (!String.IsNullOrEmpty(request)) break;
                }
                Console.WriteLine("Searching...");
                LinkedList<Book> matches = SearchBooksByKeyWord(bookCollection, request);
                int matchesLen = matches.Count;
                if (matchesLen == 0) {
                    Console.WriteLine($"No results for request: {request}!");
                }
                Console.WriteLine($"----------Results: {matchesLen}----------");
                LinkedList<Book>.Enumerator em = matches.GetEnumerator();
                int i = 0;
                while (em.MoveNext())
                {
                    i++;
                    Book b = em.Current;
                    Console.WriteLine($"#{i}");
                    Console.WriteLine("Book information:");
                    Console.WriteLine($"Title: {b.Title}");
                    Console.WriteLine($"Author: {b.Author}");
                    Console.WriteLine($"Annotation: {b.Annot}");
                    Console.WriteLine($"ISBN: {b.Isbn}");
                    Console.WriteLine($"Publication date: {b.Date}");
                }
            }
            else if (input == "4")
            {
                string jsonStr = JsonSerializer.Serialize(bookCollection);
                File.WriteAllText(filePath, jsonStr);
                Environment.Exit(0);
            }
            else {
                Console.WriteLine("Invalid input!");
            }

        }
    }

    static Book GetBookByISBN(LinkedList<Book> bookCollection, string isbn)
    {
        foreach (Book b in bookCollection)
        {
            if (String.Equals(b.Isbn, isbn)) return b;
        }
        return null;
    }

    static LinkedList<Book> SearchBooksByKeyWord(LinkedList<Book> collection, string keyWord)
    {
        LinkedList<Book> matchList = new LinkedList<Book>();
        foreach (Book b in collection)
        {
            bool match = b.Title.StartsWith(keyWord) || b.Author.StartsWith(keyWord) || b.Annot.StartsWith(keyWord) ||
                         b.Isbn.StartsWith(keyWord) || b.Date.StartsWith(keyWord);
            if (match) matchList.AddLast(b);
        }
        return matchList;
    }
}