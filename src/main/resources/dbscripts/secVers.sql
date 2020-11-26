alter table if exists product_manipulations
    drop constraint if exists product_manipulations_dependent_demand_id_fkey;

alter table if exists product_manipulations
    drop constraint if exists product_manipulations_dependent_supply_id_fkey;

alter table product_lines
    drop constraint if exists product_lines_manipulation_id_fkey;

drop table if exists product_manipulations;

alter table product_lines
    drop column if exists manipulation_id;
alter table product_lines
    add column manipulation_id bigint;

create table product_manipulations
(

    id                  bigserial primary key unique,
    disc                varchar(255),
    type                varchar(255),
    creation_date       timestamp,
    description         varchar(255),
    bitrix_deal_id      bigint unique,
    dependent_supply_id bigint,
    dependent_demand_id bigint,
    supply_status       varchar(255),
    supply_provider_id  integer,
    user_id             bigint,
    old_id              bigint


);



insert into product_manipulations (id, disc, type, creation_date, bitrix_deal_id)
select demands.id, 'BDD', 'DEMAND', demands.creation_date, bitrix_deal_demands.bitrix_deal_id
from demands
         inner join bitrix_deal_demands on demands.id = bitrix_deal_demands.id;

INSERT INTO product_manipulations(id, disc, type, creation_date)
select supplies.id, 'SDD', 'DEMAND', supplies.creation_date
from supplies
where supply_status = 'WORKSHOP';

INSERT INTO product_manipulations(disc, type, creation_date, description, dependent_demand_id, supply_status,
                                  supply_provider_id, old_id)
select 'S',
       'SUPPLY',
       supplies.creation_date,
       supplies.description,
       supplies.id,
       supplies.supply_status,
       supplies.supply_provider_id,
       supplies.id
from supplies
where supply_status = 'WORKSHOP';

update product_manipulations
set dependent_supply_id = man.id
from product_manipulations as man
where product_manipulations.id = man.old_id;

INSERT INTO product_manipulations(id, disc, type, creation_date, description, supply_status, supply_provider_id)
select supplies.id,
       'S',
       'SUPPLY',
       supplies.creation_date,
       supplies.description,
       supplies.supply_status,
       supplies.supply_provider_id
from supplies
where supplies.supply_status not like 'WORKSHOP';


update product_lines
set manipulation_id = man.id
from product_manipulations as man
where product_lines.demand_product_line_ids = man.id;

update product_lines
set manipulation_id = man.id
from product_manipulations as man
where man.id = product_lines.supply_product_line_ids;

update product_lines
set manipulation_id = man.id
from product_manipulations as man
where man.id = product_lines.supply_dependent_product_line_ids;

update product_lines
set manipulation_id = man.id
from product_manipulations as man
where man.old_id = product_lines.supply_product_line_ids;

alter table product_lines
    add foreign key (manipulation_id) references product_manipulations (id);

alter table product_manipulations
    drop column old_id;

alter table product_manipulations
    add foreign key (dependent_supply_id) references product_manipulations (id);

alter table product_manipulations
    add foreign key (dependent_demand_id) references product_manipulations (id);

update product_manipulations
set user_id = 1
where disc = 'S'
  AND disc = 'SDD';

alter table product_manipulations
    add foreign key (user_id) references users (id);

alter table product_lines
    drop constraint if exists fk7v0huqu6qs4dm6cdvtyr1j6lr;
alter table product_lines
    drop constraint if exists fkctkkorhq5qb0lw7qdcclrqj94;
alter table product_lines
    drop constraint if exists fkf8hnj2vsjiqq0muna6o68hq97;
alter table product_lines
    drop column if exists demand_product_line_ids;
alter table product_lines
    drop column if exists supply_dependent_product_line_ids;
alter table product_lines
    drop column if exists supply_product_line_ids;

drop table if exists bitrix_deal_demands;
drop table if exists demands;
drop table if exists supplies;






