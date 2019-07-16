# How to upload to AWS Lambda

## Create AWS Lambda Function

```
mvn package
aws lambda create-function \
--function-name send_email_java \
--zip-file fileb://~/Documents/Personal/lambda_examples/send_email_java/target/lambda-java-example-1.0-SNAPSHOT.jar \
--role arn:aws:iam::367683483263:role/send-email-role \
--runtime java8 \
--handler "send_email_java.Main::handleRequest" \
--environment Variables="{`cat .env | xargs | sed 's/ /,/g'`}" \
--memory-size 512 \
--timeout 5 \
--publish
```

## Update Lamda Code

```
mvn package
aws lambda update-function-code --function-name send_email_java \
    --zip-file fileb://~/Documents/Personal/lambda_examples/send_email_java/target/lambda-java-example-1.0-SNAPSHOT.jar 
```