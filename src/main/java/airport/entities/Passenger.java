package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "passenger")
@Getter @Setter
public class Passenger extends Person { }
