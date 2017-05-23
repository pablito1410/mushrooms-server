package pl.polsl.mushrooms.application.model;


import pl.polsl.mushrooms.application.enums.Gender;
import pl.polsl.mushrooms.application.enums.MushroomerLevel;
import pl.polsl.mushrooms.application.enums.UserRole;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class Mushroomer extends User {

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@Column(name = "GENDER")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "LEVEL")
	@Enumerated(EnumType.STRING)
	private MushroomerLevel level;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "CITY")
	private String city;

	@Column(name = "PHOTO")
	private byte[] photo;

	@ManyToMany(targetEntity = Trip.class, mappedBy = "mushroomers",
			fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Trip> trips;

	@OneToMany(mappedBy = "mushroomer")
	private Set<Score> scores;

	@OneToMany(mappedBy = "mushroomer")
	private Set<Discovery> discoveries;

	@OneToMany(mappedBy = "friends")
	private Set<Mushroomer> friends;

    @Override
    @Enumerated(EnumType.STRING)
    public UserRole getRole() {
        return UserRole.MUSHROOMER;
    }

	protected Mushroomer() {
        trips = new HashSet<>();
        scores = new HashSet<>();
        discoveries = new HashSet<>();
        friends = new HashSet<>();
    }

	public Mushroomer(
			String username, String email, String password, String firstName, String lastName, Date birthDate, Gender gender, MushroomerLevel level) {
	    super(username, email, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.level = level;

        trips = new HashSet<>();
        scores = new HashSet<>();
        discoveries = new HashSet<>();
        friends = new HashSet<>();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MushroomerLevel getLevel() {
		return this.level;
	}

	public void setLevel(MushroomerLevel level) {
		this.level = level;
	}

	public Set<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(Set<Trip> trips) {
		this.trips = trips;
	}

	public Set<Score> getScores() {
		return scores;
	}

	public void setScores(Set<Score> scores) {
		this.scores = scores;
	}

	public Set<Discovery> getDiscoveries() {
		return discoveries;
	}

	public void setDiscoveries(Set<Discovery> discoveries) {
		this.discoveries = discoveries;
	}

    public Set<Mushroomer> getFriends() {
        return friends;
    }

    public void setFriends(Set<Mushroomer> friends) {
        this.friends = friends;
    }

	public int hashCode() {
		int hashCode = 0;
		if ( this.firstName != null ) {
			hashCode += this.firstName.hashCode();
		}
		if ( this.lastName != null ) {
			hashCode += this.lastName.hashCode();
		}
		if ( this.birthDate != null ) {
			hashCode += this.birthDate.hashCode();
		}
		if ( this.gender != null ) {
			hashCode += this.gender.hashCode();
		}
		if ( hashCode == 0 ) {
			hashCode = super.hashCode();
		}
		return hashCode;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof Mushroomer) {
			Mushroomer mushroomerObject = (Mushroomer) object;
			boolean equals = true;
			equals &= ((this.firstName == mushroomerObject.firstName)
				|| (this.firstName != null && this.firstName.equals(mushroomerObject.firstName)));
			equals &= ((this.lastName == mushroomerObject.lastName)
				|| (this.lastName != null && this.lastName.equals(mushroomerObject.lastName)));
			equals &= ((this.birthDate == mushroomerObject.birthDate)
				|| (this.birthDate != null && this.birthDate.equals(mushroomerObject.birthDate)));
			equals &= ((this.gender == mushroomerObject.gender)
				|| (this.gender != null && this.gender.equals(mushroomerObject.gender)));
			equals &= this.level == mushroomerObject.level;
			equals &= this.trips == mushroomerObject.trips;
			equals &= this.scores == mushroomerObject.scores;
			equals &= this.discoveries == mushroomerObject.discoveries;
			equals &= this.friends == mushroomerObject.friends;
			return equals;
		}
		return false;
	}

}