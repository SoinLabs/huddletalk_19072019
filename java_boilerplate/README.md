# How to upload to AWS Lambda

## Set FUNCTION_NAME variable

```
FUNCTION_NAME=nameofthefunction
```

## Create AWS Lambda Function

```
mvn package
aws lambda create-function \
--function-name $FUNCTION_NAME \
--zip-file fileb://~/Documents/Personal/lambda_examples/$FUNCTION_NAME/target/lambda-java-example-1.0-SNAPSHOT.jar \
--role arn:aws:iam::367683483263:role/send-email-role \
--runtime java8 \
--handler "$FUNCTION_NAME.Main::handleRequest" \
--environment Variables="{`cat .env | xargs | sed 's/ /,/g'`}" \
--memory-size 512 \
--timeout 5 \
--publish
```

## Update Lambda Code

```
mvn package
aws lambda update-function-code --function-name $FUNCTION_NAME \
    --zip-file fileb://~/Documents/Personal/lambda_examples/$FUNCTION_NAME/target/lambda-java-example-1.0-SNAPSHOT.jar
```

## Add to REST API Gateway

```
aws apigateway get-resources --rest-api-id bzrus7wvl9
```
Returns gmkm0kq5l4

```
aws apigateway create-resource --rest-api-id bzrus7wvl9 \
      --parent-id gmkm0kq5l4 \
      --path-part /$FUNCTION_NAME
```
Returns daqkhm

```
aws apigateway put-method --rest-api-id bzrus7wvl9 \
       --resource-id daqkhm \
       --http-method ANY \
       --authorization-type "NONE" 
```

```
aws apigateway put-integration \
        --rest-api-id bzrus7wvl9 \
        --resource-id daqkhm \
        --http-method ANY \
        --type AWS_PROXY \
        --integration-http-method POST \
        --uri arn:aws:apigateway:us-east-1:lambda:path/2019-07-18/functions/arn:aws:lambda:us-east-1:367683483263:function:$FUNCTION_NAME/invocations
```

```
aws lambda add-permission \
        --function-name $FUNCTION_NAME \
        --statement-id lambda-ccc3776e-90cd-4ef8-abdf-7fd788edf369 \
        --action lambda:InvokeFunction \
        --principal apigateway.amazonaws.com \
        --source-arn arn:aws:execute-api:us-east-1:367683483263:bzrus7wvl9/*/*/$FUNCTION_NAME
```

```
aws apigateway create-deployment --rest-api-id bzrus7wvl9 --stage-name default
```