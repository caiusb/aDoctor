package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

public class LeakingThreadRule implements ClassRule {

    public boolean isLeakingThread(ClassBean pClass) {

        if (pClass.getTextContent().contains("run()")) {
            if (!pClass.getTextContent().contains("stop()")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isLeakingThread(c);
    }

    @Override
    public String getName() {
        return "LT";
    }
}
