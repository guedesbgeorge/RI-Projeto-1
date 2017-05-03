package classifier;

/**
 * Created by Ian on 23/04/2017.

 */
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SimpleLogistic;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.converters.TextDirectoryLoader;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.gui.graphvisualizer.GraphVisualizer;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BatchClassifier {
    private String data_location = "classifier-data/pages-txt";
    private Instances data;
    private Classifier classifier;
    private String classifier_type;

    public BatchClassifier(String classifier_type) {
        this.classifier_type = classifier_type;
        try {
            this.loadData();
            this.randomizeData();
            this.filterData();
            this.evaluateClassifier();
            //this.trainClassifier();
            //this.visualizeClassifier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void evaluateClassifier() throws Exception {
        //J48 TREE
        Evaluation eval = new Evaluation(data);
        J48 tree = new J48();
        eval.crossValidateModel(tree, data, 10, new Random(1));
        System.out.println(eval.toSummaryString("\nTree Results\n\n", false));

        //BAYES NET
        Evaluation eval2 = new Evaluation(data);
        BayesNet bayes = new BayesNet();
        eval2.crossValidateModel(bayes, data, 10, new Random(1));
        System.out.println(eval2.toSummaryString("\nBayes Net Results\n\n", false));

        //RANDOM FOREST
        Evaluation eval3 = new Evaluation(data);
        RandomForest rmf = new RandomForest();
        eval3.crossValidateModel(rmf, data, 10, new Random(1));
        System.out.println(eval3.toSummaryString("\nRandom Forest Results\n\n", false));

        //NAIVE BAYES
        Evaluation eval4 = new Evaluation(data);
        NaiveBayes nb = new NaiveBayes();
        eval4.crossValidateModel(nb, data, 10, new Random(1));
        System.out.println(eval4.toSummaryString("\nNaive Bayes Results\n\n", false));

        //LINEAR REGRESSION
        Evaluation eval7 = new Evaluation(data);
        SimpleLogistic regression = new SimpleLogistic();
        eval7.crossValidateModel(regression, data, 10, new Random(1));
        System.out.println(eval7.toSummaryString("\nLogistic Regression Results\n\n", false));

        //SMO
        Evaluation eval8 = new Evaluation(data);
        SMO smoCls = new SMO();
        eval8.crossValidateModel(smoCls, data, 10, new Random(1));
        System.out.println(eval8.toSummaryString("\nSMO Results\n\n", false));

        /*
        //MULTILAYER PERCEPTRON
        Evaluation eval6 = new Evaluation(data);
        MultilayerPerceptron mp = new MultilayerPerceptron();
        eval6.crossValidateModel(mp, data, 10, new Random(1));
        System.out.println(eval6.toSummaryString("\nMultilayer Perceptron Results\n\n", false));
        */
    }

    private void loadData() throws IOException {
        TextDirectoryLoader loader = new TextDirectoryLoader();
        loader.setDirectory(new File(data_location));
        data = loader.getDataSet();
        //System.out.println("\n\nImported data:\n\n" + data);
    }

    private void randomizeData() {
        Random randomGenerator = data.getRandomNumberGenerator(1);
        data.randomize(randomGenerator);
    }

    private void filterData() throws Exception {
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(data);
        data = Filter.useFilter(data, filter);
        //System.out.println("\n\nFiltered data:\n\n" + data);
    }

    private void trainClassifier() throws Exception {
        switch(classifier_type) {
            case "tree":
                J48 tree = new J48();
                tree.buildClassifier(data);
                this.classifier = tree;
            case "bayes":
                this.classifier = new BayesNet();
                this.classifier.buildClassifier(data);
            default:
                break;
        }
        //System.out.println("\n\nClassifier model:\n\n" + this.classifier);
    }

    public void visualizeClassifier() throws Exception {
        switch(classifier_type) {
            case "bayes":
                visualizeBayes();
            case "tree":
                visualizeTree();
            default:
                break;
        }
    }

    public void visualizeBayes() throws Exception {
        GraphVisualizer gv = new GraphVisualizer();
        gv.readBIF(((BayesNet) this.classifier).graph());

        JFrame jf = new JFrame("Classifier graph");
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setSize(800, 600);
        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(gv, BorderLayout.CENTER);
        jf.setVisible(true);

        gv.layoutGraph();
    }

    public void visualizeTree() throws Exception {
        TreeVisualizer tv = new TreeVisualizer(
                null, ((J48)this.classifier).graph(), new PlaceNode2());

        JFrame jf = new JFrame("Classifier graph");
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setSize(800, 600);
        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(tv, BorderLayout.CENTER);
        jf.setVisible(true);

        tv.fitToScreen();
    }

    public boolean classify(Instance instance) throws Exception {
        double clsLabel = this.classifier.classifyInstance(instance);
        return this.data.classAttribute().value((int) clsLabel).equals("yes");
    }
}
