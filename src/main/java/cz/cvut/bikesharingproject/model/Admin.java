package cz.cvut.bikesharingproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Person {

    @ManyToMany(mappedBy = "responsiblePersons")
    private List<CustomerSupportForm> supportForms;
}
