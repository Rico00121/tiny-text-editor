package fr.istic.aco.editor;

import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.EngineImpl;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    public static final String INSERT = "insert";
    public static final String MOVE_SELECTION = "moveSelection";

    public static final String COPY="copy";

    public static final String CUT="cut";

    public static final String DELETE="delete";
    public static final String PASTE = "paste";

    @Bean
    public Engine engine() {
        return new EngineImpl();
    }

    @Bean
    public Invoker invoker() {
        Invoker invoker = new Invoker();
        invoker.addCommand(INSERT, new Insert(engine(), invoker));
        invoker.addCommand(MOVE_SELECTION, new MoveSelection(engine(), invoker));
        invoker.addCommand(COPY, new Copy(engine()));
        invoker.addCommand(CUT, new Cut(engine()));
        invoker.addCommand(DELETE, new Delete(engine()));
        invoker.addCommand(PASTE, new Paste(engine()));
        return invoker;
    }

}
