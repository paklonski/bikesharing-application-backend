package cz.cvut.bikesharingproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = "Bike.findRent", query = "SELECT b FROM Bike b WHERE b.isRemoved = false AND b.isRent = true")
})
public class Bike extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String brand;

    @Basic(optional = false)
    @Column(nullable = false)
    private String model;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean isRent;

    @Basic(optional = false)
    @Column(nullable = false)
    private double pricePerMinute;

    // TODO. Change double to Duration, columnDefinition = "interval".
    @Basic(optional = false)
    @Column(nullable = false)
    private double totalRentalDuration;

    // TODO. Cascade Types.
    @ToString.Exclude
    @JsonBackReference(value = "bike_station")
    @ManyToOne
    @JoinColumn(name = "station_id")
    private ParkingStation currentParkingStation;

    @OneToMany(mappedBy = "bike")
    private List<Trip> tripHistory;

    @OneToMany(mappedBy = "bike")
    private List<CustomerSupportForm> supportFormsHistory;
}
