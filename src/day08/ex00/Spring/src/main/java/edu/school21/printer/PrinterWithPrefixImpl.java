package edu.school21.printer;

import edu.school21.processor.PreProcessor;
import edu.school21.renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private final Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
        this.prefix = null;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void print(String message) {
        if (prefix == null) {
            renderer.send(message);
        } else {
            renderer.send(prefix + " " + message);
        }
    }
}
