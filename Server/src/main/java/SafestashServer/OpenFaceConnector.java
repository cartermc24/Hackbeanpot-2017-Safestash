package SafestashServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by cartermccardwell on 3/25/17.
 */
public class OpenFaceConnector
{
    static String SearchModelByFacialImage(String path)
    {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"/home/carter/identFace.sh", path};
            Process proc = rt.exec(commands);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                if (s.startsWith("Predict"))
                {
                    return s.substring(8);
                }
            }

            System.out.println("No faces detected - [[Sending LOCK_ALL command]]");

            // read any errors from the attempted command
            //System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                //System.out.println(s);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return null;
    }

    static float getConfidence(String substring)
    {
        String confidence = "";
        for (int i = 0; i < substring.length(); i++)
        {
            confidence += substring.charAt(i);
            if (substring.charAt(i) == '/')
            {
                confidence = "";
            }
        }
        return Float.parseFloat(confidence);
    }

    static String getUid(String substring)
    {
        String uid = "";
        for (int i = 0; i < substring.length(); i++)
        {
            if (substring.charAt(i) == '/')
            {
                return uid;
            }
            uid += substring.charAt(i);
        }
        return null;
    }
}
