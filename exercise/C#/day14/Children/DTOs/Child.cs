namespace Children.DTOs
{
    public class Child
    {
        public Guid Id { get; set; }
        public string? FirstName { get; set; }
        public string? MiddleName { get; set; }
        public string? LastName { get; set; }
        public string? BirthCity { get; set; }
        public DateOnly BirthDate { get; set; }
        public Gender Gender { get; set; }
        public Address? Address { get; set; }
    }

    public enum Gender
    {
        Girl,
        Boy
    }

    public class Address
    {
        public string Number { get; set; }
        public string Street { get; set; }
        public string City { get; set; }
        public int CountryId { get; set; }
    }
}