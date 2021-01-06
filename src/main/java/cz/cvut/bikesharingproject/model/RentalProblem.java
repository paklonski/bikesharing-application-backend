package cz.cvut.bikesharingproject.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "RENTAL_PROBLEMS")
public class RentalProblem extends CustomerSupportForm {

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
