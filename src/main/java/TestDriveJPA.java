import com.route.entity.Driver;
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
        List<Driver> result = em.createQuery("from Driver", Driver.class).getResultList();
        for (Driver d : result) {
            System.out.println(d.getId() + " " + d.getName());
        }
        em.getTransaction().commit();
        em.close();
    }
}