package fr.istic.aco.editor.controller;

/**
 * The type Event response.
 */
public record EventResponse(String name, String currentBufferContent, Selected selected, String clipboard) {
}
