COPY Person FROM 'data/persons.csv';
COPY Country FROM 'data/countries.csv';
COPY Event FROM 'data/events.csv';
 

# Then import relationships
 COPY Ruled FROM 'data/ruled.csv';
 COPY ParticipatedIn FROM 'data/participated_in.csv';
 COPY OccurredIn FROM 'data/occurred_in.csv';
 
