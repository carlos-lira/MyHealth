package services.mail;

import javax.mail.event.TransportListener;

/**
 * Smtp listener.
 *
 * @author adlo
 */
public interface SmtpTransportListener extends TransportListener {

	/**
	 * Event fired on start sending email, on pre send.
	 */
	public void onStart();

	/**
	 * Event fired on email sended.
	 */
	public void onFinish();

	/**
	 * Event fired if any exception ocurred.
	 *
	 * @param ex the exception
	 */
	public void onFail(Exception ex);
}
