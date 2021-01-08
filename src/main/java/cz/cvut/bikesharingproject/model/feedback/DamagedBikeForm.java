package cz.cvut.bikesharingproject.model.feedback;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.cvut.bikesharingproject.model.Bike;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DAMAGED_BIKES")
@DiscriminatorValue("DAMAGED_BIKE")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class DamagedBikeForm extends CustomerFeedbackForm {

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;
}
