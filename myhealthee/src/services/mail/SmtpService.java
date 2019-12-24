package services.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.SSLSocketFactory;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.logging.impl.Log4jLogger;

import configuration.ApplicationInitializer;

/**
 * Class to send emails with a smtp.
 *
 * @author adlo
 */
public final class SmtpService {

	private static final Logger logger = Log4jLogger.getLogger(SmtpService.class);
	
	/* Constants */
	private static final String SMTP_HOST = "mail.smtp.host";
	private static final String SMTP_PORT = "mail.smtp.port";

	// SMTP with an authentication mechanism
	private static final String SMTP_AUTH = "mail.smtp.auth";
	private static final String SMTP_STARTTLS_ENABLED = "mail.smtp.starttls.enable";
	private static final String SMTP_SSL_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
	private static final String SMTP_SSL_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";

	/* Constants errors */
	private static final String ERROR_SMTP = "Fail loading the smtp %s";
	private static final String INVALID_PROPERTIES = "Invalid %s properties, The value need be a %s value";

	/* Fields */
	private String smtpHost;
	private String smtpPort;
	private Protocol smtpProtocol;
	private String smtpId;
	private String smtpPassword;
	private String smtpFromEmail;
	private SmtpTransportListener smtpListener;

	/**
	 * SmtpService constructor.
	 */
	public SmtpService() {
		this.smtpHost = SmtpHosts.getHostFrom(ApplicationInitializer.SMTP_HOST);
		this.smtpProtocol = Protocol.getFor(ApplicationInitializer.SMTP_PROTOCOL);
		this.smtpPort = this.smtpProtocol.getDefaultPort();
		this.smtpId = ApplicationInitializer.SMTP_ID;
		this.smtpPassword = ApplicationInitializer.SMTP_PASSWORD;
		if (SmtpHosts.getFor(this.smtpHost).useStmpIdentifierAsFromEmail()) {
			this.smtpFromEmail = smtpId;
		} else {
			this.smtpFromEmail = ApplicationInitializer.SMTP_FROM_EMAIL;
		}
	}

	// CHAIN CONSTURCTORS
	/**
	 * Chan constructor to add or change the smtp.
	 *
	 * @param smtpHost the smtp host
	 * @return a new instance of this class with the smtp changed.
	 */
	public SmtpService withSmtp(String smtpHost) {
		this.smtpHost = smtpHost;
		return this;
	}

	/**
	 * Chain constructor to add or change the smtp port.
	 *
	 * @param smtpPort the smtp port
	 * @return a new instance of this class with the smtp port changed.
	 */
	public SmtpService withPort(String smtpPort) {
		this.smtpPort = smtpPort;
		return this;
	}

	/**
	 * Chain constructor to add or change the smtp protocol.
	 *
	 * @param smtpProtocol the smtp protocol
	 * @return a new instance of this class with the smtp protocol changed.
	 */
	public SmtpService withProtocol(String smtpProtocol) {
		this.smtpProtocol = Protocol.getFor(smtpProtocol);
		return this;
	}

	/**
	 * Chain constructor to add or change the smtp id.
	 *
	 * @param smtpId the smtp id
	 * @return a new instance of this class with the smtp id changed.
	 */
	public SmtpService withId(String smtpId) {
		this.smtpId = smtpId;
		return this;
	}

