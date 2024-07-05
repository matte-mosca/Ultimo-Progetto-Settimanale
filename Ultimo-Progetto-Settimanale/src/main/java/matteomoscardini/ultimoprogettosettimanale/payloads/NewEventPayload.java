package matteomoscardini.ultimoprogettosettimanale.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventPayload (@NotEmpty(message = "Don't forget to set a Title for this Event!")
                               String name,

                               @NotEmpty(message = "Don't forget to descripe the Event!")
                               @Size(min = 3, max = 30, message = "The first must be between 10 and 150 characters")
                               String description,

                               @NotEmpty(message = "Your Event needs a location!")
                               String location,

                               @NotNull(message = "Don't forget to add a Date!")
                               LocalDate date,

                               @NotNull(message = "Don't forget to add a maximun number of attendees")
                               int nPartecipants) {
}
