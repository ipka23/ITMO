public class Read extends Action {
    private Neznayka neznayka; // Добавлено поле для хранения объекта Neznayka
    private Book book;

    public Read(Neznayka neznayka, Book book) {
        this.neznayka = neznayka; // Сохраняем объект Neznayka
        this.book = book;
    }

    @Override
    public String doSomething() {
        return neznayka.getName() + " читает " + book.getTitle(); // Используем методы getName() и getTitle()
    }
}