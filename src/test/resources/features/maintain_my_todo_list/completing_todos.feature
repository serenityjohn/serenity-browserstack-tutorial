@cucumber
@completing
Feature: Completing todos

  In order to make me feel **a sense of accomplishment**
  As a forgetful person
  I want to be to _view all of things I have completed_

  Scenario: Mark a task as completed
    Given that Jane has a todo list containing Buy some milk, Walk the dog
    When she completes the task called "Walk the dog"
    And she filters her list to show only Completed tasks
    Then her todo list should contain Walk the dog

  Scenario: Completed tasks should be indicated as such
    Given that Jane has a todo list containing Buy some milk, Walk the dog
    When she completes the task called "Walk the dog"
    And she filters her list to show only Completed tasks
    Then the "Walk the dog" task should be marked as completed

  Scenario: List of completed items should be empty if nothing has been completed
    Given that Jane has a todo list containing Buy some milk, Walk the dog
    When she filters her list to show only Completed tasks
    Then her todo list should be empty

  Scenario: Should be able to clear completed items
    Given that Jane has a todo list containing Buy some milk, Walk the dog
    When she completes the task called "Walk the dog"
    And she clears the completed items
    Then her todo list should contain Buy some milk
