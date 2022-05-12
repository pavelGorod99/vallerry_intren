package peopledb.model;

import peopledb.annotation.Id;

import java.util.Objects;

public final class Address {
    @Id
    private final Long ID;
    private final String streetAddress;
    private final String address2;
    private final String city;
    private final String state;
    private final String postcode;
    private final String country;
    private final String county;
    private final Region region;

    public Address(@Id Long ID, String streetAddress, String address2, String city, String state, String postcode,
                   String country,
                   String county, Region region) {
        this.ID = ID;
        this.streetAddress = streetAddress;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.country = country;
        this.county = county;
        this.region = region;
    }

    @Id
    public Long ID() {
        return ID;
    }

    public String streetAddress() {
        return streetAddress;
    }

    public String address2() {
        return address2;
    }

    public String city() {
        return city;
    }

    public String state() {
        return state;
    }

    public String postcode() {
        return postcode;
    }

    public String country() {
        return country;
    }

    public String county() {
        return county;
    }

    public Region region() {
        return region;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Address) obj;
        return Objects.equals(this.ID, that.ID) &&
                Objects.equals(this.streetAddress, that.streetAddress) &&
                Objects.equals(this.address2, that.address2) &&
                Objects.equals(this.city, that.city) &&
                Objects.equals(this.state, that.state) &&
                Objects.equals(this.postcode, that.postcode) &&
                Objects.equals(this.country, that.country) &&
                Objects.equals(this.county, that.county) &&
                Objects.equals(this.region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, streetAddress, address2, city, state, postcode, country, county, region);
    }

    @Override
    public String toString() {
        return "Address[" +
                "ID=" + ID + ", " +
                "streetAddress=" + streetAddress + ", " +
                "address2=" + address2 + ", " +
                "city=" + city + ", " +
                "state=" + state + ", " +
                "postcode=" + postcode + ", " +
                "country=" + country + ", " +
                "county=" + county + ", " +
                "region=" + region + ']';
    }

}
