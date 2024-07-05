package matteomoscardini.ultimoprogettosettimanale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import matteomoscardini.ultimoprogettosettimanale.entities.Event;

import java.util.Optional;
import java.util.UUID;

public interface EventDAO extends JpaRepository<Event, UUID> {
    Optional<Event> findByName(String name);
}
