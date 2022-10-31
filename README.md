# Login Project


### Before you run
- Please make sure you have the local Android Sdk specified in the local.properties file, under Gradle Script.
- Preferably choose a Pixel 5 as the android emulator. The UI may not respond correctly on other devices.
- Then run the app and put the below credentials to Login. Should take a couple seconds to redirect you.


### Credentials to Login
```
userId: TH1234
password: 3NItas1!
```


### Description
- The app consists of a Login Page letting our user sign in after giving the correct credentials and making a call to our API.
- Then we are redirected to the Home Page consisting of two tabs, the Book and Settings.
- We can "download" the loaded pdfs, sorted by date. 
- We can move between tabs to the settings where we are able to toggle notifications, delete the "downloaded" pdfs and logout.
- In a next login the "downloaded" pdfs should be still visible, unless they are deleted.


### Implementation Assumptions
- Language Toggle functionality not included, as instructed
- Icons Misc and Profile not added (no UI pages given), also settings choices: language and feedback
- Error handling in Userid and Password fields is generic (basic error when credentials aren't correct)
- Download functionality is simulated meaning we change states to show the different images on the UI on a timer
- Couldn't load images from the urls provided by the api so I added new hardcoded ones, then made random per pdf on first load
- No open pdf functionality implemented
- Play Button only changes it's image


### Some Issues Faced
- Positioning AlertDialog under info button correctly (has small bug)
- Waiting the login request is made by hardcoded delay, I avoided the same issue on books by using LaunchedEffect.
- Issues with LazyVerticalGrid,
    - no sticky headers(experimental on LazyList)
    - hardcoded years and filtered booksList
- Removing Ripple effect from NavBar