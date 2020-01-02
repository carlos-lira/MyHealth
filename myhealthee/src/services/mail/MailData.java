package services.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mail data with all necessary properties
 *
 * @author adlo
 */
public final class MailData {

	/* Email pattern */
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	/* Fields */
	private String type = "text/html";
	private String encoding = "UTF-8";
	// Message subject and body
	private String subject;
	private String body;
	private String replyTo;
	private List<String> toEmails = new ArrayList<String>();
	private List<String> cc = new ArrayList<String>();
	private List<String> bcc = new ArrayList<String>();
	private List<File> attachments = new ArrayList<File>();

	/**
	 *
	 * @param subject
	 * @param body
	 * @param fromEmail
	 * @param toEmails
	 */
	public MailData(String subject, String body, String... toEmails) {
		this.subject = subject;
		this.body = body;
		for (String email : toEmails) {
			if (isAValidEmail(email)) {
				this.addToEmail(email);
			}
		}
	}

	// Chain constructors
	/**
	 * Chain constructor to add list of cc.
	 *
	 * @param cc email cc.
	 * @return a new instance of EmailMessage with cc added.
	 */
	public MailData withCc(String... cc) {
		this.cc = Arrays.asList(cc);
		return this;
	}

	/**
	 * Chain constructor to add list of bcc.
	 *
	 * @param bcc email bcc.
	 * @return a new instance of EmailMessage with bcc added.
	 */
	public MailData withBcc(String... bcc) {
		this.bcc = Arrays.asList(bcc);
		return this;
	}

	/**
	 * Chain constructor to add reply to
	 *
	 * @param replyTo email
	 * @return a new instance of EmailMessage with replyTo added.
	 */
	public MailData withReplyTo(String replyTo) {
		if (isAValidEmail(replyTo)) {
			this.replyTo = replyTo;
		}
		return this;
	}

	// TO EMAIL
	/**
	 * Add a to email.
	 *
	 * @param toEmail the email
	 */
	public void addToEmail(String toEmail) {
		if (isAValidEmail(toEmail)) {
			this.toEmails.add(toEmail);
		}
	}

	/**
	 * Add a to emails.
	 *
	 * @param toEmails the array of to emails
	 */
	public void addToEmail(String... toEmails) {
		for (String email : toEmails) {
			if (isAValidEmail(email)) {
				this.toEmails.add(email);
			}
		}
	}

	/**
	 * Remove a to email.
	 *
	 * @param toEmail the email
	 */
	public void removeToEmail(String toEmail) {
		this.toEmails.remove(toEmail);
	}

	/**
	 * Clear all to emails.
	 */
	public void removeAllToEmails() {
		this.toEmails.clear();
	}

	// CC
	/**
	 * Add a cc email.
	 *
	 * @param cc the cc email
	 */
	public void addCc(String cc) {
		if (isAValidEmail(cc)) {
			this.cc.add(cc);
		}
	}

	/**
	 * Add a cc emails.
	 *
	 * @param ccs the array of cc emails
	 */
	public void addCc(String... ccs) {
		for (String email : ccs) {
			if (isAValidEmail(email)) {
				this.cc.add(email);
			}
		}
	}

	/**
	 * Remove a cc email.
	 *
	 * @param cc the cc email
	 */
	public void removeCc(String cc) {
		this.cc.remove(cc);
	}

	/**
	 * Clear all cc emails.
	 */
	public void removeAllCc() {
		this.cc.clear();
	}

	// CCO
	/**
	 * Add a bcc email.
	 *
	 * @param bcc the bcc email
	 */
	public void addBcc(String bcc) {
		if (isAValidEmail(bcc)) {
			this.bcc.add(bcc);
		}
	}

	/**
	 * Add a bcc emails.
	 *
	 * @param bccs the array of bcc emails
	 */
	public void addBcc(String... bccs) {
		for (String email : bccs) {
			if (isAValidEmail(email)) {
				this.bcc.add(email);
			}
		}
	}

	/**
	 * Remove a bcc email.
	 *
	 * @param bcc the bcc email
	 */
	public void removeBcc(String bcc) {
		this.bcc.remove(bcc);
	}

	/**
	 * Clear all bcc emails.
	 */
	public void removeAllBcc() {
		this.bcc.clear();
	}

	// ATTACHMENTS
	/**
	 * Add an attachment.
	 *
	 * @param attachment the attachment
	 */
	public void addAttachment(File attachment) {
		this.attachments.add(attachment);
	}

	/**
	 * Add attachments.
	 *
	 * @param attachments the array of attachments
	 */
	public void addAttachment(File... attachments) {
		this.attachments.addAll(Arrays.asList(attachments));
	}

	/**
	 * Remove an attachment.
	 *
	 * @param attachment the attachment
	 */
	public void removeAttachment(File attachment) {
		this.attachments.remove(attachment);
	}

	/**
	 * Clear all attachments.
	 */
	public void removeAllAttachment() {
		this.attachments.clear();
	}

	// GETTERS & SETTERS
	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return a list of bcc emails.
	 */
	public List<String> getBcc() {
		return bcc;
	}

	/**
	 * Set the list of bcc emails.
	 *
	 * @param bcc the list of bcc emails
	 */
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the body of the email.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set the body of the email.
	 *
	 * @param body the body of the email
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the list of cc emails.
	 */
	public List<String> getCc() {
		return cc;
	}

	/**
	 * Set the list of cc email.
	 *
	 * @param cc the list of cc emails
	 */
	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	/**
	 * @return the content type of the email, by default is "text/html;
	 *         charset=UTF-8"
	 */
	public String getContentType() {
		return type + "; charset=" + encoding;
	}

	/**
	 * @return the type of the email, by default is text/html.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set a different type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the encoding of the email, by default return UTF-8.
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * Set a new encoding.
	 *
	 * @param encoding the encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the reply to email.
	 */
	public String getReplyTo() {
		return replyTo;
	}

	/**
	 * Set a new reply to email.
	 *
	 * @param replyTo the reply to email
	 */
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	/**
	 * @return the subject of the email.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Set a new subjet for the email.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the to emails.
	 */
	public List<String> getToEmails() {
		return toEmails;
	}

	/**
	 * Set a new list of to emails.
	 *
	 * @param toEmails the list of to emails
	 */
	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}

	// PRIVATE METHODS
	/**
	 * Validate a email.
	 *
	 * @param email a email to validate
	 * @return true if the email is a valid email, false otherwise.
	 */
	private boolean isAValidEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}
}
