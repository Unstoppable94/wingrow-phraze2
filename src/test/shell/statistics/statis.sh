curl  -X POST -H "Content-Type:application/json" http://10.10.112.89:8100/webapi/login --data-binary @user.json
curl  -X POST -H "Content-Type:application/json" http://10.10.112.89:8100/webapi/statistics/projectStat --data-binary @group.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA5NTEwNTIyLCJpYXQiOjE1MDk0MjA1MjIsImp0aSI6IjEifQ.xU_zIMBql4P0G5ddxO8hIfSutCmg1woMbHUPpfB--sY"
curl  -X POST -H "Content-Type:application/json" http://10.10.112.89:8100/webapi/statistics/buildStat --data-binary @group.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA5NTEwNTIyLCJpYXQiOjE1MDk0MjA1MjIsImp0aSI6IjEifQ.xU_zIMBql4P0G5ddxO8hIfSutCmg1woMbHUPpfB--sY"
