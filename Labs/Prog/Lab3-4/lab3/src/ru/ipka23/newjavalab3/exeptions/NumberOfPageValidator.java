package ru.ipka23.newjavalab3.exeptions;

public class NumberOfPageValidator {
    public void validatePageNumber(int numberOfPage) throws InvalidNumberOfPage {
        if (numberOfPage <= 0) {
            throw new InvalidNumberOfPage("Номер " + numberOfPage +  " для страницы недопустим. Номер страницы может быть только натуральным числом.");
        }
    }
}
