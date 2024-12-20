package fr.istic.aco.editor.controller;

public record EventResponse(String name, String currentBufferContent, Selected selected, String clipboard) {
}
