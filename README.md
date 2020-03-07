# Popular-Github-Repo
 
## Introduction
Android application to find popular repositories of desired organization on github and for each repository the top committess.

## Approach
I have used the API https://api.github.com/orgs/:org/repos to list all repositories of an organization. You can find its documentation [here](https://developer.github.com/v3/repos/#list-organization-repositories).

Based on the number of forks on each repo, I have sorted and displayed then on the activity using RecyclerView. User can tap on each RecyclerView item to find the top Committes on that repository.

## Screenshots

<img src="Screenshots/Screenshot1.png" width="250" />
<img src="Screenshots/Screenshot2.png" width="250" />
<img src="Screenshots/Screenshot3.png" width="250" />
