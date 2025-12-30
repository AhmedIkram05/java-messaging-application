# Java Messaging Application

## 🚀 Overview
A feature-rich desktop messaging application built with Java Swing that provides real-time communication capabilities. This chat system simulates modern messaging platforms with support for individual and group chats, contact management, message reactions, and persistent data storage. The application emphasizes clean object-oriented design, intuitive user interface, and efficient data management.

## 🧠 Tech Stack
- **Language:** Java (Swing for GUI)
- **UI Framework:** Java Swing with custom components
- **Graphics:** Java AWT, Custom graphics utilities
- **Data Persistence:** Java Serialization
- **Design Patterns:** MVC, Observer Pattern (PropertyChangeListener)

## 📊 Features

### Core Messaging
- ✉️ **Individual & Group Chats:** Create private conversations or group chats with multiple contacts
- 💬 **Real-time Messaging:** Send and receive messages with timestamp tracking
- 👀 **Read/Unread Status:** Track message read status with visual indicators
- 👍 **Message Reactions:** React to messages with like/dislike functionality
- 🕒 **Message History:** View complete conversation history with scrollable interface
- 😊 **Emoji Support:** Integrated emoji picker for expressive communication

### Contact Management
- 📇 **Contact List:** Add, remove, and manage contacts with profile information
- 🔤 **Alphabetical Sorting:** Automatically sorted contact lists for easy access
- 📱 **Contact Details:** Store name, phone number, and custom profile images
- 👤 **Profile Images:** Custom or default profile pictures for each user/contact
- 🔍 **Recent Conversations:** Track last three messages with each contact

### User Interface
- 🎨 **Modern Design:** Clean, intuitive interface with custom-styled components
- 📱 **Responsive Layout:** Adaptive panels that adjust to different screen sizes
- 🗂️ **Multiple Views:** Switch between contacts, chats, and user info panels
- 🎯 **Interactive Elements:** Custom buttons, cards, and dialogs
- 🔄 **Navigation:** Bottom menu panel for easy view switching

### Data Management
- 💾 **Session Persistence:** Save and load user data between application sessions
- 📂 **File Serialization:** Store contacts, chats, and messages to disk
- 🔐 **User Profiles:** Maintain individual user profiles with complete chat history
- ♻️ **Data Continuity:** Resume conversations from previous sessions

## 🏗️ Architecture

### Key Classes
- **User:** Extends Contact with chat management and data persistence capabilities
- **Contact:** Represents individuals with profile info and message history
- **Chat:** Manages group conversations with members and message lists
- **Message:** Encapsulates message content, metadata, and reactions
- **MainWindow:** Primary GUI controller implementing MVC pattern
- **BackendImplementation:** Business logic layer for chat operations

### UI Components
- Custom Swing components: MessageCard, ContactCard, ChatCard
- Specialized panels: ChatsPanel, ContactsPanel, MessagesPanel
- Dialogs: CreateNewContactDialog, ChooseContactsDialog
- Utilities: GraphicsUtils, JFlatButton for modern UI elements

## 📈 Results
- ✅ **Full-Featured Messaging:** Complete implementation of modern chat functionality
- 🎯 **Object-Oriented Design:** Clean separation of concerns with well-defined classes
- 💪 **Robust UI:** Responsive interface with 1000x1000px default window size
- 📊 **Scalable Architecture:** Supports multiple users, unlimited contacts and chats
- 🔄 **Session Management:** Seamless data persistence across application restarts

## 🧪 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- IDE with Java support (IntelliJ IDEA, Eclipse, or NetBeans) or command line tools

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd java-messaging-application-1
   ```

2. **Compile the source files:**
   ```bash
   cd src
   javac *.java
   ```

3. **Run the application:**
   ```bash
   java Main
   ```

### Alternative: Using an IDE
1. Open the project in your preferred Java IDE
2. Ensure the `src` directory is marked as the source root
3. Run the [Main.java](src/Main.java) file

### First Launch
- On first run, the application creates a default user profile
- The window will be centered on your screen
- Use the bottom menu to navigate between Contacts, Chats, and User Info panels

## 🎯 Usage

### Creating a New Contact
1. Navigate to the Contacts panel
2. Click the "+" button
3. Enter contact name and phone number
4. Optionally add a profile image

### Starting a Chat
1. Select a contact from your contact list
2. Click "Start Chat" or use existing chat
3. Type your message in the input field
4. Press Send or hit Enter

### Creating a Group Chat
1. Navigate to Chats panel
2. Click "New Group Chat"
3. Select multiple contacts
4. Name your group and start messaging

### Managing Messages
- Click 👍 or 👎 to react to messages
- View reaction counts on each message
- Scroll through message history
- Check read/unread status indicators

## 👨‍💻 Development
This project was developed as part of AC12001 coursework, demonstrating proficiency in:
- Java Swing GUI development
- Object-oriented programming principles
- Event-driven architecture
- Data persistence and serialization
- UI/UX design for desktop applications

**Note:** This is a desktop simulation of a messaging platform and does not include actual network communication or real-time server connectivity.