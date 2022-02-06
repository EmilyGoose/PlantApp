# evergreen

Created for UofTHacks IX

## Inspiration
Throughout the course of the pandemic, many people have gravitated towards owning plants as relatively low-cost and low-maintenance companions. New plant owners can find it overwhelming to keep track of the types of all the plants they own, as well as their watering schedules and specific needs on top of their personal lives. Our goal was to design an Android application to aid new plant parents and help restore the livelihood of their plants by improving access to better care instructions.  The time saved and hassle streamlined through use of evergreen most importantly restores the user's ability to allocate time to care for themself as well.

## What it does
evergreen includes a variety of tools to help new plant owners tend to their garden.
* Unique user register/login, allowing users to see their garden from any Android device.
* An implemented [Plant id API](https://github.com/flowerchecker/Plant-id-API) to allow users to identify their plants by uploading a photo.
* A dedicated garden screen which allows users to see a quick overview of their plants and when to water them next.
* A plant search bar which enables users to search for information such as common names, a general description, and care tips for any given plant.

## Technology we used
* Android SDK
* Firebase Authentication
* Realtime Database
* Plant.id API
* MediaWiki API

We decided on creating an Android Application in Kotlin and XML as all but one of us experience with mobile development, and were looking to tackle an exciting new development stack to try something new. We utilized Firebase Authentication and Realtime Database for user registration/authentication and database management respectively. We used MediaWiki's [API](https://www.mediawiki.org/wiki/API:Main_page) as well as the aforementioned Plant.id API to handle retrieving info about the plants and to identify them.

## Challenges we ran into
a lot of them :^)

## What we learned
- everyone should probably outline something specific they are proud of and we can put this on the devpost too
- coordinating remote work
- fragments vs activities lol


## What's next for evergreen
evergreen is intended to be a one-stop location for all your plant care and community needs.  

The scope of evergreen is to be further expanded to implement a community aspect to the application. This would entail being able to find and connect with other nearby users to exchange seeds, grafts, and cuttings of their plants. Additionally we'd implement a system where users can add their personal tips and tricks to the info page for various plants, that other users could then up/down vote to curate the best information for care.

Additionally, the Plant.id API also has the capability to identify diseased plants and provide treatment tips.  This functionality is also to be added in future updates to evergreen, to further aid the user in their tasks to restore the health of their plants with convenience and ease by providing a personalized and comprehensive treatment plan.

# Building the app

This app uses [Firebase](firebase.google.com). Create a new project and enable password authentication, as well as read/write access to the real-time database. Put the `google_services.json` provided into the `app` directory.

This app also uses the [Plant ID API](github.com/flowerchecker/Plant-id-API). Request an api key from them, then create a file named `apikey.properties` in the project root directory with the following:

```properties
PLANT_API_KEY="apikey"
```
