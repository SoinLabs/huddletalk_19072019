package send_email_java;

public class EmailRequest {

    String subject;
    String message;
    String toEmail;

    public EmailRequest() {
    }

    public EmailRequest(String subject, String message, String toEmail) {
        this.subject = subject;
        this.message = message;
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

}