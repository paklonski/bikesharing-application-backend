package cz.cvut.bikesharingproject.dao;

import cz.cvut.bikesharingproject.BikesharingProjectApplication;
import cz.cvut.bikesharingproject.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cz.cvut.bikesharingproject.utils.Generator.generateUser;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = BikesharingProjectApplication.class)
public class BaseDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao sut;

    @Test
    public void persistSavesAnInstance() {
        final User user = generateUser();
        sut.persist(user);
        assertNotNull(user.getId());
        final User result = em.find(User.class, user.getId());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void findRetrievesInstanceById() {
        final User user = generateUser();
        em.persistAndFlush(user);
        assertNotNull(user.getId());
        final User result = sut.find(user.getId());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void findAllRetrievesAllInstancesOfType() {
        final User user1 = generateUser();
        em.persistAndFlush(user1);
        final User user2 = generateUser();
        em.persistAndFlush(user2);
        final User user3 = generateUser();
        em.persistAndFlush(user3);
        final List<User> result = sut.findAll();
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(user1.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(user2.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(user3.getId())));
    }

    @Test
    public void updateUpdatesAnInstance() {
        final User user = generateUser();
        em.persistAndFlush(user);
        final User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setName("Tomas");
        sut.update(updatedUser);
        final User result = sut.find(user.getId());
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void removeRemovesACertainInstance() {
        final User user = generateUser();
        em.persistAndFlush(user);
        assertNotNull(em.find(User.class, user.getId()));
        em.detach(user);
        sut.remove(user);
        assertNull(em.find(User.class, user.getId()));
    }

    @Test
    public void removeDoesNothingIfInstanceDoesNotExist() {
        final User user = generateUser();
        user.setId(18880290);
        assertNull(em.find(User.class, user.getId()));
        sut.remove(user);
        assertNull(em.find(User.class, user.getId()));
    }

    @Test
    public void existsReturnsTrueForExistingIdentifier() {
        final User user = generateUser();
        em.persistAndFlush(user);
        assertTrue(sut.exists(user.getId()));
    }
}
