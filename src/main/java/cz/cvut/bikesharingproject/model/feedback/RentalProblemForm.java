package cz.cvut.bikesharingproject.model.feedback;

import cz.cvut.bikesharingproject.model.Trip;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RENTAL_PROBLEMS")
@DiscriminatorValue("RENTAL_PROBLEM")
public class RentalProblemForm extends CustomerFeedbackForm {

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}
