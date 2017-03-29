package SafestashServer.AWS;

import SafestashServer.DAO.UserRecord;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cartermccardwell on 3/25/17.
 */

@Component
public class DynamoDBController
{
    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    public DynamoDBController()
    {
        client = AmazonDynamoDBClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1)
                        .withCredentials(AmazonAWSRoleProvider.getCredentials())
                        .build();
        mapper = new DynamoDBMapper(client);
    }

    public List<UserRecord> queryUsersByUID(String uid)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":UID", new AttributeValue().withS(uid));

        DynamoDBQueryExpression<UserRecord> queryExpression = new DynamoDBQueryExpression<UserRecord>()
                .withKeyConditionExpression("uid = :UID")
                .withExpressionAttributeValues(eav);

        return mapper.query(UserRecord.class, queryExpression);
    }

    public void commitUser(UserRecord record)
    {
        mapper.save(record);
    }
}
