curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @user.json

curl  -X GET -H "Content-Type:application/json" "http://localhost:8100/webapi/download?name=/job/pro1505966862278/4/artifact/jenkins-pro1505966862278-4.zip"  -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MTM3MDkyLCJpYXQiOjE1MDYwNTA2OTIsImp0aSI6IjEifQ.2arQVGHhOkBznE1GhvanRa8tqbTahbBU6tW2o3_zbBE"  -o test.zip
curl -X GET -H "Content-Type:application/json" "http://localhost:8100/webapi/download?name=/job/pro1505966862278/4/artifact/jenkins-pro1505966862278-4.zip" -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDcyMjA2LCJpYXQiOjE1MDU5ODU4MDYsImp0aSI6IjEifQ.bLCpBJBkdomVbgzU5zXyGXoZZl3Lem7A0O3mjq5X_3Q" -o test.zip



curl -X GET -H "Content-Type:application/json" "http://localhost:8100/webapi/project?action=new" -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2NTYyMDU5LCJpYXQiOjE1MDY0NzU2NjAsImp0aSI6IjEifQ.h_VWJncNhRHA2c3yyFiGWECXD1L1dxxfk0vx9kFlUXs"


curl -X GET -H "Content-Type:application/json" "http://localhost:8100/webapi/projectgroup" -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2NTYyMDU5LCJpYXQiOjE1MDY0NzU2NjAsImp0aSI6IjEifQ.h_VWJncNhRHA2c3yyFiGWECXD1L1dxxfk0vx9kFlUXs"


curl  -X POST -H "Content-Type:application/json" http://192.168.101.4:8100/webapi/login --data-binary @user.json

curl  -X POST -H "Content-Type:application/json" http://192.168.101.6:8100/webapi/login --data-binary @user.json

curl -X GET -H "Content-Type:application/json" "http://192.168.101.4:8100/webapi/project?projectType=traditionalDocker" -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDY4MjIwLCJpYXQiOjE1MDU5ODE4MjAsImp0aSI6IjEifQ.qDkEK_xPoBgMvq-FkyP8-TgVrJKKEmeDM919bPFS6k0"


curl -X GET -H "Content-Type:application/json" "http://192.168.101.6:8100/webapi/project?projectName=docker&projectType=traditionalDocker"  -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDY3NzQ0LCJpYXQiOjE1MDU5ODEzNDQsImp0aSI6IjEifQ.2tRFYLVVDSVeibWX9WLUbDjx7m2OuD4ZXDH_TkCkooU"

newWorkflow
curl -X GET -H "Content-Type:application/json" "http://localhost:8100/webapi/project?action=new"


curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/projectestAddOrModifyMavenProject --data-binary @project.json


curl  -X PUT -H "Content-Type:application/json" http://localhost:8100/webapi/project/ --data-binary @project.json



curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/projectgroup/test2 --data-binary @test2.json

curl  -X DELETE -H "Content-Type:application/json" http://localhost:8100/webapi/projectgroup/test2 --data-binary @test2.json



curl  -X DELETE -H "Content-Type:application/json" http://10.211.55.6:18100/webapi/project/test --data-binary @project.json


curl  -X PUT -H "Content-Type:application/json" http://10.211.55.6:18100/webapi/project/%B2%E2%CA%D4 --data-binary @project.json


curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/project/ --data-binary @project.json

curl -X POST -H "Content-Type:application/json" 
http://10.211.55.6:28080/createItem?name=pro1492011234004 --data-binary @project.json


curl -X POST -H "$CRUMB" http://jenkins:jenkins@10.211.55.6:28080/createItem?name=maskio2  --data-binary  "@p.xml"-H "Content-Type:text/xml"


CRUMB=$(curl -s 'http://jenkins:jenkins@10.211.55.6:28080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')



curl -X POST -H "$CRUMB" http://10.211.55.6:8080/createItem?name=maskio2  --data-binary  "@p.xml"-H "Content-Type:text/xml"

curl -X GET  -H "Content-Type:application/json" http://192.168.101.4:8100/webapi/project

curl -X POST  -H "Content-Type:application/json" http://192.168.101.4:8100/webapi/project --data-binary @pro.json

curl -X POST  -H "Content-Type:application/json" http://localhost:8100/webapi/project --data-binary @traDockerPro.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDUyMDE3LCJpYXQiOjE1MDU5NjU2MTcsImp0aSI6IjEifQ.wCHNAZR_HzXAkZDudZVM7pX86OMebEy704C_DWKTH9c"


curl -X POST  -H "Content-Type:application/json" http://localhost:8100/webapi/project --data-binary @newMavenPro.json  -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDUyMDE3LCJpYXQiOjE1MDU5NjU2MTcsImp0aSI6IjEifQ.wCHNAZR_HzXAkZDudZVM7pX86OMebEy704C_DWKTH9c"
curl -X POST  -H "Content-Type:application/json" http://localhost:8100/webapi/project --data-binary @multiDockerPro.json -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDUyMDE3LCJpYXQiOjE1MDU5NjU2MTcsImp0aSI6IjEifQ.wCHNAZR_HzXAkZDudZVM7pX86OMebEy704C_DWKTH9c"

curl  -X POST -H "Content-Type:application/json" http://192.168.101.8:8100/webapi/login --data-binary @user.json
curl -X POST  -H "Content-Type:application/json" http://192.168.101.8:8100/webapi/project --data-binary @WingrowMavenPro.json  -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2NTgyOTg1LCJpYXQiOjE1MDY0OTY1ODYsImp0aSI6IjEifQ.mwqNsyKrQGlTQO26BtnMgawBznaKfCMv_G4nszWhKj8"
curl  -X GET -H "Content-Type:application/json" http://192.168.101.8:8100/webapi/projectgroup -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2Mzk5NDk4LCJpYXQiOjE1MDYzOTc2OTksImp0aSI6IjEifQ.j7L0935PkKwCKOB-5clfz2GtxGx3qxiM0ecXxo2bqpk"

