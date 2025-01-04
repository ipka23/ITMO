public class Address implements Validatable {
    private String street; //Длина строки не должна быть больше 198, Поле не может быть null
    private String zipCode; //Поле не может быть null
    private Location town; //Поле не может быть null

    public Address(String street, String zipCode, Location town) {
        this.street = street;
        this.zipCode = zipCode;
        this.town = town;
    }

    @Override
    public boolean validate() {
        if (street == null || street.length() > 198) return false;
        if (zipCode == null) return false;
        if (town == null) return false;
        return true;
    }
}