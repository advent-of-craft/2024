type Eid = string & { readonly __brand: unique symbol };

export const isEid = (value: string | null | undefined): value is Eid  => {
    if(value?.length !== 8) {
        return false;
    }

    const EID_REGEX = /(?<sex>[123])(?<birthYear>[0-9]{2})(?<serial>[0-9]{3})(?<key>[0-9]{2})/;
    if(!EID_REGEX.test(value)) {
        return false
    }

    const parsed = value.match(EID_REGEX)?.groups;

    return true;
}
