# *flicks*

**flicks** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **60** hours spent in total

## User Stories

The following functionality is completed:

* User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.
* Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* For each movie displayed, user can see the following details:
  * Title, Poster Image, Overview (Portrait mode)
  * Title, Backdrop Image, Overview (Landscape mode)
* Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* Improve the user interface through styling and coloring
* When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * Overlay a play icon for videos that can be played.
    * More popular movies should start a separate activity that plays the video immediately.
    * Less popular videos rely on the detail page should show ratings and a YouTube preview.
* Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* Apply rounded corners for the poster or background images using [Glide transformations](https://github.com/wasabeef/glide-transformations)
* Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) networking libraries.
* Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) to display results
* Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the Movie Database API.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

[Link to GIF Walkthrough](https://i.imgur.com/bsKmaeS.gif)

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Butterknife](http://jakewharton.github.io/butterknife/) - Popular View "injection" library for Android
- [Glide](https://github.com/bumptech/glide) - Image Loader Library for Android developed by bumptech
- [GSON](https://github.com/google/gson) - Java serialization/deserialization library to convert Java Objects into JSON and back 
- [Parceler](https://github.com/johncarl81/parceler) - Code generation library that generates the Android Parcelable boilerplate source code
- [Retrofit networking](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java

## License

    Copyright 2019 Winnie Yang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
