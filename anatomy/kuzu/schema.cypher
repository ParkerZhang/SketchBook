// Drop existing tables
DROP TABLE Ruled;
DROP TABLE ParticipatedIn;
DROP TABLE OccurredIn;
DROP TABLE Person;
DROP TABLE Country;
DROP TABLE Event;

// Recreate the schema
CREATE NODE TABLE Person(
    name STRING,
    title STRING,
    PRIMARY KEY (name)
);

CREATE NODE TABLE Country(
    name STRING,
    capital STRING,
    PRIMARY KEY (name)
);

CREATE NODE TABLE Event(
    name STRING,
    year INT32,
    description STRING,
    PRIMARY KEY (name)
);

CREATE REL TABLE Ruled(
    FROM Person TO Country,
    start_year INT32,
    end_year INT32
);

CREATE REL TABLE ParticipatedIn(
    FROM Person TO Event,
    role STRING
);

CREATE REL TABLE OccurredIn(
    FROM Event TO Country
);
