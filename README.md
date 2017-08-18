# Pre-work - *Simple Todo Android App*

**Name of your app** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Shusheng Lu**

Time spent: **3** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are to be implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/qgG9I1M.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** The development platform needs some effort of learning at the beginning, but the overall architecture is very neat. Every activity is associated with a .xml layout file, and all activities are registered in the manifest. This design decouples the UI and business logic, makes collaboration between developer and designer easier. Google's Android doc is also very descriptive, and the online community is active and resourseful. Both making learning and troubleshooting very easy and fun.
            I am recently working with JavaFX. Both JavaFX and Android use xml or xml derived code, and have GUI tools allowing drag and drop and edit of UI components. In JavaFX, business logic is also put elsewhere. JavaFX has different types of Panes, which are predefined layouts providing different UI needs. Android has several essential layouts like Relative and Linear layouts, list view and grid view. Although the strategy of composing is not exactly the same, the gist is very similar to those in JavaFX. However, above that, Android has Recycler View, which is an advanced version of list view but specifically designed for large data set, and card view for building rich Android apps.
            Also compared with ASP.NET, which uses html+css+js for UI and layout design (as well as many other web stacks). Html is designed for displaying web. I did some research on why not use html for Android, and found several reasons: 1) XML can be used for custom configuration on element, and add style. 2) XML has better formed style for a native UI.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** In the API documentation, Adapter is described as "a bridge between an AdapterView (the ListView in our app) and the underlying data (the ArrayList) for that view. The Adapter is also responsible for making a **View** for each item in the data set." Looking at the ArrayAdapter constructor, it takes 3 arguments, the context, an Android predefined layout, and the data. The Adapter wraps the data element as the indicated layout, and create a view. AdapterView can simply set its adapter to display the data.
            Adapter at least has two advantages: 1) It allows data being wrapped into different layouts, and the layouts can be defined by developer and reused. 2) Because it also "makes a view", with the `getView` method, the adapter can recycle the view to avoid make thousands of listItems when the data set is big.
            `convertView` is supplied by ListView. ListView keeps track of all the extra views that are moving off screen, and pass this view as `convertView` to request more view from Adapter (`getView`). When `convertView` is not null, `getView` will reuse `convertView`, update it with new data, and return it.

## Notes

Describe any challenges encountered while building the app.

## License

    Copyright 2017 Shusheng Lu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