	/**
	 * Chain constructor to add or change the smtp password.
	 *
	 * @param smtpPassword the smtp password
	 * @return a new instance of this class with the smtp password changed.
	 */
	public SmtpService withPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
		return this;
	}

	/**
	 * Chain constructor to add or change the smtp from email.
	 *
	 * @param smtpFromEmail the smtp from email
	 * @return a new instance of this class with the smtp from email changed.
	 */
	public SmtpService withFromEmail(String smtpFromEmail) {
		this.smtpFromEmail = smtpFromEmail;
		return this;
	}

	/**
	 * Chain constructor to add a smtp listener.
	 *
	 * @param smtpListener the smtp lisetner
	 * @return a new instance of this class with the smtp listener added.
	 */
	public SmtpService withSmtpListener(SmtpTransportListener smtpListener) {
		this.smtpListener = smtpListener;
		return this;
	}

	// METHODS
	/**
	 * Send email.
	 *
	 * @param message the message configuration.
	 */
	public void sendEmail(final MailData message) {
		Transport transport = null;
		try {
			logger.info("Sending email...");
			// Start sending email
			if (smtpListener != null) {
				smtpListener.onStart();
			}
			// Error smtp host
			if (smtpHost == null) {
				throw new Exception(String.format(ERROR_SMTP, "host"));
			}
			// Check if port
			if (smtpPort != null && !smtpPort.matches("^\\d+$")) {
				throw new Exception(String.format(INVALID_PROPERTIES, "port", "numeric"));
			}
			// Error fromEmail
			if (smtpId == null) {
				throw new Exception(String.format(ERROR_SMTP, "id"));
			}
			// Error password
			if (smtpPassword == null) {
				throw new Exception(String.format(ERROR_SMTP, "password"));
			}
			// Configure smtp session email
			Session smtpSession = configureSmtpEmail();
			MimeMessage msg = configureMessage(smtpSession, message);
			transport = smtpSession.getTransport("smtp");
			transport.addTransportListener(smtpListener);
			transport.connect();
			transport.sendMessage(msg, msg.getAllRecipients());

			// Finish sending emails
			if (this.smtpListener != null) {
				this.smtpListener.onFinish();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			if (this.smtpListener != null) {
				this.smtpListener.onFail(ex);
			}
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException ex) {
					logger.error(ex.getMessage());
				}
			}
		}
	}

	/**
	 * Set smtp listener.
	 *
	 * @param smtpListener the smtp listener
	 */
	public void addSmtpListener(SmtpTransportListener smtpListener) {
		this.smtpListener = smtpListener;
	}

	// PRIVATE METHODS
	/**
	 * Configure smtp session email.
	 *
	 * @param message the message configuration.
	 * @return the smtp session email.
	 */
	private Session configureSmtpEmail() {
		Authenticator auth = null;
		Properties props = new Properties();
		props.put(SMTP_HOST, smtpHost);
		props.put(SMTP_PORT, smtpProtocol.getDefaultOnNull(smtpPort));
		if (this.smtpProtocol != Protocol.NO_AUTHENTICATION) {
			props.put(SMTP_AUTH, true);
			auth = new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(smtpId, smtpPassword);
				}
			};
		}

		// Define custom properties for the protocols
		switch (this.smtpProtocol) {
		case SSL:
			props.put(SMTP_SSL_SOCKETFACTORY_PORT, smtpProtocol.getDefaultOnNull(smtpPort));
			props.put(SMTP_SSL_SOCKETFACTORY_CLASS, SSLSocketFactory.class.getCanonicalName());
			break;
		case TLS:
			props.put(SMTP_STARTTLS_ENABLED, true);
			break;
		default:
			// No authentication mecanism
		}

		// Generate email session
		return Session.getInstance(props, auth);
	}

	/**
	 * Configure message to send
	 *
	 * @param session the java mail session
	 * @param message the message
	 */
	private MimeMessage configureMessage(final Session session, final MailData message) throws MessagingException {
		MimeMessage msg = new MimeMessage(session);
		Multipart multipart = new MimeMultipart();

		// Headers
		msg.addHeader("Content-Type", message.getContentType());
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		// Create body part
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message.getBody(), message.getType());
		multipart.addBodyPart(messageBodyPart);

		// Set recipients
		for (String email : message.getToEmails()) {
			msg.addRecipient(RecipientType.TO, new InternetAddress(email));
		}
		for (String email : message.getCc()) {
			msg.addRecipient(RecipientType.CC, new InternetAddress(email));
		}
		for (String email : message.getBcc()) {
			msg.addRecipient(RecipientType.BCC, new InternetAddress(email));
		}

		// Add attachments
		for (File attachmentFile : message.getAttachments()) {
			BodyPart attachment = new MimeBodyPart();
			attachment.setDataHandler(new DataHandler(new FileDataSource(attachmentFile)));
			messageBodyPart.setFileName(attachmentFile.getName());
			multipart.addBodyPart(attachment);
		}

		// Message configuration
		msg.setSubject(message.getSubject(), message.getEncoding());
		msg.setContent(multipart);
		msg.setFrom(new InternetAddress(this.smtpFromEmail));
		if (message.getReplyTo() != null) {
			msg.setReplyTo(InternetAddress.parse(message.getReplyTo(), false));
		}
		msg.setSentDate(new Date());

		// Return message
		return msg;
	}
}
