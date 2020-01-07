package entity.imp;

import javax.persistence.Entity;
import javax.persistence.Table;

import entity.BaseEntity;


@Entity
@Table(name = "IMAGE")
public class Image extends BaseEntity{
	private static final long serialVersionUID = -8927732273869513716L;
}
