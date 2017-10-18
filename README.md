# BRAIN-Display

The Band Room Automated INformation Display is an application for the purpose of displaying music lesson schedules for my high school band. It was designed to replace the antiquated whiteboard schedule which needed to be tediously redrawn every week. 

## What does it need?
The "BRAIN" relies on several dependency files which it will create the first time it launches.  These files are created and must be found within the same directory as the executable JAR file that the application is launched from.  

**settings.txt** Contains a text representation of the settings dictionary that the application uses to configure the UI and load the appropriate data.  It keeps track of which lesson data file to display, the display theme, and whether to load a secondary lesson file over the primary one for a given date range.

**log.txt** Contains any output that the application provides while it is running.  This could be any errors that the application encounters or notification that a particular process has occurred.

**themes.txt** Contains a list of all of the visual themes that the display can take on.  New ones can be designed from within the Theme Editor in the application.  The Settings text file will reference the data in here by the name of the theme.

**ScheduleFiles/** A folder where all lesson data files must go.  Multiple files can be placed in this folder and the application will retrieve the data here.



## How does it work?

### Startup ###
When launched, the BRAIN Display will do the following:
1. Check if all of its dependencies exist.  If they do not, it will create theme with default values.
2. Construct the UI.
3. Build the date system.
4. Display the lessons that are scheduled during the days picked by the date system.
5. Begin a timer to keep the display updated when the date changes.

### Parsing Lesson Data ###
A given schedule data file is expected to contain a list of lessons, one per line, with the format: "date,group,period".  An example could be "10/18,WE Trumpet A,2" which would represent a lesson scheduled on October 10th for the WE Trumpet A group during period 2.

The **LessonFileParser** class is responsible for reading the current lesson file (specified in the settings file) and parsing it into an ArrayList of **Lesson** objects – one Lesson per line in the text file.  Another thing to note is the group names are passed through an instrument shortening method that way the display doesn't look too cramped.  For example, if a lesson group is "WE Euphonium", it will be shortened to "WE Euph".

At any time, other parts of the application can request the ArrayList of Lesson objects or have the file reparsed if necessary.

### Getting the Dates ###
The **DateManager** class is responsible for determining the four dates to display.  Starting with the current day, the date manager will try to determine the next four days that have lessons scheduled.  If *today* has a lesson scheduled, it will be saved as a schedule date to display.  From here, the date manager keeps looking one day forward to see if a lesson is scheduled on that date.  When four dates are found with corresponding scheduled lessons, the date manager stops looking for days. 

### Getting the Lessons ###
[ in progress ]

### Settings ###
[ in progress ]
