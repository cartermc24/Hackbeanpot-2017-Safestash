package SafestashServer;

import SafestashServer.AWS.DynamoDBController;
import SafestashServer.AWS.InidicoController;
import SafestashServer.AWS.SNSController;
import SafestashServer.AWS.TwilioController;
import SafestashServer.DAO.UserRecord;
import SafestashServer.StorageSystem.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

/**
 * Created by cartermccardwell on 3/25/17.
 */
@RestController
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    private DynamoDBController dbController;

    @Autowired
    private TwilioController twilioController;

    @Autowired
    private InidicoController inidicoController;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/checkFace")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        System.out.println("checkFace: running statistical facial feature match");

        String path = storageService.store(file);

        String response = OpenFaceConnector.SearchModelByFacialImage(path);

        if (response == null)
        {
            return "X";
        }

        if (OpenFaceConnector.getConfidence(response) > 0.65)
        {
            List<UserRecord> records = dbController.queryUsersByUID(OpenFaceConnector.getUid(response));
            if (records.size() < 1) { return "X"; }

            UserRecord record = records.get(0);
            String compartments = "";

            for (int i : record.getCompartments())
            {
                compartments += i + "/";
            }

            System.out.println("\nReasoning behind recognition:\n\tName: " + record.getNiceName() + "\n\tConfidence: "
                                + OpenFaceConnector.getConfidence(response));

            //twilioController.sendAltMessage(inidicoController.getFERByImage(path));

            return compartments;
        }
        else
        {
            twilioController.sendMessage("Login failed for " + OpenFaceConnector.getUid(response) + " (confidence=" + OpenFaceConnector.getConfidence(response) + ")");
        }

        return "X";
    }
}