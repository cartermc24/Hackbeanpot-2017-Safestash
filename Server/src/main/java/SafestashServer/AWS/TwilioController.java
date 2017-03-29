package SafestashServer.AWS;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cartermccardwell on 3/26/17.
 */
@Component
public class TwilioController
{
    public static final String ACCOUNT_SID = ""; //Put your Twilio Accound SID here
    public static final String AUTH_TOKEN = ""; //Put your auth token here
    public static final String PHONE_NUMBER = ""; //The phone number sending the messages
    public static final String ALT_PHONE_NUMBER = ""; //The alt phone number

    public final List<String> recipients;
    public final List<String> altRecipients;

    public TwilioController()
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //List of phone numbers to recieve notifications
        recipients = new ArrayList<>();
        recipients.add("Phone number 1");
        recipients.add("Phone number 2");

        //Alt phone number list
        altRecipients = new ArrayList<>();
        recipients.add("Alt number 1");
        recipients.add("Alt number 2");
    }

    public void sendMessage(String message)
    {
        for (String number : recipients) {
            Message m = Message.creator(new PhoneNumber(number),
                    new PhoneNumber(PHONE_NUMBER), message).create();
            System.out.println(m.getSid());
        }
    }

    public void sendAltMessage(String message)
    {
        for (String number : altRecipients) {
            Message m = Message.creator(new PhoneNumber(number),
                    new PhoneNumber(ALT_PHONE_NUMBER), message).create();
            System.out.println(m.getSid());
        }
    }
}
