create function check_repair_time() returns trigger as $repair_trigger$
    BEGIN

        if new.finish_time < new.start_time then
            raise exception E'Repair\'s finish time can\'t be less than start time';
        end if;

        return new;

    END
$repair_trigger$ LANGUAGE plpgsql;

create trigger repair_trigger before insert or update on repair
    for each row execute procedure check_repair_time();