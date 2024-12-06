type Eid = string & { readonly __brand: unique symbol };
export const isEid = (value: string | null | undefined): value is Eid  => {
    if(!value) {
        return false;
    }
    return true;
}
