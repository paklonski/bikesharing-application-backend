package cz.cvut.bikesharingproject.model.feedback;

import com.fasterxml.jackson.annotation.*;
import cz.cvut.bikesharingproject.model.AbstractEntity;
import cz.cvut.bikesharingproject.model.User;
import cz.cvut.bikesharingproject.model.enums.CustomerFeedbackFormState;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "FEEDBACKTYPE")
@DiscriminatorValue("FEEDBACK")
@Table(name = "FEEDBACKS")
@NamedQueries({
        @NamedQuery(name = "Feedback.findOpen",
                query = "SELECT c FROM CustomerFeedbackForm c WHERE c.enabled = true AND " +
                        "c.status = cz.cvut.bikesharingproject.model.enums.CustomerFeedbackFormState.OPENED")
})
public class CustomerFeedbackForm extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerFeedbackFormState status;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column
    private String comment;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime timeOfCreation;

    @ManyToMany
    @JoinTable(
            name = "feedbacks_users",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
    private Set<User> participants = new HashSet<>();
}
