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

The application have the functions of creating a new student and a new tutor, changing the username and password for both, searching and filtering for student to find tutors, a chat part where the student can contact with the teacher, a schedule page where both the teacher and the student can mark the timeslots they are busy or remove the marks. There is a recommendations page on the student menu where the student can see course details and tutor info so that the student can send message if required.

What remains to be done is the saving of the timeslots that are marked to the database.

Task Assignment : 
Beren Irgat: General database logic and table relations
Elif Su Temirel: Functions that are related to the database information and relate the method with current information
Elif Duman: Functions that are related to the database information and relate the method with current information
Barış Sağlam: Essential functions (mostly about the appearance of UI)
Nazim Nasibli: Essential functions (mostly about the appearance of UI)

Technical Details : 
In the project the version of Java is 22 while the version of JavaFX is 22.0.1 JavaFX jar files are added to the libraries. To set up the project in your computer you should make some changes in the pom.xml file. In the line "<maven.compiler.source>22</maven.compiler.source>" under properties the 22 must be changed with your own Java version and <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>22.0.1</version>
        </dependency>
        In this dependency of JavaFX, you should change the 22.0.1 with your javaFX version.






