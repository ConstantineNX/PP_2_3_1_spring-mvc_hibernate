package model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "phone"})
})
//@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_Name")
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    private String lastName;

    @Column(name = "age")
    @Min(value = 0, message = "возраст не может быть отрицательным или 0")
    @Max(value = 130, message = "возраст не может превышать 130 лет")
    private Byte age;

    @Column(name = "city")
    private String city;


    @Column(name = "email", nullable = false, length = 50)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {}

    public User(String firstName, String lastName, Byte age, String city, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.truncatedTo(ChronoUnit.SECONDS);
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt != null ? updatedAt.truncatedTo(ChronoUnit.SECONDS) : null;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class <?> thisClass = this instanceof HibernateProxy
                ? ((HibernateProxy)this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        Class <?> thatClass = o instanceof HibernateProxy
                ? ((HibernateProxy)o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        if (thisClass != thatClass) return false;
        User user = (User) o;
        if (getId() != null && user.getId() != null)
            return Objects.equals(getId(), user.getId());
        else {
            return Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPhone(), user.getPhone());
        }
    }

    @Override
    public final int hashCode() {
        if (getId() != null)
            return Objects.hashCode(getId());
        else {
            return Objects.hash(email, phone);
        }
    }
}
