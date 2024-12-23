from core.control import ControlSystem, ReindeersNeedRestException, SleighNotStartedException


def main():
    control_system = ControlSystem()
    control_system.start_system()

    keep_running = True

    while keep_running:
        command = input("Enter a command (ascend (a), descend (d), park (p), or quit (q)): ")

        if command in ("ascend", "a"):
            try:
                control_system.ascend()
            except (ReindeersNeedRestException, SleighNotStartedException) as e:
                print(e)
        elif command in ("descend", "d"):
            try:
                control_system.descend()
            except SleighNotStartedException as e:
                print(e)
        elif command in ("park", "p"):
            try:
                control_system.park()
            except SleighNotStartedException as e:
                print(e)
        elif command in ("quit", "q"):
            keep_running = False
        else:
            print("Invalid command. Please try again.")

    control_system.stop_system()


if __name__ == "__main__":
    main()
