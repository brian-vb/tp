package seedu.duke.command;

//@author wcwy
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.data.TransactionList;

import static seedu.duke.command.CommandTag.COMMAND_TAG_HELP_OPTION;
import static seedu.duke.common.HelpMessages.COMMAND_PARAMETERS_COMMAND;
import static seedu.duke.common.HelpMessages.COMMAND_PARAMETERS_DETAILED;
import static seedu.duke.common.HelpMessages.COMMAND_DESCRIPTION_HELP;
import static seedu.duke.common.HelpMessages.COMMAND_USAGE_HELP;
import static seedu.duke.common.InfoMessages.LINE_SEPARATOR;


/**
 * Represents a help command object that will execute the operations for Help command.
 */
public class HelpCommand extends Command {
    //@@author wcwy
    // The command word used to trigger the execution of Moolah Manager's operations
    public static final String COMMAND_WORD = "HELP";
    // The formatting information for the parameters used by the command
    public static final String COMMAND_PARAMETERS_INFO = "Parameters information:" + LINE_SEPARATOR
            + COMMAND_PARAMETERS_DETAILED + LINE_SEPARATOR + COMMAND_PARAMETERS_COMMAND;
    // Basic help description
    public static final String COMMAND_HELP = "Command Word: " + COMMAND_WORD + LINE_SEPARATOR
            + COMMAND_DESCRIPTION_HELP + LINE_SEPARATOR + COMMAND_USAGE_HELP + LINE_SEPARATOR;
    // Detailed help description
    public static final String COMMAND_DETAILED_HELP = COMMAND_HELP + COMMAND_PARAMETERS_INFO + LINE_SEPARATOR;

    //@@author wcwy
    private boolean isDetailed;

    /**
     * Instantiates the ListCommand class with required variables.
     */
    public HelpCommand() {
        this.isDetailed = false;
    }

    /**
     * Sets a boolean value to indicate if the user chooses to display the help message in detail.
     *
     * <p>When the boolean isDetailed is set to true, the help command will display the detailed help messages
     * to the user.
     *
     * @param isDetailed A boolean indicating if the user chooses to display the help message in detail.
     */
    @Override
    public void setIsDetailedOption(boolean isDetailed) {
        this.isDetailed = isDetailed;
    }

    /**
     * Gets the optional tags of the command.
     *
     * @return A string array containing all optional tags.
     */
    @Override
    public String[] getOptionalTags() {
        String[] optionalTags = new String[]{
            COMMAND_TAG_HELP_OPTION
        };
        return optionalTags;
    }

    /**
     * Executes the operations related to the command.
     *
     * @param ui           An instance of the Ui class.
     * @param transactions An instance of the TransactionList class.
     * @param storage      An instance of the Storage class.
     */
    @Override
    public void execute(TransactionList transactions, Ui ui, Storage storage) {
        String helpMessage = "";
        if (isDetailed) {
            helpMessage = generateDetailedHelp();
        } else {
            helpMessage = generateBasicHelp();
        }

        Ui.showHelp(helpMessage);
    }

    /**
     * Consolidates all the basic help messages from the available commands and returns it.
     *
     * @return A string containing all basic help messages of valid commands.
     */
    private String generateBasicHelp() {
        String helpMessage = HelpCommand.COMMAND_HELP + LINE_SEPARATOR
                + BudgetCommand.COMMAND_HELP + LINE_SEPARATOR
                + AddCommand.COMMAND_HELP + LINE_SEPARATOR
                + ListCommand.COMMAND_HELP + LINE_SEPARATOR
                + FindCommand.COMMAND_HELP + LINE_SEPARATOR
                + StatsCommand.COMMAND_HELP + LINE_SEPARATOR
                + EditCommand.COMMAND_HELP + LINE_SEPARATOR
                + DeleteCommand.COMMAND_HELP + LINE_SEPARATOR
                + PurgeCommand.COMMAND_HELP + LINE_SEPARATOR
                + ByeCommand.COMMAND_HELP;

        return helpMessage;
    }

    /**
     * Consolidates all the detailed help messages from the available commands and returns it.
     *
     * @return A string containing all detailed help messages of valid commands.
     */
    private String generateDetailedHelp() {
        String helpMessage = HelpCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + BudgetCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + AddCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + ListCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + FindCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + StatsCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + EditCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + DeleteCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + PurgeCommand.COMMAND_DETAILED_HELP + LINE_SEPARATOR
                + ByeCommand.COMMAND_DETAILED_HELP;

        return helpMessage;
    }

    //@@author paullowse

    /**
     * Enables the program to exit when the Bye command is issued.
     *
     * @return A boolean value that indicates whether the program shall exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
