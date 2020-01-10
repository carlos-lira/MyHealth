package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Base entity.
 * 
 * @author adlo
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Id
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
	private Date updateDate;

	// Methods
	@PrePersist
	public void onCreate() {
		this.createDate = new Date();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updateDate = new Date();
	}
	
	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
