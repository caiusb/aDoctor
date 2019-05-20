package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class InefficientSQLQueryRule implements ClassRule {

    public boolean isInefficientSQLQuery(ClassBean pClass) {

        for (String importString : pClass.getImports()) {
            if (importString.contains("java.sql")) {
                for (MethodBean method : pClass.getMethods()) {
                    if (method.getTextContent().contains("Connection ")
                            || method.getTextContent().contains("Statement ")
                            || method.getTextContent().contains("ResultSet ")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isInefficientSQLQuery(c);
    }

    @Override
    public String getName() {
        return "ISQLQ";
    }
}
