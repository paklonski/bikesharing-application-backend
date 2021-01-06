package cz.cvut.bikesharingproject.model;

import cz.cvut.bikesharingproject.model.enums.District;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "LOCATIONS")
public class Location extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private double latitude;

    @Basic(optional = false)
    @Column(nullable = false)
    private double longitude;

    @Basic(optional = false)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private District district;
}
