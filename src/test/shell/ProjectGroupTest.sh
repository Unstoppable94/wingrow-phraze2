#json
{"stat":{"total":3,"failed":1,"running":0,"inqueue":0,"nobuilt":0,"success":1,"aborted":1},"projects":[{"id":"k8s","projectUrl":"todo/k8s","status":"aborted","lastBuild":1491034812446,"duration":1523415,"artifact":null,"imageCmd":null},{"id":"k8s-steps","projectUrl":"todo/k8s-steps","status":"failed","lastBuild":1490973287827,"duration":305893,"artifact":null,"imageCmd":null},{"id":"maskio","projectUrl":"todo/maskio","status":"success","lastBuild":1491357931049,"duration":18123,"artifact":null,"imageCmd":null}],"name":"test2","description":"modifytest","createtime":1491448239957,"lastModifyTime":1491450026956}
#cmd

curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/projectgroup/test --data-binary @test.json


curl  -X PUT -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/projectgroup/%E9%BB%98%E8%AE%A4 --data-binary @pb.json



curl  -X POST -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/projectgroup/test2 --data-binary @test2.json

curl  -X DELETE -H "Content-Type:application/json" http://localhost:8080/cicdweb/webapi/projectgroup/test2 --data-binary @test2.json

