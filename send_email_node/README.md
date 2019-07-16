# How to upload to AWS Lambda

## First install dependencies

```
npm i node-mailer
```

## Create AWS Lambda Function

```
zip -r send_email_node.zip .
aws lambda create-function \
--function-name send_email_node \
--zip-file fileb://~/Documents/Personal/lambda_examples/send_email_node/send_email_node.zip \
--role arn:aws:iam::367683483263:role/service-role/send_email_node-role-bbihkjmw \
--runtime nodejs10.x \
--handler "index.handler" \
--environment Variables="{`cat .env | xargs | sed 's/ /,/g'`}" \
--memory-size 512 \
--timeout 5 \
--publish
```

## Update Lambda Code

```
zip -r send_email_node.zip .
aws lambda update-function-code --function-name send_email_node \
    --zip-file fileb://~/Documents/Personal/lambda_examples/send_email_node/send_email_node.zip
```