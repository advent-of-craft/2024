type Eid = string & { readonly __brand: unique symbol };
export const isEid = (value: string | null | undefined): value is Eid  => {
    if(value?.length !== 8) {
        return false;
    }

    if(!['1', '2', '3'].includes(value.at(0))) {
        return false
    }
    return true;
}
