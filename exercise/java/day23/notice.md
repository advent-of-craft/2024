# Notice of Use

This document provides a detailed explanation of the Sleigh ControlSystem

## !! Prohibited !!

The external package cannot be touched. 
They are provided by Santa and these components cannot be touched.

## The Sleigh Control System - Main App

You can control the command of the sleigh by typing the command you want.
Command prompt: (ascend (a), descend (d), park (p), or quit (q))

The state of the system is going to be displayed on the dashboard of the sleigh.

To see the prompt, you need to run the App component.

## Component: ControlSystem

The `ControlSystem` component represents the control system for the sleigh. It has the following properties:

- `XmasSpirit`: The amount of magic power required to fly the sleigh.
- `dashboard`: The dashboard for displaying status messages.
- `magicStable`: The magic stable that houses all the reindeer.
- `reindeerPowerUnits`: A list of all the reindeer with their power units attached.
- `status`: The current status of the sleigh engine.
- `action`: The current action of the sleigh.
- `controlMagicPower`: The current amount of magic power being controlled by the system.

The ControlSystem can do 5 actions:

### Start the system

Starts the sleigh engine and sets the status to "ON".

### Fly the sleigh

It makes the sleigh ascend by harnessing the magic power of all the reindeer. In order to make the sleigh fly, all the magic power of the control system is used.
- If the total magic power of the control system is less than the required Xmas spirit, an error state is returned asking to rest the reindeer.
- If the sleigh engine is not started, an error state is returned asking to start the sleigh.

### Hover the sleigh

It makes the sleigh descend and hover over a house. It stabilizes the sleigh so outside conditions don't affect Santa when he's dropping gifts through the chimney.
- If the sleigh engine is not started, an error state is returned asking to start the sleigh.

### Park the sleigh

It parks the sleigh and resets the times to harness magic power for all the reindeer.
- If the sleigh engine is not started, an error state is returned asking to start the sleigh.

### Stop the system

Stops the sleigh engine and sets the status to "OFF".



## Component: Reindeer

The Reindeer component represents a live reindeer capable of manifesting magical power:

- `spirit`: The spirit of the reindeer, which affects its magic power.
- `age`: The age of the reindeer, which affects its magic power and power pull limit.
- `name`: The name of the reindeer.
- `sick`: A boolean indicating whether the reindeer is sick. A sick reindeer cannot produce magic power.
- `timesHarnessing`: The number of times the reindeer has produced magic power.
- `powerPullLimit`: The maximum number of times the reindeer can produce magic power before needing rest.

The Reindeer component has two abilities:

### GetMagicPower

It returns the magic power of the reindeer. The magic power is calculated based on the reindeer's spirit, age, and sick status.

### NeedsRest

It returns `true` if the reindeer needs rest, and `false` otherwise. A reindeer needs rest if it has produced magic power the maximum number of times for its age, or if it is sick.



## Component: ReindeerPowerUnit

The ReindeerPowerUnit component represents a equipment to harness the magic power of the reindeer to reroute it to an external system. Here to the control system. It has the following properties:

- `reindeer`: The reindeer with the power unit attached.
- `amplifier`: The magic power amplifier associated with the power unit.

The `ReindeerPowerUnit` class has two methods:

- `harnessMagicPower()`: Harnesses the magic power of the reindeer and returns the amplified magic power. If the reindeer needs rest, the method returns 0.
- `checkMagicPower()`: Returns the current magic power of the reindeer.

## WARNING: existing issues

- Different type of amplifier exists but only the BASIC amplifier is used by the control system. 
  - The power unit in place at the moment does not allow to attach a new amplifier.
  - The other amplifiers are Blessed that can amplify the magic power by 2 and Divine that can amplify the magic power by 3.
  - **_Christmas town only has one divine amplifier and 2 blessed amplifiers._**
- A sick reindeer cannot generate magic power.
- The control system allows multiple actions.
