package fr.istic.aco.editor.controller;

import fr.istic.aco.editor.Invoker;
import fr.istic.aco.editor.kernel.Engine;
import fr.istic.aco.editor.kernel.Recorder;
import fr.istic.aco.editor.kernel.SelectionImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class EditorControllerTest {
    @MockBean
    private Engine engine;

    @MockBean
    private Invoker invoker;

    @MockBean
    private Recorder recorder;

    @Autowired
    private EditorController editorController;

    @Test
    public void should_handle_event_move_election_correctly() {
        // Arrange
        String text = "Hello";
        EventRequest request = new EventRequest("moveSelection", text, new Selected(2,2));
        when(engine.getBufferContents()).thenReturn(text);
        SelectionImpl selection = new SelectionImpl(new StringBuffer(text));
        selection.setEndIndex(2);
        selection.setBeginIndex(2);
        when(engine.getSelection()).thenReturn(selection);

        // Act
        ResponseEntity<EventResponse> responseEntity = editorController.handleEvent(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("moveSelection", responseEntity.getBody().name());
        assertEquals(text, responseEntity.getBody().currentBufferContent());
        assertEquals(2, responseEntity.getBody().selected().beginIndex());
        assertEquals(2, responseEntity.getBody().selected().endIndex());
        verify(invoker).setSelection(2,2);
        verify(invoker).playCommand("moveSelection");
    }

    @Test
    public void should_handle_event_insert_text_correctly() {
        // Arrange
        String text = "Hello";
        EventRequest request = new EventRequest("insert", text, new Selected(0,0));
        when(engine.getBufferContents()).thenReturn(text);
        when(engine.getSelection()).thenReturn(new SelectionImpl(new StringBuffer()));

        // Act
        ResponseEntity<EventResponse> responseEntity = editorController.handleEvent(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("insert", responseEntity.getBody().name());
        assertEquals(text, responseEntity.getBody().currentBufferContent());
        assertEquals(0, responseEntity.getBody().selected().beginIndex());
        assertEquals(0, responseEntity.getBody().selected().endIndex());
        verify(invoker).setText(text);
        verify(invoker).playCommand("insert");
    }

    @Test
    public void should_return_503_when_service_is_not_available() {
        // Arrange
        String text = "Hello";
        EventRequest request = new EventRequest("insert", text, new Selected(0,0));
        when(recorder.isReplaying()).thenReturn(true);

        // Act
        ResponseEntity<EventResponse> responseEntity = editorController.handleEvent(request);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
    }
}