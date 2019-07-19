# How to upload to AWS Lambda

## First install dependencies

```
npm i dependency1
npm i dependency2
npm i dependency3
```

## Set FUNCTION_NAME variable

```
FUNCTION_NAME=nameofthefunction
```

## Create AWS Lambda Function

```
zip -r $FUNCTION_NAME.zip .
aws lambda create-function \
--function-name $FUNCTION_NAME \
--zip-file fileb://~/Documents/Personal/lambda_examples/$FUNCTION_NAME/$FUNCTION_NAME.zip \
--role arn:aws:iam::367683483263:role/send-email-role \
--runtime nodejs10.x \
--handler "index.handler" \
--environment Variables="{`cat .env | xargs | sed 's/ /,/g'`}" \
--memory-size 512 \
--timeout 5 \
--publish
```

## Update Lambda Code

```
zip -r $FUNCTION_NAME.zip .
aws lambda update-function-code --function-name $FUNCTION_NAME \
    --zip-file fileb://~/Documents/Personal/lambda_examples/$FUNCTION_NAME/$FUNCTION_NAME.zip
```