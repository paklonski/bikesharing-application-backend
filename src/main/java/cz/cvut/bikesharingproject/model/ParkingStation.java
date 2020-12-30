package cz.cvut.bikesharingproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ParkingStation extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer capacity;

    // TODO. Cascade Types.
    @ToString.Exclude
    @JsonManagedReference(value = "bike_station")
    @OneToMany(mappedBy = "currentParkingStation")
    private List<Bike> currentBikes;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
}
