# evergreen

Created for UofTHacks IX

App Home Screen           |  Detailed Plant View
:-------------------------:|:-------------------------:
![App Home Screen](https://github.com/EmilyGoose/PlantApp/raw/main/screenshots/HomeScreen.png) | ![App Plant Detail](https://github.com/EmilyGoose/PlantApp/raw/main/screenshots/PlantDetail.png)

## Inspiration
Throughout the course of the pandemic, many people have gravitated towards owning plants as relatively low-cost and low-maintenance companions. New plant owners can find it overwhelming to keep track of the types of all the plants they own, watering schedules, and specific plant needs on top of their personal lives. Our goal was to design an Android application to aid new plant parents and help restore the livelihood of their plants by improving access to better care instructions. The time saved and hassle streamlined through use of evergreen most importantly restores the user's ability to allocate time to care for themself as well.

## What it does
evergreen includes a variety of tools to help new plant owners tend to their garden.
* Unique user register/login, allowing users to see their garden from any Android device.
* An implemented [Plant.id API](https://github.com/flowerchecker/Plant-id-API) to allow users to identify their plants by uploading a photo.
* A dedicated garden screen which allows users to see a quick overview of their plants.
* A plant search bar which enables users to search for information such as common names, a general description, and care tips for any given plant.
* A photo-based search to identify plants and provide information for the given plant.


## Technology we used
* Android SDK
* Firebase Authentication
* Realtime Database
* Plant.id API
* MediaWiki API

We decided on creating an Android Application in Kotlin and XML as all but one of us do not have experience with mobile development, and were looking to tackle an exciting development stack to try something new. We utilized Firebase Authentication and Realtime Database for user registration/authentication and database management respectively. We used MediaWiki's [API](https://www.mediawiki.org/wiki/API:Main_page) as well as the aforementioned Plant.id API to handle retrieving info about the plants and to identify them.


## Challenges we ran into
A major challenge encountered during the development of evergreen was learning to integrate android apps with both external and native APIs.  APIs as a whole are something only one member of the development team had experience with, so it was important to ensure the entire team was able to learn how the app worked inside and out for our own personal growth.  The entire team was able to develop a better understanding of APIs and the data they use to communicate with the system through this project.

Implementing a community component into the app also proved to be more challenging than what could be tackled within a 36 hour period.  Additionally, learning how to work with the camera function in order to visually identify plants in addition to text-based search was difficult for our team.

Learning a completely new software stack and development platform for 3/4 of our team members was the largest challenge of all.  Though we all had experience with Java, Kotlin was a new and exciting tool for us to learn how to utilize alongside XML. Integrating APIs within the coded functionality of the was an unforeseen difficulty that yielded significant benefits to the overall functionality of the app in a way that could not have been achieved otherwise. Our team's development experience has been limited to web applications or simple command line interface programs, so this was a huge step up in scope for us and we are incredibly proud of what we were able to accomplish.


## What's next for evergreen
evergreen is intended to be a one-stop hub for all your plant care and planting-community needs.  

The scope of evergreen is to be further expanded to implement a community aspect to the application. This would entail being able to find and connect with other nearby users to exchange seeds, grafts, and cuttings of their plants. Additionally we'd implement a system where users can add their personal tips and tricks to the info page for various plants, that other users could then up/down vote to curate the best information for care.

Additionally, the Plant.id API also has the capability to identify plants with diseases by photo and provide treatment tips.  This functionality is also to be added in future updates to evergreen, to further aid the user in restoring the health of their plants with convenience and ease through providing a personalized and comprehensive treatment plan.


# Building the app

This app uses [Firebase](firebase.google.com). Create a new project and enable password authentication, as well as read/write access to the real-time database. Put the `google_services.json` provided into the `app` directory.

This app also uses the [Plant ID API](github.com/flowerchecker/Plant-id-API). Request an api key from them, then create a file named `apikey.properties` in the project root directory with the following:

```properties
PLANT_API_KEY="apikey"
```
