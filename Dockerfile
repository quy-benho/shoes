FROM maven
	
	WORKDIR /opt/app
	
	COPY . .
	
	RUN mvn clean
	
	ENTRYPOINT ["mvn","spring-boot:run"]
