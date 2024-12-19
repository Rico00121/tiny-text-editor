package fr.istic.aco.editor.controller;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Recorder;
import fr.istic.aco.editor.kernel.UndoManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static fr.istic.aco.editor.Configuration.INSERT;
import static fr.istic.aco.editor.Configuration.MOVE_SELECTION;

@RestController
public class EditorController {

    private final Engine engine;
    private final Invoker invoker;
    private final Recorder recorder;
    private final UndoManager undoManager;

    public EditorController(Engine engine, Invoker invoker, Recorder recorder, UndoManager undoManager) {
        this.engine = engine;
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    @PostMapping("/event")
    public ResponseEntity<EventResponse> handleEvent(@RequestBody EventRequest event) {
        if (recorder.isReplaying()) {
            return ResponseEntity.
                    status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        if (Objects.equals(event.name(), INSERT)) {
            invoker.setText(event.text());
        }
        if (Objects.equals(event.name(), MOVE_SELECTION)) {
            invoker.setSelection(event.selected().beginIndex(), event.selected().endIndex());
        }

        invoker.playCommand(event.name());


        EventResponse response = new EventResponse(
                event.name(),
                engine.getBufferContents(),
                new Selected(engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex())
        );
        return ResponseEntity.ok(response);
    }

}