package it.unisa.aDoctor.smellDetectionRules;

import org.eclipse.jdt.core.dom.CompilationUnit;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.parser.CodeParser;
import it.unisa.aDoctor.parser.ForStatementVisitor;

public class SlowLoopRule implements ClassRule {

    public boolean isSlowLoop(ClassBean pClassBean) {
        CodeParser parser = new CodeParser();
        CompilationUnit compilationUnit = parser.createParser(pClassBean.getTextContent());
        ForStatementVisitor forVisitor = new ForStatementVisitor();

        compilationUnit.accept(forVisitor);

        return !forVisitor.getForStatements().isEmpty();
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isSlowLoop(c);
    }

    @Override
    public String getName() {
        return "SL";
    }
}
