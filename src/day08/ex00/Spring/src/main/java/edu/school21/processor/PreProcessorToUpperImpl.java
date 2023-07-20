package edu.school21.processor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String preProcesses(String message) {
        return message.toUpperCase();
    }
}
