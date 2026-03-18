# Stage 2 — MeetingEngine, Command-Driven Architecture & Greetings

This document defines the complete Stage 2 implementation including the MeetingEngine and greeting behavior.

All changes occur **only inside `/sandbox`** and **inside `/test`**. Do **not** modify `/src`.

---

# A. Command Model

Create:

```
sandbox/Command.ts
```

```typescript
export type Command =
  | { type: "start"; subject: string }
  | { type: "stop"; subject: string }
  | { type: "resume"; subject: string }
  | { type: "note"; subject: string; text: string }
  | { type: "end"; subject: string };
```

---

# B. Meeting Class

Modify:

```
sandbox/Meeting.ts
```

### Properties
- `subject: string`
- `active: boolean`
- `notes: string[]`
- `agents: AgentSandbox[]`
- `log: string[]`

### Methods
- `tick()` — empty for now
- `addNote(text: string)` — append to notes
- `summarize()` — return summary string
- `addAgent(agent: AgentSandbox)` — add agent and trigger greetings
- `runInitialGreetings()` — all agents greet via session.process()
- `resumeGreetings()` — agents greet each other when meeting resumes
- `getAgentCount()` — return agent count

### Initial Greeting Behavior
When agent joins:
1. Log: `"${agent.name} joins the meeting."`
2. Agent responds via `session.process('Hello everyone!')`
3. Existing agents respond to newcomer via `session.process()`

### Resume Greeting Behavior
When meeting resumes:
1. Log: `"${agent.name} is back!"`
2. Agent responds via `session.process('Hello everyone, I am back!')`
3. All agents greet each other with welcome messages

---

# C. MeetingEngine

Create:

```
sandbox/MeetingEngine.ts
```

### Responsibilities
- Maintain a map of active meetings
- Maintain a command queue
- Provide `sendCommand()` for external input
- Run a forever loop using `setInterval()`
- Dispatch commands to meetings
- Call `meeting.tick()` on active meetings
- Expose `tick()` for synchronous command processing
- Expose `getMeeting()` for direct meeting access

### Command Handling

| Command | Behavior |
|---------|----------|
| `start` | Create new Meeting, set active=true |
| `stop` | Set active=false |
| `resume` | Set active=true, call `resumeGreetings()` |
| `note` | Append text to meeting.notes |
| `end` | Call summarize(), archive (placeholder), remove from map |

---

# D. SandboxRunner

Modify:

```
sandbox/SandboxRunner.ts
```

Demonstrates full meeting lifecycle:
1. START meeting
2. Add agents (greetings triggered)
3. Initial discussion with notes
4. PAUSE meeting (stop)
5. RESUME meeting (resume greetings)
6. Continue discussion
7. END meeting

---

# E. Test Cases

Create:

```
test/stage2.greetings.test.ts
```

Tests verify:
- Agent joins are logged
- Agents respond via session.process() when joining
- Meeting.runInitialGreetings() produces responses for all agents
- Adding new agent triggers responses from existing agents
- Meeting.log contains greeting responses
- Full meeting lifecycle: start, 3 agents join, greetings, end

---

# F. README

Update:

```
sandbox/README.md
```

Document:
- Purpose of MeetingEngine
- Command model
- How to send commands
- How the tick loop works
- How to run SandboxRunner
- Greeting behavior
- Resume greeting behavior

---

# Completed Features

| Feature | Status |
|---------|--------|
| Command model | Implemented |
| MeetingEngine with forever loop | Implemented |
| Meeting lifecycle (start, stop, resume, note, end) | Implemented |
| Tick loop | Implemented |
| Initial greeting behavior | Implemented |
| Resume greeting behavior | Implemented |
| Test suite | Implemented |

# Not Yet Implemented

- Agent conversation logic
- Meeting discussion behavior
- Archive storage
- External command interface (CLI)

---

# Next: Stage 3

Externalize the meeting command interface so commands can be sent from:
- command line
- external scripts
- other processes

Stage 3 will introduce:
- a CLI command dispatcher
- a simple input loop or stdin listener
- a command parser
- a multi-meeting controller
