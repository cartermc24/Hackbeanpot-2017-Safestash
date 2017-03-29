package SafestashServer;

import SafestashServer.AWS.DynamoDBController;
import SafestashServer.DAO.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cartermccardwell on 3/25/17.
 */
@RestController
public class Requests
{
    @Autowired
    DynamoDBController dbController;

    @RequestMapping("/status/{uid}")
    public String requestStatus(@PathVariable("uid") String uid)
    {
        List<UserRecord> records = dbController.queryUsersByUID(uid);
        if (records.size() < 1)
        {
            return uid + ": not found";
        }

        UserRecord record = records.get(0);

        String response = "User details for <strong>" + uid + "</strong>:<br>" + "Nice name: <strong>" + record.getNiceName() + "</strong><br>\tCompartments:<strong> ";
        for (Integer i : record.getCompartments())
        {
            response += " " + i;
        }

        return response + "</strong>";
    }

    @RequestMapping("/testimage")
    public String testImage()
    {
        String result = OpenFaceConnector.SearchModelByFacialImage("/home/carter/PICTURES/original/keith/image-55.jpg");
        if (result == null)
        {
            return "Unknown";
        }

        String uid = OpenFaceConnector.getUid(result);
        float confidence = OpenFaceConnector.getConfidence(result);

        List<UserRecord> records = dbController.queryUsersByUID(uid);
        if (records.size() < 1)
        {
            return uid + ": not found";
        }

        UserRecord record = records.get(0);

        String response = "User details for <strong>" + uid + "</strong>:<br>" + "Nice name: <strong>" + record.getNiceName() +
                "</strong><br>Confidence:<strong>" + confidence + "</strong><br>\tCompartments:<strong> ";
        for (Integer i : record.getCompartments())
        {
            response += " " + i;
        }

        return response + "</strong>";
    }
}
