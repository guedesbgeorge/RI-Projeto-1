package classifier;

/**
 * Created by Ian on 23/04/2017.

 */
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.converters.TextDirectoryLoader;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BatchClassifier {
    private String data_location = "classifier-data/pages-txt";
    private Instances data;
    private Classifier classifier;

    public BatchClassifier() {
        try {
            this.loadData();
            this.randomizeData();
            this.filterData();
            this.trainClassifier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws IOException {
        TextDirectoryLoader loader = new TextDirectoryLoader();
        loader.setDirectory(new File(data_location));
        data = loader.getDataSet();
        Instances dataRaw = loader.getDataSet();
        System.out.println("\n\nImported data:\n\n" + dataRaw);
    }

    private void randomizeData() {
        Random randomGenerator = data.getRandomNumberGenerator(1);
        data.randomize(randomGenerator);
    }

    private void filterData() throws Exception {
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(data);
        Instances dataFiltered = Filter.useFilter(data, filter);
        System.out.println("\n\nFiltered data:\n\n" + dataFiltered);
    }

    private void trainClassifier() throws Exception {
        J48 tree = new J48();
        tree.buildClassifier(data);
        this.classifier = tree;
        System.out.println("\n\nClassifier model:\n\n" + tree);
    }

    public double classify(Instance instance) throws Exception {
        return classifier.classifyInstance(instance);
    }
}
