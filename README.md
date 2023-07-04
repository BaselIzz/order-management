# order-management
Final Project OF Spring Boot


# Docker 

First we need to  Make image to mysql 
```bash
docker pull mysql:8
```
To l now the image name  then verify if the Sql is exist 
```bash
docker images 
```
It will create the springboot server and sql 
```bash
docker network create springboot-mysql-net

```
Then see if the network created 
```bash
docker network ls
```
Run the database and connect the sql with my data base  it will also create table 
```bash
docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=jwt_security -e MYSQ
L_USER=sa -e MYSQL_PASSWORD=root mysql:8
```

Create a image of java spring project 
```bash
docker build -t springbootmsql .
```
Run the container  with sql conection  it i will return the name of contaner  
 ```bash
docker run --network springboot-mysql-net --name springboot-container -p 8080:8080 -d springbootmsql
```

Check if its work  on the 
```bash
docker logs  <Name of the Created Container > 
```






