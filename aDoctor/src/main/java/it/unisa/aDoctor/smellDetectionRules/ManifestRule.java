package it.unisa.aDoctor.smellDetectionRules;

import java.io.File;
import java.io.IOException;

public interface ManifestRule {

    public boolean hasSmell(File androidManifest) throws IOException;

    public String getName();
}
