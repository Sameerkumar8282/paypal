# PayPal / AnyPayment App Service 

## Client 
- Postman / Web

## Infra
- Config Server
- Eureka Server
- Redis
- kafka Broker
- Docker/Docker Compose

## API Gateway
- Spring Cloud Gateway
- JWT Authentication & Routing

## Services

### User Service
### Auth Filter
### Wallet Service
### Transaction Service
- **Kafka Broker**
- Notification Service(kafka consumer)
- Reward Service (kafka consumer)
### Fraud Detection Service

### To check Redis is Working
```bash
docker exec -it redis redis-cli ping  
```

### Curl to verify the rate limiting
```bash
 for i in {1..50}; do                                          ✔ │ 12:34:33 
curl --location 'localhost:8080/api/transactions/create' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGUiOiJST0xFX1VTRVIiLCJzdWIiOiJzYW1AZ2FtaWwuY29tIiwiaWF0IjoxNzU4OTU2MjMwLCJleHAiOjE3NTkwNDI2MzB9.9wYyWi_TUZAUdn1niwCT_wCFo_b1Uwuerzvvt6BJoN4' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=E33BAEEEEF6EE2559834E887E552FAF5' \
--data '{
    "senderId":1,
    "receiverId":2,
    "amount":220
}' \
-s -o /dev/null -w "request $i -> %{http_code}\n"
done
```