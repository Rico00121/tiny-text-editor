package fr.istic.aco.editor;

import fr.istic.aco.editor.commands.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * The Invoker class manages the execution of commands, designed for the Command design pattern.
 * It holds the current text and selection indices, and maintains a map of command identifiers to their corresponding Command objects.
 */
public class Invoker {
    /**
     * The current text being manipulated.
     */
    private String text;
    /**
     * The beginning index of the current selection.
     */
    private int beginIndex;
    /**
     * The ending index of the current selection.
     */
    private int endIndex;
    /**
     * A map that associates command identifiers with their respective Command objects.
     */
    private final Map<String, Command> commands;

    /**
     * Constructs a new Invoker instance with an empty text and an empty command map.
     */
    public Invoker() {
        this.text = "";
        this.commands = new HashMap<>();
    }

    /**
     * Sets the current text.
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the current selection indices.
     *
     * @param beginIndex the beginning index of the selection
     * @param endIndex   the ending index of the selection
     */
    public void setSelection(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    /**
     * Gets the beginning index of the current selection.
     *
     * @return the beginning index
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Gets the ending index of the current selection.
     *
     * @return the ending index
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Gets the current text.
     *
     * @return the current text
     */
    public String getText() {
        return text;
    }

    /**
     * Adds a command to the command map.
     *
     * @param id      the identifier for the command
     * @param command the Command object to add
     */
    public void addCommand(String id, Command command) {
        commands.put(id, command);
    }

    /**
     * Executes a command by its identifier.
     *
     * @param id the identifier of the command to execute
     */
    public void playCommand(String id) {
        Command command = commands.get(id);
        command.execute();
    }

}
