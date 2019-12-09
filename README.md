# studentmicroservice
Student micro service

Description : Designed course and student microservices as per CodingTest-Microservice.pdf


1) MYSQL database
   ======================
    * install mysql database in local system
    * create account 
    * create database : testdb    
    * Entity will be added automatically by spring bootstrap class   

      Tables
      -------
     * select * from students;
     * select * from courses;
     * select * from  students_courses;
     
2) RabbitMQ
   =============
    * Student micro service will be reciever/consumer of queue StudentAQ and StudentRQ
  

3) Run spring boot application by maven run configuration
   =========================================================

       * clean install spring-boot:run


4) Verify the output via POSTMAN
   =================================

         * GET METHOD    : http://localhost:8080/student/get?courseID=3
         * DELETE METHOD : http://localhost:8080/student/remove?studentID=1            
         * POST METHOD   : http://localhost:8080/student/add?courseID=4
                 * REQUEST PAYLOAD :  {  "name": "syed",   "age": 37 }
                 * Content-type : application/json

