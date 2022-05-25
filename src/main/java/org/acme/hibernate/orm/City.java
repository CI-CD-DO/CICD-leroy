package org.acme.hibernate.orm;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "known_city")
@NamedQuery(name = "City.findAll", query = "SELECT f FROM City f ORDER BY f.name", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Cacheable
public class City {

    @Id
    @SequenceGenerator(name = "citySequence", sequenceName = "known_city_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "citySequence")
    private Integer id;

    @Column(length = 40, unique = true)
    private String name;

    private String department_code;
    private String insee_code;
    private String zip_code;
    private double lat;
    private double lon;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public String getInsee_code() {
        return insee_code;
    }

    public void setInsee_code(String insee_code) {
        this.insee_code = insee_code;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}
