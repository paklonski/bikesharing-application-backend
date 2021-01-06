package cz.cvut.bikesharingproject.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "BIKES")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NamedQueries({
        @NamedQuery(name = "Bike.findAll", query = "SELECT b FROM Bike b WHERE b.enabled = true"),
        @NamedQuery(name = "Bike.findRent", query = "SELECT b FROM Bike b WHERE b.enabled = true AND b.rent = true"),
        @NamedQuery(name = "Bike.findFree", query = "SELECT b FROM Bike b WHERE b.enabled = true AND b.rent = false")
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
    private boolean rent;

    @Basic(optional = false)
    @Column(nullable = false, columnDefinition = "NUMERIC(7,2)")
    private BigDecimal pricePerMinute;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "station_id")
    private ParkingStation currentParkingStation;

    @ToString.Exclude
    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> tripHistory;

    @OneToMany(mappedBy = "bike")
    private List<DamagedBike> supportFormsHistory;
}
