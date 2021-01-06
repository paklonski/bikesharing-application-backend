package cz.cvut.bikesharingproject.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "DAMAGED_BIKES")
public class DamagedBike extends CustomerSupportForm {

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;
}
