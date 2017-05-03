package pl.polsl.mushrooms.application.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private UUID id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToOne(optional = false)
	private Discovery discovery;

	protected Tag() { }

	public Tag(String name, Discovery discovery) {
		this.name = name;
		this.discovery = discovery;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Discovery getDiscovery() {
		return this.discovery;
	}

	public void setDiscovery(Discovery discovery) {
		this.discovery = discovery;
	}

	public int hashCode() {
		int hashCode = 0;
		if ( this.id != null ) {
			hashCode += this.id.hashCode();
		}
		if ( this.name != null ) {
			hashCode += this.name.hashCode();
		}
		if ( this.discovery != null ) {
			hashCode += this.discovery.hashCode();
		}
		if ( hashCode == 0 ) {
			hashCode = super.hashCode();
		}
		return hashCode;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof Tag) {
			Tag tagObject = (Tag) object;
			boolean equals = true;
			equals &= ((this.id == tagObject.id)
				|| (this.id != null && this.id.equals(tagObject.id)));
			equals &= ((this.name == tagObject.name)
				|| (this.name != null && this.name.equals(tagObject.name)));
			equals &= ((this.discovery == tagObject.discovery)
				|| (this.discovery != null && this.discovery.equals(tagObject.discovery)));
			return equals;
		}
		return false;
	}
}