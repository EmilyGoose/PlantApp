# PlantApp

Created for UofTHacks IX

## Inspiration
Throughout the course of the pandemic, many people have gravitated towards owning plants as relatively low-cost and low-maintenance companions. New plant owners can find it overwhelming to keep track of the types of all the plants they own, as well as their watering schedules and specific needs. Our goal was to design an Android application to aid new plant parents, and to restore their ability to care for their plants, and more importantly themselves.

## What it does
PlantApp includes a variety of tools to help new plant owners tend to their garden.
* Unique user register/login, allowing users to see their garden from any Android device.
* An implemented [Plant id API](https://github.com/flowerchecker/Plant-id-API) to allow users to identify their plants by uploading a photo.
* A dedicated garden screen which allows users to see a quick overview of their plants and when to water them next.
* A plant search bar which enables users to search for information such as common names, a general description, and care tips for any given plant.

## Technology we used
We decided on creating an Android Application in Kotlin and XML as most of us had no experience with mobile development and wanted to try something new. We utilized Firebase Authentication and Realtime Database for user registration/authentication and database management respectively. We used MediaWiki's [API](https://www.mediawiki.org/wiki/API:Main_page) as well as the aforementioned Plant id API to handle getting info about the plants as well as identifying them.

## Challenges we ran into
a lot of them :^)

## Accomplishments we're proud of
placeholder text
## What we learned
placeholder text
## What's next for PlantApp
We had two ideas that we liked for PlantApp that we realized were outside of the scope of what we could accomplish in 36 hours that we could build off of.

Firstly, we had the idea of implementing a community aspect to the application. This would entail being able to find and connect with other nearby users to exchange seeds, grafts, and cuttings of their plants. Additionally we'd implement a system where users can add their personal tips and tricks to the info page for various plants, that other users could then up/downvote on.

Secondly, the Plant id API has the capability of identifying diseased plants and providing treatment tips. We'd look to utilize that functionality to create a personalized treatment plan for each diseased plant for users to restore their plant back to a healthy condition.
