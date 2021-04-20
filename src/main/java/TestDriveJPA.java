import com.route.entity.Driver;
import com.route.entity.Order;
import com.route.entity.Route;
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

        em.getTransaction().begin();
        Route r = em.find(Route.class, 3L);

        Driver d = r.getDriver();
        Set<Order> o = r.getOrders();

        System.out.println("route nummer" + r.getId());
        System.out.println(d.getName() + "gaat bezorgen");
        for (Order e : o) {
            System.out.println(e.getId());
            System.out.println(e.getCustumer());
        }

        em.getTransaction().commit();
        em.close();
    }
}