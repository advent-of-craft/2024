from typing import Optional
from uuid import UUID

from children.db2 import X5T78
from children.dtos import Child, Gender, Address


def map_salutation_to_gender(salutation: str) -> Optional[Gender]:
    if salutation == "Girl":
        return Gender.Girl
    elif salutation == "Boy":
        return Gender.Boy
    else:
        return None


def to_address(st_num, st_name, st_c, st_cid):
    return Address(st_num, st_name, st_c, int(st_cid))


def map_x5t78_to_child(x5t78: X5T78) -> Child:
    return Child(
        id=UUID(x5t78.id),
        first_name=x5t78.n1,
        middle_name=x5t78.n2,
        last_name=x5t78.n3,
        birth_city=x5t78.city_of_birth_pc,
        birth_date=x5t78.person_bd,
        gender=map_salutation_to_gender(x5t78.salutation),
        address=to_address(x5t78.st_num, x5t78.st_name, x5t78.st_c, x5t78.st_cid)
    )
