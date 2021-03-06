package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class MemberIgnoringMethodRule implements ClassRule {

    public boolean isMemberIgnoringMethod(ClassBean pClass) {

        for (MethodBean methodBean : pClass.getMethods()) {
            if (!methodBean.isStatic()) {
                if (pClass.getInstanceVariables().size() > 0) {
                    if (methodBean.getUsedInstanceVariables().isEmpty()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasSmell(ClassBean c) {
        return isMemberIgnoringMethod(c);
    }

    @Override
    public String getName() {
        return "MIM";
    }
}
