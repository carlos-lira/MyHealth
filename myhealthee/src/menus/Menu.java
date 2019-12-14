package menus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Menu entity.
 * 
 * @author adlo
 */
@SuppressWarnings("rawtypes")
public class Menu implements Serializable {
	private static final long serialVersionUID = -6335631151547682142L;
	
	/* Fields */
	private String pageName;
	private String iconClass;
	private String url;
	private List<Class> roles;
	
	/**
	 * Constructor by default.
	 * 
	 * @param pageName the name of the page
	 * @param iconClass the icon class name.
	 * @param url the url to redirect on click
	 * @param roles list of Entities class that can access tho the menu page.
	 */
	public Menu(String pageName, String iconClass, String url, Class... roles) {
		super();
		this.pageName = pageName;
		this.iconClass = iconClass;
		this.url = url;
		this.roles = Arrays.asList(roles);
	}
	
	// Getters & Setters
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Class> getRoles() {
		return roles;
	}

	public void setRoles(List<Class> roles) {
		this.roles = roles;
	}
}
