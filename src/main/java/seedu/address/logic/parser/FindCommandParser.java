package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ContactMatchesKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        List<String> nameKeywords = argMultimap.getValue(PREFIX_NAME)
                .map(this::splitKeywords)
                .orElse(List.of());
        List<String> phoneKeywords = argMultimap.getValue(PREFIX_PHONE)
                .map(this::splitKeywords)
                .orElse(List.of());
        List<String> emailKeywords = argMultimap.getValue(PREFIX_EMAIL)
                .map(this::splitKeywords)
                .orElse(List.of());
        List<String> addressKeywords = argMultimap.getValue(PREFIX_ADDRESS)
                .map(this::splitKeywords)
                .orElse(List.of());
        List<String> tagKeywords = splitKeywords(String.join(" ", argMultimap.getAllValues(PREFIX_TAG)));

        if (nameKeywords.isEmpty() && phoneKeywords.isEmpty() && emailKeywords.isEmpty()
                && addressKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new ContactMatchesKeywordsPredicate(
                nameKeywords, phoneKeywords, emailKeywords, addressKeywords, tagKeywords));
    }

    /**
     * Splits a raw keyword string by whitespace and drops empty tokens.
     */
    private List<String> splitKeywords(String rawKeywords) {
        return Arrays.stream(rawKeywords.trim().split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());
    }
}
