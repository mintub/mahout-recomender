package com.hadoop.x2_mahout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
      /*  String uri="hdfs://localhost:54310/demo/wordCount";
		Configuration conf = new Configuration();
		FileSystem fs=FileSystem.get(URI.create(uri), conf);
		FSDataInputStream in=null;
		in=fs.open(new Path(uri)); */
        DataModel model = new FileDataModel(new File("data/dataset.csv"));
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
        
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        List<RecommendedItem> recommendations = recommender.recommend(2, 3);
        for (RecommendedItem recommendation : recommendations) {
          System.out.println(recommendation);
        } 
       /* String uri="hdfs://localhost:54310/demo/wordCount";
		Configuration conf = new Configuration();
		FileSystem fs=FileSystem.get(URI.create(uri), conf);
		InputStream in=null;
		
		try{
			in=fs.open(new Path(uri));
			IOUtils.copyBytes(in, System.out,4096,false);
		}
		finally
		{
			IOUtils.closeStream(in);
		}
        
        System.out.println("end"); */
    }
}
