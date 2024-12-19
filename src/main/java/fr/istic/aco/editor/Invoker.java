package fr.istic.aco.editor;

import fr.istic.aco.editor.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private String text;

    private int beginIndex;

    private int endIndex;
    private final Map<String, Command> commands;


    public Invoker() {
        this.text = "";
        this.commands = new HashMap<>();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSelection(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String getText() {
        return text;
    }

    public void addCommand(String id, Command command) {
        commands.put(id, command);
    }

    public void playCommand(String id) {
        Command command = commands.get(id);

        command.execute();
    }

}
