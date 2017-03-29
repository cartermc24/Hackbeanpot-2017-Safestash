package SafestashServer.AWS;

import io.indico.Indico;
import io.indico.api.image.FacialEmotion;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

/**
 * Created by cartermccardwell on 3/26/17.
 */
@Component
public class InidicoController
{
    final static String API_KEY = ""; //Put your Indigo Key Here
    final private Indico indico;

    public InidicoController()
    {
        indico = new Indico(API_KEY);
        indico.apiKey = API_KEY;
    }

    public String getFERByImage(String path)
    {
        try {
            BufferedImage bi = ImageIO.read(new File(path));
            IndicoResult result = indico.fer.predict(bi, "jpg");
            Map<FacialEmotion, Double> emotitions = result.getFer();

            List<FacialEmotion> keys = new ArrayList<>(emotitions.keySet());

            String emoStr = "";
            for (FacialEmotion fe : keys)
            {
                emoStr += fe.name() + ":";
                emoStr += emotitions.get(fe).toString();
                emoStr += "//";
            }
            return emoStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
