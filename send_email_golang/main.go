package main

import (
	"encoding/json"
	"log"
	"net/http"
	"net/smtp"
	"os"

	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
)

var errorLogger = log.New(os.Stderr, "ERROR ", log.Llongfile)

// EmailRequest is a struct that represents and incoming email request to the API Gateway
type EmailRequest struct {
	Subject string `json:"subject"`
	Message string `json:"message"`
	ToEmail string `json:"toEmail"`
}

// HandleRequest is a function to handle AWS Request
func HandleRequest(req events.APIGatewayProxyRequest) (events.APIGatewayProxyResponse, error) {
	from := os.Getenv("EMAIL")
	pass := os.Getenv("PASSWORD")
	to := "jquesada@soin.co.cr"
	subject := "Hello"
	message := "Hello World"

	emailRequest := &EmailRequest{}

	err := json.Unmarshal([]byte(req.Body), emailRequest)

	if err != nil {
		log.Printf("JSON marshall error: %s", err)
		return serverError(err)
	}

	log.Printf("To Email: %s", emailRequest.ToEmail)
	log.Printf("Message: %s", emailRequest.Message)
	log.Printf("Subject: %s", emailRequest.Subject)

	msg := "From: " + os.Getenv("FROM_EMAIL") + "\n" +
		"To: " + to + "\n" +
		"Subject: " + subject + "\n\n" +
		message

	err = smtp.SendMail("smtp.gmail.com:587",
		smtp.PlainAuth("", from, pass, "smtp.gmail.com"),
		from, []string{to}, []byte(msg))

	if err != nil {
		log.Printf("smtp error: %s", err)
		return serverError(err)
	}

	log.Print("Email Sent!")

	response, err := json.Marshal(emailRequest)
	if err != nil {
		return serverError(err)
	}

	return events.APIGatewayProxyResponse{
		StatusCode: http.StatusOK,
		Body:       string(response),
	}, nil
}

// Add a helper for handling errors. This logs any error to os.Stderr
// and returns a 500 Internal Server Error response that the AWS API
// Gateway understands.
func serverError(err error) (events.APIGatewayProxyResponse, error) {
	errorLogger.Println(err.Error())

	return events.APIGatewayProxyResponse{
		StatusCode: http.StatusInternalServerError,
		Body:       http.StatusText(http.StatusInternalServerError),
	}, nil
}

// Similarly add a helper for send responses relating to client errors.
func clientError(status int) (events.APIGatewayProxyResponse, error) {
	return events.APIGatewayProxyResponse{
		StatusCode: status,
		Body:       http.StatusText(status),
	}, nil
}

func main() {
	lambda.Start(HandleRequest)
}
