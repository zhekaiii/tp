package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterNameCommand}.
 */
public class FilterNameCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterNameCommand findFirstCommand = new FilterNameCommand(firstPredicate);
        FilterNameCommand findSecondCommand = new FilterNameCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterNameCommand findFirstCommandCopy = new FilterNameCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterNameCommand command = new FilterNameCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command,
                model,
                new CommandResult(expectedMessage)
                        .withPersonToShow(Model.INVALID_PERSON_INDEX),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FilterNameCommand command = new FilterNameCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command,
                model,
                new CommandResult(expectedMessage)
                        .withPersonToShow(Model.INVALID_PERSON_INDEX),
                expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_matchAllMultipleKeywords_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Carl", true);
        FilterNameCommand command = new FilterNameCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command,
                model,
                new CommandResult(expectedMessage)
                        .withPersonToShow(Model.INVALID_PERSON_INDEX),
                expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_matchAllMultipleKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz", true);
        FilterNameCommand command = new FilterNameCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command,
                model,
                new CommandResult(expectedMessage)
                        .withPersonToShow(Model.INVALID_PERSON_INDEX),
                expectedModel);
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FilterNameCommand filterNameCommand = new FilterNameCommand(predicate);
        String expected = FilterNameCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterNameCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private NameContainsKeywordsPredicate preparePredicate(String userInput, boolean matchAll) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), matchAll);
    }
}
