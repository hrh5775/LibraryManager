# LibraryManager

Git project in Intellij:

1. Download IntelliJ Ultimate
2. Install Intellij
3. Install [Git](https://git-scm.com/downloads)
4. Start IntelliJ
5. Integrate Git and GitHub Plugin
6. Checkout project from repository url
7. Start working :D

The git commands are in the bottom right corner of Intellij.

Or use a [Git](https://git-scm.com/downloads) client and a preffered IDE.

## Build
**Execute the following commands in the main directory of the cloned git repository**
**Maven has to be installed!**  
cd Domain    
mvn clean install  
cd ../Hibernate  
mvn clean install  
cd ../Application  
mvn clean install  
cd ../Connector  
mvn clean install  
cd ../Server  
mvn clean install  
cd ../Client  
mvn clean install

# Create installable file
1. Install [Bitrock's InstallBuilder](https://installbuilder.bitrock.com/). You can download it [here](https://installbuilder.bitrock.com/download-step-2.html).
2. Open the *.xml in one of the subfolder of "Installers".
3. Place your files in the appropriate folders. Search for Readme.txt in the folder "Installers" if you are unclear what you should add.
4. Create a setup file for every platform you desire.

## Maven
install [Maven](https://maven.apache.org/download.cgi) for the automated build process

## Project structure
* Server
  * Contains the logic for the data validation
  * Uses Servlets for the communication with the Client
  * Communicates with the database
  * Uses Java 8, HTML5, CSS3, JavaScript
* Client
  * Loads the data from the Server
  * Does rudimentary data validation
  * Provides a GUI
  * Uses Java8 and JavaFX
* Hibernate
  * Provides our Server with an eayse to use API

## Repository
Try to avoid adding binary files such as \*.pdf, \*.docx, \*.zip, ... .

Make always the comment clear and understandable for other persons.

Try to make small commits to make it easier to find the differences when a merge error occurs.

## IDE
Use Maven for dependencies where possible to avoid unneccessary build errors with different versions of a library.

## Tests
Describe and show how to run the tests with code examples.

## Coding conventions
Use the [Google style guide](https://google.github.io/styleguide/javaguide.html)

## README syntax
[Documentation](https://enterprise.github.com/downloads/en/markdown-cheatsheet.pdf)
