# Sandbox

## Purpose

The sandbox contains experimental code that extends the base classes from `src/`.

## MeetingEngine

The MeetingEngine manages multiple meetings and processes commands.

### Command Types
- `start` - Create a new meeting
- `stop` - Pause a meeting
- `resume` - Resume a paused meeting
- `note` - Add a note to the meeting
- `end` - End and summarize the meeting

### Running

```bash
npm run run:sandbox
```

### Greeting Behavior

**When Joining:** Agent greets everyone, existing agents greet the newcomer.

**Resume:** Agents announce they're back and greet each other.

---

## Stage 4 — External Command Function + CLI

- `MeetingEngine.handleExternalCommand()` is now the universal entry point.
- Production systems, tests, and CLI all call this function.
- `CLI.parse()` converts raw text into a `Command`.
- `SandboxRunner.ts` now launches CLI mode.

---

## Rules
- Sandbox classes extend real classes in `src/`
- No modification to `src/` or `test/`
- All new logic lives in `sandbox/`

---

## Stage 3 — Demo Flow Moved to Tests

- The demo scenario previously in `SandboxRunner.ts` is now a repeatable test.
- `SandboxRunner.ts` is reserved for CLI mode in Stage 4.
- A new test file `stage3.demoFlow.test.ts` validates the full meeting lifecycle.
