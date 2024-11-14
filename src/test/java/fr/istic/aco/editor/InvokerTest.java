package fr.istic.aco.editor;

import fr.istic.aco.editor.commands.Command;
import fr.istic.aco.editor.commands.Copy;
import fr.istic.aco.editor.commands.Insert;
import fr.istic.aco.editor.commands.MoveSelection;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class InvokerTest {
    private Engine engine;
    private Invoker invoker;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.engine = new EngineImpl();
        this.invoker = new Invoker();
    }

    @Test
    void insert() {
        Command insert = new Insert(engine, invoker);
        invoker.addCommand("insert", insert);
        invoker.setText("hello");
        invoker.playCommand("insert");

        Assertions.assertEquals("hello", engine.getBufferContents());
    }

    @Test
    void moveSelection() {
        prepareHelloData();

        Command moveSelection = new MoveSelection(engine, invoker);
        invoker.addCommand("moveSelection", moveSelection);
        invoker.setSelection(1, 2);
        invoker.playCommand("moveSelection");

        Assertions.assertEquals(1, engine.getSelection().getBeginIndex());
        Assertions.assertEquals(2, engine.getSelection().getEndIndex());
    }



    private void prepareHelloData(){
        Command insert = new Insert(engine, invoker);
        invoker.addCommand("insert", insert);
        invoker.setText("hello");
        invoker.playCommand("insert");
    }
}
