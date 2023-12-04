# Serenity BDD TodoMVC Demo

This project is a demonstration of how to use Serenity BDD with the Screenplay Pattern to test a web application.

These tests use tasks, actions, questions and page elements defined in `src/main/java/net/serenitybdd/demos/todos/screenplay`.

The overall project structure is shown below:
````
+ model
    Domain model classes
+ tasks
    Business-level tasks
+ action
    UI interactions
+ pages
    Page Objects and Page Elements
+ questions
    Objects used to query the application
````

## Running the project

To run the project you'll need JDK 17 and Maven installed.

### Screenplay and Cucumber

The first demo shows the integration of Serenity BDD, Screenplay and Cucumber JVM.
To run it, execute:

```
mvn clean verify
```

## Reporting

The Serenity reports will be generated in the `target/site/serenity` directory.
