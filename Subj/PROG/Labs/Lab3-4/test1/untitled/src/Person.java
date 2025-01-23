public record Person (int age, String name, Sex sex) {
    public enum Sex {
        MALE ("Мужчина"),
        FEMALE ("Женщина");
        private final String sex;
        Sex(String sex){
            this.sex = sex;
        }
        public String getSex() {
            return sex;
        }
        @Override
        public String toString() {
            return sex;
        }
    }
}
