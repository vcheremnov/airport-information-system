create function get_average_tickets_sold_by_city_id(cityId bigint)
returns numeric
as $$

select avg(ticketsSold) from (
   select count(*) as ticketsSold
   from flight f inner join ticket t on f.id = t.flight_id
   where f.city_id = cityId
   and f.is_cancelled = false
   and t.status = 'SOLD'
   group by f.id
) as ticketsSoldPerFlight;

$$
language sql
returns null on null input