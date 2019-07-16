# How to upload to AWS Lambda

## First install dependencies

```
go get github.com/aws/aws-lambda-go/lambda
go get github.com/aws/aws-lambda-go/events
```

## Create AWS Lambda Function

```
GOOS=linux go build main.go
zip send_email_golang.zip main
aws lambda create-function \
--function-name send_email_golang \
--zip-file fileb://~/Documents/Personal/lambda_examples/send_email_golang/send_email_golang.zip \
--role arn:aws:iam::367683483263:role/send-email-role \
--runtime go1.x \
--handler "main" \
--environment Variables="{`cat .env | xargs | sed 's/ /,/g'`}" \
--memory-size 512 \
--timeout 5 \
--publish
```

## Update Lamda Code

```
GOOS=linux go build main.go
zip send_email_golang.zip main
aws lambda update-function-code --function-name send_email_golang \
    --zip-file fileb://~/Documents/Personal/lambda_examples/send_email_golang/send_email_golang.zip
```