application.properties

spring.application.name=shoppingmall

spring.datasource.url=jdbc:mysql://127.0.0.1:3307/shopdb?serverTimezone=Asia/Seoul&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true
// spring.datasource.username=shopuser
// spring.datasource.password=P@ssw0rd
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.thymeleaf.cache=false

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
