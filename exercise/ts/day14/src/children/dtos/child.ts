export class Child {
    Id!: string;
    FirstName?: string;
    MiddleName?: string;
    LastName?: string;
    BirthCity?: string;
    BirthDate!: string;
    Gender!: Gender;
    Address?: Address;
}

export enum Gender {
    Girl = "Girl",
    Boy = "Boy"
}

export class Address {
    Number!: string;
    Street!: string;
    City!: string;
    CountryId!: number;
}