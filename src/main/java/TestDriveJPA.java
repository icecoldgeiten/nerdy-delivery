import com.entity.Driver;
import com.entity.Order;
import com.entity.Route;
import com.google.CalculateRoute;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.helpers.AES256;
import javafx.scene.web.WebEngine;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;
import java.util.logging.Logger;

public class TestDriveJPA {
    Logger log = Logger.getLogger(this.getClass().getName());

    @Test
    public void findDriver() {

        //TODO:: hier gaan we even fuifen eerst thee pakken
//        engine.executeScript();
        String route = "https://maps.googleapis.com/maps/api/staticmap?sensor=false&size=838x254&path=weight:10%7Cenc:c''pEtjypUi@fAu@zA[j@mAzB&path=weight:10%7Cenc:{d`pEnpypUc@_@u@Q&key=AIzaSyBUR1VVIeyacS7msWkyp8VO8BZ6vkK9Jx8";
        System.out.println(route);

    }
}