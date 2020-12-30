package cz.cvut.bikesharingproject.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("USER")
public class User extends Person {

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean isRentNow;

    @Basic(optional = false)
    @Column(nullable = false)
    private String creditCardNumber;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate creditCardExpirationDate;

    @OneToMany(mappedBy = "user")
    private List<CustomerSupportForm> supportFormsHistory;

    @OneToMany(mappedBy = "user")
    private List<Trip> rentalHistory;
}
