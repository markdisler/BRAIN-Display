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


### Getting the Dates ###


### Getting the Lessons ###

### Settings ###
