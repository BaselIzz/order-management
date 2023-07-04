# order-management
Final Project OF Spring Boot


# Docker 

//First we need to  Make image to mysql 
docker pull mysql:8

//to l now the image name  then verify if the Sql is exist 
docker images 

// it will create the springboot server and sql 

docker network create springboot-mysql-net


// then see if the network created 
docker network ls

// run the database and connect the sql with my data base  it will also create table 
docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=jwt_security -e MYSQ
L_USER=sa -e MYSQL_PASSWORD=root mysql:8


// create a image of java spring project 

docker build -t springbootmsql .

// run the container  with sql conection  it i will return the name of contaner  
docker run --network springboot-mysql-net --name springboot-container -p 8080:8080 -d springbootmsql


//check if its work  on the 
docker logs  cca 







