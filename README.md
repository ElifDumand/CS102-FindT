CS 102 Project FindT
Group Name: Yurt 7ler
Members:
Beren Irgat 
Elif Duman
Elif Su Temirel
Nazim Nasibli
Barış Sağlam

Project Description : 
FindT is a desktop application that serves the purpose of helping students find tutors in subjects they lack or are interested in, as well as assisting tutors in reaching students and thus giving them additional job opportunities. This application is for students who want to find tutors easily in various subjects, parents who want suitable tutors for their younger children, or tutors who wish to give lessons in their expert subjects.
The application has the functions of creating a new student and a new tutor, changing the username and password for both, searching and filtering for students to find tutors, a chat part where the student can contact the teacher, a schedule page where both the teacher and the student can mark the timeslots they are busy or remove the marks. There is a recommendations page on the student menu where the student can see course details and tutor info so that the student can send a message if required.
What remains to be done is the saving of the timeslots that are marked to the database.

Task Assignment : 
Beren Irgat: General database logic and table relations
Elif Su Temirel: Functions that are related to the database information and relate the method with current information
Elif Duman: Functions that are related to the database information and relate the method with current information
Barış Sağlam: Essential functions (mostly about the appearance of UI)
Nazim Nasibli: Essential functions (mostly about the appearance of UI)

Technical Details : 
We used VSCode to write our project in and used MySQL for the database. In the project, the version of Java is 22 while the version of JavaFX is 22.0.1 JavaFX jar files are added to the libraries. To set up the project on your computer you should make some changes in the pom.xml file. In the line "<maven.compiler.source>22</maven.compiler.source>" under properties the 22 must be changed with your own Java version and <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>22.0.1</version>
        </dependency>
 In this dependency of JavaFX, you should change the 22.0.1 with the JavaFX version on your computer. Since we have a local database to run the program the person must download mySQL to their computer and create the database tables like we did. The database relations and tables are shown below with an EER diagram to help set up:

Below are the mySQL database export for setting it up on your computer
create table student( studentid int auto_increment primary key, name varchar(45) not null, password varchar(45), email varchar(60), biography varchar(300));
create table subject( subjectid int auto_increment primary key, subjectname varchar(45) not null);
create table tutor (tutorid int auto_increment primary key, name varchar(45) not null, password varchar(45), email varchar(60), biography varchar(300), price int, university varchar(50));
alter table tutor add column subjectid int, add constraint fk_subject foreign key (subjectid) references subject(subjectid);
create table messages( messageid int auto_increment primary key, sender_type ENUM('tutor', 'student') not null, senderid int not null, receiver_type ENUM('tutor', 'student') not null, receiverid int not null, messagebody varchar(1000) not null, constraint fk_sender_tutor foreign key (senderid) references tutor(tutorid) on delete cascade, constraint fk_sender_student foreign key (senderid) references student(studentid) on delete cascade,
constraint fk_receiver_tutor foreign key (receiverid) references tutor(tutorid) on delete cascade, constraint fk_receiver_student foreign key (receiverid) references student(studentid) on delete cascade);
create table timeslot( timeslotid int auto_increment primary key, tutorid int not null, studentid int not null, foreign key (tutorid) references tutor(tutorid) on delete cascade, foreign key (studentid) references student(studentid) on delete cascade);
create table tutor_subject(tutor_subject_id int auto_increment primary key, tutorid int not null, subjectid int not null, foreign key( tutorid) references tutor(tutorid) on delete cascade, foreign key (subjectid) references subject(subjectid));
ALTER TABLE timeslot ADD COLUMN timeSlotTime VARCHAR(100);
create table courses( courseid int auto_increment primary key, coursename varchar(50) not null, subjectid int, foreign key (subjectid) references subject(subjectid));
create table tutor_courses( tutorid int, courseid int, primary key(tutorid, courseid), foreign key (tutorid) references tutor(tutorid), foreign key (courseid) references courses(courseid));
create table student_courses( studentid int, subjectid int, primary key(studentid, subjectid), foreign key (studentid) references student(studentid) on delete cascade, foreign key (subjectid) references subject(subjectid) on delete cascade);
ALTER TABLE tutor ADD COLUMN university VARCHAR(50) NOT NULL;
alter table student drop column biography;
alter table tutor drop column biography;
alter table tutor drop column subjectid;
SET FOREIGN_KEY_CHECKS = 1;
ALTER TABLE tutor DROP FOREIGN KEY fk_subject;
ALTER TABLE tutor DROP COLUMN subjectid;
alter table tutor add column subjectname varchar(50) not null;
SET FOREIGN_KEY_CHECKS = 0;




