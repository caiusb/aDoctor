package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class PublicDataRule implements ClassRule {

    public boolean isPublicData(ClassBean pClass) {

        for (MethodBean method : pClass.getMethods()) {
            if (method.getTextContent().contains("Context.MODE_WORLD_READABLE")) {
                return true;
            } else if (method.getTextContent().contains("Context.MODE_WORLD_WRITEABLE")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isPublicData(c);
    }

    @Override
    public String getName() {
        return "PD";
    }
}
