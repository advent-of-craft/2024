type Eid = string & { readonly __brand: unique symbol };
export const isEid = (value: string | null | undefined): value is Eid  => {
    if(value?.length !== 8) {
        return false;
    }
    return true;
}
