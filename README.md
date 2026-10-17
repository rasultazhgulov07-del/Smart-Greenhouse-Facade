# Assignment 4 — Smart Greenhouse (Variant 9)

**Java 17 + Maven, console application, durable Properties files.** Three independent subsystems, two facades, compensation and restart recovery.

## Run (IntelliJ IDEA / terminal)

- Open this folder as a Maven project. Select JDK 17 or newer.
- `mvn compile exec:java` — start the console menu.
- `mvn test` — run JUnit tests.
- `mvn verify` — run tests and create `target/site/jacoco/index.html` coverage report.
- Reset demo data: stop the app and delete the **data** folder. Do not delete it if you want to demonstrate persistence.

Each menu action asks for a **new unique operation ID** except Resume, Inspect, Manual compensation, which need an existing ID.

## Domain and chain

- **A / Irrigation scheduling**: SoilRule validates moisture below 60; Schedule entity reserves irrigation schedule; ScheduleRepository persists it.
- **B / Ventilation**: ClimateRule validates temperature between 10 and 40; Ventilation activates; VentilationRepository persists it.
- **C / Nutrient dispatch**: NutrientRule checks nutrient stock >= 1; Nutrients records dispensing; NutrientsRepository persists it.
- Each subsystem has **4 classes**: entity, rule, repository and service. The enum is nested in its entity. Subsystems never import one another.
- The facade coordinates A → B → C and writes workflow stages to `data/jobs`.

## Live demonstration

1. Option 1, ID `success1`: all three succeed → COMPLETED.
2. Option 2, ID `badB`: temperature 50; B fails after A persisted → A CANCELLED, B absent, workflow COMPENSATED.
3. Option 3, ID `badC`: stock 0; C fails after A and B persisted → B STOPPED, then A CANCELLED, workflow COMPENSATED.
4. Option 4, ID `pause1`: pause after A → PENDING_B. Exit and restart. Option 5 with `pause1` → COMPLETED. Option 6 inspects persisted states.
5. Option 7 can manually compensate an unfinished operation. Completed nutrient dispensing is intentionally not reversible.

**Realistic interpretation:** A reserves a watering window; B starts ventilation, which can be stopped; C records nutrient dispatch. The design does not pretend to reverse physical dispensing. A failure at C is detected before nutrient dispensing is committed.

## Principle of Least Knowledge — before / after

Without facade, a client knows three subsystem services and must coordinate their order and undo operations:
```java
scheduleService.execute(id, 30);
ventilationService.execute(id, 25);
nutrientService.execute(id, 2);
```
The client would also need to handle exceptions and compensate earlier steps.

With facade:
```java
facade.start(id, 30, 25, 2);
```
The client now knows only the facade, which owns sequencing and compensation.

## Facade vs Adapter (reflection)

Facade is appropriate because the client needs one simple workflow built from three independent services. The services already have understandable interfaces; the problem is coordinating them, persisting progress, and undoing committed work after failures. An Adapter would translate one interface into another expected interface. It would not by itself orchestrate the multi-step business transaction. The second AdminFacade exposes inspection and manual compensation separately from the normal workflow.

## Tests and F.I.R.S.T.

JUnit 5 tests use temporary directories for fresh persisted state per test (Fast, Independent, Repeatable); assertions validate each outcome (Self-validating); commit tests together with code (Timely). `assertWorkflowIs` is a domain-specific test helper. Tests cover both compensation depths and restart recovery. JaCoCo measures line coverage; **verify the actual percentage in your environment** (80% is the assignment target; it is not guaranteed without running Maven).

## Caveats and limitations

- This is a small single-process teaching demo, not production-grade distributed transactions.
- Properties file writes are not atomic; unexpected power loss during a write can corrupt a file. Production code should use transactional storage or atomic replacement.
- Recovery handles an interruption after a persisted stage boundary. It does not fully solve crashes in the tiny gap between a subsystem write and the workflow-stage write; production systems need reconciliation/idempotency.
- Each new operation ID must be unique. The demo has no multi-user locking.

## Git submission

Create a new GitHub repository and push all source files, `pom.xml`, README and UML. Keep `data/` and `target/` out of Git. Commit tests with code. Submit the repository URL in Moodle. Confirm that variant 9 is approved for your individual assignment.
