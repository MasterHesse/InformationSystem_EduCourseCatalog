# HDU-ITMO Information System Laboratory Work

This is a Course Information Management System project for HDU-ITMO Information System Laboratory Work. 

## Build this site locally
### Front-End
1. Install Node
2. Run ```npm install``` to install the relevant dependencies
3. Run ```npm run dev``` to start the system front-end
4. Open http://localhost:5173 to see the system

> If your sever port is not 5173, you need to edit ```vite.config.ts``` and add code block:
    ```
        server: {
        port: 5173
    }
    ``` 

### Back-end
1. Install JDK 1.8
2. Install maven
3. Install Spring
4. Run ```mvn clean install``` to install the relevant dependencies
5. Run the ```EduCourseCatalogApplication.java``` to start the system's backend

### Database
1. Install tomcat
2. Install MySQL
3. Run ```ecc.sql``` in localhost:3306 to deploy the local database

## Background Knowledge
### Basic
- [Git][1]
- [Github][2]
- [Markdown][3]

### Advance
- [Vue][4] + [Vite][5]
- [pinia][8]
- [element-plus][9]
- [JavaScript][6]
- [Node][7]
- [Java][10]
- [Springboot][11]
- [Maven][12]

[1]:https://git-scm.com/
[2]:https://github.com/
[3]:https://markdown.com.cn/
[4]:https://cn.vuejs.org/
[5]:https://vitejs.cn/vite3-cn/guide/
[6]:https://developer.mozilla.org/en-US/docs/Glossary/JavaScript
[7]:https://nodejs.org/zh-cn
[8]:https://pinia.vuejs.org/zh/
[9]:https://element-plus.org/zh-CN/component/overview.html
[10]:https://www.oracle.com/java/
[11]:https://spring.io/projects/spring-boot
[12]:https://maven.apache.org/

## Functions
### Authority
#### 1. Login
#### 2. Logout
#### 3. Register

### Student
#### 1. Check courses
#### 2. Download syllabuses

### Teacher
#### 1. Check Courses
#### 2. Edit Courses
#### 3. Delete Courses
#### 4. Upload syllabuses
#### 5. Download syllabuses

### Admin
#### 1. Manage registration requests
#### 2. Manage identities

## Test Account

### Test-Student
- Account: s1
- Password: s1

### Test-Teacher
- Account: t1
- Password: t1

### Test-Admin
- Account: 123
- Password: 123