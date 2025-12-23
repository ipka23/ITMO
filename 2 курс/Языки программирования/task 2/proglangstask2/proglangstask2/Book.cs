namespace proglangstask2;

class Book
{
    public string Title { get; set; }
    public string Author{ get; set; }
    public string Annot{ get; set; }
    public string Isbn{ get; set; }
    public string Date{ get; set; }

    public Book(string title, string author, string annot, string isbn, string date)
    {
        Title = title;
        Author = author;
        Annot = annot;
        Isbn = isbn;
        Date = date;
    }
}