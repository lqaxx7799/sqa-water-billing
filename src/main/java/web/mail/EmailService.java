package web.mail;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService {
	public static void sendMail(String to, String subject, String content, String body) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("lqaxx000@gmail.com", "mryxicgrnpenzqil");
//				return new PasswordAuthentication("sqa.n2.dt3@gmail.com", "cwmnvgfrxdhwjuvc");
//				return new PasswordAuthentication("sqa.n2.dt3@gmail.com", "SQA_n2_pass_dt3");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("lqaxx000@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setContent(content, "text/html; charset=UTF-8");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(body, "text/html; charset=UTF-8");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
//			MimeBodyPart attachPart = new MimeBodyPart();
//
//			attachPart.attachFile("/var/tmp/image19.png");
//			multipart.addBodyPart(attachPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}
}
