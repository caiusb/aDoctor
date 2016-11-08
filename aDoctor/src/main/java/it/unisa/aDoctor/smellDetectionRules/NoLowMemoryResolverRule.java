package it.unisa.aDoctor.smellDetectionRules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import it.unisa.aDoctor.beans.ClassBean;

public class NoLowMemoryResolverRule {

    private final List<String> onLowMemoryClassList;

    public NoLowMemoryResolverRule() throws IOException {

        onLowMemoryClassList = new ArrayList<>();
        File classList = FileUtils.getFile(System.getProperty("user.dir") + "/src/main/resources/onLowMemoryClasses.txt");
        LineIterator iter = FileUtils.lineIterator(classList);
        while (iter.hasNext()) {
            onLowMemoryClassList.add(iter.next());
        }
        iter.close();
    }

    /**
     * Questo metodo restituisce true se la classe non implementa il metodo
     * onLowMemory()
     *
     * @param pClass
     * @return boolean
     */
    public boolean isNoLowMemoryResolver(ClassBean pClass) {

        if (inheritsOnLowMemoryMethod(pClass)) {
            Pattern regex = Pattern.compile("onLowMemory", Pattern.MULTILINE);
            Matcher regexMatcher = regex.matcher(pClass.getTextContent());
            if (!regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Questo metodo restituisce true se la classe eredita il metodo
     * onLowMemory()
     *
     * @param pClass
     * @return boolean
     */
    public boolean inheritsOnLowMemoryMethod(ClassBean pClass) {

        if (pClass.getSuperclass() != null) {
            for (String className : onLowMemoryClassList) {
                if (className.equals(pClass.getSuperclass())) {
                    return true;
                }
            }
        }
        return false;
    }

}
