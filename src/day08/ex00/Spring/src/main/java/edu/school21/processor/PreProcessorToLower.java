package edu.school21.processor;

public class PreProcessorToLower implements PreProcessor {
    @Override
    public String preProcesses(String message) {
        return message.toLowerCase();
    }
}
