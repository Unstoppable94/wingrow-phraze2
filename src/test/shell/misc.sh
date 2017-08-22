curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/sonar --data-binary @sonar.json


curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/mirror --data-binary @mirror.json



curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/rancher --data-binary @rancher.json




curl  -X PUT -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/sonar --data-binary @sonar.json


curl  -X PUT -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/mirror --data-binary @mirror.json



curl  -X PUT -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/rancher --data-binary @rancher.json


curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/registry/10.0.2.52 --data-binary @registry.json



curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/registry/10.0.2.52 --data-binary @registry53.json



curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/registry/10.0.2.53 --data-binary @registry53.json

curl  -X DELETE -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/registry/10.0.2.53 --data-binary @registry53.json


curl  -X PUT -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/smtp --data-binary @smtp.json

