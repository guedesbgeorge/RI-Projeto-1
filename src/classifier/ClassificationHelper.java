package classifier;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;
import java.util.Random;

/**
 * Created by ianmanor on 15/05/17.
 */
public class ClassificationHelper {
    private String data_location = "classifier-data/pages-txt";
    private String stopwords_location = "classifier-data/stopwords.txt";
    private Instances data;
    private FilteredClassifier cls;
    private String classifier_type;

    public ClassificationHelper(String classifier_type) {
        this.classifier_type = classifier_type;
        try {
            //load data
            TextDirectoryLoader loader = new TextDirectoryLoader();
            loader.setDirectory(new File(data_location));
            data = loader.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);
            
            //randomize data
            Random randomGenerator = data.getRandomNumberGenerator(1);
            data.randomize(randomGenerator);

            //build filtered classifier
            StringToWordVector filter = new StringToWordVector();
            filter.setInputFormat(data);
            filter.setStopwordsHandler(new Stopwords(stopwords_location));
            cls = new FilteredClassifier();
            cls.setFilter(filter);

            AbstractClassifier abstractClassifier;
            switch(classifier_type) {
                case "bayes":
                    abstractClassifier = new BayesNet();
                    break;
                default:
                    abstractClassifier = new J48();
                    break;
            }
            cls.setClassifier(abstractClassifier);
            cls.buildClassifier(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean classify(String page) throws Exception{
        double[] values = new double[data.numAttributes()];
        values[0] = data.attribute(0).addStringValue(page);
        weka.core.Instance instanceWeka = new weka.core.DenseInstance(1, values);
        instanceWeka.setDataset(data);
        double classificationResult = cls.classifyInstance(instanceWeka);
        if (classificationResult == 0) {
            return false;
        }
        return true;
    }

}
