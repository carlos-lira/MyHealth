package components.profile.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import components.profile.dao.ProfileFacadeRemote;

@Named("profile")
@SessionScoped
public class ProfileController implements Serializable{
	private static final long serialVersionUID = 9211805378364561644L;
	
	@EJB
    private ProfileFacadeRemote ejb;


}
