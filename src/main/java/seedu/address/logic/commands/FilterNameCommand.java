package seedu.address.logic.commands;

import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FilterNameCommand extends FilterCommand {

    public static final String TYPE = "name";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD... [--all]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE + " alice bob charlie";

    public FilterNameCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }
}
