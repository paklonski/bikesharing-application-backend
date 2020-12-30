package cz.cvut.bikesharingproject.model;

import cz.cvut.bikesharingproject.model.enums.CityDistrict;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Location extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private double latitude;

    @Basic(optional = false)
    @Column(nullable = false)
    private double longitude;

    @Enumerated(EnumType.STRING)
    private CityDistrict cityDistrict;
}
