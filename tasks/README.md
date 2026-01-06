# Shop Card Application - Tasks

This folder contains the task breakdown for recreating the Shop Card application, organized similar to Jira/Agile project management.

## Folder Structure

```
tasks/
├── README.md                              # This file - overview and navigation
└── EPIC-001-create-shop-card-app/        # Epic folder with all related tasks
    ├── EPIC-001-create-shop-card-app.md  # Epic description
    ├── TASK-001-setup-and-database.md
    ├── TASK-002-domain-and-repository.md
    ├── TASK-003-rest-api.md
    ├── TASK-004-security.md
    └── TASK-005-testing.md
```

## Current Epics

| ID | Title | Story Points | Status |
|----|-------|--------------|--------|
| [EPIC-001](EPIC-001-create-shop-card-app/EPIC-001-create-shop-card-app.md) | Create Shop Card Application | 13 | ✓ Done |

## EPIC-001: Create Shop Card Application

| ID | Title | Points | Status |
|----|-------|--------|--------|
| [TASK-001](EPIC-001-create-shop-card-app/TASK-001-setup-and-database.md) | Project Setup & Database Schema | 3 | ✓ Done |
| [TASK-002](EPIC-001-create-shop-card-app/TASK-002-domain-and-repository.md) | Domain Model & Repository Layer | 3 | ✓ Done |
| [TASK-003](EPIC-001-create-shop-card-app/TASK-003-rest-api.md) | REST API Controller | 3 | ✓ Done |
| [TASK-004](EPIC-001-create-shop-card-app/TASK-004-security.md) | Security Configuration | 2 | ✓ Done |
| [TASK-005](EPIC-001-create-shop-card-app/TASK-005-testing.md) | Testing & Verification | 2 | ✓ Done |

## How to Use

### For New Epics
1. Create a new folder: `EPIC-XXX-epic-name/`
2. Inside that folder, create:
   - `EPIC-XXX-epic-name.md` (epic description)
   - `TASK-XXX-task-name.md` (related tasks)
3. Update this README with the new epic and tasks

### For This Project
1. Navigate to [EPIC-001](EPIC-001-create-shop-card-app/)
2. Read the [epic file](EPIC-001-create-shop-card-app/EPIC-001-create-shop-card-app.md) for overview
3. Follow tasks in order (TASK-001 → TASK-005)

## Development Workflow

```
TASK-001 → TASK-002 → TASK-003 → TASK-004 → TASK-005
  (Setup)  (Model)     (API)      (Security)  (Tests)
```

## Quick Start

To recreate this application from scratch:

1. Read [EPIC-001](EPIC-001-create-shop-card-app/EPIC-001-create-shop-card-app.md) for overview
2. Follow [TASK-001](EPIC-001-create-shop-card-app/TASK-001-setup-and-database.md) to set up project
3. Continue through TASK-002 to TASK-005 in sequence
4. Verify with `./mvnw test` - all 18 tests should pass

## Story Points Legend

- **1 point**: Simple, straightforward task (~1-2 hours)
- **2 points**: Moderate complexity (~2-4 hours)
- **3 points**: Complex task requiring careful implementation (~4-8 hours)
- **5 points**: Large complex task (~1-2 days)
- **8 points**: Very large task, consider breaking down (~2-4 days)

## Naming Conventions

- **Epic Folders**: `EPIC-XXX-short-description/`
- **Epic Files**: `EPIC-XXX-short-description.md` (inside epic folder)
- **Task Files**: `TASK-XXX-short-description.md` (inside epic folder)
- Use sequential numbering across all epics and tasks
- Use kebab-case for folder and file names

## Example: Adding New Epic

When you want to add a new epic (e.g., "Payment Integration"):

```
tasks/
├── README.md
├── EPIC-001-create-shop-card-app/        (existing)
│   ├── EPIC-001-create-shop-card-app.md
│   └── TASK-001 to TASK-005 ...
└── EPIC-002-payment-integration/         (new)
    ├── EPIC-002-payment-integration.md
    ├── TASK-006-stripe-setup.md
    ├── TASK-007-payment-controller.md
    └── TASK-008-payment-tests.md
```

Benefits:
- ✅ Each epic is self-contained
- ✅ Easy to navigate and find related tasks
- ✅ Clear separation between different features
- ✅ Scalable for large projects with many epics
