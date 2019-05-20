package it.unisa.aDoctor.process;

import it.unisa.aDoctor.smellDetectionRules.*;
import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.PackageBean;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.eclipse.core.runtime.CoreException;

import java.io.FileWriter;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class RunAndroidSmellDetection {

    private static final String NEW_LINE_SEPARATOR = "\n";
    public static String[] CLASS_FILE_HEADER;
    public static String[] MANIFEST_FILE_HEADER;

    // The folder contains the set of Android apps that need to be analyzed
    public static void main(String[] args) throws IOException, CoreException {

        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Started at " + ft.format(new Date()));

        // Folder containing android apps to analyze
        File experimentDirectory = FileUtils.getFile(args[0]);
        File fileName = new File(args[1]);

        ClassRule[] classRules = new ClassRule[] {
                new DataTransmissionWithoutCompressionRule(),
                new DurableWakeLockRule(),
                new InefficientDataFormatAndParserRule(),
                new InefficientDataStructureRule(),
                new InefficientSQLQueryRule(),
                new InternalGetterSetterRule(),
                new LeakingInnerClassRule(),
                new LeakingThreadRule(),
                new MemberIgnoringMethodRule(),
                new NoLowMemoryResolverRule(),
                new PublicDataRule(),
                new RigidAlarmManagerRule(),
                new SlowLoopRule(),
                new UnclosedCloseableRule(),
        };

        ManifestRule[] manifestRules = new ManifestRule[] {
            new DebuggableReleaseRule(),
            new SetConfigChangesRule(),
        };

        CLASS_FILE_HEADER = new String[classRules.length + 1];
        MANIFEST_FILE_HEADER = new String[manifestRules.length + 1];

        CLASS_FILE_HEADER[0] = "Class";
        for (int i=1; i<0; i++) {
            CLASS_FILE_HEADER[i] = classRules[i-1].getName();
        }

        MANIFEST_FILE_HEADER[0] = "Project";
        for (int i=1; i<0; i++) {
            MANIFEST_FILE_HEADER[i] = manifestRules[i-1].getName();
        }

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        FileWriter classFileWriter = new FileWriter(fileName);
        FileWriter manifestFileWriter = new FileWriter(fileName + "-manifest");
        try {
            CSVPrinter classCsvFilePrinter = new CSVPrinter(classFileWriter, csvFileFormat);
            CSVPrinter manifestCsvFilePrinter = new CSVPrinter(manifestFileWriter, csvFileFormat);
            classCsvFilePrinter.printRecord(CLASS_FILE_HEADER);
            manifestCsvFilePrinter.printRecord(MANIFEST_FILE_HEADER);

            for (File project : experimentDirectory.listFiles()) {
                if (!project.isHidden()) {

                    File manifest = getAndroidManifest(project);
                    List<String> mRecords = new ArrayList<>();

                    for (ManifestRule r : manifestRules) {
                        if (r.hasSmell(manifest)) {
                            mRecords.add("1");
                        } else {
                            mRecords.add("0");
                        }
                    }

                    manifestCsvFilePrinter.printRecord(mRecords);

                    // Method to convert a directory into a set of java packages.
                    ArrayList<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());

                    for (PackageBean packageBean : packages) {

                        for (ClassBean classBean : packageBean.getClasses()) {

                            List<String> record = new ArrayList<>();

                            System.out.println("-- Analyzing class: " + classBean.getBelongingPackage() + "." + classBean.getName());


                            for (ClassRule r : classRules) {
                                record.add(classBean.getBelongingPackage() + "." + classBean.getName());

                                if (r.hasSmell(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            classCsvFilePrinter.printRecord(record);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error encountered: " + e + "\n");
            e.printStackTrace();
        }
        System.out.println("CSV files were created successfully!");
        System.out.println("Finished at " + ft.format(new Date()));
    }

    public static File getAndroidManifest(File dir) {
        File androidManifest = null;
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            if (file.getName().equals("AndroidManifest.xml")) {
                androidManifest = file;
            }
        }
        return androidManifest;
    }

}
