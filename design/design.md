# Beef the Budget Buddy
Beef the Budget Buddy is here to help you work towards your savings goals! Providing a visual, clear
progress towards your goals, Brynn can help eliminate impulse spending and regretful purchases. Being able to
see and plan your savings will provide motivation and excitement towards getting closer to your goals.

---

## User Stories

1. As a user, I want to *create a goal*.
2. As a user, I want to *view all goals*.
3. As a user, I want to *search for a goal by name (partial or full search)*.
4. As a user, I want to *search for a goal by category*.
5. As a user, I want to *search for a goal by priority*.
6. As a user, I want to *view a goal by name*.
7. As a user, I want to *view a goal by category*.
8. As a user, I want to *update a goal's amount*.
9. As a user, I want to *add a description to the goal*.
10. As a user, I want to *update the description*.
11. As a user, I want to *delete a goal*.
12. As a user, I want to *mark a goal as complete*.
13. As a user, I want to *tag goals by priority -> HIGH, LOW, NORMAL*.
14. As a user, I want to *categorize the goal*.

## Stretch Goals
1. As a user, I want to *share a goal with someone*.
2. As a user, I want to *add a list of items that I am saving for within the goal*.
3. As a user, I want to *make comments within the goal*.

---

## [UML Diagram](./beefUML.puml)

---

## DynamoDB Tables & GSI
1. Goals
      1. goalId (partition), String
      2. name, String
      3. category, String
      4. description, String
      5. goalAmount, Double
      6. priority, String
      7. userId, String

2. User
   1. userId (Partition), String
   2. username, String
   3. goals, List<String> (list of goal IDs)
   4. goalCount, Integer
   5. goalsCompleted, Integer   

---
## API Endpoints

1. SearchGoalByName --> full matching on name
   1. allows the user to search for a goal by the name
   2. GET
   3. /goals/search
   4. user authenticated
   5. response: (returns a list of goals that match the request)
      6. name, category, goalId, description, goalAmount, priority, userId


2. SearchGoalByCategory
   1. allows the user to search for a goal by the category
   2. GET
   3. /goals/search
   4. user authenticated


3. GetGoal
   1. allows the user to retrieve the goal by id
   2. GET
   3. /goals/{goalId}
   4. user authenticated


4. GetGoalByCategory
   1. allows the user to retrieve goal by the category
   2. GET
   3. /goals/{category}
   4. user authenticated


5. GetGoalByName
   1. allows user to retrieve goal by name. 
   2. GET
   3. /goals/{name}
   4. user authenticated


6. UpdateGoalAmount
   1. allows user to update the goal amount
   2. PUT 
   3. goals/{goalId}
   4. user authenticated

request:
   ``` 
   curl -X PUT \
      -d '{
      "goalId": "1234",
      "goalName": "clothes",
      "amount": 500,
      "priority": "high"
      }' \
      linkgoeshere.com/goals/{goalId}
   ```
   response: 
   ```
   {
   "data": {
   "goalId": "1234",
   "goalName": "clothes",
   "amount": 500,
   "priority": "high"
      }
   }
   ```


7. CreateGoal
   8. allows user to create a goal
   9. POST 
   10. /goals
   
request: 
```
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "goalName": "roadtrip to yosemite",
    "amount": 3000,
    "priority": "low",
    "description": "i don't like to camp. this is a lie. i don't want to go to yosemite",
    "goalId": 567
  }' \
  linkgoeshere.com/goals/
```

response:

```
{
  "data": {
    "goalName": "roadtrip to yosemite",
    "amount": 3000,
    "priority": "low",
    "description": "i don't like to camp. this is a lie. i don't want to go to yosemite",
    "goalId": 567
  }
}

```

8. UpdateGoalDescription
   1. allows user to update the goal description
   2. PUT
   3. goals/{goalId}
   4. user authenticated

request:
```
curl -X PUT \
  -H "Content-Type: application/json" \
  -d '{
    "goalName": "new pc setup",
    "amount": 2000,
    "priority": "high",
    "goalId": 346,
    "description": "I want new things so I can feel productive"
  }' \
  linkgoeshere.com/goals/346
```
response:
```
{
  "data": {
    "goalName": "new pc setup",
    "amount": 2000,
    "priority": "high",
    "goalId": 346,
    "description": "I want new things so I can feel productive"
  }
}
```

9. DeleteGoal
   10. allows user to delete a goal
   11. DELETE
   12. goals/{id}

request:
```
curl -X DELETE \
  -H "Content-Type: application/json" \
  linkgoeshere.com/goals/8764

```

---

## API Sequence Diagram
### [UpdateGoalAmount Diagram](./UpdateGoalAmount.puml)

---

## Frontend Mockups
### [homepage (pre-sign in)](https://drive.google.com/file/d/1luc3l847YyylJEnVvQT25GrIRWsNIBlP/view)
### [homepage (post-sign in)](https://drive.google.com/file/d/1bXn9LaPONFeWswCNDeL_f_Aol_HkbN5B/view)
### [search goals: id](https://drive.google.com/file/d/1kS6i15X9DpGzr84PeV4Ulun8q3FkNTn1/view)
### [search goals: name](https://drive.google.com/file/d/1_9ex_S747rrqW9XJBd_siC3fRWoOKQom/view)
### [search goals: priority](https://drive.google.com/file/d/17s_zNsqEiChXV0Bq9DDSWjsg8AcxyXPt/view)
### [create goal](https://drive.google.com/file/d/16N61tSAEB44XCj7DYY0ZcZ86vnIINMet/view)
### [update goal: amount](https://drive.google.com/file/d/1ShU4d-4aACRpYcp8r7PYFvZOmlQm6Yh8/view)
### [update goal: description](https://drive.google.com/file/d/1e0j9x1BqRva_wEeLJQb-fttMCV70I7hi/view)
### [view goal: individual](https://drive.google.com/file/d/1JUEwRS1FiVTDCak4oSTLl1yLeKju9XOI/view)
### [view goals: all](https://drive.google.com/file/d/1rNn5x6cV8UiUiGn82skcs5fQ8IH5kTeY/view)