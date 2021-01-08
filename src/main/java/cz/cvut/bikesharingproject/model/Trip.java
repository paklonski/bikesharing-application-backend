package cz.cvut.bikesharingproject.model;

import com.fasterxml.jackson.annotation.*;
import cz.cvut.bikesharingproject.model.feedback.RentalProblemForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "TRIPS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@EqualsAndHashCode(callSuper = false, exclude={"rentalProblems"})
public class Trip extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean opened;

    @ManyToOne
    @JoinColumn(name = "startstation_id", nullable = false)
    private ParkingStation startParkingStation;

    @ManyToOne
    @JoinColumn(name = "finalstation_id")
    private ParkingStation finalParkingStation;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime startRentalTime;

    @Basic(optional = false)
    @Column
    private LocalDateTime finalRentalTime;

    @Basic(optional = false)
    @Column
    private Long durationInMinutes;

    @Basic(optional = false)
    @Column(columnDefinition = "NUMERIC(7,2)")
    private BigDecimal totalFee;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "trip")
    private Set<RentalProblemForm> rentalProblems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id", nullable = false)
    private Bike bike;
}
