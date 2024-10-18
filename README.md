Project Description: Java Chat System

Project Overview: This project is a Java-based chat application that simulates real-time communication between users. The system models essential components of a chat platform, such as contacts, chats, messages, and users. Users can send messages, create and manage group chats, and track interactions with other users through a detailed contact list. The project emphasizes object-oriented design, efficient data management, and basic user persistence.

Key Features:

User and Contact Management:

Users are represented as objects that can manage contacts. Contacts are stored in a list and sorted alphabetically for ease of access.
Users can add, remove, and interact with their contacts.
Contact details include name, phone number, and profile image, which can be customized or defaulted.
Chat Functionality:

Users can create chat groups, name them, and add contacts as members.
A user can be a part of multiple chats, and each chat can store multiple messages.
Users can send messages to individual contacts or groups.
Messaging System:

Messages contain content, author information, and timestamp data.
Messages can be marked as read or unread.
Users can react to messages using "like" or "dislike" functionality.
Messages track reactions and show counts for each reaction type.
Recent messages (up to the last three) are tracked for each user.
Data Persistence:

The system supports saving user data to a file and loading it back, ensuring continuity between sessions.
Users, along with their contact lists and chats, can be saved and retrieved through serialization.
Profile and Image Handling:

Each user and contact can have a profile image.
The system uses ImageProcessing to load images from the file system, including default images for users without custom profile pictures.
Class Structure:

User: Extends Contact, adds user-specific features like maintaining a contact list and chats. The user can create chats, send messages, and save/load their data.
Contact: Represents an individual in the chat system. Stores name, phone number, profile image, and a history of recent messages.
Chat: A collection of Contact members and Message objects. Supports adding/removing members and sending messages within the chat.
Message: Represents the text or content sent in chats. Includes reactions (like/dislike), author, and status (read/unread).
ImageProcessing: Provides functionality for loading and handling profile images.
Main Features in Code:

User and Contact Management:

Users can add contacts and maintain a sorted list of contacts.
Users can create new chats and add members.
Messaging and Chats:

The system supports adding messages to chats and handling reactions.
Unread messages can be tracked, and once a chat is accessed, the messages are marked as read.
Data Persistence:

Users can save their session data to a file and load it in the next session, allowing for continuity between user logins.
Image Management:

Profile images are handled via the ImageProcessing class, which allows users to set custom profile pictures or fall back to a default image.
My Role

For this project, I was responsible for back-end development and initial system design. My specific tasks included:

System Design: I structured the application using object-oriented principles and created core classes like User, Contact, Chat, and Message.
Back-End Development: I implemented key functionalities such as user and contact management, chat creation, message sending, and reaction handling.
Data Persistence: I set up file-based data storage through serialization to ensure continuity between user sessions.
Image Management: I developed the image handling feature, allowing users to set profile pictures and manage default images.
This chat system demonstrates fundamental principles of object-oriented programming, data persistence, and file handling. It provides a flexible platform for future extensions into more advanced features like real-time communication and a graphical interface.
