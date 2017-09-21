
curl -X POST http://localhost:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ5NTM2LCJpYXQiOjE1MDU5NjMxMzYsImp0aSI6IjEifQ.V1XW5NzZxA63WLvh67RBQLQEXsNYXus8U_KTmRTM7k4"

curl  -X PUT -H "Content-Type:application/json" http://localhost:8100/webapi/user/admin3?action=resetpassword -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ1OTU1LCJpYXQiOjE1MDU5NTk1NTUsImp0aSI6IjEifQ.Txa1608RCzFYfbStEgtfCLzBbpdC2S59u27qAxUX0tA" 



curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @user.json



  curl -X PUT -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4" http://localhost:8100/webapi/selfInfo --data-binary @admin3-password.json


curl -X GET http://localhost:8100/webapi/user  -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4"


curl -X PUT http://localhost:8100/webapi/user/admin3  -H "Content-Type:application/json"  --data-binary @newuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4"

curl -X GET http://localhost:8100/webapi/user?userType=local   -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4"


curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @newldapuser.json

curl -X POST http://localhost:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newldapuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ1OTU1LCJpYXQiOjE1MDU5NTk1NTUsImp0aSI6IjEifQ.Txa1608RCzFYfbStEgtfCLzBbpdC2S59u27qAxUX0tA"