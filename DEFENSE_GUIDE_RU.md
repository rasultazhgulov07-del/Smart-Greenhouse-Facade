# Защита Assignment 4 — Smart Greenhouse

## 60-секундное объяснение

This project demonstrates the Facade design pattern. The client calls GreenhouseFacade instead of coordinating three independent subsystems. Subsystem A schedules irrigation after checking soil moisture. Subsystem B activates ventilation after checking temperature. Subsystem C dispenses nutrients after checking stock. Each subsystem has its own entity, validation rule, service and file repository. The facade persists workflow status and compensates earlier steps if a later step fails.

## Запуск

Откройте папку как Maven project в IntelliJ IDEA с JDK 17+. Запустите `mvn compile exec:java` из терминала проекта (или `greenhouse.Main` через IDE). `mvn verify` создаёт JaCoCo report. Проверить фактический процент обязательно.

## Что показать на защите

1. Сценарий 1: ID `success123` → `COMPLETED` и все три сущности присутствуют.
2. Сценарий 2: ID `failB123` → `COMPENSATED`, A = CANCELLED, B отсутствует.
3. Сценарий 3: ID `failC123` → `COMPENSATED`, B = STOPPED, A = CANCELLED.
4. Сценарий 4: ID `paused123` → PENDING_B; завершить программу и запустить снова.
5. Сценарий 5: `paused123` → COMPLETED. Сценарий 6: показать состояние.
6. В каталоге data открыть файлы `.properties`, чтобы доказать Persistence.

## Типичные вопросы

**Why Facade, not Adapter?** Facade simplifies a complex multi-service workflow. Adapter converts incompatible interfaces.

**Why compensation instead of transaction rollback?** Steps already saved separate files; we reverse persisted business actions rather than roll back one database transaction.

**How do you handle failure in B?** The facade cancels the persisted A reservation.

**How do you handle failure in C?** It checks persisted B state, stops B, then cancels A.

**What is Dependency Injection?** Services receive repositories and rules in constructors, which makes them easier to test independently.

**What's still not production-ready?** File writes are not atomic, there is no concurrency control, and a crash between subsystem write and workflow-stage save needs additional reconciliation.

## Перед сдачей

- Убедитесь, что вариант №9 разрешён преподавателем.
- Выполните `mvn test`, `mvn verify`, проверьте JaCoCo >= 80% для нужных классов. В этом пакете фактическая Maven coverage не подтверждена.
- Создайте GitHub repository, добавьте исходники и тесты. Историю commit-ов нельзя восстановить задним числом; сделайте свои commits при доработках.
- Отправьте URL репозитория в Moodle.
