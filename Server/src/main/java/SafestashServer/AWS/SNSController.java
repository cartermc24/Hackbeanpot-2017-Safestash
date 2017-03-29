package SafestashServer.AWS;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Created by cartermccardwell on 3/25/17.
 */
@Component
public class SNSController
{
    private AmazonSNS client;
    private final static String topic = "arn:aws:sns:us-east-1:191401174929:SafeStashTextAlerts";

    public SNSController()
    {
        client = AmazonSNSClientBuilder.standard()
                    .withCredentials(AmazonAWSRoleProvider.getCredentials())
                    .withRegion(Regions.US_EAST_1)
                    .build();
    }

    public void sendMessage(String message)
    {
        client.publish(new PublishRequest(topic, message));
    }
}
