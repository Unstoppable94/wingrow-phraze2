curl  -X GET -H "Content-Type:application/json" http://localhost:8100/webapi/misc/env --data-binary @sonar.json

curl  -X GET -H "Content-Type:application/json" http://localhost:8100/webapi/misc/stagesinfo  


curl  -X PUT -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/misc/smtp --data-binary @smtp.json

curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @user.json



 curl -X GET -H "Content-Type:application/json"  -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA1MTExODc0LCJpYXQiOjE1MDUxMTA5NzQsImp0aSI6IjEifQ.O4QSiCLzaH0ltqbXQ9JBAPAcOwdS9yjWA_y_tTcWI9A" http://localhost:8100/webapi/selfInfo 
 
 
  curl -X PUT -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA1MTExODc0LCJpYXQiOjE1MDUxMTA5NzQsImp0aSI6IjEifQ.O4QSiCLzaH0ltqbXQ9JBAPAcOwdS9yjWA_y_tTcWI9A" http://localhost:8100/webapi/selfInfo --data-binary @password.json



   curl -X PUT -H "Content-Type:application/json"  -H "authorization":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA1MTEwMDI2LCJpYXQiOjE1MDUxMDkxMjYsImp0aSI6IjEifQ.C7f24p5u-cK1xRdoH1yFIwky72C7IbO9r8xRkbs9cJE" http://localhost:8100/webapi/logout



   curl -X POST -H "Content-Type:application/json"   http://localhost:8100/webapi/logout -H "authorization":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA1MTEwMDI2LCJpYXQiOjE1MDUxMDkxMjYsImp0aSI6IjEifQ.C7f24p5u-cK1xRdoH1yFIwky72C7IbO9r8xRkbs9cJE"

curl -X GET http://localhost:8100/webapi/user  -H "Content-Type:application/json" 

curl -X POST http://localhost:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newuser.json


curl  -X GET -H "Content-Type:application/json" http://192.168.101.4:8100/webapi/misc/stagesinfo  
curl -X POST http://localhost:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newldapuser.json


curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @newldapuser.json



curl  -X PUT -H "Content-Type:application/json" http://localhost:8100/webapi/user/admin2?action=resetpassword


curl -X POST http://192.168.101.4:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newuser.json


curl -X GET http://192.168.101.4:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newuser.json


curl -X PUT http://localhost:8100/webapi/user/admin3  -H "Content-Type:application/json"  --data-binary @newuser.json

curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @user.json

curl -X POST -H "Content-Type:application/json"  -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA1NTUxMDEyLCJpYXQiOjE1MDU0NjQ2MTMsImp0aSI6IjEifQ.DmzDmzMo0B2P3lE1pRn-DI_GGRemcmrMBCuS3w5_sOg" http://localhost:8100/webapi/user --data-binary @newuser.json
curl  -X PUT -H "Content-Type:application/json" -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA1NTUxOTgwLCJpYXQiOjE1MDU0NjU1ODAsImp0aSI6IjEifQ.DzKZQ-CInAy7TNSIWOrDH7o8fa5rPL4-tV8BbhbFZSA" http://localhost:8100/webapi/user/admin3?action=resetpassword

curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @resetpass.json

curl -X GET http://localhost:8100/webapi/user -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNTU1Mjc5MywiaWF0IjoxNTA1NDY2Mzk0LCJqdGkiOiIxIn0.mHLrY5cv96Y-j1lbkHWflxD4yL9XHMXxpVtruiW5UBQ"


curl -X GET http://localhost:8100/webapi/selfInfo -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNTU1Mjc5MywiaWF0IjoxNTA1NDY2Mzk0LCJqdGkiOiIxIn0.mHLrY5cv96Y-j1lbkHWflxD4yL9XHMXxpVtruiW5UBQ"
