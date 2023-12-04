package net.serenitybdd.demos.todos.cucumber.steps;


import com.google.common.base.Splitter;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.annotations.events.AfterExample;
import net.serenitybdd.demos.todos.cucumber.MissingTodoItemsException;
import net.serenitybdd.demos.todos.screenplay.model.TodoStatus;
import net.serenitybdd.demos.todos.screenplay.model.TodoStatusFilter;
import net.serenitybdd.demos.todos.screenplay.questions.ClearCompletedItems;
import net.serenitybdd.demos.todos.screenplay.questions.CurrentFilter;
import net.serenitybdd.demos.todos.screenplay.questions.TheItemStatus;
import net.serenitybdd.demos.todos.screenplay.questions.TheItems;
import net.serenitybdd.demos.todos.screenplay.tasks.*;
import net.serenitybdd.model.buildinfo.BuildInfo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class TodoUserSteps {

    @Before
    public void set_the_stage() {
        setTheStage(new OnlineCast());
    }

    @ParameterType(".*")
    public Actor actor(String actor) {
        return OnStage.theActorCalled(actor);
    }

    @ParameterType("All|Active|Completed")
    public TodoStatusFilter filter(String filter) {
        return TodoStatusFilter.valueOf(filter);
    }

    @ParameterType(".*")
    public List<String> items(String listOfItems) {
        return Splitter.on(",").trimResults().omitEmptyStrings().splitToList(listOfItems);
    }

    @Given("that {actor} has an empty todo list")
    public void that_James_has_an_empty_todo_list(Actor actor) {
        actor.wasAbleTo(Start.withAnEmptyTodoList());
    }

    @Given("that {actor} has a todo list containing {items}")
    public void that_James_has_an_empty_todo_list(Actor actor, List<String> items) {
        actor.wasAbleTo(Start.withATodoListContaining(items));
    }

    @When("{actor} completes the task called {string}")
    public void completesTask(Actor actor, String item) {
        actor.attemptsTo(CompleteItem.called(item));
    }

    @When("{actor} clears the completed items")
    public void clearsCompletedItems(Actor actor) {
        actor.attemptsTo(Clear.completedItems());
    }

    @When("{actor} adds {string} to his/her list")
    public void adds_to_his_list(Actor actor, String item) {
        actor.attemptsTo(AddATodoItem.called(item));
    }

    @When("{actor} deletes the task called {string}")
    public void deletes_an_item(Actor actor, String item) {
        actor.attemptsTo(DeleteAnItem.called(item));
    }

    @When("{actor} completes and deletes all the items")
    public void deleteAll(Actor actor) {
        actor.attemptsTo(DeleteAllTheItems.onThePage());
    }

    @Then("{string} should be recorded in his/her list")
    public void item_should_be_recorded_in_the_list(String expectedItem) {
        theActorInTheSpotlight().should(seeThat(TheItems.displayed(), hasItem(expectedItem))
                .orComplainWith(MissingTodoItemsException.class, "Missing todo " + expectedItem));
    }

    @Then("his/her todo list should contain {items}")
    public void todo_list_should_contain(List<String> expectedItems) {
        theActorInTheSpotlight().should(seeThat(TheItems.displayed(), equalTo(expectedItems))
                .orComplainWith(MissingTodoItemsException.class, "Missing todos " + expectedItems));
    }

    @Then("{actor}'s todo list should contain {items}")
    public void a_users_todo_list_should_contain(Actor actor, List<String> expectedItems) {
        actor.should(seeThat(TheItems.displayed(), equalTo(expectedItems))
                .orComplainWith(MissingTodoItemsException.class, "Missing todos " + expectedItems));
    }


    @Then("his/her todo list should be empty")
    public void todo_list_should_be_empty() {
        theActorInTheSpotlight().should(seeThat(TheItems.displayed(), equalTo(EMPTY_LIST)));
    }


    @When("{actor} filters her list to show only {filter} tasks")
    public void filters_tasks_by(Actor actor, TodoStatusFilter status) {
        actor.attemptsTo(
                FilterItems.toShow(status)
        );
    }

    @Then("the current filter should be {}")
    public void currentFilterShouldBe(TodoStatusFilter status) {
        theActorInTheSpotlight().should(seeThat(CurrentFilter.selected(), equalTo(status)));
    }

    @Then("the {string} task should be marked as completed")
    public void the_task_should_be_marked_as_completed(String item) {
        theActorInTheSpotlight().should(seeThat(TheItemStatus.forTheItemCalled(item), equalTo(TodoStatus.Completed)));
    }
}
