package components.visit.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import components.visit.dao.VisitFacadeRemote;

@Named("removevisit")
@RequestScoped
public class RemoveVisit implements Serializable {

	private static final long serialVersionUID = 7898448825101388023L;

	@EJB
	private VisitFacadeRemote ejb;

	public void removeVisit(long visitId) throws Exception {
		try {
			ejb.removeVisit(visitId);
		} catch (Exception e) {
			// System.out.println("Error");
		}
	}

}