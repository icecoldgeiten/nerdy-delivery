import com.entity.Driver;
import com.entity.Order;
import com.entity.Route;
import com.google.CalculateRoute;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.helpers.AES256;
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
        log.info("--- Testen ---");

        EntityManagerFactory session = Persistence.createEntityManagerFactory("ice-unit");
        EntityManager em = session.createEntityManager();
        CalculateRoute re = new CalculateRoute();

        em.getTransaction().begin();
        Route r = em.find(Route.class, 2L);

        DirectionsRoute mark = re.RouteMaker(r.getOrders());

        for (DirectionsLeg w : mark.legs) {
            for (DirectionsStep s : w.steps) {
                System.out.println(s.startLocation);
                System.out.println(s.endLocation);
            }
        }

        em.getTransaction().commit();
        em.close();
    }
}