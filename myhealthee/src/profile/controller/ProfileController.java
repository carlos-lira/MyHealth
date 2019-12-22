package profile.controller;

import entity.User;
import profile.dao.ProfileFacadeRemote;
import utils.Messages;
import utils.SessionUtils;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("profile")
@RequestScoped
public class ProfileController implements Serializable{
	private static final long serialVersionUID = 9211805378364561644L;
	
	@EJB
    private ProfileFacadeRemote ejb;


}
