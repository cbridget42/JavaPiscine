package edu.school21.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({"edu.school21.processor.HtmlForm",
"edu.school21.processor.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = elem.getAnnotation(HtmlForm.class);
            StringBuilder output = new StringBuilder(String.format("<form action = \"%s\" method = \"%s\">%n",
                    htmlForm.action(), htmlForm.method()));
            List<HtmlInput> htmlInputs = elem.getEnclosedElements().stream()
                    .map(e -> e.getAnnotation(HtmlInput.class))
                    .filter(Objects::nonNull).collect(Collectors.toList());
            for (HtmlInput i : htmlInputs) {
                output.insert(output.length(), String
                        .format("\t<input type = \"%s\" name = \"%s\" placeholder = \"%s\">%n",
                        i.type(), i.name(), i.placeholder()));
            }
            output.insert(output.length(), String
                    .format("\t<input type = \"submit\" value = \"Send\">%n</form>"));
            String fileName = String.format("target/classes/%s", htmlForm.fileName());
            try (BufferedWriter writer = new BufferedWriter(new PrintWriter(fileName))) {
                writer.write(output.toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }
}
