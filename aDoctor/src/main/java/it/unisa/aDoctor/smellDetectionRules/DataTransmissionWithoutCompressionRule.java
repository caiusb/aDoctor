package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

public class DataTransmissionWithoutCompressionRule implements ClassRule {

    public boolean isDataTransmissionWithoutCompression(ClassBean pClassBean) {

        if (pClassBean.getTextContent().contains("File ")) {
            if (!pClassBean.getTextContent().contains("zip")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isDataTransmissionWithoutCompression(c);
    }

    @Override
    public String getName() {
        return "DTWC";
    }
}
