package seedu.duke.command;

import seedu.duke.Storage;
import seedu.duke.Ui;

import seedu.duke.data.TransactionList;
import seedu.duke.exception.MoolahException;
import seedu.duke.exception.AddTransactionInvalidAmountException;
import seedu.duke.exception.AddTransactionInvalidCategoryException;
import seedu.duke.exception.AddTransactionUnknownTypeException;
import seedu.duke.exception.AddTransactionInvalidDateException;
import seedu.duke.exception.AddTransactionMissingTagException;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.duke.common.DateFormats.DATE_INPUT_PATTERN;
import static seedu.duke.common.InfoMessages.INFO_ADD_EXPENSE;
import static seedu.duke.common.InfoMessages.INFO_ADD_INCOME;

/**
 * Represents an add command object that will execute the operations for Add command.
 */
public class AddCommand extends Command {

    private String input;

    public AddCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the "add" command. Check and parse the necessary parameters before adding transaction.
     *
     * @param ui           An instance of the Ui class.
     * @param transactions An instance of the TransactionList class.
     * @param storage      An instance of the Storage class.
     */
    @Override
    public void execute(TransactionList transactions, Ui ui, Storage storage) throws MoolahException {
        /*
        Checks if userInput is in the correct input format by further parsing,
        before adding entry to arraylist
        */

        String[] splits = input.split(" ");
        checkTagsExist(splits);

        String description = "";
        int amount = 0;
        String category = "";
        LocalDate date = null;
        String type = "";

        for (String split : splits) {
            String tag = split.substring(0, 2);
            String parameter = split.substring(2);
            switch (tag) {
            case "t/":
                type = parameter;
                break;
            case "c/":
                category = parseCategoryTag(parameter);
                break;
            case "a/":
                amount = parseAmountTag(parameter);
                break;
            case "d/":
                date = parseDateTag(parameter);
                break;
            case "i/":
                description = parameter;
                break;
            default:
                break;
            }
        }
        assert date != null;
        addTransactionByType(transactions, type, description, amount, category, date);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    private static void addTransactionByType(TransactionList transactions, String type, String description,
            int amount, String category, LocalDate date) throws AddTransactionUnknownTypeException {

        switch (type) {
        case "expense":
            String expense = transactions.addExpense(description, amount, category, date);
            Ui.showTransactionAction(INFO_ADD_EXPENSE.toString(), expense);
            break;
        case "income":
            String income = transactions.addIncome(description, amount, category, date);
            Ui.showTransactionAction(INFO_ADD_INCOME.toString(), income);
            break;
        default:
            throw new AddTransactionUnknownTypeException();
        }
    }

    /**
     * Checks if the targeted tags exists in the split user inputs.
     *
     * @param splits The user input after the command word, split into a list for every space found.
     * @throws AddTransactionMissingTagException Missing tag exception.
     */
    private static void checkTagsExist(String[] splits) throws AddTransactionMissingTagException {
        // TODO: To add the tags into Command class instead
        String[] tags = new String[]{"t/", "c/", "a/", "d/", "i/"};
        for (String tag : tags) {
            boolean found = findMatchingTagFromInputs(tag, splits);
            if (!found) {
                throw new AddTransactionMissingTagException();
            }
        }
    }

    /**
     * Checks if the parameter contains numeric characters.
     *
     * @param parameter The user input after the user tag.
     * @return true if there are numeric characters within the parameter.
     */
    public static boolean containNumeric(String parameter) {
        char[] characters = parameter.toCharArray();
        for (char character : characters) {
            if (Character.isDigit(character)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the parameter contains alphabetical characters.
     *
     * @param parameter The user input after the user tag.
     * @return true if there are alphabetical characters within the parameter.
     */
    public static boolean containAlphabet(String parameter) {
        char[] characters = parameter.toCharArray();
        for (char character : characters) {
            if (Character.isAlphabetic(character)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Parse the user parameter input for the description and returns it.
     *
     * @param parameter The user input after the user tag.
     * @return The category parameter if no exceptions are thrown.
     * @throws AddTransactionInvalidCategoryException Invalid category format exception.
     */

    private static String parseCategoryTag(String parameter) throws AddTransactionInvalidCategoryException {
        Pattern specialSymbols = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecialSymbols = specialSymbols.matcher(parameter);
        if (containNumeric(parameter) || hasSpecialSymbols.find()) {
            throw new AddTransactionInvalidCategoryException();
        }
        return parameter;

    }

    /**
     * Parse the user parameter input for the amount and returns it.
     *
     * @param parameter The user input after the user tag.
     * @return The amount integer if no exceptions are thrown.
     * @throws AddTransactionInvalidAmountException Invalid amount format exception.
     */
    private static int parseAmountTag(String parameter) throws AddTransactionInvalidAmountException {
        Pattern specialSymbols = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecialSymbols = specialSymbols.matcher(parameter);
        if (containAlphabet(parameter) || hasSpecialSymbols.find()) {
            throw new AddTransactionInvalidAmountException();
        } else {
            return Integer.parseInt(parameter);
        }
    }

    /**
     * Parse the user parameter input for date into a LocalDate object and returns it.
     *
     * @param parameter The user input after the user tag.
     * @return The LocalDate object parsed from user input given.
     * @throws AddTransactionInvalidDateException Invalid date format exception.
     */
    private static LocalDate parseDateTag(String parameter) throws AddTransactionInvalidDateException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_INPUT_PATTERN.toString());
            LocalDate date = LocalDate.parse(parameter, formatter);
            return date;
        } catch (DateTimeParseException exception) {
            throw new AddTransactionInvalidDateException();
        }
    }

    /**
     * Returns a boolean value on whether a tag can be found among the split user inputs.
     *
     * @param tag    A specific tag used to locate the command parameter.
     * @param splits The user input after the command word, split into a list for every space found.
     * @return Whether the tag is found within the split inputs.
     */
    private static boolean findMatchingTagFromInputs(String tag, String[] splits) {
        boolean found = false;
        for (String split : splits) {
            if (split.startsWith(tag)) {
                found = true;
                break;
            }
        }
        return found;
    }

}
