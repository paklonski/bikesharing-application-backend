package cz.cvut.bikesharingproject.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
public class Trip extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean isClosed;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime startRentalTime;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime finalRentalTime;

    // TODO. Change double to Duration, columnDefinition = "interval".
    @Basic(optional = false)
    @Column(nullable = false)
    private double duration;

    @Basic(optional = false)
    @Column(nullable = false)
    private double totalFee;

    // TODO. Add nullable = false.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "trip")
    private List<CustomerSupportForm> supportForms;

    // TODO. Add nullable = false
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id")
    private Bike bike;
}
