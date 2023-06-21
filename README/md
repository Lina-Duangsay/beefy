# Beefy
Full-stack capstone project, utilizing AWS services (Cloudfront, Cloudformation, S3, DynamoDb, Lambda), JavaScript, Gradle, HTML, & CSS. 

## Description
Beefy the Budget Buddy is designed to help you picture your wishlist while
keeping your spending habits on track. Create a goal to save for and add updates to it as you continue saving money.
With Beefy the Budget Buddy, no item is out of reach. 

## Features
Each endpoint has its own request, result, and activity classes. 

1. Create Goal
- /goals/
- POST 
- User can create goal to track, required name, amount, category, priority, and description.
2. Update Goal Description
- /goals/updateDescription/{goalId}
- PUT
- User can update pre-existing goal's description.
3. Update Goal Amount
- /goals/updateAmount/{goalId}
- PUT
- User can update pre-existing goal's amount.
4. Update Goal Priority
- /goals/updatePriority/{goalId}
- PUT
- User can update pre-existing goal's priority.
5. Delete Goal
- /goals/deleteGoal/{goalId}
- Users can delete pre-existing goals. This will erase the data from DynamoDb.
- DELETE
6. Get Goal
- /goals/{goalId}
- Users can retrieve a goal by its goal ID. 
- GET
7. Get Goal By Category
- /goals/category/{category}
- Users can retrieve a goal by its category. Must be an exact match, case-sensitive. 
- Uses the GetGoalsByCategoryIndex.
- GET
8.  Get Goal By Name
- /goals/name/{name}
- Users can retrieve a goal by its name. Must be an exact match, case-sensitive.
- Uses the GetGoalsByNameIndex.
9.  Get All Goals
- /goals/AllGoalsUserIdIndex
- allows user to view all goals **they** have created. 

Each request is authenticated, users must sign in to access information.

## Models & GSIs

1. Goals Table
- Goal ID, String (Hashkey)
- Name, String
- Category, String
- Amount, Double
- Priority, String
- Description, String
- Completion Status, Boolean

2. GoalDao
- getGoal
- getGoalByCategory -> GSI
- getGoalByName -> GSI
- getGoalByUserId -> GSI

## Future Development

- I would like each goal to appear on its own card, similar to how Pinterest displays its data (a grid-format, with each entry on its own)
- I would like to have the user dashboard display the user's information (username, number of goals, amount needed to save, goals completed, etc)
- I would like to implement a feature that allows the user to make comments to each goal, a way to indicate how much money you've saved towards it,
  and a progress bar that updates as you go. 
