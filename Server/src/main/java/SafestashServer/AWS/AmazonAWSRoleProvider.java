package SafestashServer.AWS;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

/**
 * Created by cartermccardwell on 3/25/17.
 */
public class AmazonAWSRoleProvider
{
    final private static String AWS_ACCESS_KEY = ""; //Put your AWS Access Key Here
    final private static String AWS_SECRET_KEY = ""; //Put your AWS Secret Key Here

    public static AWSStaticCredentialsProvider getCredentials()
    {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY));
    }
}
