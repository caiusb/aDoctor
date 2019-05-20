package it.unisa.aDoctor.smellDetectionRules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class DurableWakeLockRule implements ClassRule {

    public boolean isDurableWakeLock(ClassBean pClass) {

        Pattern regex = Pattern.compile("(.*)acquire(\\s*)()", Pattern.MULTILINE);
        for (MethodBean method : pClass.getMethods()) {
            Matcher regexMatcher = regex.matcher(method.getTextContent());
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isDurableWakeLock(c);
    }

    @Override
    public String getName() {
        return "DW";
    }
}
