package csvfile;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class AWScsv {

	public static void main(String[] args) throws Exception {
		String accessKey = "AKIAIRKDJ6MFDPE7DL";
		String SecretKey = "3z+JmfR5kWvw26/ymcHVzR7WpAoaYgstI6Y4";
		String bucketName = "dataelements123";
		String key = "annual-enterprise-survey-2018-financial-year-provisional-csv.csv";
		
		AWSCredentials credentials = new BasicAWSCredentials("AKIAIRKDJ6MFDPE7DL","3z+JmfR5kWvw26/ymcHVzR7WpAoaYgstI6Y4");
		  ClientConfiguration clientConfiguration = new ClientConfiguration();
		
           clientConfiguration.setConnectionTimeout(50000);
           clientConfiguration.setMaxConnections(500);
           clientConfiguration.setSocketTimeout(100000);
           clientConfiguration.setMaxErrorRetry(10);
		 
           AmazonS3 s3client = new AmazonS3Client(credentials);
          List<Bucket> bu = s3client.listBuckets();
          for(Bucket bucket : bu) {
		 System.out.println(bucket.getName());
		 
		 S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
	        try {
	            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                    .withRegion(Regions.AP_SOUTH_1)
	                    .withCredentials(new ProfileCredentialsProvider())
	                    .build();
		 fullObject = s3client.getObject(new GetObjectRequest(bucketName, key));
         System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
         System.out.println("Content: ");
         displayTextInputStream(fullObject.getObjectContent());
         System.out.println(fullObject.getObjectMetadata());
		
          }catch (AmazonServiceException e) {
        	  e.printStackTrace();
          }
	}
}



private static void displayTextInputStream(InputStream input) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    String line = null;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
    System.out.println();
}
}
