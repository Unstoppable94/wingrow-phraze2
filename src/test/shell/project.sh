curl  -X POST -H "Content-Type:application/json" http://localhost:8100/webapi/login --data-binary @user.json


curl -X GET -H "Content-Type:application/json" "http://localhost:8100/webapi/project?projectName=docker&projectType=traditionalDocker"  -H "authorization:eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTA2MDQ5ODgxLCJpYXQiOjE1MDU5NjM0ODEsImp0aSI6IjEifQ.VArfzeQ_-i-egfYz5CdM3qdFmLFHBPiCd6iSgkt_s1M"
newWorkflow
curl -X GET -H "Content-Type:application/json" "http://localhost:8100/webapi/project?action=newWorkflow&projecttype=mavenProject"


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
curl -X POST  -H "Content-Type:application/json" http://localhost:8100/webapi/project --data-binary @pro.json


curl -X POST  -H "Content-Type:application/json" http://localhost:8100/webapi/project --data-binary @newMavenPro.json
curl -X POST  -H "Content-Type:application/json" http://localhost:8100/webapi/project --data-binary @multiDockerPro.json



