# Тechnical Task for the course project:


### The test task is: 
 * #### Build the app that will implement this  [API](https://rickandmortyapi.com/) and will have this functions:
    * Splash screen
    * Screens with lists of characters, locations and episodes.
    * Support backward navigation on all screens, except the main screen, also need to display the back arrow button.
    * Details screens with information about the character, location, or episodes selected in the bottom navigation or another screen
    * Display the progress bar when data is loading
    * Filtering by some parameters and searching by name on each list screen. If there is no data on the specified parameters need to display information about it on the screen
    * Supporting caching and work without internet
    * Pull-to-Refresh function on all screens
    * Support Dagger2 for DI
   
    ---


### My implementation

* ### UI 
    * #### Single Activity Architecture:

            Splash screen
            Activity as container for fragments
            Bottom Navigation View for navigate between fragments(Characters, Location, Episodes)
            Characters fragment -> Detail fragment
            Location fragment -> Details location fragment
            Episodes fragment -> Details episodes fragment
            
    
           UI RESUME:
           The app will have 1 Splash screen, 1 Activity, 6 Fragments
    
<img src=https://user-images.githubusercontent.com/117711604/203156372-8eab6d0d-6f0a-4df9-91d3-24da3b56eaff.svg width=600>

---

### What I have implemented:
---




### What i have not implemented:
---
