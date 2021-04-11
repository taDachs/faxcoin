# faxcoin
Messenger for people with taste.

# Usage
Just run 
```shell
mvn package
```
to build the jar and 
```shell
java -jar target/faxcoin-0.0.1-SNAPSHOT.jar -n [your name] -p [your printer name]
```

To send a message use
```shell
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"content":"[Your friendly message]", "receiver":"Message receiver"}' \
  [address of node]/sendMessage
```
