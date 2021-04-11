# faxcoin
Messenger for people with taste.

# Usage
Just run 
```shell
mvn package
```
to build the jar and 
```shell
java -jar target/faxcoin-0.0.1-SNAPSHOT.jar -a [address to run on]:[port to run on] -p [port to run on]
```

To register a new messenger use
```shell
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"address":"[messenger address]"}' \
  [address of node]/registerMessenger
```

To register a new neighbour use
```shell
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"address":"[neighbour address]"}' \
  [address of node]/registerNeighbour
```

To send a message use
```shell
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"content":"[Your friendly message]", "sender":"[message sender]", "receiver":"[Message receiver]"}' \
  [address of node]/sendMessage
```

To get the current message queue for a messenger
```shell
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"content":"[Your friendly message]", "sender":"[message sender]", "receiver":"[Message receiver]"}' \
  [address of node]/getMessageQueue
```
