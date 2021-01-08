package cz.cvut.bikesharingproject.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "PARKING_STATIONS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@EqualsAndHashCode(callSuper = false, exclude={"currentBikes"})
public class ParkingStation extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer capacity;

    @ToString.Exclude
    @OneToMany(mappedBy = "currentParkingStation")
    private Set<Bike> currentBikes;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Location location;
}
