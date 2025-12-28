    package persistence.entities;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.List;

    @Getter
    @Setter
    @NoArgsConstructor
    @Entity
    @Table(name = "users")
    public class UserEntity {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "login", nullable = false, unique = true)
        private String login;
        @Column(name = "password", nullable = false)
        private String password;

        public UserEntity(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }
