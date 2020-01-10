package components.visit.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import components.visit.dao.VisitFacadeRemote;

/**
 * Remove visit managed bean.
 * 
 * @author clira
 * @author adlo
 */
@Named("removevisit")
@RequestScoped
public class RemoveVisit implements Serializable {
	private static final long serialVersionUID = 7898448825101388023L;

	@EJB
	private VisitFacadeRemote ejb;

	// ACTIONS
	public void removeVisit(long visitId) {
		ejb.removeVisit(visitId);
	}

}