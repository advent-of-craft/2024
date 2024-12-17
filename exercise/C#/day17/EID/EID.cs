using LanguageExt;
using LanguageExt.Common;

namespace EID;

public class EID(string potentialEid)
{
    public string Value => potentialEid;
    private const int ComplementNumber = 97;
    
    public static Either<Error, EID> Parse(string potentialEid)
        => VerifyNotEmpty(potentialEid)
            .Bind(VerifyDigit)
            .Bind(VerifySex)
            .Bind(VerifySerialNumber)
            .Bind(VerifyKey)
            .Select(s => new EID(s));

    private static Either<Error, string> VerifyKey(string potentialEid)
        => ParseEidKey(potentialEid) == ComplementNumber - GetEidNumber(potentialEid) % ComplementNumber ?
            Either<Error, string>.Right(potentialEid) :
            Error.New("EID key is invalid");
    
    private static Either<Error, string> VerifySerialNumber(string potentialEid)
    {
        int serialNumber = int.Parse(potentialEid.Substring(2, 3));
        return serialNumber is < 1 or > 999   ?
            Error.New("EID serial number must be between 001 and 999 digits") 
            : Either<Error, string>.Right(potentialEid);
    }

    private static Either<Error, string> VerifySex(string potentialEid)
        => (ElfSex)char.GetNumericValue(potentialEid[0]) switch
        {
            ElfSex.Sloubi or ElfSex.Gagna or ElfSex.Catact => Either<Error, string>.Right(potentialEid),
            _ => Error.New("EID sex is invalid, must be 1, 2 or 3")
        };

    private static Either<Error, string> VerifyDigit(string potentialEid)
    {
        return potentialEid.Any(c => !char.IsDigit(c)) ?
            Error.New("EID must contain only digits") 
            : Either<Error, string>.Right(potentialEid);
    }

    private static Either<Error, string> VerifyNotEmpty(string potentialEid)
        => string.IsNullOrWhiteSpace(potentialEid)
            ? Error.New("EID cannot be empty")
            : Either<Error, string>.Right(potentialEid);
    
    private static int GetEidNumber(string potentialEid)
        => int.Parse(potentialEid.Substring(0, 6));

    private static int ParseEidKey(string potentialEid)
        => int.Parse(potentialEid.Substring(6, 2));
}