@cucumber
@filtering
Feature: Filtering todos

  In order to make me feel a sense of accomplishment
  As a forgetful person
  I want to be to view all of things I have completed

  @current
  Scenario Outline: Viewing the items by status
    Given that Jane has a todo list containing <tasks>
    And she completes the task called "Walk the dog"
    When she filters her list to show only <filter> tasks
    Then the current filter should be <filter>
    And her todo list should contain <expected>
    Examples:
      | tasks                       | filter    | expected                     |
      | Buy some milk, Walk the dog | Active    | Buy some milk                |
      | Buy some milk, Walk the dog | Completed | Walk the dog                 |
      | Buy some milk, Walk the dog | All       | Buy some milk,  Walk the dog |

  Rule: The current filter should be visible
    Background:
      Given that Jane has a todo list containing Walk the dog

    Scenario: The default filter should be Active
      Then the current filter should be All

    Scenario Outline: Displaying other filter status values
      When she filters her list to show only <filter> tasks
      Then the current filter should be <filter>
      Examples:
        | filter    |
        | Active    |
        | Completed |
        | All       |

