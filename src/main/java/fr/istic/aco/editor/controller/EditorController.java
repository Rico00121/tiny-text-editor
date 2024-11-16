package fr.istic.aco.editor.controller;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.Engine;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static fr.istic.aco.editor.Configuration.INSERT;
import static fr.istic.aco.editor.Configuration.MOVE_SELECTION;

@RestController
public class EditorController {

    private final Engine engine;
    private final Invoker invoker;

    public EditorController(Engine engine, Invoker invoker) {
        this.engine = engine;
        this.invoker = invoker;
    }

    @PostMapping("/event")
    public EventResponse handleEvent(@RequestBody EventRequest event) {
        if (Objects.equals(event.name(), INSERT)) {
            invoker.setText(event.text());
        }
        if (Objects.equals(event.name(), MOVE_SELECTION)) {
            invoker.setSelection(event.selection().beginIndex(), event.selection().endIndex());
        }
        invoker.playCommand(event.name());
        return new EventResponse(event.name(),
                engine.getBufferContents(),
                new Selection(engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
    }

}