package edu.school21.renderer;

import edu.school21.processor.PreProcessor;

public class RendererStandardImpl implements Renderer {
    PreProcessor processor;

    public RendererStandardImpl(PreProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void send(String message) {
        System.out.println(processor.preProcesses(message));
    }
}
