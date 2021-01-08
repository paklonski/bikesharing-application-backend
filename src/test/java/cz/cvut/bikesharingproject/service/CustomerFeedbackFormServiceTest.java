package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.BikesharingProjectApplication;
import cz.cvut.bikesharingproject.model.enums.CustomerFeedbackFormState;
import cz.cvut.bikesharingproject.model.feedback.CustomerFeedbackForm;
import cz.cvut.bikesharingproject.service.feedback.CustomerFeedbackFormService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static cz.cvut.bikesharingproject.utils.Generator.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@ComponentScan(basePackageClasses = BikesharingProjectApplication.class)
public class CustomerFeedbackFormServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CustomerFeedbackFormService sut;

    @Test
    public void findOpenReturnsSize() {
        CustomerFeedbackForm feedback1 = generateFeedback("It was OK.");
        CustomerFeedbackForm feedback2 = generateFeedback("It was AWFUL.");
        sut.persist(feedback1);
        sut.persist(feedback2);
        assertNotNull(feedback1.getId());
        final CustomerFeedbackForm updatedFeedback1 = em.find(CustomerFeedbackForm.class, feedback1.getId());
        updatedFeedback1.setStatus(CustomerFeedbackFormState.CLOSED);
        sut.update(updatedFeedback1);
        assertEquals(1, sut.findOpen().size());
    }
}
