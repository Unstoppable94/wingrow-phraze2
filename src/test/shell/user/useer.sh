
curl -X POST http://localhost:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ5NTM2LCJpYXQiOjE1MDU5NjMxMzYsImp0aSI6IjEifQ.V1XW5NzZxA63WLvh67RBQLQEXsNYXus8U_KTmRTM7k4"

curl  -X PUT -H "Content-Type:application/json" http://localhost:8100/webapi/user/admin3?action=resetpassword -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MTQ1MjU1LCJpYXQiOjE1MDYwNTg4NTUsImp0aSI6IjEifQ.oAbkz6Em4fLfUvB4fDnk7202eCpCmfVr9I_haEECZW8"


curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @user.json

curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @admin3.json


	


curl -X GET http://localhost:8100/webapi/user  -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjE0NTMzMCwiaWF0IjoxNTA2MDU4OTMwLCJqdGkiOiIxIn0.P-3pMLRnAv-Z269OZlcd07YzotKfxULg7TsnVthet-0"
curl -X GET http://localhost:8100/webapi/project  -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjE0NTMzMCwiaWF0IjoxNTA2MDU4OTMwLCJqdGkiOiIxIn0.P-3pMLRnAv-Z269OZlcd07YzotKfxULg7TsnVthet-0"

curl -X PUT http://localhost:8100/webapi/user/admin3  -H "Content-Type:application/json"  --data-binary @newuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4"

curl -X GET http://localhost:8100/webapi/user?userType=local   -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4"


curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @newldapuser.json

curl -X POST http://localhost:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newldapuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ1OTU1LCJpYXQiOjE1MDU5NTk1NTUsImp0aSI6IjEifQ.Txa1608RCzFYfbStEgtfCLzBbpdC2S59u27qAxUX0tA"





curl -X POST http://192.168.101.4:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ5NTM2LCJpYXQiOjE1MDU5NjMxMzYsImp0aSI6IjEifQ.V1XW5NzZxA63WLvh67RBQLQEXsNYXus8U_KTmRTM7k4"

curl  -X PUT -H "Content-Type:application/json" http://192.168.101.4:8100/webapi/user/admin3?action=resetpassword -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MTQ1MjU1LCJpYXQiOjE1MDYwNTg4NTUsImp0aSI6IjEifQ.oAbkz6Em4fLfUvB4fDnk7202eCpCmfVr9I_haEECZW8"


curl  -X POST -H "Content-Type:application/json" http://192.168.101.6:8100/webapi/login --data-binary @user.json

curl  -X POST -H "Content-Type:application/json" http://192.168.101.4:8100/webapi/login --data-binary @admin3.json


	


curl -X GET http://192.168.101.4:8100/webapi/projectgroup  -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MTQ2NDM5LCJpYXQiOjE1MDYwNjAwMzksImp0aSI6IjEifQ.py2w9gH4ZFLITabgUWF3UVnIywwOjfksmoY-8rgbAuQ"


curl -X GET http://192.168.101.4:8100/webapi/project  -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MTQ3NzI2LCJpYXQiOjE1MDYwNjEzMjYsImp0aSI6IjEifQ.pFc_4c1XFxFZJXTculXM0onJo8NWeTMQMB8Czu7m8AA"

curl -X PUT http://192.168.101.4:8100/webapi/user/admin3  -H "Content-Type:application/json"  --data-binary @newuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4"

curl -X GET http://192.168.101.4:8100/webapi/user?userType=local   -H "Content-Type:application/json"   -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbjMiLCJhdWQiOiJhZG1pbiIsImV4cCI6MTUwNjA0NjQ2MSwiaWF0IjoxNTA1OTYwMDYxLCJqdGkiOiIxIn0.BGRarr1yYYUS3y0ra3gcKInNRhGz3RmasJ3jUIuzKQ4"


curl  -X POST -H "Content-Type:application/json" http://192.168.101.4:8100/webapi/login --data-binary @newldapuser.json

curl -X POST http://192.168.101.4:8100/webapi/user  -H "Content-Type:application/json"  --data-binary @newldapuser.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ1OTU1LCJpYXQiOjE1MDU5NTk1NTUsImp0aSI6IjEifQ.Txa1608RCzFYfbStEgtfCLzBbpdC2S59u27qAxUX0tA"