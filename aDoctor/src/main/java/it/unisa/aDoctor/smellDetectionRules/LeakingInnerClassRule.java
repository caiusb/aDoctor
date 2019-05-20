package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

public class LeakingInnerClassRule implements ClassRule {

    public boolean isLeakingInnerClass(ClassBean pClass) {
        for (ClassBean inner : pClass.getInnerClasses()) {
            if (!inner.isStatic()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isLeakingInnerClass(c);
    }

    @Override
    public String getName() {
        return "LIC";
    }
}
