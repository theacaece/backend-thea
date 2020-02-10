package edu.caece.app.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class UserLogPK implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(name = "user_access_date")
	private LocalDateTime accessDate;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessDate == null) ? 0 : accessDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLogPK other = (UserLogPK) obj;
		if (accessDate == null) {
			if (other.accessDate != null)
				return false;
		} else if (!accessDate.equals(other.accessDate))
			return false;
		return true;
	}

	
}