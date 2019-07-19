package send_email_java;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Main implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {
        final String username = System.getenv("EMAIL");
        final String password = System.getenv("PASSWORD");

        Gson g = new Gson();
        EmailRequest emailRequest = g.fromJson(request.getBody(), EmailRequest.class);

        LambdaLogger logger = context.getLogger();
        logger.log("Subject : " + emailRequest.getSubject());
        logger.log("Message : " + emailRequest.getMessage());
        logger.log("To Email : " + emailRequest.getToEmail());

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(System.getenv("FROM_EMAIL")));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailRequest.getToEmail())
            );

            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getMessage());

            Transport.send(message);

            logger.log("Email Sent!!!");

        } catch (MessagingException e) {
            e.printStackTrace();
            logger.log(e.getLocalizedMessage());
        }

        return new ApiGatewayResponse(200, request.getHeaders(), request.getBody());
    }
}