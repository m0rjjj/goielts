package au.com.goielts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
 
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
	
	public interface ValidationStepOne {
        // validation group marker interface
    }

	public interface ValidationStepTwo {
        // validation group marker interface
    }
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(groups = {ValidationStepTwo.class})
    @Column(nullable=false)
    private String password;
  
    @NotBlank(groups = {ValidationStepTwo.class})
    @Transient
    private String confirmedPassword;
         
    @NotBlank(groups = {ValidationStepOne.class})
    @Column(name="FIRST_NAME", nullable=false)
    private String firstName;
 
    @NotBlank(groups = {ValidationStepOne.class})
    @Column(name="LAST_NAME", nullable=false)
    private String lastName;
 
    @NotBlank(groups = {ValidationStepOne.class})
    @Column(name="EMAIL", nullable=false)
    private String email;
    
    @NotBlank(groups = {ValidationStepOne.class})
    @Column(name="STATE", nullable=false)
    private String state=State.ACTIVE.getState();
 
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles = new HashSet<Role>();
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getState() {
        return state;
    }
 
    public void setState(String state) {
        this.state = state;
    }

	public Set<Role> getUserProfiles() {
        return roles;
    }
 
    public void setUserProfiles(Set<Role> roles) {
        this.roles = roles;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", password=" + password
                + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + ", state=" + state + ", userProfiles=" + roles +"]";
    }
 
     
}