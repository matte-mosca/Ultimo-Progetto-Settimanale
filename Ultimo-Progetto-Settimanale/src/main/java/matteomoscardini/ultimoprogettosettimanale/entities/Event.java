package matteomoscardini.ultimoprogettosettimanale.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String description;
    private LocalDate date;
    private String location;
    private int nPartecipants;
    @ManyToMany(mappedBy = "events")
    private List<User> users = new ArrayList<>();

    public Event(String name, String description, LocalDate date, String location, int nPartecipants) {
        this.name = this.name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.nPartecipants = nPartecipants;
    }
}
