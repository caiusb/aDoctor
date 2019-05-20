package it.unisa.aDoctor.smellDetectionRules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unisa.aDoctor.beans.ClassBean;

public class InefficientDataStructureRule implements ClassRule {

    public boolean isInefficientDataStructure(ClassBean pClass) {

        Pattern regex = Pattern.compile("(.*)HashMap<(\\s*)(Integer|Long)(\\s*),(\\s*)(.+)(\\s*)>", Pattern.MULTILINE);
        Matcher regexMatcher = regex.matcher(pClass.getTextContent());
        return regexMatcher.find();
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isInefficientDataStructure(c);
    }

    @Override
    public String getName() {
        return "IDS";
    }
}
