package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

public interface ClassRule {

    public boolean hasSmell(ClassBean c);

    public String getName();
}
