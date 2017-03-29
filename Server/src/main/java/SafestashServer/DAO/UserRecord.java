package SafestashServer.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cartermccardwell on 3/25/17.
 */

@DynamoDBTable(tableName = "SAFESTASHUSERS")
public class UserRecord
{
    String uid;
    String niceName;
    List<Integer> compartments;

    public UserRecord()
    {
        compartments = new ArrayList<>();
    }

    @DynamoDBHashKey(attributeName = "uid")
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    @DynamoDBAttribute(attributeName = "niceName")
    public String getNiceName() { return niceName; }
    public void setNiceName(String niceName) { this.niceName = niceName; }

    @DynamoDBAttribute(attributeName = "compartments")
    public List<Integer> getCompartments() { return compartments; }
    public void setCompartments(List<Integer> compartments) { this.compartments = compartments; }
}
