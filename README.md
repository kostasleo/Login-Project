# Login-Project

 The project should run with the Login Page showing first

Credentials to Login
```
userId: TH1234
password: 3NItas1!
```

Home (Books) page not loading properly (more bellow). To fix go to NavigationScreen.kt and add a ! in the if of line 24 (or copy bellow)
```
val start = if(!loggedIn) Pages.Books.route else Pages.Login.route
```
Then rerun to see Home(Books) page, they should load now

***

### Small Issues Faced

- positioning AlertDialog under info button
- error handling in userid and password fields is generic (basic error when credentials aren't correct)
- some issues with LazyVerticalGrid,
	- no sticky headers(experimental on lazyList)
	- hardcoded years and filtered booksList
- couldn't load image urls from the api request so added new hardcoded ones, then made random per pdf
- no download functionality

***

### Bigger Issues
- books page api call works when it's the first page but not when navigated to from login!!! (so navigation issue)
- problem with login api call, so hardcoded credentials
- not added tab pannel ui in home page
- no extra pages (settings, etc) implemented
