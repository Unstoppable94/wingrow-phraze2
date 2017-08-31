
FROM 10.0.2.50/library/openjdk:8-jdk-alpine
COPY target/cicd-jetty-jar-with-dependencies.jar /cicd-jetty-jar-with-dependencies.jar
RUN mkdir -p /wingrow_data/config && mkdir -p /wingrow_data/projectGroup && mkdir -p /wingrow_data/projects && mkdir -p /wingrow_data/deletedProjects
ENV WINGROW_DATA="/wingrow_data"  \
		JENKINS_MASTER="http://192.168.101.6:8080/" \
		WINGARDEN_URL="http://192.168.101.2:8080/" \
		SECRETKEY="i21bRJAQE4PcAUFzHnQGHxHncbvL5TuzaU35UiGZ" \
		ACCESSKEY="47551EC490F934813DE0" \
		ENVIRONMENT="1a5" \
		JENKINS_PASSWORD="w12sedwiokd" \
		JENKINS_USERNAME="jenkins" 
CMD ["java","-jar","cicd-jetty-jar-with-dependencies.jar"]	
