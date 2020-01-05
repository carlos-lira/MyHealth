package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter to prevent access pages without authorization.
 * 
 * @author adlo
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;

			// Resource (css, js, etc)
			if (isResource(req)) {
				chain.doFilter(request, response);
				return;
			} 
			
			// Template inside templates.
			if (isTemplate(req)) {
				redirect(req, resp);
				return;
			} 
			
			// Is one of the login pages.
			if (isLoginPage(req)) {
				if (isLogged(req)) {
					resp.sendRedirect(req.getContextPath() + "/homeView.xhtml");
				} else {
					chain.doFilter(request, response);
				}
				return;
			}
			
			// Just check if the user is logged
			if (!isLogged(req)) {
				resp.sendRedirect(req.getContextPath() + "/loginView.xhtml");
				return;
			}
			
			// Normal behavior.
			chain.doFilter(request, response);
		} catch (Exception e) {
			// TODO logger
		}
	}

	// PROTECTED METHODS
	protected boolean isResource(HttpServletRequest req) {
		String uri = req.getRequestURI();
		return uri.indexOf("/public/") >= 0 || uri.contains("javax.faces.resource");
	}

	protected boolean isTemplate(HttpServletRequest req) {
		String uri = req.getRequestURI();
		return uri.indexOf("/templates/") >= 0;
	}

	protected boolean isLoginPage(HttpServletRequest req) {
		String uri = req.getRequestURI();
		return uri.indexOf("/loginView.xhtml") >= 0 || uri.indexOf("/forgotPasswordView.xhtml") >= 0
				|| uri.indexOf("/registerView.xhtml") >= 0;
	}

	protected boolean isLogged(HttpServletRequest req) {
		HttpSession ses = req.getSession(false);
		return ses != null && ses.getAttribute("user") != null;
	}
	
	protected void redirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (isLogged(req)) {
			resp.sendRedirect(req.getContextPath() + "/homeView.xhtml");
		} else {
			resp.sendRedirect(req.getContextPath() + "/loginView.xhtml");
		}
	}
}
