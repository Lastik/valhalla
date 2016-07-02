Welcome to Valhalla, where player can beat God!

This project is made specially for Pixonic (part of their test task).

WorldService class can process ScheduleAttack(time: DateTime, attack: AttackNpc) messages.
AttackNpc class extends Callable. Messages to WorldService come from Player actors, which
might run on different threads.