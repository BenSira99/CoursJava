# PLAN-mini-dashboard.md

## Overview
Transform the current application into a professional mini dashboard for managing "Personne" entities (Code, Name, Salary). The project will use SQLite for persistent data storage and separate windows for CRUD operations, following a clean architecture with a light teal design.

## Project Type: WEB (Standalone Java Desktop App)
*Note: Although it's a desktop app, the roles from `frontend-specialist` for UI and `backend-specialist` for DB logic apply.*

## Success Criteria
- [ ] Maven handles SQLite dependency automatically.
- [ ] Database is initialized with predefined data on first launch.
- [ ] Main dashboard displays a `JTable` with all entries.
- [ ] "Insertion" button opens a form; successful insertion updates the table.
- [ ] "Modifier" and "Supprimer" actions in the table redirect to dedicated status/form pages.
- [ ] Visual style is professional, using a light teal (Sarcelle) palette.

## Tech Stack
- **Language**: Java 21
- **GUI Framework**: Java Swing
- **Database**: SQLite (via `sqlite-jdbc`)
- **Build System**: Maven
- **Design**: Modern Teal Theme (Custom styles)

## File Structure
```text
src/main/java/com/bensira/
├── model/
│   └── Personne.java           [NEW]
├── database/
│   └── BaseDonnees.java        [NEW]
├── service/
│   └── PersonneService.java    [NEW]
├── ui/
│   ├── FenetrePrincipale.java  [MODIFY]
│   ├── FenetreSaisie.java      [NEW] (Unified Add/Edit)
│   ├── FenetreAction.java      [NEW] (Status/Delete Info)
│   └── styles/
│       └── StyleTeal.java      [NEW] (Utility for UI)
├── Main.java                   [MODIFY]
```

## Task Breakdown

### Phase 1: Foundation (P0) - [backend-specialist]
- **T1: Configuration Maven**
  - **Input**: `pom.xml`
  - **Output**: `pom.xml` with `sqlite-jdbc` dependency.
  - **Verify**: `mvn compile` runs without error.
- **T2: Database Manager**
  - **Input**: None (New file)
  - **Output**: `database/BaseDonnees.java` handling SQLite connection and table creation/seeding.
  - **Verify**: SQLite `.db` file is created with initial data.

### Phase 2: Core Logic (P1) - [backend-specialist]
- **T3: Entity & Service**
  - **Input**: DB Schema
  - **Output**: `model/Personne.java` and `service/PersonneService.java` with CRUD methods.
  - **Verify**: Unit test or simple main test for CRUD operations.

### Phase 3: UI/UX (P2) - [frontend-specialist]
- **T4: Central Dashboard (FenetrePrincipale)**
  - **Input**: `PersonneService`
  - **Output**: Redesigned `FenetrePrincipale` with `JTable` and "Insertion" button.
  - **Verify**: View table with data on launch.
- **T5: Forms & Actions**
  - **Input**: Feature requirements
  - **Output**: `FenetreSaisie.java` and `FenetreAction.java` for CRUD feedback.
  - **Verify**: Clicking "Modifier" or "Supprimer" opens the correct window.

### Phase 4: Cleanup & Polish
- **T6: Removing Legacy Code**
  - **Input**: `FenetreInformation.java`, `FenetreTraitement.java`
  - **Output**: DELETED files, updated references in `FenetrePrincipale`.
  - **Verify**: App runs without legacy imports.

## Phase X: Verification
- [ ] `mvn clean compile` passes.
- [ ] No hardcoded English in UI (User rule matches French for names/labels).
- [ ] Color check: Light teal palette applied.
- [ ] CRUD Flow: Add -> Read (Table) -> Update -> Delete (Confirm) -> Read (Updated Table).
