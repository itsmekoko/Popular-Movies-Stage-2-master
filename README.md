# Popular Movies Stage 2 app by Marian Cocota for Udacity Android Developer Nanodegree

## Project Overview
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. 
In this project, we’ll build an app that allows users to discover the most popular movies playing.
## What can you learn from this project?
- You will fetch data from the Internet with theMovieDB API.
- You will use adapters and custom list layouts to populate list views.
- You will incorporate libraries to simplify the amount of code you need to write
- You will build a fully featured application that looks and feels natural on the latest Android operating system.
### The core experience:
   - Present the user with a grid arrangement of movie posters upon launch.
   - Allow your user to change sort order via a setting.
   - The sort order can be by most popular or by highest-rated.
   - Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
     - Original title
     - Movie poster image thumbnail
     - Plot synopsis (called overview in the api)
     - User rating (called vote_average in the api)
     - Release date
### Adding additional functionality to build a fully featured application that looks and feels natural on the latest Android operating system.
  - We’ll allow users to view and play trailers (either in the youtube app or a web browser).
  - We’ll allow users to read reviews of a selected movie.
  - We’ll also allow users to mark a movie as a favorite in the details view by tapping a button (star).
 -  We'll make use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle) to create a robust an efficient application.
 -  We'll create a database using Room to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
-  We’ll modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
##### Instruction to run the app
Put your api key in a string resource named "api_key".
You can get your api key by signing up here: https://developers.themoviedb.org/3/getting-started/introduction