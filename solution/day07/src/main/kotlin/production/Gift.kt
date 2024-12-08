package production

import production.Status.Producing

data class Gift(val name: String, var status: Status = Producing)

enum class Status {
    Producing,
    Produced
}
