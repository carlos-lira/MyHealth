package services.images;

import java.io.Serializable;
import java.util.Base64;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Image bean to allow transformations between blob and base 64.
 * 
 * @author adlo
 */
@Named("images")
@SessionScoped
public class ImageBean implements Serializable {
	private static final long serialVersionUID = 6574219172568107197L;

	/**
	 * Convert image to base 64.
	 * 
	 * @param image an array of bytes that represents the image.
	 * @return a base64 string that represent the image
	 */
	public String convertImageAsBase64(byte[] imageBytes) {
		if (imageBytes != null && imageBytes.length > 0) {
			return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
		}
		return null;
	}
}
