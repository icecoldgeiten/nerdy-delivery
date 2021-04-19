import com.route.entity.Driver;
import com.route.entity.Order;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.logging.Logger;

public class TestDriveJPA {
    Logger log = Logger.getLogger(this.getClass().getName());

    @Test
    public void findDriver() {
        log.info("--- Testen ---");

        EntityManagerFactory session = Persistence.createEntityManagerFactory("ice-unit");
        EntityManager em = session.createEntityManager();

        em.getTransaction().begin();
        List<Order> result = em.createQuery("from Order", Order.class).getResultList();
        for (Order d : result) {
            System.out.println(d.getId() + " " + d.getRouteID() + " " + d.getComments() +  " " + d.getDelivered());
        }
        em.getTransaction().commit();
        em.close();
    }
}
