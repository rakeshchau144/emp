# Employee Management System - Spring Boot Application

This Spring Boot application manages employees with functionalities to add, 
retrieve, update, and delete employees. It also includes advanced features 
like notifying the level 1 manager via email on new employee addition.

Step-1: Connect your Mysql Database
-----	spring.datasource.url=jdbc:mysql://localhost:3306/employee
	spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
	spring.datasource.username=your_user_name
	spring.datasource.password=your_password
	spring.jpa.hibernate.ddl-auto=update
	spring.h2.console.enabled=true
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true
-----use this in your application.properties file--------


Step-2: Connect Email:-
	spring.mail.host=smpt.gmail.com
	spring.mail.port=587
	spring.mail.username=your_mail
	spring.mail.password=password
	spring.mail.properties.mail.smtp.auth=true
	spring.mail.properties.mail.smtp.starttls.enable=true
-------------------------------------------------------------

Step-3 Extract Zip file open in Intellij IDEA
Step-4 Run EmployeeApplication file ----

Step-5 Use this link for check all endPoint of API(http://localhost:8080/swagger-ui/index.html#/) 
or You can check also in postman.........
