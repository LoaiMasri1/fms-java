# fms-java
File Management System project is a requirement for the advanced programming
 course.
 
## The Problem:
A file management system is a software program that helps a user organize and keep track of files on a computer or other device. It provides a way to store, organize, and locate files easily. Some features of a file management system include the ability to import, export, and delete files, and search for files.

## There are several reasons why file management systems are important:

- They help users organize their files in a logical and consistent manner, making it easier to find and access the files they need.

- They allow users to share files with others by giving them access to specific folders or files.

- They provide a way to back up important files to ensure that they are not lost in the event of a hardware failure or other disaster.

- They help users manage the storage space on their devices by allowing them to delete unnecessary files and move large files to external storage devices when needed.

Overall, file management systems help users to be more efficient and productive by allowing them to easily find and access the files they need and keep their devices organized.

# The Functional Requirements:

### File Repository:-
First, we create a FileSchema class that takes parameters with File.io type, then divides it to name, type, path, etc. then we called import file function.
- Import file:
We used two main libraries, MongoDB and its functionality to add the file information to Database, and File IO to make a copy for the file.
- Export file:
We used two main libraries, MongoDB and its functionality to get the path using name and type, then File IO to make a copy in the Document folder in your pc.
- Delete file:
We used two main libraries, MongoDB and its functionality to get the path using name and type, then File IO to delete the file by its path.

- File Classification:
We used MongoDB libraries to search on files, using different types of searching, like between two dates, sorted by size, etc.

- Version control for the files:
First, we create a version scheme, to store main info for versions, so if the user adds a file that already exists, he can make a new version, by MongoDB libraries to update the file info to the new version and sort the old version and new version to versions array in file document in the database, then we make a copy for the new version, with keeping the old version. 

# The non-functional requirements

All users should authenticate themself: the user can log in using username/password and there are three permissions in our system admin (can do everything), staff (import-export not overwrite), and the reader (export)


- The system should be secure: using the HashBase64 library when we add a new file to the database we encode the name, type, and path, when we want to access anything in the file we decoded it first and the user password stored in the database as decoded string (Bcrybt).



- The system should be available and accepts all requests coming to it within less than 1 second: Use the singleton design pattern for the database and logger to ensure that there is only one instance of each, which can improve performance and efficiency.

- The system should be scalable: To improve the scalability and performance of a system that stores tokens, you can use caching techniques to store frequently accessed tokens in a temporary location, such as memory or a cache server.

- The application should solve the conflict in case two users try to modify the same file in parallel: using the synchronized keyword to ensure that only one thread can access the file at a time. This will prevent conflicts and ensure that the file is updated consistently.

- For debugging/accountability: use a logging framework such as Log4j or java.util.logging. This will allow you to capture important information about every action taken in the system, which can be useful for debugging and accountability purposes.

- 90% of the users are reader users: I made use by default is a reader and there is a role called database manager who has given the user his role of admin or staff

### Concrete Design:
![image](https://user-images.githubusercontent.com/90678867/210150607-6adac334-28e9-4188-b8f6-94273e7ab8ae.png)


