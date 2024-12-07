type Eid = string & { readonly __brand: unique symbol };

const isValidSerial = (serial: string): boolean => {
    return Number.parseInt(serial) > 0;
}

export const isEid = (value: string | null | undefined): value is Eid  => {
    if(value?.length !== 8) {
        return false;
    }

    const EID_REGEX = /(?<sex>[123])(?<birthYear>[0-9]{2})(?<serial>[0-9]{3})(?<key>[0-9]{2})/;

    if(!value.match(EID_REGEX) || !isValidSerial(value.match(EID_REGEX)?.groups?.serial)) {
        return false
    }

    return true;
}
