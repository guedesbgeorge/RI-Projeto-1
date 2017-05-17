package classifier;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.SGD;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
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
    private static ClassificationHelper instance = null;
    private String data_location = "classifier-data/pages-txt";
    private String stopwords_location = "classifier-data/stopwords.txt";
    private String classifier_type;

    private Instances data;
    private FilteredClassifier cls;
    private int runs = 0;

    /*
    private Instances trainSet;
    private Instances testSet;
    private Instances evalSet;
    */

    protected ClassificationHelper() {
        // Exists only to defeat instantiation.
    }

    public static synchronized ClassificationHelper getInstance() {
        if(instance == null) {
            instance = new ClassificationHelper("sgd");
        }
        return instance;
    }

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

            /*
            AttributeSelection as = new AttributeSelection();
            Ranker ranker = new Ranker();
            InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
            as.setEvaluator(infoGain);
            as.setSearch(ranker);
            cls.setFilter(as);
            */

            AbstractClassifier abstractClassifier;
            switch (classifier_type) {
                case "bayes":
                    abstractClassifier = new BayesNet();
                    break;
                case "sgd":
                    abstractClassifier = new SGD();
                    break;
                case "rmf":
                    abstractClassifier = new RandomForest();
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

    public synchronized void addToDataSet(String page, String classValue) throws Exception{
        double[] values = new double[data.numAttributes()];
        values[0] = data.attribute(0).addStringValue(page);
        weka.core.Instance instanceWeka = new weka.core.DenseInstance(1, values);
        instanceWeka.setDataset(data);
        instanceWeka.setClassValue(classValue);
        data.add(instanceWeka);
        runs++;
        if(runs >= 100) {
            Random randomGenerator = data.getRandomNumberGenerator(2);
            data.randomize(randomGenerator);
            cls.buildClassifier(data);
        }
    }

    public synchronized boolean classify(String page) throws Exception{
        double[] values = new double[data.numAttributes()];
        values[0] = data.attribute(0).addStringValue(page);
        weka.core.Instance instanceWeka = new weka.core.DenseInstance(1, values);
        instanceWeka.setDataset(data);
        double classificationResult = cls.classifyInstance(instanceWeka);
        if (classificationResult == 0) {
            System.out.println("false");
            return false;
        }
        System.out.println("true");
        return true;
    }

}
