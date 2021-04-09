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
curl -X POST -F 'content=[your friendly message]' -F 'sender=[your sender]' [address to send to]/sendMessage
```
