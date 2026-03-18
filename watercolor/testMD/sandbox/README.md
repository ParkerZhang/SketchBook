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

## Rules
- Sandbox classes extend real classes in `src/`
- No modification to `src/` or `test/`
- All new logic lives in `sandbox/`
