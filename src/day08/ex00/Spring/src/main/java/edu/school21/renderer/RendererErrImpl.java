package edu.school21.renderer;

import edu.school21.processor.PreProcessor;

public class RendererErrImpl implements Renderer {
    PreProcessor processor;

    public RendererErrImpl(PreProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void send(String message) {
        System.err.println(processor.preProcesses(message));
    }
}
