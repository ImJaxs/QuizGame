# QuizGame


### INSERT THE PLUGIN INTO THE SERVER
* Put the JAR file in the plugins folder of the server.
* Restart the server

### CONFIGURE THE PLUGIN
* Go inside the plugins/QuizGame folder and open the config.yml file
* Enter your database login details and restart the server.

### EDIT PLUGIN MESSAGES
* Go inside the plugins/QuizGame folder and open the language.yml file
* Edit as many messages as you need.

### CATEGORY EXAMPLE
```
  categories:
    "1": -> Number to index, careful not to use the same for 2 items
      name: "Animals" -> Category Name
      question-list:
        "1": -> Number to index, careful not to use the same for 2 items
          question: "What animals have 4 legs?" -> Question
          answers:
          - "Dog" -> Answer 1
          - "Cat" -> Answer 2
          - "Horse" -> Answer 3
```          
