package cz.cvut.bikesharingproject.model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Address extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer number;

    @Basic(optional = false)
    @Column(nullable = false)
    private String street;

    @Basic(optional = false)
    @Column(nullable = false)
    private String city;

    @Basic(optional = false)
    @Column(nullable = false)
    private String postcode;
}
