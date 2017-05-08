package pl.polsl.mushrooms.application.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.polsl.mushrooms.application.enums.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USERS")
@DiscriminatorColumn(name = "ROLE", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements Serializable {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	protected UUID id;

	@Column(name = "USERNAME", nullable = false)
	protected String username;

	@Column(name = "EMAIL", nullable = false)
	protected String email;

	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false)
	protected String password;

//	@Column(name = "ROLE", nullable = false, insertable = false, updatable = false)
//	@Enumerated(EnumType.STRING)
//	protected UserRole role;

	@OneToMany(mappedBy = "user")
	protected Set<Comment> comments;

	protected User() { }

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;

		comments = new HashSet<>();
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public abstract UserRole getRole();

	public int hashCode() {
		int hashCode = 0;
		if ( this.id != null ) {
			hashCode += this.id.hashCode();
		}
		if ( this.username != null ) {
			hashCode += this.username.hashCode();
		}
		if ( this.email != null ) {
			hashCode += this.email.hashCode();
		}
		if ( this.password != null ) {
			hashCode += this.password.hashCode();
		}
//		if ( this.role != null ) {
//			hashCode += this.role.hashCode();
//		}
		if ( hashCode == 0 ) {
			hashCode = super.hashCode();
		}
		return hashCode;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof User) {
			User userObject = (User) object;
			boolean equals = true;
			equals &= ((this.id == userObject.id)
				|| (this.id != null && this.id.equals(userObject.id)));
			equals &= ((this.username == userObject.username)
				|| (this.username != null && this.username.equals(userObject.username)));
			equals &= ((this.email == userObject.email)
				|| (this.email != null && this.email.equals(userObject.email)));
			equals &= ((this.password == userObject.password)
				|| (this.password != null && this.password.equals(userObject.password)));
//			equals &= ((this.role == userObject.role)
//				|| (this.role != null && this.role.equals(userObject.role)));
			equals &= this.comments == userObject.comments;
			return equals;
		}
		return false;
	}
}